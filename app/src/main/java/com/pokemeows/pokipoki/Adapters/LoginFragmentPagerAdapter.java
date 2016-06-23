package com.pokemeows.pokipoki.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pokemeows.pokipoki.Fragments.LoginFragment;
import com.pokemeows.pokipoki.Fragments.SignupFragment;

/**
 * Created by alexisjouhault on 6/22/16.
 * ~~PokiPoki project~~
 */
public class LoginFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final int LOGIN_FRAGMENT_COUNT = 2;

    private LoginFragment loginFragment = new LoginFragment();
    private SignupFragment signupFragment = new SignupFragment();

    public LoginFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == getLoginPos()) {
            return loginFragment;
        } else if (position == getSignUpPos()) {
            return signupFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return LOGIN_FRAGMENT_COUNT;
    }

    public int getSignUpPos() {
        return signupFragment.getPosition();
    }

    public int getLoginPos() {
        return loginFragment.getPosition();
    }


}
