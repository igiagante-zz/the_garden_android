package com.example.igiagante.thegarden.core.repository.network;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by igiagante on 26/4/16.
 */


public class HttpStatus {

    private static final int NOT_FOUND_KEY = 404;
    private static final int INTERNAL_SERVER_ERROR_KEY = 505;

    private static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    private static final String NOT_FOUND = "NOT_FOUND";

    private final Map<Integer, String> httpCodeMap = new HashMap<>();

    @Inject
    public HttpStatus() {
        httpCodeMap.put(INTERNAL_SERVER_ERROR_KEY, INTERNAL_SERVER_ERROR);
        httpCodeMap.put(NOT_FOUND_KEY, NOT_FOUND);
    }

    public String getHttpStatusValue(int code) {
        return httpCodeMap.get(code);
    }
}
