package com.app.altex.altexio.customelements;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.app.altex.altexio.R;

import java.util.ArrayList;

/**
 * Created by igorqua on 13.03.2018.
 */

public class GridAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflater;
    ArrayList<ImageGridItem> ITEMS;


    public GridAdapter(Context c, ArrayList<ImageGridItem> items){
        ctx = c;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ITEMS = items;
    }
    @Override
    public int getCount() {
        return ITEMS.size();
    }

    @Override
    public ImageGridItem getItem(int position) {
        return ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_image_grid, viewGroup, false);
        }

        final ImageGridItem item = getItem(position);

        // Load the thumbnail image
        ImageView image = (ImageView) view.findViewById(R.id.imageview_item);

        TextView name = (TextView) view.findViewById(R.id.textview_name);
        //    image.setImageUrl(item.getThumbnailUrl(), mImageLoader);
        image.setImageResource(item.image);

        // Set the TextView's contents
        name.setText(item.getName());

        // END_INCLUDE(grid_set_view_name)

        return view;
    }

}