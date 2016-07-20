package com.example.igiagante.thegarden.home.irrigations.presentation.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientView;
import com.example.igiagante.thegarden.home.irrigations.IrrigationDetailActivity;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationsComponent;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationPresenter;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 5/5/16.
 */
public class IrrigationsFragment extends BaseFragment implements IrrigationView {

    @Inject
    IrrigationPresenter irrigationPresenter;

    @Bind(R.id.nutrients_add_new_nutrient_id)
    Button buttonAddNutrient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(IrrigationsComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.irrigations_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        buttonAddNutrient.setOnClickListener(v ->
                getActivity().startActivity(new Intent(getContext(), IrrigationDetailActivity.class)));

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.irrigationPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.irrigationPresenter.destroy();
    }

    @Override
    public void loadIrrigations(List<Irrigation> irrigations) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
