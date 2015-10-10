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
import com.example.atayansy.tot.java.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shermainesy on 10/10/15.
 */
public class CustomAdapterFavorite extends ArrayAdapter<Favorite> {
    ArrayList<Favorite> favorites;

    public CustomAdapterFavorite(Context context, int resource, List<Favorite> objects) {
        super(context, resource, objects);
        this.favorites = (ArrayList<Favorite>) objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //inflate meaning... the xml
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_favorite_list_view, parent, false);
        }
        //customize convertview to change the text, and the icon
        TextView foodName = (TextView) convertView.findViewById(R.id.tv_FoodName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_foodPic);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rbstar);

        foodName.setText(favorites.get(position).getName());
        imageView.setImageResource(favorites.get(position).getPictureIcon());
        ratingBar.setNumStars(favorites.get(position).getRatingStar());
        return convertView;

    }
}

