package edu.illinois.g31.worldview;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tyler on 11/13/2016.
 */

public class User implements Parcelable {
    private String username;
    private ArrayList<Feed> feeds;
    private int curFeed;
    private ArrayList<Article> saved, compare;


    public User(String name) {
        this.username = name;
        feeds = new ArrayList<>();
        curFeed = -1;
        saved = new ArrayList<>();
        compare = new ArrayList<>();

    }
    public User(String name, ArrayList<Feed> feeds){
        this.username = name;
        this.feeds = feeds;
        if(feeds.size() > 0)
            curFeed = 0;
        else
            curFeed = -1;
        saved = new ArrayList<>();
        compare = new ArrayList<>();

    }
    public User(String name, Feed feed){
        this.username = name;
        feeds = new ArrayList<Feed>();
        feeds.add(feed);
        curFeed = 0;
        saved = new ArrayList<>();
        compare = new ArrayList<>();
    }
    public User(String name,  ArrayList<Feed> feeds, ArrayList<Article> saved, ArrayList<Article> compare){
        this.username = name;
        this.feeds = feeds;
        if(feeds.size() > 0)
            curFeed = 0;
        else
            curFeed = -1;
        this.saved = saved;
        this.compare = compare;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public ArrayList<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
    }

    public Feed getFeed(String name){
        for(Feed feed : feeds){
            if(feed.getFeedname().equals(name))
                    return feed;
        }
        return null;
    }

    public boolean addFeed(String name) {
        if(getFeed(name) == null) {
            Feed newFeed = new Feed(name);
            feeds.add(newFeed);
            curFeed = feeds.size()-1;
            return true;
        }
        else
            return false;
    }

    public Feed getCurFeed() {
        return feeds.get(curFeed);
    }

    public void setCurFeed(int curFeed) {
        this.curFeed = curFeed;
    }

    public ArrayList<Article> getSaved() {
        return saved;
    }

    public void setSaved(ArrayList<Article> saved) {
        this.saved = saved;
    }

    public ArrayList<Article> getCompare() {
        return compare;
    }

    public void addToCompare(Article article, Context context){
        boolean inCompare = false;
        for(Article art : compare){
            if(art.getTitle().equals(article.getTitle()) && art.getSource().equals(article.getSource()))
                inCompare = true;
        }
        if(!inCompare)
            compare.add(article);
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.couldnt_add_title)
                    .setMessage(R.string.couldnt_add_message);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void addToSaved(Article article, Context context){
        boolean inSaved = false;
        for(Article art : saved){
            if(art.getTitle().equals(article.getTitle()) && art.getSource().equals(article.getSource()))
                inSaved = true;
        }
        if(!inSaved)
            saved.add(article);
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.couldnt_add_title)
                    .setMessage(R.string.couldnt_add_message);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void setCompare(ArrayList<Article> compare) {
        this.compare = compare;
    }

    public void setDefaults(){
        ArrayList<String> default_topics = new ArrayList<>(Arrays.asList("Politics","Donald Trump", "Hillary Clinton", "Barack Obama", "Style"));
        ArrayList<String> default_sources = new ArrayList<>(Arrays.asList("NY Times","Guardian", "BBC"));
        Feed default_feed = new Feed("Default", default_topics, default_sources);
        feeds.add(default_feed);
        curFeed = 0;
    }
    public void setFakeDefaults(){
        ArrayList<String> default_topics = new ArrayList<>(Arrays.asList("Politics", "Style", "Donald Trump", "Baseball"));
        ArrayList<String> default_sources = new ArrayList<>(Arrays.asList("BBC", "NY Times", "Washington Post"));
        ArrayList<String> alt_topics = new ArrayList<>(Arrays.asList("Sports", "Tech", "Entertainment"));
        ArrayList<String> alt_sources = new ArrayList<>(Arrays.asList("Washington Post", "NY Times", "Guardian", "BBC", "Huffington Post"));
        Feed default_feed = new Feed("Default", default_topics, default_sources);
        Feed alt_feed = new Feed("Alternate", alt_topics, alt_sources);
        feeds.add(default_feed);
        feeds.add(alt_feed);
        curFeed = 0;
    }

    public String toString(){
        String ret = "";
        ret += "Username: "+username+"\n";
        ret += "Feeds: \n";
        for(int i = 0; i < this.feeds.size(); i++) {
            ret += "\t" + this.feeds.get(i) + "\n";
        }
        return  ret;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeTypedList(this.feeds);
        dest.writeInt(this.curFeed);
        dest.writeTypedList(this.saved);
        dest.writeTypedList(this.compare);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.feeds = in.createTypedArrayList(Feed.CREATOR);
        this.curFeed = in.readInt();
        this.saved = in.createTypedArrayList(Article.CREATOR);
        this.compare = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
