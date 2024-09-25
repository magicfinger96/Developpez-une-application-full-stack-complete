package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Represents a register request.
 */
@Data
public class RegisterRequest {

    @Size(max = 255, message = "L'email ne doit pas excéder 255 caractères.")
    @Email
    @NotBlank(message = "L'adresse email est requise.")
    private String email;

    @Size(max = 255, message = "Le nom d'utilisateur ne doit pas excéder 255 caractères.")
    @NotBlank(message = "Le nom d'utilisateur est requis.")
    private String username;

    @Size(max = 255, message = "Le mot de passe ne doit pas excéder 255 caractères.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&^#()\\-_+=\\[\\]{}:;\"'<>.,?/\\\\|~])[A-Za-z\\d@$!%*?&^#()\\-_+=\\[\\]{}:;\"'<>.,?/\\\\|~]{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, dont une lettre majuscule, une lettre minuscule et un caractère spécial."
    )
    private String password;

}