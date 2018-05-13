package com.app.altex.alttexio.customelements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.firebaseandroid.util.UserList;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by igorqua on 16.03.2018.
 */

public class UserListAdapter extends BaseAdapter {
    ArrayList<UserListItem> objects;
    Context ctx;
    LayoutInflater lInflater;

    public UserListAdapter(Context context, ArrayList<UserListItem> items){
        objects = items;
        ctx = context;
        //lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(ctx);
            view = vi.inflate(R.layout.item_userlist, null);
        }

        UserListItem user = getUserListItem(position);
        ((TextView)view.findViewById(R.id.user_txt_name)).setText(user.name);
       // ((TextView)view.findViewById(R.id.user_txt_mail)).setText(user.mail);
        Picasso.get().load(user.imgUrl).centerInside().fit().into((ImageView)view.findViewById(R.id.user_img_avatar));
        return view;
    }


    UserListItem getUserListItem(int position) {
        return ((UserListItem) getItem(position));
    }
}
