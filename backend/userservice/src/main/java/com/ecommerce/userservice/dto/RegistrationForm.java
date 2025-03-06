package com.ecommerce.userservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
    private String address;
    private long pincode;
    private String password;
}
