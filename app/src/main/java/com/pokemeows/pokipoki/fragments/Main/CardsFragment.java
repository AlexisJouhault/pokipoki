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
import android.widget.ImageView;
import android.widget.ListView;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.activities.CardDetailActivity;
import com.pokemeows.pokipoki.activities.SetActivity;
import com.pokemeows.pokipoki.adapters.SetsListAdapter;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.events.OpenActivityEvent;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.Card;
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
            CardSet set = (CardSet) parent.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(), SetActivity.class);
            int[] screenLocation = new int[2];
            view.getLocationOnScreen(screenLocation);

            //Pass the image title and url to DetailsActivity
            intent.putExtra("left", screenLocation[0]).
                    putExtra("top", screenLocation[1]).
                    putExtra("width", view.getWidth()).
                    putExtra("height", view.getHeight()).
                    putExtra("set", set);

            startActivity(intent);
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
