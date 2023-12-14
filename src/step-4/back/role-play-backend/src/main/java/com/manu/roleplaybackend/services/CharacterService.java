package com.manu.roleplaybackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.Character;

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

}
