package com.example.igiagante.thegarden.core.repository.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class BaseRepositoryManager {

    private Context context;

    public BaseRepositoryManager(Context context) {
        this.context = context;
    }

    protected boolean checkInternet() {

        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
