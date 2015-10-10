package com.example.atayansy.tot.java;

/**
 * Created by shermainesy on 10/10/15.
 */
public class HistoryObject {

    private int hPictureIcon;
    private String hName;
    private int rRatingStar;

    public HistoryObject(int hPictureIcon, String hName, int rRatingStar) {
        this.hPictureIcon = hPictureIcon;
        this.hName = hName;
        this.rRatingStar = rRatingStar;
    }

    public int gethPictureIcon() {
        return hPictureIcon;
    }

    public void sethPictureIcon(int hPictureIcon) {
        this.hPictureIcon = hPictureIcon;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public int getrRatingStar() {
        return rRatingStar;
    }

    public void setrRatingStar(int rRatingStar) {
        this.rRatingStar = rRatingStar;
    }
}
