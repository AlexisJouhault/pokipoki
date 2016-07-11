package com.pokemeows.pokipoki.activities;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.tools.database.models.Card;

import butterknife.BindView;
import butterknife.ButterKnife;
import ooo.oxo.library.widget.TouchImageView;

public class CardDetailActivity extends AppCompatActivity {

    private static final long ANIM_DURATION = 300L;
    private Card card;

    private ColorDrawable colorDrawable;
    private float mWidthScale;
    private float mHeightScale;
    private int mLeftDelta;
    private int mTopDelta;
    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    @BindView(R.id.card_detail_main_layout) RelativeLayout mainLayout;
    @BindView(R.id.card_image) TouchImageView cardImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Bundle bundle = getIntent().getExtras();
        this.card = (Card) bundle.getSerializable("card");
        this.thumbnailTop = bundle.getInt("top");
        this.thumbnailLeft = bundle.getInt("left");
        this.thumbnailWidth = bundle.getInt("width");
        this.thumbnailHeight = bundle.getInt("height");

        //Set the background color to black
        this.colorDrawable = new ColorDrawable(Color.BLACK);
        this.mainLayout.setBackground(colorDrawable);

        Glide.with(this)
                .load(card.getImageUrl())
                .placeholder(R.drawable.pokemon_back)
                .into(cardImageView);


        if (savedInstanceState == null) {
            ViewTreeObserver observer = cardImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    cardImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    cardImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / cardImageView.getWidth();
                    mHeightScale = (float) thumbnailHeight / cardImageView.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }

    }

    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location.
     */
    public void enterAnimation() {

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        cardImageView.setPivotX(0);
        cardImageView.setPivotY(0);
        cardImageView.setScaleX(mWidthScale);
        cardImageView.setScaleY(mHeightScale);
        cardImageView.setTranslationX(mLeftDelta);
        cardImageView.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        cardImageView.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();

    }

    /**
     * The exit animation is basically a reverse of the enter animation.
     * This Animate image back to thumbnail size/location as relieved from bundle.
     *
     * @param endAction This action gets run after the animation completes (this is
     *                  when we actually switch activities)
     */
    public void exitAnimation(final Runnable endAction) {

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        cardImageView.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }


}
