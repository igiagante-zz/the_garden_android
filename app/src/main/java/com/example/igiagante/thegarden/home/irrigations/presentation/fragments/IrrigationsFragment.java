package com.example.igiagante.thegarden.home.irrigations.presentation.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.home.gardens.presentation.GardenFragment;
import com.example.igiagante.thegarden.home.irrigations.IrrigationDetailActivity;
import com.example.igiagante.thegarden.home.irrigations.presentation.adapters.IrrigationsAdapter;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationPresenter;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationView;
import com.example.igiagante.thegarden.widgets.WidgetProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 5/5/16.
 */
public class IrrigationsFragment extends GardenFragment implements IrrigationView,
        IrrigationsAdapter.OnIrrigationSelected,
        IrrigationsAdapter.OnDeleteIrrigation {

    private static final String IRRIGATIONS_KEY = "IRRIGATIONS";

    public static final int REQUEST_CODE_IRRIGATION_DETAIL = 334;

    @Inject
    IrrigationPresenter irrigationPresenter;

    private IrrigationsAdapter irrigationsAdapter;

    @Bind(R.id.irrigation_list_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.irrigations_recycle_view_id)
    RecyclerView recyclerViewIrrigations;

    @Bind(R.id.create_one_garden_first_irrigations)
    TextView createOneGarden;

    private ArrayList<Irrigation> mIrrigations = new ArrayList<>();

    private String irrigationId;

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
        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(MainComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.irrigations_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        irrigationsAdapter = new IrrigationsAdapter(getContext(), this, this);
        this.recyclerViewIrrigations.setLayoutManager(new LinearLayoutManager(context()));
        this.recyclerViewIrrigations.setAdapter(irrigationsAdapter);

        this.mProgressBar.setVisibility(View.VISIBLE);

        if (savedInstanceState != null) {
            mIrrigations = savedInstanceState.getParcelableArrayList(IRRIGATIONS_KEY);
            irrigationsAdapter.setIrrigations(mIrrigations);
            mProgressBar.setVisibility(View.GONE);
        } else {

            Bundle args = getArguments();

            if (args != null) {
                garden = args.getParcelable(MainActivity.GARDEN_KEY);
                if (garden != null) {
                    mIrrigations = (ArrayList<Irrigation>) garden.getIrrigations();
                    irrigationsAdapter.setIrrigations(mIrrigations);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(IRRIGATIONS_KEY, mIrrigations);
    }

    @Override
    public void setGarden(Garden garden) {
        this.garden = garden;
        this.mIrrigations = (ArrayList<Irrigation>) garden.getIrrigations();
        this.irrigationsAdapter.setIrrigations(this.mIrrigations);
        this.mProgressBar.setVisibility(View.GONE);
        this.createOneGarden.setVisibility(View.INVISIBLE);
    }

    @Override
    public void createOneGardenFirst() {
        this.createOneGarden.setVisibility(View.VISIBLE);
        this.mProgressBar.setVisibility(View.GONE);
        this.garden = null;
    }

    @Override
    public void notifyIfGardenWasUpdated(Garden garden) {
        this.garden = garden;
    }

    private void startIrrigationDetailActivity(@Nullable Irrigation irrigation) {
        Intent intent = new Intent(getContext(), IrrigationDetailActivity.class);
        intent.putExtra(MainActivity.GARDEN_KEY, garden);
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
        this.irrigationId = irrigationId;
        this.irrigationPresenter.deleteIrrigation(irrigationId);
    }

    @Override
    public void notifyIfIrrigationWasDeleted() {
        this.irrigationsAdapter.removeIrrigation();
        List<Irrigation> irrigations = this.garden.getIrrigations();
        Irrigation irrigation = getIrrigationToBeRemoved(irrigations);
        irrigations.remove(irrigation);
        updateWidgetWithLastIrrigation();
    }

    private Irrigation getIrrigationToBeRemoved(List<Irrigation> irrigations) {
        for (Irrigation irrigation : irrigations) {
            if (irrigation.getId().equals(this.irrigationId)) {
                return irrigation;
            }
        }
        return new Irrigation();
    }

    /**
     * Update widget after one irrigation was deleted.
     */
    private void updateWidgetWithLastIrrigation() {
        Intent intent = new Intent(getContext(), WidgetProvider.class);
        intent.setAction(WidgetProvider.IRRIGATION_WIDGET_UPDATE);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void showIrrigationDetails(Irrigation irrigation) {
        startIrrigationDetailActivity(irrigation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IRRIGATION_DETAIL && resultCode == Activity.RESULT_OK) {
            Garden garden = data.getParcelableExtra(MainActivity.GARDEN_KEY);
            Irrigation irrigation = data.getParcelableExtra(IrrigationDetailFragment.IRRIGATION_KEY);
            if (garden != null) {
                this.irrigationPresenter.updateGarden(garden);
                this.irrigationsAdapter.addIrrigation(irrigation);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.irrigationPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.irrigationPresenter.destroy();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
