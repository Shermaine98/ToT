package com.example.atayansy.tot.java;

/**
 * Created by Geraldine on 10/31/2015.
 */
public class Food {
    String foodName;
    String restaurant;
    String definition;
    double price;
    String location;
    String type;
    int image;
    double latitue;
    double longtitude;

    public Food(String foodName, String restaurant, String definition, double price, String location, String type, int image, double latitue, double longtitude) {
        this.foodName = foodName;
        this.restaurant = restaurant;
        this.definition = definition;
        this.price = price;
        this.location = location;
        this.type = type;
        this.image = image;
        this.latitue = latitue;
        this.longtitude = longtitude;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

}
