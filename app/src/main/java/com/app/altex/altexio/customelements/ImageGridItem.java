package com.app.altex.altexio.customelements;

/**
 * Created by igorqua on 13.03.2018.
 */


import com.app.altex.altexio.R;

/**
 * Represents an Item in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class ImageGridItem {

    private static final String LARGE_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/";
    private static final String THUMB_BASE_URL = "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/";



    private final String mName;
    private final String mAuthor;
    public final int image;

    public ImageGridItem(String name, String author, int fileName) {
        mName = name;
        mAuthor = author;
        image = fileName;
    }

    public int getId() {
        return mName.hashCode() + image;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getName() {
        return mName;
    }
}

