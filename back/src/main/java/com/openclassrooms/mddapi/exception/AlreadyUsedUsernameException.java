package com.openclassrooms.mddapi.exception;

/**
 * Exception thrown when the username is already used.
 */
public class AlreadyUsedUsernameException extends Exception {
    public AlreadyUsedUsernameException(String errorMessage) {
        super(errorMessage);
    }
}
