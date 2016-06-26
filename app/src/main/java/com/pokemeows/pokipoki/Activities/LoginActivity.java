package com.pokemeows.pokipoki.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pokemeows.pokipoki.Adapters.LoginFragmentPagerAdapter;
import com.pokemeows.pokipoki.Listeners.LoginActionListener;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Tools.AuthManager;
import com.pokemeows.pokipoki.Views.UnswipableViewPager;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginActionListener {

    private final static String TAG = LoginActivity.class.toString();

    private LoginFragmentPagerAdapter loginPagerAdapter;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AuthManager authManager;

    @BindView(R.id.login_pager) UnswipableViewPager loginPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        this.authManager = new AuthManager(this, firebaseAuth);

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

        authManager.createAccount(firebaseAuth, email, password, arguments, onCompleteListener);
    }

    @Override
    public void signIn(String email, String password,
                       final OnCompleteListener<AuthResult> onCompleteListener) {

        authManager.login(firebaseAuth, email, password, onCompleteListener);
    }
}
