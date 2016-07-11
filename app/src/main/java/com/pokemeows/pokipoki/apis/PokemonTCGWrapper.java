package com.pokemeows.pokipoki.apis;

import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.tools.database.models.SetsResponse;

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
        call.enqueue(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

    public void getSetCards(final Callback<CardsResponse> thenDo, String setId) {
        Call<CardsResponse> call = getService().getSetCards(setId);
        call.enqueue(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

    public void getAllSets(final Callback<SetsResponse> thenDo) {
        Call<SetsResponse> call = getService().getAllSets();
        call.enqueue(new Callback<SetsResponse>() {
            @Override
            public void onResponse(Call<SetsResponse> call, Response<SetsResponse> response) {
                thenDo.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<SetsResponse> call, Throwable t) {
                thenDo.onFailure(call, t);
            }
        });
    }

}
