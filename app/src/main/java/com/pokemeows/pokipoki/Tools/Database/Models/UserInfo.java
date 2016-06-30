package com.pokemeows.pokipoki.tools.database.Models;

import com.orm.SugarRecord;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class UserInfo extends SugarRecord {

    private String userId;
    private String displayName;
    private String email;

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
}
