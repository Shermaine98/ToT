package com.example.atayansy.tot.java;

/**
 * Created by shermainesy on 10/10/15.
 */
public class Favorite {

    private int pictureIcon;
    private String name;
    private int ratingStar;

    public Favorite(int pictureIcon, String name, int ratingStar) {
        this.pictureIcon = pictureIcon;
        this.name = name;
        this.ratingStar = ratingStar;
    }

    public int getPictureIcon() {
        return pictureIcon;
    }

    public void setPictureIcon(int pictureIcon) {
        this.pictureIcon = pictureIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }
}
