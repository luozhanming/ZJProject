package com.cndatacom.zjproject.entry;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.CookieManager;

import com.blankj.utilcode.util.SPUtils;

/**
 * 登录信息
 * Created by luozhanming on 2018/1/3.
 */

public class LoginEntry {

    private volatile static LoginEntry sLoginEntry = null;
    private boolean isLogin;   //是否登录
    private UserInfoEntry userInfo = null;

    private LoginEntry(){

    }


    public static LoginEntry instance(){
        if(sLoginEntry==null){
            synchronized (LoginEntry.class){
                if(sLoginEntry==null){
                    sLoginEntry = new LoginEntry();
                }
            }
        }
        return sLoginEntry;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public UserInfoEntry getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(final UserInfoEntry userInfo) {
        this.userInfo = userInfo;
        if(this.userInfo!=null){
            setLogin(true);
            SPUtils.getInstance().put("username",userInfo.getLogonId());
            SPUtils.getInstance().put("password","jcy@2018");
        }
    }

    public void logout(){
        setUserInfo(null);
        setLogin(false);
        SPUtils.getInstance().put("username","");
        SPUtils.getInstance().put("password","");
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookie();
    }
}
