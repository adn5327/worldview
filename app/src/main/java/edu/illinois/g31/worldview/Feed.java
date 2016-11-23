package edu.illinois.g31.worldview;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tyler on 11/16/2016.
 */

public class Feed implements Parcelable {
    private String feedname;
    private ArrayList<String> topics, sources;

    public Feed(String name) {
        this.feedname = name;
        topics = new ArrayList<>();
        sources = new ArrayList<>();
    }
    public Feed(String name, ArrayList<String> topics, ArrayList<String> sources){
        this.feedname = name;
        this.topics = new ArrayList<>();
        for(String topic : topics)
            this.topics.add(topic);
        this.sources = new ArrayList<>();
        for(String source : sources)
            this.sources.add(source);

    }

    public String getFeedname() {
        return feedname;
    }

    public void setFeedname(String feedname) {
        this.feedname = feedname;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = new ArrayList<>();
        for(String topic : topics)
            this.topics.add(topic);
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public void setSources(ArrayList<String> sources) {
        this.sources = new ArrayList<>();
        for(String source : sources)
            this.sources.add(source);
    }

    public String toString(){
        String ret = "";
        ret += "Feed Name: "+this.feedname+"\n";
        ret += "Topics: \n";
        for(int i = 0; i < this.topics.size(); i++)
            ret += "\t" + this.topics.get(i) + "\n";
        ret += "Sources: \n";
        for(int i = 0; i < this.sources.size(); i++)
            ret += "\t" + this.sources.get(i) + "\n";
        return  ret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.feedname);
        dest.writeStringList(this.topics);
        dest.writeStringList(this.sources);
    }

    protected Feed(Parcel in) {
        this.feedname = in.readString();
        this.topics = in.createStringArrayList();
        this.sources = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}

