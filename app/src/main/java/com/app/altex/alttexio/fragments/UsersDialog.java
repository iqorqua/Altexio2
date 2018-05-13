package com.app.altex.alttexio.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.UserListAdapter;
import com.app.altex.alttexio.customelements.UserListItem;
import com.app.altex.alttexio.firebaseandroid.adapter.ChatFirebaseAdapter;
import com.app.altex.alttexio.firebaseandroid.adapter.ClickListenerChatFirebase;
import com.app.altex.alttexio.firebaseandroid.util.UserList;
import com.app.altex.alttexio.firebaseandroid.util.Util;
import com.app.altex.alttexio.firebaseandroid.view.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersDialog extends Fragment implements ClickListenerChatFirebase {
    private static final String TAG = "UserList";
    private DatabaseReference userlistReference;
    private ValueEventListener mUserListListener;
    public static ArrayList<UserListItem> usernamelist = new ArrayList<>();
    UserListAdapter arrayAdapter;

    //Firebase and GoogleApiClient
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseUser mFirebaseUser;
    public static DatabaseReference mFirebaseDatabaseReference;

    ListView userListView;
    private boolean notSended = true;

    public UsersDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        View result = inflater.inflate(R.layout.fragment_users_dialog, container, false);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getContext(), LoginActivity.class));
            //finish();
        }
        else {
            userlistReference = FirebaseDatabase.getInstance().getReference().child("users");
            onStart();
            userListView = (ListView) result.findViewById(R.id.userlistview);
            userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Conversations.CHAT_REFERENCE =
                    char[] to = usernamelist.get(i).id.toCharArray();
                    char[] me = FirebaseAuth.getInstance().getCurrentUser().getUid().toCharArray();
                    for (int j = 0; j < usernamelist.get(i).id.length(); j++) {
                        if (me[j] > to[j]) {
                            Conversations.CHAT_REFERENCE = FirebaseAuth.getInstance().getCurrentUser().getUid() + usernamelist.get(i).id;
                            Conversations.speaking_to = usernamelist.get(i);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Conversations()).commit();
                            break;
                        }
                        if (me[j] < to[j]) {
                            Conversations.CHAT_REFERENCE = usernamelist.get(i).id + FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Conversations.speaking_to = usernamelist.get(i);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Conversations()).commit();
                            break;
                        }
                    }
                }
            });
        }
        return result;
    }
    @Override
    public void onStart() {
        super.onStart();
        final ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usernamelist.clear();
                DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                String me = FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"#"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "#" + FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
                ArrayList<String> users = new ArrayList<>();
                HashMap hashMap = ((HashMap)dataSnapshot.getValue());
                if(hashMap != null){
                    users = new ArrayList<>(Arrays.asList(Arrays.copyOf(((HashMap)dataSnapshot.getValue()).values().toArray(), ((HashMap)dataSnapshot.getValue()).values().toArray().length, String[].class)));
                }

                for (Object user:users) {
                    String data[] = ((String)user).split("#");
                    if(!data[1].equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        usernamelist.add(new UserListItem(data[0], data[1], data[2]));
                }
                if (!users.contains(me)&notSended){
                    mFirebaseDatabaseReference.push().setValue(me);
                    notSended = false;
                }

                arrayAdapter = new UserListAdapter(getContext(), usernamelist);
                userListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
                Toast.makeText(getContext(), "Failed to load User list.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        if (userlistReference == null)
            userlistReference = FirebaseDatabase.getInstance().getReference().child("users");
        userlistReference.addValueEventListener(userListener);

        mUserListListener = userListener;
    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mUserListListener != null) {
            userlistReference.removeEventListener(mUserListListener);
        }

    }

    @Override
    public void clickImageChat(View view, int position, String nameUser, String urlPhotoUser, String urlPhotoClick) {

    }

    @Override
    public void clickImageMapChat(View view, int position, String latitude, String longitude) {

    }
}
