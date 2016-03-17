package com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer;

import android.os.AsyncTask;
import android.util.Log;


import com.s1.movie1x.content_provider.Movie_Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by s1mar_000 on 03-03-2016.
 */
public class reviewAsync extends AsyncTask<String,Void,review[]> {

    final String id;
    URL url;
    review[] reviewList;
    reviewAdapter adapter;

    public reviewAsync(String id,reviewAdapter adapter) {
        this.id = id;
        this.adapter=adapter;

    }


    @Override
    protected review[] doInBackground(String... params) {

        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();
        String urlsi = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key="+ Movie_Contract.API_KEY;

        try {
            url = new URL(urlsi);
        } catch (MalformedURLException ex) {
            Log.e("urlsi", ex.getMessage());
        }

        Log.v("URL review Value", params[0].toString());
        BufferedReader reader = null;
        String mfetchedJson = " ";
        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(is);

            String line = "";
            while ((line = reader.readLine()) != null) {
                Log.v("lineV", line.toString());
                buffer.append(line.toString() + "\n");
            }

            if (buffer.length() == 0) {
                //empty stream;return nothing
                return null;
            } else {
                mfetchedJson = buffer.toString();
            }

        } catch (Exception ex) {
            Log.e("AsyncProcess1", ex.getMessage(), ex);
        } finally {

            try {
                if (connection != null) {

                    //state donates conn still open;close it
                    connection.disconnect();

                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                Log.e("AsyncProcess2", ex.getMessage(), ex);
            }
        }

        try {
            return getMovieDataFromJson(mfetchedJson);  // returns Object Array

        } catch (JSONException e) {
            Log.e("AsyncProcess3", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }


    protected review[] getMovieDataFromJson(String x) throws JSONException{

        final String MD_RESULTS = "results"; //array
        final String MD_AUTHOR = "author";
        final String MD_CONTENT = "content";
        final String MD_URL = "url";

        final String MD_TUBE = "youtube"; //array
        final String MD_SOURCE = "source";



            JSONObject alpha = new JSONObject(x);
            JSONArray results = alpha.getJSONArray(MD_RESULTS);

            int length=results.length();
            reviewList = new review[length];
            for (int i=0;i<length;++i){

                JSONObject root = results.getJSONObject(i);
                String author = root.getString(MD_AUTHOR);
                String content = root.getString(MD_CONTENT);

                reviewList[i] = new review();
                reviewList[i].setId(id);
                reviewList[i].setAuthor(author);
                reviewList[i].setContent(content);

                //region test
                Log.e("Review","Author "+reviewList[i].getAuthor());
                Log.e("Review","Content "+reviewList[i].getContent());
                //endregion
            }
                return reviewList;
        }

    @Override
    protected void onPostExecute(review[] reviews) {
        super.onPostExecute(reviews);

        adapter.update(reviews);
        adapter.notifyDataSetChanged();

    }
}



