package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Represents a login request.
 */
@Data
public class LoginRequest {

    @NotBlank(message = "L'email ou le nom d'utilisateur est requis.")
    private String emailOrUsername;

    @NotBlank(message = "Le mot de passe est requis.")
    private String password;
}
