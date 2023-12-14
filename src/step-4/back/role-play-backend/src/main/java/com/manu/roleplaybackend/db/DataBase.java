package com.manu.roleplaybackend.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.model.User;

@Component
public class DataBase {

    @Autowired
    JdbcTemplate template;
    
    public DataBase() {

    }

    public ResponseEntity<String> createUser(User user) {
        try {
            String slq = "select create_user(?, ?, ?, ?, ?, ?, ?, ?, ?::user_status)";
            template.update(slq, user.getLogin(), user.getName(), user.getPassword(), user.getPicture(), user.getKarma(), user.getTimezone(), user.getTelegramTag(), user.getVkTag(), user.getCurrentStatus());
        } catch (DataIntegrityViolationException igonre) {
        } catch (DataAccessException dae) {
            System.out.println(dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Serious error detected! Contact MT urgently!");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            md.update(user.getLogin().getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            String generatedPassword = sb.toString();
            return ResponseEntity.status(HttpStatus.CREATED).header("token", generatedPassword).body("User created succesfully");
        } catch (NoSuchAlgorithmException nsae) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hashing trouble");
        }
    }

    // public Iterable<User> getUsers() {
    //     String sql = "select * from users";
    //     List<User> result = template.query(sql, new RowMapper<User>() {
    //         @Override
    //         @Nullable
    //         public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    //             User user = new User();
    //             user.setId(rs.getInt("id"));
    //             user.setPicture(rs.getBytes("picture"));
    //             return user;
    //         }
    //     });
    //     return result;
    // }

}
