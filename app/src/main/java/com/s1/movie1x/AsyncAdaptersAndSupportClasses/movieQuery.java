package com.s1.movie1x.AsyncAdaptersAndSupportClasses;

import android.text.format.Time;
import android.util.Log;

import com.s1.movie1x.content_provider.Movie_Contract;

import java.net.URL;

/**
 * Created by s1mar_000 on 27-01-2016.
 */
public class movieQuery {

    // member variables

    private String MovieQuery = "";
    private URL urlMovieQuery = null;

    public movieQuery() {
        movieQueryGenerator("default");

    }

    protected String year() {
       Time now =new Time();
        now.setToNow();
        String date = now.toString();
        Log.v("Year",date.substring(0,4));
        return date.substring(0,4);



    }

    public URL getUrlMovieQuery(){

        if (urlMovieQuery == null)
        {
            return movieQueryGenerator("default");

        }
        else
            return urlMovieQuery;

    }

    public URL movieQueryGenerator(String cat) {


        final String API_KEY = Movie_Contract.API_KEY;
        String optionAppend = "/discover/movie?sort_by=popularity.desc";
        final String BASE_STRING = "http://api.themoviedb.org/3";

        try {

            switch (cat) {

                case "popular":
                    optionAppend = "/discover/movie?sort_by=popularity.desc";     /*Most popular movies*/
                    break;
                case "ratedR":
                    optionAppend = "/discover/movie/?certification_country=US&certification=R&sort_by=vote_average.desc"; //Best R Rated Movies
                    break;
                case "kidsPopular":
                    optionAppend = "/discover/movie?certification_country=US&certification.lte=G&sort_by=popularity.desc"; //Popular Kids Movies
                    break;
                case "releaseOfTheYear":
                    optionAppend = "/discover/movie?primary_release_year=" + year() + "&sort_by=vote_average.desc"; // Release(s) of the year
                    break;
                default:
                    optionAppend = "/discover/movie?sort_by=popularity.desc";//Most Popular Movies
                    break;

            }



            MovieQuery = (BASE_STRING + optionAppend + "&api_key="+Movie_Contract.API_KEY); // returning the final string
            Log.v("MovieQuery", MovieQuery);
            urlMovieQuery = new URL(MovieQuery);
            return urlMovieQuery;
        } catch (Exception e) {

            Log.e("MovieQuery", e.toString());
        } finally {

            return urlMovieQuery;
        }


    }

    public static String[] seperator(int i, String[] y) {
        String[] result = new String[y.length];
        int index = 0;

        switch (i) {                             //1. for image path
            case 1:
                for (String s : y) {
                    for (String v : s.split("'",2)) {
                        result[index] = "http://image.tmdb.org/t/p/w185/" + v;
                        break;
                    }

                    index+=1;

                }

                break;
            case 2:                             //2.for title
                for (String s : y) {
                    int c = 1;
                    for (String v : s.split("'", 3)) {
                        if(c==3){break;}
                        else {
                            result[index] = v;
                        }
                        c+=1;
                    }

                    index++;

                }
                break;

            case 3:                                         //3. for Vote
                for (String s : y) {
                    int c = 1;
                    for (String v : s.split("'", 3)) {
                        if(c==4){break;}
                        else {
                            result[index] = v;
                        }
                        c+=1;
                    }

                    index++;

                }
                break;

            default:
                for (String s : y) {
                    for (String v : s.split("'",2)) {
                        result[index] = "http://image.tmdb.org/t/p/w185/" + v;
                        break;
                    }

                    index+=1;

                }
                break;

        }
        return result;

    }


}


