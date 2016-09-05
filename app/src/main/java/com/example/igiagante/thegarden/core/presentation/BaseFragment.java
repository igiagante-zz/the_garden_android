package com.example.igiagante.thegarden.core.presentation;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igiagante.thegarden.core.di.HasComponent;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Used as event bus for validation
     */
    protected PublishSubject<ValidationMessage> subject = PublishSubject.create();

    /**
     * Used as event bus to enable view
     */
    private PublishSubject<Boolean> subjectView = PublishSubject.create();

    private String mTitle;

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setGravity(Gravity.CENTER);
        toast.show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected void setValidationMessage(ValidationMessage validationMessage) {
        subject.onNext(validationMessage);
    }

    public Observable<ValidationMessage> getValidationMessageObservable() {
        return subject;
    }

    protected void setEnablePaging(Boolean enable) {
        subjectView.onNext(enable);
    }

    public Observable<Boolean> getEnableViewPagerObservable() {
        return subjectView;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTittle) {
        this.mTitle = mTittle;
    }

    protected boolean isLandScape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean checkInternet() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
