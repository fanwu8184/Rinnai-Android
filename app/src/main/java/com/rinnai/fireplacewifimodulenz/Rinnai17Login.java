package com.rinnai.fireplacewifimodulenz;

import android.Manifest;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import AWSmodule.AWSconnection;

import static java.lang.Long.parseLong;

/**
 * Created by jconci on 14/09/2017.
 */

public class Rinnai17Login extends MillecActivityBase
        implements ActivityClientInterfaceTCP, ActivityServerInterfaceUDP, ActivityTimerInterface {

    ImageView ViewId_imageview18;



    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Timer fireanimationCheckTimer;
    int fireanimationCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    boolean isDeviceGetVersion = false;
    boolean isDeviceSetTime = false;

    boolean isAccessPoint = false;

    int scrollviewrowmultiunitrinnai21homescreen_id = 0;

    String secondsSinceMondayHexLittleEndian = "";

    LinearLayout ViewId_linearlayout_multiunit_row;

    private boolean isShowList = false;
    private boolean isTimer = false;

    ProgressBar progressBarOnStart;

    ArrayList<Appliance> appliances = new ArrayList<Appliance>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai17_login);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            // Permission is not granted

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }



        }else {
            isTimer = true;
            Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onCreate.");

            Runtime rt = Runtime.getRuntime();
            long maxMemory = rt.maxMemory();
            Log.d("myApp_Memory", "maxMemory:" + Long.toString(maxMemory));

            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            int memoryClass = am.getMemoryClass();
            Log.d("myApp_Memory", "memoryClass:" + Integer.toString(memoryClass));

            //startFireAnimation();
            progressBarOnStart = (ProgressBar) findViewById(R.id.progressBarOnStart);

            this.startCommunicationErrorFault();

            this.appStart();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onStart.");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onRestart.");

        isClosing = false;
        isDeviceGetVersion = false;
        isDeviceSetTime = false;

        try {
            AppGlobals.UDPSrv.stopServer();
            AppGlobals.UDPSrv.setCurrentActivity(this);
            AppGlobals.UDPSrv.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiUDP", "Rinnai17Login: onRestart(Exception - " + e + ")");
        }

        startFireAnimation();

        startCommunicationErrorFault();

        startTxRN171DeviceGetStatus();

//        if(AppGlobals.rfwmEmail != null){
//            if(!AppGlobals.rfwmEmail.equals("NA")) {
//                showWifiList();
//            } else {
//                goToLoginPage();
//            }
//        } else {
//            goToLoginPage();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onPause.");
        if(isTimer){
            AppGlobals.CommErrorFault.stopTimer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onStop.");

        if (AppGlobals.rfwmInitialSetupFlag == true) {
            AppGlobals.UDPSrv.stopServer();
        }
        if(isTimer){
            startupCheckTimer.cancel();
            //fireanimationCheckTimer.cancel();
            progressBarOnStart.setVisibility(View.INVISIBLE);
            isClosing = true;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai17Login_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai17Login_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent myIntent = new Intent(getBaseContext(), Rinnai17Login.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    this.startActivity(myIntent);

                    finish();//we are done with this activity

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showPopup();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void showPopup(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Location is required.")
                .setMessage("Can't use Wi-Fi when location is off since Android 6.0")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent myIntent = new Intent(getBaseContext(), Rinnai17Login.class);
                        startActivity(myIntent);

                        //we are done with this activity
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close the app
                        finish();
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog al = builder.create();
        al.requestWindowFeature(Window.FEATURE_NO_TITLE);
        al.show();
    }

    private void showWifiList(){

        addRemoteDevices();

        ImageButton closeBtn = (ImageButton) findViewById(R.id.imBtn_close);
        closeBtn.setVisibility(View.INVISIBLE);

        //      check fireplace wifi if only one
        if(AppGlobals.fireplaceWifi.size() == 1){
            AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;
//          auto select only one fireplace and proceed to home screen
            goToHomePage();
        }else{

            isShowList = true;

            AppGlobals.UDPSrv.stopServer();
            progressBarOnStart.setVisibility(View.INVISIBLE);

            ViewGroup ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);

            ViewId_include_multiunit.setVisibility(View.VISIBLE);

            TableLayout ViewId_multiunit_tableLayout;

            ViewId_multiunit_tableLayout = (TableLayout) findViewById(R.id.multiunit_tableLayout);

            TextView tvHoldPress = (TextView) findViewById(R.id.textView74);
            tvHoldPress.setVisibility(View.GONE);

            //clear the table, start with blank table
            ViewId_multiunit_tableLayout.removeAllViews();

            int id = 0;

            for (int i = 0; i <= AppGlobals.fireplaceWifi.size() - 1; i++) {


                View ViewId_scrollview_row_multiunit_rinnai21_home_screen = getLayoutInflater().inflate(R.layout.scrollview_row_multiunit_rinnai21_home_screen, null, false);

                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView80)).setText(id + "");

                if(!AppGlobals.fireplaceWifi.get(i).isRemote){
                    ((ImageView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.ivRemote)).setVisibility(View.INVISIBLE);
                }
                //add listener
                ViewId_scrollview_row_multiunit_rinnai21_home_screen.setOnClickListener(wifiListOnclickListener);//add OnClickListener Here

                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView77)).setText(AppGlobals.fireplaceWifi.get(i).DeviceName + "");

                //Add the Row to the table
                ViewId_multiunit_tableLayout.addView(ViewId_scrollview_row_multiunit_rinnai21_home_screen);

                //Next
                id++;
            }
        }

