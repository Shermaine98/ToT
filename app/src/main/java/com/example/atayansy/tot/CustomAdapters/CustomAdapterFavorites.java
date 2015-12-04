package com.example.atayansy.tot.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.FavoriteObject;

import java.util.ArrayList;

/**
 * Created by shermainesy on 12/4/15.
 */
public class CustomAdapterFavorites extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Context context;
    private ArrayList<FavoriteObject> favoriteObjects;

    public CustomAdapterFavorites(Context context, ArrayList<FavoriteObject> objects) {
        this.context = context;
        this.favoriteObjects = objects;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return favoriteObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grindview_favorites, null);

        holder.img = (ImageView) rowView.findViewById(R.id.gf_image);
        holder.foodName = (TextView) rowView.findViewById(R.id.gf_foodName);
        holder.foodPrice = (TextView) rowView.findViewById(R.id.gf_price);
        String stringId = "p" + Integer.toString(favoriteObjects.get(position).getfPictureIcon());

        holder.img.setImageResource(context.getResources().getIdentifier(stringId, "drawable", context.getPackageName()));
        holder.foodName.setText(favoriteObjects.get(position).getfName());
        holder.foodPrice.setText(String.valueOf(favoriteObjects.get(position).getPrice()));


        return rowView;
    }

    public class Holder {
        TextView foodName;
        TextView foodPrice;
        ImageView img;
    }
}
