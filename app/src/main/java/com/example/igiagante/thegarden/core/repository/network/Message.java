package com.example.igiagante.thegarden.core.repository.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author Ignacio Giagante, on 27/6/16.
 */
public class Message {

    @SerializedName("name")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
