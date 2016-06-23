package com.pokemeows.pokipoki.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.pokemeows.pokipoki.Adapters.LoginFragmentPagerAdapter;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Fragments.SignupFragment;
import com.pokemeows.pokipoki.Views.UnswipableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private LoginFragmentPagerAdapter loginPagerAdapter;

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

    public void showSignUpFragment() {
        loginPager.setCurrentItem(loginPagerAdapter.getSignUpPos());
    }

    public void showLoginFragment() {
        loginPager.setCurrentItem(loginPagerAdapter.getLoginPos());

    }
}
