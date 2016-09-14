package com.example.twins.retrofitrss.data;

import com.example.twins.retrofitrss.model.RssModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Twins on 14.09.2016.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("cmlink/rss-sports")
    Observable<RssModel> getRSS(@Field("") String string);
}