package com.example.igiagante.thegarden.creation.nutrients.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.core.ui.CountViewDecimal;
import com.example.igiagante.thegarden.creation.nutrients.di.NutrientsComponent;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientDetailPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.view.NutrientDetailView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
public class NutrientDetailFragment extends BaseFragment implements NutrientDetailView, TextWatcher {

    private static final String NUTRIENT_DETAIL_KEY = "NUTRIENT_DETAIL";

    @Bind(R.id.name_of_nutrient_id)
    EditText nameOfNutrient;

    @Bind(R.id.nutrient_ph_id)
    CountViewDecimal ph;

    @Bind(R.id.nitrogen_id)
    EditText mNitrogen;

    @Bind(R.id.phosphorus_id)
    EditText mPhosphorus;

    @Bind(R.id.potassium_id)
    EditText mPotassium;

    @Bind(R.id.nutrient_description_id)
    EditText description;

    private Nutrient mNutrient;

    @Inject
    NutrientDetailPresenter nutrientDetailPresenter;

    private OnButtonAvailable mOnButtonAvailable;

    public interface OnButtonAvailable {
        void activeButton(boolean active);
    }

    public static NutrientDetailFragment newInstance(Nutrient nutrient) {
        NutrientDetailFragment myFragment = new NutrientDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(NUTRIENT_DETAIL_KEY, nutrient);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(NutrientsComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.nutrient_detail_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        if (savedInstanceState != null) {
            mNutrient = savedInstanceState.getParcelable(NUTRIENT_DETAIL_KEY);
        }

        Bundle arguments = getArguments();

        if (arguments != null) {
            mNutrient = arguments.getParcelable(NUTRIENT_DETAIL_KEY);

            if (mNutrient != null) {
                nameOfNutrient.setText(mNutrient.getName());
                ph.setEditValue(mNutrient.getPh());

                String[] npk = mNutrient.getNpk().split("-");
                String nitrogen = npk[0];
                String phosphorus = npk[1];
                String potassium = npk[2];

                mNitrogen.setText(nitrogen);
                mPhosphorus.setText(phosphorus);
                mPotassium.setText(potassium);

                description.setText(mNutrient.getDescription());
            }
        }

        nameOfNutrient.addTextChangedListener(this);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NUTRIENT_DETAIL_KEY, mNutrient);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.nutrientDetailPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.nutrientDetailPresenter.destroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!TextUtils.isEmpty(s.toString())) {
            nutrientDetailPresenter.existNutrient(s.toString().trim());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void notifyIfNutrientExist(boolean exist) {
        if(exist) {
            nameOfNutrient.setError(getString(R.string.name_of_the_nutrient_already_exist));
            mOnButtonAvailable.activeButton(false);
        } else {
            mOnButtonAvailable.activeButton(true);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnButtonAvailable = (OnButtonAvailable) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnButtonAvailable");
        }
    }

    public Nutrient getNutrient() {
        saveNutrientData();
        return mNutrient;
    }

    private void saveNutrientData() {
        if(this.mNutrient == null) {
            this.mNutrient = new Nutrient();
        }
        mNutrient.setName(nameOfNutrient.getText().toString().trim());
        mNutrient.setPh(ph.getEditValue());
        mNutrient.setNpk(getNPK());
        mNutrient.setDescription(description.getText().toString());
    }

    private String getNPK() {

        String nitrogen = mNitrogen.getText().toString();
        String phosphorus = mPhosphorus.getText().toString();
        String potassium = mPotassium.getText().toString();

        return nitrogen + "-" + phosphorus + "-" + potassium;
    }

}
