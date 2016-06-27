package com.pokemeows.pokipoki.Tools.Session;

import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class CurrentUserInfo {

    private static CurrentUserInfo currentUserInfo = new CurrentUserInfo();
    private FirebaseUser firebaseUser;

    private CurrentUserInfo() {

    }

    public static CurrentUserInfo getInstance() {
        return currentUserInfo;
    }

    public void setFireBaseUser(FirebaseUser user) {
        this.firebaseUser = user;

    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void logout() {
        this.firebaseUser = null;
    }
}
