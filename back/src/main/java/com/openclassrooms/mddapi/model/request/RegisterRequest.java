package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represents a register request.
 */
@Data
public class RegisterRequest {

    @Email
    @NotBlank(message = "The email is required and can't be empty.")
    private String email;

    @NotBlank(message = "The username is required and can't be empty.")
    private String username;

    @NotBlank(message = "The password is required and can't be empty.")
    private String password;

}