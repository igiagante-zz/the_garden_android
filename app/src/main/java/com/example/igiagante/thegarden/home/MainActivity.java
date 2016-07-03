package com.example.igiagante.thegarden.home;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.charts.presentation.ChartsFragment;
import com.example.igiagante.thegarden.home.irrigations.presentation.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.di.DaggerPlantComponent;
import com.example.igiagante.thegarden.home.plants.di.PlantComponent;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.home.plants.presentation.PlantListFragment;
import com.example.igiagante.thegarden.home.plants.presentation.PlantsAdapter;
import com.example.igiagante.thegarden.home.plants.presentation.adapters.NavigationGardenAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 18/4/16.
 */
public class  MainActivity extends BaseActivity implements HasComponent<PlantComponent>,
        PlantsAdapter.OnEditPlant {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PlantComponent plantComponent;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * RecycleView for garden list of the navigation drawer
     */
    @Bind(R.id.recycler_view_gardens)
    RecyclerView recyclerViewGardens;

    @Bind(R.id.fab_id)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initializeInjector();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // show FAB
        fab.setOnClickListener(view -> startActivity(new Intent(this, CreatePlantActivity.class)));

        setupToolbar();


    }

    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(mDrawerToggle);

        ArrayList<Garden> gardens = new ArrayList<>();
        gardens.add(new Garden("1", "Garden One"));
        gardens.add(new Garden("2", "Garden Two"));
        gardens.add(new Garden("3", "Garden Three"));
        gardens.add(new Garden("3", "Add new garden"));

        NavigationGardenAdapter adapter = new NavigationGardenAdapter(gardens);

        recyclerViewGardens = (RecyclerView) findViewById(R.id.recycler_view_gardens);

        this.recyclerViewGardens.setLayoutManager(new LinearLayoutManager(this));

        this.recyclerViewGardens.setAdapter(adapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_id);
        fab.setVisibility(View.INVISIBLE);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlantListFragment(), "Plants");
        adapter.addFragment(new IrrigationsFragment(), "Irrigations");
        adapter.addFragment(new ChartsFragment(), "Charts");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public PlantComponent getComponent() {
        return plantComponent;
    }

    @Override
    public void editPlant(PlantHolder plantHolder) {
        Intent intent = new Intent(this, CreatePlantActivity.class);
        intent.putExtra(CreatePlantActivity.PLANT_KEY, plantHolder.getModel());
        startActivity(intent);
    }

    private void initializeInjector() {
        this.plantComponent = DaggerPlantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
