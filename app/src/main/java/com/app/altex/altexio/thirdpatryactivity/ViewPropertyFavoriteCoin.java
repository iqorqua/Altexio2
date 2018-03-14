package com.app.altex.altexio.thirdpatryactivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.app.altex.altexio.R;

import java.text.Format;

public class ViewPropertyFavoriteCoin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_favorite_coin);
        String data[] = getIntent().getStringArrayExtra("coin_str_array");
        ((TextView)findViewById(R.id.vievCoin_txt_view)).setText("Price USD: $" + data[2] + "\r\n"
                + "Price BTC: 0.010122\r\n"
                + "Market Cap: $20.51451 \r\n"
                + "Volume (24h): $523576154\r\n"
                + "Circulating Supply: 21,24,2333");
    }
}
