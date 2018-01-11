package com.hyphenate.easeui.model;

import com.hyphenate.chat.EMGroup;

/**
 * Created by cdc4512 on 2018/1/11.
 */

public class EaseGroup {

    private EMGroup group;
    private String imgUrl;

    public EaseGroup(EMGroup group,String imgUrl){
        this.group = group;
        this.imgUrl = imgUrl;
    }

    public String getGroupId(){
        return group.getGroupId();
    }

    public String getGroupName(){
        return group.getGroupName();
    }

    public String getImgUrl(){
        return imgUrl;
    }
}
