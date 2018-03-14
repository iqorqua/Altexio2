package com.app.altex.altexio;

import android.Manifest;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.altex.altexio.fragments.Conversations;
import com.app.altex.altexio.fragments.Cryptoshop;
import com.app.altex.altexio.fragments.Entertainment;
import com.app.altex.altexio.fragments.Settings;
import com.app.altex.altexio.fragments.Wallets;
import com.thanosfisherman.mayi.Mayi;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            findViewById(R.id.mainLO).setBackground(getDrawable(R.drawable.bckg));
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_conversation: {
                    fragment = new Conversations();
                    break;
                }
                case R.id.navigation_wallets: {
                    fragment = new Wallets();
                    break;
                }
                /*case R.id.navigation_entertainment: {
                    fragment = new Entertainment();
                    break;
                }*/
                case R.id.navigation_cryptoshop: {
                    fragment = new Cryptoshop();
                    break;
                }
                case R.id.navigation_settings: {
                    fragment = new Settings();
                    break;
                }
            }

            if(fragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Mayi.withActivity(MainActivity.this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

}
