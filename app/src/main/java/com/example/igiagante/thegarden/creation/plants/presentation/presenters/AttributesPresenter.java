package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import android.util.Log;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Attribute;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.AttributeHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.views.AttributesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 2/6/16.
 */
@PerActivity
public class AttributesPresenter extends AbstractPresenter<AttributesView> {

    private final UseCase getAttributesUserCase;

    @Inject
    public AttributesPresenter(@Named("attributes") UseCase getAttributesUserCase) {
        this.getAttributesUserCase = getAttributesUserCase;
    }

    public void destroy() {
        this.getAttributesUserCase.unsubscribe();
        this.view = null;
    }

    /**
     * Load the attributes in the view
     *
     * @param attributes list of attributes
     */
    public void addAttributesInView(List<Attribute> attributes) {
        getView().loadAttributes(createAttributeHolderList(attributes));
    }

    /**
     * Transform a list of flavors to flavor holders
     *
     * @param attributes list of attributes
     * @return list of attribute holders
     */
    public ArrayList<AttributeHolder> createAttributeHolderList(List<Attribute> attributes) {

        ArrayList<AttributeHolder> attributeHolders = new ArrayList<>();

        for (Attribute attribute : attributes) {
            AttributeHolder attributeHolder = new AttributeHolder();
            attributeHolder.setModel(attribute);
            attributeHolders.add(attributeHolder);
        }

        return attributeHolders;
    }

    /**
     * Get attribute list
     */
    public void getAttributes() {
        this.getAttributesUserCase.execute(null, new AttributesSubscriber());
    }

    private final class AttributesSubscriber extends DefaultSubscriber<List<Attribute>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Attribute> attributes) {
            AttributesPresenter.this.addAttributesInView(attributes);
        }
    }
}