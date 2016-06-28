package com.pokemeows.pokipoki.Tools;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pokemeows.pokipoki.Tools.Database.DatabaseReferenceKeys;
import com.pokemeows.pokipoki.Tools.Database.FirebaseDatabaseHelper;
import com.pokemeows.pokipoki.Tools.Database.Models.User;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class FirebaseUserWrapper {

    private String displayName = null;
    private FirebaseUser firebaseUser;
    private DatabaseReference userReference;

    public FirebaseUserWrapper(FirebaseUser user) {
        this.firebaseUser = user;
        this.userReference = FirebaseDatabaseHelper.getInstance()
                .getReference(DatabaseReferenceKeys.USERS).child(user.getUid());

        Query userQuery = FirebaseDatabaseHelper.getInstance()
                .getReference(DatabaseReferenceKeys.USERS).orderByKey();
        userQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User userModel = dataSnapshot.getValue(User.class);
                Log.d("Test", "toto");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Test", "toto");

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("Test", "toto");

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("Test", "toto");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Test", "toto");

            }
        });
    }


    public String getName() {
        return displayName;
    }

    public String getEmail() {
        return firebaseUser.getEmail();
    }
}
