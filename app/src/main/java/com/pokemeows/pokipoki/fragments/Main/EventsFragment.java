package com.pokemeows.pokipoki.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.activities.CreateEventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class EventsFragment extends TabFragment {

    public EventsFragment() {
        this.title = "Events";
    }

    @BindView(R.id.create_event) Button createEventButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.events_tab, container, false);
        ButterKnife.bind(this, mainView);

        //Button createEventButton = (Button) mainView.findViewById(R.id.create_event);
        //createEventButton.setVisibility(View.INVISIBLE);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                intent.putExtra("hello", "Hello create event");
                // Launcher for the activity
                startActivity(intent);
            }
        });

        return mainView;
    }
}
