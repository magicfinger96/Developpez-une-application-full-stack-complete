package com.openclassrooms.mddapi.model.response;


import lombok.Data;

/**
 * An end point response containing a message.
 */
@Data
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}

