package com.s1.movie1x;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.s1.movie1x.AsyncAdaptersAndSupportClasses.fav_adapters;
import com.s1.movie1x.content_provider.dataX;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by s1mar_000 on 17-03-2016.
 */
public class frag_favourite extends Fragment {


    RealmBaseAdapter<dataX> adapter;
    Realm myRealm;
    View GL;
    boolean isPhone;
    LaunchDetails mCallBack;
static interface LaunchDetails{

    void Launch(String name,boolean isTablet);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            isPhone = false;
        } else {
            isPhone = true;
        }


        if (isPhone) {
            return inflater.inflate(R.layout.frag_grid, container, false);
        }
        else {return inflater.inflate(R.layout.frag_list,container,false);}




    }




    @Override
    public void onStart() {
        super.onStart();
        myRealm = Realm.getDefaultInstance();
        RealmResults<dataX> results = myRealm.allObjects(dataX.class);
        adapter = new fav_adapters(getActivity(),results,true,R.layout.test2,R.id.iView2);

        if (isPhone) {
            GridView lv = (GridView) getView().findViewById(R.id.frag_menu_grid);
            GL = lv;
            lv.setAdapter(adapter);
        }else
        {
            ListView lv = (ListView)getView().findViewById(R.id.frag_list_view_menu);
            GL=lv;
            lv.setAdapter(adapter);

        }

    }

    @Override
    public void onResume() {
        super.onResume();


        if (isPhone) {
            GridView c = (GridView) GL;

            c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mCallBack.Launch(adapter.getItem(position).getMovie_Name(),!isPhone);
                }


            });

        } else {
            ListView lv = (ListView) GL;
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    mCallBack.Launch(adapter.getItem(position).getMovie_Name(),!isPhone);
                }
            });

        }


    }

    @Override
    public void onStop() {
        super.onStop();
        myRealm.close();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (LaunchDetails) activity;
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
}
