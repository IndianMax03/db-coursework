package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GameControllerTest {

    @Mock
    private GameService service;

    @Mock
    private Game game;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private GameController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame() {
        controller.createCharacter(null, game);
        verify(service, times(1)).createGame(game);
    }

    @Test
    void createGameWithImage() throws IOException {
        controller.createCharacter(file, game);
        verify(file, times(1)).getBytes();
        verify(game, times(1)).setPicture(file.getBytes());
        verify(service, times(1)).createGame(game);
    }

    @Test
    void getAllGames() {
        controller.getAllGames();
        verify(service, times(1)).getGames();
    }

    @Test
    void changeStatus() {
        controller.changeStatus(game);
        verify(service, times(1)).updateStatus(game);
    }

    @Test
    void getLobbyInformation() {
        Integer gameId = 1;
        controller.getLobbyInformation(gameId);
        verify(service, times(1)).getLobbyInformation(gameId);
    }
}