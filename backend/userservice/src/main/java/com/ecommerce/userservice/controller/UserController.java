package com.ecommerce.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.userservice.dto.LoginForm;
import com.ecommerce.userservice.dto.RegistrationForm;
import com.ecommerce.userservice.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationForm form) {
        return userService.registerUser(form);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginForm form) {// ,HttpServletResponse response){

        return userService.loginUser(form);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody String email) {
        return userService.deleteUser(email);
    }
}
