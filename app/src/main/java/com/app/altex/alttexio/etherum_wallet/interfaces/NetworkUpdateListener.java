package com.app.altex.alttexio.etherum_wallet.interfaces;


import okhttp3.Response;

public interface NetworkUpdateListener {

    public void onUpdate(Response s);
}
