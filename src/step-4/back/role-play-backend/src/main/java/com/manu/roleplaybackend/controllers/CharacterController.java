package com.manu.roleplaybackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.services.CharacterService;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    CharacterService characterService;
    
    @PostMapping("/create")
    public ResponseEntity<Object> createCharacter(@RequestBody Character character) {
        return characterService.createCharacter(character);
    }

    @PostMapping("/request")
    public ResponseEntity<Object> createRequest(@RequestBody LobbyRequest lobbyRequest) {
        return characterService.createRequest(lobbyRequest);
    }

}
