package com.example.igiagante.thegarden.core.presentation.mvp;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public interface LoadDataView extends IView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();
}
