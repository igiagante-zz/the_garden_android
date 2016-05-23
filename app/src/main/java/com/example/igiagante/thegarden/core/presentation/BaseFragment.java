package com.example.igiagante.thegarden.core.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.igiagante.thegarden.R;
import com.example.igiagante.thegarden.core.di.HasComponent;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {

    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTittle) {
        this.mTitle = mTittle;
    }
}
