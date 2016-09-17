package com.pokemeows.pokipoki.views;

import android.content.Context;

/**
 * Created by alexisjouhault on 5/21/16.
 * ~~PokiPoki project~~
 */
public class ViewInfo {

    private Context context;
    private int layoutId;

    public ViewInfo(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
}
