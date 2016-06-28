package com.pokemeows.pokipoki.Tools.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by alexisjouhault on 6/26/16.
 * ~~PokiPoki project~~
 */
public class FirebaseDatabaseHelper {

    private static FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
    private FirebaseDatabase firebaseDatabase;

    private FirebaseDatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public static FirebaseDatabaseHelper getInstance() {
        return firebaseDatabaseHelper;
    }

    public DatabaseReference getReference(String id) {
        return firebaseDatabase.getReference(id);
    }
}
