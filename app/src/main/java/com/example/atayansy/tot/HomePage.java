package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFoodFeedbacks;
import com.example.atayansy.tot.CustomAdapters.CustomExpandableListView;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.Food;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomePage extends BaseActivity {


    public CustomExpandableListView ExpAdapter;
    TextView welcome;
    SharedPreferences sharedPreferences;
    String username;

    JSONArray FoodArray = null;
    JSONArray CommentsArray = null;
    ArrayList<Food> food;
    private ArrayList<Comments> commentses;
    private ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_home_page);

        GetTopFood get = new GetTopFood();
        get.execute();

        /**Custom Adapter **/


        int noObjectsLevel1 = 1;
        int noObjectsLevel2 = 1;
        int noObjectsLevel3 = 1;


        ArrayList<FoodFeedFeedbacks> objectsLvl1 = new ArrayList<FoodFeedFeedbacks>();
        // objectsLvl1.add(new FoodFeedFeedbacks(R.drawable.food_temp1,commentses));

        for (int i = 0; i < noObjectsLevel1; i++) {
            ArrayList<Comments> objectsLvl2 = new ArrayList<Comments>();
            for (int j = 0; j < noObjectsLevel2; j++) {
                ArrayList<Comments> objectsLvl3 = new ArrayList<Comments>();
                for (int k = 0; k < noObjectsLevel3; k++) {
                    objectsLvl3.add(new Comments("user", "hello", null));
                }
                objectsLvl2.add(new Comments("user", "sds", objectsLvl3));
            }
            objectsLvl1.add(new FoodFeedFeedbacks(R.drawable.food_temp1, objectsLvl2));
        }

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.parent);

        ExpAdapter = new CustomExpandableListView(this);
        CustomAdapterFoodFeedbacks customAdapterFoodFeedbacks = new CustomAdapterFoodFeedbacks(HomePage.this, objectsLvl1);
        ExpAdapter.setAdapter(customAdapterFoodFeedbacks);
        parent.addView(ExpAdapter);

        /**End Custom Adapter **/


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

    // Codes that are not changed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }
    //  END  DATABASE

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

    //   DATABASE
    private class GetTopFood extends AsyncTask<String, Void, String> {

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);


            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "GetTopServlet").build();
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
            //check if result is null
            if (!s.equalsIgnoreCase("null")) {
                //Food
                try {
                    Food Foodtemp;
                    food = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(s);
                    // Getting JSON Array node
                    FoodArray = jsonObj.getJSONArray("Food");
                    for (int i = 0; i < FoodArray.length(); i++) {
                        JSONObject temp = FoodArray.getJSONObject(i);
                        Foodtemp = new Food();
                        Foodtemp.setFoodID(Integer.parseInt(temp.getString("foodID")));
                        Foodtemp.setDefinition(temp.getString("foodDescription"));
                        Foodtemp.setFoodName(temp.getString("foodName"));
                        Foodtemp.setPrice(Double.parseDouble(temp.getString("price")));
                        Foodtemp.setRating(Double.parseDouble(temp.getString("rating")));
                        Foodtemp.setFoodName(temp.getString("foodName"));
                        food.add(Foodtemp);
                    }

                    Comments Comments;
                    commentses = new ArrayList<>();
                    CommentsArray = jsonObj.getJSONArray("Comments");
                    for (int i = 0; i < CommentsArray.length(); i++) {
                        JSONObject temp = CommentsArray.getJSONObject(i);
                        Comments = new Comments();
                        Comments.setName(temp.getString("IDUser"));
                        Comments.setFoodID(Integer.parseInt(temp.getString("foodID")));
                        Comments.setComments(temp.getString("comments"));
                        commentses.add(Comments);
                    }

                } catch (JSONException e) {
                }
            } else {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }
    }
// end codes that are not changed
}

