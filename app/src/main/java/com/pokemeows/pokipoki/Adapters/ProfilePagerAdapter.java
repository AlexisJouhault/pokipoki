package com.pokemeows.pokipoki.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by alexisjouhault on 6/28/16.
 * ~~PokiPoki project~~
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
