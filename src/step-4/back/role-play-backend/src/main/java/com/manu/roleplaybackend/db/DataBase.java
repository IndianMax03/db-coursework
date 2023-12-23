package com.manu.roleplaybackend.db;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.model.Role;
import com.manu.roleplaybackend.model.User;

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
                        user.setPicture(mapper.readValue(rs.getBytes("picture"), byte[][].class));
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
                        user.setPicture(mapper.readValue(rs.getBytes("picture"), byte[][].class));
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
                        game.setPicture(mapper.readValue(rs.getBytes("picture"), byte[][].class));
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
                        mapper.readValue(rs.getBytes("picture"), byte[][].class),
                        rs.getInt("game_system_id"),
                        rs.getInt("user_id"),
                        rs.getString("current_status"),
                        mapper.readValue(rs.getBytes("stats"), byte[][].class)
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
                        mapper.readValue(rs.getBytes("picture"), byte[][].class),
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
                        mapper.readValue(rs.getBytes("picture"), byte[][].class),
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
        if (!findLobbyById(lobbyRequest.getLobbyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no lobby with id = " + lobbyRequest.getLobbyId());
        }
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

}
