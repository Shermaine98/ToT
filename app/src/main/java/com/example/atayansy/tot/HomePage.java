package com.example.atayansy.tot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

// TODO: FIX Navigation Design Home(TOP FOOD),favorites , RANDMOZIE, favorite, me(setting)
// TODO: List Item for Picture with ArrayList of comments (no like and etc buttons)
public class HomePage extends AppCompatActivity {
    ListView lvFoodFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        lvFoodFeed = (ListView) findViewById(R.id.lv_foodfeed);
        ArrayList<FoodFeed> foodFeedArrayList = new ArrayList<>();


        CustomAdpaterFoodFeed customAdpaterFoodFeed = new CustomAdpaterFoodFeed(getBaseContext(), R.layout.foodfeed_list_view, foodFeedArrayList);
        lvFoodFeed.setAdapter(customAdpaterFoodFeed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
