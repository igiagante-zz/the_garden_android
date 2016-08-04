package com.example.igiagante.thegarden.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.presenters.LoginPresenter;
import com.example.igiagante.thegarden.login.view.LoginView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 4/8/16.
 */
public class RegisterFragment extends BaseFragment implements LoginView {

    @Inject
    LoginPresenter loginPresenter;

    @Bind(R.id.username_id)
    EditText mUserName;

    @Bind(R.id.password_id)
    EditText mPassword;

    @Bind(R.id.btn_signup)
    Button mButtonLogin;

    @Bind(R.id.link_login)
    Button mButtonSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(LoginComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        mButtonLogin.setOnClickListener(v -> createUser());

        return fragmentView;
    }

    private void createUser(){
        String username = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            loginPresenter.registerUser(user);
        } else {
            showToastMessage("Pass username and password please.");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loginPresenter.setView(new WeakReference<>(this));
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.loginPresenter.destroy();
    }

    @Override
    public void notifyUserRegistration(String result) {
        if(!result.equals("OK")) {
            showToastMessage(result);
        }
    }

    @Override
    public void notifyUserLogin(String result) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
