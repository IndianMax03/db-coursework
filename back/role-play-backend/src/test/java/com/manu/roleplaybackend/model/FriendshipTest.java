package com.manu.roleplaybackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendshipTest {

    Friendship validFriendship;

    @BeforeEach
    void setUp() {
        validFriendship = new Friendship(1, 2, "on-review");
    }

    @Test
    void isValidWithValid() {
        assertTrue(validFriendship.isValid());
    }

    @Test
    void isInvalidWithNegativeSenderId() {
        validFriendship.setSenderId(-1);
        assertFalse(validFriendship.isValid());
    }

    @Test
    void isInvalidWithNullSenderId() {
        validFriendship.setSenderId(null);
        assertFalse(validFriendship.isValid());
    }

    @Test
    void isInvalidWithNegativeReceiverId() {
        validFriendship.setReceiverId(-1);
        assertFalse(validFriendship.isValid());
    }

    @Test
    void isInvalidWithNullReceiverId() {
        validFriendship.setReceiverId(null);
        assertFalse(validFriendship.isValid());
    }

    @Test
    void isInvalidWitEqualSenderIdAndReceiverId() {
        validFriendship.setReceiverId(validFriendship.getSenderId());
        assertFalse(validFriendship.isValid());
    }

}