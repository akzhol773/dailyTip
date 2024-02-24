package com.neobis.dailytip.service;

import com.neobis.dailytip.dto.UsersDto;
import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.exceptions.UserAlreadyExistException;
import com.neobis.dailytip.repository.UserRepository;
import com.neobis.dailytip.util.TipSender;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public ResponseEntity<String> addUser(UsersDto user) {
        Optional<Users> existingUser = userRepository.findByEmail(user.email());
        if (existingUser.isPresent()){
            throw new UserAlreadyExistException("User already exist");
        }
        Users users = new Users();
        users.setEmail(user.email());
        users.setName(user.name());
        userRepository.save(users);

        return ResponseEntity.ok().body("The user successfully added to the database");
    }
    public void unsubscribeUser(String email){
        Users existingUser = userRepository.findUsersByEmail(email);
        userRepository.delete(existingUser);
    }


    public List<Users> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users;
    }
}
