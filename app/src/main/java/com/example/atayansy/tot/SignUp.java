package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    Button btnSignUp;
    EditText EdtName;
    EditText EdtEmail;
    EditText EdtUsername;
    EditText EdtPassword;
    View.OnClickListener signUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // if all edit text is not null
            Intent i = new Intent();
            if (!EdtName.getText().toString().isEmpty() &&
                    !EdtEmail.getText().toString().isEmpty() &&
                    !EdtUsername.getText().toString().isEmpty() &&
                    !EdtPassword.getText().toString().isEmpty()) {
                i.setClass(getBaseContext(), MainActivity.class);
                startActivity(i);
            } else if (EdtName.getText().toString().isEmpty() ||
                    EdtEmail.getText().toString().isEmpty() ||
                    EdtUsername.getText().toString().isEmpty() ||
                    EdtPassword.getText().toString().isEmpty()) {

                Toast.makeText(getBaseContext(), "Please Complete the Fields needed", Toast.LENGTH_LONG).show();
            }
            // for wrong password || username
            else {
                Toast.makeText(getBaseContext(), "Incorrect Username or Password", Toast.LENGTH_LONG).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.bt_signUp);

        EdtName = (EditText) findViewById(R.id.et_inputName);
        EdtEmail = (EditText) findViewById(R.id.et_inputEmail);
        EdtUsername = (EditText) findViewById(R.id.et_inputUsername);
        EdtPassword = (EditText) findViewById(R.id.et_password);


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
}
