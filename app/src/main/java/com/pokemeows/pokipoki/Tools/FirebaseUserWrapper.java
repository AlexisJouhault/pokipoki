package com.pokemeows.pokipoki.tools;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.pokemeows.pokipoki.tools.firebase.FirebaseDatabaseReferenceKeys;
import com.pokemeows.pokipoki.tools.firebase.FirebaseDatabaseHelper;
import com.pokemeows.pokipoki.tools.database.Models.UserInfo;

import org.greenrobot.eventbus.EventBus;

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
}
