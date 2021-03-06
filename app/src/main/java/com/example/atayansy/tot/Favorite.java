package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFavorites;
import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FavoriteObject;
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

public class Favorite extends BaseActivity {
    ArrayList<FavoriteObject> userFavorites;
    SharedPreferences sharedPreferences;
    int userId;
    String username;
    GridView gridview;
    ArrayList<Comments> comments1;
    AdapterView.OnItemClickListener showItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent i = new Intent();
            i.setClass(getBaseContext(), Result_Favorite_History.class);
            i.putExtra("FaveClicked", userFavorites.get(position));
            i.putExtra("Kind", "Favorite");
            i.putExtra("userID", userId);
            i.putExtra("currlocation", "null");
            startActivity(i);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_favorite);
        gridview = (GridView) findViewById(R.id.gridview);
        userFavorites = new ArrayList<>();
         /* Shared Preferences */
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getInt("id", 0);

        if (username.isEmpty()) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } else {
            onResume();
        }

        GetFavorites f = new GetFavorites();
        f.execute();

        gridview.setOnItemClickListener(showItem);


    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getInt("id", 0);

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

    private class GetFavorites extends AsyncTask<String, Void, String> {

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = null;
            Response response = null;

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("userID", String.valueOf(userId)).build();

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "GetFavoritesServlet").post(requestbody).build();
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
                try {

                    //getting result body and converting to JSON
                    JSONObject jo = new JSONObject(s);

                    JSONArray fList = jo.getJSONArray("Favorites");
                    JSONArray cList = jo.getJSONArray("Comments");

                    FavoriteObject foodtemp;
                    Comments commentstemp;

                    for (int i = 0; i < fList.length(); i++) {
                        JSONObject obj = fList.getJSONObject(i);
                        foodtemp = new FavoriteObject(
                                obj.getInt("picture"), obj.getString("foodName"),
                                obj.getInt("rating"), obj.getString("foodDescription"),
                                obj.getInt("price"), obj.getInt("foodID")
                        );
                        foodtemp.setRestaurantName(obj.getString("RestaurantName"));
                        foodtemp.setAddress(obj.getString("address"));
                        comments1 = new ArrayList<>();
                        for (int j = 0; j < cList.length(); j++) {
                            JSONObject objC = cList.getJSONObject(j);
                            commentstemp = new Comments();
                            if (foodtemp.getFoodID() == objC.getInt("foodID")) {
                                commentstemp.setName(objC.getString("IDUser"));
                                commentstemp.setFoodID(objC.getInt("foodID"));
                                commentstemp.setComments(objC.getString("comments"));
                                comments1.add(commentstemp);
                            }
                        }
                        foodtemp.setComments(comments1);
                        userFavorites.add(foodtemp);
                    }

                    gridview.setAdapter(new CustomAdapterFavorites(getBaseContext(), userFavorites));

                } catch (JSONException e) {
                }
            } else {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
