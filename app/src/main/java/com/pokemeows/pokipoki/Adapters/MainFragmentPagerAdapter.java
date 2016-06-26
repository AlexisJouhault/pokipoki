package com.pokemeows.pokipoki.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pokemeows.pokipoki.Fragments.Main.CardsFragment;
import com.pokemeows.pokipoki.Fragments.Main.EventsFragment;
import com.pokemeows.pokipoki.Fragments.Main.NewsFeedFragment;
import com.pokemeows.pokipoki.Fragments.Main.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    List<TabFragment> fragmentList = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList.add(new NewsFeedFragment());
        fragmentList.add(new EventsFragment());
        fragmentList.add(new CardsFragment());
    }

    public void addFragment(TabFragment fragment) {
        fragmentList.add(fragment);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < fragmentList.size()) {
            return fragmentList.get(position).getTitle();
        }
        return "Tab " + position;
    }
}
