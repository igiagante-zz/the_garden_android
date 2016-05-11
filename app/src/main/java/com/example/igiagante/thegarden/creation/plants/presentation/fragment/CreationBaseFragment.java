package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.presentation.PlantBuilder;

/**
 * @author Ignacio Giagante, on 11/5/16.
 */
public class CreationBaseFragment extends BaseFragment {

    protected PlantBuilder builder;

    public void moveToPreviousStep() {}

    public void moveToNextStep() {}

    public PlantBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(PlantBuilder builder) {
        this.builder = builder;
    }
}
