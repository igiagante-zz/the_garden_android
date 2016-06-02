package com.example.igiagante.thegarden.creation.plants.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.creation.plants.presentation.views.AttributesView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author igiagante on 10/5/16.
 */
public class AttributesFragment extends CreationBaseFragment implements AttributesView {

    private ArrayList<Attribute> attributes = new ArrayList<>();

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

        GridLayout gridLayout = (GridLayout) containerView.findViewById(R.id.attributes_available_id);
        setButtons(gridLayout);

        return containerView;
    }

    private void setButtons(GridLayout gridLayout) {

        for(int i = 0; i < attributes.size(); i++) {
            Button button = new Button(getContext());
            button.setText(attributes.get(i).getName());
            button.setGravity(Gravity.CENTER);
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.tag_background));
            button.setPadding(30, 30, 30, 30);

            gridLayout.addView(button, new GridLayout.LayoutParams(
                    GridLayout.spec(0, GridLayout.CENTER),
                    GridLayout.spec(i, GridLayout.CENTER)));

            GridLayout.LayoutParams params = (GridLayout.LayoutParams) button.getLayoutParams();
            params.setMarginStart(20);
            params.setMarginEnd(20);
            button.setLayoutParams(params);
        }
    }

    private void loadAtt() {
        Attribute attributeOne = new Attribute();
        attributeOne.setName("Relaxed");
        attributeOne.setType("Medicinal");

        Attribute attributeTwo = new Attribute();
        attributeTwo.setName("Headache");
        attributeTwo.setType("Medicinal");

        Attribute attributeThree = new Attribute();
        attributeThree.setName("Pain");
        attributeThree.setType("Medicinal");

        attributes.add(attributeOne);
        attributes.add(attributeTwo);
        attributes.add(attributeThree);
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
