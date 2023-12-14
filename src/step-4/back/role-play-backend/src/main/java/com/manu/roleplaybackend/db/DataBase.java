package com.manu.roleplaybackend.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

import com.manu.roleplaybackend.model.User;

import io.micrometer.common.lang.Nullable;

@Component
public class DataBase {

    @Autowired
    JdbcTemplate template;
    
    public DataBase() {

    }

    public ResponseEntity<Object> createUser(User user) {
        if (findUserByLoginAndPassword(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists. Try /enter/login.");
        }
        try {
            String slq = "select create_user(?, ?, ?, ?, ?, ?, ?, ?, ?::user_status)";
            template.update(slq, user.getLogin(), user.getName(), user.getPassword(), user.getPicture(), user.getKarma(), user.getTimezone(), user.getTelegramTag(), user.getVkTag(), user.getCurrentStatus());
        } catch (DuplicateKeyException dke) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists. Try /enter/login.");
        } catch (DataIntegrityViolationException igonre) {
            System.out.println(igonre.getClass());
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        String token = generateToken(user.getPassword());
        if (token != null) {
            return ResponseEntity.status(HttpStatus.CREATED).header("token", token).body("User created succesfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hashing trouble");
    }

    public boolean findUserByLoginAndPassword(User user) {
        String sql = "select count(*) from users where (login = '" + user.getLogin() + "' and password = '" + user.getPassword() + "')";
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
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setLogin(rs.getString("login"));
                    user.setName(rs.getString("name"));
                    user.setPicture(rs.getBytes("picture"));
                    user.setKarma(rs.getInt("karma"));
                    user.setTimezone(rs.getString("timezone"));
                    user.setTelegramTag(rs.getString("telegram_tag"));
                    user.setVkTag(rs.getString("vk_tag"));
                    user.setCurrentStatus(rs.getString("current_status"));
                    return user;
                }
            });
        } catch (EmptyResultDataAccessException ignore) {
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserByLogin(String login) {
        String sql = "select id, login, name, picture, karma, timezone, telegram_tag, vk_tag, current_status from users where login = '" + login + "'";
        try {
            User result = template.queryForObject(sql, new RowMapper<User>() {
                @Override
                @org.springframework.lang.Nullable
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setLogin(rs.getString("login"));
                    user.setName(rs.getString("name"));
                    user.setPicture(rs.getBytes("picture"));
                    user.setKarma(rs.getInt("karma"));
                    user.setTimezone(rs.getString("timezone"));
                    user.setTelegramTag(rs.getString("telegram_tag"));
                    user.setVkTag(rs.getString("vk_tag"));
                    user.setCurrentStatus(rs.getString("current_status"));
                    return user;
                }
            });
            return new ResponseEntity<Object>(result, HttpStatus.OK);
        } catch (EmptyResultDataAccessException erde) {
            return new ResponseEntity<Object>("User with login " + login + " not found", HttpStatus.NOT_FOUND);
        }
    }

}
