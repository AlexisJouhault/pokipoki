package com.pokemeows.pokipoki.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Singletons.CurrentUserInfo;

public class SplashScreenActivity extends Activity {

    private final static String TAG = SplashScreenActivity.class.toString();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private boolean mainLaunched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private FirebaseAuth.AuthStateListener authStateListener= new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                // User is signed in
                if (!mainLaunched) {
                    mainLaunched = true;
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + firebaseUser.getUid());
                    CurrentUserInfo.getInstance().setFireBaseUser(firebaseUser);
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
                //redirect to correct activity here
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
