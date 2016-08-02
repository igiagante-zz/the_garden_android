package com.example.igiagante.thegarden.core;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.inject.Singleton;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */

@Singleton
public class Session {

    public Session() {}

    private String userName;
    private String token;
    private Date tokenExpiration;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        test();
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;

    }

    private void test(){
        String[] jwtParts = token.split("\\.");

        String jwtHeader = new Gson().toJson(
                new ByteArrayInputStream(Base64.decode(jwtParts[0], 0)));

        String jwtPayload = new Gson().toJson(
                new ByteArrayInputStream(Base64.decode(jwtParts[1], 0)));

        byte[] data = Base64.decode(jwtParts[1], Base64.DEFAULT);
        try {
            String text = new String(data, "UTF-8");
            Log.d("TEST", text);
        } catch (UnsupportedEncodingException e){
        }

        Log.d("TEST", jwtPayload);
    }
}
