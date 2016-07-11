package com.pokemeows.pokipoki.activities;

import android.os.Build;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.adapters.CardGridAdapter;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.transitions.TransitionHelper;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetActivity extends AppCompatActivity {

    private final static String TAG = SetActivity.class.toString();

    private CardGridAdapter cardGridAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cards_grid) GridView cardsGridView;

    private ActionBarDrawerToggle toolbarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // if we transition the status and navigation bar we have to wait till everything is available
        TransitionHelper.fixSharedElementTransitionForStatusAndNavigationBar(this);
        // set a custom shared element enter transition
        TransitionHelper.setSharedElementEnterTransition(this, R.transition.set_activity_shared_element_enter_transition);

        String setId = getIntent().getStringExtra("setCode");
        if (setId != null) {
            populateCards(setId);
        } else {
            MessageDisplayer.showMessage(this, "Error getting set");
            finishInStyle();
        }
    }

    private void finishInStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    private void populateCards(String setId) {
        PokemonTCGWrapper pokemonTCGWrapper = new PokemonTCGWrapper();
        pokemonTCGWrapper.getSetCards(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                if (response.isSuccessful()) {
                    CardsResponse cards = response.body();
                    Collections.sort(cards.getCards());

                    cardGridAdapter = new CardGridAdapter(SetActivity.this, cards.getCards());
                    cardsGridView.setAdapter(cardGridAdapter);

                }
            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {
                MessageDisplayer.showMessage(SetActivity.this, t.getMessage());
            }
        }, setId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishInStyle();
        }
        return super.onOptionsItemSelected(item);
    }

}
