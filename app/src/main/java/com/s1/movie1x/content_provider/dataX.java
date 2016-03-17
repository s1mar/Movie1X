package com.s1.movie1x.content_provider;

import com.s1.movie1x.AsyncAdaptersAndSupportClasses.Movie;

import io.realm.RealmObject;

/**
 * Created by s1mar_000 on 16-03-2016.
 */
public class dataX extends RealmObject {

    private String Movie_Name;
    private String Description;
    private String Image_Path;
    private String Rating;
    private String Trailer;   // a link to the trailer

    public dataX() {}

    public void setMovie_Name(String Movie_Name)
    {
        this.Movie_Name = Movie_Name;

    }

    public Movie getMovieObj()
    {

        Movie movie = new Movie();
        movie.setName(getMovie_Name());
        movie.setImgPath(getImage_Path());
        movie.setVote(getRating());
        movie.setOverview(getDescription());

        return movie;

    }

    public String getMovie_Name() {
        return Movie_Name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setImage_Path(String image_Path) {
        Image_Path = image_Path;
    }

    public String getImage_Path() {
        return Image_Path;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getRating() {
        return Rating;
    }

    public void setTrailer(String trailer) {
        Trailer = trailer;
    }

    public String getTrailer() {
        return Trailer;
    }
}
