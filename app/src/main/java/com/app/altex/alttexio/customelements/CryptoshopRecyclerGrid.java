package com.app.altex.alttexio.customelements;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.fragments.Cryptoshop;

public class CryptoshopRecyclerGrid extends RecyclerView.Adapter<CryptoshopRecyclerGrid.ViewHolder> {

    private ImageGridItem[] mData = new ImageGridItem[0];
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CryptoshopRecyclerGrid(Context context, ImageGridItem[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_image_grid, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData[position].getName());
        holder.price_usd.setText(mData[position].getPriceUsd() + " $");
        holder.price_btc.setText("BTC " + mData[position].getPriceBtc());
        holder.image.setImageResource(mData[position].image);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView price_usd;
        TextView price_btc;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textview_name);
            price_usd = (TextView) itemView.findViewById(R.id.textview_price_usd);
            price_btc = (TextView) itemView.findViewById(R.id.textview_price_btc);
            image = (ImageView) itemView.findViewById(R.id.imageview_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ImageGridItem getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}