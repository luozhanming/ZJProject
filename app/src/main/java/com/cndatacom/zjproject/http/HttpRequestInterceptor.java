package com.cndatacom.zjproject.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Okhttp拦截器，用于拦截输出请求响应信息
 * Created by cdc4512 on 2018/1/4.
 */

public class HttpRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //请求地址
        String url = request.url().toString();
        Log.e("Test", "请求地址：" + url);

        Headers headers = request.headers();
        int size = headers.size();
        if (size > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("请求头：");
            for (int i = 0; i < size; i++) {
                sb.append(headers.name(i) + " = " + headers.value(i) + " , ");
            }
            Log.e("Test", sb.toString());
        }

        if (request.body() != null) {
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            String requestbody = buffer.readString(charset);
            Log.e("Test", "请求参数：" + requestbody);
        }

        Response response = chain.proceed(request);

        if (response.body() != null && response.body().contentType() != null) {
            MediaType mediaType = response.body().contentType();
            String string = response.body().string();
            Log.e("Test", "响应结果："+string);
            ResponseBody responseBody = ResponseBody.create(mediaType, string);
            return response.newBuilder().body(responseBody).build();
        } else {
            return response;
        }

    }
}
