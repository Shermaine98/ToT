package com.example.atayansy.tot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.bt_signInPage);
        btnSignUp = (Button) findViewById(R.id.bt_signUpPage);

        btnSignIn.setOnClickListener(redirect);
        btnSignUp.setOnClickListener(redirect);

    }

    View.OnClickListener redirect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(btnSignIn)){
                i.setClass(getBaseContext(), SignIn.class);
            } else if(v.equals(btnSignUp)){
                i.setClass(getBaseContext(), SignUp.class);
            }
            startActivity(i);
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
}
