package com.pokemeows.pokipoki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.tools.database.models.Card;

import java.util.List;

/**
 * Created by alexisjouhault on 7/10/16.
 *
 */
public class CardGridAdapter extends BaseAdapter {

    private List<Card> cards;
    private Context context;

    public CardGridAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridelem_card, parent, false);
        }
        generateCardView(convertView, cards.get(position));
        return convertView;
    }

    private void generateCardView(View cardView, Card card) {
        ImageView cardImage = (ImageView) cardView.findViewById(R.id.card_image);
        Glide.with(context)
                .load(card.getImageUrl())
                .placeholder(R.drawable.pokemon_back)
                .into(cardImage);
    }

    public List<Card> getCards() {
        return cards;
    }
}
