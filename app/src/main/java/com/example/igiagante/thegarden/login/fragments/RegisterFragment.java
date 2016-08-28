package com.example.igiagante.thegarden.login.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.login.RegisterActivity;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.presenters.RegisterPresenter;
import com.example.igiagante.thegarden.login.view.RegisterView;
import com.google.android.gms.analytics.HitBuilders;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 4/8/16.
 */
public class RegisterFragment extends BaseFragment implements RegisterView {

    @Inject
    RegisterPresenter registerPresenter;

    @Bind(R.id.register_email_id)
    EditText mUserEmail;

    @Bind(R.id.register_password_id)
    EditText mPassword;

    @Bind(R.id.btn_signup)
    Button mButtonSignUp;

    @Bind(R.id.link_login)
    TextView mButtonLogin;

    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(LoginComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        mButtonSignUp.setOnClickListener(v -> {
            if(checkInternet()) {
                signup();
            } else {
                showToastMessage(getString(R.string.there_is_not_internet_connection));
            }
        });

        mButtonLogin.setOnClickListener(v -> getActivity().finish());

        return fragmentView;
    }

    private void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mButtonSignUp.setEnabled(false);

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.creating_account));
        mProgressDialog.show();

        createUser();
    }

    private void onSignupFailed() {
        showToastMessage(getString(R.string.login_failed));
        mButtonSignUp.setEnabled(true);
    }

    private void createUser() {
        String username = mUserEmail.getText().toString();
        String password = mPassword.getText().toString();

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        registerPresenter.registerUser(user);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.registerPresenter.setView(new WeakReference<>(this));
        mProgressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mProgressDialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.registerPresenter.destroy();
    }

    @Override
    public void notifyUserRegistration(String result) {
        mProgressDialog.hide();
        mButtonSignUp.setEnabled(true);
        if (!result.equals(getString(R.string.login_ok))) {
            showToastMessage(result);
        } else {

            RegisterActivity activity = (RegisterActivity) getActivity();
            activity.getTracker().send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.category_register))
                    .setAction(getString(R.string.action_new_user_registered))
                    .build());

            getActivity().finish();
            getActivity().startActivity(new Intent(getContext(), MainActivity.class));
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private boolean validate() {
        boolean valid = true;

        String email = mUserEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mUserEmail.setError(getString(R.string.email_not_valid));
            valid = false;
        } else {
            mUserEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError(getString(R.string.check_email_characters));
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }
}
