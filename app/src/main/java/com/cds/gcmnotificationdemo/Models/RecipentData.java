package com.cds.gcmnotificationdemo.Models;

/**
 * Created by fazal on 2/24/2017.
 */

public class RecipentData {
    String username;
    String message;
    String date;

    public RecipentData(String username, String message, String date) {
        this.username = username;
        this.message = message;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
