package com.example.igiagante.thegarden.core.repository.network;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Ignacio Giagante, on 26/4/16.
 */

public class HttpStatus {

    private static final int OK_KEY = 200;

    private static final int BAD_AUTHENTICATION_KEY = 401;
    private static final int FORBIDDEN_KEY = 403;
    private static final int NOT_FOUND_KEY = 404;
    private static final int CONFLICT_KEY = 409;

    private static final int INTERNAL_SERVER_ERROR_KEY = 505;

    private static final String OK = "OK";

    private static final String BAD_AUTHENTICATION = "BAD_AUTHENTICATION";
    private static final String FORBIDDEN = "FORBIDDEN";
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String CONFLICT = "CONFLICT";

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    private final Map<Integer, String> httpCodeMap = new HashMap<>();
    private final Map<String, String> messages = new HashMap<>();

    private static final String INVALID_USER_KEY = "INVALID_USER";
    private static final String USER_NOT_FOUND_KEY = "USER_NOT_FOUND";
    private static final String WRONG_PASSWORD_KEY = "WRONG_PASSWORD";

    private static final String USER_CREATED_KEY = "The user was created successfully!";
    private static final String INVALID_USER_MESSAGE_KEY = "The username already exists. Please try other!";
    private static final String USER_NOT_FOUND_MESSAGE_KEY = "The username or password is not valid!";
    private static final String WRONG_PASSWORD_MESSAGE_KEY = "The password is wrong!";

    public HttpStatus() {
        httpCodeMap.put(OK_KEY, OK);
        httpCodeMap.put(BAD_AUTHENTICATION_KEY, BAD_AUTHENTICATION);
        httpCodeMap.put(FORBIDDEN_KEY, FORBIDDEN);
        httpCodeMap.put(NOT_FOUND_KEY, NOT_FOUND);
        httpCodeMap.put(CONFLICT_KEY, CONFLICT);
        httpCodeMap.put(INTERNAL_SERVER_ERROR_KEY, INTERNAL_SERVER_ERROR);

        messages.put(INVALID_USER_KEY, INVALID_USER_MESSAGE_KEY);
        messages.put(USER_NOT_FOUND_KEY, USER_NOT_FOUND_MESSAGE_KEY);
        messages.put(WRONG_PASSWORD_KEY, WRONG_PASSWORD_MESSAGE_KEY);
    }

    public String getHttpStatusValue(int code) {
        return httpCodeMap.get(code);
    }

    public String getMessage(String key){
        return messages.get(key);
    }
}
