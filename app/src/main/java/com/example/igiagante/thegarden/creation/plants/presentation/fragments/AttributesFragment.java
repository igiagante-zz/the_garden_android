package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.ui.RecyclerViewItemClickListener;
import com.example.igiagante.thegarden.creation.plants.di.CreatePlantComponent;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.PlantBuilder;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.AttributeAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.AttributeDecorator;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.AttributeHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.FlavorHolder;
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

    @Bind(R.id.attributes_selected_id)
    RecyclerView attributesSelected;

    @Bind(R.id.attributes_available_id)
    RecyclerView availableAttributes;

    @Inject
    AttributesPresenter mAttributesPresenter;

    private AttributeAdapter attributeAdapter;

    private AttributeAdapter attributeSelectedAdapter;

    private ArrayList<AttributeHolder> mAttributes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.attributes_fragment, container, false);

        this.getComponent(CreatePlantComponent.class).inject(this);
        ButterKnife.bind(this, containerView);

        GridLayoutManager availableLayout = new GridLayoutManager(getContext(), 3);
        availableAttributes.setLayoutManager(availableLayout);
        attributeAdapter = new AttributeAdapter(getContext(), this);
        availableAttributes.setAdapter(attributeAdapter);
        availableAttributes.addItemDecoration(new AttributeDecorator(10));

        GridLayoutManager selectedLayout = new GridLayoutManager(getContext(), 2);
        attributesSelected.setLayoutManager(selectedLayout);
        attributeSelectedAdapter = new AttributeAdapter(getContext(), this);
        attributesSelected.setAdapter(attributeSelectedAdapter);

        attributesSelected.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), attributeSelectedAdapter));

        //Get available attributes
        mAttributesPresenter.getAttributes();

        return containerView;
    }

    @Override
    public void onTagClicked(AttributeHolder attributeHolder) {
        if(attributeHolder.isSelected()) {
            attributeSelectedAdapter.addTag(attributeHolder);
        }
        else {
            attributeAdapter.addTag(attributeHolder);
        }
    }

    @Override
    public void loadAttributes(Collection<AttributeHolder> attributes) {
        this.mAttributes = (ArrayList<AttributeHolder>) attributes;
        attributeAdapter.setAttributeHolders(this.mAttributes);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mAttributesPresenter.setView(new WeakReference<>(this));
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
        PlantBuilder builder = ((CreatePlantActivity)getActivity()).getPlantBuilder();
        builder.addAttributes(createAttributesSelectedList());
    }

    private ArrayList<Attribute> createAttributesSelectedList() {

        ArrayList<Attribute> attributes = new ArrayList<>();

        for (AttributeHolder holder : attributeSelectedAdapter.getAttributeHolders()) {
            if(holder.isSelected()) {
                Attribute attribute = holder.getModel();
                attributes.add(attribute);
            }
        }
        return attributes;
    }
}
