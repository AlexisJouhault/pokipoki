package com.pokemeows.pokipoki.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.adapters.CardGridAdapter;
import com.pokemeows.pokipoki.events.CardRequest;
import com.pokemeows.pokipoki.events.SingleCardRequest;
import com.pokemeows.pokipoki.fragments.ScrollViewFragment;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardOptions;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;
import com.pokemeows.pokipoki.views.CardImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexisjouhault on 7/25/16.
 *
 */
public class ProfileCardsFragment extends ScrollViewFragment {

    private FirebaseUserWrapper userWrapper;

    private CardGridAdapter haveCardsAdapter;

    @BindView(R.id.profile_favourite_cards) TableLayout favouriteCardsTableLayout;
    @BindView(R.id.profile_cards_i_have) TableLayout cardsIHaveTableLayout;

    public ProfileCardsFragment() {
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, CARD_POSITION);
        this.setArguments(b);
        setTitle("Cards");
        this.userWrapper = CurrentUserInfo.getInstance().getFirebaseUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_cards, container, false);

        ButterKnife.bind(this, v);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mScrollView.setOnScrollChangedListener(this);

        setCardsUI();

        return v;
    }

    private void setCardsUI() {
        userWrapper.getCardsByOptions();
        List<Card> favouriteCards = new ArrayList<>();
        haveCardsAdapter = new CardGridAdapter(getActivity(), favouriteCards);
    }

    private final int CARD_PER_ROW = 3;
    private int favouriteCardPos = 0;
    private TableRow currentFavouriteCardsRow;
    private int haveCardsPos = 0;
    private TableRow currentHaveCardsRow;

    @Subscribe
    public void onSingleCardAdded(SingleCardRequest cardRequest) {
        int width = favouriteCardsTableLayout.getWidth() / 3;
        int height = (int) ((float) width * ((float) CardImageView.REAL_HEIGHT / (float) CardImageView.REAL_WIDTH));
        int totalPos = haveCardsPos + favouriteCardPos;
        switch (cardRequest.getOption()) {
            case CardOptions.FAVOURITE:
                if ((favouriteCardPos % CARD_PER_ROW) == 0) {
                    currentFavouriteCardsRow = new TableRow(getActivity());
                    favouriteCardsTableLayout.addView(currentFavouriteCardsRow);
                }
                haveCardsAdapter.addCard(cardRequest.getCard());
                currentFavouriteCardsRow.addView(haveCardsAdapter.getView(totalPos, null, null), width, height);
                favouriteCardPos++;
                break;
            case CardOptions.HAVE:
                if ((haveCardsPos % CARD_PER_ROW) == 0) {
                    currentHaveCardsRow = new TableRow(getActivity());
                    cardsIHaveTableLayout.addView(currentHaveCardsRow);
                }
                haveCardsAdapter.addCard(cardRequest.getCard());
                currentHaveCardsRow.addView(haveCardsAdapter.getView(totalPos, null, null), width, height);
                haveCardsPos++;
                break;
        }
    }

    @Subscribe
    public void onGetCardStarted(CardRequest request) {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
