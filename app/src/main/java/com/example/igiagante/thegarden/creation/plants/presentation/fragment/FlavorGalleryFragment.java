package com.example.igiagante.thegarden.creation.plants.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.presentation.mvp.IView;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.FlavorAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.presenter.FlavorGalleryPresenter;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class FlavorGalleryFragment extends CreationBaseFragment implements IView {

    @Inject
    FlavorGalleryPresenter mFlavorGalleryPresenter;

    @Bind(R.id.recycler_view_flavors)
    RecyclerView mFlavors;

    private FlavorAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(CreatePlantComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.flavors_fragment, container, false);
        ButterKnife.bind(this, containerView);

        mFlavors.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mFlavors.setLayoutManager(manager);

        mAdapter = new FlavorAdapter(getContext());
        mFlavors.setAdapter(mAdapter);

        // Get Flavor List
        mFlavorGalleryPresenter.getFlavors();

        return containerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mFlavorGalleryPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mFlavorGalleryPresenter.destroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mFlavors.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    public void loadFlavors(List<Flavor> flavors) {
        mAdapter.setFlavors(flavors);
    }
}
