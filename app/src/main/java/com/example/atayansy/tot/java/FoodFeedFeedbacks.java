package com.example.atayansy.tot.java;

import java.util.ArrayList;

/**
 * Created by shermainesy on 10/8/15.
 */
public class FoodFeedFeedbacks {
    /**
     * static final String TABLE_Name = "";
     * static final String COLUMN_PICTURE = "";
     * static final ArrayList<String> COLUMN_COMMENTS = "";
     * static final int COLUMN_ICON = "";
     **/

    private int icon;
    private ArrayList<Comments> comments;

    public FoodFeedFeedbacks(int icon, ArrayList<Comments> comments) {
        this.icon = icon;
        this.comments = comments;
    }

    public FoodFeedFeedbacks() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }
}
