package com.example.igiagante.thegarden;

import android.os.Bundle;
import android.widget.TextView;

import com.example.igiagante.thegarden.core.AndroidApplication;
import com.example.igiagante.thegarden.core.activity.BaseActivity;
import com.example.igiagante.thegarden.core.network.HttpStatus;

import javax.inject.Inject;

/**
 * Created by igiagante on 18/4/16.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mBody;

    @Inject
    public HttpStatus httpStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AndroidApplication) getApplication()).getApplicationComponent().inject(this);

        mBody = (TextView) findViewById(R.id.text_id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
