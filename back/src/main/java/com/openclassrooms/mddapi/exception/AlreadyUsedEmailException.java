package com.openclassrooms.mddapi.exception;

/**
 * Exception thrown when the email is already used.
 */
public class AlreadyUsedEmailException extends Exception {
    public AlreadyUsedEmailException(String errorMessage) {
        super(errorMessage);
    }
}
