package com.openclassrooms.mddapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO of a comment.
 */
@Data
public class CommentDto {

    @NotBlank(message = "Le commentaire ne doit pas être vide.")
    String message;

    String author;

}
