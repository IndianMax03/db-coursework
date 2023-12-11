package com.manu.roleplaybackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    public MainController() { }
    
    @GetMapping("ready")
    public String getName() {
        return "ready";
    }

}
