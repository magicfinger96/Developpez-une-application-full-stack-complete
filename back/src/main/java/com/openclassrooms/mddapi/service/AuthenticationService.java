package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exception.AlreadyUsedEmailException;
import com.openclassrooms.mddapi.exception.AlreadyUsedUsernameException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.model.request.LoginRequest;
import com.openclassrooms.mddapi.model.request.RegisterRequest;
import com.openclassrooms.mddapi.model.response.AuthSuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

/**
 * Service which handles the authentication logic.
 */
@Service
public class AuthenticationService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    public JWTService jwtService;

    /**
     * Registers a user: encode the password and save the user in the DDB,
     * generates the token.
     *
     * @param registerRequest contains user data to save.
     * @return a AuthSuccessResponse object, containing the token.
     * @throws Exception exception if the username or email already exists.
     */
    public AuthSuccessResponse register(RegisterRequest registerRequest) throws Exception {

        if (userService.getUserDtoByEmail(registerRequest.getEmail()) != null) {
            throw new AlreadyUsedEmailException("L'adresse e-mail est déjà utilisée !");
        }

        if (userService.getUserDtoByUsername(registerRequest.getUsername()) != null) {
            throw new AlreadyUsedUsernameException("Le nom d'utilisateur est déjà utilisé !");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user = userService.saveUser(user);

        String token = jwtService.generateToken(user);
        return new AuthSuccessResponse(token);

    }

    /**
     * Logs the user in:
     * Checks the credentials and generate the token if it is correct.
     *
     * @param loginRequest contains credentials.
     * @return a AuthSuccessResponse object, containing the token.
     * @throws Exception if the credentials are wrong.
     */
    public AuthSuccessResponse login(LoginRequest loginRequest) throws Exception {
        User user = userService.getUserByEmailOrUsername(loginRequest.getEmailOrUsername());

        if ((user == null) || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception();
        }

        String token = jwtService.generateToken(user);
        return new AuthSuccessResponse(token);
    }

    /**
     * Get the authenticated user id.
     *
     * @return the id of the user.
     * @throws UserNotFoundException exception if there's no authenticated user.
     */
    public int getAuthenticatedUserId() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException("");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();

        try {
            return Integer.parseInt(jwt.getSubject());
        } catch (NumberFormatException e) {
            throw new UserNotFoundException("");
        }
    }
}

