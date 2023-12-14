package com.manu.roleplaybackend.controllers;

import com.manu.roleplaybackend.model.User;
import com.manu.roleplaybackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enter")
public class EnterController {

    @Autowired
    private UserService userService;

    public EnterController() { }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // @GetMapping("gimmeUsers")
    // public Iterable<User> gimmeUsers() {
    //     return dataBase.getUsers();
    // }

    // @PostMapping("add")
    // public Optional<User> addUser(@RequestBody User user) {
    //     System.out.println(user);
    //     dataBase.addUser(user);
    //     return Optional.of(user);
    // }

    // @PostMapping("addGame")
    // public Optional<Game> addGame(@RequestBody Game game) {
    //     System.out.println(game);
    //     dataBase.addGame(game);
    //     return Optional.of(game);
    // }

}
