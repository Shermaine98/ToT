package com.example.atayansy.tot;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterFavorite;
import com.example.atayansy.tot.java.FavoriteObject;

import java.util.ArrayList;

public class Favorite extends BaseActivity {
    ListView listViewfavorites;
    CustomAdapterFavorite customAdapterFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_favorite);

        listViewfavorites = (ListView) findViewById(R.id.lv_favorites);
        ArrayList<FavoriteObject> favorites1 = new ArrayList<>();
        favorites1.add(new FavoriteObject(R.mipmap.ic_launcher, "ddddd", R.mipmap.ic_launcher));

        customAdapterFavorite = new CustomAdapterFavorite(getBaseContext(), R.layout.activity_favorite, favorites1);
        listViewfavorites.setAdapter(customAdapterFavorite);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
