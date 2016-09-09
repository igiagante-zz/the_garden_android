package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientPerDoseRealm;

/**
 * @author Ignacio Giagante, on 8/9/16.
 */
public class NutrientPerDoseRealmToNutrient implements Mapper<NutrientPerDoseRealm, Nutrient> {

    public NutrientPerDoseRealmToNutrient() {
    }

    @Override
    public Nutrient map(NutrientPerDoseRealm nutrientPerDoseRealm) {
        Nutrient nutrient = new Nutrient();
        nutrient.setId(nutrientPerDoseRealm.getId());
        return copy(nutrientPerDoseRealm, nutrient);
    }

    @Override
    public Nutrient copy(NutrientPerDoseRealm nutrientPerDoseRealm, Nutrient nutrient) {

        nutrient.setQuantityUsed(nutrientPerDoseRealm.getQuantityUsed());
        nutrient.setDescription(nutrientPerDoseRealm.getDescription());
        nutrient.setName(nutrientPerDoseRealm.getName());
        nutrient.setNpk(nutrientPerDoseRealm.getNpk());
        nutrient.setPh(nutrientPerDoseRealm.getPh());

        return nutrient;
    }
}
