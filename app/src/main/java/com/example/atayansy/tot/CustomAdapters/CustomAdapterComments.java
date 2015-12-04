package com.example.atayansy.tot.CustomAdapters;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.Comments;

import java.util.ArrayList;

/**
 * Created by shermainesy on 12/5/15.
 */
public class CustomAdapterComments extends ArrayAdapter<Comments> {

    ArrayList<Comments> commentses;

    public CustomAdapterComments(Context context, int resource, ArrayList<Comments> commentses) {
        super(context, resource, commentses);
        this.commentses = commentses;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_list_view, parent, false);

            comments sHolder = new comments();
            sHolder.tvName = (TextView) convertView.findViewById(R.id.tv_nameUser);
            sHolder.tvComment = (TextView) convertView.findViewById(R.id.tv_userComments);
            convertView.setTag(sHolder);
        }
        comments sHolder = (comments) convertView.getTag();
        sHolder.tvName.setText(commentses.get(position).getName());
        sHolder.tvComment.setText(commentses.get(position).getComments());
        return convertView;
    }

    private class comments {
        TextView tvName;
        TextView tvComment;

    }
}
