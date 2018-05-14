package com.app.altex.alttexio;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.altex.alttexio.etherum_wallet.fragments.FragmentWallets;
import com.app.altex.alttexio.etherum_wallet.utils.WalletStorage;
import com.app.altex.alttexio.firebaseandroid.util.UserList;
import com.app.altex.alttexio.fragments.Conversations;
import com.app.altex.alttexio.fragments.Cryptoshop;
import com.app.altex.alttexio.fragments.Settings;
import com.app.altex.alttexio.fragments.UsersDialog;
import com.app.altex.alttexio.fragments.Wallets;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.thanosfisherman.mayi.Mayi;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            findViewById(R.id.mainLO).setBackground(getDrawable(R.drawable.bckg));
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_conversation: {
                    fragment = new UsersDialog();
                    break;
                }
                case R.id.navigation_wallets: {
                    fragment = new Wallets();
                    break;
                }
                /*case R.id.navigation_entertainment: {
                    fragment = new FragmentWallets();
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
                .withPermissions(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE)
                .check();

        //startActivity(new Intent(getApplicationContext(), UserList.class));
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FragmentWallets.IMPORT_WALLET && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                try {
                    ArrayList<File> files = new ArrayList<>();
                    files.add(new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)));
                    WalletStorage.getInstance(this).importWallets(this, files);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void snackError(String s) {
        Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().getRootView(), s, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
