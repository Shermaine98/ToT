package com.example.atayansy.tot;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atayansy.tot.java.Food;

import java.util.ArrayList;
import java.util.Random;

public class Randomize extends AppCompatActivity {
    ArrayList<Food> FoodList;
    ImageView iv_randomize;
    TextView tv_randomize;
    ArrayList<Food> FilteredResult;
    int result;
    boolean x = true;
    Random random = new Random();
    private Handler handler = new Handler();
    private double Distance;
    private double CurrLatitude;
    private double CurrLongitude;
    private int Budget;
    private boolean location_spinner;
    private boolean budget_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        tv_randomize = (TextView) findViewById(R.id.tvrandomize);

        //  iv_randomize.setBackgroundResource(R.drawable.random_animation);
//        tv_randomize.setBackgroundResource(R.drawable.text_animation);

        //     AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();

        //  AnimationDrawable frameAnimation1 = (AnimationDrawable) tv_randomize.getBackground();

        //   frameAnimation.start();
        //    frameAnimation1.start();

        //TODO: delete if database


        FoodList = new ArrayList<>();
        FoodList.add(new Food("Chicken Katsu", "Crazy Katsu",
                "Breaded, deep-fried chicken cutlet served with shredded lettuce and rice",
                155.00, "Ermita One Archers Place, Taft Avenue, Manila", "Main Course", 1, 14.57, 120.98));
        FoodList.add(new Food("Tonkatsu", "Crazy Katsu",
                "Breaded, deep-fried pork cutlet served with shredded lettuce and rice",
                150.00, "Ermita One Archers Place, Taft Avenue, Manila", "Main Course", 2, 14.57, 120.98));
        FoodList.add(new Food("Katsu Curry", "Crazy Katsu",
                "Breaded pork cutlet in curry sauce served with rice",
                190.00, "Ermita One Archers Place, Taft Avenue, Manila", "Main Course", 2, 14.57, 120.99));


        FoodList.add(new Food("Breakfast Beef Bulgogi Ricebox", "Chicken Bonchon - Taft (University Mall)",
                "Beef bulgogi with egg and rice", 135.00,
                "University Mall, Taft Avenue, Manila City, Metro Manila", "Main Course", 3, 14.56, 120.99));
        FoodList.add(new Food("Breakfast Chops Ricebox", "Chicken Bonchon - Taft (University Mall)",
                "Chicken chops with egg and garlic rice", 50.00,
                "University Mall, Taft Avenue, Manila City, Metro Manila", "Main Course", 4, 14.56, 120.99));


        //Get Chosen
        location_spinner = getIntent().getExtras().getBoolean("location_spinner");
        budget_spinner = getIntent().getExtras().getBoolean("Budget_spinner");
        Budget = getIntent().getExtras().getInt("Budget");
        Distance = getIntent().getExtras().getFloat("Distance");
        CurrLatitude = getIntent().getExtras().getDouble("Latitude");
        CurrLongitude = getIntent().getExtras().getDouble("Longitude");

        //printing Console
//TODO: delete
        Log.e("location_spinner", String.valueOf(location_spinner));
        Log.e("Budget_spinner", String.valueOf(budget_spinner));
        Log.e("Budget", String.valueOf(Budget));
        Log.e("Distance", String.valueOf(Distance));
        Log.e("Latitude", String.valueOf(CurrLatitude));
        Log.e("Longitude", String.valueOf(CurrLongitude));
        sort();
    }

    //Sorting
    public void sort() {

        FilteredResult = new ArrayList<>();
        if (location_spinner == false && budget_spinner == false) {
            randomize();
        }
        //sort/filter budget then randomize
        else if (location_spinner == false && budget_spinner == true) {
            filterBudget();
        }
        //sort/filter location then randomize
        else if (location_spinner == true && budget_spinner == false) {
            filterLocation();
        } else {
            //sort/filter location and budget randmize
            //TODO:

        }

    }

    //sort/filter location then randomize
    public void filterLocation() {
        Location currentLocation = new Location("Current Location");
        currentLocation.setLatitude(CurrLatitude);
        currentLocation.setLongitude(CurrLongitude);

        for (int i = 0; i < FoodList.size(); i++) {
            Location otherLocation = new Location("Other Location");
            otherLocation.setLatitude(FoodList.get(i).getLatitue());
            otherLocation.setLongitude(FoodList.get(i).getLongtitude());
            float distanceResult = currentLocation.distanceTo(otherLocation);
            if (Distance <= distanceResult) {
                FilteredResult.add(FoodList.get(i));
                x = true;
            } else {
                x = false;
            }
        }
        if (x) {
            result = random.nextInt(FilteredResult.size());
            intent(FilteredResult);
        } else {
            intent();
        }
    }

    //sort/filter budget then randomize
    public void filterBudget() {
        for (int i = 0; i < FoodList.size(); i++) {
            if (FoodList.get(i).getPrice() <= Budget) {
                FilteredResult.add(FoodList.get(i));
                x = true;
            } else {
                x = false;
            }
        }
        if (x) {
            result = random.nextInt(FilteredResult.size());
            intent(FilteredResult);
        } else {
            intent();
        }
    }

    public void randomize() {
        result = random.nextInt(FoodList.size());
        intent(FoodList);
    }

    public void intent(ArrayList<Food> FoodResult) {
        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        i.putExtra("Result", FoodResult.get(result).getFoodName());
        startActivity(i);
        finish();
    }

    public void intent() {
        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        i.putExtra("Result", "No Result");
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_randomize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
