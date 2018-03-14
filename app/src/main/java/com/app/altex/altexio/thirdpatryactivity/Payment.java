package com.app.altex.altexio.thirdpatryactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.altex.altexio.R;
import com.app.altex.altexio.fragments.PaymentHistory;
import com.app.altex.altexio.fragments.ScanQRPayment;
import com.app.altex.altexio.fragments.StartPayment;

import goldzweigapps.tabs.Builder.EasyTabsBuilder;
import goldzweigapps.tabs.Items.TabItem;
import goldzweigapps.tabs.View.EasyTabs;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        EasyTabs tabs = (EasyTabs) findViewById(R.id.EasyTabs);
        EasyTabsBuilder.with(tabs)
         .addTabs(
                 new TabItem(new PaymentHistory(), "PaymentHistory"),
                 new TabItem(new StartPayment(), "Payment"),
                 new TabItem(new ScanQRPayment(), "ScanQr"))
                .hideAllTitles(true)
                .Build();
    }
}
