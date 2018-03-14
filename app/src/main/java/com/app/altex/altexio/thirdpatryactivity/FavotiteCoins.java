package com.app.altex.altexio.thirdpatryactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.altex.altexio.R;
import com.app.altex.altexio.customelements.WalletAdapter;
import com.app.altex.altexio.customelements.WalletItem;

import java.util.ArrayList;

public class FavotiteCoins extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favotite_coins);

        ListView listView = (ListView) findViewById(R.id.favorite_coins_list);
        final ArrayList<WalletItem> currentCoinSet = new ArrayList<WalletItem>();
        currentCoinSet.add(new WalletItem("BTC", "Bitcoin", 9087));
        currentCoinSet.add(new WalletItem("ETR", "Etherium", 682));
        currentCoinSet.add(new WalletItem("XRP", "Ripple", 0.7854));
        currentCoinSet.add(new WalletItem("LTC", "Litecoin", 174));
        currentCoinSet.add(new WalletItem("NEO", "Neo", 82.254));
        ListAdapter adapter = new WalletAdapter(this, currentCoinSet);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewPropertyFavoriteCoin.class);
                intent.putExtra("coin_str_array", new String[]{currentCoinSet.get(i).walletNameTitle, currentCoinSet.get(i).walletSubTitle, currentCoinSet.get(i).price+""});
                startActivity(intent);
            }
        });
    }
}
