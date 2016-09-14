package com.example.twins.retrofitrss.data;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Twins on 14.09.2016.
 */

public class ApiFactory {
    private static final String BASE_URL = "http://www.cbc.ca/";
    private static final int CONNECT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;

    @NonNull
    static ApiService getApiService() {
        return getRetrofit(BASE_URL)
                .create(ApiService.class);
    }


    @NonNull
    private static Retrofit getRetrofit(@NonNull String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        httpClient.interceptors().add(logging);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build();
    }
}

