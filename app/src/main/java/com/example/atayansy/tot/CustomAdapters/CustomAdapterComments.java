package com.example.atayansy.tot.CustomAdapters;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.Comments;

import java.util.ArrayList;

/**
 * Created by shermainesy on 12/5/15.
 */
public class CustomAdapterComments extends BaseAdapter {
    Context context;
    ArrayList<Comments> commentses;

    public CustomAdapterComments(Context context, ArrayList<Comments> objects) {
        this.context = context;
        this.commentses = objects;

    }


    @Override
    public int getCount() {
        return commentses.size();
    }

    @Override
    public Object getItem(int position) {
        return commentses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_list_view, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.tv_nameUser);
        TextView setComments = (TextView) convertView.findViewById(R.id.tv_userComments);

        name.setText("@" + commentses.get(position).getName());
        setComments.setText(commentses.get(position).getComments());
        return convertView;
    }
}
