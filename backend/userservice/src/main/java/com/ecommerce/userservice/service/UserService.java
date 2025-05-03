package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.ResponseItem;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exceptions.PhoneAlreadyExists;
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
import com.ecommerce.userservice.exceptions.EmailAlreadyExists;
import com.ecommerce.userservice.exceptions.UserNotFound;
import com.ecommerce.userservice.repo.UserRepo;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

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
        User user2 = userRepo.findByPhoneNumber(form.getPhoneNumber());

        if (user == null && user2 == null) {
//            String firstName, String lastName, String email, int phoneNumber, String address, int pincode, String password
              userRepo.save(new User(form.getFirstName(),form.getLastName(),form.getEmail(),form.getPhoneNumber(),form.getAddress(), form.getPincode(), new BCryptPasswordEncoder(12).encode(form.getPassword())));
//            userRepo.save(new User(form.getFirstName(),form.getLastName(),form.getEmail(),new BCryptPasswordEncoder(12).encode(form.getPassword())));
            return new ResponseEntity<String>("Account Created !!", HttpStatus.OK);
        }else if(user != null){
            throw new EmailAlreadyExists("Email Already Exists !!");
        }else{
            throw new PhoneAlreadyExists("Phone Number Already Exists !!");
        }
    }

    //This method is not getting used, need to use this method rather than direct executing logic in controller class.
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

    public ResponseEntity<Object> getLoginResponse(String email){
        User user = userRepo.findByEmail(email);
        return ResponseEntity.ok(new ResponseItem(user.getFirstName(),user.getId()));
    }

    public ResponseEntity<User> getAccountInfo(int userId){
        Optional<User> user = userRepo.findById(userId);
        User foundUser = user.orElseThrow(() -> new UserNotFound("User with id "+userId+" not found !!"));
        return ResponseEntity.ok(foundUser);
    }
}
