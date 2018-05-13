package com.app.altex.alttexio.customelements;

/**
 * Created by igorqua on 16.03.2018.
 */

public class UserListItem {
    public String name;
    public String id;
    //public String mail;
    public String imgUrl;

    public UserListItem(String name, String id, String imgUrl){
        this.name = name;
        this.id = id;
        this.imgUrl = imgUrl;
       // this.mail = mail;
    }
}
