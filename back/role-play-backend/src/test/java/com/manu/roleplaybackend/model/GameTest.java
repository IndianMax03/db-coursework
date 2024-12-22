package com.manu.roleplaybackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game validGame;

    @BeforeEach
    void setUp() {
        validGame = new Game(null, "Игра игрулька", 1, null, 1, LocalDate.now().toString(), "not-started", null, "Описание игры");
    }

    @Test
    void isValidWithValid() {
        assertTrue(validGame.isValid());
    }

    @Test
    void isInvalidWithEmptyName() {
        validGame.setName("");
        assertFalse(validGame.isValid());
    }

    @Test
    void isInvalidWithNullName() {
        validGame.setName(null);
        assertFalse(validGame.isValid());
    }

    @Test
    void isInvalidWithNegativeGameSystemId() {
        validGame.setGameSystemId(-1);
        assertFalse(validGame.isValid());
    }

    @Test
    void isInvalidWithNullGameSystemId() {
        validGame.setGameSystemId(null);
        assertFalse(validGame.isValid());
    }

    @Test
    void isInvalidWithNegativeMasterId() {
        validGame.setMasterId(-1);
        assertFalse(validGame.isValid());
    }

    @Test
    void isInvalidWithNullMasterId() {
        validGame.setMasterId(null);
        assertFalse(validGame.isValid());
    }

}