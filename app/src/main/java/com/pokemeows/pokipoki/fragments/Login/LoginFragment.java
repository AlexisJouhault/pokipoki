package com.pokemeows.pokipoki.Fragments.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.pokemeows.pokipoki.Activities.MainActivity;
import com.pokemeows.pokipoki.Listeners.LoginActionListener;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Tools.Session.CurrentUserInfo;
import com.pokemeows.pokipoki.Tools.MessageDisplayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexisjouhault on 6/22/16.
 * ~~PokiPoki project~~
 */
public class LoginFragment extends LoginPagerFragment {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private View mainView;
    private LoginActionListener actionListener;

    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.link_signup) TextView signupLink;

    public LoginFragment() {
        setPosition(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_login, container, false);
        actionListener = (LoginActionListener) getActivity();

        ButterKnife.bind(this, mainView);

        return mainView;
    }

    @OnClick(R.id.link_signup)
    public void onLinkSignUpClick() {
        actionListener.showSignUpFragment();

    }

    @OnClick(R.id.btn_login)
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed("Could not validate");
            return;
        }

        loginButton.setEnabled(false);

        showProgressDialog("Authenticating...");

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        actionListener.signIn(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        onLoginSuccess(task);
                    } else {
                        throw task.getException();
                    }
                } catch (Exception e) {
                    onLoginFailed(e.getMessage());
                } finally {
                    hideProgressDialog();
                }
            }
        });
    }

    public void onLoginSuccess(Task<AuthResult> task) {
        loginButton.setEnabled(true);
        if (task.getResult() != null) {
            CurrentUserInfo.getInstance().setFireBaseUser(task.getResult().getUser());
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    public void onLoginFailed(String errorMsg) {
        MessageDisplayer.showMessage(getActivity(), errorMsg);
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}
