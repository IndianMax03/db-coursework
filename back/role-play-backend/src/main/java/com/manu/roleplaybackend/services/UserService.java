package com.manu.roleplaybackend.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.manu.roleplaybackend.model.*;
import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.request.Friend;
import com.manu.roleplaybackend.model.request.Reviewer;
import com.manu.roleplaybackend.repositories.*;
import com.manu.roleplaybackend.security.JwtUtils;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UserService {

    @Autowired
    JdbcTemplate template;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<Object> register(User user) {
        final String mainPassword = user.getPassword();
        UserDetailsImpl userDetails = UserDetailsImpl.fromUser(user);
        userDetails.setUsername(userDetails.getUsername());
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setPassword(userDetails.getPassword());

        try {
            userRepository.save(user);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), mainPassword
            ));

            String token = jwtUtils.generateJwtToken(authentication);

            userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.status(HttpStatus.CREATED).header("token", token).body(userRepository.findByLogin(user.getLogin()).get());
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: cannot create user");
        }
    }

    public ResponseEntity<Object> login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getLogin(), user.getPassword()
            ));
            String token = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.status(HttpStatus.OK).header("token", token).body(userRepository.findByLogin(user.getLogin()).get());

        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(401).body("Permission denied");

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: cannot create user");
        }
    }

    public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Object> getByLogin(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> becomeMaster(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with login = " + login);
        }
        User user = opUser.get();
        Optional<Role> opMasterRole = rolesRepository.findByName("master");
        if (opMasterRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no master role in database");
        }
        Role masterRole = opMasterRole.get();

        List<UserRole> userRoles = userRolesRepository.findAllByUserId(user.getId());

        for (UserRole userRole : userRoles) {
            if (Objects.equals(userRole.getRoleId(), masterRole.getId())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User already master!");
            }
        }

        UserRole newRole = new UserRole();
        newRole.setUserId(user.getId());
        newRole.setRoleId(masterRole.getId());
        userRolesRepository.save(newRole);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.getReasonPhrase());
    }

    public ResponseEntity<Object> getUserRoles(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with login = " + login);
        }

        User user = opUser.get();
        List<Role> roles = rolesRepository.findAll();
        List<UserRole> userRoles = userRolesRepository.findAllByUserId(user.getId());
        List<Integer> userRolesIds = userRoles.stream().map(UserRole::getRoleId).toList();
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            if (userRolesIds.contains(role.getId())) {
                roleNames.add(role.getName());
            }
        }

        return new ResponseEntity<>(roleNames, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserCharacters(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isPresent()) {
            Integer userId = opUser.get().getId();
            List<Character> characters = characterRepository.findAllByUserId(userId);
            return new ResponseEntity<>(characters, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> getUserGames(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isPresent()) {
            Integer userId = opUser.get().getId();
            List<Game> games = gameRepository.findAllByMasterId(userId);
            return new ResponseEntity<>(games, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> updateKarma(UpdateKarmaRequest updKarmaRequest) {
        if (!updKarmaRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }

        Optional<User> opUser = userRepository.findById(updKarmaRequest.getReceiverUserId());
        if (opUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with id = " + updKarmaRequest.getReceiverUserId());
        }

        User user = opUser.get();
        user.setKarma(user.getKarma() + (updKarmaRequest.isIncrease() ? 1 : -1));
        userRepository.save(user);

        Map<String, Integer> responseNode = new HashMap<>();
        responseNode.put("karma", user.getKarma());
        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    public ResponseEntity<Object> updateFriendRequestStatus(Friendship friendship) {
        if (!friendship.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        Friendship existingFriendship = friendshipRepository.findBySenderIdAndReceiverId(friendship.getSenderId(), friendship.getReceiverId());
        existingFriendship.setCurrentStatus(friendship.getCurrentStatus());
        friendshipRepository.save(existingFriendship);
        Map<String, String> responseNode = new HashMap<>();
        responseNode.put("currentStatus", existingFriendship.getCurrentStatus());
        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    public ResponseEntity<Object> friendRequest(Friendship friendship) {
        if (!friendship.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        Friendship newFriendship = friendshipRepository.save(friendship);
        Map<String, String> responseNode = new HashMap<>();
        responseNode.put("currentStatus", newFriendship.getCurrentStatus());
        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    public ResponseEntity<Object> getFriends(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with login = " + login);
        }
        User user = opUser.get();
        String outcomeSql = "select users.id id, users.login login, users.name name, users.picture picture, users.karma karma, users.timezone timezone, users.telegram_tag telegram_tag, users.vk_tag vk_tag, users.current_status current_status, friendships.current_status friendships_status from friendships join users on users.id=friendships.receiver_user_id where sender_user_id=" + user.getId();
        List<Friend> outcome = null;
        try {
            outcome = template.query(outcomeSql, new RowMapper<Friend>() {
                @Override
                @Nullable
                public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Friend friend = new Friend();
                    friend.setId(rs.getInt("id"));
                    friend.setLogin(rs.getString("login"));
                    friend.setName(rs.getString("name"));
                    friend.setPicture(rs.getBytes("picture"));
                    friend.setKarma(rs.getInt("karma"));
                    friend.setTimezone(rs.getString("timezone"));
                    friend.setTelegramTag(rs.getString("telegram_tag"));
                    friend.setVkTag(rs.getString("vk_tag"));
                    friend.setCurrentStatus(rs.getString("current_status"));
                    friend.setFriendshipStatus(rs.getString("friendships_status"));
                    return friend;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        String incomeSql = "select users.id id, users.login login, users.name name, users.picture picture, users.karma karma, users.timezone timezone, users.telegram_tag telegram_tag, users.vk_tag vk_tag, users.current_status current_status, friendships.current_status friendships_status from friendships join users on users.id=friendships.sender_user_id where receiver_user_id=" + user.getId();
        List<Friend> income = null;
        try {
            income = template.query(incomeSql, new RowMapper<Friend>() {
                @Override
                @Nullable
                public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Friend friend = new Friend();
                    friend.setId(rs.getInt("id"));
                    friend.setLogin(rs.getString("login"));
                    friend.setName(rs.getString("name"));
                    friend.setPicture(rs.getBytes("picture"));
                    friend.setKarma(rs.getInt("karma"));
                    friend.setTimezone(rs.getString("timezone"));
                    friend.setTelegramTag(rs.getString("telegram_tag"));
                    friend.setVkTag(rs.getString("vk_tag"));
                    friend.setCurrentStatus(rs.getString("current_status"));
                    friend.setFriendshipStatus(rs.getString("friendships_status"));
                    return friend;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        Map<String, List<Friend>> responseNode = new HashMap<>();
        responseNode.put("outcome", outcome);
        responseNode.put("income", income);
        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    public ResponseEntity<Object> leaveReview(Review review) {
        if (!review.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewRepository.save(review));
    }

    public ResponseEntity<Object> getReviews(String login) {
        Optional<User> opUser = userRepository.findByLogin(login);
        if (opUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with login = " + login);
        }
        User user = opUser.get();
        String sql = "select id, login, name, picture, karma, timezone, telegram_tag, vk_tag, current_status, content, date, rating from reviews join users on users.id=reviews.reviewer_user_id where recipient_user_id=" + user.getId();
        List<Reviewer> reviewers = null;
        try {
            reviewers = template.query(sql, new RowMapper<Reviewer>() {
                @Override
                @Nullable
                public Reviewer mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Reviewer reviewer = new Reviewer();
                    reviewer.setId(rs.getInt("id"));
                    reviewer.setLogin(rs.getString("login"));
                    reviewer.setName(rs.getString("name"));
                    reviewer.setPicture(rs.getBytes("picture"));
                    reviewer.setKarma(rs.getInt("karma"));
                    reviewer.setTimezone(rs.getString("timezone"));
                    reviewer.setTelegramTag(rs.getString("telegram_tag"));
                    reviewer.setVkTag(rs.getString("vk_tag"));
                    reviewer.setCurrentStatus(rs.getString("current_status"));
                    reviewer.setContent(rs.getString("content"));
                    reviewer.setDate(rs.getTimestamp("date"));
                    reviewer.setRating(rs.getInt("rating"));
                    return reviewer;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        return ResponseEntity.status(HttpStatus.OK).body(reviewers);
    }

    public String generateToken(String password) {

        StringBuilder sb = new StringBuilder();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException nsae) {
            return null;
        }
        return sb.toString();
    }

}
