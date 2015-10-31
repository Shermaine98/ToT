package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class FilterMenu extends AppCompatActivity {

    Spinner spinner_Bd;
    Spinner spinner_lt;
    ImageButton ibFilterButtonHome;
    ImageButton ibFilterButtonFavorite;
    ImageButton ibFilterButtonRandomize;
    ImageButton ibFilterButtonHistory;
    ImageButton ibFilterButtonLogOut;


    View.OnClickListener randomizeOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent();
            if (v.equals(ibFilterButtonHome)) {
                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(ibFilterButtonFavorite)) {
                i.setClass(getBaseContext(), Favorite.class);
            } else if (v.equals(ibFilterButtonRandomize)) {
                i.setClass(getBaseContext(), Randomize.class);
            } else if (v.equals(ibFilterButtonHistory)) {
                i.setClass(getBaseContext(), History.class);
            } else if (v.equals(ibFilterButtonLogOut)) {
                //TODO: something code here to not crash on activity exit??
                i.setClass(getBaseContext(), MainActivity.class);
            }

            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);
        //budget
        spinner_Bd = (Spinner) findViewById(R.id.sn_budget);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.budget, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner_Bd.setAdapter(adapter);

        //location
        spinner_lt = (Spinner) findViewById(R.id.sn_location);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.distance, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner_lt.setAdapter(adapter2);

        ibFilterButtonHome = (ImageButton) findViewById(R.id.filterButton_Home);
        ibFilterButtonFavorite = (ImageButton) findViewById(R.id.filterButton_favorites);
        ibFilterButtonRandomize = (ImageButton) findViewById(R.id.filterButton_randomize);
        ibFilterButtonHistory = (ImageButton) findViewById(R.id.filterButton_history);
        ibFilterButtonLogOut = (ImageButton) findViewById(R.id.filterButton_logout);

        ibFilterButtonHome.setOnClickListener(randomizeOnClick);
        ibFilterButtonFavorite.setOnClickListener(randomizeOnClick);
        ibFilterButtonRandomize.setOnClickListener(randomizeOnClick);
        ibFilterButtonHistory.setOnClickListener(randomizeOnClick);
        ibFilterButtonLogOut.setOnClickListener(randomizeOnClick);
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

