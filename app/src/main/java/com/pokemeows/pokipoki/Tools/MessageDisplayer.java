package com.pokemeows.pokipoki.Tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class MessageDisplayer {

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showError(Context context, int error) {
        //add Reporting
        switch (error) {
            default:
                showMessage(context, "error");
        }
    }

}
