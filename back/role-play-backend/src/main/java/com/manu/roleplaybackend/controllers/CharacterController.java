package com.manu.roleplaybackend.controllers;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
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

import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.services.CharacterService;


@RestController
@RequestMapping("/characters")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CharacterController {

    @Autowired
    CharacterService characterService;
    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createCharacter(@RequestParam(value = "img", required = false) MultipartFile img, @RequestParam(value = "pdf", required = false) MultipartFile pdf, @ModelAttribute Character character) {
        log.info("Got request: POST: characters/");
        if (img != null) {
            try {
                character.setPicture(img.isEmpty() ? null : img.getBytes());
            } catch (IOException ioe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot read your img");
            }
        }

        if (pdf != null) {
            try {
                character.setStats(pdf.isEmpty() ? null : pdf.getBytes());
            } catch (IOException ioe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot read your stats");
            }
        }
        
        return characterService.createCharacter(character);
    }

    @PostMapping("/request")
    public ResponseEntity<Object> createRequest(@RequestBody LobbyRequest lobbyRequest) {
        log.info("Got request: POST: characters/request");
        return characterService.createRequest(lobbyRequest);
    }

    @PatchMapping("/request/update")
    public ResponseEntity<Object> updateRequestStatus(@RequestBody LobbyRequest lobbyRequest) {
        log.info("Got request: PATCH: characters/request/update");
        return characterService.updateRequest(lobbyRequest);
    }

    @GetMapping("/{characterId}/lobby")
    public ResponseEntity<Object> getLobbyInformation(@PathVariable Integer characterId) {
        log.info("Got request: GET: characters/{}/lobby", characterId);
        return characterService.getLobbyInformation(characterId);
    }

}
