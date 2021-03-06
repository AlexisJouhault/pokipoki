package com.pokemeows.pokipoki.tools.database.models;

import com.orm.SugarRecord;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.events.SingleCardRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexisjouhault on 6/27/16.
 * ~~PokiPoki project~~
 */
public class UserInfo extends SugarRecord {

    private String userId;
    private String displayName;
    private String email;
    private int profileResource;
    private HashMap<String, Integer> userCardsOptions = new HashMap<>();
    /***
     * Data for cards : {setId}:{cardId}:{status};...
     * status : 0 - 7
     * 0 = none, 1 = favourite, 2 = have, 3 = have + favourite, 4 = want, 5 = want + favourite, 6 = want + have (if want multiple), 7 = want + have + favourite
     */
    private String cards;

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProfileResource() {
        return profileResource;
    }

    public void setProfileResource(int profileResource) {
        this.profileResource = profileResource;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public void processCardsInfo() {
        String[] allCardsInfo = cards.split(";");
        for (String cardInfo : allCardsInfo) {
            String[] splitCardInfo = cardInfo.split(":");
            if (splitCardInfo.length > 1) {
                String cardId = splitCardInfo[0];
                String cardOptions = splitCardInfo[1];
                userCardsOptions.put(cardId, Integer.parseInt(cardOptions));
            }
        }

    }

    public void addCardOption(String id, int option) {
        //Check if map contains cardId
        if (userCardsOptions.containsKey(id)) {
            //if it does get options for this card
            int options = userCardsOptions.get(id);

            //check if option is already selected
            if (CardOptions.isOptionSelected(options, option)) {
                options -= option;
                if (options == 0) {
                    //remove card from map if no options left
                    userCardsOptions.remove(id);
                } else {
                    //update card options
                    userCardsOptions.put(id, options);
                }
            } else {
                //add new option
                options += option;
                userCardsOptions.put(id, options);
            }

        } else {
            //if it doesn't add new option for this card
            userCardsOptions.put(id, option);
        }
    }

    public HashMap<String, Integer> getUserCardsOptions() {
        return userCardsOptions;
    }

    public String getFormattedOptions() {
        String formattedOptions = "";

        for (String cardId : userCardsOptions.keySet()) {
            int options = userCardsOptions.get(cardId);
            formattedOptions += cardId + ":" + options + ";";
        }

        return formattedOptions;
    }

    public void getCardsWithOption(final int option) {

        for (String cardId : userCardsOptions.keySet()) {
            int options = userCardsOptions.get(cardId);

            if (CardOptions.isOptionSelected(options, option)) {
                PokemonTCGWrapper tcgWrapper = new PokemonTCGWrapper();
                tcgWrapper.getCard(cardId, new Callback<SingleCardResponse>() {
                    @Override
                    public void onResponse(Call<SingleCardResponse> call, Response<SingleCardResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            EventBus.getDefault().post(new SingleCardRequest(response.body().getCard(), option));
                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCardResponse> call, Throwable t) {
                    }
                });
            }
        }
    }

    public void getCardsByOptions() {
        for (String cardId : userCardsOptions.keySet()) {
            final int options = userCardsOptions.get(cardId);

            PokemonTCGWrapper tcgWrapper = new PokemonTCGWrapper();
            tcgWrapper.getCard(cardId, new Callback<SingleCardResponse>() {
                @Override
                public void onResponse(Call<SingleCardResponse> call, Response<SingleCardResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (CardOptions.isOptionSelected(options, CardOptions.FAVOURITE)) {
                            EventBus.getDefault().post(new SingleCardRequest(response.body().getCard(), CardOptions.FAVOURITE));
                        }
                        if (CardOptions.isOptionSelected(options, CardOptions.WANT)) {
                            EventBus.getDefault().post(new SingleCardRequest(response.body().getCard(), CardOptions.WANT));
                        }
                        if (CardOptions.isOptionSelected(options, CardOptions.HAVE)) {
                            EventBus.getDefault().post(new SingleCardRequest(response.body().getCard(), CardOptions.HAVE));
                        }
                    }
                }

                @Override
                public void onFailure(Call<SingleCardResponse> call, Throwable t) {
                }
            });
        }
    }
}
