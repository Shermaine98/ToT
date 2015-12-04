package com.example.atayansy.tot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atayansy.tot.CustomAdapters.CustomAdapterComments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.ImageResources;

public class ResultActivity extends AppCompatActivity {

    Button button_eat;
    Button button_main;
    ImageView imageView;
    TextView resultFoodName, resultDescription, resultPrice;
    CustomAdapterComments customAdapterComments;
    ListView listView;
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
            finish();
        }
    };
    private FoodFeedFeedbacks result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultFoodName = (TextView) findViewById(R.id.tv_fodName);
        resultDescription = (TextView) findViewById(R.id.tv_foodescription);
        resultPrice = (TextView) findViewById(R.id.tv_price);
        button_eat = (Button) findViewById(R.id.button_eat);
        button_main = (Button) findViewById(R.id.button_main);
        imageView = (ImageView) findViewById(R.id.tv_foodImage);
        listView = (ListView) findViewById(R.id.evFoodComment_Result);

        button_eat.setOnClickListener(decision);
        button_main.setOnClickListener(decision);

        result = (FoodFeedFeedbacks) getIntent().getSerializableExtra("Result");

        if (result.equals(null)) {
            resultFoodName.setText("NONE");
            button_eat.setVisibility(Button.INVISIBLE);
            button_main.setVisibility(Button.INVISIBLE);
            resultPrice.setText("");
            resultDescription.setText("");
        } else {
            resultPrice.setText("P" + String.valueOf(result.getPrice()));
            resultFoodName.setText(result.getFoodName());
            resultDescription.setText(result.getDefinition());
            ImageResources imageResources = new ImageResources();
            imageView.setImageResource(imageResources.getImage(result.getImage(), getBaseContext()));
            customAdapterComments = new CustomAdapterComments(getBaseContext(), R.layout.comment_list_view, result.getComments());
            listView.setAdapter(customAdapterComments);
        }
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
