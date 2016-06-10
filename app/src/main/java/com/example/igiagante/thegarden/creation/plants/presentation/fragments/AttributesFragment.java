package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.AttributeAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.AttributeDecorator;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.AttributeHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.FlavorHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.presenters.AttributesPresenter;
import com.example.igiagante.thegarden.creation.plants.presentation.views.AttributesView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class AttributesFragment extends CreationBaseFragment implements AttributesView,
        AttributeAdapter.TagActionListener {

    public static final String AVAILABLE_ATTRIBUTES = "AVAILABLE_ATTRIBUTES";
    public static final String SELECTED_ATTRIBUTES = "SELECTED_ATTRIBUTES";

    @Bind(R.id.attributes_selected_id)
    RecyclerView attributesSelected;

    @Bind(R.id.attributes_available_id)
    RecyclerView availableAttributes;

    @Inject
    AttributesPresenter mAttributesPresenter;

    private AttributeAdapter attributeAdapter;

    private AttributeAdapter attributeSelectedAdapter;

    private ArrayList<AttributeHolder> mAttributes = new ArrayList<>();
    private ArrayList<AttributeHolder> mAttributesSelected = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mAttributes = savedInstanceState.getParcelableArrayList(AVAILABLE_ATTRIBUTES);
            mAttributesSelected = savedInstanceState.getParcelableArrayList(SELECTED_ATTRIBUTES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.attributes_fragment, container, false);

        this.getComponent(CreatePlantComponent.class).inject(this);
        ButterKnife.bind(this, containerView);

        GridLayoutManager availableLayout;

        if (isLandScape()) {
            availableLayout = new GridLayoutManager(getContext(), 5);
        } else {
            availableLayout = new GridLayoutManager(getContext(), 3);
        }

        availableAttributes.setLayoutManager(availableLayout);
        attributeAdapter = new AttributeAdapter(getContext(), this);
        availableAttributes.setAdapter(attributeAdapter);
        availableAttributes.addItemDecoration(new AttributeDecorator(10));

        GridLayoutManager selectedLayout;

        if (isLandScape()) {
            selectedLayout = new GridLayoutManager(getContext(), 3);
        } else {
            selectedLayout = new GridLayoutManager(getContext(), 2);
        }

        attributesSelected.setLayoutManager(selectedLayout);
        attributeSelectedAdapter = new AttributeAdapter(getContext(), this);
        attributesSelected.setAdapter(attributeSelectedAdapter);

        if (mAttributes.isEmpty()) {
            //Get available attributes
            mAttributesPresenter.getAttributes();
        } else {
            attributeAdapter.setAttributeHolders(mAttributes);
            // add selected attributes
            attributeSelectedAdapter.setAttributeHolders(mAttributesSelected);
        }
        return containerView;
    }

    @Override
    public void onTagClicked(AttributeHolder attributeHolder) {
        if (attributeHolder.isSelected()) {
            attributeSelectedAdapter.addTag(attributeHolder);
        } else {
            attributeAdapter.addTag(attributeHolder);
        }
    }

    @Override
    public void loadAttributes(Collection<AttributeHolder> attributes) {
        this.mAttributes = (ArrayList<AttributeHolder>) attributes;

        // ask to the activity if it has a plant for edition and filter the flavor list
        if (mPlant != null) {
            createAttributesHolderSelectedList();
            attributeSelectedAdapter.setAttributeHolders(mAttributesSelected);
        }

        attributeAdapter.setAttributeHolders(this.mAttributes);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mAttributesPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        attributesSelected.setAdapter(null);
        availableAttributes.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(AVAILABLE_ATTRIBUTES, mAttributes);
        ArrayList<AttributeHolder> attributeHoldersSelected = attributeSelectedAdapter.getAttributeHolders();
        outState.putParcelableArrayList(SELECTED_ATTRIBUTES, attributeHoldersSelected);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    protected void move() {
        Plant.PlantBuilder builder = ((CreatePlantActivity) getActivity()).getPlantBuilder();
        builder.addAttributes(createAttributesSelectedList());
    }

    private ArrayList<Attribute> createAttributesSelectedList() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        if (attributeSelectedAdapter.getAttributeHolders() != null) {
            for (AttributeHolder holder : attributeSelectedAdapter.getAttributeHolders()) {
                if (holder.isSelected()) {
                    Attribute attribute = holder.getModel();
                    attributes.add(attribute);
                }
            }
        }

        return attributes;
    }

    /**
     * Filter the attribute holder list in order to create a list of selected attributes
     */
    private void createAttributesHolderSelectedList() {

        if (mAttributes != null) {
            for (Attribute attribute : mPlant.getAttributes()) {
                AttributeHolder attributeHolder = new AttributeHolder();
                attributeHolder.setModel(attribute);
                attributeHolder.setSelected(true);
                mAttributesSelected.add(attributeHolder);
            }
        }
    }
}
