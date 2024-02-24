package com.neobis.dailytip.service;

import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.exceptions.UserAlreadyExistException;
import com.neobis.dailytip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public ResponseEntity<String> addUser(Users user) {
        Optional<Users> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()){
            throw new UserAlreadyExistException("User already exist");
        }

        userRepository.save(user);
        return ResponseEntity.ok().body("The user successfully added to the database");
    }


}
