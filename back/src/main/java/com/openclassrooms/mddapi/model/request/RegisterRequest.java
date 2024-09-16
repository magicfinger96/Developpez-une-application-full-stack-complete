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
    @NotBlank(message = "L'adresse email est requise.")
    private String email;

    @NotBlank(message = "Le nom d'utilisateur est requis.")
    private String username;

    @NotBlank(message = "Le mot de passe est requis.")
    private String password;

}