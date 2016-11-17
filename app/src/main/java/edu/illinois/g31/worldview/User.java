package edu.illinois.g31.worldview;

import java.util.ArrayList;

/**
 * Created by tyler on 11/13/2016.
 */

public class User {
    public String name;
    public ArrayList<String> topics, sources;

    public User(String name) {
        this.name = name;
        topics = new ArrayList<>();
        sources = new ArrayList<>();
    }
    public User(String name, ArrayList<String> topics, ArrayList<String> sources){
        this.name = name;
        this.topics = topics;
        this.sources = sources;
    }
    public String toString(){
        String ret = "";
        ret += "Username: "+name+"\n";
        ret += "Topics: \n";
        for(int i = 0; i < this.topics.size(); i++)
            ret += "\t" + this.topics.get(i) + "\n";
        ret += "Sources: \n";
        for(int i = 0; i < this.sources.size(); i++)
            ret += "\t" + this.sources.get(i) + "\n";
        return  ret;
    }
}
