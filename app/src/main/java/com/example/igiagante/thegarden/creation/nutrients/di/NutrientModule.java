package com.example.igiagante.thegarden.creation.nutrients.di;

import com.example.igiagante.thegarden.core.di.PerActivity;
import com.example.igiagante.thegarden.core.usecase.UseCase;
import com.example.igiagante.thegarden.creation.nutrients.usecase.DeleteNutrientUseCase;
import com.example.igiagante.thegarden.creation.nutrients.usecase.ExistNutrientUseCase;
import com.example.igiagante.thegarden.creation.nutrients.usecase.GetNutrientsUseCase;
import com.example.igiagante.thegarden.creation.nutrients.usecase.SaveNutrientUseCase;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ignacio Giagante, on 12/7/16.
 */
@Module
public class NutrientModule {

    @Provides
    @PerActivity
    @Named("getNutrients")
    UseCase provideGetNutrientsUseCase(GetNutrientsUseCase getNutrientsUseCase) {
        return getNutrientsUseCase;
    }

    @Provides
    @PerActivity
    @Named("saveNutrient")
    UseCase provideSaveNutrientUseCase(SaveNutrientUseCase saveNutrientUseCase) {
        return saveNutrientUseCase;
    }

    @Provides
    @PerActivity
    @Named("deleteNutrient")
    UseCase provideDeleteNutrientUseCase(DeleteNutrientUseCase deleteNutrientUseCase) {
        return deleteNutrientUseCase;
    }

    @Provides
    @PerActivity
    @Named("existNutrient")
    UseCase provideExistNutrientUseCase(ExistNutrientUseCase existNutrient) {
        return existNutrient;
    }
}