//        Button ViewId_button14 = (Button) findViewById(R.id.button14);
//        ViewId_button14.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(AppGlobals.userregInfo.userregistrationEmail.equals("NA")){
//                    AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;
//                    intent = new Intent(Rinnai17Login.this, Rinnai11bRegistration.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;
//                    intent = new Intent(Rinnai17Login.this, Rinnai21HomeScreen.class);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });

    }

    public void removeHighlight() {

        TableLayout ViewId_multiunit_tableLayout = (TableLayout) findViewById(R.id.multiunit_tableLayout);

        for (int a = 0; a <= AppGlobals.fireplaceWifi.size() - 1; a++) {

            View ViewId_scrollview_row_multiunit_rinnai21_home_screen = ViewId_multiunit_tableLayout.getChildAt(a);

            //Highlight Selection
            ViewId_linearlayout_multiunit_row = ((LinearLayout) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.linearlayout_multiunit_row));

            ViewId_linearlayout_multiunit_row.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    private View.OnClickListener wifiListOnclickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //Get Selected Text (timersdaysofweek)
            TextView ViewId_textview80 = ((TextView) v.findViewById(R.id.textView80));
            scrollviewrowmultiunitrinnai21homescreen_id = Integer.parseInt(ViewId_textview80.getText().toString(), 10);

            removeHighlight();

            //Highlight Selection
            ViewId_linearlayout_multiunit_row = ((LinearLayout) v.findViewById(R.id.linearlayout_multiunit_row));

            ViewId_linearlayout_multiunit_row.setBackgroundColor(Color.parseColor("#32FFFFFF"));

            AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;

            ViewGroup ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);
            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress == null) {
                goToHomePage();
            } else {
                Tx_RN171DeviceGetVersion();
            }


