package com.example.atayansy.tot.CustomAdapters;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by shermainesy on 12/3/15.
 */

//Custom adapter
public class CustomExpandableListView extends ExpandableListView {
    public CustomExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
		 * Adjust height
		 */
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

