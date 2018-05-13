package com.app.altex.alttexio.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.altex.alttexio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtherumWallet extends Fragment {


    public EtherumWallet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_ethereum_wallet, container, false);
        return result;
    }

}
