package edu.illinois.g31.worldview;

import java.util.ArrayList;

/**
 * Created by tyler on 11/13/2016.
 */

public class User {
    public String name;
    public ArrayList<Feed> feeds;
    public Feed curFeed;

    public User(String name) {
        this.name = name;
        feeds = new ArrayList<>();
        curFeed = null;
    }
    public User(String name, ArrayList<Feed> feeds){
        this.name = name;
        this.feeds = feeds;
        if(feeds.size() > 0)
            curFeed = this.feeds.get(0);
        else
            curFeed = null;
    }
    public User(String name, Feed feed){
        this.name = name;
        feeds = new ArrayList<Feed>();
        feeds.add(feed);
        this.curFeed = feed;
    }
    public String toString(){
        String ret = "";
        ret += "Username: "+name+"\n";
        ret += "Feeds: \n";
        for(int i = 0; i < this.feeds.size(); i++) {
            ret += "\t" + this.feeds.get(i) + "\n";
        }
        return  ret;
    }
}
