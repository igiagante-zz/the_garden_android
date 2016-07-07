package com.example.igiagante.thegarden.repository;

import android.test.AndroidTestCase;

import com.example.igiagante.thegarden.core.repository.realm.AttributeRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.GardenRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlagueRealmRepository;
import com.example.igiagante.thegarden.core.repository.realm.PlantRealmRepository;
import com.example.igiagante.thegarden.repository.sqlite.TestUtilities;

/**
 * @author Ignacio Giagante, on 10/6/16.
 */
public class CleanDatabaseTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PlantRealmRepository plantRealmRepository = new PlantRealmRepository(getContext());
        AttributeRealmRepository attributeRealmRepository = new AttributeRealmRepository((getContext()));
        PlagueRealmRepository plagueRealmRepository = new PlagueRealmRepository(getContext());
        GardenRealmRepository gardenRealmRepository = new GardenRealmRepository(getContext());
        plantRealmRepository.removeAll();
        attributeRealmRepository.removeAll();
        plagueRealmRepository.removeAll();
        gardenRealmRepository.removeAll();
        TestUtilities.cleanDataBase(getContext());
    }
}
