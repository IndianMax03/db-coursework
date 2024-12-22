package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EnterControllerTest {

    @Mock
    private UserService service;

    @Mock
    private User user;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private EnterController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        controller.registerWithIMG(null, user);
        verify(service, times(1)).register(user);
    }

    @Test
    void registerWithImage() throws IOException {
        controller.registerWithIMG(file, user);
        verify(file, times(1)).getBytes();
        verify(user, times(1)).setPicture(file.getBytes());
        verify(service, times(1)).register(user);
    }

    @Test
    void login() {
        controller.login(user);
        verify(service, times(1)).login(user);
    }
}