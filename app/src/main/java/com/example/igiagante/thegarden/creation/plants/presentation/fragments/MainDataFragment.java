package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.ui.CountView;
import com.example.igiagante.thegarden.core.ui.CountViewDecimal;
import com.example.igiagante.thegarden.creation.plants.di.components.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.MainDataPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.MainDataView;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.satsuware.usefulviews.LabelledSpinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment used for the main data plant.
 *
 * @author Ignacio Giagante, on 6/5/16.
 */
public class MainDataFragment extends CreationBaseFragment implements LabelledSpinner.OnItemChosenListener,
        TextWatcher, MainDataView{

    public static final String PLANT_KEY = "PLANT";

    @Inject
    MainDataPresenter mainDataPresenter;

    @Bind(R.id.name_of_plant_id)
    TextView mNameOfPlant;

    @Bind(R.id.genotype_id)
    TextView mGenotype;

    @Bind(R.id.ph_soil_id)
    CountViewDecimal mPhSoil;

    @Bind(R.id.ec_soil_id)
    CountViewDecimal mEcSoil;

    @Bind(R.id.size_id)
    CountView mSize;

    private LabelledSpinner spinner;

    private String mFloweringTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getComponent(CreatePlantComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.create_plant_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        spinner = (LabelledSpinner) fragmentView.findViewById(R.id.spinner_flowering_time_id);
        spinner.setItemsArray(R.array.flowering_time_array);
        spinner.setOnItemChosenListener(this);

        // ask to the activity if it has a plant for edition
        if (mPlant != null) {
            setPlantValuesInView();
        }

        if (savedInstanceState != null) {
            mPlant = savedInstanceState.getParcelable(PLANT_KEY);
            setPlantValuesInView();
        }

        mNameOfPlant.addTextChangedListener(this);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainDataPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mainDataPresenter.destroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void informIfPlantExist(Boolean exist) {
        if(exist) {
            mNameOfPlant.setError(getString(R.string.name_of_the_plant_already_exist));
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(s.toString())) {
            mainDataPresenter.existPlant(s.toString().trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
        if (labelledSpinner.getId() == R.id.spinner_flowering_time_id) {
            mFloweringTime = (String) adapterView.getAdapter().getItem(position);
        }
    }

    @Override
    public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            mPlant = createParcelable();
            outState.putParcelable(PLANT_KEY, mPlant);
        }
    }

    @Override
    protected void move() {
        Plant.PlantBuilder builder = ((CreatePlantActivity) getActivity()).getPlantBuilder();
        builder.addPlantName(mNameOfPlant.getText().toString().trim());
        builder.addPhSoil(mPhSoil.getEditValue());
        builder.addEcSoil(mEcSoil.getEditValue());
        builder.addFloweringTime(mFloweringTime);
        builder.addGenotype(mGenotype.getText().toString().trim());
        builder.addSize(mSize.getEditValue());
    }

    @Override
    protected void loadPlantDataForEdition(PlantHolder plantHolder) {
        super.loadPlantDataForEdition(plantHolder);
    }

    private Plant createParcelable() {
        Plant plant = new Plant();
        plant.setName(mNameOfPlant.getText().toString().trim());
        plant.setPhSoil(mPhSoil.getEditValue());
        plant.setEcSoil(mEcSoil.getEditValue());
        plant.setFloweringTime(mFloweringTime);
        plant.setGenotype(mGenotype.getText().toString().trim());
        plant.setSize(mSize.getEditValue());
        return plant;
    }

    private void setPlantValuesInView() {

        mNameOfPlant.setText(mPlant.getName());
        mPhSoil.setEditValue(mPlant.getPhSoil());
        mEcSoil.setEditValue(mPlant.getEcSoil());

        String[] list = getActivity().getResources().getStringArray(R.array.flowering_time_array);
        ArrayList<String> newList = new ArrayList<>(Arrays.asList(list));

        if (spinner != null) {
            spinner.setSelection(newList.indexOf(mPlant.getFloweringTime()));
        }

        mGenotype.setText(mPlant.getGenotype());
        mSize.setEditValue(mPlant.getSize());
    }
}
