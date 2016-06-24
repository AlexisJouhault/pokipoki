package com.pokemeows.pokipoki.Listeners;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;

import java.util.Map;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public interface LoginActionListener {
    public void showSignUpFragment();
    public void showLoginFragment();

    public void createUser(String email, String password, Map<String,
            String> arguments, OnCompleteListener<AuthResult> onCompleteListener);

    public void signIn(String email, String password,
                       OnCompleteListener<AuthResult> onCompleteListener);
}
