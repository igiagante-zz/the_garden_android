package com.example.igiagante.thegarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.network.HttpStatus;
import com.example.igiagante.thegarden.plants.getPlants.PlantListActivity;

import javax.inject.Inject;

/**
 * Created by igiagante on 18/4/16.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, PlantListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
