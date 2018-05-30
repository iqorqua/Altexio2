package com.app.altex.alttexio.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.CryptoshopRecyclerGrid;
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
    RecyclerView rv;

    public Cryptoshop() {
        // Required empty public constructor
        currentItemsSet = new ArrayList<ImageGridItem>(Arrays.asList(ITEMS));
    }

    private ImageGridItem[] ITEMS = new ImageGridItem[]{
            new ImageGridItem("iPhone x", "0.25", "345", R.drawable.apple_iphone_x_256gb_space_gray_images_2433232441),
            new ImageGridItem("Asus", "0.45","567", R.drawable.asus),
            new ImageGridItem("Dell", "0.55","6784", R.drawable.dell),
            new ImageGridItem("Phone", "0.22","3421", R.drawable.pic),
            new ImageGridItem("Gaming", "0.65","1254", R.drawable.pic1),
            new ImageGridItem("S9", "0.35","442", R.drawable.s9),
            new ImageGridItem("Asus", "0.55","562", R.drawable.sus),
            new ImageGridItem("Razer", "0.28","2463", R.drawable.razer),
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
        rv = (RecyclerView)result.findViewById(R.id.grid_recycler);
        rv.setLayoutManager(new GridLayoutManager( getContext(), 2));
        UpdateDataSet();

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

                UpdateDataSet();
            }
        });
        return result;
    }

    private void UpdateDataSet() {
        CryptoshopRecyclerGrid adapter = new CryptoshopRecyclerGrid(getContext(), currentItemsSet.toArray(new ImageGridItem[0]));
        adapter.setClickListener(new CryptoshopRecyclerGrid.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ShopView.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
        rv.setAdapter(adapter);
    }

}
