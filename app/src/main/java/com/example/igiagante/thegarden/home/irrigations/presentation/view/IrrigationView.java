package com.example.igiagante.thegarden.home.irrigations.presentation.view;

import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 20/7/16.
 */
public interface IrrigationView extends IView {

    void loadIrrigations(List<Irrigation> irrigations);

    void notifyIfIrrigationWasDeleted();
}
