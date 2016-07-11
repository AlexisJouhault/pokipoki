package com.pokemeows.pokipoki.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by alexisjouhault on 7/10/16.
 *
 */
public class CardImageView extends ImageView {

    public CardImageView(Context context) {
        super(context);
    }

    public CardImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int realWidth = 630;
        int realHeight = 880;
        setMeasuredDimension(getMeasuredWidth(), (getMeasuredWidth() * realHeight / realWidth)); //Snap to width
    }
}
