package com.example.igiagante.thegarden.core.presentation;

/**
 * @author Ignacio Giagante, on 9/4/16.
 */
public class ValidationMessage {

    private String message;
    private Boolean error;

    public ValidationMessage(String message, Boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getError() {
        return error;
    }
}
