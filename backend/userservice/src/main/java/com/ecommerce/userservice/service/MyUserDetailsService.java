package com.ecommerce.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.entity.UserPrinciple;
import com.ecommerce.userservice.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        System.out.println(user);
        if(user != null){
            return new UserPrinciple(user);
        }else{
            throw new UsernameNotFoundException("User Not Found !!");
        }
    }
}
