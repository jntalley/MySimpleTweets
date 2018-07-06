package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public User() {

    }


    //same steps : lists the attributes (name uid screenName and profileImage)

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;


    //deserialize the JSON file

    public static User fromJSON(JSONObject json) throws JSONException{
        User user  = new User();
        //extract and fill out user values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");

        return user;
    }
}