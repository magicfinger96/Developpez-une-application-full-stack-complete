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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Log the user in")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Logged in the user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthSuccessResponse.class))}),
            @ApiResponse(responseCode = "401", description = "The credentials are wrong", content = @Content),
            @ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})})
    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthSuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authenticationService.login(loginRequest);
            return ResponseEntity.ok(new AuthSuccessResponse(token));
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * End point registering a user.
     *
     * @param registerRequest the data used to create a new user.
     * @return a ResponseEntity containing the jwt token if succeeded. Otherwise, an
     * error ResponseEntity.
     */
    @Operation(summary = "Register the user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Registered the user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthSuccessResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "409", description = "The email or username is already registered", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))})})
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            String token = authenticationService.register(registerRequest);
            return ResponseEntity.ok(token);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * End point that returns the authenticated user.
     *
     * @return a ResponseEntity containing the user. Otherwise, returns an error
     * ResponseEntity.
     */
    @Operation(summary = "Fetch the authenticated user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fetched the user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "The user info can't be found", content = @Content),
            @ApiResponse(responseCode = "401", description = "There is no authenticated user", content = @Content)})
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

    /**
     * End point that modify the authenticated user.
     *
     * @param email    the new email.
     * @param username the new username.
     * @return a ResponseEntity containing a MessageResponse if successful or an error ResponseEntity.
     */
    @Operation(summary = "Update the authenticated user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated the user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Input data are missing or not valid", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "409", description = "The email or username is already used", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))}),
            @ApiResponse(responseCode = "401", description = "There is no authenticated user", content = @Content)})
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
        } catch (UserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        MessageResponse response = new MessageResponse("Votre profil a été mis à jour !");
        return ResponseEntity.ok(response);
    }
}

