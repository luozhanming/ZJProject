package com.cndatacom.zjproject.http;

import com.cndatacom.zjproject.common.AppConstant;
import com.google.gson.Gson;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cdc4512 on 2018/1/4.
 */

public class MyRetrofit {

    private static Retrofit retrofit;

    static {
        Executors.newCachedThreadPool();

    }

    public static Retrofit init() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new LoggerInterceptor())
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(AppConstant.URL_PRODUCT_PUB)
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }
        return retrofit;
    }

    public static HttpService getHttpService() {
        if (retrofit == null) {
            init();
        }
        return retrofit.create(HttpService.class);
    }


}
