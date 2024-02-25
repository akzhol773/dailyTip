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
    public void addUser(UsersDto user) {
        Users users = new Users();
        users.setEmail(user.email());
        users.setName(user.name());
        userRepository.save(users);
    }



    public List<Users> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return users;
    }

    public Users findByEmail(String email) {
        Users existingUser = userRepository.findByEmail(email);
        return existingUser;
    }

    public void deleteUserByEmail(String email) {
        Optional<Users> userOptional = userRepository.findUsersByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found");
        }
    }
}
