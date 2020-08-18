package com.example.weatherforecast.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//Name and Matric Number: Hafsah Kamran, S1627179
public class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "wifi";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "mobile_data";
                return status;
            }else if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                status = "ethernet";
                return status;
            }
        } else {
            status = "no_internet";
            return status;
        }
        return status;
    }
}