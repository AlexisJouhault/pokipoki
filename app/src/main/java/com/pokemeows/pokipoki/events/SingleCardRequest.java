package com.pokemeows.pokipoki.events;

import com.pokemeows.pokipoki.tools.database.models.Card;

/**
 * Created by alexisjouhault on 7/25/16.
 *
 */
public class SingleCardRequest {

    private Card card;
    private int option;

    public SingleCardRequest(Card card, int option) {
        this.card = card;
        this.option = option;
    }

    public Card getCard() {
        return card;
    }

    public int getOption() {
        return option;
    }
}
