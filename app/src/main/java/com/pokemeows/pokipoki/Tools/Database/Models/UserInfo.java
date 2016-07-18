package com.pokemeows.pokipoki.tools.database.models;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class UserInfo extends SugarRecord {

    private String userId;
    private String displayName;
    private String email;
    private int profileResource;
    private HashMap<String, Integer> userCardsOptions = new HashMap<>();
    /***
     * Data for cards : {setId}:{cardId}:{status};...
     * status : 0 - 7
     * 0 = none, 1 = favourite, 2 = have, 3 = have + favourite, 4 = want, 5 = want + favourite, 6 = want + have (if want multiple), 7 = want + have + favourite
     */
    private String cards;

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProfileResource() {
        return profileResource;
    }

    public void setProfileResource(int profileResource) {
        this.profileResource = profileResource;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public void processCardsInfo() {
        String[] allCardsInfo = cards.split(";");
        for (String cardInfo : allCardsInfo) {
            String[] splitCardInfo = cardInfo.split(":");
            if (splitCardInfo.length > 1) {
                String cardId = splitCardInfo[0];
                String cardOptions = splitCardInfo[1];
                userCardsOptions.put(cardId, Integer.parseInt(cardOptions));
            }
        }

    }

    public void addCardOption(String id, int option) {
        if (userCardsOptions.containsKey(id)) {
            int options = userCardsOptions.get(id);

            if (CardOptions.isOptionSelected(options, option)) {
                options -= option;
                userCardsOptions.put(id, options);
            } else {
                options += option;
                userCardsOptions.put(id, options);
            }

        } else {
            userCardsOptions.put(id, option);
        }
    }

    public HashMap<String, Integer> getUserCardsOptions() {
        return userCardsOptions;
    }
}
