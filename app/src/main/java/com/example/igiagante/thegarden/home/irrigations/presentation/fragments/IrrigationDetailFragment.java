package com.example.igiagante.thegarden.home.irrigations.presentation.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Irrigation;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.core.ui.CountView;
import com.example.igiagante.thegarden.core.ui.CountViewDecimal;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.home.irrigations.di.IrrigationComponent;
import com.example.igiagante.thegarden.home.irrigations.presentation.adapters.ExpandableListAdapter;
import com.example.igiagante.thegarden.home.irrigations.presentation.holders.NutrientHolder;
import com.example.igiagante.thegarden.home.irrigations.presentation.presenters.IrrigationDetailPresenter;
import com.example.igiagante.thegarden.home.irrigations.presentation.view.IrrigationDetailView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 21/7/16.
 */
public class IrrigationDetailFragment extends BaseFragment implements IrrigationDetailView, View.OnClickListener {

    public static final String IRRIGATION_DETAIL_KEY = "IRRIGATION_DETAIL";

    private Irrigation mIrrigation;
    private ExpandableListAdapter expandableListAdapter;
    private DatePickerDialog mIrrigationDatePickerDialog;

    @Inject
    IrrigationDetailPresenter irrigationDetailPresenter;

    @Bind(R.id.irrigation_expandable_list_id)
    ExpandableListView mExpandableListView;

    @Bind(R.id.irrigation_date_id)
    EditText mIrrigationDate;

    /**
     * How much dose should be irrigate in each plant
     */
    @Bind(R.id.irrigation_quantity_id)
    CountView quantity;

    /**
     * How much ph should be used in the dose
     */
    @Bind(R.id.irrigation_ph_dose_id)
    CountViewDecimal phDose;

    /**
     * How much water should be used in the dose
     */
    @Bind(R.id.irrigation_water_id)
    CountView water;

    /**
     * Level of ph after reading
     */
    @Bind(R.id.irrigation_ph_id)
    CountViewDecimal ph;

    /**
     * Level of ec after reading
     */
    @Bind(R.id.irrigation_ec_id)
    CountViewDecimal ec;

    @Bind(R.id.irrigation_save_button)
    Button mSaveButton;

    @Bind(R.id.irrigation_cancel_button)
    Button mCancelButton;

    private String mGardenId;

    private Garden garden;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("d MMM", Locale.US);

    private List<NutrientHolder> mNutrients;

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

        expandableListAdapter = new ExpandableListAdapter(getContext());
        mExpandableListView.setAdapter(expandableListAdapter);

        if (savedInstanceState != null) {
            mIrrigation = savedInstanceState.getParcelable(IRRIGATION_DETAIL_KEY);
        }

        Bundle arguments = getArguments();
        if (arguments != null) {
            mIrrigation = arguments.getParcelable(IRRIGATION_DETAIL_KEY);
        }

        Intent intent = getActivity().getIntent();
        mIrrigation = intent.getParcelableExtra(IRRIGATION_DETAIL_KEY);

        if (mIrrigation != null) {
            loadData(mIrrigation);
        } else {
            //Load nutrients
            irrigationDetailPresenter.getNutrients();
        }

        setDateTimeField();

        mSaveButton.setOnClickListener(v -> {
            Irrigation irrigation = saveIrrigation();
            this.irrigationDetailPresenter.saveIrrigation(irrigation);
        });

        mCancelButton.setOnClickListener(v -> {
            getActivity().setResult(getActivity().RESULT_CANCELED);
            getActivity().finish();
        });

        return fragmentView;
    }

    @Override
    public void loadNutrients(List<NutrientHolder> nutrients) {
        this.mNutrients = new ArrayList<>(nutrients);
        this.expandableListAdapter.setNutrients(nutrients);
    }

    @Override
    public void notifyIfIrrigationWasPersistedOrUpdated(Irrigation irrigation) {
        this.mIrrigation = irrigation;
        this.garden.getIrrigations().add(irrigation);
        this.irrigationDetailPresenter.updateGarden(this.garden);
    }

    @Override
    public void notifyIfGardenWasUpdated(Garden garden) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.GARDEN_KEY, garden);
        getActivity().setResult(getActivity().RESULT_OK, intent);
        getActivity().finish();
    }

    private void loadData(Irrigation irrigation) {

        mIrrigationDate.setText(dateFormatter.format(irrigation.getIrrigationDate()));
        quantity.setEditValue((int) irrigation.getQuantity());

        phDose.setEditValue(irrigation.getDose().getPhDose());
        water.setEditValue((int) irrigation.getDose().getWater());
        ph.setEditValue(irrigation.getDose().getPh());
        ec.setEditValue(irrigation.getDose().getEc());

        phDose.setEnabled(false);
        water.setEnabled(false);
        ph.setEnabled(false);
        ec.setEnabled(false);

        ArrayList<NutrientHolder> nutrientHolders = irrigationDetailPresenter
                .createNutrientHolderList(irrigation.getDose().getNutrients());

        this.mNutrients = new ArrayList<>(nutrientHolders);
        setNutrientsSelected();
    }

    private void setNutrientsSelected(){
        for(NutrientHolder nutrientHolder : mNutrients){
            nutrientHolder.setSelected(true);
        }
        this.expandableListAdapter.setNutrients(mNutrients);
    }

    /**
     * Build irrigation object
     *
     * @return irrigation
     */
    private Irrigation saveIrrigation() {

        Irrigation irrigation = new Irrigation();
        irrigation.setQuantity(quantity.getEditValue());
        irrigation.setIrrigationDate(new Date());
        irrigation.setGardenId(this.garden.getId());

        Dose dose = new Dose();
        dose.setWater(water.getEditValue());
        dose.setPh(ph.getEditValue());
        dose.setEc(ec.getEditValue());
        dose.setPhDose(phDose.getEditValue());

        dose.setNutrients(expandableListAdapter.getNutrientsSelected());

        irrigation.setDose(dose);

        return irrigation;
    }

    private void setDateTimeField() {

        mIrrigationDate.setOnClickListener(this);

        mIrrigationDate.setText(dateFormatter.format(new Date()));

        Calendar newCalendar = Calendar.getInstance();
        mIrrigationDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mIrrigationDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Set garden id which will be added to the irrigation
     *
     * @param gardenId
     */
    public void setGardenId(String gardenId) {
        this.mGardenId = gardenId;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void onClick(View v) {
        if (v == mIrrigationDate) {
            mIrrigationDatePickerDialog.show();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.irrigationDetailPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.irrigationDetailPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
