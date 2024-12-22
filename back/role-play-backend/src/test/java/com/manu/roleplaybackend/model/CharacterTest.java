package com.manu.roleplaybackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    Character validCharacter;

    @BeforeEach
    void setUp() {
        validCharacter = new Character(null, "Леприкон", null, 1, 1, "free", null);
    }

    @Test
    void isValidWithValid() {
        assertTrue(validCharacter.isValid());
    }

    @Test
    void isInvalidWithEmptyName() {
        validCharacter.setName("");
        assertFalse(validCharacter.isValid());
    }

    @Test
    void isInvalidWithNullName() {
        validCharacter.setName(null);
        assertFalse(validCharacter.isValid());
    }

    @Test
    void isInvalidWithNegativeGameSystemId() {
        validCharacter.setGameSystemId(-2);
        assertFalse(validCharacter.isValid());
    }

    @Test
    void isInvalidWithNullGameSystemId() {
        validCharacter.setGameSystemId(null);
        assertFalse(validCharacter.isValid());
    }

    @Test
    void isInvalidWithNegativeUserId() {
        validCharacter.setUserId(-2);
        assertFalse(validCharacter.isValid());
    }

    @Test
    void isInvalidWithNullUserId() {
        validCharacter.setUserId(null);
        assertFalse(validCharacter.isValid());
    }

}