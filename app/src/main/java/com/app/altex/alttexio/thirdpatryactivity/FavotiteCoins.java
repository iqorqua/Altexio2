package com.app.altex.alttexio.thirdpatryactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.WalletAdapter;
import com.app.altex.alttexio.customelements.WalletItem;
import com.app.altex.alttexio.fragments.Wallets;

import java.util.ArrayList;

public class FavotiteCoins extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favotite_coins);

        ListView listView = (ListView) findViewById(R.id.favorite_coins_list);
        final ArrayList<WalletItem> currentCoinSet = new ArrayList<WalletItem>();
        currentCoinSet.add(Wallets.currentCoinSet.get(0));
        currentCoinSet.add(Wallets.currentCoinSet.get(1));
        currentCoinSet.add(Wallets.currentCoinSet.get(2));
        ListAdapter adapter = new WalletAdapter(this, currentCoinSet);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewPropertyFavoriteCoin.class);
                intent.putExtra("coin_str_array", i);
                startActivity(intent);
            }
        });
    }
}
