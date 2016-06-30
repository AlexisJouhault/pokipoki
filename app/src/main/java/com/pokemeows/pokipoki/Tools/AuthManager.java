package com.pokemeows.pokipoki.tools;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * Created by alexisjouhault on 6/25/16.
 * ~~PokiPoki project~~
 */
public class AuthManager {

    private Activity parentActivity;
    private FirebaseAuth fireBaseAuth;

    public AuthManager(Activity parentActivity, FirebaseAuth firebaseAuth) {
        this.parentActivity = parentActivity;
        this.fireBaseAuth = firebaseAuth;
    }

    public void login(String email, String password,
                      final OnCompleteListener<AuthResult> onCompleteListener) {
        this.fireBaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(parentActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MessageDisplayer.showMessage(parentActivity, "Success");
                        }
                        onCompleteListener.onComplete(task);

                    }
                });
    }

    public void createAccount(String email, String password,
                              Map<String, String> arguments,
                              final OnCompleteListener<AuthResult> onCompleteListener) {

        this.fireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(parentActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MessageDisplayer.showMessage(parentActivity, "Success");
                        }

                        onCompleteListener.onComplete(task);

                    }
                });
    }
}
