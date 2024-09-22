package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * The request to create a comment.
 */
@Data
public class CommentRequest {

    @NotNull(message = "L'article doit être renseigné.")
    Integer postId;

    @NotBlank(message = "Le commentaire ne peut être vide.")
    String message;
}
