package com.example.atayansy.tot;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shermainesy on 10/8/15.
 */
public class CustomAdpaterFoodFeed extends ArrayAdapter<FoodFeed> {

    ArrayList<FoodFeed> foodfeeds;

    public CustomAdpaterFoodFeed(Context context, int resource, List<FoodFeed> objects) {
        super(context, resource, objects);
        this.foodfeeds = (ArrayList<FoodFeed>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.foodfeed_list_view, parent, false);
        }

        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_feedbackIcon);
        ListView lvComments = (ListView) convertView.findViewById(R.id.lv_comments);

        ivIcon.setImageResource(foodfeeds.get(position).getIcon());

        ArrayList<Comments> commentsArrayList = new ArrayList<>();
        //CustomAdapterComments customAdapterComments = new CustomAdapterComments();
        //lvComments.setAdapter(customAdapterComments);

        return convertView;


    }
}
