package com.example.igiagante.thegarden.home.irrigations.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientDetailPresenter;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationComponent;
import com.example.igiagante.thegarden.home.irrigations.presentation.adapters.ExpandableListAdapter;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationDetailPresenter;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationDetailView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
public class IrrigationDetailFragment extends BaseFragment implements IrrigationDetailView {

    private static final String IRRIGATION_DETAIL_KEY = "NUTRIENT_DETAIL";

    private Irrigation mIrrigation;
    private ExpandableListAdapter expandableListAdapter;

    @Inject
    IrrigationDetailPresenter irrigationDetailPresenter;

    @Bind(R.id.irrigation_expandable_list_id)
    ExpandableListView mExpandableListView;

    public static IrrigationDetailFragment newInstance(Irrigation irrigation) {
        IrrigationDetailFragment myFragment = new IrrigationDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(IRRIGATION_DETAIL_KEY, irrigation);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(IrrigationComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.irrigation_detail_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        if (savedInstanceState != null) {
            mIrrigation = savedInstanceState.getParcelable(IRRIGATION_DETAIL_KEY);
        }

        Bundle arguments = getArguments();

        if (arguments != null) {
            mIrrigation = arguments.getParcelable(IRRIGATION_DETAIL_KEY);
        }

        expandableListAdapter = new ExpandableListAdapter(getContext());
        mExpandableListView.setAdapter(expandableListAdapter);

        //Load nutrients
        irrigationDetailPresenter.getNutrients();

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.irrigationDetailPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.irrigationDetailPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation) {

    }

    @Override
    public void loadNutrients(List<Nutrient> nutrients) {
        this.expandableListAdapter.setNutrients(nutrients);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
