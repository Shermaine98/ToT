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
    float latitude;
    float longitude;
    private int foodID;
    private double rating;


    public Food(String foodName, String restaurant, String definition, double price, String location, String type, int image, float latitude, float longitude) {
        this.foodName = foodName;
        this.restaurant = restaurant;
        this.definition = definition;
        this.price = price;
        this.location = location;
        this.type = type;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Food() {

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

    public double getlongitude() {
        return longitude;
    }

    public void setlongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitue() {
        return latitude;
    }

    public void setLatitue(float latitude) {
        this.latitude = latitude;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
