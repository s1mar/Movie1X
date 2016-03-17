package com.s1.movie1x;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.s1.movie1x.AsyncAdaptersAndSupportClasses.Movie;

import io.realm.Realm;

/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class main_activity extends Activity implements fragUISelect.comm,frag_favourite.LaunchDetails {


    Realm realm;


    @Override
    public void Launch(String name,boolean isTablet) {

        int container=0;

        if (isTablet) {

            container = R.id.Fragment_Container_2;
        }
        else {

            container = R.id.Fragment_Container_1;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        frag_DetailsPersist fragment = new frag_DetailsPersist();
        fragment.setMovieName(name);
        ft.replace(container,fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void callB(Movie movie,boolean isTablet) {

        int container=0;

        if (isTablet) {

            container = R.id.Fragment_Container_2;
        }
        else {

            container = R.id.Fragment_Container_1;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        frag_Details fragment = new frag_Details();
        fragment.movie = movie;
        ft.replace(container,fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }




    protected  void startFav()
    {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment frag_fav = new frag_favourite();
        fragmentTransaction.replace(R.id.Fragment_Container_1,frag_fav);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            realm = Realm.getDefaultInstance();


        setContentView(R.layout.activity_main); //static setup


        ActionBar actionBar = getActionBar();
        View spinnerM = getLayoutInflater().inflate(R.layout.spinner_menu, null);
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setCustomView(spinnerM);
        Spinner spinner = (Spinner) spinnerM.findViewById(R.id.spin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String choice = "";

                switch (position) {

                    case 1:
                        choice = "popular";
                        break;
                    case 2:
                        choice = "ratedR";
                        break;
                    case 3:
                        choice = "kidsPopular";
                        break;
                    case 4:
                        choice = "releaseOfTheYear";
                        break;
                    case 5:
                        choice = "Fav";

                        startFav();
                        return;

                    default:
                        choice = "popular";
                        break;

                }
                Log.e("ch", choice);
                startNorm(choice);
                Log.e("ch", choice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        FragmentTransaction f_transaction = getFragmentManager().beginTransaction();
        Fragment fragment;
        fragment = new fragUISelect();
        f_transaction.replace(R.id.Fragment_Container_1, fragment);
        f_transaction.addToBackStack(null);
        f_transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        f_transaction.commit();

/*
        View detailsFragmentCheck = (View) findViewById(R.id.Fragment_Container_2);

        if (detailsFragmentCheck != null) //meaning its a tablet
        {
            Log.e("Frag2","going in view");
            f_transaction = getFragmentManager().beginTransaction();
            fragment = new frag_Details();
            f_transaction.replace(R.id.Fragment_Container_2, fragment);
            f_transaction.addToBackStack(null);
            f_transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            f_transaction.commit();

        }

*/
    }


    void startNorm(String choice){

        fragUISelect fragment = new fragUISelect();
        fragment.populateTheGridORList(choice);
        Log.e("ch1", choice);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.Fragment_Container_1, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}

