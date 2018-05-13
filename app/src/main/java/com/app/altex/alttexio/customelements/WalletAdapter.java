package com.app.altex.alttexio.customelements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.altex.alttexio.R;

import java.util.ArrayList;

/**
 * Created by igorqua on 13.03.2018.
 */

public class WalletAdapter extends BaseAdapter {
    ArrayList<WalletItem> objects;
    Context ctx;
    LayoutInflater lInflater;

    public WalletAdapter(Context context, ArrayList<WalletItem> items){
        objects = items;
        ctx = context;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ((LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_wallet_btc, parent, false);
        }

        WalletItem wi = getWallettItem(position);
        ((TextView)view.findViewById(R.id.short_name_txt)).setText(wi.symbol);
        ((TextView)view.findViewById(R.id.long_name_txt)).setText(wi.name);
        ((TextView)view.findViewById(R.id.value_txt)).setText("$" + wi.price_usd);
        ((TextView)view.findViewById(R.id.item_number_txt)).setText((position+1) + ".");
        return view;
    }


    WalletItem getWallettItem(int position) {
        return ((WalletItem) getItem(position));
    }
}
