package com.pokemeows.pokipoki.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

/**
 * Created by alexisjouhault on 6/22/16.
 * ~~PokiPoki project~~
 */
public class BaseFragment extends Fragment {
    protected String TAG = "";
    protected View mainView;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "Attaching fragment");
        super.onAttach(context);
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }
}
