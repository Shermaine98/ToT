package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class Randomize extends AppCompatActivity {
//TODO: Picture Changing Code
    ImageView iv_randomize;
    TextView tv_randomize;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        tv_randomize = (TextView) findViewById(R.id.tvrandomize);

        iv_randomize.setBackgroundResource(R.drawable.random_animation);
        //   tv_randomize.setBackgroundResource(R.drawable.text_animation);

        AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();

        // AnimationDrawable frameAnimation1 = (AnimationDrawable) tv_randomize.getBackground();

        frameAnimation.start();
        //    frameAnimation1.start();


        Intent i = new Intent();
        i.setClass(getBaseContext(), ResultActivity.class);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setClass(getBaseContext(), ResultActivity.class);
                startActivity(i);
            }
        }, 4000);


        //Get Chosen
        int Budget = getIntent().getExtras().getInt("Budget");
        String Distance = getIntent().getExtras().getString("Distance");
        Double Latitude = getIntent().getExtras().getDouble("Latitude");
        Double Longitude = getIntent().getExtras().getDouble("Longitude");

        //printing Console
        Log.d("Budget", String.valueOf(Budget));
        Log.d("Distance", Distance.toString());
        Log.d("Latitude", String.valueOf(Latitude));
        Log.d("Longitude", String.valueOf(Longitude.toString()));

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_randomize, menu);
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
