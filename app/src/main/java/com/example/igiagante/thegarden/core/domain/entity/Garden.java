package com.example.igiagante.thegarden.core.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by igiagante on 5/5/16.
 */
public class Garden {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    public Garden(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
