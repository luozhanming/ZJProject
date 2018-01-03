package com.cndatacom.zjproject.entry;

/**
 * Created by cdc4512 on 2018/1/3.
 */

public class MessageEntry {
    private String imgUrl;
    private String fromUser;
    private String message;
    private String time;
    private boolean fromDepartment;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFromDepartment() {
        return fromDepartment;
    }

    public void setFromDepartment(boolean fromDepartment) {
        this.fromDepartment = fromDepartment;
    }
}
