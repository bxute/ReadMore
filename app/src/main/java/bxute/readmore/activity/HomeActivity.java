package bxute.readmore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.InjectView;
import bxute.readmore.R;
import bxute.readmore.adapters.BooksPagerAdapter;
import bxute.readmore.data.DatabaseHelper;
import bxute.readmore.fragments.NavigationFragment;
import bxute.readmore.interfaces.OnNavigationItemClickListener;
import bxute.readmore.preference.PreferenceManager;

public class HomeActivity extends AppCompatActivity implements OnNavigationItemClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.left_drawer)
    FrameLayout leftDrawer;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        initializeViews();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionNotification:
                transitToSearch();
                break;
        }
        return true;
    }

    private void transitToSearch() {

        Intent notificationsPageIntent = new Intent(this, SearchActivity.class);
        startActivity(notificationsPageIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void initializeViews() {

        setSupportActionBar(toolbar);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        setUpDrawer();

    }

    private void setupViewPager(ViewPager viewpager) {
        BooksPagerAdapter booksPagerAdapter = new BooksPagerAdapter(this,getSupportFragmentManager());
        viewpager.setAdapter(booksPagerAdapter);
    }

    private void setUpDrawer() {

        if (drawerLayout != null) {

            mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.open,
                    R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {
                    // Set the title on the action when drawer open
                    getSupportActionBar().setTitle(R.string.action_bar_title);
                    super.onDrawerOpened(drawerView);
                }
            };

            drawerLayout.setDrawerListener(mDrawerToggle);

        }

        NavigationFragment navigationFragment = new NavigationFragment();
        navigationFragment.setOnNavigationItemClickListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.left_drawer, navigationFragment)
                .commit();

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onItemClicked(int id) {

        if (id == OnNavigationItemClickListener.FAVORITE) {
            Intent detailsIntent = new Intent(this, FavoriteActivity.class);
            Bundle bundle = new Bundle();
            detailsIntent.putExtras(bundle);
            startActivity(detailsIntent);

        } else {
            mAuth.signOut();
            new DatabaseHelper(this).clearDB();
            new PreferenceManager(this).clearPreference();
            Intent detailsIntent = new Intent(this, LoginActivity.class);
            startActivity(detailsIntent);
            finish();
        }
    }
}
