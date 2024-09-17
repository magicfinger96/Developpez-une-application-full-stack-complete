package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.exception.AlreadyUsedEmailException;
import com.openclassrooms.mddapi.exception.AlreadyUsedUsernameException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service which handles the users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save the user.
     *
     * @param user user to be saved.
     * @return the new saved user.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Get a User.
     *
     * @param id id of the user fetched.
     * @return the user.
     */
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Get a User.
     *
     * @param username username of the user fetched.
     * @return the user.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get a User.
     *
     * @param email email of the user fetched.
     * @return the user.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get the DTO of a User.
     *
     * @param email email of the user fetched.
     * @return a UserDto.
     */
    public UserDto getUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Get the DTO of a User.
     *
     * @param username username of the user fetched.
     * @return a UserDto.
     */
    public UserDto getUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * Get the DTO of a User.
     *
     * @param id id of the user fetched.
     * @return a UserDto.
     */
    public Optional<UserDto> getUserDtoById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(modelMapper.map(user, UserDto.class));
    }

    /**
     * Get User with the given email or username.
     *
     * @param emailOrUsername email or username used to find the user.
     * @return the user corresponding to the information.
     */
    public User getUserByEmailOrUsername(String emailOrUsername) {
        User user = getUserByEmail(emailOrUsername);
        if (user == null) {
            user = getUserByUsername(emailOrUsername);
        }
        return user;
    }

    /**
     * Updates a user.
     *
     * @param userId   user id to update.
     * @param email    the new email.
     * @param username the new username.
     * @throws AlreadyUsedUsernameException if the username is already used.
     * @throws AlreadyUsedEmailException    if the email is already used.
     * @throws UserNotFoundException        if the user to update is not found.
     */
    public void updateUser(int userId, String email, String username) throws AlreadyUsedUsernameException, AlreadyUsedEmailException, UserNotFoundException {

        User userWithUsername = getUserByUsername(username);
        User userWithEmail = getUserByEmail(email);

        if (userWithUsername != null && userWithUsername.getId() != userId) {
            throw new AlreadyUsedUsernameException("Le nom d'utilisateur est déjà utilisé.");
        }

        if (userWithEmail != null && userWithEmail.getId() != userId) {
            throw new AlreadyUsedEmailException("L'adresse e-mail est déjà utilisée.");
        }

        User user = getUserById(userId).orElseThrow(() -> new UserNotFoundException("L'utilisateur n'a pas été trouvé."));

        user.setUsername(username);
        user.setEmail(email);

        saveUser(user);
    }
}