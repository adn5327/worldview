package edu.illinois.g31.worldview;

/**
 * Created by tyler on 11/10/2016.
 */

public class Article {
    public String title, author, source, date, full_text, preview_text;
    public String[] tags;

    public Article(String title,String source, String author, String date, String text, String[] tags){
        this.title = title;
        this.author = author;
        this.source = source;
        this.date = date;
        this.full_text = text;
        this.preview_text = text.substring(0, 80);
        this.preview_text += "...";
        this.tags = tags;
    }

}
