package com.app.altex.alttexio.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.GridAdapter;
import com.app.altex.alttexio.customelements.ImageGridItem;
import com.app.altex.alttexio.thirdpatryactivity.SellItem;
import com.app.altex.alttexio.thirdpatryactivity.ShopView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cryptoshop extends Fragment {

    public static ArrayList<ImageGridItem> currentItemsSet = new ArrayList<>();
    GridView gv;

    public Cryptoshop() {
        // Required empty public constructor
        currentItemsSet = new ArrayList<ImageGridItem>(Arrays.asList(ITEMS));
    }

    private ImageGridItem[] ITEMS = new ImageGridItem[]{
            new ImageGridItem("iPhone x", "ETH 0.25", R.drawable.apple_iphone_x_256gb_space_gray_images_2433232441),
            new ImageGridItem("Asus", "ETH 0.45", R.drawable.asus),
            new ImageGridItem("Dell", "ETH 0.55", R.drawable.dell),
            new ImageGridItem("Phone", "ETH 0.22", R.drawable.pic),
            new ImageGridItem("Gaming", "ETH 0.65", R.drawable.pic1),
            new ImageGridItem("S9", "ETH 0.35", R.drawable.s9),
            new ImageGridItem("Asus", "ETH 0.55", R.drawable.sus),
            new ImageGridItem("Razer", "ETH 0.28", R.drawable.razer),
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_cryptoshop, container, false);
        result.findViewById(R.id.floatingActionButton_add_goods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SellItem.class);
                startActivity(intent);
            }
        });
        gv = (GridView) result.findViewById(R.id.grid);
        gv.setAdapter(new GridAdapter(this.getContext(), currentItemsSet));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getContext(), ShopView.class);
                intent.putExtra("pos", i);
                startActivity(intent);

            }
        });

        ((TextInputEditText)result.findViewById(R.id.cryptoshop_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentItemsSet.clear();
                for (ImageGridItem i:ITEMS){
                    if (i.getName().contains(editable.toString())){
                        currentItemsSet.add(i);
                    }
                }

                ListAdapter adapter = new GridAdapter(getContext(), currentItemsSet);
                gv.setAdapter(adapter);
            }
        });
        return result;
    }

}
