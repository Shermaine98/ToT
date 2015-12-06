package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterComments;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.ImageResources;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity {
    Button button_eat;
    ImageButton button_main;
    ImageView imageView;
    TextView resultFoodName, resultDescription, resultPrice, resultResto, numberofComments;
    RatingBar rating;
    CustomAdapterComments customAdapterComments;
    ListView listView;
    String username;
    int userID;
    SharedPreferences sharedPreferences;
    private FoodFeedFeedbacks result;
    View.OnClickListener decision = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent();
            if (v.equals(button_eat)) {
                i.setClass(getBaseContext(), Feedback.class);
                i.putExtra("ResultFeedBack", result);
            } else if (v.equals(button_main)) {
                i.setClass(getBaseContext(), FilterMenu.class);
                i.putExtra("currlocation", "filter");
            }
            startActivity(i);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultFoodName = (TextView) findViewById(R.id.tv_fodName);
        resultDescription = (TextView) findViewById(R.id.tv_foodescription);
        resultPrice = (TextView) findViewById(R.id.tv_price);
        resultResto = (TextView) findViewById(R.id.tv_restaurant);
        button_eat = (Button) findViewById(R.id.button_eat);
        button_main = (ImageButton) findViewById(R.id.button_main);
        imageView = (ImageView) findViewById(R.id.tv_foodImage);
        listView = (ListView) findViewById(R.id.evFoodComment_Result);
        rating = (RatingBar) findViewById(R.id.randomize_rating);
        numberofComments = (TextView) findViewById(R.id.NumberofCommentsResult);
        button_eat.setOnClickListener(decision);
        button_main.setOnClickListener(decision);
        result = (FoodFeedFeedbacks) getIntent().getSerializableExtra("Result");

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userID = sharedPreferences.getInt("id", 0);


        if (username.isEmpty()) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } else {
            onResume();
        }
        if (result.equals(null)) {
            resultFoodName.setText("NONE");
            button_eat.setVisibility(Button.INVISIBLE);
            button_main.setVisibility(Button.INVISIBLE);
            resultPrice.setText("");
            resultDescription.setText("");
        } else {
            resultPrice.setText("P" + String.valueOf(result.getPrice()) + "0");
            resultFoodName.setText(result.getFoodName());
            resultDescription.setText(result.getDefinition());
            resultResto.setText(result.getRestaurant() + ", " + result.getLocation());
            rating.setRating(Float.parseFloat(String.valueOf(result.getRating())));

            ImageResources imageResources = new ImageResources();
            imageView.setImageResource(imageResources.getImage(result.getImage(), getBaseContext()));
            customAdapterComments = new CustomAdapterComments(getBaseContext(), R.layout.comment_list_view, result.getComments());
            listView.setAdapter(customAdapterComments);
            numberofComments.setText("Comments(" + result.getComments().size() + ")");
            AddToHistory AddToHistory = new AddToHistory();
            AddToHistory.execute();

            ViewGroup.LayoutParams lp = listView.getLayoutParams();
            if (result.getComments().size() != 0) {
                if (result.getComments().size() <= 3) {
                    lp.height = 200;
                } else {
                    lp.height = 350;
                }

                listView.setLayoutParams(lp);
                listView.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
            } else {
                listView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userID = sharedPreferences.getInt("id", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    //add to favorite
    private class AddToHistory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder().add("foodID", String.valueOf(result.getFoodID()))
                    .add("idUser", String.valueOf(userID)).build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "AddHistoryServlet").post(requestbody).build();
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
            if (s.equalsIgnoreCase("true")) {
                Toast.makeText(getBaseContext(), "Added To History", Toast.LENGTH_LONG).show();
            } else if (s.equalsIgnoreCase("false")) {
                Toast.makeText(getBaseContext(), "Already in History", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }
}
