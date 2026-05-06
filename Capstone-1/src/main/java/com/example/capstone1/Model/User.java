package com.example.capstone1.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "id can not be empty")
    private String id;
    @NotEmpty(message = "username can not be empty")
    private String username;
    @NotEmpty(message = "password can not be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$",
            message = "Password must be 8-16 characters and include letters and numbers")
    private String password;
    @NotEmpty(message = "email can not be empty")
    @Email
    private String email;
    @NotEmpty(message = "role can not be empty")
    @Pattern(regexp = "(?i)^(Admin|Customer)$", message = "role must be either Admin or Customer")
    private String role;
    @NotNull(message = "balance can not be empty")
    private double balance;

    // extra
    private ArrayList<Product> purchaseHistory = new ArrayList<>();


}
