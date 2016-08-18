package com.example.igiagante.thegarden.show_plant.presentation.fragments;

import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ignacio Giagante, on 18/8/16.
 */
public class HorizontalBarBuilder {

    private HorizontalBarChart horizontalBarChart;
    private ArrayList<Attribute> attributes;

    public HorizontalBarBuilder(HorizontalBarChart horizontalBarChart, ArrayList<Attribute> attributes) {
        this.horizontalBarChart = horizontalBarChart;
        this.attributes = attributes;
    }

    public HorizontalBarChart build() {
        createHorizontalBarChart();
        return this.horizontalBarChart;
    }

    private void createHorizontalBarChart() {

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

    private void setData() {

        List<BarEntry> entries = new ArrayList<>();
        ArrayList<String> xAxis = new ArrayList<>();

        int [] values = createListNumber();

        for (int i = 0; i < attributes.size(); i++) {
            entries.add(new BarEntry((float) values[i], i));
            xAxis.add(attributes.get(i).getName());
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
