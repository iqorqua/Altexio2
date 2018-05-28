package com.app.altex.alttexio.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.WalletAdapter;
import com.app.altex.alttexio.customelements.WalletItem;
import com.app.altex.alttexio.etherum_wallet.fragments.FragmentWallets;
import com.app.altex.alttexio.thirdpatryactivity.FavotiteCoins;
import com.app.altex.alttexio.thirdpatryactivity.ViewPropertyFavoriteCoin;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import eu.amirs.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Wallets extends Fragment {
    private static final String TAG = "Main";
    ListView listView;
    ArrayList<WalletItem> wallesItems;
    public static ArrayList<WalletItem> currentCoinSet;
    BarChart chart;

    public Wallets() {
        // Required empty public constructor
        wallesItems = new ArrayList<>();
        currentCoinSet = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View result = inflater.inflate(R.layout.fragment_wallets, container, false);
        listView = (ListView) result.findViewById(R.id.wallet_listview);


        chart = (BarChart) result.findViewById(R.id.wallet_chart);


        ((TextInputEditText) result.findViewById(R.id.wallet_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentCoinSet.clear();
                for (WalletItem i : wallesItems) {
                    if (i.symbol.contains(editable.toString())) {
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
                /*Intent intent = new Intent(getContext(), Payment.class);
                startActivity(intent);*/
                ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FragmentWallets()).commit();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ViewPropertyFavoriteCoin.class);
                intent.putExtra("coin_str_array", i);
                startActivity(intent);
            }
        });
        UpdateBitcoinData();
        return result;
    }

    private void UpdateBitcoinData() {
        wallesItems.clear();
        Log.d(TAG, "Wallets -> new UpdateBitcoinDataAsyncTask");
        new UpdateBitcoinDataAsyncTask(getActivity(), this, wallesItems).execute();
    }

    private static class UpdateBitcoinDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<FragmentActivity> activityReference;
        private WeakReference<Wallets> walletsReference;
        private ArrayList<WalletItem> wallesItems;

        private UpdateBitcoinDataAsyncTask(FragmentActivity activity, Wallets wallets, ArrayList<WalletItem> wallesItems) {
            activityReference = new WeakReference<>(activity);
            walletsReference = new WeakReference<>(wallets);
            this.wallesItems = wallesItems;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Wallets doInBackground");
            OkHttpClient client = new OkHttpClient();
            try {
                Request request;
                request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/?limit=10").get().build();
                Response response = client.newCall(request).execute();
                JSON json = new JSON(response.body().string());
                for (int i = 0; i < json.count(); i++) {
                    JSONObject js = (JSONObject) json.getJsonArray().get(i);
                    wallesItems.add(new WalletItem(
                            js.getString("id"),
                            js.getString("name"),
                            js.getString("symbol"),
                            js.getDouble("price_usd"),
                            js.getDouble("price_btc"),
                            js.getDouble("total_supply"),
                            js.getDouble("percent_change_1h"),
                            js.getDouble("percent_change_24h"),
                            js.getDouble("percent_change_7d"),
                            js.getDouble("last_updated")));
                }

                currentCoinSet = (ArrayList<WalletItem>) wallesItems.clone();
                FragmentActivity activity = activityReference.get();
                if (activity == null) return null;
                final ListAdapter adapter = new WalletAdapter(activity, wallesItems);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Wallets doInBackground runOnUiThread");
                        Wallets wallets = walletsReference.get();
                        if (wallets == null) return;
                        Log.d(TAG, "Wallets doInBackground setAdapter");
                        wallets.listView.setAdapter(adapter);
                        wallets.getDataSet();
                    }
                });
            } catch (IOException e) {
                Log.d(TAG, "Wallets IOException " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.d(TAG, "Wallets JSONException " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.d(TAG, "Wallets Exception " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
    }

    private ArrayList<IBarDataSet> getDataSet() {

        ArrayList<IBarDataSet> dataSets = null;

        dataSets = new ArrayList<>();
        /*dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);*/
        for (int i = 0; i < currentCoinSet.size(); i++) {
            ArrayList<BarEntry> btc_set = new ArrayList<>();
            BarEntry be = new BarEntry(i, (float) currentCoinSet.get(i).price_usd);
            btc_set.add(be);
            BarDataSet dset = new BarDataSet(btc_set, currentCoinSet.get(i).symbol);
            if ((i % 2) == 0) {
                dset.setColor(Color.rgb(204, 0, 0));
            } else {
                dset.setColor(Color.rgb(102, 255, 51));
            }
            dataSets.add(dset);
        }
        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.invalidate();
        return dataSets;
    }
}
