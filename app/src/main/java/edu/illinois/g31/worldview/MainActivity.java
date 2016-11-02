package edu.illinois.g31.worldview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int feed_num = 6;
        RelativeLayout newsFeed = new RelativeLayout(this);
        RelativeLayout f[] = new RelativeLayout[feed_num];
        for(int i = 0; i < f.length; i ++){
            f[i] = newFeed(getText(R.string.title_text),getText(R.string.source_text), getText(R.string.preview_text), getText(R.string.tags));
            if(i > 0){
                RelativeLayout.LayoutParams LP = new RelativeLayout.LayoutParams(
                        f[i].getLayoutParams().width,
                        f[i].getLayoutParams().height);
                LP.addRule(RelativeLayout.BELOW, f[i-1].getId());
                f[i].setLayoutParams(LP);
            }
            newsFeed.addView(f[i]);
        }

        setContentView(newsFeed);
        //setContentView(R.layout.activity_main);
    }

    protected RelativeLayout newFeed(CharSequence title, CharSequence source, CharSequence preview, CharSequence tags){
        RelativeLayout feed = new RelativeLayout(this);

        feed.setId(View.generateViewId());
        RelativeLayout.LayoutParams feedLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        feed.setLayoutParams(feedLP);
        feed.setBackground(getDrawable(R.drawable.feed_background));

        ImageView thumbnail = new ImageView(this);
        thumbnail.setId(View.generateViewId());
        thumbnail.setContentDescription("Thumbnail of article");
        RelativeLayout.LayoutParams thumbnailLP = new RelativeLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.thumbnail_width),
                getResources().getDimensionPixelSize(R.dimen.thumbnail_height));
        thumbnail.setLayoutParams(thumbnailLP);
        thumbnail.setImageResource(R.mipmap.thumbnail);

        TextView titleText = new TextView(this);
        titleText.setId(View.generateViewId());
        RelativeLayout.LayoutParams titleTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleTextLP.addRule(RelativeLayout.ALIGN_TOP,thumbnail.getId());
        titleTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        titleText.setLayoutParams(titleTextLP);
        titleText.setPadding(0,getResources().getDimensionPixelSize(R.dimen.title_vertical_padding),0,0);
        titleText.setText(title);
        titleText.setTextSize(getResources().getDimensionPixelSize(R.dimen.title_text_size));
        titleText.setTextColor(getResources().getColor(R.color.titleColor));

        TextView sourceText = new TextView(this);
        sourceText.setId(View.generateViewId());
        RelativeLayout.LayoutParams sourceTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sourceTextLP.addRule(RelativeLayout.RIGHT_OF,titleText.getId());
        sourceText.setLayoutParams(sourceTextLP);
        sourceText.setPadding(getResources().getDimensionPixelSize(R.dimen.source_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.source_vertical_padding),0,0);
        sourceText.setText(source);
        sourceText.setTextSize(getResources().getDimensionPixelSize(R.dimen.source_text_size));
        sourceText.setTextColor(getResources().getColor(R.color.sourceColor));

        TextView previewText = new TextView(this);
        previewText.setId(View.generateViewId());
        RelativeLayout.LayoutParams previewTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        previewTextLP.addRule(RelativeLayout.BELOW,titleText.getId());
        previewTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        previewText.setLayoutParams(previewTextLP);
        previewText.setText(preview);
        previewText.setTextSize(getResources().getDimensionPixelSize(R.dimen.preview_text_size));
        previewText.setTextColor(getResources().getColor(R.color.previewColor));

        TextView tagText = new TextView(this);
        tagText.setId(View.generateViewId());
        RelativeLayout.LayoutParams tagTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tagTextLP.addRule(RelativeLayout.ALIGN_BOTTOM,thumbnail.getId());
        tagTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        tagText.setLayoutParams(tagTextLP);
        tagText.setPadding(0,0,0,getResources().getDimensionPixelSize(R.dimen.tag_vertical_padding));
        tagText.setText(tags);
        tagText.setTextSize(getResources().getDimensionPixelSize(R.dimen.tag_text_size));
        tagText.setTextColor(getResources().getColor(R.color.tagColor));


        feed.addView(thumbnail);
        feed.addView(titleText);
        feed.addView(sourceText);
        feed.addView(previewText);
        feed.addView(tagText);

        return feed;
    }
}
