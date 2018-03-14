package com.app.altex.altexio.customelements;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by igorqua on 13.03.2018.
 */

public class WalletItem {
    public String walletNameTitle;
    public String walletSubTitle;
    public double price;

    public WalletItem(String title, String subtitle, double price_p){
        walletNameTitle = title;
        walletSubTitle = subtitle;
        price = price_p;
    }
}
