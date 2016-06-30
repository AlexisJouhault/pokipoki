package com.pokemeows.pokipoki.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pokemeows.pokipoki.fragments.login.LoginFragment;
import com.pokemeows.pokipoki.fragments.login.SignUpFragment;

/**
 * Created by alexisjouhault on 6/22/16.
 * ~~PokiPoki project~~
 */
public class LoginFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final int LOGIN_FRAGMENT_COUNT = 2;

    private LoginFragment loginFragment = new LoginFragment();
    private SignUpFragment signUpFragment = new SignUpFragment();

    public LoginFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == getLoginPos()) {
            return loginFragment;
        } else if (position == getSignUpPos()) {
            return signUpFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return LOGIN_FRAGMENT_COUNT;
    }

    public int getSignUpPos() {
        return signUpFragment.getPosition();
    }

    public int getLoginPos() {
        return loginFragment.getPosition();
    }


}
