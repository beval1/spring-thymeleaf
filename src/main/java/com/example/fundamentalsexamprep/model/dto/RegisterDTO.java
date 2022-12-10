package com.example.fundamentalsexamprep.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterDTO {
    @Size(min = 3, max = 20)
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @Size(min = 3, max = 20)
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
