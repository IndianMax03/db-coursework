package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.services.UserService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/enter")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "token")
public class EnterController {

    @Autowired
    private UserService userService;

    public EnterController() { }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> registerWithIMG(@RequestParam(value = "img", required = false) MultipartFile img, @ModelAttribute User user) {
        if (img != null) {
            try {
                user.setPicture(img.isEmpty() ? null : img.getBytes());
            } catch (IOException ioe) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot read your file");
            }
        }
        
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        return userService.login(user);
    }

}
