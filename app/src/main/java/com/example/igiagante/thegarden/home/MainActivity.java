package com.example.igiagante.thegarden.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.di.DaggerMainComponent;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.home.plants.presentation.PlantsAdapter;
import com.example.igiagante.thegarden.home.gardens.presentation.adapters.GardenViewPagerAdapter;
import com.example.igiagante.thegarden.home.gardens.presentation.adapters.NavigationGardenAdapter;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateButtonAddGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.presenters.GardenPresenter;
import com.example.igiagante.thegarden.home.gardens.presentation.view.GardenView;
import com.example.igiagante.thegarden.home.gardens.presentation.viewTypes.ViewTypeGarden;
import com.example.igiagante.thegarden.login.LoginActivity;
import com.example.igiagante.thegarden.login.fragments.LoginFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 18/4/16.
 */
public class MainActivity extends BaseActivity implements HasComponent<MainComponent>,
        PlantsAdapter.OnEditPlant,
        GardenView,
        AdapterDelegateButtonAddGarden.OnGardenDialog,
        AdapterDelegateGarden.OnClickGardenListener {

    /**
     * Used to handle intent which starts the activity {@link CreatePlantActivity}
     */
    public static final int REQUEST_CODE_CREATE_PLANT_ACTIVITY = 2345;

    public static final String GARDEN_KEY = "GARDEN";
    private static final String GARDENS_KEY = "GARDENS";

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainComponent mainComponent;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GardenViewPagerAdapter mAdapter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationGardenAdapter mNavigationGardenAdapter;

    @Inject
    GardenPresenter mGardenPresenter;

    @Inject
    Session mSession;

    @Inject
    SharedPreferences sharedPreferences;

    /**
     * RecycleView for garden list of the navigation drawer
     */
    @Bind(R.id.recycler_view_gardens)
    RecyclerView recyclerViewGardens;

    @Bind(R.id.fab_id)
    FloatingActionButton fab;

    /**
     * Save garden's position from Garden, which should be deleted
     */
    private int editGardenPosition;

    /**
     * The garden that is tried to be persisted or updated.
     */
    private GardenHolder garden;

    private ArrayList<GardenHolder> gardens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initializeInjector();
        getComponent().inject(this);

        if (savedInstanceState != null) {
            gardens = savedInstanceState.getParcelableArrayList(GARDENS_KEY);
        }

        // set view for this presenter
        this.mGardenPresenter.setView(new WeakReference<>(this));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // show FAB
        fab.setOnClickListener(view -> startActivity(new Intent(this, CreatePlantActivity.class)));

        setupToolbar();

        mAdapter = new GardenViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Load gardens!
        if(gardens != null) {
            mGardenPresenter.getGardens(mSession.getUser().getId());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GARDENS_KEY, gardens);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_CREATE_PLANT_ACTIVITY && resultCode == Activity.RESULT_OK) {
            setActiveGarden(data);
        }
    }

    /**
     * Get Garden data from Intent
     * @param intent Intent Object
     */
    private void setActiveGarden(Intent intent) {
        GardenHolder garden = intent.getParcelableExtra(GARDEN_KEY);
        if(garden != null) {
            int position = existGarden(garden);
            if(position != -1) {
                gardens.set(position, garden);
            }
        }
    }

    /**
     * Check if the garden is already on the list
     *
     * @param garden Garden Object
     * @return garden's position within list or -1 in case doesn't exist
     */
    private int existGarden(GardenHolder garden) {
        for (int i = 0; i < gardens.size(); i++) {
            if (gardens.get(i).getGardenId().equals(garden.getGardenId())) {
                return i;
            }
        }
        return -1;
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

        mNavigationGardenAdapter = new NavigationGardenAdapter(this, this, this, mSession);
        recyclerViewGardens = (RecyclerView) findViewById(R.id.recycler_view_gardens);
        this.recyclerViewGardens.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewGardens.setAdapter(mNavigationGardenAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_id);
        fab.setVisibility(View.INVISIBLE);

        //nutrients
        Button nutrientsOption = (Button) findViewById(R.id.nutrients_id);
        nutrientsOption.setOnClickListener(v -> {
            this.drawerLayout.closeDrawers();
            startActivity(new Intent(this, NutrientActivity.class));
        });

        //logout
        Button logout = (Button) findViewById(R.id.logout_id);
        logout.setOnClickListener(v -> {

            // Delete token from preferences
            String token = sharedPreferences.getString(LoginFragment.TOKEN_PREFS_NAME, "");
            if(!TextUtils.isEmpty(token)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginFragment.TOKEN_PREFS_NAME, "");
                editor.apply();
            }
            this.drawerLayout.closeDrawers();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    public void loadGardens(List<GardenHolder> gardens) {

        mNavigationGardenAdapter.setGardens(gardens);
        this.gardens = (ArrayList<GardenHolder>) gardens;

        if (!gardens.isEmpty() && this.garden == null) {
            this.garden = gardens.get(0);
        }

        if(garden != null) {
            // load default garden
            mAdapter.setGardenHolder(garden);
            //add gardens to session's user
            mSession.getUser().setGardens(mGardenPresenter.createGardenListFromGardenHolderList(gardens));
        }
    }

    @Override
    public void loadGarden(GardenHolder gardenHolder) {
        this.drawerLayout.closeDrawers();
        this.mAdapter.setGardenHolder(gardenHolder);
    }

    @Override
    public void showGardenDialog(int position) {
        this.editGardenPosition = position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        ViewTypeGarden gardenViewTypeText = (ViewTypeGarden) mNavigationGardenAdapter.getItem(editGardenPosition);
        Garden garden = new Garden();
        garden.setId(gardenViewTypeText.getId());
        garden.setName(gardenViewTypeText.getName());
        garden.setStartDate(gardenViewTypeText.getStartDate());
        garden.setPlants(gardenViewTypeText.getPlants());

        //add userId
        garden.setUserId(mSession.getUser().getId());

        switch (item.getItemId()) {
            case R.id.edit_plant:
                showEditGardenDialog(garden);
                break;
            case R.id.delete_plant:
                if (!garden.getPlants().isEmpty()) {
                    Toast.makeText(this, "The garden has plants. Remove first the plants", Toast.LENGTH_SHORT).show();
                } else {
                    mGardenPresenter.deleteGarden(garden);
                }

                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showEditGardenDialog(Garden garden) {
        View promptView = LayoutInflater.from(this).inflate(R.layout.add_garden_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.add_garden_dialog_enter_name);
        editText.setText(garden.getName());
        // setup a dialog window
        alertDialogBuilder
                .setPositiveButton("Yes", (dialog, which) -> {
                    garden.setName(editText.getText().toString());
                    mGardenPresenter.saveGarden(garden);
                })
                .setNegativeButton("No", null);

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void notifyIfGardenExists(boolean exists) {
        if(exists) {
            Toast.makeText(this, "The garden's name already exists. Try other please!",
                    Toast.LENGTH_SHORT).show();
        } else {
            mGardenPresenter.saveGarden(this.garden.getModel());
        }
    }

    @Override
    public void createGarden(Garden garden) {
        this.garden = new GardenHolder();
        this.garden.setModel(garden);
        mGardenPresenter.existsGarden(garden);
    }

    @Override
    public void getGarden(String gardenId) {
        mGardenPresenter.getGarden(gardenId);
    }

    @Override
    public void notifyIfGardenWasPersistedOrUpdated(Garden garden) {
        drawerLayout.closeDrawers();

        // update garden data in adapter menu
        mNavigationGardenAdapter.addOrUpdateGarden(garden);

        this.garden = mGardenPresenter.createGardenHolder(garden, gardens.size());

        if (!gardens.contains(this.garden)) {
            this.gardens.add(this.garden.getPosition(), this.garden);
        }

        loadGarden(this.garden);

        //add garden to user and update it
        addGardenToUser(garden);
    }

    private void addGardenToUser(Garden garden) {
        User user = mSession.getUser();
        user.getGardens().add(garden);
        // update gardensIds from user
        this.mGardenPresenter.updateUser(user);
    }

    @Override
    public void notifyIfUserWasUpdated(User user) {
        // update user's gardens after their ids were persisted
        this.mSession.getUser().setGardens(user.getGardens());
    }

    @Override
    public void notifyIfGardenWasDeleted() {
        drawerLayout.closeDrawers();

        // update adapter
        mNavigationGardenAdapter.removeGarden(editGardenPosition);
        mNavigationGardenAdapter.notifyDataSetChanged();

        // load the first garden after one is removed
        loadGarden(gardens.get(0));

        // update gardensIds from user
        removeGardenFromUser(this.gardens.get(editGardenPosition).getModel());

        // at the end it updates the model
        this.gardens.remove(editGardenPosition);
    }

    private void removeGardenFromUser(Garden garden) {
        User user = mSession.getUser();
        user.getGardens().remove(garden);
        // update gardensIds from user
        this.mGardenPresenter.updateUser(user);
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


    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    @Override
    public void editPlant(PlantHolder plantHolder) {
        Intent intent = new Intent(this, CreatePlantActivity.class);
        intent.putExtra(GARDEN_KEY, garden);
        intent.putExtra(CreatePlantActivity.PLANT_KEY, plantHolder.getModel());
        startActivity(intent);
    }

    private void initializeInjector() {
        this.mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
