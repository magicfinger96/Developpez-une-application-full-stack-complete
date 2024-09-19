package com.openclassrooms.mddapi.exception;

/**
 * Exception raised by a client error.
 */
public class BadRequestException extends Exception {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}