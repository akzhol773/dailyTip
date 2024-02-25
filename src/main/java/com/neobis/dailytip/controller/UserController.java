package com.neobis.dailytip.controller;


import com.neobis.dailytip.dto.UsersDto;
import com.neobis.dailytip.entities.Users;
import com.neobis.dailytip.service.UserService;
import com.neobis.dailytip.util.TipSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TipSender tipSender;


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @GetMapping("/deletePage")
    public String deleteUser(Model model) {
        model.addAttribute("user", new Users());
        return "deleteForm";
    }




    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") UsersDto user, BindingResult result){
        Users existingUserEmail = userService.findByEmail(user.email());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            return "redirect:register?fail";
        }
        if(result.hasErrors()){
            return "register"; // No need to add 'user' attribute here, Thymeleaf automatically redisplay form data
        }
        userService.addUser(user);
        tipSender.sendFirstTip(user.email(), user.name());
        return "registerSuccess";
    }


    @PostMapping("/deleteUser")
    public String delUser(@Valid @ModelAttribute("user") UsersDto user, BindingResult result){
        Users existingUserEmail = userService.findByEmail(user.email());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()){
            userService.deleteUserByEmail(user.email());
            return "deleteSuccess";
        }
        if(result.hasErrors()){
            return "deleteForm"; // No need to add 'user' attribute here, Thymeleaf automatically redisplay form data
        }

        return "redirect:deletePage?fail ";
    }

}
