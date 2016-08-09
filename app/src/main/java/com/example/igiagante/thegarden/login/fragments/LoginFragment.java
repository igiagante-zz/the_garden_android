package com.example.igiagante.thegarden.login.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.presentation.BaseFragment;
import com.example.igiagante.thegarden.home.MainActivity;
import com.example.igiagante.thegarden.login.RegisterActivity;
import com.example.igiagante.thegarden.login.di.LoginComponent;
import com.example.igiagante.thegarden.login.presenters.LoginPresenter;
import com.example.igiagante.thegarden.login.view.LoginView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class LoginFragment extends BaseFragment implements LoginView {

    public static final String PREFS_NAME = "The_Garden_Preferences";
    private static final String TOKEN_PREFS_NAME = "token";

    @Inject
    Session session;

    @Inject
    LoginPresenter loginPresenter;

    @Bind(R.id.login_email_id)
    EditText mUserEmail;

    @Bind(R.id.login_password_id)
    EditText mPassword;

    @Bind(R.id.btn_login_id)
    Button mButtonLogin;

    @Bind(R.id.signup_id)
    TextView mButtonSignUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Get component in order to inject the presenter
         */
        this.getComponent(LoginComponent.class).inject(this);

        final View fragmentView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        // Restore preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString(TOKEN_PREFS_NAME, "");

        if(!TextUtils.isEmpty(token)){

            session.setToken(token);

            if(session.checkIfTokenIsExpired()){
                this.loginPresenter.refreshToken(this.session.getUser().getId());
            }
        }

        mButtonLogin.setOnClickListener(v -> loginUser());

        mButtonSignUp.setOnClickListener(v ->
                getActivity().startActivity(new Intent(getContext(), RegisterActivity.class)));

        return fragmentView;
    }

    private void loginUser() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mButtonLogin.setEnabled(false);

        String username = mUserEmail.getText().toString();
        String password = mPassword.getText().toString();

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        loginPresenter.loginUser(user);
    }

    private void onLoginFailed() {
        showToastMessage("Login Failed");
        mButtonLogin.setEnabled(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loginPresenter.setView(new WeakReference<>(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.loginPresenter.destroy();
    }

    @Override
    public void notifyUserLogin(String result) {
        if(!result.equals("OK")) {
            showToastMessage(result);
        } else {
            getActivity().startActivity(new Intent(getContext(), MainActivity.class));
        }
    }

    @Override
    public void sendNewToken(String token) {
        this.session.setToken(token);
        this.session.setTokenExpirationDateFromStringCodeBase64();
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
            mUserEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mUserEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }
}
