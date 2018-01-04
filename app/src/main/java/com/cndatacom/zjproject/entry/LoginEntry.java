package com.cndatacom.zjproject.entry;

/**
 * 登录信息
 * Created by luozhanming on 2018/1/3.
 */

public class LoginEntry {

    private static LoginEntry sLoginEntry = null;
    private boolean isLogin;   //是否登录
    private UserInfoEntry userInfo = null;

    private LoginEntry(){

    }


    public synchronized static LoginEntry instance(){
        if(sLoginEntry==null){
            sLoginEntry = new LoginEntry();
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

    public void setUserInfo(UserInfoEntry userInfo) {
        this.userInfo = userInfo;
    }
}
