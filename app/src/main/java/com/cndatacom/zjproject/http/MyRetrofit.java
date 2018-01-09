package com.cndatacom.zjproject.http;

import com.cndatacom.zjproject.common.AppConstant;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cdc4512 on 2018/1/4.
 */

public class MyRetrofit {

    private static Retrofit retrofit;

    public static Retrofit init(){
            if(retrofit==null){
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new HttpRequestInterceptor())
                        .connectTimeout(5000, TimeUnit.MILLISECONDS)
                        .build();
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(AppConstant.URL_DEVELOPMENT)
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();
            }
        return retrofit;
    }

    public static HttpService getHttpService(){
        return retrofit.create(HttpService.class);
    }


}
