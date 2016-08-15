package com.example.igiagante.thegarden.home.irrigations.presentation.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientDetailActivity;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.home.irrigations.IrrigationDetailActivity;
import com.example.igiagante.thegarden.home.irrigations.presentation.adapters.IrrigationsAdapter;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationPresenter;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationView;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 5/5/16.
 */
public class IrrigationsFragment extends BaseFragment implements IrrigationView,
        IrrigationsAdapter.OnIrrigationSelected,
        IrrigationsAdapter.OnDeleteIrrigation {

    public static final int REQUEST_CODE_IRRIGATION_DETAIL = 334;

    public static final String GARDEN_ID_KEY = "GARDEN_ID";

    @Inject
    IrrigationPresenter irrigationPresenter;

    private IrrigationsAdapter irrigationsAdapter;

    @Bind(R.id.irrigation_list_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.irrigations_recycle_view_id)
    RecyclerView recyclerViewIrrigations;

    @Bind(R.id.irrigations_add_new_irrigation_id)
    FloatingActionButton buttonAddNutrient;

    private GardenHolder mGarden;

    private ArrayList<Irrigation> mIrrigations = new ArrayList<>();

    public static IrrigationsFragment newInstance(Garden garden) {
        IrrigationsFragment myFragment = new IrrigationsFragment();

        Bundle args = new Bundle();
        args.putParcelable(MainActivity.GARDEN_KEY, garden);
        myFragment.setArguments(args);

        return myFragment;
    }

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
        this.getComponent(MainComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.irrigations_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        irrigationsAdapter = new IrrigationsAdapter(getContext(), this, this);
        this.recyclerViewIrrigations.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewIrrigations.setAdapter(irrigationsAdapter);

        Bundle args = getArguments();
        if(args != null) {
            mGarden = args.getParcelable(MainActivity.GARDEN_KEY);
            if(mGarden != null) {
                mIrrigations = (ArrayList<Irrigation>) mGarden.getModel().getIrrigations();
                irrigationsAdapter.setIrrigations(mIrrigations);
                mProgressBar.setVisibility(View.GONE);
            }
        }

        buttonAddNutrient.setOnClickListener(v -> startIrrigationDetailActivity(null));

        return fragmentView;
    }

    private void startIrrigationDetailActivity(Irrigation irrigation) {
        Intent intent = new Intent(getContext(), IrrigationDetailActivity.class);
        intent.putExtra(GARDEN_ID_KEY, mGarden.getGardenId());
        intent.putExtra(IrrigationDetailFragment.IRRIGATION_DETAIL_KEY, irrigation);
        IrrigationsFragment.this.startActivityForResult(intent, REQUEST_CODE_IRRIGATION_DETAIL);
    }

    @Override
    public void showDeleteIrrigationDialog(int position) {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.delete_irrigation_dialog_title)
                .setMessage(R.string.delete_irrigation_dialog_content)
                .setPositiveButton("Yes", (dialog, which) -> irrigationsAdapter.deleteIrrigation(position))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void deleteIrrigation(String irrigationId) {
        this.irrigationPresenter.deleteIrrigation(irrigationId);
    }

    @Override
    public void notifyIfIrrigationWasDeleted() {
        this.irrigationsAdapter.removeIrrigation();
    }

    @Override
    public void showIrrigationDetails(Irrigation irrigation) {
        startIrrigationDetailActivity(irrigation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_IRRIGATION_DETAIL && resultCode == Activity.RESULT_OK){
            Irrigation irrigation = data.getParcelableExtra(IrrigationDetailFragment.IRRIGATION_DETAIL_KEY);
            if(irrigation != null){
                this.irrigationsAdapter.addIrrigation(irrigation);
            }
        }
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

    public void setGarden(GardenHolder mGarden) {
        this.mGarden = mGarden;
        ArrayList<Irrigation> irrigations = (ArrayList<Irrigation>) this.mGarden.getModel().getIrrigations();

        if(irrigationsAdapter != null) {
            this.irrigationsAdapter.setIrrigations(irrigations);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
