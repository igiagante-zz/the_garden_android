package com.example.igiagante.thegarden.creation.nutrients.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.core.ui.CountViewDecimal;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientDetailPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.GalleryAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientDetailFragment extends BaseFragment {

    private static final String NUTRIENT_DETAIL_KEY = "NUTRIENT_DETAIL";

    @Inject
    NutrientDetailPresenter nutrientDetailPresenter;

    @Bind(R.id.name_of_nutrient_id)
    EditText name;

    @Bind(R.id.nutrient_ph_id)
    CountViewDecimal ph;

    @Bind(R.id.nitrogen_id)
    EditText mNitrogen;

    @Bind(R.id.phosphorus_id)
    EditText mPhosphorus;

    @Bind(R.id.potassium_id)
    EditText mPotassium;

    @Bind(R.id.nutrient_description_id)
    EditText description;

    private Nutrient mNutrient;

    public static NutrientDetailFragment newInstance(Nutrient nutrient) {
        NutrientDetailFragment myFragment = new NutrientDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(NUTRIENT_DETAIL_KEY, nutrient);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(NutrientsComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.nutrient_detail_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        if (savedInstanceState != null) {
            mNutrient = savedInstanceState.getParcelable(NUTRIENT_DETAIL_KEY);
        }

        Bundle arguments = getArguments();

        if (arguments != null) {
            mNutrient = arguments.getParcelable(NUTRIENT_DETAIL_KEY);

            if (mNutrient != null) {
                name.setText(mNutrient.getName());
                ph.setEditValue(mNutrient.getPh());

                String[] npk = mNutrient.getNpk().split("-");
                String nitrogen = npk[0];
                String phosphorus = npk[1];
                String potassium = npk[2];

                mNitrogen.setText(nitrogen);
                mPhosphorus.setText(phosphorus);
                mPotassium.setText(potassium);

                description.setText(mNutrient.getDescription());
            }
        }

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NUTRIENT_DETAIL_KEY, mNutrient);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.nutrientDetailPresenter.destroy();
    }

    public Nutrient getNutrient() {
        saveNutrientData();
        return mNutrient;
    }

    private void saveNutrientData() {
        this.mNutrient = new Nutrient();
        mNutrient.setName(name.getText().toString());
        mNutrient.setPh(ph.getEditValue());
        mNutrient.setNpk(getNPK());
        mNutrient.setDescription(description.getText().toString());
    }

    private String getNPK() {

        String nitrogen = mNitrogen.getText().toString();
        String phosphorus = mPhosphorus.getText().toString();
        String potassium = mPotassium.getText().toString();

        return nitrogen + "-" + phosphorus + "-" + potassium;
    }

}
