package com.pokemeows.pokipoki.Fragments.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokemeows.pokipoki.R;

import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class CardsFragment extends TabFragment {

    public CardsFragment() {
        this.title = "Cards";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, mainView);
        return mainView;
    }
}
