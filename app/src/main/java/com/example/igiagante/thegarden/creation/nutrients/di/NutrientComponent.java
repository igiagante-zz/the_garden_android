package com.example.igiagante.thegarden.creation.nutrients.di;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.di.components.ApplicationComponent;
import com.example.igiagante.thegarden.core.di.modules.ActivityModule;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientActivity;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientDetailActivity;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientDetailFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.fragments.NutrientListFragment;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientDetailPresenter;
import com.example.igiagante.thegarden.creation.nutrients.presentation.presenters.NutrientPresenter;
import com.example.igiagante.thegarden.creation.plants.di.components.GalleryComponent;
import com.example.igiagante.thegarden.creation.plants.di.module.GalleryModule;

import dagger.Component;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, NutrientModule.class, GalleryModule.class})
public interface NutrientComponent extends GalleryComponent {

    void inject(NutrientListFragment nutrientListFragment);
    void inject(NutrientDetailFragment nutrientDetailFragment);
    void inject(NutrientDetailActivity nutrientDetailActivity);
    void inject(NutrientActivity nutrientActivity);

    NutrientPresenter nutrientPresenter();

    NutrientDetailPresenter nutrientDetailPresenter();

    Session session();
}
