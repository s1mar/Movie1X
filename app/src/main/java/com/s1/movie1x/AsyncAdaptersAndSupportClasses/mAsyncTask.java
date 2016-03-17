package com.s1.movie1x.AsyncAdaptersAndSupportClasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



/**
 * Created by s1mar_000 on 02-03-2016.
 */
public class mAsyncTask extends AsyncTask<URL,Void,String[]> {

    private ArrayList<String> path1;
    private ArrayList<String> title2;
    private ArrayList<String> vote3;
    private ArrayList<String> resultsOverview;
    private ArrayList<String>resultsID;
    MovieAdapter adapter;

    public mAsyncTask(MovieAdapter adapter){
        this.adapter=adapter;
    }

    public ArrayList<String> getDataList(int selector)
    {
        try {

            switch (selector) {
                case 1:
                    return path1;

                case 2:
                    return title2;

                case 3:
                    return vote3;

                case 4:
                    return resultsOverview;

            }

        }catch (Exception ex){
            Log.e("getDataList", ex.getMessage(), ex);
        }

        return null;
    }

    protected String[] doInBackground(URL... params) {

            HttpURLConnection connection = null;
            StringBuffer buffer = new StringBuffer();
            URL url = params[0];
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
                return getMovieDataFromJson(mfetchedJson);  // returns String Array

            } catch (JSONException e) {
                Log.e("AsyncProcess3", e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

    //region json process
    protected String[] getMovieDataFromJson(String fetchedDataMovies) throws JSONException {

// These are the names of the JSON objects that need to be extracted.
        final String MD_RESULTS = "results";
        final String MD_IMAGE_POSTER = "poster_path";
        final String MD_RATING = "adult";
        final String MD_OVERVIEW = "overview";
        final String MD_RELEASE_DATE = "release_date";
        final String MD_IMAGE_BACKDROP = "backdrop_path";
        final String MD_VOTE = "vote_average";
        final String MD_TITLE = "title";
        final String MD_ID = "id";

        JSONObject fetchedDataMoviesJson = new JSONObject(fetchedDataMovies);
        JSONArray movieArray = fetchedDataMoviesJson.getJSONArray(MD_RESULTS);

        ArrayList<String> resultStrs = new ArrayList<String>();
        resultsOverview=new ArrayList<String>();
        resultsID = new ArrayList<String>();

        for (int i = 0; i < movieArray.length(); i++) {

            // Get the JSON object representing the day
            JSONObject movieNumber = movieArray.getJSONObject(i);
            String movieImage = movieNumber.getString(MD_IMAGE_POSTER);
            String movieTitle = movieNumber.getString(MD_TITLE);
            String movieVote = movieNumber.getString(MD_VOTE);
            String movieID = movieNumber.getString(MD_ID);
            //for description
            String movieOverview = movieNumber.getString(MD_OVERVIEW);
            resultsOverview.add(i,movieOverview);
            resultsID.add(i,movieID);


            String result = movieImage + "'" + movieTitle + "'" + movieVote;

            resultStrs.add(i, result);

        }
        String[] resultsArray = new String[movieArray.length()];

        int index = 0;
        for (String s : resultStrs) {
            resultsArray[index] = s;
            index += 1;

        }

        return resultsArray;
    }

    //endregion


    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        if (strings!=null){

          //  adapter.clear();
            adapter.update(strings, resultsOverview,resultsID);
            adapter.notifyDataSetChanged();
        }

    }


}
