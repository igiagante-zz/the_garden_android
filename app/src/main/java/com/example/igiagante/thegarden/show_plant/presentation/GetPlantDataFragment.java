package com.example.igiagante.thegarden.show_plant.presentation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.show_plant.di.PlantDataComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 13/6/16.
 */
public class GetPlantDataFragment extends BaseFragment implements GetPlantDataView{

    @Inject
    GetPlantDataPresenter getPlantDataPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(PlantDataComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.fragment_plant_list, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getPlantDataPresenter.setView(this);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.getPlantDataPresenter.destroy();
    }

    @Override
    public void loadPlantData(Plant plant) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
