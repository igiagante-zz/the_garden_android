package com.example.igiagante.thegarden.core.repository.realm.mapper;

import com.example.igiagante.thegarden.core.domain.entity.Plague;
import com.example.igiagante.thegarden.core.repository.Mapper;
import com.example.igiagante.thegarden.core.repository.network.Settings;
import com.example.igiagante.thegarden.core.repository.realm.modelRealm.PlagueRealm;

import io.realm.Realm;

/**
 * @author Ignacio Giagante, on 6/5/16.
 */
public class PlagueToPlagueRealm implements Mapper<Plague, PlagueRealm> {

    private final Realm realm;

    public PlagueToPlagueRealm(Realm realm) {
        this.realm = realm;
    }

    @Override
    public PlagueRealm map(Plague plague) {
        PlagueRealm plagueRealm = realm.createObject(PlagueRealm.class);
        plagueRealm.setId(plague.getId());
        copy(plague, plagueRealm);

        return plagueRealm;
    }

    @Override
    public PlagueRealm copy(Plague plague, PlagueRealm plagueRealm) {

        plagueRealm.setName(plague.getName());
        plagueRealm.setImageUrl(plague.getImageUrl().replace(Settings.DOMAIN, ""));
        plagueRealm.setLocalPath(plague.getLocalPath());

        return plagueRealm;
    }

}
