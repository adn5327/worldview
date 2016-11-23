package edu.illinois.g31.worldview;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.*;

/**
 * Created by tyler on 11/15/2016.
 */

public class JSONParser {
    public static String loadJSONFromAsset(Context context, String filename){
        String json = null;
        System.out.println("Parsing file..");
        try {
            InputStream is = context.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<Article> getArticles(User user, String articlejson){
        System.out.println("Getting articles for user "+ user.getUsername() + "...");
        ArrayList<Article> articles = new ArrayList<>();
        JSONObject articleobj;
        JSONArray arts;
        try{
            articleobj = new JSONObject(articlejson);
            arts = articleobj.getJSONArray("articles");
            for(int i = 0; i < arts.length(); i++) {
                JSONObject article = arts.getJSONObject(i);
                String title = article.getString("title");
                String source = article.getString("source");
                String author = article.getString("author");
                String date = article.getString("date");
                JSONArray tagJson = article.getJSONArray("tags");
                String tags[] = new String[tagJson.length()];
                for(int j = 0; j < tagJson.length(); j++) {
                    tags[j] = tagJson.getString(j);
                }
                String text = article.getString("text");
                if(includeArticle(user,tags,source))
                    articles.add(new Article(title,source,author,date,text,tags));
            }
        } catch(JSONException ex){
            ex.printStackTrace();
        }
        return articles;
    }
    public static boolean includeArticle(User user, String[] tags, String source){
        boolean topicPass = false;
        boolean sourcePass = false;
        for(int j = 0; j < tags.length; j++)
            if(user.getCurFeed().getTopics().contains(tags[j]))
                topicPass = true;
        if(user.getCurFeed().getSources().contains(source))
            sourcePass = true;
        return topicPass && sourcePass;
    }
}
