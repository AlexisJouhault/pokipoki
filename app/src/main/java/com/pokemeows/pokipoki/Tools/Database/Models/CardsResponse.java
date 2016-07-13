package com.pokemeows.pokipoki.tools.database.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexisjouhault on 7/10/16.
 */
public class CardsResponse implements Serializable {

    List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
