package com.example.atayansy.tot.CustomAdapters;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;

import java.util.ArrayList;

/**
 * Created by shermainesy on 10/8/15.
 */
public class CustomAdapterFoodFeedbacks extends BaseExpandableListAdapter {
    private Context Context;
    private ArrayList<FoodFeedFeedbacks> foodfeeds;

    public CustomAdapterFoodFeedbacks(Context context, ArrayList<FoodFeedFeedbacks> objects) {
        this.Context = context;
        this.foodfeeds = objects;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Comments> Comments = foodfeeds.get(groupPosition).getComments();
        return Comments.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Comments comments = (Comments) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_list_view, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.tv_nameUser);
        TextView setComments = (TextView) convertView.findViewById(R.id.tv_userComments);

        name.setText(comments.getName());
        setComments.setText(comments.getComments());
        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Comments> commentsArrayList = foodfeeds.get(groupPosition).getComments();
        return commentsArrayList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return foodfeeds.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return foodfeeds.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        FoodFeedFeedbacks FoodFeedFeedbacks = (FoodFeedFeedbacks) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) Context
                    .getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.foodfeed_list_view, null);
        }

        String stringId = "p" + Integer.toString(FoodFeedFeedbacks.getIcon());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_feedbackIcon);
        TextView textView = (TextView) convertView.findViewById(R.id.comments);
        imageView.setImageResource(Context.getResources().getIdentifier(stringId, "drawable", Context.getPackageName()));
        textView.setText(String.valueOf(FoodFeedFeedbacks.getComments().size()) + " Comment/s");
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}