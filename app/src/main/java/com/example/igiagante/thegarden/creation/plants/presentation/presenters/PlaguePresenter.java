package com.example.igiagante.thegarden.creation.plants.presentation.presenters;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.presentation.mvp.AbstractPresenter;
import com.example.igiagante.thegarden.core.usecase.DefaultSubscriber;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.plants.presentation.dataHolders.PlagueHolder;
import com.example.igiagante.thegarden.creation.plants.presentation.views.PlagueView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Ignacio Giagante, on 6/4/16.
 */
@PerActivity
public class PlaguePresenter extends AbstractPresenter<PlagueView> {

    private final UseCase getPlaguesUserCase;

    @Inject
    public PlaguePresenter(@Named("plagues") UseCase getPlaguesUserCase) {
        this.getPlaguesUserCase = getPlaguesUserCase;
    }

    public void destroy() {
        this.getPlaguesUserCase.unsubscribe();
        this.view = null;
    }

    /**
     * Load the plagues in the view
     *
     * @param plagues list of plagues
     */
    public void addAttributesInView(List<Plague> plagues) {
        getView().loadPlagues(createPlagueHolderList(plagues));
    }

    /**
     * Transform a list of flavors to flavor holders
     *
     * @param plagues list of attributes
     * @return list of plague holders
     */
    private ArrayList<PlagueHolder> createPlagueHolderList(List<Plague> plagues) {

        ArrayList<PlagueHolder> plagueHolders = new ArrayList<>();

        for (Plague plague : plagues) {
            PlagueHolder plagueHolder = new PlagueHolder();
            plagueHolder.setModel(plague);
            plagueHolders.add(plagueHolder);
        }

        return plagueHolders;
    }

    /**
     * Get plague list
     */
    public void getPlagues() {
        this.getPlaguesUserCase.execute(null, new AttributesSubscriber());
    }

    private final class AttributesSubscriber extends DefaultSubscriber<List<Plague>> {

        @Override
        public void onCompleted() {
            //PlantListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            //PlantListPresenter.this.hideViewLoading();
            //PlantListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            //PlantListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Plague> plagues) {
            PlaguePresenter.this.addAttributesInView(plagues);
        }
    }
}