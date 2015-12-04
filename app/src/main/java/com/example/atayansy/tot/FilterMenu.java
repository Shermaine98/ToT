package com.example.atayansy.tot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.atayansy.tot.Location.AppLocationService;
import com.example.atayansy.tot.Location.LocationAddress;

public class FilterMenu extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Switch location;
    Switch Budget;

    Spinner spinner_Bd;
    Spinner spinner_lt;
    TextView tvAddress;
    TextView tvBudgetLocation;
    String locationAddress;
    AppLocationService appLocationService;
    ImageButton buttonHome;
    ImageButton buttonRandomize;

    // Redirect
    //location end
    Spinner.OnClickListener switchSpinBd = new Spinner.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (spinner_Bd.isEnabled()) {
                spinner_Bd.setEnabled(false);
            } else {
                spinner_Bd.setEnabled(true);
            }
        }
    };
    private double latitude;
    private double longitude;
    View.OnClickListener Navigation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent();
            if (v.equals(buttonHome)) {
                i.setClass(getBaseContext(), HomePage.class);
            } else if (v.equals(buttonRandomize)) {
                //put Extra Filter Options
                int budget = Integer.parseInt(spinner_Bd.getSelectedItem().toString());
                String distancevalue = spinner_lt.getSelectedItem().toString();
                distancevalue = distancevalue.replaceAll("meters", "");
                float distance = Float.parseFloat(distancevalue);
                //return spinner
                i.putExtra("location_spinner", spinner_lt.isEnabled());
                i.putExtra("Budget_spinner", spinner_Bd.isEnabled());
                //
                i.putExtra("Budget", budget);
                i.putExtra("Distance", distance);
                i.putExtra("Latitude", latitude);
                i.putExtra("Longitude", longitude);
                i.setClass(getBaseContext(), Randomize.class);

            }
            startActivity(i);
            finish();
        }
    };
    Spinner.OnClickListener switchSpinLt = new Spinner.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (spinner_lt.isEnabled())
                spinner_lt.setEnabled(false);
            else {
                spinner_lt.setEnabled(true);
                location();
            }
        }

    };

    public void location() {

        // two types, since GPS sometimes wont work or took long time to load
        Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);

        Location networkLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();

            LocationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeocoderHandler());
        } else if (networkLocation != null) {
            latitude = networkLocation.getLatitude();
            longitude = networkLocation.getLongitude();

            LocationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeocoderHandler());
        } else {
            showSettingsAlert();
        }

    }

    // Ask user to change location permission setting
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                FilterMenu.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        AlertDialog.Builder settings = alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        FilterMenu.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    // Ask user to change location permission setting
    public void PrintLocation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                FilterMenu.this);
        alertDialog.setTitle("CURRENT LOCATION");
        alertDialog.setMessage(locationAddress + "\n IS THIS YOUR CURRENT LOCATION? ");
        AlertDialog.Builder settings = alertDialog.setPositiveButton("Refresh",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        location();
                    }
                });
        alertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tvAddress.setText("Your Current Location: \n" + locationAddress);
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_menu);
        //budget
        Budget = (Switch) findViewById(R.id.ms_budget);
        spinner_Bd = (Spinner) findViewById(R.id.sn_budget);
        spinner_lt = (Spinner) findViewById(R.id.sn_location);
        location = (Switch) findViewById(R.id.ms_nearMe);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvBudgetLocation = (TextView) findViewById(R.id.selectedBudgetLocation);
        buttonHome = (ImageButton) findViewById(R.id.fbutton_home);
        buttonRandomize = (ImageButton) findViewById(R.id.fbutton_randomize);

        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer> (this, android.R.layout.simple_dropdown_item_1line, budget);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.budget, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner_Bd.setEnabled(false);
        spinner_Bd.setAdapter(adapter);

        Budget.setOnClickListener(switchSpinBd);


        appLocationService = new AppLocationService(FilterMenu.this);
        //location
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.distance, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        spinner_lt.setEnabled(false);

        spinner_lt.setAdapter(adapter2);
        location.setOnClickListener(switchSpinLt);

        buttonHome.setOnClickListener(Navigation);
        buttonRandomize.setOnClickListener(Navigation);

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            PrintLocation();
        }
    }


}

