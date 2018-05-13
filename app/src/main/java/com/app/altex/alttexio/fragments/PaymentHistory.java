package com.app.altex.alttexio.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.altex.alttexio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentHistory extends Fragment {


    public PaymentHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_payment_history, container, false);
        String[] dates = {"2.01.2018","21.04.2011","2.03.2018","24.01.2018","21.04.2012","2.01.2108","2.02.2014"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(result.getContext(), android.R.layout.simple_list_item_1, dates);
        ((ListView)result.findViewById(R.id.history_list)).setAdapter(adapter);
        return result;
    }

}
