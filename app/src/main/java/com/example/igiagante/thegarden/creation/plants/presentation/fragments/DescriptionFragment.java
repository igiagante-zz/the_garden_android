package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.PlagueAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.AttributeHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.FlavorHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.PlagueHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.PlaguePresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.PlagueView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class DescriptionFragment extends CreationBaseFragment implements PlagueView {

    public static final String PLAGUES_KEY = "PLAGUES";
    public static final String PLANT_DESCRIPTION_KEY = "PLANT_DESCRIPTION";

    @Bind(R.id.palgues_recycleview_id)
    RecyclerView mPlaguesRecycleView;

    @Bind(R.id.plant_description_id)
    EditText descriptionTextArea;

    @Bind(R.id.plant_save_button)
    Button saveButton;

    @Bind(R.id.plant_cancel_button)
    Button cancelButton;

    @Inject
    PlaguePresenter mPlaguePresenter;

    private PlagueAdapter mAdapter;

    private ArrayList<PlagueHolder> mPlagues = new ArrayList<>();

    private String mPlantDescription;

    /**
     * Listener used to notify when wizard process has finished
     */
    private OnSavePlantListener onSavePlantListener;

    public interface OnSavePlantListener {
        void onSavePlant();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mPlagues = savedInstanceState.getParcelableArrayList(PLAGUES_KEY);
            mPlantDescription = savedInstanceState.getString(PLANT_DESCRIPTION_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.description_fragment, container, false);

        this.getComponent(CreatePlantComponent.class).inject(this);
        ButterKnife.bind(this, containerView);

        if (!TextUtils.isEmpty(mPlantDescription)) {
            descriptionTextArea.setText(mPlantDescription);
        }

        GridLayoutManager selectedLayout = new GridLayoutManager(getContext(), 3);
        mPlaguesRecycleView.setLayoutManager(selectedLayout);
        mAdapter = new PlagueAdapter(getContext());
        mPlaguesRecycleView.setAdapter(mAdapter);

        if (mPlagues.isEmpty()) {
            //Get plagues
            mPlaguePresenter.getPlagues();
        } else {
            mAdapter.setPlagues(mPlagues);
        }

        saveButton.setOnClickListener(v -> {
            saveDescription();
            onSavePlantListener.onSavePlant();
        });

        return containerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPlaguePresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mPlaguePresenter.destroy();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mPlaguesRecycleView.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void loadPlagues(Collection<PlagueHolder> plagues) {
        this.mPlagues = (ArrayList<PlagueHolder>) plagues;

        // ask to the activity if it has a plant for edition
        if(mPlant != null) {
            createAttributesHolderSelectedList();
            descriptionTextArea.setText(mPlant.getDescription());
        }

        mAdapter.setPlagues(mPlagues);
    }

    /**
     * Filter the plague holder list
     */
    private void createAttributesHolderSelectedList() {

        if(mPlagues != null) {
            for (Plague plague : mPlant.getPlagues()) {
                for ( PlagueHolder plagueHolder : mPlagues) {
                    if(plague.getName().equals(plagueHolder.getPlagueName())) {
                        plagueHolder.setSelected(true);
                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PLAGUES_KEY, mPlagues);
        outState.putString(PLANT_DESCRIPTION_KEY, descriptionTextArea.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onSavePlantListener = (CreatePlantActivity)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSavePlantListener");
        }

    }

    private void saveDescription() {
        move();
    }

    @Override
    protected void move() {
        Plant.PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
        builder.addPlagues(createPlaguesSelectedList());
        builder.addDescription(descriptionTextArea.getText().toString());
    }

    private ArrayList<Plague> createPlaguesSelectedList() {

        ArrayList<Plague> plagues = new ArrayList<>();

        if(mAdapter.getPlagues() != null) {
            for (PlagueHolder holder : mAdapter.getPlagues()) {
                if(holder.isSelected()) {
                    Plague plague = holder.getModel();
                    plague.setImageUrl(holder.getImagePath());
                    plagues.add(plague);
                }
            }
        }
        return plagues;
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
