package com.s1.movie1x.AsyncAdaptersAndSupportClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.s1.movie1x.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by s1mar_000 on 02-03-2016.
 */
public class MovieAdapter extends BaseAdapter {

    Movie[] movieArray;
    int count;
    Context context;
    int resLayout;
    int resItem;

    public Movie getMovie(int position){

    return movieArray[position];

 }

    public MovieAdapter(String[] raw,Context context,int resLayout,int resItem){
        this.resLayout=resLayout;
        this.resItem=resItem;
        this.context=context;
        int length = raw.length;
        movieArray = new Movie[length];
        String []temp1=movieQuery.seperator(1,raw); //image path
        String []temp2=movieQuery.seperator(2,raw); // title
        String []temp3=movieQuery.seperator(3,raw); //vote

        for(int i=0;i<length;++i){

            movieArray[i].setImgPath(temp1[i]); // set image path
            movieArray[i].setName(temp2[i]); //set title
            movieArray[i].setVote(temp3[i]); //set vote

        }
        count=length;

    }

    public void clear()
    {
        movieArray = new Movie[0];

    }

    public void update(String[] raw,ArrayList<String> desc,ArrayList<String> id)
    {
        int length = raw.length;
        movieArray = new Movie[length];
        for(int i =0;i<length;++i){
            movieArray[i] = new Movie();
        }
        String []temp1=movieQuery.seperator(1,raw); //image path
        String []temp2=movieQuery.seperator(2,raw); // title
        String []temp3=movieQuery.seperator(3,raw); //vote
        String []temp4=desc.toArray(new String[1]);
        String []temp5 = id.toArray(new String[0]); //id
        for(int i=0;i<length;++i){

            //regiontest
            Log.e("temp1",temp4[i].toString());
            //endregion

            movieArray[i].setImgPath(temp1[i]);// set image path
            movieArray[i].setName(temp2[i].toString()); //set title
            movieArray[i].setVote(temp3[i].toString()); //set vote
            movieArray[i].setOverview(temp4[i]);  //set description
            movieArray[i].setId(temp5[i]);

        }
        count=length;

    }

    @Override
    public int getCount() {
        return count;
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
                    .inflate(resLayout,parent,false);
        }


        ImageView movieImageView = (ImageView) convertView.findViewById(resItem);

        Picasso.with(context).load(movieArray[position].getImgPath()).error(R.drawable.no_image).into(movieImageView);

        return convertView;


    }
}
