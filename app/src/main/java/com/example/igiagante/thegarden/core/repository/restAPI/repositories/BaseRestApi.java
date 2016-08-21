package com.example.igiagante.thegarden.core.repository.restAPI.repositories;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Ignacio Giagante, on 21/8/16.
 */
public class BaseRestApi {

    private Context context;

    public BaseRestApi(Context context) {
        this.context = context;
    }

    public boolean checkInternet() {

        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
