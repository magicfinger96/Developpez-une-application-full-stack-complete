package com.openclassrooms.mddapi.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}