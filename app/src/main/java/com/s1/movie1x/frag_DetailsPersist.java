package com.s1.movie1x;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.s1.movie1x.content_provider.Movie_Contract;
import com.s1.movie1x.content_provider.dataX;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class frag_DetailsPersist extends Fragment {

    Realm myRealm;
    String name;
    @Bind(R.id.detail_image_id)
    ImageView imageHolder;
    @Bind(R.id.detail_text_title)
    TextView titleText;
    @Bind(R.id.detail_text_voting)
    TextView voteText;
    @Bind(R.id.detail_text_desc)
    TextView descText;
    @Bind(R.id.detail_trailers)
    ListView trailerLView;


    public void setMovieName(String name) {
        this.name = name;

    }


    private ShareActionProvider mShareActionProvider;


    public void onDetach() {
        super.onDetach();
        myRealm.close();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRealm = Realm.getDefaultInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        dataX obj = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE, name).findFirst();

        Picasso.with(inflater.getContext()).load("file:///" + obj.getImage_Path()).error(R.drawable.no_image).into(imageHolder); //image set
        titleText.setText(obj.getMovie_Name());
        descText.setText(obj.getDescription());
        if (titleText.getText().length() > 35) {
            titleText.setMaxWidth(600);
        }
        voteText.setText(obj.getRating());
        setHasOptionsMenu(true);
/*All walues set*/
        return root;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menux, menu);
        MenuItem item = menu.findItem(R.id.is_fav);

        Realm myRealm = Realm.getDefaultInstance();
        try {

            dataX element = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE, titleText.getText().toString().trim()).findFirst();
            if (element != null) {
                item.setChecked(true);
            }

        } catch (Exception ex) {
            Log.e("isChecked()", ex.getMessage());
        } finally {
            myRealm.close();

        }

        MenuItem item2 = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item2.getActionProvider();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.is_fav) {
            if (item.isChecked()) {
                item.setChecked(false);
                //uncheck it and remove the entry from the database
                try {
                    String uriX = "content://" + Movie_Contract.AUTHORITY + "/" + Movie_Contract.PATH;
                    Uri ur = Uri.parse(uriX);
                    int result = getActivity().getContentResolver().delete(ur, (titleText.getText().toString().trim()), null);
                    if (result != 1) {
                        Log.e("errDelFav", "No Success");
                    }
                    myRealm.close();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    Fragment frag_fav = new frag_favourite();
                    fragmentTransaction.replace(R.id.Fragment_Container_1,frag_fav);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();

                } catch (Exception ex) {
                    Log.e("ERRURIDEL", ex.getMessage(), ex);
                }


            }
        }
        return super.onOptionsItemSelected(item);

    }

}



