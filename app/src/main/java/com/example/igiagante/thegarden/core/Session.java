package com.example.igiagante.thegarden.core;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

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
        setTokenExpirationDate();
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;

    }

    /**
     * Extract expiration date from token
     */
    private void setTokenExpirationDate(){

        if(!TextUtils.isEmpty(token)){
            String[] jwtParts = token.split("\\.");
            byte[] data = Base64.decode(jwtParts[1], Base64.DEFAULT);

            try {

                String text = new String(data, "UTF-8");
                Claims claims = new Gson().fromJson(text, Claims.class);
                this.tokenExpiration = new Date(Integer.parseInt(claims.getExp()));

            } catch (UnsupportedEncodingException e){
                Log.d("Exception", "Something was wrong trying to parse the token to extract expiration date");
            }
        }
    }

    /**
     * Used to parse string data token into object Claims
     */
    private class Claims {
        private String sub;
        private String iat;
        private String exp;

        public String getSub() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub = sub;
        }

        public String getIat() {
            return iat;
        }

        public void setIat(String iat) {
            this.iat = iat;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
    }
}
