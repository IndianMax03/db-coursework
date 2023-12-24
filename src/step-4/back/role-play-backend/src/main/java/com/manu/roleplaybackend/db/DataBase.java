package com.manu.roleplaybackend.db;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.Friendship;
import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.Lobby;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.model.Review;
import com.manu.roleplaybackend.model.Role;
import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.model.request.CharacterRequest;
import com.manu.roleplaybackend.model.request.Friend;
import com.manu.roleplaybackend.model.request.LobbyInformation;
import com.manu.roleplaybackend.model.request.Reviewer;
import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;

import io.micrometer.common.lang.Nullable;

@Component
public class DataBase {

    @Autowired
    JdbcTemplate template;
    ObjectMapper mapper;
    
    public DataBase() {
        mapper = new ObjectMapper();
    }

    public ResponseEntity<Object> createUser(User user) {
        if (findUserByLoginAndPassword(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists. Try /enter/login.");
        }
        Integer userId = null;
        try {
            String slq = "select create_user(?, ?, ?, ?, ?, ?, ?, ?, ?::user_status)";
            userId = template.queryForObject(slq, Integer.class, user.getLogin(), user.getName(), user.getPassword(), mapper.writeValueAsBytes(user.getPicture()), user.getKarma(), user.getTimezone(), user.getTelegramTag(), user.getVkTag(), user.getCurrentStatus());
        } catch (DuplicateKeyException dke) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists. Try /enter/login.");
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        } catch (IOException ioe) {
            System.out.println(ioe);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
        }
        String token = generateToken(user.getPassword());
        if (token != null) {
            user.setId(userId);
            return ResponseEntity.status(HttpStatus.CREATED).header("token", token).body(user);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hashing trouble");
    }

    public ResponseEntity<Object> updateUserKarma(UpdateKarmaRequest updKarmaRequest) {

        if (!findUserById(updKarmaRequest.getSenderUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (sender) with id = " + updKarmaRequest.getSenderUserId());
        }
        if (!findUserById(updKarmaRequest.getReceiverUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (receiver) with id = " + updKarmaRequest.getReceiverUserId());
        }

        Integer karma = null;
        String operator = updKarmaRequest.isIncrease() ? "+" : "-";
        String sql = "update users set karma = karma " + operator + " 1 where id = ? returning karma";
        try {
            karma = template.queryForObject(sql, Integer.class, updKarmaRequest.getReceiverUserId());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (karma != null) {
            Map<String, Integer> responseNode = new HashMap<>();
            responseNode.put("karma", karma);
            return ResponseEntity.status(HttpStatus.OK).body(responseNode);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change user karma!");
    }

    public ResponseEntity<Object> updateLobbyRequest(LobbyRequest lobbyRequest) {
        if (!findLobbyRequestById(lobbyRequest.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no lobby request with id = " + lobbyRequest.getId());
        }

        String newStatus = null;
        String sql = "update lobby_requests set current_status = ?::request_status where id = ? returning current_status";
        try {
            newStatus = template.queryForObject(sql, String.class, lobbyRequest.getCurrentStatus(), lobbyRequest.getId());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (newStatus != null) {
            Map<String, String> responseNode = new HashMap<>();
            responseNode.put("current_status", newStatus);
            return ResponseEntity.status(HttpStatus.OK).body(responseNode);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change lobby request status!");

    }

    public ResponseEntity<Object> updateFriendRequestStatus(Friendship friendship) {
        if (!findUserById(friendship.getSenderId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (sender) with id = " + friendship.getSenderId());
        }
        if (!findUserById(friendship.getReceiverId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (receiver) with id = " + friendship.getReceiverId());
        }
        String newStatus = null;
        String sql = "update friendships set current_status = ?::request_status where (sender_user_id = ? and receiver_user_id = ?) returning current_status";
        try {
            newStatus = template.queryForObject(sql, String.class, friendship.getCurrentStatus(), friendship.getSenderId(), friendship.getReceiverId());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cursed sql statement! Contact MT urgently!");
        }
        if (newStatus != null) {
            Map<String, String> responseNode = new HashMap<>();
            responseNode.put("currentStatus", newStatus);
            return ResponseEntity.status(HttpStatus.OK).body(responseNode);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change request status!");
    }

    public ResponseEntity<Object> friendRequest(Friendship friendship) {
        if (!findUserById(friendship.getSenderId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (sender) with id = " + friendship.getSenderId());
        }
        if (!findUserById(friendship.getReceiverId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (receiver) with id = " + friendship.getReceiverId());
        }
        String currentStatus = null;
        try {
        String slq = "select create_friendship(?, ?, ?::request_status)";
        currentStatus = template.queryForObject(slq, String.class, friendship.getSenderId(), friendship.getReceiverId(), friendship.getCurrentStatus());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (currentStatus != null) {
            Map<String, String> responseNode = new HashMap<>();
            responseNode.put("currentStatus", currentStatus);
            return ResponseEntity.status(HttpStatus.OK).body(responseNode);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your friend request cannot be processed!");
    }

    public ResponseEntity<Object> getFriends(String login) {
        Optional<User> opUser = getUserByLogin(login);
        if (!opUser.isPresent()) {
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
                    try {
                        Friend friend = new Friend();
                        friend.setId(rs.getInt("id"));
                        friend.setLogin(rs.getString("login"));
                        friend.setName(rs.getString("name"));
                        friend.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        friend.setKarma(rs.getInt("karma"));
                        friend.setTimezone(rs.getString("timezone"));
                        friend.setTelegramTag(rs.getString("telegram_tag"));
                        friend.setVkTag(rs.getString("vk_tag"));
                        friend.setCurrentStatus(rs.getString("current_status"));
                        friend.setFriendshipStatus(rs.getString("friendships_status"));
                        return friend;
                    } catch (IOException ioe) {
                        return null;
                    }
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
                    try {
                        Friend friend = new Friend();
                        friend.setId(rs.getInt("id"));
                        friend.setLogin(rs.getString("login"));
                        friend.setName(rs.getString("name"));
                        friend.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        friend.setKarma(rs.getInt("karma"));
                        friend.setTimezone(rs.getString("timezone"));
                        friend.setTelegramTag(rs.getString("telegram_tag"));
                        friend.setVkTag(rs.getString("vk_tag"));
                        friend.setCurrentStatus(rs.getString("current_status"));
                        friend.setFriendshipStatus(rs.getString("friendships_status"));
                        return friend;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        Map<String, List<Friend>> responseNode = new HashMap<>();
        responseNode.put("outcome", outcome);
        responseNode.put("income", income);
        return ResponseEntity.status(HttpStatus.OK).body(responseNode);
    }

    public boolean findUserByLoginAndPassword(User user) {
        String sql = "select count(*) from users where (login = '" + user.getLogin() + "' and password = '" + user.getPassword() + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public boolean findUserById(Integer userId) {
        String sql = "select count(*) from users where (id = '" + userId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public boolean findGameById(Integer gameId) {
        String sql = "select count(*) from games where (id = '" + gameId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
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

    public ResponseEntity<Object> getAllUsers() {
        String sql = "select id, login, name, picture, karma, timezone, telegram_tag, vk_tag, current_status from users";
        List<User> result = null;
        try {
            result = template.query(sql, new RowMapper<User>() {
                @Override
                @Nullable
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("login"));
                        user.setName(rs.getString("name"));
                        user.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        user.setKarma(rs.getInt("karma"));
                        user.setTimezone(rs.getString("timezone"));
                        user.setTelegramTag(rs.getString("telegram_tag"));
                        user.setVkTag(rs.getString("vk_tag"));
                        user.setCurrentStatus(rs.getString("current_status"));
                        return user;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public Optional<User> getUserByLogin(String login) {
        String sql = "select id, login, name, picture, karma, timezone, telegram_tag, vk_tag, current_status from users where login = '" + login + "'";
        try {
            User result = template.queryForObject(sql, new RowMapper<User>() {
                @Override
                @org.springframework.lang.Nullable
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("login"));
                        user.setName(rs.getString("name"));
                        user.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        user.setKarma(rs.getInt("karma"));
                        user.setTimezone(rs.getString("timezone"));
                        user.setTelegramTag(rs.getString("telegram_tag"));
                        user.setVkTag(rs.getString("vk_tag"));
                        user.setCurrentStatus(rs.getString("current_status"));
                        return user;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(Integer id) {
        String sql = "select id, login, name, picture, karma, timezone, telegram_tag, vk_tag, current_status from users where id=" + id;
        try {
            User result = template.queryForObject(sql, new RowMapper<User>() {
                @Override
                @org.springframework.lang.Nullable
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setLogin(rs.getString("login"));
                        user.setName(rs.getString("name"));
                        user.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        user.setKarma(rs.getInt("karma"));
                        user.setTimezone(rs.getString("timezone"));
                        user.setTelegramTag(rs.getString("telegram_tag"));
                        user.setVkTag(rs.getString("vk_tag"));
                        user.setCurrentStatus(rs.getString("current_status"));
                        return user;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }

    public Optional<Character> getCharacterById(Integer id) {
        String sql = "select * from characters where id=" + id;
        try {
            Character result = template.queryForObject(sql, new RowMapper<Character>() {
                @Override
                @org.springframework.lang.Nullable
                public Character mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        Character character = new Character();
                        character.setId(rs.getInt("id"));
                        character.setName(rs.getString("name"));
                        character.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        character.setGameSystemId(rs.getInt("game_system_id"));
                        character.setUserId(rs.getInt("user_id"));
                        character.setCurrentStatus(rs.getString("current_status"));
                        character.setStats(rs.getBytes("stats") == null ? null : mapper.readValue(rs.getBytes("stats"), byte[][].class));
                        return character;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }
    
    public boolean findGameSystemById(Integer gameSystemId) {
        String sql = "select count(*) from game_systems where (id = '" + gameSystemId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public boolean findCharacterById(Integer characterId) {
        String sql = "select count(*) from characters where (id = '" + characterId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public boolean findLobbyById(Integer lobbyId) {
        String sql = "select count(*) from lobbies where (id = '" + lobbyId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public boolean findLobbyRequestById(Integer lobbyRequestId) {
        String sql = "select count(*) from lobby_requests where (id = '" + lobbyRequestId + "')";
        Integer result = template.queryForObject(sql, Integer.class);
        return result != null && result != 0;
    }

    public ResponseEntity<Object> createCharacter(Character character) {
        if (!findGameSystemById(character.getGameSystemId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game system with id = " + character.getGameSystemId());
        }
        if (!findUserById(character.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user with id = " + character.getUserId());
        }
        try {
            String slq = "select create_character(?, ?, ?, ?, ?::character_status, ?)";
            Integer characterId = template.queryForObject(slq, Integer.class, character.getName(), mapper.writeValueAsBytes(character.getPicture()), character.getGameSystemId(), character.getUserId(), character.getCurrentStatus(), mapper.writeValueAsBytes(character.getStats()));
            character.setId(characterId);
            return new ResponseEntity<Object>(character, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        } catch (IOException ioe) {
            System.out.println(ioe);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
    }

    public boolean isUserMasterById(Integer id) {
        try {
            String sql = "select is_user_master(?)";
            Boolean isMaster = template.queryForObject(sql, Boolean.class, id);
            return isMaster;
        } catch (DataAccessException dae) {
            System.out.println(dae);
        }
        return false;
    }

    public Optional<Game> getGameById(Integer gameId) {
        String sql = "select id, name, game_system_id, picture, master_id, creation_date, current_status, finish_date, description from games where id = " + gameId;
        try {
            Game result = template.queryForObject(sql, new RowMapper<Game>() {
                @Override
                @org.springframework.lang.Nullable
                public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        Game game = new Game();
                        game.setId(rs.getInt("id"));
                        game.setName(rs.getString("name"));
                        game.setGameSystemId(rs.getInt("game_system_id"));
                        game.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        game.setMasterId(rs.getInt("master_id"));
                        game.setCreationDate(rs.getString("creation_date"));
                        game.setCurrentStatus(rs.getString("current_status"));
                        game.setFinishDate(rs.getString("finish_date"));
                        game.setDescription(rs.getString("description"));
                        return game;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }
    
    public Optional<Lobby> getLobbyByGameId(Integer gameId) {
        String sql = "select id, game_id, format from lobbies where game_id=" + gameId;
        try {
            Lobby result = template.queryForObject(sql, new RowMapper<Lobby>() {
                @Override
                @org.springframework.lang.Nullable
                public Lobby mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Lobby lobby = new Lobby();
                    lobby.setId(rs.getInt("id"));
                    lobby.setGameId(rs.getInt("game_id"));
                    lobby.setFormat(rs.getString("format"));
                    return lobby;
                }
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        }
    }
    
    public ResponseEntity<Object> createGame(Game game) {
        if (!findGameSystemById(game.getGameSystemId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game system with id = " + game.getGameSystemId());
        }
        if (!isUserMasterById(game.getMasterId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with id = " + game.getMasterId() + " is not master!");
        }

        Integer gameId = null;
        try {
            
            String sql = "select create_game(?, ?, ?, ?, ?::game_status, ?)";
            gameId = template.queryForObject(sql, Integer.class, game.getName(), game.getGameSystemId(), mapper.writeValueAsBytes(game.getPicture()), game.getMasterId(), game.getCurrentStatus(), game.getDescription());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        } catch (IOException ioe) {
            System.out.println(ioe);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
        }

        if (gameId != null) {
            Optional<Game> newGame = getGameById(gameId);
            if (newGame.isPresent()) {
                return new ResponseEntity<Object>(newGame, HttpStatus.CREATED);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
    }

    public ResponseEntity<Object> updateGameStatus(Game game) {
        if (!findGameById(game.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + game.getId());
        }

        String sql = "update games set current_status = ?::game_status where id = ? returning id";
        Integer result = null;
        try {
            result = template.queryForObject(sql, Integer.class, game.getCurrentStatus(), game.getId());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Game status changed successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change game status!");
    }

    public ResponseEntity<Object> getUserRolesById(Integer id) {
        String sql = "select roles.id, roles.name from user_roles join roles on user_roles.role_id = roles.id join users on user_roles.user_id = users.id where users.id = " + id;
        List<Role> result = null;
        try {
            result = template.query(sql, new RowMapper<Role>() {
                @Override
                @Nullable
                public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    return role;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserCharactersById(Integer id) {
        String sql = "select * from characters where user_id = " + id;
        List<Character> result = null;
        try {
            result = template.query(sql, new RowMapper<Character>() {
                @Override
                @Nullable
                public Character mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        Character character = new Character(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("game_system_id"),
                        rs.getInt("user_id"),
                        rs.getString("current_status"),
                        rs.getBytes("stats") == null ? null : mapper.readValue(rs.getBytes("stats"), byte[][].class)
                        );

                        return character;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserGamesById(Integer id) {
        String sql = "select * from games where master_id = " + id;
        List<Game> result = null;
        try {
            result = template.query(sql, new RowMapper<Game>() {
                @Override
                @Nullable
                public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("game_system_id"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("master_id"),
                        rs.getString("creation_date"),
                        rs.getString("current_status"),
                        rs.getString("finish_date"),
                        rs.getString("description")
                        );
                        return game;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllGames() {
        String sql = "select * from games";
        List<Game> result = null;
        try {
            result = template.query(sql, new RowMapper<Game>() {
                @Override
                @Nullable
                public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        Game game = new Game(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("game_system_id"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("master_id"),
                        rs.getString("creation_date"),
                        rs.getString("current_status"),
                        rs.getString("finish_date"),
                        rs.getString("description")
                        );
                        return game;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> createLobbyRequest(LobbyRequest lobbyRequest) {
        if (!findCharacterById(lobbyRequest.getCharacterId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no character with id = " + lobbyRequest.getCharacterId());
        }
        if (!findGameById(lobbyRequest.getGameId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + lobbyRequest.getGameId());
        }

        Character character = getCharacterById(lobbyRequest.getCharacterId()).get();

        if (!character.getCurrentStatus().equals("free")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot register busy character!");
        }

        Game game = getGameById(lobbyRequest.getGameId()).get();

        if (character.getGameSystemId() != game.getGameSystemId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game systems don't match!");
        }

        Lobby lobby = getLobbyByGameId(lobbyRequest.getGameId()).get();
        lobbyRequest.setLobbyId(lobby.getId());
        Integer lobbyRequestId = null;
        try {
            String slq = "select create_lobby_request(?, ?, ?::request_status)";
            lobbyRequestId = template.queryForObject(slq, Integer.class, lobbyRequest.getLobbyId(), lobbyRequest.getCharacterId(), lobbyRequest.getCurrentStatus());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (lobbyRequestId != null) {
            lobbyRequest.setId(lobbyRequestId);
            return ResponseEntity.status(HttpStatus.CREATED).body(lobbyRequest);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot create lobby request! Contact MT urgently!");
    }

    public ResponseEntity<Object> leaveReview(Review review) {
        if (!findUserById(review.getReviewerId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (reviewer) with id = " + review.getReviewerId());
        }
        if (!findUserById(review.getRecipientId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no user (recipient) with id = " + review.getRecipientId());
        }
        Timestamp creationDate = null;
        try {
            String slq = "insert into reviews values (?, ?, ?, now(), ?) returning date";
            creationDate = template.queryForObject(slq, Timestamp.class, review.getReviewerId(), review.getRecipientId(), review.getContent(), review.getRating());
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        if (creationDate != null) {
            review.setDate(creationDate);
            return ResponseEntity.status(HttpStatus.CREATED).body(review);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cursed sql statement! Contact MT urgently!");
    }

    public ResponseEntity<Object> getReviews(String login) {
        Optional<User> opUser = getUserByLogin(login);
        if (!opUser.isPresent()) {
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
                    try {
                        Reviewer reviewer = new Reviewer();
                        reviewer.setId(rs.getInt("id"));
                        reviewer.setLogin(rs.getString("login"));
                        reviewer.setName(rs.getString("name"));
                        reviewer.setPicture(rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class));
                        reviewer.setKarma(rs.getInt("karma"));
                        reviewer.setTimezone(rs.getString("timezone"));
                        reviewer.setTelegramTag(rs.getString("telegram_tag"));
                        reviewer.setVkTag(rs.getString("vk_tag"));
                        reviewer.setCurrentStatus(rs.getString("current_status"));
                        reviewer.setContent(rs.getString("content"));
                        reviewer.setDate(rs.getTimestamp("date"));
                        reviewer.setRating(rs.getInt("rating"));
                        return reviewer;
                    } catch (IOException ioe) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(reviewers);
    }

    public ResponseEntity<Object> getLobbyInformation(Integer gameId) {
        
        Optional<Game> opGame = getGameById(gameId);
        if (!opGame.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no game with id = " + gameId);
        }
        Optional<Lobby> opLobby = getLobbyByGameId(gameId);
        if (!opLobby.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no lobby with id = " + gameId + ". Contact MT urgently!");
        }

        Game game = opGame.get();
        Lobby lobby = opLobby.get();
        User master = getUserById(game.getMasterId()).get();

        String sql = "select characters.id chid, name, picture, game_system_id, user_id, characters.current_status chstatus, stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id where lobby_requests.lobby_id=" + lobby.getId();
        List<CharacterRequest> characterRequests = null;
        try {
            characterRequests = template.query(sql, new RowMapper<CharacterRequest>() {
                @Override
                @Nullable
                public CharacterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        CharacterRequest characterRequest = new CharacterRequest(
                        rs.getInt("chid"),
                        rs.getString("name"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("game_system_id"),
                        rs.getInt("user_id"),
                        rs.getString("chstatus"),
                        rs.getBytes("stats") == null ? null : mapper.readValue(rs.getBytes("stats"), byte[][].class),
                        rs.getInt("lobreqid"),
                        rs.getString("lobreqstat")
                        );
                        return characterRequest;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        return ResponseEntity.status(HttpStatus.OK).body(new LobbyInformation(game, lobby, master, characterRequests));
    }

    public ResponseEntity<Object> getLobbyInformationByCharacterId(Integer characterId) {
        String sql = "select characters.id chid, characters.name name , characters.picture picture, characters.game_system_id game_system_id, characters.user_id user_id, characters.current_status chstatus, characters.stats stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id join lobbies on lobby_requests.lobby_id=lobbies.id join games on lobbies.game_id=games.id where (lobby_requests.character_id=" + characterId + " and lobby_requests.current_status <> cast('rejected' as request_status) and games.current_status <> cast('finished' as game_status))";
        CharacterRequest characterRequest = null;
        try {
            characterRequest = template.queryForObject(sql, new RowMapper<CharacterRequest>() {
                @Override
                @Nullable
                public CharacterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        CharacterRequest characterRequest = new CharacterRequest(
                        rs.getInt("chid"),
                        rs.getString("name"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("game_system_id"),
                        rs.getInt("user_id"),
                        rs.getString("chstatus"),
                        rs.getBytes("stats") == null ? null : mapper.readValue(rs.getBytes("stats"), byte[][].class),
                        rs.getInt("lobreqid"),
                        rs.getString("lobreqstat")
                        );
                        return characterRequest;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }

        if (characterRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your character is not yet registered in any of the lobbies");
        }

        Integer gameId = null;
        sql = "select games.id from characters join lobby_requests on lobby_requests.character_id=characters.id join lobbies on lobby_requests.lobby_id=lobbies.id join games on lobbies.game_id=games.id where (lobby_requests.character_id=" + characterId + " and lobby_requests.current_status <> cast('rejected' as request_status) and games.current_status <> cast('finished' as game_status))";
        
        try {
            gameId = template.queryForObject(sql, Integer.class);
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            dae.printStackTrace();
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }

        if (gameId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot find game for character with id = " + characterId);
        }

        Game game = getGameById(gameId).get();
        Lobby lobby = getLobbyByGameId(gameId).get();
        User master = getUserById(game.getMasterId()).get();

        sql = "select characters.id chid, characters.name name, characters.picture picture, characters.game_system_id game_system_id, characters.user_id user_id, characters.current_status chstatus, characters.stats stats, lobby_requests.id lobreqid, lobby_requests.current_status lobreqstat from characters join lobby_requests on characters.id=lobby_requests.character_id where lobby_requests.lobby_id=" + lobby.getId();
        List<CharacterRequest> characterRequests = null;
        try {
            characterRequests = template.query(sql, new RowMapper<CharacterRequest>() {
                @Override
                @Nullable
                public CharacterRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        CharacterRequest characterRequest = new CharacterRequest(
                        rs.getInt("chid"),
                        rs.getString("name"),
                        rs.getBytes("picture") == null ? null : mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("game_system_id"),
                        rs.getInt("user_id"),
                        rs.getString("chstatus"),
                        rs.getBytes("stats") == null ? null : mapper.readValue(rs.getBytes("stats"), byte[][].class),
                        rs.getInt("lobreqid"),
                        rs.getString("lobreqstat")
                        );
                        return characterRequest;
                    } catch (IOException ignore) {
                        return null;
                    }
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(new LobbyInformation(game, lobby, master, characterRequests));
        
    }

}
