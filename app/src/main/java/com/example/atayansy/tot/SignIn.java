package com.example.atayansy.tot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    Button btnSignIn;
    EditText et_username;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = (Button) findViewById(R.id.bt_signIn);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btnSignIn.setOnClickListener(signIn);

    }

    View.OnClickListener signIn = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            Intent i = new Intent();
            if(!et_username.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty()) {
                i.setClass(getBaseContext(), HomePage.class);
                startActivity(i);
            }
            else if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){

                Toast.makeText(getBaseContext(),"Please Complete the Fields needed", Toast.LENGTH_LONG).show();
            }
            // for wrong password
            else{
                Toast.makeText(getBaseContext(),"Incorrect Username or Password", Toast.LENGTH_LONG).show();}

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
