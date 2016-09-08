package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.NutrientPerDoseRealm;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseRealmToDose implements Mapper<DoseRealm, Dose> {

    private final NutrientPerDoseRealmToNutrient toNutrient;

    public DoseRealmToDose(){
        this.toNutrient = new NutrientPerDoseRealmToNutrient();
    }

    @Override
    public Dose map(DoseRealm doseRealm) {
        Dose dose = new Dose();
        dose.setId(doseRealm.getId());
        return copy(doseRealm, dose);
    }

    @Override
    public Dose copy(DoseRealm doseRealm, Dose dose) {
        dose.setWater(doseRealm.getWater());
        dose.setPhDose(doseRealm.getPhDose());
        dose.setPh(doseRealm.getPh());
        dose.setEc(doseRealm.getEc());

        ArrayList<Nutrient> nutrients = new ArrayList<>();

        // add nutrients
        if(doseRealm.getNutrients() != null) {
            for (NutrientPerDoseRealm nutrientRealm : doseRealm.getNutrients()) {
                nutrients.add(toNutrient.map(nutrientRealm));
            }
        }

        dose.setNutrients(nutrients);

        return dose;
    }
}
