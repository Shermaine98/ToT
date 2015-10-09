package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;

// TODO: FIX Navigation Design Home(TOP FOOD),favorites , RANDMOZIE, favorite, me(setting)
// TODO: List Item for Picture with ArrayList of comments (no like and etc buttons)
public class HomePage extends AppCompatActivity {
    private CustomAdpaterFoodFeed ExpAdapter;
    private ArrayList<FoodFeed> foodFeeds;
    private ExpandableListView ExpandList;
    private ImageButton ibButtonHome;
    private ImageButton ibButtonFavorite;
    private ImageButton ibButtonRandomize;
    private ImageButton ibButtonHistory;
    private ImageButton ibButtonLogOut;
    //Onclick listener for the Navigation Bar
    View.OnClickListener Navigation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(ibButtonHome)) {

            } else if (v.equals(ibButtonFavorite)) {
                i.setClass(getBaseContext(), Favorite.class);
            } else if (v.equals(ibButtonHome)) {
                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(ibButtonRandomize)) {
                i.setClass(getBaseContext(), Randomize.class);
            } else if (v.equals(ibButtonHistory)) {
                i.setClass(getBaseContext(), History.class);
            } else if (v.equals(ibButtonLogOut)) {
                //TODO: something code here to not crash on activity exit??
                i.setClass(getBaseContext(), MainActivity.class);
            }

            startActivity(i);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ExpandList = (ExpandableListView) findViewById(R.id.evFoodFeed);
        ibButtonHome = (ImageButton) findViewById(R.id.button_Home);
        ibButtonFavorite = (ImageButton) findViewById(R.id.button_favorites);
        ibButtonRandomize = (ImageButton) findViewById(R.id.button_randomize);
        ibButtonHistory = (ImageButton) findViewById(R.id.button_history);
        ibButtonLogOut = (ImageButton) findViewById(R.id.button_logout);


        //runs the function and returns the data to foodFeeds
        foodFeeds = SetStandardGroups();

        //Adapter for ExapadableListView
        ExpAdapter = new CustomAdpaterFoodFeed(HomePage.this, foodFeeds);
        ExpandList.setAdapter(ExpAdapter);

        ibButtonFavorite.setOnClickListener(Navigation);
        ibButtonRandomize.setOnClickListener(Navigation);
        ibButtonHome.setOnClickListener(Navigation);
        ibButtonHistory.setOnClickListener(Navigation);
        ibButtonLogOut.setOnClickListener(Navigation);

    }

    // Dummy data method for pictures and comments
    public ArrayList<FoodFeed> SetStandardGroups() {

        String names[] = {"Geraldine", "Marielle", "Gina", "Bryan",
                "Pat", "Eugene", "Shermaine", "Kook"};

        String comments[] = {"TasteGood", "Nah", "DONT EAT HERE", "Cameroon",
                "Nice place", "chill", "woah Spain", "lalala"};

        int Images[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher
        };

        ArrayList<FoodFeed> list = new ArrayList<FoodFeed>();

        ArrayList<Comments> comments_list;


        for (int images : Images) {
            FoodFeed gru = new FoodFeed();
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
