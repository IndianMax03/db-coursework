package com.manu.roleplaybackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.LobbyRequest;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CharacterService {
    
    @Autowired
    private DataBase dataBase;

    public ResponseEntity<Object> createCharacter(Character character) {
        if (!character.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid character data");
        }
        return dataBase.createCharacter(character);
    }

    public ResponseEntity<Object> createRequest(LobbyRequest lobbyRequest) {
        if (!lobbyRequest.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid lobby request data");
        }
        return dataBase.createLobbyRequest(lobbyRequest);
    }

    public ResponseEntity<Object> getLobbyInformation( Integer chatacterId) {
        if (chatacterId == null || chatacterId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid character data");
        }
        return dataBase.getLobbyInformationByCharacterId(chatacterId);
    }

}
