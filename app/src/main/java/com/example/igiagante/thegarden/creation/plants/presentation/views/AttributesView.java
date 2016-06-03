package com.example.igiagante.thegarden.creation.plants.presentation.views;

import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.AttributeHolder;

import java.util.Collection;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
public interface AttributesView extends IView {

    /**
     * Load available attributes
     * @param attributes {@link AttributeHolder}
     */
    void loadAttributes(Collection<AttributeHolder> attributes);
}
