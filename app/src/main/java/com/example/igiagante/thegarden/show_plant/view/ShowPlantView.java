package com.example.igiagante.thegarden.show_plant.view;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 18/8/16.
 */
public interface ShowPlantView extends IView {

    void loadAttributes(List<Attribute> attributes);
}
