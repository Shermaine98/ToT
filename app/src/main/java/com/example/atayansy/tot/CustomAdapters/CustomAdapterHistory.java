package com.example.atayansy.tot.CustomAdapters;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.HistoryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shermainesy on 10/10/15.
 */
public class CustomAdapterHistory extends ArrayAdapter<HistoryObject> {
    ArrayList<HistoryObject> histories;

    public CustomAdapterHistory(Context context, int resource, List<HistoryObject> objects) {
        super(context, resource, objects);
        this.histories = (ArrayList<HistoryObject>) objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_favorite_list_view, parent, false);
        }

        //customize convertview to change the text, and the icon
        TextView foodName = (TextView) convertView.findViewById(R.id.tv_FoodName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_foodPic);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rb_star);

        foodName.setText(histories.get(position).gethName());
        imageView.setImageResource(histories.get(position).gethPictureIcon());
        ratingBar.setRating(histories.get(position).getrRatingStar());
        return convertView;

    }
}
