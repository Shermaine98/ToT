package com.example.atayansy.tot.CustomAdapters;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.Comments;
import com.example.atayansy.tot.java.FoodFeedFeedbacks;
import com.example.atayansy.tot.java.ImageResources;

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

        if (childPosition % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#D5D5D5"));
        }

        TextView name = (TextView) convertView.findViewById(R.id.tv_nameUser);
        TextView setComments = (TextView) convertView.findViewById(R.id.tv_userComments);

        name.setText("@"+comments.getName());
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

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_feedbackIcon);
        TextView comments = (TextView) convertView.findViewById(R.id.comments);
        TextView foodName = (TextView) convertView.findViewById(R.id.fv_foodName);
        TextView price = (TextView) convertView.findViewById(R.id.fv_price);
        TextView restaurantName = (TextView) convertView.findViewById(R.id.fv_restaurantName);
        TextView address = (TextView) convertView.findViewById(R.id.fv_restaurantAddress);

        ImageResources ir = new ImageResources();

        imageView.setImageResource(ir.getImage(FoodFeedFeedbacks.getIcon(),Context));
        foodName.setText(FoodFeedFeedbacks.getFoodName());
        price.setText("P"+String.valueOf(FoodFeedFeedbacks.getPrice())+"0");
        restaurantName.setText(FoodFeedFeedbacks.getRestaurant());
        address.setText(FoodFeedFeedbacks.getLocation());
        comments.setText(String.valueOf(FoodFeedFeedbacks.getComments().size()) + " Comment/s");


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}