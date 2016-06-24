package com.pokemeows.pokipoki;

import android.app.Application;
import android.util.Log;

import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 6/22/16.
 */
public class Poki extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application", "Launching Poki");

        ButterKnife.setDebug(true);
    }

}
