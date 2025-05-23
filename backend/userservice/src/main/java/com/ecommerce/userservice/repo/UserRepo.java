package com.ecommerce.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.userservice.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

    User findByEmail(String email);

    User findByPhoneNumber(long phoneNumber);
}
