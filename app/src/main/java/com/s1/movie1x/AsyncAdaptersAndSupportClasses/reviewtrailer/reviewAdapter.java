package com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by s1mar_000 on 03-03-2016.
 */
public class reviewAdapter extends BaseAdapter {

    review[] reviewList;
    Context context;
    int r_layout;
    int r_element_author;
    int r_element_content;
    int length;

    public reviewAdapter(Context context,int r_layout,int r_element_author,int r_element_content,review[] x) {
        this.context = context;
        this.r_layout = r_layout;
        this.r_element_author = r_element_author;
        this.r_element_content = r_element_content;

        length = x.length;
        reviewList = new review[length];
        for (int i = 0; i < length; ++i) {

            reviewList[i] = new review();
            reviewList[i].setContent(x[i].getContent());
            reviewList[i].setAuthor(x[i].getAuthor());
        }
    }

    public void update(review[] x){

        length=x.length;
        reviewList = new review[length];
        for(int i=0;i<length;++i){

            reviewList[i] = new review();
            reviewList[i].setContent(x[i].getContent());
            reviewList[i].setAuthor(x[i].getAuthor());
        }

    }

    @Override
    public int getCount() {
        return length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(r_layout,parent,false);
        }

        TextView authorName = (TextView)convertView.findViewById(r_element_author);
        TextView content   =(TextView)convertView.findViewById(r_element_content);

        authorName.setText(reviewList[position].getAuthor());
        content.setText(reviewList[position].getContent());
        Log.e("ReviewA", "Author " + authorName.getText());
        Log.e("ReviewA", "Content " + content.getText());

        return convertView;

         }
}
