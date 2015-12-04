package com.example.atayansy.tot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class BaseActivity extends AppCompatActivity {
    private ImageButton ibButtonHome;
    private ImageButton ibButtonFavorite;
    private ImageButton ibButtonFilter;
    private ImageButton ibButtonHistory;
    private ImageButton ibButtonLogOut;


    public void setUp(int resource) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.base_container);
        RelativeLayout content = (RelativeLayout) View.inflate(getBaseContext(), resource, null);
        layout.addView(content);

        ibButtonHome = (ImageButton) findViewById(R.id.button_Home);
        ibButtonFavorite = (ImageButton) findViewById(R.id.button_favorites);
        ibButtonFilter = (ImageButton) findViewById(R.id.button_filter);
        ibButtonHistory = (ImageButton) findViewById(R.id.button_history);
        ibButtonLogOut = (ImageButton) findViewById(R.id.button_logout);

        View.OnClickListener Navigation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if (v.equals(ibButtonHome)) {
                    i.setClass(getBaseContext(), HomePage.class);
                } else if (v.equals(ibButtonFavorite)) {
                    i.setClass(getBaseContext(), Favorite.class);
                } else if (v.equals(ibButtonFilter)) {
                    i.setClass(getBaseContext(), FilterMenu.class);
                } else if (v.equals(ibButtonHistory)) {
                    i.setClass(getBaseContext(), History.class);
                } else if (v.equals(ibButtonLogOut)) {
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                    i.setClass(getBaseContext(), MainActivity.class);
                }

                startActivity(i);
                finish();
            }
        };

        ibButtonFavorite.setOnClickListener(Navigation);
        ibButtonFilter.setOnClickListener(Navigation);
        ibButtonHome.setOnClickListener(Navigation);
        ibButtonHistory.setOnClickListener(Navigation);
        ibButtonLogOut.setOnClickListener(Navigation);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


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
