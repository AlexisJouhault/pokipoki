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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.pokemeows.pokipoki.Activities.MainActivity;
import com.pokemeows.pokipoki.Listeners.LoginActionListener;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Tools.Database.DatabaseReferenceKeys;
import com.pokemeows.pokipoki.Tools.Session.CurrentUserInfo;
import com.pokemeows.pokipoki.Tools.Database.FirebaseDatabaseHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupFragment extends LoginPagerFragment {
    private static final String TAG = "SignupActivity";

    private View mainView;
    private LoginActionListener actionListener;

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    public SignupFragment() {
        setPosition(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_signup, container, false);
        actionListener = (LoginActionListener) getActivity();

        ButterKnife.bind(this, mainView);

        return mainView;
    }

    @OnClick(R.id.link_login)
    public void onLinkLoginClick() {
        actionListener.showLoginFragment();
    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed("Could not vaildate");
            return;
        }

        signupButton.setEnabled(false);

        showProgressDialog("Creating account...");

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        Map<String, String> arguments = new HashMap<>();
        arguments.put("name", name);

        actionListener.createUser(email, password, arguments, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        onSignUpSuccess(task);
                    } else {
                        throw task.getException();
                    }
                } catch (Exception e) {
                    onSignupFailed(e.getMessage());
                } finally {
                    hideProgressDialog();
                }
            }
        });
    }


    public void onSignUpSuccess(Task<AuthResult> task) {
        signupButton.setEnabled(true);
        if (task.getResult() != null) {
            //setting user info in database
            FirebaseDatabaseHelper firebaseDatabaseHelper = FirebaseDatabaseHelper.getInstance();
            DatabaseReference databaseReference = firebaseDatabaseHelper.getReference(DatabaseReferenceKeys.USERS);
            databaseReference.child(DatabaseReferenceKeys.USER_ID).setValue(task.getResult().getUser().getUid());

            CurrentUserInfo.getInstance().setFireBaseUser(task.getResult().getUser());
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    public void onSignupFailed(String errorMsg) {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

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