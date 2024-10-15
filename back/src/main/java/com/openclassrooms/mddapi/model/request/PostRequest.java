package com.openclassrooms.mddapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {
    @Size(max = 255, message = "Le titre ne doit pas excéder 255 caractères.")
    @NotBlank(message = "Le titre est requis.")
    String title;

    @Size(max = 2000, message = "Le contenu ne doit pas excéder 2000 caractères.")
    @NotBlank(message = "Le contenu est requis.")
    String content;

    @NotNull(message = "Le thème est requis.")
    Integer topicId;
}
