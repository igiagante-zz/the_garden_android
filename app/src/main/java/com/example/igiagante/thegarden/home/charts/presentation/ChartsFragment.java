package com.example.igiagante.thegarden.home.charts.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.home.charts.view.SensorTempView;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataFragment;
import com.github.mikephil.charting.charts.LineChart;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 5/5/16.
 */
public class ChartsFragment extends BaseFragment implements SensorTempView {

    public static final String SENSOR_DATA_KEY = "SENSOR_DATA";

    private LineChart tempChart;
    private LineChart humidityChart;

    private ArrayList<SensorTemp> data;

    private ChartsAdapter chartsAdapter;

    @Inject
    ChartsPresenter chartsPresenter;

    @Bind(R.id.progress_bar_charts)
    ProgressBar progressBar;

    @Bind(R.id.recycle_view_charts_id)
    RecyclerView recyclerViewCharts;

    public static ChartsFragment newInstance(ArrayList<Attribute> data) {
        ChartsFragment myFragment = new ChartsFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(SENSOR_DATA_KEY, data);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(MainComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.charts_fragment, container, false);

        ButterKnife.bind(this, fragmentView);
        this.chartsPresenter.setView(new WeakReference<>(this));

        Bundle args = getArguments();
        if (args != null) {
            data = args.getParcelableArrayList(SENSOR_DATA_KEY);
            new LineChartBuilder(tempChart, data, false).build();
            new LineChartBuilder(tempChart, data, true).build();
        }

        LinearLayoutManager selectedLayout = new LinearLayoutManager(getContext());
        recyclerViewCharts.setLayoutManager(selectedLayout);

        this.chartsPresenter.getSensorData();

        return fragmentView;
    }

    @Override
    public void loadSensorTempData(List<SensorTemp> data) {
        this.chartsAdapter = new ChartsAdapter(getContext(), (ArrayList<SensorTemp>) data);
        recyclerViewCharts.setAdapter(chartsAdapter);
    }

    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}