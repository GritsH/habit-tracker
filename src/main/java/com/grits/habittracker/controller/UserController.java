package com.grits.habittracker.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class UserController {
    //todo return ResponseEntity<>?

    @PostMapping("/signup")
    public void signup() {

    }

    @PostMapping("/login")
    public void login() {

    }

    @GetMapping("/users/{username}")
    public void getUserByUsername(@PathVariable String username) {

    }

}
