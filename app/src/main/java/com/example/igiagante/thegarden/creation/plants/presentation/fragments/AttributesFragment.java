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
import com.example.igiagante.thegarden.creation.plants.presentation.adapters.AttributeAdapter;
import com.example.igiagante.thegarden.creation.plants.presentation.holders.AttributeHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.views.AttributesView;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author igiagante on 10/5/16.
 */
public class AttributesFragment extends CreationBaseFragment implements AttributesView,
        AttributeAdapter.TagActionListener{

    @Bind(R.id.attributes_selected_id)
    RecyclerView attributesSelected;

    @Bind(R.id.attributes_available_id)
    RecyclerView availableAttributes;

    private AttributeAdapter attributeAdapter;

    private AttributeAdapter attributeSelectedAdapter;

    private ArrayList<AttributeHolder> attributes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAtt();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View containerView = inflater.inflate(R.layout.attributes_fragment, container, false);

        ButterKnife.bind(this, containerView);

        GridLayoutManager availableLayout = new GridLayoutManager(getContext(), 3);
        availableAttributes.setLayoutManager(availableLayout);
        attributeAdapter = new AttributeAdapter(getContext(), this);
        availableAttributes.setAdapter(attributeAdapter);

        attributeAdapter.setAttributeHolders(attributes);

        GridLayoutManager selectedLayout = new GridLayoutManager(getContext(), 2);
        attributesSelected.setLayoutManager(selectedLayout);
        attributeSelectedAdapter = new AttributeAdapter(getContext(), this);
        attributesSelected.setAdapter(attributeSelectedAdapter);

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

    private void loadAtt() {

        AttributeHolder attributeHolderOne = new AttributeHolder();
        Attribute attributeOne = new Attribute();
        attributeOne.setName("Relaxed");
        attributeOne.setType("Medicinal");
        attributeHolderOne.setModel(attributeOne);

        AttributeHolder attributeHolderTwo = new AttributeHolder();
        Attribute attributeTwo = new Attribute();
        attributeTwo.setName("Headache");
        attributeTwo.setType("Medicinal");
        attributeHolderTwo.setModel(attributeTwo);

        AttributeHolder attributeHolderThree = new AttributeHolder();
        Attribute attributeThree = new Attribute();
        attributeThree.setName("Pain");
        attributeThree.setType("Medicinal");
        attributeHolderThree.setModel(attributeThree);

        attributes.add(attributeHolderOne);
        attributes.add(attributeHolderTwo);
        attributes.add(attributeHolderThree);
    }

    @Override
    public void loadAttributes(Collection<Attribute> attributes) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
