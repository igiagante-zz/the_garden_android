package com.example.igiagante.thegarden.core.repository.network;

import com.example.igiagante.thegarden.core.repository.network.converter.StringConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Ignacio Giagante, on 19/4/16.
 */
public class ServiceFactory {

    //Genymotion
    //public static final String API_ENDPOINT = "http://10.0.3.2:3000/api/";
    //Real Device
    public static final String API_ENDPOINT = "http://10.18.32.137:3000/api/";

    public static <T> T createRetrofitService(final Class<T> clazz) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).build();

        T service = restAdapter.create(clazz);

        return service;
    }
}
