package com.pokemeows.pokipoki.fragments.main;

import android.content.Context;

import com.pokemeows.pokipoki.activities.MainActivity;
import com.pokemeows.pokipoki.fragments.BaseFragment;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class TabFragment extends BaseFragment {

    protected MainActivity parentActivity;
    protected String title;

    @Override
    public void onAttach(Context context) {
        this.parentActivity = (MainActivity) context;
        super.onAttach(context);
    }

    public String getTitle() {
        return title;
    }
}
