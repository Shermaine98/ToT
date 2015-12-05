package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.atayansy.tot.CustomAdapters.CustomAdapterHistory;
import com.example.atayansy.tot.java.HistoryObject;

import java.util.ArrayList;

public class History extends BaseActivity {
    ArrayList<HistoryObject> histories1;
    AdapterView.OnItemClickListener showMoreResult = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent Intent = new Intent();
            Intent.setClass(getBaseContext(), Result_Favorite_History.class);
            Intent.putExtra("Class", "History");
            String name = histories1.get(position).gethName();
            Intent.putExtra("FoodName", name);
            startActivity(Intent);
        }
    };
    private CustomAdapterHistory customAdapterHistory;
    private SwipeMenuListView menulistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_history);
        menulistView = (SwipeMenuListView) findViewById(R.id.listView_history);

        customAdapterHistory = new CustomAdapterHistory(getBaseContext(), R.layout.activity_history, histories1);
        menulistView.setAdapter(customAdapterHistory);
        menulistView.setOnItemClickListener(showMoreResult);

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
