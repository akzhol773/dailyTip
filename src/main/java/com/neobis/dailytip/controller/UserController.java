package com.neobis.dailytip.controller;

import com.neobis.dailytip.dto.UsersDto;
import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.service.UserService;
import com.neobis.dailytip.util.TipSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TipSender tipSender;

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UsersDto user){

          userService.addUser(user);
          tipSender.sendFirstTip(user.email(), user.name());
          return ResponseEntity.ok().body("Added successfully");

    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        userService.unsubscribeUser(email);
        return ResponseEntity.ok().body("User unsubscribed  successfully");
    }
}
