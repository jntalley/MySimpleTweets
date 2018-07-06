package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    //create twitter client that API gives access to
    private TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if (item.getItemId() == R.id.compose_item) {
            // start activity
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //access the actual twitter client
        client = TwitterApp.getRestClient(this);

        //find the recycler view
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //initialize the data source (API of tweets)
        tweets = new ArrayList<>();
        //construct tweet adapter from the arrayList data source
        tweetAdapter = new TweetAdapter(tweets);
        //setup the recycler view (layout manager => adapter => view)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(tweetAdapter);

        populateTimeline(); //method to be later implemented

    }

    public void composeNewTweet(MenuItem item) {

        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        startActivityForResult(i,0111);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tweet newTweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("newTweet"));

        tweets.add(0, newTweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);

    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //IF FAIL: check order of success as array is after object success in video
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwitterClient", response.toString());
                //STEPS: iterate through JSON, for each entry- deserialize the JSON, convert each entry to a tweet
                //add tweet to data source array, notify adapter of new item so it can set up the view
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size()-1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

        });
    }

}
