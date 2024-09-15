package com.manu.roleplaybackend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.services.GameService;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    
    @Autowired
    GameService gameService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createCharacter(@RequestParam(value = "img", required = false) MultipartFile img, @ModelAttribute Game game) {
        if (img != null) {
            try {
                game.setPicture(img.isEmpty() ? null : img.getBytes());
            } catch (IOException ioe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot read your file");
            }
        }
        
        return gameService.createGame(game);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        return gameService.getGames();
    }

    @PatchMapping("/update/status")
    public ResponseEntity<Object> changeStatus(@RequestBody Game game) {
        return gameService.updateStatus(game);
    }

    @GetMapping("/lobby/{gameId}")
    public ResponseEntity<Object> getLobbyInformation(@PathVariable Integer gameId) {
        return gameService.getLobbyInformation(gameId);
    }
    

}
