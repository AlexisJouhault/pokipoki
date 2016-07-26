package com.pokemeows.pokipoki.tools.database.models;

import java.io.Serializable;

/**
 * Created by alexisjouhault on 7/25/16.
 *
 */
public class SingleCardResponse implements Serializable {

    private Card card;

    public Card getCard() {
        return card;
    }
}
