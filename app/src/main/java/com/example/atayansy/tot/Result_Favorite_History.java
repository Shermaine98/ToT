package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterComments;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.FavoriteObject;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.ImageResources;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Result_Favorite_History extends BaseActivity {
    TextView name;
    TextView desc;
    TextView price;
    TextView resto;
    TextView noComments;
    ImageView image;
    Button remove;
    Button feedBack;
    ScrollView fh_scroll;
    RatingBar rating;
    int userID;
    ListView listView;
    String kind;

    View.OnClickListener removeItem = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RemoveFromFavorites r = new RemoveFromFavorites();
            r.execute();

        }
    };
    private FoodFeedFeedbacks feedbackResult;
    private FavoriteObject clicked;
    View.OnClickListener feedBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();

            feedbackResult = new FoodFeedFeedbacks();
            feedbackResult.setFoodID(clicked.getFoodID());
            feedbackResult.setFoodName(clicked.getfName());
            feedbackResult.setRating(clicked.getfRatingStar());
            feedbackResult.setComments(clicked.getComments());
            feedbackResult.setDefinition(clicked.getDescription());
            feedbackResult.setPrice(clicked.getPrice());
            feedbackResult.setLocation(clicked.getAddress());
            feedbackResult.setRestaurant(clicked.getRestaurantName());
            feedbackResult.setImage(clicked.getfPictureIcon());
            intent.setClass(getBaseContext(), Feedback.class);
            intent.putExtra("ResultFeedBack", feedbackResult);
            startActivity(intent);
            finish();
        }
    };
    private CustomAdapterComments customAdapterComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_result__favorite__history);

        ImageResources ir = new ImageResources();
        name = (TextView) findViewById(R.id.Fh_ResultText);
        desc = (TextView) findViewById(R.id.tvfh_foodescription);
        price = (TextView) findViewById(R.id.tvFh_price);
        image = (ImageView) findViewById(R.id.tvFh_foodImage);
        remove = (Button) findViewById(R.id.btn_removeFromFave);
        listView = (ListView) findViewById(R.id.listview_comments);
        noComments = (TextView) findViewById(R.id.NumberofComments);
        resto = (TextView) findViewById(R.id.tvfh_restaurant);
        fh_scroll = (ScrollView) findViewById(R.id.fh_scroll);
        rating = (RatingBar) findViewById(R.id.fh_rating);
        feedBack = (Button) findViewById(R.id.bt_FeedBack);
        clicked = (FavoriteObject) getIntent().getSerializableExtra("FaveClicked");
        kind = getIntent().getExtras().getString("Kind");
        userID = getIntent().getExtras().getInt("userID");
        desc.setTypeface(desc.getTypeface(), Typeface.BOLD_ITALIC);
        name.setText(clicked.getfName());
        resto.setText(clicked.getRestaurantName() + ", " + clicked.getAddress());
        desc.setText(clicked.getDescription());
        price.setText("P" + clicked.getPrice() + ".00");
        noComments.setText("Comments(" + clicked.getComments().size() + ")");
        rating.setRating(Float.parseFloat(String.valueOf(clicked.getfRatingStar())));

        customAdapterComments = new CustomAdapterComments(getBaseContext(), R.layout.comment_list_view, clicked.getComments());
        listView.setAdapter(customAdapterComments);

        ViewGroup.LayoutParams lp = listView.getLayoutParams();

        if(clicked.getComments().size()!=0) {
            if (clicked.getComments().size() <= 3) {
                lp.height = 400;
            } else {
                lp.height = 350;
            }
            listView.setLayoutParams(lp);

        }else{
            listView.setVisibility(View.GONE);
        }
        if (kind.equalsIgnoreCase("history"))
            remove.setVisibility(View.GONE);

        image.setImageResource(ir.getImage(clicked.getfPictureIcon(), getBaseContext()));
        remove.setOnClickListener(removeItem);
        if (kind.equalsIgnoreCase("Favorite"))
            feedBack.setVisibility(View.GONE);

        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        feedBack.setOnClickListener(feedBackClick);
    }

    private void requestDisallowParentInterceptTouchEvent(View __v, Boolean __disallowIntercept) {
        while (__v.getParent() != null && __v.getParent() instanceof View) {
            if (__v.getParent() instanceof ScrollView) {
                __v.getParent().requestDisallowInterceptTouchEvent(__disallowIntercept);
            }
            __v = (View) __v.getParent();
        }
    }
    private class RemoveFromFavorites extends AsyncTask<String, Void, String> {

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = null;
            Response response = null;

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("userID", String.valueOf(userID))
                    .add("foodID", String.valueOf(clicked.getFoodID())).build();

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "RemoveFavorite").post(requestbody).build();
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
            //check if result is null
            if (!s.equalsIgnoreCase("null")) {

                if (s.equalsIgnoreCase("true")) {
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), Favorite.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
                }

            }
        }

    }


}
