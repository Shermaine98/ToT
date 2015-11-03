package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Button button_eat;
    Button button_main;
    TextView resultFoodName;
    View.OnClickListener decision = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent();
            if (v.equals(button_eat)) {
                i.setClass(getBaseContext(), Feedback.class);
            } else if (v.equals(button_main)) {
                i.setClass(getBaseContext(), FilterMenu.class);
            }
            startActivity(i);
        }
    };
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultFoodName = (TextView) findViewById(R.id.tv_fodName);

        button_eat = (Button) findViewById(R.id.button_eat);
        button_main = (Button) findViewById(R.id.button_main);

        button_eat.setOnClickListener(decision);
        button_main.setOnClickListener(decision);

        result = getIntent().getExtras().getString("Result");
        resultFoodName.setText(result);
    /**    Random myRandom = new Random();
       iv_randomize   = (ImageView) findViewById(R.id.ivRandomize);
      /  switch(myRandom.nextInt()%3) {
            case 0:
                iv_randomize.setImageResource(R.mipmap.ic_launcher);
                break;
            case 1:
                iv_randomize.setImageResource(R.drawable.tomato);
                break;
            case 2:
                iv_randomize.setImageResource(R.drawable.food_temp);
                break;
            default:
        }
     **/
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
