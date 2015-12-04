package com.example.atayansy.tot.CustomAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.atayansy.tot.R;
import com.example.atayansy.tot.java.ImageResources;

/**
 * Created by Dindin on 12/4/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mThumbIds;

    // Constructor
    public ImageAdapter(Context c, int[] resources) {
        mContext = c;
        mThumbIds = resources;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        ImageResources ir = new ImageResources();
        imageView.setImageResource(ir.getImage(mThumbIds[position], mContext));
        return imageView;
    }

}
