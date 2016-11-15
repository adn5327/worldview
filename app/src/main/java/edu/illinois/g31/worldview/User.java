package edu.illinois.g31.worldview;

/**
 * Created by tyler on 11/13/2016.
 */

public class User {
    public String name;
    public String[] topics, sources;

    public User(String name) {
        this.name = name;
        topics = new String[10];
        sources = new String[6];
    }
    public User(String name, String[] topics, String[] sources){
        this.name = name;
        this.topics = topics;
        this.sources = sources;
    }
    public String toString(){
        String ret = "";
        ret += "Username: "+name+"\n";
        ret += "Topics: \n";
        for(int i = 0; i < this.topics.length; i++)
            ret += "\t" + this.topics[i] + "\n";
        ret += "Sources: \n";
        for(int i = 0; i < this.sources.length; i++)
            ret += "\t" + this.sources[i] + "\n";
        return  ret;
    }
}
