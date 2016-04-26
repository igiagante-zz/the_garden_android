package com.example.igiagante.thegarden.plants.repository.realm;

/**
 * Created by igiagante on 26/4/16.
 */

public interface PlantTable {

    String ID = "id";
    String NAME = "name";
    String GARDENID = "gardenId";
    String ECSOIL = "ecSoil";
    String PHSOIL = "phSoil";

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
