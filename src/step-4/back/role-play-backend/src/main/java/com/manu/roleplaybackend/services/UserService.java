package com.manu.roleplaybackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.User;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class UserService {
    
    @Autowired
    private DataBase dataBase;

    public ResponseEntity<Object> register(User user) {
        if (!user.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
        }
        return dataBase.createUser(user);
    }

    public ResponseEntity<Object> login(User user) {
        if (user.validLogin() && user.validPassword()) {
            if (dataBase.findUserByLoginAndPassword(user)) {
                return ResponseEntity.status(HttpStatus.OK).header("token", dataBase.generateToken(user.getPassword())).body(HttpStatus.OK.getReasonPhrase());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    }

    public ResponseEntity<Object> getAll() {
        return dataBase.getAllUsers();
    }

    public ResponseEntity<Object> getByLogin(String login) {
        return dataBase.getUserByLogin(login);
    }

}
