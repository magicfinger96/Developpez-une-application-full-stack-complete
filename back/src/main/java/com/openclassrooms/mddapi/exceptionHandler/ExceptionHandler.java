package com.openclassrooms.mddapi.exceptionHandler;

import com.openclassrooms.mddapi.model.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Handles generic exceptions.
 */
@ControllerAdvice
public class ExceptionHandler {

    /**
     * Returns a bad request response when the validation of input failed.
     *
     * @param exception exception generated from the call.
     * @return an error ResponseEntity.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(new MessageResponse(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns a bad request response when the type in the api url mismatch.
     *
     * @param exception exception generated from the call.
     * @return an error ResponseEntity.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns a bad request response when the request body can't be read.
     *
     * @param exception exception generated from the call.
     * @return an error ResponseEntity.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleValidationExceptions(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
