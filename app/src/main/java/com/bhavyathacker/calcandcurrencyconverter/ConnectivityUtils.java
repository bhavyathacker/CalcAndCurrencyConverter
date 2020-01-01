package com.bhavyathacker.calcandcurrencyconverter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityUtils {
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo currentNetworkInfo = connMgr.getActiveNetworkInfo();
            if (currentNetworkInfo != null) {
                if (currentNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return currentNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}
