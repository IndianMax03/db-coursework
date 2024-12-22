package com.manu.roleplaybackend.services;

import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.Lobby;
import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.repositories.GameRepository;
import com.manu.roleplaybackend.repositories.LobbyRepository;
import com.manu.roleplaybackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

class GameServiceTest {

    @Mock
    private Game game;

    @Mock
    private User user;

    @Mock
    private Lobby lobby;

    @Mock
    JdbcTemplate template;

    @Mock
    GameRepository gameRepository;

    @Mock
    LobbyRepository lobbyRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    GameService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGameWithValidGame() {
        when(game.isValid()).thenReturn(true);
        when(game.getId()).thenReturn(1);
        when(gameRepository.save(game)).thenReturn(game);

        ResponseEntity<Object> result = service.createGame(game);

        verify(game, times(1)).isValid();
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        verify(gameRepository, times(1)).save(game);
        verify(lobbyRepository, times(1)).save(any());
    }

    @Test
    void createGameWithInvalidGame() {
        when(game.isValid()).thenReturn(false);

        ResponseEntity<Object> result = service.createGame(game);

        verify(game, times(1)).isValid();
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(result.getBody(), "Invalid game data");
        verifyNoInteractions(gameRepository);
        verifyNoInteractions(lobbyRepository);
    }

    @Test
    void getGames() {
        ResponseEntity<Object> result = service.getGames();

        verify(gameRepository, times(1)).findAll();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void updateStatus() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        ResponseEntity<Object> result = service.updateStatus(game);

        verify(gameRepository, times(1)).save(game);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getLobbyInformation() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(lobbyRepository.findByGameId(any())).thenReturn(lobby);
        when(game.getMasterId()).thenReturn(1);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(lobby.getId()).thenReturn(1);

        ResponseEntity<Object> result = service.getLobbyInformation(1);

        verify(template, times(1)).query(anyString(), any(RowMapper.class));
    }
}