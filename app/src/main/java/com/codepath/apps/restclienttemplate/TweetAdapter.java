package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets; //empty list tweet variable
    Context context;

    //pass the tweet array into the constructor (that you create as the tweet adapter)

    public TweetAdapter(List<Tweet> tweets) { //has final variable mTweets loaded from JSON tweets

        mTweets = tweets;
    }
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }



        //for each row in recycler view (new item_tweet), inflate them into view
    //only invoke when new row is needed

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        //create view holder object
        ViewHolder viewHolder = new ViewHolder(tweetView); //takes view and puts it inside viewholder
        return viewHolder;

    }

    //then cache into the viewHolder (used to bind all rows in the views)
    //bind the values based on the position of the element (get position to identify tweets)
    public void onBindViewHolder(ViewHolder holder, int position){
        //get the tweet according to position
        Tweet tweet = mTweets.get(position);

        //populate the views according this data (user body and profile image w/Glide)
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(getRelativeTimeAgo(tweet.createdAt));

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .into(holder.ivProfileImage);

    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public int getItemCount() {

        return mTweets.size();
    }

    //create the ViewHolder class (you want this in the Adapter)
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;


        //below is the ViewHolder constructor
        public ViewHolder (View itemView) {
            super(itemView); //call super class

            //perform findViewById lookups
            //links above image/ text views to the actual views you created in the xml file
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp =(TextView) itemView.findViewById(R.id.tvTimeStamp);


        }
    }

}
