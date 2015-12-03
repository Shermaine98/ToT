package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFoodFeedbacks;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.Food;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.User;
import com.google.gson.JsonParser;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomePage extends BaseActivity {


    TextView welcome;
    SharedPreferences sharedPreferences;
    String username;
    private CustomAdapterFoodFeedbacks ExpAdapter;
    private ArrayList<FoodFeedFeedbacks> foodFeedFeedbacks;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_home_page);
        ExpandList = (ExpandableListView) findViewById(R.id.evFoodFeed);


        //   ExpandList.setOnChildClickListener();

        GetTopFood get = new GetTopFood();
        get.execute();

        //runs the function and returns the data to foodFeedFeedbacks
        foodFeedFeedbacks = SetStandardGroups();

        //Adapter for ExapadableListView
        ExpAdapter = new CustomAdapterFoodFeedbacks(HomePage.this, foodFeedFeedbacks);
        ExpandList.setAdapter(ExpAdapter);
     /* Shared Preferences */
        welcome = (TextView) findViewById(R.id.welcomeText);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        if (username.isEmpty()) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } else {
            onResume();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        welcome.setText("Welcome, " + username + "!");

    }

    JSONArray usernames;
    JSONArray comments;
    JSONArray FoodName;
    JSONArray FoodDesc;
    JSONArray Images;

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

    //   DATABASE
    private class GetTopFood extends AsyncTask<String, Void, String> {

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters
            RequestBody requestbody = new FormEncodingBuilder().build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "GetTopServlet").post(requestbody).build();
            String result = "";

            try {
                //Run
                response = okHttpClient.newCall(request).execute();
                //get the page body
                result = response.body().string();
                Log.i("result", result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ENTER", "POSTEXCUTE");
            //check if result is null
            if (!s.equalsIgnoreCase("null")) {
                try {
                    //getting result body and converting to JSON
                    Log.i("ENTER", "AFTER TRY");
                    Log.i("ENTER", s);
                    JSONObject jo = new JSONObject(s);

                    //Setting Json variable
                    usernames = jo.getJSONArray("username");
                    comments = jo.getJSONArray("comments");
                    FoodName = jo.getJSONArray("FoodName");
                    FoodDesc = jo.getJSONArray("FoodDescription");
                    Images = jo.getJSONArray("Picture");

                } catch (JSONException e) {
                }
            } else {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }
    }
    //  END  DATABASE

    // Codes that are not changed
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
// end codes that are not changed
}
