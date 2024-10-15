package com.openclassrooms.mddapi.model.dto;

import lombok.Data;

/**
 * DTO of a comment.
 */
@Data
public class CommentDto {
    
    String message;
    String author;

}
