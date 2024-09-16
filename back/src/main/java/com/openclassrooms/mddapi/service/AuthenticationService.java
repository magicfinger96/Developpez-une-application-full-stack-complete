package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.model.request.LoginRequest;
import com.openclassrooms.mddapi.model.request.RegisterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /**
     * Save a new user in the DDB. Encodes the password before.
     *
     * @param registerRequest contains user data to save.
     * @return the newly created user.
     */
    public User register(RegisterRequest registerRequest) {

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.saveUser(user);
    }

    /**
     * Check if the credentials are valid and returns the user if it is.
     *
     * @param loginRequest contains credentials.
     * @return the corresponding user.
     * @throws Exception if the user details is null.
     */
    public User login(LoginRequest loginRequest) throws Exception {

        String emailOrUsername = loginRequest.getEmailOrUsername();
        User user = userService.getUserByEmail(emailOrUsername);

        if (user == null) {
            user = userService.getUserByUsername(emailOrUsername);
        }

        if ((user == null) || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new Exception();
        }
        return user;
    }
}

