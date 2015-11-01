package com.example.atayansy.tot;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.atayansy.tot.CustomAdapters.CustomAdapterHistory;
import com.example.atayansy.tot.java.HistoryObject;

import java.util.ArrayList;

public class History extends BaseActivity {
    ArrayList<HistoryObject> histories1;
    private CustomAdapterHistory customAdapterHistory;
    private SwipeMenuListView menulistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_history);
        menulistView = (SwipeMenuListView) findViewById(R.id.listView_history);

        //TODO: delete this
        histories1 = new ArrayList<>();
        histories1.add(new HistoryObject(R.mipmap.ic_launcher, "shermaine", R.mipmap.ic_launcher));

        customAdapterHistory = new CustomAdapterHistory(getBaseContext(), R.layout.activity_history, histories1);
        menulistView.setAdapter(customAdapterHistory);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };
        // set creator
        menulistView.setMenuCreator(creator);

        // step 2. listener item click event
        menulistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                HistoryObject item = histories1.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(item);
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        menulistView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        menulistView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });
    }


    private void open(HistoryObject item) {
        //TODO: fix this put extra

        Intent Intent = new Intent();
        Intent.setClass(getBaseContext(), Result_Favorite_History.class);
        startActivity(Intent);

    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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
