package com.example.igiagante.thegarden.home.charts.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 24/8/16.
 */
public class ChartsAdapter extends RecyclerView.Adapter<ChartsAdapter.ChartViewHolder> {

    private final LayoutInflater layoutInflater;

    /**
     * A list with holders which contain the model and extra data
     */
    private ArrayList<LineChart> charts = new ArrayList<>();

    private ArrayList<SensorTemp> data;

    public ChartsAdapter(Context context, ArrayList<SensorTemp> data) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        charts.add(new LineChart(context));
        charts.add(new LineChart(context));
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 1 : 0;
    }

    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.chart_view, parent, false);

        if (viewType == 1) {
            return new ChartViewHolder(view, false);
        } else {
            return new ChartViewHolder(view, true);
        }
    }

    @Override
    public void onBindViewHolder(ChartViewHolder holder, int position) {
        holder.setChart(charts.get(position));
    }

    @Override
    public int getItemCount() {
        return charts != null ? charts.size() : 0;
    }

    /**
     * Inner class to hold a reference to each plague of RecyclerView
     */
    public class ChartViewHolder extends RecyclerView.ViewHolder {

        LineChart chart;
        boolean forHumidity;

        public ChartViewHolder(View view, boolean forHumidity) {
            super(view);
            this.forHumidity = forHumidity;
            chart = (LineChart) view.findViewById(R.id.chart_id);
            new LineChartBuilder(chart, data, forHumidity).build();
        }

        public void setChart(LineChart chart) {
            this.chart = chart;
        }
    }
}
