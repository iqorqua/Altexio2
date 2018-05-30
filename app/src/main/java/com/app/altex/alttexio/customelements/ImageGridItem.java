package com.app.altex.alttexio.customelements;

/**
 * Created by igorqua on 13.03.2018.
 */


/**
 * Represents an Item in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class ImageGridItem {

    private static final String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
    private static final String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";



    private final String mName;
    private final String mPrice_btc;
    private final String mPrice_usd;
    public final int image;

    public ImageGridItem(String name, String price_btc, String price_usd, int fileName) {
        mName = name;
        mPrice_btc = price_btc;
        mPrice_usd = price_usd;
        image = fileName;
    }

    public int getId() {
        return mName.hashCode() + image;
    }

    public String getPriceBtc() {
        return mPrice_btc;
    }

    public String getPriceUsd() {
        return mPrice_usd;
    }

    public String getName() {
        return mName;
    }
}

