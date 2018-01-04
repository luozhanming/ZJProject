package com.cndatacom.zjproject.http;

import com.google.gson.Gson;

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
                        .build();
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("http://192.168.200.92:8099/zjjcyPlatForm/rest/")
                        .addConverterFactory(GsonConverterFactory.create(new Gson()))
                        .build();
            }
        return retrofit;
    }

    public static HttpService getHttpService(){
        return retrofit.create(HttpService.class);
    }


}
