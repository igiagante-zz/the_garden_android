package com.example.igiagante.thegarden.core.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by igiagante on 19/4/16.
 */
public class ServiceFactory {

    //Genymotion
    //public static final String API_ENDPOINT = "http://10.0.3.2:3000/api/";
    //Real Device
    public static final String API_ENDPOINT = "http://192.168.0.100:3000/api/";

    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}
