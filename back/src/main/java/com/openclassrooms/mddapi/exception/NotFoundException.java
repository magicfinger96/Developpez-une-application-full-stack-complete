package com.openclassrooms.mddapi.exception;

/**
 * Exception thrown when something is not found.
 */
public class NotFoundException extends Exception {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
