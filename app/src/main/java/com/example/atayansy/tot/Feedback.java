package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class Feedback extends AppCompatActivity {

    ImageView Image;
    TextView textName;
    TextView textInfo;
    TextView textResto;
    TextView textPrice;
    TextView numberofComments;
    TextView numberChar;
    ListView listViewC;
    CustomAdapterComments customAdapterComments;
    SharedPreferences sharedPreferences;
    String userName;
    int userID;
    RatingBar ratingbar;
    ImageView send;
    ImageView cancel;
    Button buttonAddToFavorite;
    EditText comments;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            int text = 100 - s.length();
            numberChar.setText("Numbers of Character Left -" + String.valueOf(text));
            if (s.length() >= 100) {
                comments.setKeyListener(null);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    View.OnClickListener sendFeedBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent();
            if (v.equals(send)) {
                SendComments sendComments = new SendComments();
                sendComments.execute();

            } else if (v.equals(cancel)) {
                i.setClass(getBaseContext(), HomePage.class);
                i.putExtra("currlocation", "home");
                startActivity(i);
                finish();
            }

        }
    };
    View.OnClickListener addToFavorite = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddThisTofavorite AddThisTofavorite = new AddThisTofavorite();
            AddThisTofavorite.execute();

        }
    };
    private FoodFeedFeedbacks feedbackResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Image = (ImageView) findViewById(R.id.tv_foodImage);
        textName = (TextView) findViewById(R.id.tv_fodName);
        textInfo = (TextView) findViewById(R.id.tv_foodinfo);
        textResto = (TextView) findViewById(R.id.feedback_restaurant);
        textPrice = (TextView) findViewById(R.id.tv_price);
        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
        listViewC = (ListView) findViewById(R.id.listView_feedComments);
        buttonAddToFavorite = (Button) findViewById(R.id.addToFavorite);
        send = (ImageView) findViewById(R.id.send);
        cancel = (ImageView) findViewById(R.id.back);
        numberofComments = (TextView) findViewById(R.id.NumberofCommentsFeedBack);
        comments = (EditText) findViewById(R.id.tv_FeedBack);
        numberChar = (TextView) findViewById(R.id.characterCount);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        userName = sharedPreferences.getString("username", "");
        userID = sharedPreferences.getInt("id", 0);

        if (userName.isEmpty()) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            onResume();
        }


        feedbackResult = (FoodFeedFeedbacks) getIntent().getSerializableExtra("ResultFeedBack");
        textName.setText(feedbackResult.getFoodName());
        textInfo.append("\n" + feedbackResult.getDefinition());
        textInfo.append("\n" + String.valueOf(feedbackResult.getRestaurant()));
        textInfo.append("\n" + String.valueOf(feedbackResult.getLocation()));
        textInfo.append("\n" + "P" + String.valueOf(feedbackResult.getPrice()));
        ImageResources imageResources = new ImageResources();
        Image.setImageResource(imageResources.getImage(feedbackResult.getImage(), getBaseContext()));
        customAdapterComments = new CustomAdapterComments(getBaseContext(), R.layout.comment_list_view, feedbackResult.getComments());
        listViewC.setAdapter(customAdapterComments);
        ratingbar.setRating(Float.parseFloat(String.valueOf(feedbackResult.getRating())));
        numberofComments.setText("Comments(" + feedbackResult.getComments().size() + ")");
        ViewGroup.LayoutParams lp = listViewC.getLayoutParams();
        if (feedbackResult.getComments().size() != 0) {
            if (feedbackResult.getComments().size() <= 3) {
                lp.height = 200;
            } else {
                lp.height = 350;
            }
            listViewC.setLayoutParams(lp);
        } else {
            listViewC.setVisibility(View.GONE);
        }


        addListenerOnRatingBar();
        send.setOnClickListener(sendFeedBack);
        cancel.setOnClickListener(sendFeedBack);

        CheckIFfavorite CheckIFfavorite = new CheckIFfavorite();
        CheckIFfavorite.execute();
        comments.addTextChangedListener(mTextEditorWatcher);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        userName = sharedPreferences.getString("username", "");
        userID = sharedPreferences.getInt("id", 0);
    }

    public void addListenerOnRatingBar() {
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Toast.makeText(getBaseContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_LONG).show();

            }
        });
    }

    //codes not changed
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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

    //send comment to database
    private class SendComments extends AsyncTask<String, Void, String> {
        float rating;
        String Comment;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rating = ratingbar.getRating();
            Comment = comments.getText().toString();

            if (Comment.matches("") && rating == 0.0) {
                rating = (float) 0.0;
                Comment = "null%";
            } else if (Comment.matches("")) {
                Comment = "null%";
            } else if (rating == 0.0) {
                rating = (float) 0.0;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("rating", String.valueOf(rating)).add("Comment", Comment).add("userID", String.valueOf(userID))
                    .add("foodID", String.valueOf(feedbackResult.getFoodID())).build();
            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "AddCommentRatingServlet").post(requestbody).build();
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
                Intent i = new Intent();
                i.setClass(getBaseContext(), History.class);
                i.putExtra("currlocation", "history");
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();

            }
        }
    }

    //End send to database
//add to favorite
    private class AddThisTofavorite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters

            RequestBody requestbody = new FormEncodingBuilder().add("task", "add")
                    .add("foodID", String.valueOf(feedbackResult.getFoodID()))
                    .add("idUser", String.valueOf(userID)).build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "AddFavoriteServlet").post(requestbody).build();
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
                Toast.makeText(getBaseContext(), "ADDED TO FAVORITES", Toast.LENGTH_LONG).show();
                buttonAddToFavorite.setVisibility(View.GONE);
            } else {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class CheckIFfavorite extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters
            RequestBody requestbody = new FormEncodingBuilder().add("task", "check")
                    .add("foodID", String.valueOf(feedbackResult.getFoodID()))
                    .add("idUser", String.valueOf(userID)).build();

            Request request = null;
            Response response = null;

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "AddFavoriteServlet").post(requestbody).build();
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
                buttonAddToFavorite.setVisibility(View.GONE);
            } else if (s.equalsIgnoreCase("false")) {
                buttonAddToFavorite.setOnClickListener(addToFavorite);
            } else {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    //codes not changed
}
