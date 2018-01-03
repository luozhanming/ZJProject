package com.cndatacom.zjproject.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * Created by cdc4512 on 2018/1/3.
 */

public class EncryptUtil {

    public static String EncryptMD5(String input) {
        MessageDigest md5 = null;
        String result = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = input.getBytes("utf-8");
            byte[] base64 = Base64.encode(bytes, Base64.DEFAULT);
            result = new String(md5.digest(base64),"utf-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
