package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.model.request.LoginRequest;
import com.openclassrooms.mddapi.model.request.RegisterRequest;
import com.openclassrooms.mddapi.model.response.AuthSuccessResponse;
import com.openclassrooms.mddapi.service.AuthenticationService;
import com.openclassrooms.mddapi.service.JWTService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Handles the end points related to the user authentication.
 */
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    public JWTService jwtService;

    /**
     * End point logging the user in.
     *
     * @param loginRequest credentials used to log in.
     * @return a ResponseEntity containing the jwt token if succeeded. Otherwise, an
     * error ResponseEntity.
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthSuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            User user = authenticationService.login(loginRequest);
            String token = jwtService.generateToken(user);
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
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {


        if (userService.getUserDtoByEmail(registerRequest.getEmail()) != null) {
            return new ResponseEntity<>("L'adresse e-mail est déjà utilisée !", HttpStatus.CONFLICT);
        }

        if (userService.getUserDtoByUsername(registerRequest.getUsername()) != null) {
            return new ResponseEntity<>("Le nom d'utilisateur est déjà utilisé !", HttpStatus.CONFLICT);
        }

        User user = authenticationService.register(registerRequest);
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthSuccessResponse(token));
    }

    /**
     * End point that returns the authenticated user.
     *
     * @return a ResponseEntity containing the user. Otherwise, returns an error
     * ResponseEntity.
     */
    @GetMapping("/api/auth/me")
    public ResponseEntity<UserDto> getMe() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();

        return ResponseEntity.ok(userService.getUserDtoByEmail(email));
    }
}

