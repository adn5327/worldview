package edu.illinois.g31.worldview;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by tyler on 11/16/2016.
 */

public class Feed {
    public String feedname;
    public ArrayList<String> topics, sources;

    public Feed(String name) {
        this.feedname = name;
        topics = new ArrayList<>();
        sources = new ArrayList<>();
    }
    public Feed(String name, ArrayList<String> topics, ArrayList<String> sources){
        this.feedname = name;
        this.topics = topics;
        this.sources = sources;
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
}

