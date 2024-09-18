package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.exception.AlreadyUsedEmailException;
import com.openclassrooms.mddapi.exception.AlreadyUsedUsernameException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.request.LoginRequest;
import com.openclassrooms.mddapi.model.request.RegisterRequest;
import com.openclassrooms.mddapi.model.response.AuthSuccessResponse;
import com.openclassrooms.mddapi.model.response.MessageResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles the end points related to the user authentication.
 */
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;
    
    /**
     * End point logging the user in.
     *
     * @param loginRequest credentials used to log in.
     * @return a ResponseEntity containing the jwt token if succeeded. Otherwise, an
     * error ResponseEntity.
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthSuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthSuccessResponse response;
        try {
            response = authenticationService.login(loginRequest);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * End point registering a user.
     *
     * @param registerRequest the data used to create a new user.
     * @return a ResponseEntity containing the jwt token if succeeded. Otherwise, an
     * error ResponseEntity.
     */
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthSuccessResponse response;
        try {
            response = authenticationService.register(registerRequest);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * End point that returns the authenticated user.
     *
     * @return a ResponseEntity containing the user. Otherwise, returns an error
     * ResponseEntity.
     */
    @GetMapping("/api/auth/me")
    public ResponseEntity<UserDto> getMe() {

        int id;
        try {
            id = authenticationService.getAuthenticatedUserId();
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<UserDto> userDto = userService.getUserDtoById(id);
        return userDto.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    @PutMapping("/api/auth/me")
    public ResponseEntity<?> updateMe(
            @NotBlank @Email @RequestParam String email,
            @NotBlank @RequestParam String username
    ) {

        int authenticatedUserId;
        try {
            authenticatedUserId = authenticationService.getAuthenticatedUserId();
            userService.updateUser(authenticatedUserId, email, username);
        } catch (AlreadyUsedEmailException | AlreadyUsedUsernameException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        } catch(UserNotFoundException exception){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        MessageResponse response = new MessageResponse("Votre profil a été mis à jour !");
        return ResponseEntity.ok(response);
    }
}

