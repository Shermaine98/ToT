package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterHistory;
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

public class History extends BaseActivity {
    ArrayList<FavoriteObject> userHistory;
    ArrayList<Comments> comments1;
    SharedPreferences sharedPreferences;
    int userId;
    String username;
    GridView grid;
    AdapterView.OnItemClickListener showItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent i = new Intent();
            i.setClass(getBaseContext(), Result_Favorite_History.class);
            i.putExtra("FaveClicked", userHistory.get(position));
            i.putExtra("Kind", "History");
            i.putExtra("userID", userId);
            startActivity(i);
        }
    };
    private CustomAdapterHistory customAdapterHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_history);
        grid = (GridView) findViewById(R.id.gridHistory);
        userHistory = new ArrayList<>();
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

        GetHistory h = new GetHistory();
        h.execute();


        grid.setOnItemClickListener(showItem);
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

    private class GetHistory extends AsyncTask<String, Void, String> {

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = null;
            Response response = null;

            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);
            RequestBody requestbody = new FormEncodingBuilder()
                    .add("userID", String.valueOf(userId)).build();

            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "GetHistory").post(requestbody).build();
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
                try {
                    //TODO: not getting comments
                    JSONObject jo = new JSONObject(s);
                    JSONArray fList = jo.getJSONArray("History");
                    JSONArray cList = jo.getJSONArray("Comments");
                    FavoriteObject foodtemp;
                    Comments commentstemp;

                    for (int i = 0; i < fList.length(); i++) {
                        JSONObject obj = fList.getJSONObject(i);
                        foodtemp = new FavoriteObject(
                                obj.getInt("picture"), obj.getString("foodName"), obj.getDouble("rating"),
                                obj.getString("foodDescription"), obj.getInt("price"), obj.getInt("foodID"));
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
                        userHistory.add(foodtemp);
                    }
                    grid.setAdapter(new CustomAdapterHistory(getBaseContext(), userHistory));

                } catch (JSONException e) {
                }
            } else {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
