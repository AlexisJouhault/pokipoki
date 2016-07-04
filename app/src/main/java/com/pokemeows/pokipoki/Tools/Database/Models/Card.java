package com.pokemeows.pokipoki.tools.database.models;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */
public class Card {

    private String name;
    private Long id;
    private int rarity;
    private int type;

    enum RARITY {
        COMMON(0),
        UNCOMMON(1),
        RARE(2);

        private int value;

        RARITY(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }
    }
}
