package com.pokemeows.pokipoki.tools.database.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */
public class CardSet implements Serializable {

    private String code;
    private String name;
    private String series;
    private int totalCards;
    private boolean standardLegal;
    private String releaseDate;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSeries() {
        return series;
    }

    public boolean isStandardLegal() {
        return standardLegal;
    }

    public int getTotalCards() {
        return totalCards;
    }
}
