package com.example.igiagante.thegarden.creation.nutrients.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientView;
import com.example.igiagante.thegarden.creation.nutrients.presentation.adapters.NutrientsAdapter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientListFragment extends BaseFragment implements NutrientView {

    @Inject
    NutrientPresenter nutrientPresenter;

    @Inject
    NutrientsAdapter nutrientsAdapter;

    @Bind(R.id.nutrients_recycle_view_id)
    RecyclerView recyclerViewNutrients;

    @Bind(R.id.nutrients_add_new_nutrient_id)
    Button buttonAddNutrient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(NutrientsComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.plant_list_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        this.recyclerViewNutrients.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewNutrients.setAdapter(nutrientsAdapter);

        //load nutrient list
        this.nutrientPresenter.loadNutrients();

        return fragmentView;
    }

    @Override
    public void loadNutrients(List<Nutrient> nutrients) {
        this.nutrientsAdapter.setPlants(nutrients);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.nutrientPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        recyclerViewNutrients.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.nutrientPresenter.destroy();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
