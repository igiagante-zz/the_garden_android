package com.example.igiagante.thegarden.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;
import com.example.igiagante.thegarden.core.presentation.BaseActivity;
import com.example.igiagante.thegarden.login.di.DaggerLoginComponent;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.fragments.LoginFragment;
import com.example.igiagante.thegarden.login.usecase.RefreshTokenUseCase;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class LoginActivity extends BaseActivity implements HasComponent<LoginComponent> {

    private LoginComponent loginComponent;

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initializeInjector();

        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        Fragment loginFragment = new LoginFragment();
        addFragment(R.id.login_container_data, loginFragment);
    }

    private void initializeInjector() {
        this.loginComponent = DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public void onBackPressed() {
    }
}
