package com.example.atayansy.tot;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shermainesy on 10/8/15.
 */
public class CustomAdapterComments extends ArrayAdapter {
    ArrayList<Comments> comments;

    public CustomAdapterComments(Context context, int resource, List objects, ArrayList<Comments> comments) {
        super(context, resource, objects);
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.foodfeed_list_view, parent, false);
        }
         TextView tvName = (TextView) convertView.findViewById(R.id.tv_nameUser);
         TextView tvComments= (TextView) convertView.findViewById(R.id.tv_userComments);

        tvName.setText(comments.get(position).getName());
        tvComments.setText(comments.get(position).getComments());
        return convertView;
    }
}

