package com.example.atayansy.tot;

/**
 * Created by shermainesy on 10/8/15.
 */
public class Comments {

    private String name;
    private String comments;

    public Comments(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    public Comments() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
