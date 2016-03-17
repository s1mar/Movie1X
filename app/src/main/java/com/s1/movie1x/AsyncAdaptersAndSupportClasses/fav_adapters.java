package com.s1.movie1x.AsyncAdaptersAndSupportClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.s1.movie1x.R;
import com.s1.movie1x.content_provider.dataX;
import com.squareup.picasso.Picasso;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by s1mar_000 on 17-03-2016.
 */
public class fav_adapters extends RealmBaseAdapter<dataX> {

    Context currentContext;
    int layout;
    RealmResults<dataX> results;
    int element;
    int size;

    public fav_adapters(Context context, RealmResults<dataX> realmResults, boolean automaticUpdate,int layoutToExpand,int elementInLayout) {
        super(context, realmResults, automaticUpdate);
        currentContext = context;
        layout=layoutToExpand;
        results=realmResults;
        element=elementInLayout;

    }

    private boolean notifyChange()
    {
        if(results.size()!=size)
        { return true;}
        else
        {return false;}

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if ((convertView==null)){
            convertView = LayoutInflater.from(currentContext).inflate(layout,parent,false);
        }

        ImageView movieImageView = (ImageView) convertView.findViewById(element);
        String path="file://"+results.get(position).getImage_Path();
        Picasso.with(currentContext).load(path).error(R.drawable.no_image).into(movieImageView);
        return convertView;

    }
}
