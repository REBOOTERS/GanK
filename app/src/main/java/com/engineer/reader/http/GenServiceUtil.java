package com.engineer.reader.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by co-mall on 2017/5/10.
 */

public class GenServiceUtil {
    private static final String BASE_URL = "http://gank.io/api/data/";

    //
    private static final OkHttpClient.Builder client = new
            OkHttpClient.Builder().addInterceptor(new LogInterceptor());

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.client(client.build()).build();

    public static <S> S createService(Class<S> service) {
        return retrofit.create(service);
    }

}
