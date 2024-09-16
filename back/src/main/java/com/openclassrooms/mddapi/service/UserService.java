package com.openclassrooms.mddapi.service;


import java.util.Optional;

import com.openclassrooms.mddapi.model.dto.UserDto;
import com.openclassrooms.mddapi.model.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}