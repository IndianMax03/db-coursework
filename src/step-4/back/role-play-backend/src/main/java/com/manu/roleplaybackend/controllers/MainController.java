package com.manu.roleplaybackend.controllers;

import java.util.Optional;

import com.manu.roleplaybackend.db.DataBase;
import com.manu.roleplaybackend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    private DataBase dataBase;

    public MainController() { }

    @GetMapping("ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("add")
    public Optional<User> addUser(@RequestBody User user) {
        System.out.println(user);
        dataBase.addUser(user);
        return Optional.of(user);
    }

}
