package com.pokemeows.pokipoki.tools.database.models;

import com.orm.SugarRecord;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class UserInfo extends SugarRecord {

    private String userId;
    private String displayName;
    private String email;
    private int profileResource;
    /***
     * Data for cards : {setId}:{cardId};...
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
}
