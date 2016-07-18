package com.pokemeows.pokipoki.tools;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardOptions;
import com.pokemeows.pokipoki.tools.firebase.FirebaseDatabaseReferenceKeys;
import com.pokemeows.pokipoki.tools.firebase.FirebaseDatabaseHelper;
import com.pokemeows.pokipoki.tools.database.models.UserInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class FirebaseUserWrapper {

    private String displayName = null;
    private FirebaseUser firebaseUser;
    private DatabaseReference userReference;
    private UserInfo userInfo;

    public FirebaseUserWrapper(final FirebaseUser user) {
        this.firebaseUser = user;
        this.userReference = FirebaseDatabaseHelper.getInstance()
                .getReference(FirebaseDatabaseReferenceKeys.USERS).child(user.getUid());

        Query userQuery = FirebaseDatabaseHelper.getInstance()
                .getReference(FirebaseDatabaseReferenceKeys.USERS).orderByKey();
        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserInfo snapShotUserInfo = dataSnapshot.getValue(UserInfo.class);
                if (dataSnapshot.getKey().equals(user.getUid())) {
                    userInfo = snapShotUserInfo;
                    EventBus.getDefault().post(userInfo);
                    if (userInfo.getCards() != null) {
                        userInfo.processCardsInfo();
                        EventBus.getDefault().post(userInfo.getCards());
                    }
                    Log.d("DataChangeListener", "success retrieving user info");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("DataChangeListener", "changed");

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("DataChangeListener", "removed");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("DataChangeListener", "moved");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("DataChangeListener", "cancelled : " + databaseError.getMessage());

            }
        });
    }


    public String getName() {
        if (displayName == null && userInfo != null) {
            displayName = userInfo.getDisplayName();
        }
        return displayName;
    }

    public String getEmail() {
        return firebaseUser.getEmail();
    }

    public int getProfilePictureResource() {
        if (userInfo != null && userInfo.getProfileResource() != 0) {
            return userInfo.getProfileResource();
        }
        return DefaultUserSettings.DEFAULT_USER_IMAGE;
    }

    public void addFavouriteCard(Card card) {
        userInfo.addCardOption(card.getId(), CardOptions.FAVOURITE);
        userReference.child(FirebaseDatabaseReferenceKeys.CARDS).setValue(card.getId());
    }

    public void addHaveCard(Card card) {

    }

    public void addWantCard(Card card) {

    }

    public int getCardOption(String cardId) {
        if (userInfo.getUserCardsOptions().containsKey(cardId)) {
            return userInfo.getUserCardsOptions().get(cardId);
        }
        return 0;
    }
}
