package com.s1.movie1x.AsyncAdaptersAndSupportClasses.reviewtrailer;

/**
 * Created by s1mar_000 on 03-03-2016.
 */
public class review {

    String id; //movie id
    String author;
    String content;
    String url;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
