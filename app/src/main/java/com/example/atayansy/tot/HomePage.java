package com.example.atayansy.tot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFoodFeedbacks;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.User;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

// TODO: FIX Navigation Design Home(TOP FOOD),favorites , RANDMOZIE, favorite, me(setting)
public class HomePage extends BaseActivity {


    private CustomAdapterFoodFeedbacks ExpAdapter;
    private ArrayList<FoodFeedFeedbacks> foodFeedFeedbacks;
    private ExpandableListView ExpandList;
    TextView welcome;

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

        welcome = (TextView) findViewById(R.id.welcomeText);

//        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
//        String user = prefs.getString("user", null);
//        JSONObject json = null;
//        try {
//            json = new JSONObject(user);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            welcome.append(json.getString("username") + "!");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

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
