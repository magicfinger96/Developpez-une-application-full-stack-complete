package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Handles the end points related to the users.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * End point that provides a user.
     *
     * @param id id of the user.
     * @return a ResponseEntity containing the user if the call succeeded.
     * Otherwise, returns an error ResponseEntity.
     */
    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") final Integer id) {
        Optional<UserDto> userDto = userService.getUserDtoById(id);
        return userDto.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
