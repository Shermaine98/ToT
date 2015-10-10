package com.example.atayansy.tot.java;

/**
 * Created by shermainesy on 10/10/15.
 */
public class FavoriteObject {

    private int fPictureIcon;
    private String fName;
    private int fRatingStar;

    public FavoriteObject(int fPictureIcon, String fName, int fRatingStar) {
        this.fPictureIcon = fPictureIcon;
        this.fName = fName;
        this.fRatingStar = fRatingStar;
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

    public int getfRatingStar() {
        return fRatingStar;
    }

    public void setfRatingStar(int fRatingStar) {
        this.fRatingStar = fRatingStar;
    }
}
