package com.pokemeows.pokipoki.tools.database.models;

import java.util.List;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */
public class CardSet {

    private String name;
    private Long id;
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardCount() {
        return cards.size();
    }
}
