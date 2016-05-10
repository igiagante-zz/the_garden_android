package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.satsuware.usefulviews.LabelledSpinner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 6/5/16.
 */
public class CreatePlantMainDataFragment extends BaseFragment implements LabelledSpinner.OnItemChosenListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CreatePlantComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_create_plant, container, false);


        LabelledSpinner yourSpinner = (LabelledSpinner) fragmentView.findViewById(R.id.spinner_flowering_time);
        yourSpinner.setItemsArray(R.array.flowering_time_array);

        return fragmentView;
    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {

    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }
}
