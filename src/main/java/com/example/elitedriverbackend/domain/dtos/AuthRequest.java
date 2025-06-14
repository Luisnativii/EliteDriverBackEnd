package com.example.elitedriverbackend.domain.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}