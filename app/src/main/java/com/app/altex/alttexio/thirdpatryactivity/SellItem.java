package com.app.altex.alttexio.thirdpatryactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.CustomIconSpinnerAdapter;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SellItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);
        String[] ITEMS = {"Bitcoin", "Ethereum", "Ripple", "Bitcoin Cash", "Litecoin", "Cardano"};
        int [] ICONS = {R.drawable.bitcoin_icon, R.drawable.eth, R.drawable.nem, R.drawable.monero, R.drawable.neo, R.drawable.litecoin, R.drawable.bitcoin_icon, R.drawable.lisk};
        ArrayAdapter<String> adapter = new CustomIconSpinnerAdapter(this, ICONS, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((MaterialSpinner) findViewById(R.id.spinner)).setAdapter(adapter);
    }
}
