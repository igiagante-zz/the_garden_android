package com.example.igiagante.thegarden;

import android.os.Bundle;

import com.example.igiagante.thegarden.core.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by igiagante on 18/4/16.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
