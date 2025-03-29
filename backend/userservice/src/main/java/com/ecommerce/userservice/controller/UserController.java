package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.userservice.dto.LoginForm;
import com.ecommerce.userservice.dto.RegistrationForm;
import com.ecommerce.userservice.service.UserService;


@RestController
@RequestMapping("user")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationForm form) {
        return userService.registerUser(form);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(){
        System.out.println("Recieved at User Service");
        return ResponseEntity.ok("Got it");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginForm user , HttpServletResponse response){
        System.out.println("Reached login form.");
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            System.out.println("Token : "+jwtService.generateToken(user.getEmail()));
            String token = jwtService.generateToken(user.getEmail());
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            // cookie.setMaxAge(30000);
            cookie.setMaxAge(3600);
            cookie.setDomain("localhost");
            // cookie.set
            // cookie.setAttribute("SameSite", "Lax");
            // cookie.setSecure(false);
            response.addCookie(cookie);
            return ResponseEntity.ok(userService.getUsername(user.getEmail()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

//        return userService.loginUser(form);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestBody String email) {
        return userService.deleteUser(email);
    }
}
