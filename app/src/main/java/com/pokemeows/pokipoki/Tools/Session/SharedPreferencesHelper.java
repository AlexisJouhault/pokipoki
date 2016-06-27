package com.pokemeows.pokipoki.Tools.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alexisjouhault on 6/25/16.
 * ~~PokiPoki project~~
 */
public class SharedPreferencesHelper {

    private static SharedPreferencesHelper sharedPreferenceHelper = new SharedPreferencesHelper();
    private static String USER_KEY = "user";

    private SharedPreferencesHelper() {
    }

    public static SharedPreferencesHelper getInstance() {
        return sharedPreferenceHelper;
    }

    public static String getUser(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(USER_KEY, "");
    }

    public static void saveUser(Context context, String user) {
        if (user != null && !user.equals("")) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString(USER_KEY, user);
            editor.apply();
        }
    }

}
