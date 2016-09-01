package com.example.igiagante.thegarden.home.charts.presentation;

import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Ignacio Giagante, on 19/8/16.
 */
public class LineChartBuilder {

    private LineChart lineChart;
    private ArrayList<SensorTemp> data;
    private boolean forHumidityChart;

    public LineChartBuilder(LineChart lineChart, ArrayList<SensorTemp> data, boolean forHumidityChart) {
        this.lineChart = lineChart;
        this.data = data;
        this.forHumidityChart = forHumidityChart;
    }

    public LineChart build() {
        createHorizontalBarChart();
        return this.lineChart;
    }

    private void createHorizontalBarChart() {

        lineChart.setGridBackgroundColor(209);
        lineChart.setBorderColor(255);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(true);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.setPinchZoom(false);
        lineChart.setDescription("");
        lineChart.setTouchEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getXAxis().setEnabled(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.invalidate();

        xAxis.setTextSize(10f);

        lineChart.animateY(1500);
        lineChart.getLegend().setEnabled(true);

        setData();
    }

    private void setData() {

        List<Entry> entries = new ArrayList<>();

        ArrayList<String> xAxis = new ArrayList<>();

        String format = "dd-MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        for (int i = 0; i < data.size(); i++) {
            if(forHumidityChart){
                entries.add(new Entry(data.get(i).getHumidity(), i));
            } else {
                entries.add(new Entry(data.get(i).getTemp(), i));
            }
            xAxis.add(sdf.format(data.get(i).getDate()));
        }

        // The api return last 16 days temp data, but the last element is the first. This is a workaround
        // to avoid inconsistent data shown in the chart.
        xAxis.add(0, xAxis.get(xAxis.size() - 1));
        xAxis.remove(xAxis.size() - 1);

        String legendTitle = "";

        if(forHumidityChart) {
            legendTitle = "Humidity";
        } else {
            legendTitle = "Temperature";
        }

        LineDataSet lineDataSet = new LineDataSet(entries, legendTitle);
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setFillColor(209);

        LineData data = new LineData(xAxis, lineDataSet);
        data.setValueTextSize(14f);
        lineChart.setData(data);
    }
}