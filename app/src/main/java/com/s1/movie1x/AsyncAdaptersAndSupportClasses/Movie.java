package com.s1.movie1x.AsyncAdaptersAndSupportClasses;

/**
 * Created by s1mar_000 on 02-03-2016.
 */
public class Movie {

    String name;
    String vote;
    String rating;
    String overview;
    String imgPath;
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getRating() {
        return rating;
    }

    public String getVote() {
        return vote;
    }
}
