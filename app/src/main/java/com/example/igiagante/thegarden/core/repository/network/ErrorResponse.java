package com.example.igiagante.thegarden.core.repository.network;

/**
 * Created by igiagante on 22/4/16.
 */

public class ErrorResponse {

    Error error;

    public static class Error {
        Data data;

        public static class Data {
            String message;
        }
    }
}
