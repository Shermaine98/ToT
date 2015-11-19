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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //change this
    private static String url_login = "http://127.0.0.1:3306/ToT/LoginServlet";
    Button btnSignIn;
    TextView btnSignUp;
    EditText userName, password;
    JSONParser jParser = new JSONParser();
    JSONObject json;
    View.OnClickListener redirect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(btnSignIn)) {


                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(btnSignUp)) {
                i.setClass(getBaseContext(), SignUp.class);
            }
            startActivity(i);
            finish();
        }
    };

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

    private class Login extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            // Getting username and password from user input
            String username = userName.getText().toString();
            String pass = password.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("u", username));
            params.add(new BasicNameValuePair("p", pass));
            json = jParser.makeHttpRequest(url_login, "GET", params);
            String s = null;

            try {
                s = json.getString("info");
                Log.d("Msg", json.getString("info"));
                if (s.equals("success")) {
                    Intent login = new Intent(getApplicationContext(), Welcome.class);
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
