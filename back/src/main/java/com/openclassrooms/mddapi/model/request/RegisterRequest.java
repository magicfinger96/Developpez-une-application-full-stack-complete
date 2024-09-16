package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&^#()\\-_+=\\[\\]{}:;\"'<>.,?/\\\\|~])[A-Za-z\\d@$!%*?&^#()\\-_+=\\[\\]{}:;\"'<>.,?/\\\\|~]{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, dont une lettre majuscule, une lettre minuscule et un caractère spécial."
    )
    private String password;

}