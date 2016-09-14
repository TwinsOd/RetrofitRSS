package com.example.twins.retrofitrss.data;

import android.support.annotation.NonNull;

import com.example.twins.retrofitrss.model.RssModel;

import rx.Observable;

/**
 * Created by Twins on 14.09.2016.
 */

public class ApiManager {
    private static final int RETRY_COUNT_FOR_REQUEST = 0;

    private ApiManager() {}
    @NonNull
    public static Observable<RssModel> getListImage() {
        return ApiFactory.getApiService()
                .getRSS("")
                .retry(RETRY_COUNT_FOR_REQUEST)
                .map(response -> response);
    }
}
