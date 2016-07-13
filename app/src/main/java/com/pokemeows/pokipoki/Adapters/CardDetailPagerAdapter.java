package com.pokemeows.pokipoki.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.tools.database.models.Card;

import java.util.List;

import ooo.oxo.library.widget.TouchImageView;

/**
 * Created by alexisjouhault on 7/12/16.
 *
 */
public class CardDetailPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Card> cards;

    public CardDetailPagerAdapter(Context context, List<Card> cards) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.pagerelem_card_detail, container, false);

        Card card = cards.get(position);

        TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.card_image);
        Glide.with(context)
                .load(card.getImageUrl())
                .placeholder(R.drawable.pokemon_back)
                .into(imageView);


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
