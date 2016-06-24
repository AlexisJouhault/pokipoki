package com.pokemeows.pokipoki.Fragments;

import android.app.ProgressDialog;

import com.pokemeows.pokipoki.R;

/**
 * Created by alexisjouhault on 6/22/16.
 * ~~PokiPoki project~~
 */
public class PagerFragment extends BaseFragment {

    private int position = 0;
    private ProgressDialog progressDialog;

    public final int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    protected void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.hide();
            progressDialog = null;
        }
    }
}
