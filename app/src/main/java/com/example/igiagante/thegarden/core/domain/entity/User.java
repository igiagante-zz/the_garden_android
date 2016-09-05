package com.example.igiagante.thegarden.core.domain.entity;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class User {

    private String id;

    /**
     * User Email
     */
    private String userName;
    private String password;

    private ArrayList<Garden> gardens = new ArrayList<>();

    private ArrayList<Nutrient> nutrients = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public ArrayList<Garden> getGardens() {
        return gardens;
    }

    public void setGardens(ArrayList<Garden> gardens) {
        this.gardens = gardens;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }
}
