package com.example.atayansy.tot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    private String location;

    /**
     * Sets the specified image buttonto the given state, while modifying or
     * "graying-out" the icon as well
     *
     * @param enabled   The state of the menu item
     * @param item      The menu item to modify
     * @param iconResId The icon ID
     */
    public static void setImageButtonEnabled(Context ctxt, boolean enabled, ImageButton item,
                                             int iconResId) {
        item.setEnabled(enabled);
        Drawable originalIcon = ctxt.getResources().getDrawable(iconResId);
        Drawable icon = enabled ? originalIcon : convertDrawableToGrayScale(originalIcon);
        item.setImageDrawable(icon);
    }

    /**
     * Mutates and applies a filter that converts the given drawable to a Gray
     * image. This method may be used to simulate the color of disable icons in
     * Honeycomb's ActionBar.
     *
     * @return a mutated version of the given drawable with a color filter
     * applied.
     */
    public static Drawable convertDrawableToGrayScale(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Drawable res = drawable.mutate();
        res.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
        return res;
    }

    public void setUp(int resource) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.base_container);
        RelativeLayout content = (RelativeLayout) View.inflate(getBaseContext(), resource, null);
        layout.addView(content);

        ibButtonHome = (ImageButton) findViewById(R.id.button_Home);
        ibButtonFavorite = (ImageButton) findViewById(R.id.button_favorites);
        ibButtonFilter = (ImageButton) findViewById(R.id.button_filter);
        ibButtonHistory = (ImageButton) findViewById(R.id.button_history);
        ibButtonLogOut = (ImageButton) findViewById(R.id.button_logout);
        location = getIntent().getExtras().getString("currlocation");

        if (location.equalsIgnoreCase("home")) {
            setImageButtonEnabled(getBaseContext(), false, ibButtonHome, R.drawable.ic_home);
        } else if (location.equalsIgnoreCase("favorites")) {
            setImageButtonEnabled(getBaseContext(), false, ibButtonFavorite, R.drawable.ic_action);
        } else if (location.equalsIgnoreCase("filter")) {
            setImageButtonEnabled(getBaseContext(), false, ibButtonFilter, R.drawable.dice);
        } else if (location.equalsIgnoreCase("history")) {
            setImageButtonEnabled(getBaseContext(), false, ibButtonHistory, R.drawable.ic_history);
        }

        View.OnClickListener Navigation = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if (v.equals(ibButtonHome)) {
                    if (location.equalsIgnoreCase("home")) {
                        setImageButtonEnabled(getBaseContext(), false, ibButtonHome, R.drawable.ic_home);
                    } else {
                        i.setClass(getBaseContext(), HomePage.class);
                        i.putExtra("currlocation", "home");
                        startActivity(i);
                        finish();
                    }
                } else if (v.equals(ibButtonFavorite)) {
                    if (location.equalsIgnoreCase("favorites")) {
                        setImageButtonEnabled(getBaseContext(), false, ibButtonFavorite, R.drawable.ic_action);
                    } else {
                        i.setClass(getBaseContext(), Favorite.class);

                        i.putExtra("currlocation", "favorites");
                        startActivity(i);
                        finish();
                    }
                } else if (v.equals(ibButtonFilter)) {
                    if (location.equalsIgnoreCase("filter")) {
                        setImageButtonEnabled(getBaseContext(), false, ibButtonFilter, R.drawable.dice);
                    } else {
                        i.setClass(getBaseContext(), FilterMenu.class);
                        i.putExtra("currlocation", "filter");
                        startActivity(i);
                        finish();
                    }

                } else if (v.equals(ibButtonHistory)) {
                    if (location.equalsIgnoreCase("history")) {
                        setImageButtonEnabled(getBaseContext(), false, ibButtonHistory, R.drawable.ic_history);
                    } else {
                        i.setClass(getBaseContext(), History.class);
                        i.putExtra("currlocation", "history");
                        startActivity(i);
                        finish();
                    }
                } else if (v.equals(ibButtonLogOut)) {
                    SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    i.setClass(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
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
