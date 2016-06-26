package com.pokemeows.pokipoki.Singletons;

import android.support.annotation.NonNull;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

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
}
