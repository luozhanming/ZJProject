package com.cndatacom.zjproject.entry;

import java.util.List;

/**
 * Created by cdc4512 on 2018/1/24.
 */

public class TasksEntry {
    private String msg;
    private String status;
    private List<TaskEntry> dbList;
    private List<TaskEntry> ybList;
    private List<TaskEntry> dyList;
    private List<TaskEntry> yyList;


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

    public List<TaskEntry> getDbList() {
        return dbList;
    }

    public void setDbList(List<TaskEntry> dbList) {
        this.dbList = dbList;
    }

    public List<TaskEntry> getYbList() {
        return ybList;
    }

    public void setYbList(List<TaskEntry> ybList) {
        this.ybList = ybList;
    }

    public List<TaskEntry> getDyList() {
        return dyList;
    }

    public void setDyList(List<TaskEntry> dyList) {
        this.dyList = dyList;
    }

    public List<TaskEntry> getYyList() {
        return yyList;
    }

    public void setYyList(List<TaskEntry> yyList) {
        this.yyList = yyList;
    }
}
