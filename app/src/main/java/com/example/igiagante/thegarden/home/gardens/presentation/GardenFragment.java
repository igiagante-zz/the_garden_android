package com.example.igiagante.thegarden.home.gardens.presentation;

import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;

/**
 * @author Ignacio Giagante, on 23/8/16.
 */
public class GardenFragment extends BaseFragment {

    protected Garden garden;

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public void createOneGardenFirst() {
    }

    public void filterList(String query) {
    }
}
