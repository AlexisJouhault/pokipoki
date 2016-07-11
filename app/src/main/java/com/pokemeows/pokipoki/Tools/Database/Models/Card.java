package com.pokemeows.pokipoki.tools.database.models;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */

/***
 *  Every single card that is made will be based on this model
 *  Long id: is for the database
 */
public class Card {

    private String name;
    private String id;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
