package com.example.atayansy.tot.java;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shermainesy on 10/10/15.
 */
public class FavoriteObject implements Serializable {

    private int fPictureIcon;
    private String fName;
    private double fRatingStar;
    private String description;
    private int price;
    private int foodID;
    private String address;
    private String restaurantName;
    private ArrayList<Comments> comments;

    public FavoriteObject(){

    }

    public FavoriteObject(int fPictureIcon, String fName, double fRatingStar, String description, int price, int foodID) {
        this.fPictureIcon = fPictureIcon;
        this.fName = fName;
        this.fRatingStar = fRatingStar;
        this.description = description;
        this.price = price;
        this.foodID = foodID;
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

    public double getfRatingStar() {
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

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
