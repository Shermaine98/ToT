package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.ImageResources;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Randomize extends AppCompatActivity {
    ImageView iv_randomize;
    TextView tv_randomize, header;
    Random random;
    FoodFeedFeedbacks foodTempResult;
    ArrayList<Comments> comments1;
    float maxValue;
    String[] animationStrings;

    final Animation textAnimation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Log.i("time", String.valueOf(interpolatedTime));
            random = new Random();
            tv_randomize = (TextView) findViewById(R.id.tvrandomize);
            int randomNum = random.nextInt((animationStrings.length - 1 - 0) + 1) + 0;
            tv_randomize.setText("");
            Log.i("this", String.valueOf(randomNum));
            tv_randomize.setText(animationStrings[randomNum]);
            Log.i("this", animationStrings[randomNum]);

        }

        @Override
        public void setDuration(long durationMillis) {
            super.setDuration(durationMillis);
            durationMillis = 50;
        }
    };
    private double Distance;
    private double CurrLatitude;
    private double CurrLongitude;
    private int Budget;
    private boolean location_spinner;
    private boolean budget_spinner;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        random = new Random();

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        tv_randomize = (TextView) findViewById(R.id.tvrandomize);
        header = (TextView) findViewById(R.id.randomizing);
        iv_randomize.setBackgroundResource(R.drawable.randomize_image);
        animationStrings = new String[]{"This looks good!", "This might be a little far", "Nah, it wont be healthy for you",
                "how about this one?", "I know you are own diet, so not this one!", "there you go!"};
        maxValue = animationStrings.length - 1;
        //Get Chosen
        location_spinner = getIntent().getExtras().getBoolean("location_spinner");
        budget_spinner = getIntent().getExtras().getBoolean("Budget_spinner");
        sort();

        AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();
        frameAnimation.start();

        tv_randomize.startAnimation(textAnimation);
    }


    //Sorting
    public void sort() {
        if (location_spinner == false && budget_spinner == false) {
            none none = new none();
            none.execute();
        }
        //sort/filter budget then randomize
        else if (location_spinner == false && budget_spinner == true) {
            randomizeFilterBudget randomizeFilterBudget = new randomizeFilterBudget();
            randomizeFilterBudget.execute();
        }
        //sort/filter location then randomize
        else if (location_spinner == true && budget_spinner == false) {
            randomizeByLocation randomizeByLocation = new randomizeByLocation();
            randomizeByLocation.execute();
        } else {
            //randomize both
            randomizeByBoth randomizeByBoth = new randomizeByBoth();
            randomizeByBoth.execute();
        }

    }
    //Result intent
    public void Result(String s) {
        //check if result is null
        if (!s.equalsIgnoreCase("null")) {
            foodTempResult = new FoodFeedFeedbacks();
            try {
                //getting result body and coverting to JSON
                JSONObject base = new JSONObject(s);
                JSONObject jo = base.getJSONObject("Result");
                JSONArray cList = base.getJSONArray("Comments");
                Comments commentstemp;
                //Setting Json variable
                foodTempResult.setFoodID(jo.getInt("foodID"));
                foodTempResult.setFoodName(jo.getString("foodName"));
                foodTempResult.setDefinition(jo.getString("foodDescription"));
                foodTempResult.setPrice(jo.getDouble("price"));
                foodTempResult.setRating(jo.getDouble("rating"));
                foodTempResult.setImage(jo.getInt("picture"));
                foodTempResult.setRestaurant(jo.getString("RestaurantName"));
                foodTempResult.setLocation(jo.getString("address"));
                comments1 = new ArrayList<>();
                for (int j = 0; j < cList.length(); j++) {
                    JSONObject objC = cList.getJSONObject(j);
                    commentstemp = new Comments();
                    commentstemp.setName(objC.getString("IDUser"));
                    commentstemp.setFoodID(objC.getInt("foodID"));
                    commentstemp.setComments(objC.getString("comments"));
                    comments1.add(commentstemp);
                }
                foodTempResult.setComments(comments1);
            } catch (JSONException e) {
            }
            animation();

        } else {
            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }
    //end sort

    public void animation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.putExtra("Result", foodTempResult);
                i.setClass(getBaseContext(), ResultActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
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

    //Filter by both
    private class randomizeByBoth extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Distance = getIntent().getExtras().getDouble("Distance");
            CurrLatitude = getIntent().getExtras().getDouble("Latitude");
            CurrLongitude = getIntent().getExtras().getDouble("Longitude");
            Budget = getIntent().getExtras().getInt("Budget");
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
                    .add("filterBy", "Both").add("distance", String.valueOf(Distance))
                    .add("longitude", String.valueOf(CurrLongitude))
                    .add("latitude", String.valueOf(CurrLatitude))
                    .add("price", String.valueOf(Budget)).build();

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
            Result(s);

        }
    }

    //end filter by both
    //Filter by Location
    private class randomizeByLocation extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Distance = getIntent().getExtras().getDouble("Distance");
            CurrLatitude = getIntent().getExtras().getDouble("Latitude");
            CurrLongitude = getIntent().getExtras().getDouble("Longitude");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ImageResources ir = new ImageResources();
            iv_randomize.setBackgroundResource(ir.getImage(values[0], getBaseContext()));
            AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();
        }

        @Override
        protected String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters
            RequestBody requestbody = new FormEncodingBuilder()
                    .add("filterBy", "Location")
                    .add("longitude", String.valueOf(CurrLongitude))
                    .add("latitude", String.valueOf(CurrLatitude))
                    .add("distance", String.valueOf(Distance)).build();

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
            Result(s);

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
                    .add("filterBy", "price").add("price", String.valueOf(Budget)).build();

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
            Result(s);

        }
    }

}
