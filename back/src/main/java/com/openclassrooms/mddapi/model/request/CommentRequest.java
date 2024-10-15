package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * The request to create a comment.
 */
@Data
public class CommentRequest {

    @NotNull(message = "L'article doit être renseigné.")
    Integer postId;

    @Size(max = 2000, message = "Le commentaire ne doit pas excéder 2000 caractères.")
    @NotBlank(message = "Le commentaire ne peut être vide.")
    String message;
}
