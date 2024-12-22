package com.manu.roleplaybackend.services;

import com.manu.roleplaybackend.model.Character;
import com.manu.roleplaybackend.model.Game;
import com.manu.roleplaybackend.model.Lobby;
import com.manu.roleplaybackend.model.LobbyRequest;
import com.manu.roleplaybackend.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Mock
    JdbcTemplate template;

    @Mock
    private Character character;

    @Mock
    private Game game;

    @Mock
    Lobby lobby;

    @Mock
    private LobbyRequest lobbyRequest;

    @Mock
    CharacterRepository characterRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    LobbyRepository lobbyRepository;

    @Mock
    LobbyRequestRepository lobbyRequestRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private CharacterService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCharacterWithValidCharacter() {
        when(character.isValid()).thenReturn(true);

        ResponseEntity<Object> result = service.createCharacter(character);

        verify(character, times(1)).isValid();
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        verify(characterRepository, times(1)).save(character);
    }

    @Test
    void createCharacterWithInvalidCharacter() {
        when(character.isValid()).thenReturn(false);

        ResponseEntity<Object> result = service.createCharacter(character);

        verify(character, times(1)).isValid();
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(result.getBody(), "Invalid character data");
        verifyNoInteractions(characterRepository);
    }

    @Test
    void createLobbyRequest() {
        when(lobbyRequest.isValid()).thenReturn(true);
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(gameRepository.findById(any())).thenReturn(Optional.of(game)).thenReturn(Optional.of(game));
        when(character.getCurrentStatus()).thenReturn("free");
        when(character.getGameSystemId()).thenReturn(1);
        when(game.getGameSystemId()).thenReturn(1);
        when(lobbyRepository.findByGameId(any())).thenReturn(lobby);
        when(lobbyRequestRepository.save(lobbyRequest)).thenReturn(lobbyRequest);

        ResponseEntity<Object> result = service.createLobbyRequest(lobbyRequest);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    void updateRequest() {
        when(lobbyRequestRepository.findById(any())).thenReturn(Optional.of(lobbyRequest));

        ResponseEntity<Object> result = service.updateRequest(lobbyRequest);

        verify(lobbyRequestRepository, times(1)).save(lobbyRequest);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getLobbyInformationByCharacterId() {
        when(gameRepository.findById(any())).thenReturn(Optional.of(game)).thenReturn(Optional.of(game));

        ResponseEntity<Object> result = service.getLobbyInformationByCharacterId(1);

        verify(template, times(1)).queryForObject(anyString(), any(RowMapper.class));

    }
}