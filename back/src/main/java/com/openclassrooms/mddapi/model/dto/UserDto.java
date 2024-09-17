package com.openclassrooms.mddapi.model.dto;


import lombok.Data;

/**
 * DTO of a user.
 */
@Data
public class UserDto {
    int id;
    String username;
    String email;
}

