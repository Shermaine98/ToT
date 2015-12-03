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
import android.widget.Toast;

import com.example.atayansy.tot.URL.url;
import com.example.atayansy.tot.java.User;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    Button btnSignUp;
    EditText EdtEmail;
    EditText EdtUsername;
    EditText EdtPassword;
    View.OnClickListener signUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // if all edit text is not null
            Intent i = new Intent();
            if (!EdtEmail.getText().toString().isEmpty() &&
                    !EdtUsername.getText().toString().isEmpty() &&
                    !EdtPassword.getText().toString().isEmpty()) {
                Register reg = new Register();
                reg.execute();
            } else if (EdtEmail.getText().toString().isEmpty() ||
                    EdtUsername.getText().toString().isEmpty() ||
                    EdtPassword.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Please Complete the Fields needed", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.bt_signUp);
        EdtEmail = (EditText) findViewById(R.id.et_inputEmail);
        EdtUsername = (EditText) findViewById(R.id.et_inputUsername);
        EdtPassword = (EditText) findViewById(R.id.et_inputPassword);

        btnSignUp.setOnClickListener(signUp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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

    private class Register extends AsyncTask<String, Void, String> {
        User newUser = new User();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Create New User
            newUser.setEmail(EdtEmail.getText().toString());
            newUser.setUserName(EdtUsername.getText().toString());
            newUser.setPassword(EdtPassword.getText().toString());
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);
            Request request = null;
            Response response = null;

            RequestBody requestbody = new FormEncodingBuilder()
                    .add("username", newUser.getUserName())
                    .add("password", newUser.getPassword())
                    .add("email", newUser.getEmail())
                    .build();

            request = new Request.Builder().url(url.ip + "RegisterServlet").post(requestbody).build();
            String result = "";

            try {
                response = okHttpClient.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.i("RESULT", result);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("PostExecute", s);
            if (s == "Username Exists") {
                Toast.makeText(getBaseContext(), "Username already taken", Toast.LENGTH_LONG).show();
            } else if (s.equals("Email Exists")) {
                Toast.makeText(getBaseContext(), "Email already taken", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
                Toast.makeText(getBaseContext(), "Successfully Signed Up!", Toast.LENGTH_LONG).show();
            }

        }
    }
}
