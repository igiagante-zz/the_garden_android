package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.PlagueHolder;

import java.util.Collection;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
public interface PlagueView extends IView {

    /**
     * Load available plagues
     * @param plagues {@link PlagueHolder}
     */
    void loadPlagues(Collection<PlagueHolder> plagues);
}
