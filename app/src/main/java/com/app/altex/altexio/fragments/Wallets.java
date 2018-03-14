package com.app.altex.altexio.fragments;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.altex.altexio.R;
import com.app.altex.altexio.customelements.WalletAdapter;
import com.app.altex.altexio.customelements.WalletItem;
import com.app.altex.altexio.thirdpatryactivity.FavotiteCoins;
import com.app.altex.altexio.thirdpatryactivity.Payment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallets extends Fragment {

    ListView listView;
    ArrayList<WalletItem> wallesItems;
    ArrayList<WalletItem> currentCoinSet;

    public Wallets() {
        // Required empty public constructor
        wallesItems = new ArrayList<>();
        currentCoinSet = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View result =  inflater.inflate(R.layout.fragment_wallets, container, false);
        listView = (ListView)result.findViewById(R.id.wallet_listview);
        wallesItems.add(new WalletItem("BTC", "Bitcoin", 9087));
        wallesItems.add(new WalletItem("ETR", "Etherium", 682));
        wallesItems.add(new WalletItem("XRP", "Ripple", 0.7854));
        wallesItems.add(new WalletItem("LTC", "Litecoin", 174));
        wallesItems.add(new WalletItem("NEO", "Neo", 82.254));
        currentCoinSet = (ArrayList<WalletItem>)wallesItems.clone();
        ListAdapter adapter = new WalletAdapter(getContext(), wallesItems);
        listView.setAdapter(adapter);

        final BarChart chart = (BarChart) result.findViewById(R.id.wallet_chart);

        BarData data = new BarData(getDataSet());
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        ((TextInputEditText)result.findViewById(R.id.wallet_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentCoinSet.clear();
                for (WalletItem i:wallesItems){
                    if (i.walletNameTitle.contains(editable.toString())){
                        currentCoinSet.add(i);
                    }
                }
                BarData data = new BarData(getDataSet());
                chart.setData(data);
                chart.animateXY(2000, 2000);
                chart.invalidate();
                ListAdapter adapter = new WalletAdapter(getContext(), currentCoinSet);
                listView.setAdapter(adapter);
            }
        });

        result.findViewById(R.id.wallet_favorite_coin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FavotiteCoins.class);
                startActivity(intent);
            }
        });
        result.findViewById(R.id.wallet_btn_witdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Payment.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String temp = currentCoinSet.get(i).walletNameTitle + ":" + currentCoinSet.get(i).walletSubTitle + ":" + currentCoinSet.get(i).price;
                FileOutputStream is;
                BufferedReader reader;

                return false;
            }
        });
        return result;
    }


    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;

        dataSets = new ArrayList<>();
        /*dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);*/
        for (int i=0;i<currentCoinSet.size();i++){
            ArrayList<BarEntry> btc_set = new ArrayList<>();
            BarEntry be = new BarEntry(i, (float) currentCoinSet.get(i).price);
            btc_set.add(be);
            BarDataSet dset = new BarDataSet(btc_set,  currentCoinSet.get(i).walletNameTitle);
            if((i%2)==0){
                dset.setColor(Color.rgb(204, 0, 0));
            }
            else{
                dset.setColor( Color.rgb(102, 255, 51));
            }
            dataSets.add(dset);
        }
        return dataSets;
    }
}
