package com.openclassrooms.mddapi.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles the validation exceptions.
 */
@ControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Returns a readable error response when the validation of input failed.
     *
     * @param exception exception generated from the call.
     * @return an error ResponseEntity.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
