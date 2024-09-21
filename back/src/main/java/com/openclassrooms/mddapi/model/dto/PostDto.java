package com.openclassrooms.mddapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * DTO of a post.
 */
@Data
public class PostDto {
    int id;

    @NotBlank(message = "Le titre est requis.")
    String title;

    @NotBlank(message = "Le contenu est requis.")
    String content;

    Integer author_id;

    @NotNull(message = "Le thème est requis.")
    Integer topic_id;

    Date created_at;
}
