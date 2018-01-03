package com.cndatacom.zjproject.entry;

/**
 * 个人信息
 * Created by cdc4512 on 2018/1/3.
 */

public class LoginEntry {

    private static LoginEntry sLoginEntry = null;
    private boolean isLogin;   //是否登录


    public synchronized static LoginEntry instance(){
        if(sLoginEntry==null){
            sLoginEntry = new LoginEntry();
        }
        return sLoginEntry;
    }



}
