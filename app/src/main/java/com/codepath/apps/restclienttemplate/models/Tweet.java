package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    //define all the attributes of the tweet that you want
    public String body;
    public long uid; //database id for the tweet
    public User user; //after creation of user class acts as the user attribute of each tweet
    public String createdAt; //in the JSON for location

    public Tweet() {

    }

    //deserialize the data using parsable JSON to Tweet

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract all of the values from JSON that you need for the tweet
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;

    }



}