//            intent = new Intent(Rinnai17Login.this, Rinnai21HomeScreen.class);
//            startActivity(intent);
//            finish();
        }
    };

    //********************//
    //***** appStart *****//
    //********************//
    void goToLoginPage() {
        isClosing = true;
        AppGlobals.CommErrorFault.stopTimer();
        Intent intent = new Intent(Rinnai17Login.this, Rinnai11bRegistration.class);
        startActivity(intent);
        finish();
    }

    void goToHomePage() {
        AppGlobals.UDPSrv.stopServer();
        intent = new Intent(Rinnai17Login.this, Rinnai21HomeScreen.class);
        startActivity(intent);
        finish();
    }

    void goToOTAPage() {
        AppGlobals.UDPSrv.stopServer();
        if (startupCheckTimer != null) {
            startupCheckTimer.cancel();
        }
        isClosing = true;
        intent = new Intent(Rinnai17Login.this, Rinnai12OTA.class);
        startActivity(intent);
        finish();
    }

    void appStart() {

                try {
                    AppGlobals.UDPSrv.stopServer();
                    AppGlobals.UDPSrv.setCurrentActivity(this);
                    AppGlobals.UDPSrv.start();
                } catch (Exception e) {
                    Log.d("myApp_WiFiUDP", "Rinnai17Login: appStart(Exception - " + e + ")");
                }

                AppGlobals.loadPersistentStorage(Rinnai17Login.this);

                AppGlobals.userregInfo.userregistrationEmail = AppGlobals.rfwmEmail;
                AppGlobals.userregInfo.userregistrationPassword = AppGlobals.rfwmPassword;


                String compareAP = "RinnaiWiFi_";
                String currentAP = NetworkFunctions.getCurrentAccessPointName(this);

                Log.d("myApp_WiFiSystem", "Rinnai17Login_appStart: AP FOUND (" + currentAP + ")");
                Log.d("myApp_WiFiSystem", "Rinnai17Login_appStart: AP FOUND Length (" + currentAP.length() + ")");

                if (currentAP.contains(compareAP) && currentAP.length() == 17) {
                    Log.d("myApp_WiFiSystem", "Rinnai17Login_appStart: CONNECTED TO AP.");

                    isAccessPoint = true;

                } else {
                    Log.d("myApp_WiFiSystem", "Rinnai17Login_appStart:NOT CONNECTED IN AP.");

                    isAccessPoint = false;

                    if(AppGlobals.rfwmEmail != null){
                        if(!AppGlobals.rfwmEmail.equals("NA")) {
                            getAWSCustomerAppliance();
                        }
                    }
                }
                startTxRN171DeviceGetStatus();
    }

    //******************************//
    //***** startFireAnimation *****//
    //******************************//

    public void startFireAnimation() {

//        this.fireanimationCheckTimerCount = 0;
//
//        this.fireanimationCheckTimer = new Timer();
//
//        this.fireanimationCheckTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//                Log.d("myApp", "startFireAnimation: Tick.. " + fireanimationCheckTimerCount);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // Load the ImageView that will host the animation and
//                        // set its background to our AnimationDrawable XML resource.
//                        ViewId_imageview18 = (ImageView) findViewById(R.id.imageView18);
//
//                        switch(fireanimationCheckTimerCount){
//                            case 1:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_1);
//                                break;
//                            case 2:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_5);
//                                break;
//                            case 3:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_10);
//                                break;
//                            case 4:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_15);
//                                break;
//                            case 5:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_20);
//                                break;
//                            case 6:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_25);
//                                break;
//                            case 7:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_30);
//                                break;
//                            case 8:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_35);
//                                break;
//                            case 9:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_40);
//                                break;
//                            case 10:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_45);
//                                break;
//                            case 11:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_50);
//                                break;
//                            case 12:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_55);
//                                break;
//                            case 13:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_55);
//                                break;
//                            case 14:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_50);
//                                break;
//                            case 15:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_45);
//                                break;
//                            case 16:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_40);
//                                break;
//                            case 17:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_35);
//                                break;
//                            case 18:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_30);
//                                break;
//                            case 19:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_25);
//                                break;
//                            case 20:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_20);
//                                break;
//                            case 21:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_15);
//                                break;
//                            case 22:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_10);
//                                break;
//                            case 23:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_5);
//                                break;
//                            case 24:
//                                ViewId_imageview18.setBackgroundResource(R.drawable.fireanimation_1);
//                                fireanimationCheckTimerCount = 0;
//                                break;
//                        }
//
//                        fireanimationCheckTimerCount++;
//                    }
//                });
//            }
//
//        }, 0, 100);

    }

    //*********************************//
    //***** getSecondsSinceMonday *****//
    //*********************************//

    public void getSecondsSinceMonday() {

        int dayofweek_sincemonday = 0;
        int seconds_sincemonday = 0;

        secondsSinceMondayHexLittleEndian = "";

        //**********************************************************************************//
        //***** Get Current Time: DAY, MONTH, DATE, TIME, TIME ZONE ABBREVIATION, YEAR *****//
        //**********************************************************************************//
        //eg. Wed Nov 29 16:23:18 AEDT 2017

        Date currentTime = Calendar.getInstance().getTime();
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: currentTime (" + currentTime + ")");

        //Convert DAY OF WEEK to Day of week since Monday.
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: dayofWeek (" + day + ")");

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                dayofweek_sincemonday = 6;
                break;

            case Calendar.MONDAY:
                // Current day is Monday
                dayofweek_sincemonday = 0;
                break;

            case Calendar.TUESDAY:
                // Current day is TUESDAY
                dayofweek_sincemonday = 1;
                break;

            case Calendar.WEDNESDAY:
                // Current day is WEDNESDAY
                dayofweek_sincemonday = 2;
                break;

            case Calendar.THURSDAY:
                // Current day is THURSDAY
                dayofweek_sincemonday = 3;
                break;

            case Calendar.FRIDAY:
                // Current day is FRIDAY
                dayofweek_sincemonday = 4;
                break;

            case Calendar.SATURDAY:
                // Current day is SATURDAY
                dayofweek_sincemonday = 5;
                break;
            default:
                break;
        }
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: dayofweek_sincemonday (" + dayofweek_sincemonday + ")");

        //Convert Day of week since Monday to Seconds since Monday.
        //Add to seconds_sincemonday running total.
        seconds_sincemonday += dayofweek_sincemonday * 60 * 60 * 24;
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday (" + seconds_sincemonday + ")");

        //************************************//
        //***** Get Current Hour of day. *****//
        //************************************//

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: hourofday (" + hour + ")");

        //Convert Current Hour of day to Seconds since Monday.
        //Add to seconds_sincemonday running total.
        seconds_sincemonday += hour * 60 * 60;
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday (" + seconds_sincemonday + ")");

        //**************************************//
        //***** Get Current Minute of day. *****//
        //**************************************//

        int minute = calendar.get(Calendar.MINUTE);
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: minuteofday (" + minute + ")");

        //Convert Current Minute of day to Seconds since Monday.
        //Add to seconds_sincemonday running total.
        seconds_sincemonday += minute * 60;
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday (" + seconds_sincemonday + ")");

        //**************************************//
        //***** Get Current Second of day. *****//
        //**************************************//

        int second = calendar.get(Calendar.SECOND);
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: secondofday (" + second + ")");

        //Add Current Second of day to seconds_sincemonday running total (FINAL).
        seconds_sincemonday += second;
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday-FINAL (" + seconds_sincemonday + ")");

        //***************************************************************//
        //***** Get Seconds Since Monday - HexLittleEndian (FINAL). *****//
        //***************************************************************//

        //Convert seconds_sincemonday running total to seconds_sincemonday-Hex String (This is Big Endian)
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday-Hex_FINAL (" + String.format("%08X", seconds_sincemonday) + ")");

        //Convert seconds_sincemonday-Hex String (Big Endian) to secondsSinceMondayHexLittleEndian (Final).
        for (int i = String.format("%08X", seconds_sincemonday).length() - 2; i >= 0; i -= 2) {
            secondsSinceMondayHexLittleEndian += String.format("%08X", seconds_sincemonday).substring(i, i + 2);
        }
        Log.d("myApp", "Rinnai17Login_getSecondsSinceMonday: seconds_sincemonday-HexLittleEndian_FINAL (" + secondsSinceMondayHexLittleEndian + ")");

    }

    //***************************************//
    //***** startTxRN171DeviceGetStatus *****//
    //***************************************//

    public void startTxRN171DeviceGetStatus() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        getSecondsSinceMonday();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai17Login: Tick.. " + startupCheckTimerCount);

                if (startupCheckTimerCount >= 10) {

                    if (AppGlobals.fireplaceWifi.size() >= 1) {

                        if (isAccessPoint == true) {

                            if (isDeviceSetTime == false) {

                                if (startupCheckTimerCount % 2 == 0) {
                                    Tx_RN171DeviceSetTime();
                                    Log.d("myApp", "Rinnai17Login_startTxRN171DeviceGetStatus - Tx_RN171DeviceSetTime");
                                }
                            } else {
                                if (startupCheckTimerCount % 2 == 0) {

                                    startupCheckTimer.cancel();
                                    //fireanimationCheckTimer.cancel();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBarOnStart.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                    isClosing = true;
                                    isAccessPoint = false;
                                    intent = new Intent(Rinnai17Login.this, Rinnai00fInitialSetupNetwork.class);
                                    startActivity(intent);

                                    finish();
                                    Log.d("myApp_WiFiTCP", "Rinnai17Login_startTxRN171DeviceGetStatus: startActivity(Rinnai00fInitialSetupNetwork).");
                                }
                            }
                        } else {
                            if (AppGlobals.userregInfo.userregistrationEmail == null && AppGlobals.userregInfo.userregistrationPassword == null) {
                                AppGlobals.rfwmUserFlag = 0;
                            } else {
                                AppGlobals.rfwmUserFlag = 1;
                            }

                            if (AppGlobals.rfwmUserFlag == 1) {

                                if (isDeviceGetVersion == false) {
                                    //Tx_RN171DeviceGetVersion();
                                    Log.d("myApp", "Rinnai17Login_startTxRN171DeviceGetStatus - Tx_RN171DeviceGetVersion");
                                } else {
                                    if (isDeviceSetTime == false) {

                                        if (startupCheckTimerCount % 2 == 0) {
                                            Tx_RN171DeviceSetTime();
                                            Log.d("myApp", "Rinnai17Login_startTxRN171DeviceGetStatus - Tx_RN171DeviceSetTime");
                                        }
                                    } else {
                                        if (startupCheckTimerCount % 2 == 0) {
                                            Tx_RN171DeviceGetStatus();
                                            Log.d("myApp", "Rinnai17Login_startTxRN171DeviceGetStatus - Tx_RN171GetStatus");
                                        }
                                    }
                                }
                            } else {
                                AppGlobals.UDPSrv.stopServer();

                                startupCheckTimer.cancel();
                                //fireanimationCheckTimer.cancel();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarOnStart.setVisibility(View.INVISIBLE);
                                    }
                                });
                                goToLoginPage();
//                                isClosing = true;
//                                intent = new Intent(Rinnai17Login.this, Rinnai11aRegistration.class);
//                                startActivity(intent);
//
//                                finish();
//                                Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai11aRegistration).");
                            }
                        }
                    } else {
                        //show box
                    }
                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }

    //************************//
    //***** UDP - Server *****//
    //************************//

    @Override
    public void serverCallBackUDP(String text) {
        Log.d("myApp_WiFiUDP", "Rinnai17Login_serverCallBackUDP: " + text);
    }

    //************************//
    //***** TCP - Client *****//
    //************************//

    @Override
    public void clientCallBackTCP(String commandID, String text) {
        final String pType = commandID;
        final String pText = text;

        if (isClosing == true) {
            return;
        }

        if (commandID != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (pText.contains("RINNAI_10") && pText.contains("OTA")) {
                        goToOTAPage();
                    }

                    if(AppGlobals.fireplaceWifi.size() == 1){
                        try {
                            if (pType.contains("10")) {
                                Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Device Version (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion + ")");

                                if ((2.10f > AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                        (1.99f < AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                        (2.02f != AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion)) {

                                    showUpdateVersionPopup();
                                } else {
                                    isDeviceGetVersion = true;
                                }
                            }

                            if (pType.contains("12")) {
                                Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Set Time Result (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).settimeResult + ")");

                                if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).settimeResult.equals("OK")) {
                                    isDeviceSetTime = true;
                                }
                            }

                            if (pType.contains("22")) {

                                //*****************************//
                                //***** Main Power Switch *****//
                                //*****************************//

                                Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Main Power Switch (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch + ")");

                                //Main power switch = ON:[0x00]
                                if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch == 0) {

                                    //***************************//
                                    //***** Error Code HIGH *****//
                                    //***************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Error Code HIGH (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI + ")");

                                    //***************************//
                                    //***** Error Code LOW *****//
                                    //***************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Error Code LOW (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO + ")");

                                    //Error code HIGH = no error code:[Hx(0x20), Dec(32)]
                                    //Error code LOW = no error code:[Hx(0x20), Dec(32)]
                                    if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI == 32 && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO == 32) {

                                        //***************************//
                                        //***** Operation State *****//
                                        //***************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Operation State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState + ")");

                                        //Operation state = Stop:[0x00]
                                        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 0) {
                                            AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = false;
                                        }

                                        //Operation state = Operate:[0x01]
                                        else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 1) {
                                            AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = true;
                                        }

                                        //Operation state = Error stop:[0x02]
                                        else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 2) {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai26Fault.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                        } else {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai26Fault.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                        }

                                        //*************************//
                                        //***** Burning State *****//
                                        //*************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: Burning State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState + ")");

                                        //Burning state = Extinguish:[0x00]
                                        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 0) {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;



                                            if(AppGlobals.rfwmEmail != null){
                                                if(!AppGlobals.rfwmEmail.equals("NA")) {
                                                    showWifiList();
                                                } else {
                                                    goToLoginPage();
                                                }
                                            } else {
                                                goToLoginPage();
                                            }
                                        }

                                        //Burning state = Ignite:[0x01]
                                        else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 1) {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai22IgnitionSequence.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai22IgnitionSequence).");
                                        }

                                        //Burning state = Thermostat:[0x02]
                                        else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 2) {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai21HomeScreen.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                        }

                                        //Burning state = Thermostat OFF:[0x03]
                                        else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 3) {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai21HomeScreen.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                        } else {
                                            AppGlobals.UDPSrv.stopServer();

                                            startupCheckTimer.cancel();
                                            //fireanimationCheckTimer.cancel();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressBarOnStart.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                            isClosing = true;
                                            intent = new Intent(Rinnai17Login.this, Rinnai26Fault.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                        }

                                    } else {


                                        AppGlobals.UDPSrv.stopServer();

                                        startupCheckTimer.cancel();
                                        //fireanimationCheckTimer.cancel();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressBarOnStart.setVisibility(View.INVISIBLE);
                                            }
                                        });
                                        isClosing = true;

                                        showWifiList();
