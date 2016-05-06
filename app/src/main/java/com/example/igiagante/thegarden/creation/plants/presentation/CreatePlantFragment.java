package com.example.igiagante.thegarden.creation.plants.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;

import butterknife.ButterKnife;

/**
 * @author igiagante on 6/5/16.
 */
public class CreatePlantFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CreatePlantComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_create_plant, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }
}
