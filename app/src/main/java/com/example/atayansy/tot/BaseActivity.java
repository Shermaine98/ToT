package com.example.atayansy.tot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class BaseActivity extends Activity {
    private ImageButton ibButtonHome;
    private ImageButton ibButtonFavorite;
    private ImageButton ibButtonRandomize;
    private ImageButton ibButtonHistory;
    private ImageButton ibButtonLogOut;
    View.OnClickListener Navigation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(ibButtonHome)) {
                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(ibButtonFavorite)) {
                i.setClass(getBaseContext(), Favorite.class);
            } else if (v.equals(ibButtonHome)) {
                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(ibButtonRandomize)) {
                i.setClass(getBaseContext(), Randomize.class);
            } else if (v.equals(ibButtonHistory)) {
                i.setClass(getBaseContext(), History.class);
            } else if (v.equals(ibButtonLogOut)) {
                //TODO: something code here to not crash on activity exit??
                i.setClass(getBaseContext(), MainActivity.class);
            }

            startActivity(i);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nagivation_layout_framelayout);
        ibButtonHome = (ImageButton) findViewById(R.id.button_Home);
        ibButtonFavorite = (ImageButton) findViewById(R.id.button_favorites);
        ibButtonRandomize = (ImageButton) findViewById(R.id.button_randomize);
        ibButtonHistory = (ImageButton) findViewById(R.id.button_history);
        ibButtonLogOut = (ImageButton) findViewById(R.id.button_logout);

        ibButtonFavorite.setOnClickListener(Navigation);
        ibButtonRandomize.setOnClickListener(Navigation);
        ibButtonHome.setOnClickListener(Navigation);
        ibButtonHistory.setOnClickListener(Navigation);
        ibButtonLogOut.setOnClickListener(Navigation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter_menus, menu);
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
