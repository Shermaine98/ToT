package com.example.atayansy.tot;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFoodFeedbacks;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;

import java.util.ArrayList;

// TODO: FIX Navigation Design Home(TOP FOOD),favorites , RANDMOZIE, favorite, me(setting)
public class HomePage extends BaseActivity {


    private CustomAdapterFoodFeedbacks ExpAdapter;
    private ArrayList<FoodFeedFeedbacks> foodFeedFeedbacks;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_home_page);

        ExpandList = (ExpandableListView) findViewById(R.id.evFoodFeed);
        //runs the function and returns the data to foodFeedFeedbacks
        foodFeedFeedbacks = SetStandardGroups();

        //Adapter for ExapadableListView
        ExpAdapter = new CustomAdapterFoodFeedbacks(HomePage.this, foodFeedFeedbacks);
        ExpandList.setAdapter(ExpAdapter);
        //   ExpandList.setOnChildClickListener();

    }

    // Dummy data method for pictures and comments
    public ArrayList<FoodFeedFeedbacks> SetStandardGroups() {

        String names[] = {"Geraldine", "Marielle", "Gina", "Bryan",
                "Pat", "Eugene", "Shermaine", "Kook"};

        String comments[] = {"TasteGood", "Nah", "DONT EAT HERE", "Cameroon",
                "Nice place", "chill", "woah Spain", "lalala"};

        int Images[] = {R.drawable.food_temp1, R.drawable.food_temp};

        ArrayList<FoodFeedFeedbacks> list = new ArrayList<FoodFeedFeedbacks>();

        ArrayList<Comments> comments_list;


        for (int images : Images) {
            FoodFeedFeedbacks gru = new FoodFeedFeedbacks();
            gru.setIcon(images);

            comments_list = new ArrayList<Comments>();
            for (int j = 0; j < 4; j++) {
                Comments comments1 = new Comments();
                comments1.setName(names[j]);
                comments1.setComments(comments[j]);

                comments_list.add(comments1);
            }
            gru.setComments(comments_list);
            list.add(gru);
        }

        return list;
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
