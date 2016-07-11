package com.pokemeows.pokipoki.fragments.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.activities.SetActivity;
import com.pokemeows.pokipoki.adapters.SetsListAdapter;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.events.OpenActivityEvent;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.SetsResponse;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexisjouhault on 6/24/16.
 * ~~PokiPoki project~~
 */
public class CardsFragment extends TabFragment {

    private CurrentUserInfo currentUserInfo = CurrentUserInfo.getInstance();

    private SetsListAdapter setsListAdapter;

    @BindView(R.id.sets_list) ListView setsListView;

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
        mainView = inflater.inflate(R.layout.fragment_cards, container, false);
        ButterKnife.bind(this, mainView);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        populateSets();

        return mainView;
    }

    private void populateSets() {
        PokemonTCGWrapper pokemonTCGWrapper = new PokemonTCGWrapper();
        pokemonTCGWrapper.getAllSets(new Callback<SetsResponse>() {
            @Override
            public void onResponse(Call<SetsResponse> call, Response<SetsResponse> response) {
                if (response.isSuccessful()) {
                    SetsResponse setsResponse = response.body();
                    setsListAdapter = new SetsListAdapter(getActivity(), setsResponse.getSets());
                    setsListView.setAdapter(setsListAdapter);
                    setsListView.setOnItemClickListener(onOpenSet);
                } else {
                    MessageDisplayer.showError(getActivity(), response.code());
                }
            }

            @Override
            public void onFailure(Call<SetsResponse> call, Throwable t) {
                MessageDisplayer.showMessage(getActivity(), t.getMessage());
            }
        });
    }

    private AdapterView.OnItemClickListener onOpenSet = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), SetActivity.class);
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                EventBus.getDefault().post(new OpenActivityEvent());

                Intent i = new Intent(getActivity(), SetActivity.class);
                i.putExtra("setCode", ((CardSet) setsListAdapter.getItem(position)).getCode());

                Pair[] transitionPairs = new Pair[4];
                transitionPairs[0] = Pair.create(getActivity().findViewById(R.id.toolbar), "toolbar"); // Transition the Toolbar
                transitionPairs[1] = Pair.create(view, "content_area"); // Transition the content_area (This will be the content area on the detail screen)

                // We also want to transition the status and navigation bar barckground. Otherwise they will flicker
                transitionPairs[2] = Pair.create(getActivity().findViewById(android.R.id.statusBarBackground), Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
                transitionPairs[3] = Pair.create(getActivity().findViewById(android.R.id.navigationBarBackground), Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
                Bundle b = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), transitionPairs).toBundle();

                ActivityCompat.startActivity(getActivity(), i, b);
            }
        }
    };

    @Subscribe
    public void onCardsUpdated(String cards) {
        Log.d(TAG, "Cards : " + cards);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
