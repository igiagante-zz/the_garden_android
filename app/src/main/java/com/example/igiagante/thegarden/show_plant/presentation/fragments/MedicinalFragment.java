package com.example.igiagante.thegarden.show_plant.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.show_plant.presentation.GetPlantDataFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ignacio Giagante, on 18/8/16.
 */
public class MedicinalFragment extends BaseFragment {

    private HorizontalBarChart horizontalBarChart;

    private List<Attribute> attributes;

    public static MedicinalFragment newInstance(ArrayList<Attribute> attributes) {
        MedicinalFragment myFragment = new MedicinalFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(GetPlantDataFragment.ATTRIBUTES_KEY, attributes);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.medicinal_fragment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            attributes = args.getParcelableArrayList(GetPlantDataFragment.ATTRIBUTES_KEY);
        }

        horizontalBarChart = (HorizontalBarChart) fragmentView.findViewById(R.id.medicinal_chart);
        horizontalBarChart.setDescription("");

        loadAttributes();

        return fragmentView;
    }

    private void loadAttributes() {

        ArrayList<Attribute> attrs = new ArrayList<>();

        for (Attribute attribute : attributes) {
            if (attribute.getType().equals(getString(R.string.medicinal))) {
                attrs.add(attribute);
            }
        }
        new HorizontalBarBuilder(horizontalBarChart, attrs).build();
    }
}