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
public class trailerAsync extends AsyncTask<String,Void,trailer[]> {

    URL url;
    final String id;
    trailer[] trailerList;
    trailerAdapter adapter;
    public trailerAsync(String id,trailerAdapter adapter){
        this.id=id;
        this.adapter=adapter;
    }


    @Override
    protected trailer[] doInBackground(String... params) {

        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();
        String urlsi = "https://api.themoviedb.org/3/movie/"+id+"/trailers?api_key="+ Movie_Contract.API_KEY;

        try {
            url = new URL(urlsi);
        } catch (MalformedURLException ex) {
            Log.e("urlsi", ex.getMessage());
        }

        Log.v("URL trailer Value", params[0].toString());
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

    protected trailer[] getMovieDataFromJson(String x) throws JSONException{

        final String MD_RESULTS = "results"; //array
        final String MD_AUTHOR = "author";
        final String MD_CONTENT = "content";
        final String MD_URL = "url";

        final String MD_TUBE = "youtube"; //array
        final String MD_SOURCE = "source";

            JSONObject alpha = new JSONObject(x);
            JSONArray tube = alpha.getJSONArray(MD_TUBE);

            trailerList = new trailer[tube.length()];

            for(int i=0;i<tube.length();++i)
            {
                JSONObject root = tube.getJSONObject(i);
                String source = root.getString(MD_SOURCE);

                trailerList[i] = new trailer();
                trailerList[i].setSource(source);
                trailerList[i].setId(id);
            }

            return trailerList;
        }

    @Override
    protected void onPostExecute(trailer[] trailers) {
        super.onPostExecute(trailers);

        if (trailers != null) {
            adapter.update(trailers);
            adapter.notifyDataSetChanged();

        }
    }
}



