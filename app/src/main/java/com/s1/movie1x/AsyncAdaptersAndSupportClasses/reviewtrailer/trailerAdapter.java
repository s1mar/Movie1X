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
public class trailerAdapter extends BaseAdapter {

    Context context;
    int r_layout;
    int r_textTag;
    int length;
    trailer[] trailers;

    public trailerAdapter(Context context,int r_layout,int r_textTag){

        this.context=context;
        this.r_layout=r_layout;
        this.r_textTag=r_textTag;
        trailers = new trailer[0];
    }

    public void update(trailer[] x){

        length=x.length;
        trailers = new trailer[length];
        for(int i=0;i<length;++i){

            trailers[i] = new trailer();
            trailers[i].setSource(x[i].getSource());
        }

    }
    public String getSource(int positon){

        return trailers[positon].getSource().trim();
    }

    public String objOnTop(){

        return trailers[0].getSource().trim();

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

        TextView tagT=(TextView)convertView.findViewById(r_textTag);
        String dips=("Watch Trailer "+Integer.toString(position+1));
        tagT.setText(dips);

        Log.e("Source",tagT.getText().toString());

        return convertView;
    }
}
