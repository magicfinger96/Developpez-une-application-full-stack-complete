package com.openclassrooms.mddapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * DTO of a post.
 */
@Data
public class PostDto {
    int id;
    String title;
    String content;
    UserDto author;
    TopicDto topic;
    Date creationDate;
}
