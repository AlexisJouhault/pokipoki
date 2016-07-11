package com.pokemeows.pokipoki.apis;

import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexisjouhault on 7/10/16.
 */
public class PokemonTCGWrapper {

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
        Call call = getService().getAllCards();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

    public void getSetCards(final Callback<CardsResponse> thenDo, String setId) {
        Call call = getService().getSetCards(setId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

    public void getAllSets(final Callback<List<CardSet>> thenDo) {
        Call call = getService().getAllSets();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

}
