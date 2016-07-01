package com.pokemeows.pokipoki.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.pokemeows.pokipoki.adapters.MainFragmentPagerAdapter;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.tools.database.Models.UserInfo;
import com.pokemeows.pokipoki.tools.DrawerTags;
import com.pokemeows.pokipoki.tools.FirebaseUserWrapper;
import com.pokemeows.pokipoki.tools.session.CurrentUserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.toString();
    private FirebaseUserWrapper currentUser;
    private MainFragmentPagerAdapter fragmentPagerAdapter;
    private Drawer drawer;
    private AccountHeader accountHeader;

    @BindView(R.id.fragment_viewpager) ViewPager fragmentViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private Drawer.OnDrawerItemClickListener itemClickListener = new Drawer.OnDrawerItemClickListener() {
        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            if (drawerItem.getTag().equals(DrawerTags.LOGOUT)) {
                Log.d(TAG, "Logging out");
                CurrentUserInfo.getInstance().logout();
                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                intent.putExtra("logout", true);
                startActivity(intent);
                finish();
            } else if (drawerItem.getTag().equals(DrawerTags.PROFILE)) {
                return onProfileClickListener.onProfileImageClick(view, accountHeader.getActiveProfile(), true);
            }
            return false;
        }
    };

    private AccountHeader.OnAccountHeaderProfileImageListener onProfileClickListener = new AccountHeader.OnAccountHeaderProfileImageListener() {
        @Override
        public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        @Override
        public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        this.fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        this.fragmentViewPager.setAdapter(fragmentPagerAdapter);
        this.tabLayout.setupWithViewPager(fragmentViewPager);
        this.currentUser = CurrentUserInfo.getInstance().getFirebaseUser();

        try {
            buildProfile();
            setUpNavigationDrawer();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }

    private void setUpNavigationDrawer() throws Exception {
        PrimaryDrawerItem homeItem = new PrimaryDrawerItem()
                .withIdentifier(1)
                .withName(R.string.home)
                .withIcon(R.drawable.ic_home_black)
                .withTag(DrawerTags.HOME);
        PrimaryDrawerItem profileItem = new PrimaryDrawerItem()
                .withIdentifier(2)
                .withName(R.string.profile)
                .withIcon(R.drawable.ic_profile_black)
                .withTag(DrawerTags.PROFILE);
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem()
                .withIdentifier(3)
                .withName(R.string.logout)
                .withIcon(R.drawable.ic_exit_black)
                .withTag(DrawerTags.LOGOUT);
        this.drawer = new DrawerBuilder()
                .withActivity(this)
                .withSelectedItem(-1)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withAccountHeader(accountHeader)
                .withOnDrawerItemClickListener(itemClickListener)
                .addDrawerItems(homeItem, profileItem, new DividerDrawerItem(), logoutItem)
                .build();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer.getDrawerLayout(), R.string.profile, R.string.profile);
        this.drawer.setActionBarDrawerToggle(drawerToggle);
        this.drawer.setToolbar(this, toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout mDrawerLayout = this.drawer.getDrawerLayout();
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mDrawerToggle.syncState();

    }

    private void buildProfile() {
        // Create the AccountHeader
        this.accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withOnAccountHeaderProfileImageListener(onProfileClickListener)
                .withAlternativeProfileHeaderSwitching(false)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withIdentifier(200)
                                .withEmail(currentUser.getEmail())
                                .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
    }

    @Subscribe
    public void onUserDataChanged(UserInfo userData) {
        this.accountHeader.updateProfile(new ProfileDrawerItem()
                .withIdentifier(200)
                .withName(currentUser.getName() != null ? currentUser.getName() : "Trainer")
                .withEmail(currentUser.getEmail())
                .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null))
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen()) {
            this.drawer.closeDrawer();
        } else {
            Log.d(TAG, "Handle back button");
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
