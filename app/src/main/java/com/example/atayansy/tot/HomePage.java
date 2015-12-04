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


    public CustomAdapterFoodFeedbacks ExpAdapter;
    TextView welcome;
    SharedPreferences sharedPreferences;
    String username;

    JSONArray FoodArray = null;
    JSONArray CommentsArray = null;
    ArrayList<FoodFeedFeedbacks> foodFeedFeedbacks;
    ArrayList<Comments> comments;
    private ExpandableListView ExpandList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_home_page);
        foodFeedFeedbacks = new ArrayList<>();
        ExpandList = (ExpandableListView) findViewById(R.id.evFoodFeed);

        GetTopFood get = new GetTopFood();
        get.execute();


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
        /*Shared Preference End*/

    }


    public void ShowView() {

        ExpAdapter = new CustomAdapterFoodFeedbacks(getBaseContext(), foodFeedFeedbacks);
        ExpandList.setAdapter(ExpAdapter);

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
//                Log.i("result", result);
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

                    JSONObject jo = new JSONObject(s);
                    JSONArray fList = jo.getJSONArray("Food");
                    JSONArray cList = jo.getJSONArray("Comments");
                    FoodFeedFeedbacks foodtemp;
                    Comments commentstemp;

                    for (int i = 0; i < fList.length(); i++) {
                        JSONObject obj = fList.getJSONObject(i);
                        foodtemp = new FoodFeedFeedbacks();
                        foodtemp.setFoodID(obj.getInt("foodID"));
                        foodtemp.setFoodName(obj.getString("foodName"));
                        foodtemp.setDefinition(obj.getString("foodDescription"));
                        foodtemp.setPrice(obj.getDouble("price"));
                        foodtemp.setRating(obj.getDouble("rating"));
                        foodtemp.setIcon(obj.getInt("picture"));
                        comments = new ArrayList<>();
                        for (int j = 0; j < cList.length(); j++) {
                            JSONObject objC = cList.getJSONObject(j);
                            commentstemp = new Comments();
                            if (foodtemp.getFoodID() == objC.getInt("foodID")) {
                                commentstemp.setName(objC.getString("IDUser"));
                                commentstemp.setFoodID(objC.getInt("foodID"));
                                commentstemp.setComments(objC.getString("comments"));
                                comments.add(commentstemp);
                                Log.i("print:", comments.get(j).getComments());
                            }
                        }
                        foodtemp.setComments(comments);
                        foodFeedFeedbacks.add(foodtemp);
                        Log.i("print:", foodFeedFeedbacks.get(i).getFoodName());
                    }
                } catch (JSONException e) {
                }

                ShowView();
            } else {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }
    }
// end codes that are not changed
}

