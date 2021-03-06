package com.cndatacom.zjproject.entry;

/**
 * 请求返回结果实体
 * Created by cdc4512 on 2018/1/4.
 */

public  class Result<T> {
    private String msg;
    private String status;
    private String token;
    private T body;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
