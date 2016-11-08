package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        int feed_num = 10;
        RelativeLayout newsFeed = new RelativeLayout(this);
        RelativeLayout f[] = new RelativeLayout[feed_num];
        for(int i = 0; i < f.length; i ++){
            final String articleTitle = getText(R.string.title_text)+" "+i;
            final String articleSource = getText(R.string.source_text)+" "+i;
            final String articlePreview = getText(R.string.preview_text)+" "+i;
            final String articleAuthor = getText(R.string.author_text)+" "+i;
            final String articleDate = getText(R.string.date_text)+" "+i;
            final String articleText = getText(R.string.full_text)+" "+i;
            final String articleTags[] = new String[3];
            for(int j = 0; j < articleTags.length; j++){
                articleTags[j] = getText(R.string.tags)+""+j;
            }



            f[i] = addArticleToFeed(articleTitle,articleSource,articlePreview,articleTags);
            if(i > 0){
                RelativeLayout.LayoutParams LP = new RelativeLayout.LayoutParams(
                        f[i].getLayoutParams().width,
                        f[i].getLayoutParams().height);

                LP.addRule(RelativeLayout.BELOW, f[i-1].getId());
                f[i].setLayoutParams(LP);

            }
            f[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent article = new Intent(NewsFeed.this, ArticleViewer.class);
                    article.putExtra("article_title", articleTitle);
                    article.putExtra("article_source", articleSource);
                    article.putExtra("article_author", articleAuthor);
                    article.putExtra("article_date", articleDate);
                    article.putExtra("article_tags", articleTags);
                    article.putExtra("article_text", articleText);
                    startActivity(article);
                }
            });
            newsFeed.addView(f[i]);
        }

        scroll.addView(newsFeed);
        setContentView(scroll);
    }

    protected RelativeLayout addArticleToFeed(CharSequence title, CharSequence source, CharSequence preview, CharSequence[] tags){
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
                getResources().getDimensionPixelSize(R.dimen.feed_thumbnail_width),
                getResources().getDimensionPixelSize(R.dimen.feed_thumbnail_height));
        thumbnail.setLayoutParams(thumbnailLP);
        thumbnail.setImageResource(R.mipmap.worldview);

        TextView titleText = new TextView(this);
        titleText.setId(View.generateViewId());
        RelativeLayout.LayoutParams titleTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleTextLP.addRule(RelativeLayout.ALIGN_TOP,thumbnail.getId());
        titleTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        titleText.setLayoutParams(titleTextLP);
        titleText.setPadding(0,getResources().getDimensionPixelSize(R.dimen.feed_title_vertical_padding),0,0);
        titleText.setText(title);
        titleText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_title_text_size));
        titleText.setTextColor(getResources().getColor(R.color.titleColor));

        TextView sourceText = new TextView(this);
        sourceText.setId(View.generateViewId());
        RelativeLayout.LayoutParams sourceTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sourceTextLP.addRule(RelativeLayout.RIGHT_OF,titleText.getId());
        sourceText.setLayoutParams(sourceTextLP);
        sourceText.setPadding(getResources().getDimensionPixelSize(R.dimen.feed_source_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_source_vertical_padding),0,0);
        sourceText.setText(source);
        sourceText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_source_text_size));
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
        previewText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_preview_text_size));
        previewText.setTextColor(getResources().getColor(R.color.previewColor));

        RelativeLayout tagList = new RelativeLayout(this);
        tagList.setId(View.generateViewId());
        RelativeLayout.LayoutParams tagListLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tagListLP.addRule(RelativeLayout.ALIGN_BOTTOM,thumbnail.getId());
        tagListLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        tagList.setLayoutParams(tagListLP);
        TextView tagTexts[] = new TextView[tags.length];
        for(int j = 0; j < tags.length; j++){
            tagTexts[j] = new TextView(this);
            tagTexts[j].setId(View.generateViewId());
            RelativeLayout.LayoutParams tagTextLP = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tagTextLP.addRule(RelativeLayout.ALIGN_BOTTOM,thumbnail.getId());
            if(j > 0)
                tagTextLP.addRule(RelativeLayout.RIGHT_OF,tagTexts[j-1].getId());
            tagTexts[j].setLayoutParams(tagTextLP);
            tagTexts[j].setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.feed_tag_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_tag_vertical_padding));
            tagTexts[j].setText(tags[j]);
            tagTexts[j].setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_tag_text_size));
            tagTexts[j].setTextColor(getResources().getColor(R.color.tagColor));
            tagList.addView(tagTexts[j]);
        }



        feed.addView(thumbnail);
        feed.addView(titleText);
        feed.addView(sourceText);
        feed.addView(previewText);
        feed.addView(tagList);

        return feed;
    }


}
