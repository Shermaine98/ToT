package com.example.atayansy.tot.java;

import java.util.ArrayList;

/**
 * Created by shermainesy on 10/8/15.
 */
public class Comments {

    private String name;
    private String comments;
    private ArrayList<Comments> commentses;

    public Comments(String name, String comments, ArrayList<Comments> commentses) {
        this.name = name;
        this.comments = comments;
        this.setCommentses(commentses);
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

    public ArrayList<Comments> getCommentses() {
        return commentses;
    }

    public void setCommentses(ArrayList<Comments> commentses) {
        this.commentses = commentses;
    }
}
