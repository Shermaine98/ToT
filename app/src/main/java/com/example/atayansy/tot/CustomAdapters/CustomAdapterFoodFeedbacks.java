package com.example.atayansy.tot.CustomAdapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
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
    private ArrayList<FoodFeedFeedbacks> FoodFeedFeedbacks;
    private Context context;
    private LayoutInflater inflater;

    public CustomAdapterFoodFeedbacks(Context context, ArrayList<FoodFeedFeedbacks> objects) {
        this.FoodFeedFeedbacks = objects;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Comments getChild(int groupPosition, int childPosition) {
        return FoodFeedFeedbacks.get(groupPosition).getComments().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Comments comments = getChild(groupPosition, childPosition);
        CustomExpandableListView subObjects = (CustomExpandableListView) convertView;
        if (convertView == null) {
            subObjects = new CustomExpandableListView(context);
        }
        Adapter2 adapter = new Adapter2(context, comments);
        subObjects.setAdapter(adapter);

        return subObjects;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return FoodFeedFeedbacks.get(groupPosition).getComments().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return FoodFeedFeedbacks.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return FoodFeedFeedbacks.size();
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
            LayoutInflater inf = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.foodfeed_list_view, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_feedbackIcon);
        imageView.setImageResource(FoodFeedFeedbacks.getIcon());
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


// LEVEL 3
class Adapter2 extends BaseExpandableListAdapter {
    private Comments comments;
    private LayoutInflater inflater;
    private Context context;

    public Adapter2(Context context, Comments comments) {
        this.context = context;
        this.comments = comments;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return comments.getCommentsArray().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Comments object = (Comments) getChild(0, childPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_list_view, null);

            Resources r = context.getResources();
            float px40 =
                    TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
            convertView.setPadding(
                    convertView.getPaddingLeft() + (int) px40,
                    convertView.getPaddingTop(),
                    convertView.getPaddingRight(),
                    convertView.getPaddingBottom());
        }

        TextView name = (TextView) convertView.findViewById(R.id.tv_nameUser);
        TextView setComments = (TextView) convertView.findViewById(R.id.tv_userComments);

        name.setText(object.getName());
        setComments.setText(object.getComments());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return comments.getCommentsArray().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return comments;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_list_view, null);
            Resources r = context.getResources();
            float px20 =
                    TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
            convertView.setPadding(
                    convertView.getPaddingLeft() + (int) px20,
                    convertView.getPaddingTop(),
                    convertView.getPaddingRight(),
                    convertView.getPaddingBottom());
        }
        TextView name = (TextView) convertView.findViewById(R.id.tv_nameUser);
        TextView setComments = (TextView) convertView.findViewById(R.id.tv_userComments);

        name.setText(comments.getName());
        setComments.setText(comments.getComments());
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