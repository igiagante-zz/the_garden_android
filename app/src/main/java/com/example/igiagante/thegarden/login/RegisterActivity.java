package com.example.igiagante.thegarden.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.login.di.DaggerLoginComponent;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.fragments.RegisterFragment;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 4/8/16.
 */
public class RegisterActivity extends BaseActivity implements HasComponent<LoginComponent> {

    private LoginComponent loginComponent;

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();

        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);

        Fragment registerFragment = new RegisterFragment();
        addFragment(R.id.register_container_data, registerFragment);
    }

    private void initializeInjector() {
        this.loginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
