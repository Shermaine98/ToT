package com.example.atayansy.tot;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Food;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Randomize extends AppCompatActivity {
    ArrayList<FoodFeedFeedbacks> FoodList;
    ImageView iv_randomize;
    TextView tv_randomize;
    ArrayList<FoodFeedFeedbacks> FilteredResult;
    int result;
    boolean x = true;
    Random random;
    none none;
    FoodFeedFeedbacks foodTempResult;
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
        random = new Random();

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        tv_randomize = (TextView) findViewById(R.id.tvrandomize);

        //Get Chosen
        location_spinner = getIntent().getExtras().getBoolean("location_spinner");
        budget_spinner = getIntent().getExtras().getBoolean("Budget_spinner");

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
            none = new none();
            none.execute();
        }
        //sort/filter budget then randomize
        else if (location_spinner == false && budget_spinner == true) {
            randomizeFilterBudget randomizeFilterBudget = new randomizeFilterBudget();
            randomizeFilterBudget.execute();
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
            otherLocation.setLatitude(FoodList.get(i).getLatitude());
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
            //   intent(FilteredResult.get();
        } else {
            intent();
        }
    }

    //Filter by location

    //Result intent
    public void Result(String s) {
        //check if result is null
        if (!s.equalsIgnoreCase("null")) {
            foodTempResult = new FoodFeedFeedbacks();
            try {
                //getting result body and coverting to JSON

                JSONObject jo = new JSONObject(s);
                //Setting Json variable
                foodTempResult.setFoodID(jo.getInt("foodID"));
                foodTempResult.setFoodName(jo.getString("foodName"));
                foodTempResult.setDefinition(jo.getString("foodDescription"));
                foodTempResult.setPrice(jo.getDouble("price"));
                foodTempResult.setRating(jo.getDouble("rating"));
                foodTempResult.setImage(jo.getInt("picture"));
                foodTempResult.setRestaurant(jo.getString("restaurantName"));
                Log.i("this", foodTempResult.getFoodName());
            } catch (JSONException e) {
            }
            Intent i = new Intent();
            i.putExtra("Result", foodTempResult);
            i.setClass(getBaseContext(), ResultActivity.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Invalid Username/Password!", Toast.LENGTH_LONG).show();
        }

    }
    //end sort

    public void intent(ArrayList<Food> FoodResult) {
        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        i.putExtra("Result", FoodResult.get(result).getFoodName());
        startActivity(i);
        finish();
    }
    // end none

    public void intent() {
        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        i.putExtra("Result", "No Result");
        startActivity(i);
        finish();
    }
    // end filter by budget

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

    //Filter by Location
    private class filterByLocation extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Distance = getIntent().getExtras().getFloat("Distance");
            CurrLatitude = getIntent().getExtras().getDouble("Latitude");
            CurrLongitude = getIntent().getExtras().getDouble("Longitude");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("filterBy", "Both").build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "RandomizeServlet").post(requestbody).build();
            String result = "";
            try {
                //Run
                response = okHttpClient.newCall(request).execute();
                //get the page body
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ENTER", "POSTEXCUTE");

        }
    }

    // Filter NONE then randomize
    private class none extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("filterBy", "none").build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "RandomizeServlet").post(requestbody).build();
            String result = "";
            try {
                //Run
                response = okHttpClient.newCall(request).execute();
                //get the page body
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ENTER", "POSTEXCUTE");
            Result(s);

        }
    }

    // Filter budget then randomize
    private class randomizeFilterBudget extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Budget = getIntent().getExtras().getInt("Budget");
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("filterBy", "price").build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "RandomizeServlet").post(requestbody).build();
            String result = "";
            try {
                //Run
                response = okHttpClient.newCall(request).execute();
                //get the page body
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("ENTER", "POSTEXCUTE");
            Result(s);

        }
    }
}
