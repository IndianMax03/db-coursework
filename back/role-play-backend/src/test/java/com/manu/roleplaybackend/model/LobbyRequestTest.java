package com.manu.roleplaybackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LobbyRequestTest {

    LobbyRequest validLobbyRequest;

    @BeforeEach
    void setUp() {
        validLobbyRequest = new LobbyRequest(null, 1, 1, 1, "on-review");
    }

    @Test
    void isValidWithValid() {
        assertTrue(validLobbyRequest.isValid());
    }

    @Test
    void isInvalidWithNegativeGameId() {
        validLobbyRequest.setGameId(-1);
        assertFalse(validLobbyRequest.isValid());
    }

    @Test
    void isInvalidWithNullGameId() {
        validLobbyRequest.setGameId(null);
        assertFalse(validLobbyRequest.isValid());
    }

    @Test
    void isInvalidWithNegativeCharacterId() {
        validLobbyRequest.setCharacterId(-1);
        assertFalse(validLobbyRequest.isValid());
    }

    @Test
    void isInvalidWithNullCharacterId() {
        validLobbyRequest.setCharacterId(null);
        assertFalse(validLobbyRequest.isValid());
    }

}