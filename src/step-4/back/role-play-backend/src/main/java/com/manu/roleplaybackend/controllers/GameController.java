package com.manu.roleplaybackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.services.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
    
    @Autowired
    GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Object> createCharacter(@RequestBody Game game) {
        return gameService.createGame(game);
    }

}
