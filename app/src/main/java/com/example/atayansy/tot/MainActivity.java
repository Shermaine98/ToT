package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.User;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //change this
    Button btnSignIn;
    TextView btnSignUp;
    EditText username, password;
    User user;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        btnSignIn = (Button) findViewById(R.id.bt_signInPage);
        btnSignUp = (TextView) findViewById(R.id.bt_signUpPage);

        btnSignIn.setOnClickListener(redirect);
        btnSignUp.setOnClickListener(redirect);

    }

    View.OnClickListener redirect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(btnSignIn)) {
                Login uhelp = new Login();
                uhelp.execute(username.getText().toString(), password.getText().toString());
            } else if (v.equals(btnSignUp)) {
                i.setClass(getBaseContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class Login extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            Response response = null;
            client.setConnectTimeout(100, TimeUnit.SECONDS);

            RequestBody requestBody = new FormEncodingBuilder()
                    .add("username", params[0])
                    .add("password", params[1])
                    .build();
            //Log.i("link", params[0]);
            //Log.i("link", params[1]);
            //Log.i("requestBody", requestBody.toString());

            Request r = new Request.Builder().url("http://localhost:8084/ToT/LoginServlet").post(requestBody).build();
            Log.i("Request", r.toString());

            try {
                response = client.newCall(r).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i("link", response.toString());
            Log.i("urlconnect", "example");

            String result = "";

            try {
                result = response.body().string();
                Log.i("result", result);
            } catch (IOException e) {

            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jo = new JSONObject(s);
                user = new User(jo.getInt("userID"), jo.getString("username"), jo.getString("email"), jo.getString("password"));
                Log.i("user: ", user.getUserID() + "");
                Log.i("user: ", user.getUserName() + "");
                Log.i("user: ", user.getPassword() + "");
                Log.i("user: ", user.getEmail() + "");

            } catch (JSONException e) {
            }
            if (user != null) {
                sp = getSharedPreferences("login", MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user);
                editor.putString("user", json);

                editor.commit();

                Intent i = new Intent();
                i.setClass(getBaseContext(), MainActivity.class);

                startActivity(i);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Invalid Username/Password!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
