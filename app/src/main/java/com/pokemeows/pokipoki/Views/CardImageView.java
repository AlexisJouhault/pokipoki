package com.pokemeows.pokipoki.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by alexisjouhault on 7/10/16.
 *
 */
public class CardImageView extends ImageView {

    public static final int REAL_HEIGHT = 880;
    public static final int REAL_WIDTH = 630;

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
        setMeasuredDimension(getMeasuredWidth(), (getMeasuredWidth() * REAL_HEIGHT / REAL_WIDTH)); //Snap to width
    }
}
