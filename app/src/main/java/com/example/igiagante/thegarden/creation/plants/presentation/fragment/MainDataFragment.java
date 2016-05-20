package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.core.ui.CountView;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.satsuware.usefulviews.LabelledSpinner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment used for the main data plant.
 *
 * @author Ignacio Giagante, on 6/5/16.
 */
public class MainDataFragment extends CreationBaseFragment implements LabelledSpinner.OnItemChosenListener {

    public static final String STATE = CreatePlantActivity.CREATE_MAIN_DATA;
    public static final String TITLE = "Title";

    @Bind(R.id.name_of_plant_id)
    TextView mNameOfPlant;

    @Bind(R.id.ph_soil_id)
    CountView mPhSoil;

    @Bind(R.id.ec_soil_id)
    CountView mEcSoil;

    @Bind(R.id.genotype_id)
    TextView mGenotype;

    @Bind(R.id.size_id)
    CountView mSize;

    private String mFloweringTime;

    // newInstance constructor for creating fragment with arguments
    public static MainDataFragment newInstance(String state, String title) {
        MainDataFragment fragmentFirst = new MainDataFragment();
        Bundle args = new Bundle();
        args.putString(STATE, state);
        args.putString(TITLE, title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_create_plant, container, false);

        ButterKnife.bind(this, fragmentView);

        LabelledSpinner spinner = (LabelledSpinner) fragmentView.findViewById(R.id.spinner_flowering_time_id);
        spinner.setItemsArray(R.array.flowering_time_array);
        spinner.setOnItemChosenListener(this);

        return fragmentView;
    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        if(labelledSpinner.getId() == R.id.spinner_flowering_time_id) {
            mFloweringTime = (String) adapterView.getAdapter().getItem(position);
        }
    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }

    @Override
    protected void setLastState() {
        ((CreatePlantActivity)getActivity()).move(STATE);
    }
}
