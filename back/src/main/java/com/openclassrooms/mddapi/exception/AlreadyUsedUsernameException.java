package com.openclassrooms.mddapi.exception;

public class AlreadyUsedUsernameException extends Exception {
    public AlreadyUsedUsernameException(String errorMessage) {
        super(errorMessage);
    }
}
