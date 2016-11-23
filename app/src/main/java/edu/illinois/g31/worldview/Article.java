package edu.illinois.g31.worldview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tyler on 11/10/2016.
 */

public class Article implements Parcelable {
    private String title, author, source, date, full_text, preview_text;
    private String[] tags;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public String getPreview_text() {
        return preview_text;
    }

    public void setPreview_text(String preview_text) {
        this.preview_text = preview_text;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String toString(){
        return this.title + " - " + this.source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.source);
        dest.writeString(this.date);
        dest.writeString(this.full_text);
        dest.writeString(this.preview_text);
        dest.writeStringArray(this.tags);
    }

    protected Article(Parcel in) {
        this.title = in.readString();
        this.author = in.readString();
        this.source = in.readString();
        this.date = in.readString();
        this.full_text = in.readString();
        this.preview_text = in.readString();
        this.tags = in.createStringArray();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
