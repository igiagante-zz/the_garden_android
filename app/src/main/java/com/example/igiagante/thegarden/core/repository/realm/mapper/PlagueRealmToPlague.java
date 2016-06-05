package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueRealmToPlague implements Mapper<PlagueRealm, Plague> {

    @Override
    public Plague map(PlagueRealm plagueRealm) {

        Plague plague = new Plague();
        plague.setId(plagueRealm.getId());

        return copy(plagueRealm, plague);
    }

    @Override
    public Plague copy(PlagueRealm plagueRealm, Plague plague) {

        plague.setName(plagueRealm.getName());
        plague.setImageUrl(plagueRealm.getImageUrl());

        return plague;
    }
}
