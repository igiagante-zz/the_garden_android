package com.example.igiagante.thegarden.core.presentation.mvp;

import android.content.Context;

/**
 * Created by igiagante on 4/5/16.
 */
public interface IView {

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);

    /**
     * Get a {@link android.content.Context}.
     */
    Context context();
}
