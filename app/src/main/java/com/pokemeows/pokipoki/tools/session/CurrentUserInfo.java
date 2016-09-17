package com.pokemeows.pokipoki.tools.session;

import com.google.firebase.auth.FirebaseUser;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class CurrentUserInfo {

    private static CurrentUserInfo currentUserInfo = new CurrentUserInfo();
    private FirebaseUserWrapper firebaseUser;

    private CurrentUserInfo() {

    }

    public static CurrentUserInfo getInstance() {
        return currentUserInfo;
    }

    public void setFireBaseUser(FirebaseUser user) {
        this.firebaseUser = new FirebaseUserWrapper(user);

    }

    public FirebaseUserWrapper getFirebaseUser() {
        return firebaseUser;
    }

    public void logout() {
        this.firebaseUser = null;
    }
}
