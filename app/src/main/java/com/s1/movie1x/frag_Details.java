package com.s1.movie1x;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.s1.movie1x.AsyncAdaptersAndSupportClasses.Movie;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer.review;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer.reviewAdapter;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer.reviewAsync;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer.trailerAdapter;
import com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer.trailerAsync;
import com.s1.movie1x.content_provider.Movie_Contract;
import com.s1.movie1x.content_provider.dataX;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;


/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class frag_Details extends Fragment {

    public static Movie movie;
    boolean isTablet;
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
    String imagePath;
    String id;
    private ShareActionProvider mShareActionProvider;
    reviewAdapter reviewAd;
    trailerAdapter trailerAd;



    protected  boolean getScreenConfig()
    {

        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp >=600){

            return true;
        }
        else
            return false;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isTablet)
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isTablet)
       getActivity().getActionBar().setDisplayShowCustomEnabled(true);

    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        reviewAd = new reviewAdapter(inflater.getContext(), R.layout.review, R.id.review_author, R.id.review_content, new review[0]);
        trailerAd = new trailerAdapter(inflater.getContext(), R.layout.review, R.id.review_author);
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);

        isTablet=getScreenConfig();


        if (movie != null) {
            imagePath = movie.getImgPath();

            Picasso.with(inflater.getContext()).load(imagePath).resize(400, 600).error(R.drawable.no_image).into(imageHolder);
            titleText.setText(movie.getName());
            descText.setText(movie.getOverview());
            voteText.setText(movie.getVote());
            id = movie.getId();
            if (titleText.getText().length() > 35) {
                titleText.setMaxWidth(600);

            } else {
                Log.e("FRAG_DETAILS", "INTENT NULL");
            }
        }

        new reviewAsync(id, reviewAd).execute("");
        new trailerAsync(id, trailerAd).execute("");

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menux, menu);
        MenuItem item = menu.findItem(R.id.is_fav);

        Realm myRealm = Realm.getDefaultInstance();
        try {

            dataX element = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE,titleText.getText().toString().trim()).findFirst();
            if(element!= null)
            {
                item.setChecked(true);
            }

        }
        catch (Exception ex)
        {
            Log.e("isChecked()",ex.getMessage());
        }
        finally {
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
                    int result = getActivity().getContentResolver().delete(ur,(titleText.getText().toString().trim()),null);
                    if(result!=1)
                    {Log.e("errDelFav","No Success");}

                   /* dataX obj = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE,titleText.getText().toString().trim()).findFirst();
                    if (obj!=null){

                        obj.removeFromRealm();
                    }
*/
                } catch (Exception ex) {
                    Log.e("ERRURIDEL", ex.getMessage(), ex);
                }


            } else {
                item.setChecked(true);
                //check it and add the entry to the database

                try {
                    Picasso.with(getActivity()).load(imagePath).into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            try {
                                String root = Environment.getExternalStorageDirectory().toString();
                                File myDir = new File(root + "/Movieee2");

                                if (!myDir.exists()) {
                                    myDir.mkdirs();
                                }

                                String name = titleText.getText() + ".jpg";
                                myDir = new File(myDir, name);
                                FileOutputStream out = new FileOutputStream(myDir);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            } catch (Exception e) {
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
                } catch (Exception ex) {
                    Log.e("Err", ex.getMessage());

                } finally {

                    try {


                        ContentValues vals = new ContentValues();
                        String root = Environment.getExternalStorageDirectory().toString();
                        String name = titleText.getText() + ".jpg";
                        String path = root + "/Movieee2/" + name;
                        vals.put(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE, titleText.getText().toString());
                        vals.put(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_RATING, voteText.getText().toString());
                        vals.put(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_SYN, descText.getText().toString());
                        vals.put(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_IMAGE_PATH, path);
                        vals.put(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TRAILER_LINK,trailerAd.objOnTop());
                        String uriX = "content://" + Movie_Contract.AUTHORITY + "/" + Movie_Contract.PATH;
                        Uri ur = Uri.parse(uriX);
                        Uri uri= getActivity().getContentResolver().insert(ur, vals);


                    } catch (Exception ex) {
                        Log.e("ERRURI", ex.getMessage(), ex);
                        Toast.makeText(getActivity(), "Some elements are still being downloaded,please try in a few sec's", Toast.LENGTH_LONG).show();
                        item.setChecked(false);
                    }
                }


            }


        }
        else if(id==R.id.menu_item_share){

            mShareActionProvider.setShareIntent(setShare());

        }




        return super.onOptionsItemSelected(item);
    }
    private Intent setShare() {
        Intent share = new Intent(Intent.ACTION_SEND);
        String x = "https://www.youtube.com/watch?v=" + trailerAd.getSource(0);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,"Hey check out this movie :"+"Name: "+titleText.getText().toString()+" \n here's the link :"+x);
        return share;
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }

    }

    @Override
    public void onStart() {
        super.onStart();


        ListView listView = (ListView) getView().findViewById(R.id.detail_review);
        listView.setAdapter(reviewAd);
        trailerLView.setAdapter(trailerAd);


        trailerLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String x = "https://www.youtube.com/watch?v=" + trailerAd.getSource(position);
                intent.setData(Uri.parse(x));
                startActivity(intent);


            }
        });


    }
}
