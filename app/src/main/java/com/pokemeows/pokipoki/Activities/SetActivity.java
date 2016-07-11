package com.pokemeows.pokipoki.activities;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.transitions.TransitionHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetActivity extends AppCompatActivity {

    private final static String TAG = SetActivity.class.toString();

    @BindView(R.id.toolbar) Toolbar toolbar;

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

        String setId = "generations";
        populateCards(setId);
    }

    private void populateCards(String setId) {
        PokemonTCGWrapper pokemonTCGWrapper = new PokemonTCGWrapper();
        pokemonTCGWrapper.getSetCards(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                if (response.isSuccessful()) {
                    CardsResponse cards = response.body();
                    Log.d(TAG, "Just got the cards : " + cards.getCards().size());
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
