package com.pokemeows.pokipoki.Views;

import android.view.View;

/**
 * Created by alexisjouhault on 5/21/16.
 * ~~PokiPoki project~~
 */
public interface BaseView {

    ViewInfo getViewInfo();
    void setUpUIContent(int position);
    View getView(int position);
    boolean isRemoved();
}
