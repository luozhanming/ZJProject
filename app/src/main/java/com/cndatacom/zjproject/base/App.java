package com.cndatacom.zjproject.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.cndatacom.zjproject.entry.LoginEntry;
import com.cndatacom.zjproject.http.MyRetrofit;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

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
        EMChatInit();

    }

    private void EMChatInit() {
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
        EaseUI.getInstance().init(this,options);

    }
}
