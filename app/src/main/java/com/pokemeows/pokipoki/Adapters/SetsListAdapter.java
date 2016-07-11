package com.pokemeows.pokipoki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.tools.database.models.CardSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexisjouhault on 7/4/16.
 * ~~PokiPoki project~~
 */
public class SetsListAdapter extends BaseAdapter {

    private List<CardSet> cardSets;
    private Context context;

    public SetsListAdapter(Context context, List<CardSet> sets) {
        this.context = context;
        this.cardSets = sets;
    }

    @Override
    public int getCount() {
        return cardSets.size();
    }

    @Override
    public Object getItem(int position) {
        return cardSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listelem_set, parent, false);
        }
        generateSetView(convertView, cardSets.get(position));
        return convertView;
    }

    public void generateSetView(View setView, CardSet cardSet) {
        TextView name = (TextView) setView.findViewById(R.id.set_name);
        TextView totalCards = (TextView) setView.findViewById(R.id.set_total_cards);

        name.setText(cardSet.getName());
        String cardsString = "Cards : " + cardSet.getTotalCards();
        totalCards.setText(cardsString);
    }
}
