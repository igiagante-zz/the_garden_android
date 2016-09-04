package com.example.igiagante.thegarden.home.charts.view;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.presentation.mvp.LoadDataView;

import java.util.List;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public interface SensorTempView extends LoadDataView {


    void loadSensorTempData(List<SensorTemp> data);
}
