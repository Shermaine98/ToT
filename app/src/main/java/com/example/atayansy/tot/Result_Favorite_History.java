package com.example.atayansy.tot;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atayansy.tot.java.FavoriteObject;
import com.example.atayansy.tot.java.Food;

public class Result_Favorite_History extends BaseActivity {
    TextView name;
    TextView desc;
    TextView price;
    ImageView image;
    private FavoriteObject clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_result__favorite__history);

        name = (TextView) findViewById(R.id.Fh_ResultText);
        desc = (TextView) findViewById(R.id.tvfh_foodescription);
        price = (TextView) findViewById(R.id.tvFh_price);

        clicked = (FavoriteObject) getIntent().getSerializableExtra("FaveClicked");

        name.setText(clicked.getfName());
        desc.setText(clicked.getDescription());
        price.setText("P"+ clicked.getPrice() +".00");
//        image.setImageDrawable();

    }

}
