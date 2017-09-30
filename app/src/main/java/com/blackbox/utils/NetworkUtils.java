package com.blackbox.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Arnaud on 30/09/2017.
 */

public class NetworkUtils {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static final String SERVICES_URL = "http://192.168.0.26:3000";

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static Boolean getConnectivityStatusBoolean(Context context) {
        int conn = NetworkUtils.getConnectivityStatus(context);
        boolean status = false;
        if (conn == NetworkUtils.TYPE_WIFI) {
            status = true;
        } else if (conn == NetworkUtils.TYPE_MOBILE) {
            status = true;
        } else if (conn == NetworkUtils.TYPE_NOT_CONNECTED) {
            status = false;
        }
        return status;
    }
}
