package com.manu.roleplaybackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.Friendship;
import com.manu.roleplaybackend.model.Review;
import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.model.request.UpdateKarmaRequest;

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
                return ResponseEntity.status(HttpStatus.OK).header("token", dataBase.generateToken(user.getPassword())).body(dataBase.getUserByLogin(user.getLogin()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
    }

    public ResponseEntity<Object> getAll() {
        return dataBase.getAllUsers();
    }

    public ResponseEntity<Object> getByLogin(String login) {
        Optional<User> opUser = dataBase.getUserByLogin(login);
        if (opUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(opUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> becomeMaster(String login) {
        Optional<User> opUser = dataBase.getUserByLogin(login);
        if (!opUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no user with login = " + login);
        }
        return dataBase.becomeMaster(opUser.get().getId());
    }

    public ResponseEntity<Object> getUserRoles(String login) {
        Optional<User> opUser = dataBase.getUserByLogin(login);
        if (opUser.isPresent()) {
            return dataBase.getUserRolesById(opUser.get().getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> getUserCharacters(String login) {
        Optional<User> opUser = dataBase.getUserByLogin(login);
        if (opUser.isPresent()) {
            return dataBase.getUserCharactersById(opUser.get().getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> getUserGames(String login) {
        Optional<User> opUser = dataBase.getUserByLogin(login);
        if (opUser.isPresent()) {
            return dataBase.getUserGamesById(opUser.get().getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with login " + login + " does not exist");
    }

    public ResponseEntity<Object> updateKarma(UpdateKarmaRequest updKarmaRequest) {
        if (!updKarmaRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        return dataBase.updateUserKarma(updKarmaRequest);
    }

    public ResponseEntity<Object> updateFriendRequestStatus(Friendship friendship) {
        if (!friendship.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        return dataBase.updateFriendRequestStatus(friendship);
    }

    public ResponseEntity<Object> friendRequest(Friendship friendship) {
        if (!friendship.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        return dataBase.friendRequest(friendship);
    }

    public ResponseEntity<Object> getFriends(String login) {
        return dataBase.getFriends(login);
    }

    public ResponseEntity<Object> leaveReview(Review review) {
        if (!review.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
        return dataBase.leaveReview(review);
    }

    public ResponseEntity<Object> getReviews(String login) {
        return dataBase.getReviews(login);
    }

}
