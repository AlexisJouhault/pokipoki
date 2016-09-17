package com.pokemeows.pokipoki.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import com.pokemeows.pokipoki.fragments.ScrollTabHolderFragment;
import com.pokemeows.pokipoki.fragments.ScrollViewFragment;
import com.pokemeows.pokipoki.fragments.profile.ProfileCardsFragment;
import com.pokemeows.pokipoki.fragments.profile.ProfileContactFragment;
import com.pokemeows.pokipoki.tools.ScrollTabHolder;
import com.pokemeows.pokipoki.fragments.profile.ProfileUserInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 6/28/16.
 * ~~PokiPoki project~~
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private List<ScrollViewFragment> profileFragments = new ArrayList<>();
    private ScrollTabHolder mListener;

    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
        profileFragments.add(new ProfileUserInfoFragment());
        profileFragments.add(new ProfileCardsFragment());
        profileFragments.add(new ProfileContactFragment());
        mScrollTabHolders = new SparseArrayCompat<>();
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return profileFragments.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return profileFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        ScrollTabHolderFragment fragment = profileFragments.get(position);
        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }
        return fragment;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
