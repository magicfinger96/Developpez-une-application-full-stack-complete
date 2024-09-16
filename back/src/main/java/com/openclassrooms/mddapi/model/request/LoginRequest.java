package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represents a login request.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "The username or email is required and can't be empty.")
    private String emailOrUsername;

    @NotBlank(message = "The password is required and can't be empty.")
    private String password;
}
