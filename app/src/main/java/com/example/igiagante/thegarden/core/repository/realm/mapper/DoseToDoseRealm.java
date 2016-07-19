package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Dose;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.DoseRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.tables.Table;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class DoseToDoseRealm implements Mapper<Dose, DoseRealm> {

    private final Realm realm;
    private final NutrientToNutrientRealm toNutrientRealm;

    public DoseToDoseRealm(Realm realm) {
        this.realm = realm;
        this.toNutrientRealm = new NutrientToNutrientRealm(realm);
    }

    @Override
    public DoseRealm map(Dose dose) {
        DoseRealm doseRealm = realm.createObject(DoseRealm.class);
        doseRealm.setId(dose.getId());
        return copy(dose, doseRealm);
    }

    @Override
    public DoseRealm copy(Dose dose, DoseRealm doseRealm) {

        doseRealm.setWater(dose.getWater());
        doseRealm.setPh(dose.getPh());
        doseRealm.setEc(dose.getEc());
        doseRealm.setPhDose(dose.getPhDose());
        doseRealm.setEditable(dose.isEditable());

        // nutrients realm list
        RealmList<NutrientRealm> nutrientRealms = new RealmList<>();

        // add nutrients
        if (dose.getNutrients() != null) {
            for (Nutrient nutrient : dose.getNutrients()) {
                NutrientRealm nutrientRealm = realm.where(NutrientRealm.class).equalTo(Table.ID, nutrient.getId()).findFirst();
                if (nutrientRealm == null) {
                    // create nutrient realm object and set id
                    nutrientRealm = realm.createObject(NutrientRealm.class);
                    nutrientRealm.setId(nutrient.getId());
                }
                nutrientRealms.add(toNutrientRealm.copy(nutrient, nutrientRealm));
            }
        }

        doseRealm.setNutrients(nutrientRealms);

        return doseRealm;
    }
}
