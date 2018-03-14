package com.app.altex.altexio.thirdpatryactivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.altex.altexio.R;
import com.app.altex.altexio.customelements.ImageGridItem;
import com.app.altex.altexio.fragments.Cryptoshop;

public class ShopView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_view);
        ImageGridItem item = Cryptoshop.currentItemsSet.get(getIntent().getIntExtra("pos", 0));
        ((ImageView)findViewById(R.id.shop_image_preview)).setImageResource(item.image);
        ((TextView)findViewById(R.id.shop_txt_goodname)).setText(item.getName());
        ((TextView)findViewById(R.id.shop_txt_goodprice)).setText(item.getAuthor());
    }
}
