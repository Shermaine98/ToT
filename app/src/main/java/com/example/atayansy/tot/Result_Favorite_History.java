package com.example.atayansy.tot;

import android.os.Bundle;
import android.widget.TextView;

public class Result_Favorite_History extends BaseActivity {
    TextView ResultClass;
    TextView foodLabel;
    private String ClassName;
    private String FoodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setUp(R.layout.activity_result__favorite__history);

        ResultClass = (TextView) findViewById(R.id.Fh_ResultText);
        foodLabel = (TextView) findViewById(R.id.tvFh_fodName);

        ClassName = getIntent().getExtras().getString("Class");
        FoodName = getIntent().getExtras().getString("FoodName");

        ResultClass.setText(ClassName.toString());
        foodLabel.setText(FoodName.toString());

    }

}
