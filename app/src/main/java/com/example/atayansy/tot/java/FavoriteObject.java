package com.example.atayansy.tot.java;

import java.io.Serializable;

/**
 * Created by shermainesy on 10/10/15.
 */
public class FavoriteObject implements Serializable {

    private int fPictureIcon;
    private String fName;
    private float fRatingStar;
    private String description;
    private int price;

    public FavoriteObject(int fPictureIcon, String fName, float fRatingStar, String description, int price) {
        this.fPictureIcon = fPictureIcon;
        this.fName = fName;
        this.fRatingStar = fRatingStar;
        this.description = description;
        this.price = price;
    }

    public int getfPictureIcon() {
        return fPictureIcon;
    }

    public void setfPictureIcon(int fPictureIcon) {
        this.fPictureIcon = fPictureIcon;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public float getfRatingStar() {
        return fRatingStar;
    }

    public void setfRatingStar(float fRatingStar) {
        this.fRatingStar = fRatingStar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
