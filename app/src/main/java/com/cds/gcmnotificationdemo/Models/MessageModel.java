package com.cds.gcmnotificationdemo.Models;

/**
 * Created by fazal on 3/28/2017.
 */

public class MessageModel {
    String msg;
    String username;
    boolean isender;

    public MessageModel(String msg, String username, boolean isender) {
        this.msg = msg;
        this.username = username;
        this.isender = isender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean issender() {
        return isender;
    }

    public void setIsender(boolean isender) {
        this.isender = isender;
    }
}
