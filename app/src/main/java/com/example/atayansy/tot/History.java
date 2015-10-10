package com.example.atayansy.tot;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterHistory;
import com.example.atayansy.tot.java.HistoryObject;

import java.util.ArrayList;

public class History extends BaseActivity {
    ListView listView;
    private CustomAdapterHistory customAdapterHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.lv_history);
        ArrayList<HistoryObject> histories1 = new ArrayList<>();
        histories1.add(new HistoryObject(R.mipmap.ic_launcher, "shermaine", R.mipmap.ic_launcher));

        customAdapterHistory = new CustomAdapterHistory(getBaseContext(), R.layout.activity_history, histories1);
        listView.setAdapter(customAdapterHistory);
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
