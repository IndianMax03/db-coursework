package com.manu.roleplaybackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.Game;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class GameService {
    
    @Autowired
    private DataBase dataBase;

    public ResponseEntity<Object> createGame(Game game) {
        if (!game.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid game data");
        }
        return dataBase.createGame(game);
    }

    public ResponseEntity<Object> getGames() {
        return dataBase.getAllGames();
    }

    public ResponseEntity<Object> updateStatus(Game game) {
        return dataBase.updateGameStatus(game);
    }

    public ResponseEntity<Object> getLobbyInformation( Integer gameId) {
        if (gameId == null || gameId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid game data");
        }
        return dataBase.getLobbyInformation(gameId);
    }

}
