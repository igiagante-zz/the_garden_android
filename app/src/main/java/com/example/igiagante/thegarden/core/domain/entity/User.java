package com.example.igiagante.thegarden.core.domain.entity;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class User {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
