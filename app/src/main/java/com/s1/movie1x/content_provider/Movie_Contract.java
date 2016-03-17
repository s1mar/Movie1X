package com.s1.movie1x.content_provider;

/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class Movie_Contract {


    public static final String AUTHORITY = "com.s1.realm.provider";
    public static final String PATH = "m";
    public static final String API_KEY=""; //Add your API KEY



    public abstract class MTable{

        //columns names
        public static final String MOVIE_TABLE_COLUMN_TITLE="Movie_Name";
        public static final String MOVIE_TABLE_COLUMN_RATING="Rating";
        public static final String MOVIE_TABLE_COLUMN_IMAGE_PATH="Image_Path";
        public static final String MOVIE_TABLE_COLUMN_TRAILER_LINK="Trailer";
        public static final String MOVIE_TABLE_COLUMN_SYN="Description";
    }


}
