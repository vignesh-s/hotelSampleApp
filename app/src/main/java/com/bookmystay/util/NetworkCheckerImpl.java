package com.bookmystay.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bookmystay.repository.NetworkChecker;

public final class NetworkCheckerImpl implements NetworkChecker {

    private Context mContext;

    public NetworkCheckerImpl(Context context) {
        mContext = context;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }
}
