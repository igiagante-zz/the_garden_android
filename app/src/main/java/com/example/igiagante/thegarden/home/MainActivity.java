package com.example.igiagante.thegarden.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.domain.entity.Garden;
import com.example.igiagante.thegarden.core.domain.entity.Plant;
import com.example.igiagante.thegarden.core.domain.entity.SensorTemp;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.creation.nutrients.presentation.NutrientActivity;
import com.example.igiagante.thegarden.creation.plants.presentation.CreatePlantActivity;
import com.example.igiagante.thegarden.home.di.DaggerMainComponent;
import com.example.igiagante.thegarden.home.di.MainComponent;
import com.example.igiagante.thegarden.home.gardens.presentation.adapters.GardenViewPagerAdapter;
import com.example.igiagante.thegarden.home.gardens.presentation.adapters.NavigationGardenAdapter;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateButtonAddGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.delegates.AdapterDelegateGarden;
import com.example.igiagante.thegarden.home.gardens.presentation.presenters.GardenPresenter;
import com.example.igiagante.thegarden.home.gardens.presentation.view.GardenView;
import com.example.igiagante.thegarden.home.gardens.presentation.viewTypes.ViewTypeGarden;
import com.example.igiagante.thegarden.home.irrigations.IrrigationDetailActivity;
import com.example.igiagante.thegarden.home.irrigations.presentation.fragments.IrrigationsFragment;
import com.example.igiagante.thegarden.home.plants.holders.PlantHolder;
import com.example.igiagante.thegarden.home.plants.presentation.PlantsAdapter;
import com.example.igiagante.thegarden.home.plants.presentation.dataHolders.GardenHolder;
import com.example.igiagante.thegarden.home.plants.services.EmailProducerService;
import com.example.igiagante.thegarden.login.LoginActivity;
import com.example.igiagante.thegarden.login.fragments.LoginFragment;
import com.google.android.gms.analytics.HitBuilders;

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
        AdapterDelegateGarden.OnClickGardenListener,
        ViewPager.OnPageChangeListener, PlantsAdapter.OnSendEmail {

    /**
     * Used to handle intent which starts the activity {@link CreatePlantActivity}
     */
    public static final int REQUEST_CODE_CREATE_PLANT_ACTIVITY = 2345;

    /**
     * Used to handle request permissions
     */
    public final static int REQUEST_CODE_ASK_PERMISSIONS = 768;

    public static final String GARDEN_KEY = "GARDEN";
    private static final String GARDENS_KEY = "GARDENS";

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainComponent mainComponent;

    private TabLayout tabLayout;
    private ViewPager mainViewPager;
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

    @Bind(R.id.progress_bar_garden)
    ProgressBar progressBar;

    @Bind(R.id.add_main_button)
    FloatingActionButton fab;

    /**
     * Save garden's position from Garden, which should be deleted
     */
    private int editGardenPosition;

    /**
     * The garden that is tried to be persisted or updated.
     */
    private GardenHolder garden;

    /**
     * List of gardens for the {@link NavigationGardenAdapter}
     */
    private ArrayList<GardenHolder> gardens = new ArrayList<>();

    /**
     * Used to send an email with attachments
     */
    private EmailProducerService emailProducerService;

    private SearchView searchView;

    /**
     * This menuItem is taken out because it needs to hide the searchView
     */
    private MenuItem menuItem;

    /**
     * BroadcastReceiver to check internet state connection
     */
    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

                if (ni != null && ni.isConnectedOrConnecting()) {
                    Log.i(TAG, "Network " + ni.getTypeName() + " connected");
                    fab.setEnabled(true);
                } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                    Log.d(TAG, "There's no network connectivity");
                    fab.setEnabled(false);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        initializeInjector();
        getComponent().inject(this);

        this.registerReceiver(this.networkStateReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // set view for this presenter
        this.mGardenPresenter.setView(new WeakReference<>(this));

        setupToolbar();

        mainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tracker.setScreenName(TAG);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Load gardens!
        if (savedInstanceState != null) {
            this.gardens = savedInstanceState.getParcelableArrayList(GARDENS_KEY);
            this.garden = savedInstanceState.getParcelable(GARDEN_KEY);
            loadGardens(this.gardens);
            loadGarden(this.garden);
        } else {
            mGardenPresenter.getGardensByUser(mSession.getUser());
        }

        this.mainViewPager.addOnPageChangeListener(this);

        initFAB();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GARDENS_KEY, gardens);
        outState.putParcelable(GARDEN_KEY, garden);
    }

    /**
     * Get Garden data from Intent
     *
     * @param garden Object
     */
    private void setActiveGarden(@NonNull GardenHolder garden) {
        int position = existGarden(garden);
        if (position != -1) {
            gardens.set(position, garden);
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
            if (!TextUtils.isEmpty(token)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LoginFragment.TOKEN_PREFS_NAME, "");
                editor.apply();
                this.mSession.cleanSession();
            }
            this.drawerLayout.closeDrawers();

            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.category_login))
                    .setAction(getString(R.string.action_logout))
                    .build());

            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadGardens(List<GardenHolder> gardens) {

        mNavigationGardenAdapter.setGardens(gardens);
        this.gardens = (ArrayList<GardenHolder>) gardens;

        if (!gardens.isEmpty() && this.garden == null) {
            this.garden = gardens.get(0);
        }

        setupViewPager();

        if (garden != null) {
            //add gardens to session's user
            mSession.getUser().setGardens(mGardenPresenter.createGardenListFromGardenHolderList(gardens));
            //get gardens temp and humidity
            this.mGardenPresenter.getActualTempAndHumidity();
            // load default garden
            loadGarden(this.garden);
        } else {
            this.mAdapter.createFirstGardenMessage();
            setGardenDefaultValues();
        }
    }

    private void setGardenDefaultValues() {
        TextView tempAndHumd = (TextView) findViewById(R.id.header_nav_temp_and_humd);
        tempAndHumd.setText(getString(R.string.header_nav_bar_temp_humd, "25", "50"));
        TextView numberOfPlants = (TextView) findViewById(R.id.header_nav_number_of_plants);
        numberOfPlants.setText(getString(R.string.header_nav_bar_number_of_plants, "0"));
    }

    private void setupViewPager() {
        if (this.garden != null) {
            mAdapter = new GardenViewPagerAdapter(getSupportFragmentManager(), this, garden.getModel());
        } else {
            mAdapter = new GardenViewPagerAdapter(getSupportFragmentManager(), this, null);
        }
        mainViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mainViewPager);
    }

    @Override
    public void loadGarden(@NonNull GardenHolder gardenHolder) {
        gardenHolder.setSelected(true);
        this.drawerLayout.closeDrawers();
        this.mAdapter.setGardenHolder(gardenHolder);
        setupNavigationHeaderData(gardenHolder.getModel());
        //get gardens temp and humidity
        this.mGardenPresenter.getActualTempAndHumidity();
    }

    @Override
    public void updateTemp(SensorTemp sensorTemp) {

        String temp = String.valueOf(sensorTemp.getTemp());
        String humd = String.valueOf(sensorTemp.getHumidity());

        TextView tempAndHumd = (TextView) findViewById(R.id.header_nav_temp_and_humd);
        tempAndHumd.setText(getString(R.string.header_nav_bar_temp_humd, temp, humd));
    }

    private void setupNavigationHeaderData(Garden garden) {
        TextView title = (TextView) findViewById(R.id.header_nav_garden_title);
        TextView numberOfPlants = (TextView) findViewById(R.id.header_nav_number_of_plants);

        title.setText(garden.getName());
        List<Plant> plants = garden.getPlants();

        if (plants != null && plants.isEmpty()) {
            numberOfPlants.setText(getString(R.string.header_nav_bar_number_of_plants, "0"));
        } else {
            numberOfPlants.setText(getString(R.string.header_nav_bar_number_of_plants, String.valueOf(plants.size())));
        }
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
        if (exists) {
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
    public void editPlant(PlantHolder plantHolder) {
        if (checkInternet()) {
            Intent intent = new Intent(this, CreatePlantActivity.class);
            intent.putExtra(GARDEN_KEY, garden.getModel());
            intent.putExtra(CreatePlantActivity.PLANT_KEY, plantHolder.getModel());
            startActivity(intent);
        } else {
            showMessageNoInternetConnection();
        }
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
        this.menuItem = menu.findItem(R.id.action_search);
        this.searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filterList(mainViewPager.getCurrentItem(), newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // DO Nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE_PLANT_ACTIVITY && resultCode == Activity.RESULT_OK) {
            GardenHolder garden = data.getParcelableExtra(GARDEN_KEY);
            setActiveGarden(garden);
        }

        if (requestCode == IrrigationsFragment.REQUEST_CODE_IRRIGATION_DETAIL && resultCode == Activity.RESULT_OK) {
            Garden garden = data.getParcelableExtra(GARDEN_KEY);
            GardenHolder gardenHolder = new GardenHolder();
            gardenHolder.setModel(garden);
            loadGarden(gardenHolder);
        }
    }

    @Override
    public void onPageSelected(int position) {
        fab.setVisibility(View.GONE);

        switch (position) {
            case 0:
                initFAB();
                break;
            case 1:
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(v -> startIrrigationDetailActivity());
                break;
            case 2:
                fab.setVisibility(View.GONE);
                break;
        }

        if (position != 0) {
            menuItem.setVisible(false);
        } else {
            menuItem.setVisible(true);
        }
    }

    private void initFAB() {
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v ->
                startActivityForResult(createIntentForCreatePlantActivity(),
                        MainActivity.REQUEST_CODE_CREATE_PLANT_ACTIVITY));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Intent createIntentForCreatePlantActivity() {
        Intent intent = new Intent(this, CreatePlantActivity.class);
        intent.putExtra(MainActivity.GARDEN_KEY, garden.getModel());
        return intent;
    }

    private void startIrrigationDetailActivity() {
        Intent intent = new Intent(this, IrrigationDetailActivity.class);
        intent.putExtra(MainActivity.GARDEN_KEY, garden.getModel());
        startActivityForResult(intent, IrrigationsFragment.REQUEST_CODE_IRRIGATION_DETAIL);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // DO Nothing
    }

    @Override
    public void sendEmail(String emailText, ArrayList<String> urls) {

        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.create_email));
        mProgressDialog.show();

        emailProducerService = new EmailProducerService(this, emailText, urls);
        emailProducerService.getNotificationEmailObservable().subscribe(sentEmail -> {
            if (sentEmail) {
                mProgressDialog.hide();
            }
        });

        if (checkInternet()) {
            this.progressBar.setVisibility(View.VISIBLE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkPermission();
            } else {
                emailProducerService.createAttachmentAndSendEmail();
            }
        } else {
            showMessageNoInternetConnection();
        }
    }

    /**
     * Check if the user has granted permissions to read and write storage
     */
    private void checkPermission() {

        int readExternalStoragePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED
                || writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            emailProducerService.createAttachmentAndSendEmail();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    emailProducerService.createAttachmentAndSendEmail();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }

    private void initializeInjector() {
        this.mainComponent = DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
