package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Flavor;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.FlavorRealm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class FlavorRealmToFlavor implements Mapper<FlavorRealm, Flavor> {

    @Override
    public Flavor map(FlavorRealm flavorRealm) {

        Flavor flavor = new Flavor();
        flavor.setId(flavorRealm.getId());
        copy(flavorRealm, flavor);

        return flavor;
    }

    @Override
    public Flavor copy(FlavorRealm flavorRealm, Flavor flavor) {

        flavor.setName(flavorRealm.getName());
        flavor.setImageUrl(flavorRealm.getImageUrl());

        return flavor;
    }
}
