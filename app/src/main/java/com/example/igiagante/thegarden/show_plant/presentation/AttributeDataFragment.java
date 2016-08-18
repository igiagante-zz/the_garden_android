package com.example.igiagante.thegarden.show_plant.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.usecase.GetAttributesUseCase;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.show_plant.ShowPlantComponent;
import com.example.igiagante.thegarden.show_plant.presenters.GetAttributesPresenter;
import com.example.igiagante.thegarden.show_plant.view.ShowPlantView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 14/6/16.
 */
public class AttributeDataFragment extends BaseFragment implements ShowPlantView {

    @Inject
    GetAttributesPresenter getAttributesPresenter;

    private HorizontalBarChart horizontalBarChart;

    private List<Attribute> attributes;

    public AttributeDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(ShowPlantComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.attribute_data_fragment, container, false);

        horizontalBarChart = (HorizontalBarChart) fragmentView.findViewById(R.id.attributes_chart);

        ((BarChart) horizontalBarChart).setDescription("");

        this.getAttributesPresenter.getAttributes();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getAttributesPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void loadAttributes(List<Attribute> attributes) {
        this.attributes = attributes;

        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawZeroLine(true);

        YAxis rightAxis = horizontalBarChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(6, false);
        rightAxis.setAxisMinValue(0f);

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setTextSize(15f);

        horizontalBarChart.animateY(2500);
        horizontalBarChart.getLegend().setEnabled(false);

        setData();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    private void setData() {

        List<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xAxis = new ArrayList<>();

        ArrayList<Attribute> attrs = new ArrayList<>();

        for (Attribute attribute : attributes) {
            if (attribute.getType().equals("effects")) {
                attrs.add(attribute);
            }
        }

        int [] values = createListNumber();

        for (int i = 0; i < attrs.size(); i++) {
            entries.add(new BarEntry((float) values[i], i));
            xAxis.add(attrs.get(i).getName());
        }

        BarDataSet barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setBarSpacePercent(15f);
        barDataSet.setDrawValues(false);
        BarData data = new BarData(xAxis, barDataSet);
        data.setValueTextSize(14f);
        horizontalBarChart.setData(data);
    }

    private int [] createListNumber() {
        int [] numbers = new int[5];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = getRandomNumber();
        }
        Arrays.sort(numbers);

        return numbers;
    }

    private int getRandomNumber() {
        Random r = new Random();
        int low = 10;
        int high = 100;
        return r.nextInt(high - low) + low;
    }
}
