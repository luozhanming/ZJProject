package com.cndatacom.zjproject.common;

import com.cndatacom.zjproject.entry.Result;
import com.cndatacom.zjproject.entry.UserInfoEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cdc4512 on 2018/1/9.
 */

public class UserInfoCache {
    private static UserInfoCache sInstance;

    private Map<String, UserInfoEntry> cache;  //id key
   // private Map<String, UserInfoEntry> cache1;  //中文名key

    private UserInfoCache() {
        cache = new HashMap<>();
   //     cache1 = new HashMap<>();
    }

    public static final UserInfoCache getInstance() {
        synchronized (UserInfoCache.class) {
            if (sInstance == null) {
                sInstance = new UserInfoCache();
            }
        }
        return sInstance;
    }

    public void fetchInfo() {
        final Set<String> querys = new HashSet<>();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        Set<Map.Entry<String, EMConversation>> entries = conversations.entrySet();
        for (final Map.Entry<String, EMConversation> entry : entries) {
            querys.add(entry.getKey());
        }
        querys.add(EMClient.getInstance().getCurrentUser());
        EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                querys.addAll(strings);
                StringBuffer buffer = new StringBuffer();
                for (String query : querys) {
                   buffer.append(query+",");
                }
                MyRetrofit.getHttpService().getUserListByIdOrName(buffer.toString()).enqueue(new Callback<Result<List<UserInfoEntry>>>() {
                        @Override
                        public void onResponse(Call<Result<List<UserInfoEntry>>> call, Response<Result<List<UserInfoEntry>>> response) {
                            if (response.body().getStatus().equals("1")) {
                                List<UserInfoEntry> body = response.body().getBody();
                                int size = body.size();
                                for (int i = 0; i < size; i++) {
                                    cache.put(body.get(i).getLogonId(), body.get(i));
                        //            cache1.put(body.get(i).getFullName(),body.get(i));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result<List<UserInfoEntry>>> call, Throwable t) {

                        }
                    });

            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    public UserInfoEntry getUserInfo(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else
            return null;
    }


//    public UserInfoEntry getUserInfo1(String key) {
//        if (cache1.containsKey(key)) {
//            return cache1.get(key);
//        } else
//            return null;
//    }
}
