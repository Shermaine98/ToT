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

    Button btnSignIn;
    TextView btnSignUp;
    User userLogin;
    SharedPreferences sp;
    //    Another Page
    View.OnClickListener redirect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(btnSignIn)) {
                /** Connect to Database **/
                SignIn signIn = new SignIn();
                signIn.execute();
            } else if (v.equals(btnSignUp)) {
                i.setClass(getBaseContext(), SignUp.class);
                startActivity(i);
            }
        }
    };
    private EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Get LOCAL IP address*/
        /*
        url url = new url();
        url.getIp();
        */

        userName = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);

        btnSignIn = (Button) findViewById(R.id.bt_signInPage);
        btnSignUp = (TextView) findViewById(R.id.bt_signUpPage);

        /* Calling a Method*/

        btnSignIn.setOnClickListener(redirect);
        btnSignUp.setOnClickListener(redirect);

    }

    //  CODES THAT ARE NOT CHANGED

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

    //   END CODES THAT ARE NOT CHANGED

    //   DATABASE
    private class SignIn extends AsyncTask<String, Void, String> {
        User currentUser = new User();


        // Assign Variables
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            currentUser.setUserName(userName.getText().toString());
            currentUser.setPassword(password.getText().toString());
        }

        //Connecting to Servlet
        @Override
        protected String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();

            // Stop Activity?
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);

            //Setting Parameters
            RequestBody requestbody = new FormEncodingBuilder()
                    .add("username", currentUser.getUserName())
                    .add("password", currentUser.getPassword()).build();

            Request request = null;
            Response response = null;


            //Connecting to Servlet
            request = new Request.Builder().url(url.ip + "LoginServlet").post(requestbody).build();
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
            Log.i("ENTER", "POSTEXCUTE");
            //check if result is null
            if (!s.equalsIgnoreCase("null")) {
                try {
                    //getting result body and coverting to JSON
                    Log.i("ENTER", "AFTER TRY");
                    Log.i("ENTER", s);
                    userLogin = new User();
                    JSONObject jo = new JSONObject(s);

                    //Setting Json variable
                    userLogin.setUserName(jo.getString("userName"));
                    userLogin.setUserID(Integer.parseInt(jo.getString("userID")));

                    Log.i("UserName THIS", userLogin.getUserName() + "");
                    Log.i("password THIS", userLogin.getUserID() + "");

                } catch (JSONException e) {
                }

                /* Shared Preference for Username and UserID */
                SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                editor.putString("username", userLogin.getUserName());
                editor.putInt("id", userLogin.getUserID());
                editor.commit();

                Intent i = new Intent();
                i.setClass(getBaseContext(), HomePage.class);
                startActivity(i);
                finish();
            } else {
                //if wrong password
                Toast.makeText(getBaseContext(), "Invalid Username/Password!", Toast.LENGTH_LONG).show();
            }
        }
    }
    //  END  DATABASE

}
