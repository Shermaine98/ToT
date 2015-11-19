package com.example.atayansy.tot;

import android.content.Intent;
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

import com.example.atayansy.tot.Web.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //change this
    private static String url_login = "http://localhost:8081/ToT/LoginServlet";
    Button btnSignIn;
    TextView btnSignUp;
    JSONParser jParser = new JSONParser();
    JSONObject json;
    View.OnClickListener redirect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(btnSignIn)) {
                new Login().execute();
            } else if (v.equals(btnSignUp)) {
                i.setClass(getBaseContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        }
    };
    private EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);


        btnSignIn = (Button) findViewById(R.id.bt_signInPage);
        btnSignUp = (TextView) findViewById(R.id.bt_signUpPage);

        btnSignIn.setOnClickListener(redirect);
        btnSignUp.setOnClickListener(redirect);

    }

// Codes from http://www.javaknowledge.info/sample-login-app-in-android-using-servlet-and-json-parsing/

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

    /**
     * Fix to work UI thread error
     */
    private class Login extends AsyncTask<String, String, String> {
        //intialize varibales
        String username;
        String pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Getting username and password from user input
            username = userName.getText().toString();
            pass = password.getText().toString();
        }

        @Override
        protected String doInBackground(String... args) {

            //get Data
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", username));
            params.add(new BasicNameValuePair("password", pass));

            //entering parser
            json = jParser.makeHttpRequest(url_login, "GET", params);
            String s = null;

            try {
                s = json.getString("info");
                Log.d("Msg", json.getString("info"));
                if (s.equals("success")) {
                    Intent login = new Intent(getApplicationContext(), HomePage.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                    finish();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

    }


}
