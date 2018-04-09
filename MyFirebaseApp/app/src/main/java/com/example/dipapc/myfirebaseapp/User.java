package com.example.dipapc.myfirebaseapp;

import android.net.Uri;

/**
 * Created by DipaPc on 4/4/2018.
 */

public class User {

    private String name;
    private String email;
    private String description;
    private Uri photourl;


    public User(){

    }

    public User(String name, String email,String description,Uri photourl){
        this.name = name;
        this.email = email;
        this.description = description;
        this.photourl = photourl;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPhotourl(Uri photourl) {
        this.photourl = photourl;
    }

    public Uri getPhotourl() {
        return photourl;
    }
}
