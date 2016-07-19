package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Image;
import com.example.igiagante.thegarden.core.domain.entity.Nutrient;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.ImageRealm;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.NutrientRealm;

import java.util.ArrayList;

/**
 * @author Ignacio Giagante, on 4/7/16.
 */
public class NutrientRealmToNutrient implements Mapper<NutrientRealm, Nutrient> {

    private final ImageRealmToImage toImage;

    public NutrientRealmToNutrient(){
        this.toImage = new ImageRealmToImage();
    }

    @Override
    public Nutrient map(NutrientRealm nutrientRealm) {
        Nutrient nutrient = new Nutrient();
        nutrient.setId(nutrientRealm.getId());
        return copy(nutrientRealm, nutrient);
    }

    @Override
    public Nutrient copy(NutrientRealm nutrientRealm, Nutrient nutrient) {
        nutrient.setName(nutrientRealm.getName());
        nutrient.setPh(nutrientRealm.getPh());
        nutrient.setNpk(nutrientRealm.getNpk());
        nutrient.setDescription(nutrientRealm.getDescription());
        nutrient.setQuantityUsed(nutrientRealm.getQuantityUsed());

        ArrayList<Image> images = new ArrayList<>();

        // add images
        if(nutrientRealm.getImages() != null) {
            for (ImageRealm imageRealm : nutrientRealm.getImages()) {
                images.add(toImage.map(imageRealm));
            }
        }

        nutrient.setImages(images);

        return nutrient;
    }
}
