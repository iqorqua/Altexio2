package com.app.altex.alttexio.customelements;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.altex.alttexio.R;
import com.mikepenz.materialize.color.Material;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * Created by igorqua on 10.05.2018.
 */

public class CustomIconSpinnerAdapter extends ArrayAdapter<String> {
    Context context;
    String [] coins;
    int [] icons;

    public CustomIconSpinnerAdapter(Context context, int [] icons, String[] coins) {
        super(context, R.layout.item_icon_spiner, coins);
        this.context = context;
        this.coins = coins;
        this.icons = icons;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        View result = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_icon_spiner, null);
        ((TextView)result.findViewById(R.id.itemiconspinner_txt)).setText(coins[position]);
        ((ImageView)result.findViewById(R.id.itemiconspinner_imageView)).setImageResource(icons[position]);
        return result;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_icon_spiner, null);
        ((TextView)result.findViewById(R.id.itemiconspinner_txt)).setText(coins[position]);
        ((ImageView)result.findViewById(R.id.itemiconspinner_imageView)).setImageResource(icons[position]);
        return result;
    }


}
