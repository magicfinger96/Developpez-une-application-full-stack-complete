package com.openclassrooms.mddapi.exception;

public class AlreadyUsedEmailException extends Exception {
    public AlreadyUsedEmailException(String errorMessage) {
        super(errorMessage);
    }
}
