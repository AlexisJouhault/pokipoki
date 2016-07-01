package com.pokemeows.pokipoki.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.views.NotifyingScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollViewFragment extends ScrollTabHolderFragment implements NotifyingScrollView.OnScrollChangedListener {

    private static final String ARG_POSITION = "position";



    TextView title;
    TextView titleShortDescription;
    TextView titleDescription;
    TextView textSendEmail;
    TextView textContact;
    TextView textEmail;
    @BindView(R.id.layout1) LinearLayout layout1;
    @BindView(R.id.titleImage) ImageView titleImage;
    @BindView(R.id.scrollview) NotifyingScrollView mScrollView;
    @BindView(R.id.cardView) CardView cardView;

    private int mPosition;

    public static Fragment newInstance(int position) {
        ScrollViewFragment f = new ScrollViewFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scrollview, container, false);
        ButterKnife.bind(this, v);

        mScrollView.setOnScrollChangedListener(this);
        cardView.setPadding(30,30,30,30);


        if (mPosition==0){
            layout1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.pink_transparent));
            titleImage.setImageResource(R.drawable.poke);}
        if (mPosition==1) {
            layout1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green_transparent));
            titleImage.setImageResource(R.drawable.poke);
        }
        if (mPosition==2) {
            layout1.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blue_transparent));
            titleImage.setImageResource(R.drawable.poke);

        }


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void adjustScroll(int scrollHeight, int headerTranslationY)
    {
        mScrollView.setScrollY(headerTranslationY - scrollHeight);
    }

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt)
    {
        if (mScrollTabHolder != null)
            mScrollTabHolder.onScroll(view, l, t, oldl, oldt, mPosition);

    }



}
