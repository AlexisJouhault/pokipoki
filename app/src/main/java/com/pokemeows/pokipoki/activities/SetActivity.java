package com.pokemeows.pokipoki.activities;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.adapters.CardGridAdapter;
import com.pokemeows.pokipoki.apis.PokemonTCGWrapper;
import com.pokemeows.pokipoki.tools.ActivityResults;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;
import com.pokemeows.pokipoki.tools.MessageDisplayer;
import com.pokemeows.pokipoki.tools.database.models.Card;
import com.pokemeows.pokipoki.tools.database.models.CardSet;
import com.pokemeows.pokipoki.tools.database.models.CardsResponse;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;
import com.pokemeows.pokipoki.transitions.TransitionHelper;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetActivity extends AppCompatActivity {

    private final static String TAG = SetActivity.class.toString();
    private static final long ANIM_DURATION = 300L;

    private CardSet set;

    private CardGridAdapter cardGridAdapter;
    private ColorDrawable colorDrawable;
    private float mWidthScale;
    private float mHeightScale;
    private int mLeftDelta;
    private int mTopDelta;
    private int thumbnailTop;
    private int thumbnailLeft;
    private int thumbnailWidth;
    private int thumbnailHeight;

    private ActionBarDrawerToggle toolbarDrawerToggle;
    private ProgressDialog progressDialog;
    private Bitmap cardBitmap;
    private boolean isDetailActivityOpen = false;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.cards_grid) GridView cardsGridView;
    @BindView(R.id.set_main_layout) RelativeLayout setMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        this.set = (CardSet) bundle.getSerializable("set");
        this.thumbnailTop = bundle.getInt("top");
        this.thumbnailLeft = bundle.getInt("left");
        this.thumbnailWidth = bundle.getInt("width");
        this.thumbnailHeight = bundle.getInt("height");

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(this.set.getName());
        }

        //Set the background color to black
        this.colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.light_grey));
        this.setMainLayout.setBackground(colorDrawable);

        this.progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);

        if (set != null) {
            if (!progressDialog.isShowing()) {
                showProgressDialog("Loading set...");
            }
            populateCards(set.getCode());
        } else {
            MessageDisplayer.showMessage(this, "Error getting set");
            finishInStyle();
        }

        if (savedInstanceState == null) {
            ViewTreeObserver observer = cardsGridView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    setMainLayout.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    setMainLayout.getLocationOnScreen(screenLocation);
                    mLeftDelta = thumbnailLeft - screenLocation[0];
                    mTopDelta = thumbnailTop - screenLocation[1];

                    // Scale factors to make the large version the same size as the thumbnail
                    mWidthScale = (float) thumbnailWidth / cardsGridView.getWidth();
                    mHeightScale = (float) thumbnailHeight / cardsGridView.getHeight();

                    enterAnimation();
                    return true;
                }
            });
        }
    }

    protected void showProgressDialog(String message) {
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void populateCards(String setId) {
        PokemonTCGWrapper pokemonTCGWrapper = new PokemonTCGWrapper();
        pokemonTCGWrapper.getSetCards(new Callback<CardsResponse>() {
            @Override
            public void onResponse(final Call<CardsResponse> call, Response<CardsResponse> response) {
                if (response.isSuccessful()) {
                    final CardsResponse cards = response.body();
                    Collections.sort(cards.getCards());

                    cardGridAdapter = new CardGridAdapter(SetActivity.this, cards.getCards());
                    cardsGridView.setAdapter(cardGridAdapter);

                    cardsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {


                            final Card card = (Card) parent.getItemAtPosition(position);
                            ImageView imageView = (ImageView) v.findViewById(R.id.card_image);
                            final int[] screenLocation = new int[2];
                            imageView.getLocationOnScreen(screenLocation);
                            final int width = imageView.getWidth();
                            final int height = imageView.getHeight();

                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... params) {
                                    if (!isDetailActivityOpen) {
                                        isDetailActivityOpen = true;
                                        if (Looper.myLooper() == null) {
                                            Looper.prepare();
                                        }

                                        Intent intent = new Intent(SetActivity.this, CardDetailActivity.class);
                                        //Pass the image title and url to DetailsActivity
                                        intent.putExtra("left", screenLocation[0]).
                                                putExtra("top", screenLocation[1]).
                                                putExtra("width", width).
                                                putExtra("height", height).
                                                putExtra("startingPosition", position).
                                                putExtra("cards", cards);

                                        startActivityForResult(intent, ActivityResults.OPEN_CARD_DETAIL);
                                    }
                                    return null;
                                }
                                @Override
                                protected void onPostExecute(Void dummy) {

                                }
                            }.execute();
                        }
                    });

                }
                if (progressDialog.isShowing()) {
                    progressDialog.hide();
                }
            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {
                MessageDisplayer.showMessage(SetActivity.this, t.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.hide();
                }
            }
        }, setId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActivityResults.OPEN_CARD_DETAIL) {
            isDetailActivityOpen = false;
        }
    }

    private void finishInStyle() {
        progressDialog.dismiss();
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishInStyle();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The enter animation scales the picture in from its previous thumbnail
     * size/location.
     */
    public void enterAnimation() {

        // Set starting values for properties we're going to animate. These
        // values scale and position the full size version down to the thumbnail
        // size/location, from which we'll animate it back up
        setMainLayout.setPivotX(0);
        setMainLayout.setPivotY(0);
        setMainLayout.setScaleX(mWidthScale);
        setMainLayout.setScaleY(mHeightScale);
        setMainLayout.setTranslationX(mLeftDelta);
        setMainLayout.setTranslationY(mTopDelta);

        // interpolator where the rate of change starts out quickly and then decelerates.
        TimeInterpolator sDecelerator = new DecelerateInterpolator();

        // Animate scale and translation to go from thumbnail to full size
        setMainLayout.animate().setDuration(ANIM_DURATION).scaleX(1).scaleY(1).
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
        cardsGridView.setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setMainLayout.animate().setDuration(ANIM_DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // Fade out background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(ANIM_DURATION);
        bgAnim.start();
    }

    @Override
    public void onBackPressed() {
        finishInStyle();
    }

}
