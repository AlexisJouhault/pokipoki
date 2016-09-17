package com.pokemeows.pokipoki.activities;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.adapters.CardDetailPagerAdapter;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardOptions;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;
import com.pokemeows.pokipoki.views.ViewPagerFixed;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ooo.oxo.library.widget.TouchImageView;

public class CardDetailActivity extends AppCompatActivity {

    private static final long ANIM_DURATION = 300L;
    private List<Card> cards;
    private int startingPosition = 0;
    private CardDetailPagerAdapter cardAdapter;
    private FirebaseUserWrapper userWrapper;

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
    @BindView(R.id.cards_pager) ViewPagerFixed cardViewPager;
    @BindView(R.id.card_detail_options) LinearLayout cardOptions;
    @BindView(R.id.card_favourite_ico) ImageView cardFavourite;
    @BindView(R.id.card_has) CheckBox cardHas;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeCardOptions(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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
        Bitmap cardBitmap = (Bitmap) bundle.get("cardBitmap");
        CardsResponse cardsResponse = (CardsResponse) bundle.getSerializable("cards");
        if (cardsResponse != null) {
            this.cards = cardsResponse.getCards();
        } else {
            MessageDisplayer.showMessage(this, "Error loading images");
            finish();
        }
        this.userWrapper = CurrentUserInfo.getInstance().getFirebaseUser();
        this.startingPosition = bundle.getInt("startingPosition");
        changeCardOptions(this.startingPosition);
        this.thumbnailTop = bundle.getInt("top");
        this.thumbnailLeft = bundle.getInt("left");
        this.thumbnailWidth = bundle.getInt("width");
        this.thumbnailHeight = bundle.getInt("height");

        //Set the background color to black
        this.colorDrawable = new ColorDrawable(Color.BLACK);
        this.mainLayout.setBackground(colorDrawable);

        cardAdapter = new CardDetailPagerAdapter(this, cards);
        cardViewPager.setAdapter(cardAdapter);
        cardViewPager.setCurrentItem(startingPosition);
        cardViewPager.addOnPageChangeListener(onPageChangeListener);


        if (savedInstanceState == null) {
            ViewTreeObserver observer = cardViewPager.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    cardViewPager.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    cardViewPager.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / cardViewPager.getWidth();
                    mHeightScale = (float) thumbnailHeight / cardViewPager.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }

    }

    @OnClick(R.id.card_favourite_ico)
    public void onFavouriteClick() {
        userWrapper.addFavouriteCard(cards.get(cardViewPager.getCurrentItem()));
        changeCardOptions(cardViewPager.getCurrentItem());
    }

    @OnClick(R.id.card_has)
    public void onHaveCardClick() {
        userWrapper.addHaveCard(cards.get(cardViewPager.getCurrentItem()));
        changeCardOptions(cardViewPager.getCurrentItem());
    }

    @OnClick(R.id.card_want)
    public void onCardWantClick() {
        userWrapper.addWantCard(cards.get(cardViewPager.getCurrentItem()));
        changeCardOptions(cardViewPager.getCurrentItem());
    }

    private void changeCardOptions(int position) {
        Card card = cards.get(position);
        int cardOptions = userWrapper.getCardOption(card.getId());

        if (CardOptions.isOptionSelected(cardOptions, CardOptions.FAVOURITE)) {
            Glide.with(CardDetailActivity.this)
                    .load(R.drawable.ic_favorite_white_48dp)
                    .into(cardFavourite);
        } else {
            Glide.with(CardDetailActivity.this)
                    .load(R.drawable.ic_favorite_border_white_48dp)
                    .into(cardFavourite);

        }
        if (CardOptions.isOptionSelected(cardOptions, CardOptions.HAVE)) {
            cardHas.setChecked(true);
        } else {
            cardHas.setChecked(false);
        }
        if (CardOptions.isOptionSelected(cardOptions, CardOptions.WANT)) {

        } else {

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
        cardViewPager.setPivotX(0);
        cardViewPager.setPivotY(0);
        cardViewPager.setScaleX(mWidthScale);
        cardViewPager.setScaleY(mHeightScale);
        cardViewPager.setTranslationX(mLeftDelta);
        cardViewPager.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        cardViewPager.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);
        cardOptions.setAlpha(0f);
        cardOptions.animate().setDuration(ANIM_DURATION).alpha(1);

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
        mainLayout.animate().setDuration(ANIM_DURATION).alpha(0).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();

    }

    @Override
    protected void onPause() {
        userWrapper.saveChanges();
        super.onPause();
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
