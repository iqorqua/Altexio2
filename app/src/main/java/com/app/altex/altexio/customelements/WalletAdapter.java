package com.app.altex.altexio.customelements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.altex.altexio.R;
import com.lucasurbas.listitemview.ListItemView;

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
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = lInflater.inflate(R.layout.item_wallet_btc, parent, false);
        }

        WalletItem wi = getWallettItem(position);
        ((TextView)view.findViewById(R.id.short_name_txt)).setText(wi.walletNameTitle);
        ((TextView)view.findViewById(R.id.long_name_txt)).setText(wi.walletSubTitle);
        ((TextView)view.findViewById(R.id.value_txt)).setText("$" + wi.price);
        ((TextView)view.findViewById(R.id.item_number_txt)).setText((position+1) + ".");
        return view;
    }


    WalletItem getWallettItem(int position) {
        return ((WalletItem) getItem(position));
    }
}
