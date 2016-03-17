package com.s1.movie1x;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.s1.movie1x.AsyncAdaptersAndSupportClasses.Movie;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.MovieAdapter;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.mAsyncTask;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.movieQuery;

/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class fragUISelect extends Fragment {

    private boolean isTablet;
    MovieAdapter movieAdapter;
    View GL;
    String choice;

    comm mCallBack;
     static interface comm{

         void callB(Movie movie,boolean isTablet);

     }


   public void populateTheGridORList(String choice) {

       this.choice = choice;
       Log.e("popGL",choice);

    }



    protected  boolean getScreenConfig()
    {

        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp >=600){

            return true;
        }
        else
            return false;

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        movieAdapter = new MovieAdapter(new String[0],inflater.getContext(),R.layout.test,R.id.iView);
        try {

            new mAsyncTask(movieAdapter).execute(new movieQuery().movieQueryGenerator(choice));
        }
        catch (Exception ex )
        {

            Log.e("FragUI_AT",ex.getMessage());
        }

        isTablet = getScreenConfig();
        if (isTablet){

            return inflater.inflate(R.layout.frag_list,container,false);
        }
        else {

            //is a  Phone
           return inflater.inflate(R.layout.frag_grid,container,false);

        }


    }

    @Override
    public void onStart() {
        super.onStart();

        if (isTablet)
        {
            ListView listView = (ListView)getView().findViewById(R.id.frag_list_view_menu);
            GL = listView;
            listView.setAdapter(movieAdapter);

        }
        else {

            GridView gridView = (GridView)getView().findViewById(R.id.frag_menu_grid);
            GL = gridView;
            gridView.setAdapter(movieAdapter);

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (comm) activity;
        }
        catch (ClassCastException ex)
        {
            Log.e("Class cast", activity.toString() + "isnt hooked up to the interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack=null; // avoid leaking
    }
    @Override
    public void onResume() {
        super.onResume();

        if(isTablet){

           ListView gridView = (ListView)GL;
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Movie movie= movieAdapter.getMovie(position);
                    mCallBack.callB(movie,(isTablet));

                }
            });


        }
        else {

            GridView gridView = (GridView)GL;
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                     Movie movie= movieAdapter.getMovie(position);
                     mCallBack.callB(movie,(isTablet=false));


                }
            });

        }






    }
}
