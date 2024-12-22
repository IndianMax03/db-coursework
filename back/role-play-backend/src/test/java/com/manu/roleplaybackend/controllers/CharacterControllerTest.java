package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.*;

class CharacterControllerTest {

    @Mock
    private CharacterService service;

    @Mock
    private Character character;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private CharacterController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCharacter() {
        controller.createCharacter(null, null, character);
        verify(service, times(1)).createCharacter(character);
    }

    @Test
    void createCharacterWithImage() throws IOException {
        controller.createCharacter(file , null, character);
        verify(file, times(1)).getBytes();
        verify(character, times(1)).setPicture(file.getBytes());
        verify(service, times(1)).createCharacter(character);
    }

    @Test
    void createCharacterWithPdf() throws IOException {
        controller.createCharacter(null , file, character);
        verify(file, times(1)).getBytes();
        verify(character, times(1)).setStats(file.getBytes());
        verify(service, times(1)).createCharacter(character);
    }

    @Test
    void createRequest() {
        LobbyRequest lobbyRequest = new LobbyRequest();
        controller.createRequest(lobbyRequest);
        verify(service, times(1)).createLobbyRequest(lobbyRequest);
    }

    @Test
    void updateRequestStatus() {
        LobbyRequest lobbyRequest = new LobbyRequest();
        controller.updateRequestStatus(lobbyRequest);
        verify(service, times(1)).updateRequest(lobbyRequest);
    }

    @Test
    void getLobbyInformation() {
        Integer characterId = 1;
        controller.getLobbyInformation(characterId);
        verify(service, times(1)).getLobbyInformationByCharacterId(characterId);
    }

}