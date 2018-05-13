package com.app.altex.alttexio.firebaseandroid.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.altex.alttexio.MainActivity;
import com.app.altex.alttexio.R;
import com.app.altex.alttexio.customelements.UserListAdapter;
import com.app.altex.alttexio.customelements.UserListItem;
import com.app.altex.alttexio.firebaseandroid.model.ChatModel;
import com.app.altex.alttexio.firebaseandroid.model.UserModel;
import com.app.altex.alttexio.firebaseandroid.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by igorqua on 16.03.2018.
 */

public class UserList extends AppCompatActivity {

    private static final String TAG = "UserList";
    private DatabaseReference userlistReference;
    private ValueEventListener mUserListListener;
    public static ArrayList<UserListItem> usernamelist = new ArrayList<>();
    UserListAdapter arrayAdapter;
    ;

    ListView userListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userlistReference = FirebaseDatabase.getInstance().getReference().child("users");
        onStart();
        userListView = (ListView) findViewById(R.id.userlistview);


    }

    @Override
    protected void onStart() {
        super.onStart();
        final ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usernamelist.clear();
                DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
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
                if (!users.contains(me)){
                    mFirebaseDatabaseReference.child("users").push().setValue(me);
                }
                /*ArrayList<UserListItem> im_list = new ArrayList<>();
                for (UserListItem i: usernamelist){
                    if(i.id.equals(FirebaseAuth.getInstance().getUid())){
                        im_list.add(i);
                        break;
                    }
                }
                if(im_list.size() == 0){
                    DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                    mFirebaseDatabaseReference.child("users").push().setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"#"+FirebaseAuth.getInstance().getCurrentUser().getUid() + "#" + FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                }
                usernamelist.removeAll(im_list);*/
                arrayAdapter = new UserListAdapter(getBaseContext(), usernamelist);
                userListView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
                Toast.makeText(UserList.this, "Failed to load User list.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        userlistReference.addValueEventListener(userListener);

        mUserListListener = userListener;
    }

    public String usernameOfCurrentUser() {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            //finish();
        } else {
            String email = mFirebaseAuth.getCurrentUser().getEmail();
            if (email.contains("@")) {
                return email.split("@")[0];
            } else {
                return email;
            }
        }
        return "";
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mUserListListener != null) {
            userlistReference.removeEventListener(mUserListListener);
        }

    }

  /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
           case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
