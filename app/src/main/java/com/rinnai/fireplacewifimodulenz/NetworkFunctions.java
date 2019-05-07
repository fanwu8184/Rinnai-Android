package com.rinnai.fireplacewifimodulenz;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by chris on 29/3/17.
 */

public class NetworkFunctions {

    public static String getCurrentAccessPointName(Context context) {

        String currentAccessPointName = "";

		/*
		 * Note we need
		 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
		 * in the permission file
		 * (http://stackoverflow.com/questions/6079859/need-to-know-the-wifi-access-point-name-in-android)
		 * */


        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null) {

            WifiInfo info = wifiManager.getConnectionInfo();

            if (info != null) {
                currentAccessPointName = info.getSSID();

            }
        }

        Log.d("myApp_WiFiSystem","NetworkFunctions_getCurrentAccessPointName: AP (" + currentAccessPointName + ")");
        try {
            currentAccessPointName = "" + currentAccessPointName.subSequence(currentAccessPointName.indexOf("\"") + 1, currentAccessPointName.indexOf("\"", 1));
        }
        catch(Exception e){

        }

        Log.d("myApp_WiFiSystem","NetworkFunctions_getCurrentAccessPointName: AP (" + currentAccessPointName + ")");

        return currentAccessPointName;
    }

    public static String getAccessPointScurity(Context context, String CurrentAPName) {

        String scurity = "";

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


        List<ScanResult> networkList = wifiManager.getScanResults();

        if (networkList != null) {
            for (ScanResult network : networkList)
            {
                String Capabilities =  network.capabilities;

                Log.d("myApp_WiFiSystem", "getAccessPointScurity: Network Capabilities (" + network.SSID+":"+Capabilities + ")");

            }
        }

        return scurity;

    }

    public static String getIPAddress(){

		/*
		 *
		 * http://stackoverflow.com/questions/6077555/android-get-external-ip
		 * */

        String ipAddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        Log.d("myApp_WiFiSystem", "getIPAddress: IP (" +ipAddress + ")");

        return ipAddress;
    }

}
