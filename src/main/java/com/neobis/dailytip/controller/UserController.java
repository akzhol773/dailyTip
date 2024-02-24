package com.neobis.dailytip.controller;

import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody Users user){
         return userService.addUser(user);

    }
}
