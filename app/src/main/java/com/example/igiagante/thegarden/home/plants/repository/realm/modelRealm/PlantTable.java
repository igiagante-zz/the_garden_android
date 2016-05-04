package com.example.igiagante.thegarden.home.plants.repository.realm.modelRealm;

/**
 * Created by igiagante on 26/4/16.
 */

public interface PlantTable {

    String ID = "id";
    String NAME = "name";
    String GARDENID = "gardenId";
    String ECSOIL = "ecSoil";
    String PHSOIL = "phSoil";
    String SIZE = "size";
    String HARVEST = "harvest";

    // this attribute is only to check if some image should be deleted
    String RESOURCES_IDS =  "resourcesIds";

    interface Image {
        String ID = "id";
        String NAME = "name";
        String TYPE = "type";
        String SIZE = "size";
        String URL = "url";
        String THUMBURL = "thumbUrl";
        String MAIN = "main";
    }

    interface Flavor {
        String ID = "id";
        String NAME = "name";
    }
}
