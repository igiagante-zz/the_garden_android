package com.example.igiagante.thegarden.home.plants.presentation.viewTypes;

import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.IViewType;
import com.example.igiagante.thegarden.core.presentation.adapter.viewTypes.ViewTypeConstans;

import java.util.Date;

/**
 * @author Ignacio Giagante, on 5/7/16.
 */
public class ViewTypeGarden implements IViewType {

    private String id;
    private String name;
    private Date startDate;

    @Override
    public int getViewType() {
        return ViewTypeConstans.VIEW_TYPE_GARDEN;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
