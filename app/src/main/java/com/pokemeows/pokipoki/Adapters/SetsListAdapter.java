package com.pokemeows.pokipoki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

    public SetsListAdapter(Context context) {
        this.context = context;

        //remove test
        cardSets = new ArrayList<>();
        cardSets.add(new CardSet());
        cardSets.add(new CardSet());
        cardSets.add(new CardSet());
        cardSets.add(new CardSet());
        cardSets.add(new CardSet());
        cardSets.add(new CardSet());
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View setView = inflater.inflate(R.layout.listelem_set, parent, false);
        generateSetView(setView, cardSets.get(position));
        return setView;
    }

    public void generateSetView(View setView, CardSet cardSet) {

    }
}
