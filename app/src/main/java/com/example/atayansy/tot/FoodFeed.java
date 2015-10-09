package com.example.atayansy.tot;

import java.util.ArrayList;

/**
 * Created by shermainesy on 10/8/15.
 */
public class FoodFeed {
    /**
     * static final String TABLE_Name = "";
     * static final String COLUMN_PICTURE = "";
     * static final ArrayList<String> COLUMN_COMMENTS = "";
     * static final int COLUMN_ICON = "";
     **/

    private int icon;
    private ArrayList<Comments> comments;

    public FoodFeed(int icon, ArrayList<Comments> comments) {
        this.icon = icon;
        this.comments = comments;
    }

    public FoodFeed() {
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
