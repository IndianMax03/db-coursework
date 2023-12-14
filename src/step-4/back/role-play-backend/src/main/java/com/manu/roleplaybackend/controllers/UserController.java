package com.manu.roleplaybackend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manu.roleplaybackend.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{login}")
    public ResponseEntity<Object> getUserByLogin(@PathVariable String login) {
        return userService.getByLogin(login);
    }

}
