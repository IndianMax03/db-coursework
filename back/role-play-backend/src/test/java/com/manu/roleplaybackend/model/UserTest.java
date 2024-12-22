package com.manu.roleplaybackend.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User(null, "login", "name", "password", null, 0, "UTC+5", "@telegramTag" ,"@vkTag", "exists");
    }

    @Test
    void isValidWithValid() {
        assertTrue(validUser.isValid());
    }

    @Test
    void isInvalidWithEmptyLogin() {
        validUser.setLogin("");
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithNullLogin() {
        validUser.setLogin(null);
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithLoginLengthGreaterThan32() {
        int invalidLoginLength = 33;
        validUser.setLogin(StringUtils.repeat("*", invalidLoginLength));
        assertEquals(invalidLoginLength, validUser.getLogin().length());
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithEmptyName() {
        validUser.setName("");
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithNullName() {
        validUser.setName(null);
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithNameLengthGreaterThan128() {
        int invalidNameLength = 129;
        validUser.setName(StringUtils.repeat("*", invalidNameLength));
        assertEquals(invalidNameLength, validUser.getName().length());
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithEmptyPassword() {
        validUser.setPassword("");
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithNullPassword() {
        validUser.setPassword(null);
        assertFalse(validUser.isValid());
    }

    @Test
    void isInvalidWithPasswordLengthGreaterThan128() {
        int invalidPasswordLength = 129;
        validUser.setPassword(StringUtils.repeat("*", invalidPasswordLength));
        assertEquals(invalidPasswordLength, validUser.getPassword().length());
        assertFalse(validUser.isValid());
    }

}