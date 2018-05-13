package com.app.altex.alttexio.etherum_wallet.interfaces;

import android.hardware.fingerprint.FingerprintManager;

public interface FingerprintListener {
    public void authenticationFailed(String error);

    public void authenticationSucceeded(FingerprintManager.AuthenticationResult result);
}