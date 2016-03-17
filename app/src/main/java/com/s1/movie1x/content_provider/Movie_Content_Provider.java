package com.s1.movie1x.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class Movie_Content_Provider extends ContentProvider {

    Realm myRealm;
    @Override
    public boolean onCreate() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        myRealm= Realm.getDefaultInstance();
        return true ;
    }


    /* no need to provide any projection,we'll set it to null */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try{

            switch (selection) {

                case "title":
                    dataX result = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE, selectionArgs[0]).findFirst(); // getting movie with movie name=selectionArgs[0]
                    MatrixCursor matrixCursor = new MatrixCursor(new String[]{"Movie_Object"});
                    MatrixCursor.RowBuilder rowBuilder = matrixCursor.newRow();
                    rowBuilder.add(result);
                    return matrixCursor;


                case "default":                                //return every object
                    RealmResults<dataX> results = myRealm.allObjects(dataX.class);
                    MatrixCursor matrixCursor1 = new MatrixCursor(new String[]{"Movie_Object"});

                    for(dataX x : results){
                        MatrixCursor.RowBuilder rowBuilder1 = matrixCursor1.newRow();
                        rowBuilder1.add(x);

                    }
                    return matrixCursor1;

            }
        }catch (Exception ex){
            Log.e("CP",ex.getMessage());}
        finally {

        }
        return  null;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        try {


            /*Writing to the DataBase */
            myRealm.beginTransaction();
            dataX myObj = myRealm.createObject(dataX.class);
            myObj.setMovie_Name(values.getAsString(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE));
            myObj.setDescription(values.getAsString(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_SYN));
            myObj.setImage_Path(values.getAsString(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_IMAGE_PATH));
            myObj.setRating(values.getAsString(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_RATING));
            myObj.setTrailer(values.getAsString(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TRAILER_LINK));
            myRealm.commitTransaction();

            return Uri.parse("Success");

        }
        catch (Exception ex)
        {
            Log.e("CP",ex.getMessage());}
        finally {

        }
        return  null;

        }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
                                                    //using selection as a deletion argument for the movie_name column
        try{

            myRealm.beginTransaction();
            dataX obj = myRealm.where(dataX.class).equalTo(Movie_Contract.MTable.MOVIE_TABLE_COLUMN_TITLE,selection).findFirst();
            obj .removeFromRealm();
            myRealm.commitTransaction();
            return 1;
        }
        catch (Exception ex){
            Log.e("CP",ex.getMessage());
        }
        finally {

        }
return 0;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
