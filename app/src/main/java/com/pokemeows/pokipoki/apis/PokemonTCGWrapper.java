package com.pokemeows.pokipoki.apis;

import com.pokemeows.pokipoki.fragments.main.CardsFragment;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.tools.database.models.SetsResponse;
import com.pokemeows.pokipoki.tools.database.models.SingleCardResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexisjouhault on 7/10/16.
 *
 */
public class PokemonTCGWrapper {

    private static final String PAGE_SIZE = "1000";
    private final String HOST = "https://api.pokemontcg.io/v1/";
    private PokemonTCGService service = null;

    private PokemonTCGService getService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(PokemonTCGService.class);
        }
        return service;
    }

    public void getAllCards(final Callback<CardsResponse> thenDo) {
        Call<CardsResponse> call = getService().getAllCards();
        call.enqueue(thenDo);
    }

    public void getSetCards(final Callback<CardsResponse> thenDo, String setId) {
        Call<CardsResponse> call = getService().getSetCards(setId, PAGE_SIZE);
        call.enqueue(thenDo);
    }

    public void getAllSets(final Callback<SetsResponse> thenDo) {
        Call<SetsResponse> call = getService().getAllSets();
        call.enqueue(thenDo);
    }

    public void getCard(String cardId, final Callback<SingleCardResponse> thenDo) {
        Call<SingleCardResponse> call = getService().getCard(cardId);
        call.enqueue(thenDo);
    }

}
