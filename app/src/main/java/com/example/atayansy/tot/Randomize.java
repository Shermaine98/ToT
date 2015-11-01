package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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

public class Randomize extends AppCompatActivity {
    ArrayList<Food> FoodList;
    ImageView iv_randomize;
    TextView tv_randomize;
    private Handler handler = new Handler();


    private double Distance;
    private double CurrLatitude;
    private double CurrLongitude;
    private int Budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        tv_randomize = (TextView) findViewById(R.id.tvrandomize);

        iv_randomize.setBackgroundResource(R.drawable.random_animation);
        //   tv_randomize.setBackgroundResource(R.drawable.text_animation);

        AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();

        // AnimationDrawable frameAnimation1 = (AnimationDrawable) tv_randomize.getBackground();

        frameAnimation.start();
        //    frameAnimation1.start();


        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setClass(getBaseContext(), ResultActivity.class);
                startActivity(i);
            }
        }, 4000);

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
                "Chicken chops with egg and garlic rice", 135.00,
                "University Mall, Taft Avenue, Manila City, Metro Manila", "Main Course", 4, 14.56, 120.99));


        //Get Chosen
        Budget = getIntent().getExtras().getInt("Budget");
        Distance = getIntent().getExtras().getDouble("Distance");
        CurrLatitude = getIntent().getExtras().getDouble("Latitude");
        CurrLongitude = getIntent().getExtras().getDouble("Longitude");

        //printing Console
        Log.d("Budget", String.valueOf(Budget));
        Log.d("Distance", String.valueOf(Distance));
        Log.d("Latitude", String.valueOf(CurrLatitude));
        Log.d("Longitude", String.valueOf(CurrLongitude));

    }

    //Sorting
    public void sort() {

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
