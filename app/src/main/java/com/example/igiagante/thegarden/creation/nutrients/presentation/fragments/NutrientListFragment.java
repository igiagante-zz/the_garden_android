package com.example.igiagante.thegarden.creation.nutrients.presentation.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.adapters.NutrientsAdapter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientListFragment extends BaseFragment implements NutrientView, NutrientsAdapter.OnDeleteNutrient {

    private static final String NUTRIENTS_KEY = "NUTRIENTS";

    @Inject
    NutrientPresenter nutrientPresenter;

    @Inject
    Session mSession;

    private NutrientsAdapter nutrientsAdapter;

    @Bind(R.id.nutrients_recycle_view_id)
    RecyclerView recyclerViewNutrients;

    @Bind(R.id.nutrients_add_new_nutrient_id)
    FloatingActionButton buttonAddNutrient;

    @Bind(R.id.nutrient_list_progress_bar)
    ProgressBar mProgressBar;

    private OnAddNutrientListener mOnAddNutrientListener;

    private ArrayList<Nutrient> mNutrients;

    public interface OnAddNutrientListener {
        void startNutrientDetailActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(NutrientComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.nutrient_list_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        nutrientsAdapter = new NutrientsAdapter(getContext(), (NutrientsAdapter.OnNutrientSelected) getActivity(), this);

        this.recyclerViewNutrients.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewNutrients.setAdapter(nutrientsAdapter);

        //load nutrient list
        mProgressBar.setVisibility(View.VISIBLE);
        if (savedInstanceState != null) {
            mNutrients = savedInstanceState.getParcelableArrayList(NUTRIENTS_KEY);
            if (mNutrients != null) {
                this.nutrientsAdapter.setNutrients(mNutrients);
                mProgressBar.setVisibility(View.GONE);
            }
        } else {
            this.nutrientPresenter.loadNutrients(mSession.getUser().getId());
        }

        buttonAddNutrient.setOnClickListener(v -> {
            if (checkInternet()) {
                mOnAddNutrientListener.startNutrientDetailActivity();
            } else {
                showToastMessage(getString(R.string.there_is_not_internet_connection));
            }
        });

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(NUTRIENTS_KEY, mNutrients);
    }

    @Override
    public void loadNutrients(List<Nutrient> nutrients) {
        this.mNutrients = (ArrayList<Nutrient>) nutrients;
        this.nutrientsAdapter.setNutrients(mNutrients);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.nutrientPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerViewNutrients.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
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

    @Override
    public void showDeleteNutrientDialog(int position) {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.delete_nutrient_dialog_title)
                .setMessage(R.string.delete_nutrient_dialog_content)
                .setPositiveButton("Yes", (dialog, which) -> nutrientsAdapter.deleteNutrient(position))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void deleteNutrient(String nutrientId) {
        this.nutrientPresenter.deleteNutrient(nutrientId);
    }

    @Override
    public void notifyIfNutrientWasDeleted() {
        this.nutrientsAdapter.removeNutrient();
    }

    @Override
    public void notifyIfNutrientWasPersistedOrUpdated(Nutrient nutrient) {
        int position = nutrientsAdapter.existNutrient(nutrient.getId());
        if (position != -1) {
            this.nutrientsAdapter.updateNutrient(nutrient, position);
        } else {
            this.nutrientsAdapter.addNutrient(nutrient);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnAddNutrientListener = (OnAddNutrientListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnAddNutrientListener");
        }
    }

    /**
     * Add nutrient
     *
     * @param nutrient
     */
    public void addNutrient(Nutrient nutrient) {
        this.nutrientPresenter.saveNutrient(nutrient);
    }
}
