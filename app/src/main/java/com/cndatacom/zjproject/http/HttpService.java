package com.cndatacom.zjproject.http;

import com.cndatacom.zjproject.entry.Result;
import com.cndatacom.zjproject.entry.UserInfoEntry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *  1.login()登录接口
 *  2.logout()退出登录接口
 *  3.getUserListByIdOrName()通过id查用户信息
 * Created by cdc4512 on 2018/1/4.
 */

public interface HttpService {


    @POST("urlLogin/login")
    @FormUrlEncoded
    Call<Result<UserInfoEntry>> login(@Field("userName") String username, @Field("password") String password);

    @POST("urlLogin/logout")
    @FormUrlEncoded
    Call<Result> logout(@Field("userName") String username);

    @POST("user/getUserListByIdOrName")
    @FormUrlEncoded
    Call<Result<List<UserInfoEntry>>> getUserListByIdOrName(@Field("logonId") String id);



}
