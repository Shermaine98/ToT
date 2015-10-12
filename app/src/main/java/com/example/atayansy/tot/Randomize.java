package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class Randomize extends AppCompatActivity {
//TODO: Picture Changing Code
    ImageView iv_randomize;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        iv_randomize = (ImageView) findViewById(R.id.ivRandomize);
        iv_randomize.setBackgroundResource(R.drawable.random_animation);

        AnimationDrawable frameAnimation = (AnimationDrawable) iv_randomize.getBackground();

        frameAnimation.start();

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
