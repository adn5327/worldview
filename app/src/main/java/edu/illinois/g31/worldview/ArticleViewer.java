package edu.illinois.g31.worldview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArticleViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

        //set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Bundle article_info = getIntent().getExtras();

        TextView title = (TextView)this.findViewById(R.id.article_title);
        title.setText(article_info.getString("article_title"));

        TextView source = (TextView)this.findViewById(R.id.article_source);
        source.setText(article_info.getString("article_source"));

        TextView author = (TextView)this.findViewById(R.id.article_author);
        author.setText(article_info.getString("article_author"));

        TextView date = (TextView)this.findViewById(R.id.article_date);
        date.setText(article_info.getString("article_date"));

        String tags[] = article_info.getStringArray("article_tags");
        RelativeLayout tagList = (RelativeLayout)this.findViewById(R.id.article_tags);
        TextView tagTexts[] = new TextView[tags.length];
        for(int j = 0; j < tags.length; j++){
            tagTexts[j] = new TextView(this);
            tagTexts[j].setId(View.generateViewId());
            RelativeLayout.LayoutParams tagTextLP = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(j > 0)
                tagTextLP.addRule(RelativeLayout.RIGHT_OF,tagTexts[j-1].getId());
            tagTexts[j].setLayoutParams(tagTextLP);
            tagTexts[j].setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.feed_tag_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_tag_vertical_padding));
            tagTexts[j].setText(tags[j]);
            tagTexts[j].setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_tag_text_size));
            tagTexts[j].setTextColor(getResources().getColor(R.color.tagColor));
            tagList.addView(tagTexts[j]);
        }


        TextView text = (TextView)this.findViewById(R.id.article_text);
        text.setText(article_info.getString("article_text"));


    }
}
