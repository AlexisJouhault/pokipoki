package com.pokemeows.pokipoki.apis;

import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alexisjouhault on 7/10/16.
 */
public interface PokemonTCGService {

    @GET("cards")
    Call<CardsResponse> getAllCards();

    @GET("cards")
    Call<CardsResponse> getSetCards(
            @Query("set") String setId);

    @GET("sets")
    Call<List<CardSet>> getAllSets();
}
