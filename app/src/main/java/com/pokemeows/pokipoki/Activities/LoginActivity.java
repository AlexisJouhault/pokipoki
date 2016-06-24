package com.pokemeows.pokipoki.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pokemeows.pokipoki.Adapters.LoginFragmentPagerAdapter;
import com.pokemeows.pokipoki.Listeners.LoginActionListener;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Tools.MessageDisplayer;
import com.pokemeows.pokipoki.Views.UnswipableViewPager;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginActionListener {

    private final static String TAG = LoginActivity.class.toString();

    private LoginFragmentPagerAdapter loginPagerAdapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @BindView(R.id.login_pager) UnswipableViewPager loginPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupUI();
    }

    private void setupUI() {
        loginPagerAdapter = new LoginFragmentPagerAdapter(getSupportFragmentManager());
         loginPager.setAdapter(loginPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (loginPager.getCurrentItem() > 0) {
            loginPager.setCurrentItem(0);
        }
    }

    @Override
    public void showSignUpFragment() {
        loginPager.setCurrentItem(loginPagerAdapter.getSignUpPos());
    }

    @Override
    public void showLoginFragment() {
        loginPager.setCurrentItem(loginPagerAdapter.getLoginPos());

    }

    @Override
    public void createUser(String email, String password, Map<String,
            String> arguments, final OnCompleteListener<AuthResult> onCompleteListener) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            MessageDisplayer.showMessage(LoginActivity.this, "Success");
                        }

                        onCompleteListener.onComplete(task);

                    }
                });
    }

    @Override
    public void signIn(String email, String password,
                       final OnCompleteListener<AuthResult> onCompleteListener) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            MessageDisplayer.showMessage(LoginActivity.this, "Success");
                        }
                        onCompleteListener.onComplete(task);

                    }
                });
    }

    private FirebaseAuth.AuthStateListener authStateListener= new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
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
