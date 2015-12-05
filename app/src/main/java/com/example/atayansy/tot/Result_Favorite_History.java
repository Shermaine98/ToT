package com.example.atayansy.tot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterComments;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.FavoriteObject;
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
    ImageView image;
    Button remove;
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
    private FavoriteObject clicked;
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

        clicked = (FavoriteObject) getIntent().getSerializableExtra("FaveClicked");
        kind = getIntent().getExtras().getString("Kind");
        userID = getIntent().getExtras().getInt("userID");
        Log.i("userID", String.valueOf(userID));
        Log.i("foodID", String.valueOf(clicked.getFoodID()));
        name.setText(clicked.getfName());
        desc.setText(clicked.getDescription());
        price.setText("P" + clicked.getPrice() + ".00");
        customAdapterComments = new CustomAdapterComments(getBaseContext(), R.layout.comment_list_view, clicked.getComments());
        listView.setAdapter(customAdapterComments);

        if(kind.equalsIgnoreCase("history"))
            remove.setVisibility(View.INVISIBLE);

        image.setImageResource(ir.getImage(clicked.getfPictureIcon(), getBaseContext()));
        remove.setOnClickListener(removeItem);
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
