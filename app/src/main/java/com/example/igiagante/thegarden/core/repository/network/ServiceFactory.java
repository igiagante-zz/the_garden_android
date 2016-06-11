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

    public static <T> T createRetrofitService(final Class<T> clazz) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        final Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(Settings.API_ENDPOINT)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).build();

        T service = restAdapter.create(clazz);

        return service;
    }
}
