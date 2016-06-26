package com.pokemeows.pokipoki.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.pokemeows.pokipoki.Adapters.MainFragmentPagerAdapter;
import com.pokemeows.pokipoki.R;
import com.pokemeows.pokipoki.Singletons.CurrentUserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.toString();
    private FirebaseUser currentUser;
    private MainFragmentPagerAdapter fragmentPagerAdapter;
    private Drawer drawer;

    @BindView(R.id.fragment_viewpager) ViewPager fragmentViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        this.fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        this.fragmentViewPager.setAdapter(fragmentPagerAdapter);
        this.tabLayout.setupWithViewPager(fragmentViewPager);
        this.currentUser = CurrentUserInfo.getInstance().getFirebaseUser();

        try {
            setUpNavigationDrawer();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }

    private void setUpNavigationDrawer() throws Exception {
        this.drawer = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(false)
                .withAccountHeader(buildProfile())
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

    private AccountHeader buildProfile() {
        // Create the AccountHeader
        return new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Trainer")
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

    }
}
