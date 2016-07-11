package com.pokemeows.pokipoki.tools.database.models;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */

import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

/***
 *  Every single card that is made will be based on this model
 *  Long id: is for the database
 */
public class Card implements Comparable<Card>, Serializable {

    private String name;
    private String id;
    private String imageUrl;
    private String number;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int compareTo(@NonNull Card another) {
        int myNumber = getNumberFromString(this.getNumber().toCharArray());
        int anotherNumber = getNumberFromString(another.getNumber().toCharArray());
        if (myNumber < anotherNumber) {
            return -1;
        } else if (myNumber > anotherNumber) {
            return 1;
        }
        return 0;
    }

    private int getNumberFromString(char[] string) {
        int endCut;
        int startCut = -1;

        for (endCut = 0; endCut < string.length; endCut++) {
            if (string[endCut] >= '0' && string[endCut] <= '9') {
                if (startCut == -1) {
                    startCut = endCut;
                }
            } else if (startCut != -1) {
                break;
            }
        }
        int outOfSet = 0;
        if (startCut != 0) {
            outOfSet = 1000;
        }
        String numberString = new String(string);
        numberString = numberString.substring(startCut, endCut);
        return Integer.parseInt(numberString) + outOfSet;
    }
}
