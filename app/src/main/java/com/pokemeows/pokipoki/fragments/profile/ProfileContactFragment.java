package com.pokemeows.pokipoki.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.fragments.ScrollViewFragment;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;

import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 7/25/16.
 *
 */
public class ProfileContactFragment extends ScrollViewFragment {

    private FirebaseUserWrapper userWrapper;;

    public ProfileContactFragment() {
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, CONTACT_POSITION);
        this.setArguments(b);
        setTitle("Contact");
        this.userWrapper = CurrentUserInfo.getInstance().getFirebaseUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_contact, container, false);

        ButterKnife.bind(this, v);

        mScrollView.setOnScrollChangedListener(this);

        return v;
    }
}
