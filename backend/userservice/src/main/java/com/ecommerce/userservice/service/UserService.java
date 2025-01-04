package com.ecommerce.userservice.service;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.userservice.dto.LoginForm;
import com.ecommerce.userservice.dto.RegistrationForm;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exceptions.EmailAlreadyExists;
import com.ecommerce.userservice.exceptions.UserNotFound;
import com.ecommerce.userservice.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<String> registerUser(RegistrationForm form){
        User user = userRepo.findByEmail(form.getEmail());
        if (user == null) {
            userRepo.save(new User(form.getFirstName(),form.getLastName(),form.getEmail(),new BCryptPasswordEncoder(12).encode(form.getPassword())));
            return new ResponseEntity<String>("Account Created !!", HttpStatus.OK);
        }else{
            throw new EmailAlreadyExists("Email Already Exists !!");
        }
    }

    public ResponseEntity<String> loginUser(LoginForm form){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(form.getEmail(),form.getPassword()));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(form.getEmail());
            // Cookie cookie = new Cookie("token", token);
            // cookie.setHttpOnly(true);
            // cookie.setPath("/");
            // cookie.setMaxAge(3600);
            // cookie.setDomain("localhost");
            // response.addCookie(cookie);
            return ResponseEntity.ok(token);
        }
        throw new UserNotFound("User Not Found !!");
    }

    public ResponseEntity<String> deleteUser(String email){
        User user = userRepo.findByEmail(email);
        if(user != null){
            userRepo.delete(user);
            return new ResponseEntity<>("User Deleted !!",HttpStatus.OK);
        }else{
            throw new UserNotFound("User Does not exists !!");
        }
    }
}
