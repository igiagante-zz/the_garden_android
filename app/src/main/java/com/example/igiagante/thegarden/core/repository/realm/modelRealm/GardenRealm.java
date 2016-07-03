package com.example.igiagante.thegarden.core.repository.realm.modelRealm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Ignacio Giagante, on 3/7/16.
 */
public class GardenRealm extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;

    private Date startDate;

    private Date endDate;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