//                                        intent = new Intent(Rinnai17Login.this, Rinnai26Fault.class);
//                                        startActivity(intent);
//
//                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                    }
                                }
                                //Main power switch = OFF:[0x01]
                                else {
                                    AppGlobals.UDPSrv.stopServer();

                                    startupCheckTimer.cancel();
                                    //fireanimationCheckTimer.cancel();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBarOnStart.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                    showWifiList();
//                                    intent = new Intent(Rinnai17Login.this, Rinnai26PowerOff.class);
//                                    startActivity(intent);
//                                    finish();
                                    Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai26PowerOff).");
                                }
                            }
                        } catch (Exception e) {
                            Log.d("myApp_WiFiTCP", "Rinnai17Login: clientCallBackTCP(Exception - " + e + ")");
                            Log.d("myApp_WiFiTCP", "Rinnai17Login: clientCallBackTCP(RX - " + pText + ")");
                        }
                    }else{
                        if (pType.contains("10")) {

                            if ((2.10f > AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                    (1.99f < AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                    (2.02f != AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion)) {

                                showUpdateVersionPopup();
                            } else {
                                Tx_RN171DeviceSetTime();

                                isDeviceGetVersion = true;

                                startupCheckTimer.cancel();
                                //fireanimationCheckTimer.cancel();

                                if(AppGlobals.rfwmEmail != null){
                                    if(!AppGlobals.rfwmEmail.equals("NA")) {
                                        goToHomePage();
                                    } else {
                                        goToLoginPage();
                                    }
                                } else {
                                    goToLoginPage();
                                }
                            }
                        }
                    }

                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai17Login: clientCallBackTCP");
    }

    //***** RN171_DEVICE_GET_VERSION *****//
    public void Tx_RN171DeviceGetVersion() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_10,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai17Login: Tx_RN171DeviceGetVersion(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_TIME *****//
    public void Tx_RN171DeviceSetTime() {

        int offSetHourFromUTC = getOffSetHourFromUTC();
        String twoDigitHex = String.format("%02X", offSetHourFromUTC);
        String message =  twoDigitHex + "000000";

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_12," + message + ",E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai17Login: Tx_RN171DeviceSetTime(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_GET_STATUS *****//
    public void Tx_RN171DeviceGetStatus() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_22,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai17Login: Tx_RN171DeviceGetStatus(Exception - " + e + ")");
        }
    }

    //****************************************//
    //***** startCommunicationErrorFault *****//
    //****************************************//

    public void startCommunicationErrorFault() {
        AppGlobals.CommErrorFault.setCurrentActivity(this);

        AppGlobals.CommErrorFault.checkRN171DeviceCommunication();
    }

    //***********************************//
    //***** timereventCallBackTimer *****//
    //***********************************//

    @Override
    public void timereventCallBackTimer(int timerID) {

        if(isAccessPoint){
            startupCheckTimer.cancel();
            //fireanimationCheckTimer.cancel();
            progressBarOnStart.setVisibility(View.INVISIBLE);

            isClosing = true;
            isAccessPoint = false;
            intent = new Intent(Rinnai17Login.this, Rinnai00aInitialSetupThanks.class);
            startActivity(intent);

            finish();
            Log.d("myApp_WiFiTCP", "Rinnai17Login_clientCallBackTCP: startActivity(Rinnai00aInitialSetupThanks).");
        } else if (!isShowList) {
            if(AppGlobals.rfwmEmail != null){
                if(!AppGlobals.rfwmEmail.equals("NA")) {
                    showWifiList();
                } else {
                    goToLoginPage();
                }
            } else {
                goToLoginPage();
            }
        }
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Login
    //public void goToActivity_Rinnai17_Login(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai17Login.class);
    //    startActivity(intent);
    //}

    //Home Screen (Finish)
    //public void goToActivity_Rinnai21_Home_Screen_Finish(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //    finish();
    //}

    //Home Screen
    //public void goToActivity_Rinnai21_Home_Screen(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //}

    //Timers a - Scheduled Timers
    //public void goToActivity_Rinnai33a_Timers(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33aTimers.class);
    //    startActivity(intent);
    //}

    //Timers b - Scheduled Timer
    //public void goToActivity_Rinnai33b_Timers(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33bTimers.class);
    //    startActivity(intent);
    //}

    //Visit Rinnai
    //public void goToActivity_Rinnai35_Visit_Rinnai (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai35VisitRinnai.class);
    //    startActivity(intent);
    //}

    //Visit Rinnai - Website
    //public void goToActivity_Rinnai35_Visit_Rinnai_Website (View view){
    //    String locale = this.getResources().getConfiguration().locale.getCountry();
    //    Log.d("myApp", "Locale: " + locale);
    //
    //    String url;
    //    if(locale.equals("AU")){
    //        url = "http://rinnai.com.au/";
    //
    //        try {
    //            Intent i = new Intent("android.intent.action.MAIN");
    //            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //            i.addCategory("android.intent.category.LAUNCHER");
    //            i.setData(Uri.parse(url));
    //            startActivity(i);
    //        }
    //        catch(ActivityNotFoundException e) {
    //            // Chrome is not installed
    //            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //            startActivity(i);
    //        }
    //    }
    //    else if(locale.equals("NZ")){
    //        url = "https://rinnai.co.nz/";
    //
    //        try {
    //            Intent i = new Intent("android.intent.action.MAIN");
    //            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //            i.addCategory("android.intent.category.LAUNCHER");
    //            i.setData(Uri.parse(url));
    //            startActivity(i);
    //        }
    //        catch(ActivityNotFoundException e) {
    //            // Chrome is not installed
    //            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //            startActivity(i);
    //        }
    //    }
    //    else{
    //        Toast.makeText(this, "Rinnai Website not supported in your region.",
    //                Toast.LENGTH_LONG).show();
    //    }
    //}

    //Visit Rinnai - Facebook
    //public void goToActivity_Rinnai35_Visit_Rinnai_Facebook (View view){
    //    String url = "https://www.facebook.com/";
    //    try {
    //        Intent i = new Intent("android.intent.action.MAIN");
    //        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //        i.addCategory("android.intent.category.LAUNCHER");
    //        i.setData(Uri.parse(url));
    //        startActivity(i);
    //    }
    //    catch(ActivityNotFoundException e) {
    //        // Chrome is not installed
    //        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //        startActivity(i);
    //    }
    //}

    //Visit Rinnai - Youtube
    //public void goToActivity_Rinnai35_Visit_Rinnai_Youtube (View view){
    //    String url = "https://www.youtube.com/";
    //    try {
    //        Intent i = new Intent("android.intent.action.MAIN");
    //        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //        i.addCategory("android.intent.category.LAUNCHER");
    //        i.setData(Uri.parse(url));
    //        startActivity(i);
    //    }
    //    catch(ActivityNotFoundException e) {
    //        // Chrome is not installed
    //        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //        startActivity(i);
    //    }
    //}

    //Visit Rinnai - Phone
    //public void goToActivity_Rinnai35_Visit_Rinnai_Phone (View view){
    //    String locale = this.getResources().getConfiguration().locale.getCountry();
    //    Log.d("myApp", "Locale: " + locale);
    //
    //    if(locale.equals("AU")){
    //        Intent intent = new Intent(Intent.ACTION_DIAL);
    //        intent.setData(Uri.parse("tel:1300555545"));
    //        startActivity(intent);
    //    }
    //    else if(locale.equals("NZ")){
    //        Intent intent = new Intent(Intent.ACTION_DIAL);
    //        intent.setData(Uri.parse("tel:0800746624"));
    //        startActivity(intent);
    //    }
    //    else{
    //        Toast.makeText(this, "Rinnai Phone not supported in your region.",
    //                Toast.LENGTH_LONG).show();
    //    }
    //}

    //Lighting
    //public void goToActivity_Rinnai34_Lighting (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai34Lighting.class);
    //    startActivity(intent);
    //}

    //Network
    //public void goToActivity_Rinnai37_Network (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai37Network.class);
    //    startActivity(intent);
    //}

    //Fault
    //public void goToActivity_Rinnai26_Fault(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26Fault.class);
    //    startActivity(intent);
    //}

    //Fault - Service Fault Codes
    //public void goToActivity_Rinnai33_Service_Fault_Codes(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33ServiceFaultCodes.class);
    //    startActivity(intent);
    //}

    //Power Off
    //public void goToActivity_Rinnai26_Power_Off(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26PowerOff.class);
    //    startActivity(intent);
    //}

    //Ignition Sequence
    //public void goToActivity_Rinnai22_Ignition_Sequence(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai22IgnitionSequence.class);
    //    startActivity(intent);
    //}

    public void getAWSCustomerAppliance() {

        /////////////////////////////////////////
        //Select appliances owned by a customer
        /////////////////////////////////////////
        AWSconnection.selectCustomerApplianceURL(AppGlobals.userregInfo.userregistrationEmail, new AWSconnection.arrayResult() {
            @Override

            //Get Async callback results
            public void getResult(ArrayList<String> resultList) {

                //Do stuff with results here
                int listSize = resultList.size();
                for (int i = 0; i < listSize; i++) {

                    try {
                        String jsonData = resultList.get(i).toString();
                        JSONObject Jobject = new JSONObject(jsonData);
                        String uuid = Jobject.getString("wifi_dongle_UUID");
                        String nickname = Jobject.getString("nickname");
                        appliances.add(new Appliance(uuid, nickname));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public boolean containsUUID(String uuid) {
        for (int i = 0; i < AppGlobals.fireplaceWifi.size(); i++) {
            if (AppGlobals.fireplaceWifi.get(i).UUID.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public void addRemoteDevices() {
        for (int i = 0; i < appliances.size(); i++) {
            if (!containsUUID(appliances.get(i).uuid)) {
                RinnaiFireplaceWiFiModule nFireplace = new RinnaiFireplaceWiFiModule();
                nFireplace.UUID = appliances.get(i).uuid;
                nFireplace.DeviceName = appliances.get(i).name;
                nFireplace.isRemote = true;
                AppGlobals.fireplaceWifi.add(nFireplace);
            }
        }
    }

    private void showUpdateVersionPopup(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Update!")
                .setMessage("An Update for the Rinnai WiFi module is available. Press update to continue.")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goToOTAPage();
                    }
                })
//                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                    }
//                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog al = builder.create();
        al.requestWindowFeature(Window.FEATURE_NO_TITLE);
        al.show();
    }

    private int getOffSetHourFromUTC()
    {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        int offsetFromUtc = tz.getOffset(now.getTime()) / 3600000;
        return offsetFromUtc;
    }
}