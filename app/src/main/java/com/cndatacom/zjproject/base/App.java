package com.cndatacom.zjproject.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.http.MyRetrofit;

/**
 * Created by zhanming on 2018/1/1.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        MyRetrofit.init();
        LoginEntry.instance();

    }
}
