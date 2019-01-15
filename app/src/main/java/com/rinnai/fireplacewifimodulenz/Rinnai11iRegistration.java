package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import AWSmodule.AWSconnection;

/**
 * Created by JConci on 8/02/2018.
 */

public class Rinnai11iRegistration extends MillecActivityBase {

    ImageButton ViewId_imagebutton29;

    Button ViewId_button36;
    Button ViewId_button37;
    Button ViewId_button38;

    TextView ViewId_textview126;
    TextView ViewId_textview127;
    TextView ViewId_textview129;
    TextView ViewId_textview191;

    ViewGroup ViewId_include_releaseappliance_registrationi;
    ViewGroup ViewId_include_releaseappliance_lockout_registrationi;

    LinearLayout ViewId_linearlayout_appliance_row;

    TableLayout ViewId_appliance_tableLayout;

    Intent intent;

    boolean isClosing = false;

    int scrollviewrowrinnai11iregistration_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11i_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onCreate.");

        getAWSCustomerAppliance();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imageButton29 (Cross) *****//
        ViewId_imagebutton29 = (ImageButton) findViewById(R.id.imageButton29);

        ViewId_imagebutton29.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton29.setImageResource(R.drawable.registration_button_cross_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton29.setImageResource(R.drawable.registration_button_cross);

                        isClosing = true;
                        intent = new Intent(Rinnai11iRegistration.this, Rinnai11hRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11iRegistration: startActivity(Rinnai11hRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton29.setImageResource(R.drawable.registration_button_cross);
                }
                return false;
            }
        });

        //***** OnTouchListener - button36 (Claim appliance) *****//
        ViewId_button36 = (Button) findViewById(R.id.button36);
        ViewId_textview126 = (TextView) findViewById(R.id.textView126);

        ViewId_button36.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button36.setBackgroundResource(R.drawable.registration_button_small_red_background_pressed);
                        ViewId_textview126.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button36.setBackgroundResource(R.drawable.registration_button_small_red_background);
                        ViewId_textview126.setTextColor(Color.parseColor("#FFFFFFFF"));

                        AppGlobals.userregClaimAppliance = true;

                        isClosing = true;
                        intent = new Intent(Rinnai11iRegistration.this, Rinnai11cRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11iRegistration: startActivity(Rinnai11hRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button36.setBackgroundResource(R.drawable.registration_button_small_red_background);
                        ViewId_textview126.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button37 (Release appliance) *****//
        ViewId_button37 = (Button) findViewById(R.id.button37);
        ViewId_textview127 = (TextView) findViewById(R.id.textView127);
        ViewId_include_releaseappliance_registrationi = (ViewGroup) findViewById(R.id.include_releaseappliance_registrationi);
        ViewId_include_releaseappliance_lockout_registrationi = (ViewGroup) findViewById(R.id.include_releaseappliance_lockout_registrationi);
        ViewId_appliance_tableLayout = (TableLayout) findViewById(R.id.appliance_tableLayout);

        ViewId_button37.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button37.setBackgroundResource(R.drawable.registration_button_small_red_background_pressed);
                        ViewId_textview127.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button37.setBackgroundResource(R.drawable.registration_button_small_red_background);
                        ViewId_textview127.setTextColor(Color.parseColor("#FFFFFFFF"));

                        if (ViewId_appliance_tableLayout.getChildCount() != 0) {
                            ViewId_include_releaseappliance_registrationi.setVisibility(View.VISIBLE);
                            ViewId_include_releaseappliance_lockout_registrationi.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(Rinnai11iRegistration.this, "No Appliances or Web Services Error. \nTry Claim appliance.",
                                    Toast.LENGTH_LONG).show();
                        }
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button37.setBackgroundResource(R.drawable.registration_button_small_red_background);
                        ViewId_textview127.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button38 (Done) *****//
        ViewId_button38 = (Button) findViewById(R.id.button38);
        ViewId_textview129 = (TextView) findViewById(R.id.textView129);
        ViewId_include_releaseappliance_registrationi = (ViewGroup) findViewById(R.id.include_releaseappliance_registrationi);
        ViewId_include_releaseappliance_lockout_registrationi = (ViewGroup) findViewById(R.id.include_releaseappliance_lockout_registrationi);

        ViewId_button38.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button38.setBackgroundResource(R.drawable.registration_button_small_white_background_pressed);
                        ViewId_textview129.setTextColor(Color.parseColor("#FF800000"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button38.setBackgroundResource(R.drawable.registration_button_small_white_background);
                        ViewId_textview129.setTextColor(Color.parseColor("#FFFF0000"));

                        /////////////////////////////////////////
                        //Update customer appliance ownership
                        /////////////////////////////////////////
                        AWSconnection.updateCustomerApplianceURL(AppGlobals.userregInfo.UserRegistrationApplianceInfo_List.get(scrollviewrowrinnai11iregistration_id).userregistrationApplianceSerial, "unassigned",

                                //Call interface to retrieve Async results
                                new AWSconnection.textResult() {
                                    @Override

                                    public void getResult(String result) {

                                        //Do stuff with results here
                                        //Returns either success or error message
                                        //Log. i ( "Update Customer Appliance:" , result);
                                        Log.d("myApp_AWS", "Appliance:" + result);

                                        if (!result.equals("\"Update Successful\"")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Rinnai11iRegistration.this, "Web Services Error.",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Rinnai11iRegistration.this, "Update Successful.",
                                                            Toast.LENGTH_LONG).show();

                                                    getAWSCustomerAppliance();

                                                    ViewId_include_releaseappliance_registrationi.setVisibility(View.INVISIBLE);
                                                    ViewId_include_releaseappliance_lockout_registrationi.setVisibility(View.INVISIBLE);
                                                }
                                            });
                                        }
                                    }
                                });

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button38.setBackgroundResource(R.drawable.registration_button_small_white_background);
                        ViewId_textview129.setTextColor(Color.parseColor("#FFFF0000"));
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onRestart.");

        isClosing = false;

        getAWSCustomerAppliance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11iRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11iRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //*******************************************************//
    //***** scrollviewrowrinnai11iregistrationOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowrinnai11iregistrationOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            //scrollviewrow_pressed = true;

            //Get Selected Text (appliance_serial)
            ViewId_textview191 = ((TextView) v.findViewById(R.id.textView191));
            scrollviewrowrinnai11iregistration_id = Integer.parseInt(ViewId_textview191.getText().toString(), 10);

            updateScrollViewTableLayoutRinnai11iRegistrationDetails();

            //Highlight Selection
            ViewId_linearlayout_appliance_row = ((LinearLayout) v.findViewById(R.id.linearlayout_appliance_row));

            ViewId_linearlayout_appliance_row.setBackgroundResource(R.drawable.registration_yourappliances_background_pressed);
        }
    };

    //*******************************************************************//
    //***** updateScrollViewTableLayoutRinnai11iRegistrationDetails *****//
    //*******************************************************************//

    public void updateScrollViewTableLayoutRinnai11iRegistrationDetails() {

        TableLayout ViewId_appliance_tableLayout = (TableLayout) findViewById(R.id.appliance_tableLayout);

        for (int a = 0; a <= ViewId_appliance_tableLayout.getChildCount() - 1; a++) {

            View ViewId_scrollview_row_multiunit_rinnai11c_registration = ViewId_appliance_tableLayout.getChildAt(a);

            //Highlight Selection
            ViewId_linearlayout_appliance_row = ((LinearLayout) ViewId_scrollview_row_multiunit_rinnai11c_registration.findViewById(R.id.linearlayout_appliance_row));

            ViewId_linearlayout_appliance_row.setBackgroundResource(R.drawable.registration_yourappliances_background);
        }
    }

    //***********************************//
    //***** getAWSCustomerAppliance *****//
    //***********************************//

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
                    //Log.i("Appliance: ", resultList.get(i));
                    Log.d("myApp_AWS", "Appliance: " + resultList.get(i));
                }

                final ArrayList<String> ui_resultList = resultList;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ViewId_appliance_tableLayout = (TableLayout) findViewById(R.id.appliance_tableLayout);
                        //ViewId_wifiaccesspoint_tableLayout.setOnTouchListener(new AutoTimerTableTouchListener());

                        //clear the table, start with blank table
                        ViewId_appliance_tableLayout.removeAllViews();

                        int id = 0;

                        AppGlobals.userregInfo.UserRegistrationApplianceInfo_List.clear();

                        try {
                            if (ui_resultList.size() > 0) {

                                for (int i = 0; i <= ui_resultList.size() - 1; i++) {
                                    Log.d("myApp_AWS", "Rinnai11iRegistration: Appliance (" + ui_resultList + ")");

                                    //Raw split
                                    String[] ui_resultListsplit = ui_resultList.get(i).split("\"");

                                    Log.d("myApp_AWS", "WiFi Dongle UUID: " + ui_resultListsplit[3]);
                                    Log.d("myApp_AWS", "Appliance Serial Number: " + ui_resultListsplit[7]);
                                    Log.d("myApp_AWS", "Fire Model: " + ui_resultListsplit[11]);
                                    Log.d("myApp_AWS", "Email: " + ui_resultListsplit[15]);
                                    Log.d("myApp_AWS", "Nickname: " + ui_resultListsplit[19]);
                                    Log.d("myApp_AWS", "Appliance Type: " + ui_resultListsplit[23]);

                                    //Split "wifi_dongle_UUID"
                                    String[] ui_wifidongleuuidsplita = ui_resultList.get(i).split("wifi_dongle_UUID\":\"");
                                    String[] ui_wifidongleuuidsplitb = ui_wifidongleuuidsplita[1].split("\"");
                                    Log.d("myApp_AWS", "WiFi Dongle UUID(split): " + ui_wifidongleuuidsplitb[0]);

                                    //Split "App_Serial_Num"
                                    String[] ui_appserialnumsplita = ui_resultList.get(i).split("App_Serial_Num\":\"");
                                    String[] ui_appserialnumsplitb = ui_appserialnumsplita[1].split("\"");
                                    Log.d("myApp_AWS", "Appliance Serial Number(split): " + ui_appserialnumsplitb[0]);

                                    //Split "fire_model"
                                    String[] ui_firemodelsplita = ui_resultList.get(i).split("fire_model\":\"");
                                    String[] ui_firemodelsplitb = ui_firemodelsplita[1].split("\"");
                                    Log.d("myApp_AWS", "Fire Model(split): " + ui_firemodelsplitb[0]);

                                    //Split "email"
                                    String[] ui_emailsplita = ui_resultList.get(i).split("email\":\"");
                                    String[] ui_emailsplitb = ui_emailsplita[1].split("\"");
                                    Log.d("myApp_AWS", "Email(split): " + ui_emailsplitb[0]);

                                    //Split "nickname"
                                    String[] ui_nicknamesplita = ui_resultList.get(i).split("nickname\":\"");
                                    String[] ui_nicknamesplitb = ui_nicknamesplita[1].split("\"");
                                    Log.d("myApp_AWS", "Nickname(split): " + ui_nicknamesplitb[0]);

                                    //Split "app_type"
                                    String[] ui_apptypesplita = ui_resultList.get(i).split("app_type\":\"");
                                    String[] ui_apptypesplitb = ui_apptypesplita[1].split("\"");
                                    Log.d("myApp_AWS", "WiFi Dongle UUID(split): " + ui_apptypesplitb[0]);

                                    AppGlobals.userregInfo.UserRegistrationApplianceInfo_List.add(new UserRegistration_ApplianceInfo(ui_apptypesplitb[0], ui_firemodelsplitb[0], ui_nicknamesplitb[0], ui_appserialnumsplitb[0]));

                                    View ViewId_scrollview_row_rinnai11i_registration = getLayoutInflater().inflate(R.layout.scrollview_row_rinnai11i_registration, null, false);

                                    //ViewId_scrollview_row_rinnai11i_registration.setId(id);
                                    ((TextView) ViewId_scrollview_row_rinnai11i_registration.findViewById(R.id.textView191)).setText(id + "");

                                    ViewId_scrollview_row_rinnai11i_registration.setMinimumHeight(50);

                                    //clean text
                                    //nextSSID = ""+nextSSID.subSequence(nextSSID.indexOf("\"")+1,nextSSID.indexOf("\"",1));

                                    //add listener
                                    ViewId_scrollview_row_rinnai11i_registration.setOnClickListener(scrollviewrowrinnai11iregistrationOnClickListener);//add OnClickListener Here

                                    //set it on the row - textView183, app_type (Appliance Type)
                                    ((TextView) ViewId_scrollview_row_rinnai11i_registration.findViewById(R.id.textView183)).setText(ui_apptypesplitb[0]);

                                    //set it on the row - textView184, fire_model (Appliance Model)
                                    ((TextView) ViewId_scrollview_row_rinnai11i_registration.findViewById(R.id.textView184)).setText(ui_firemodelsplitb[0]);

                                    //set it on the row - textView185, nickname (Appliance Name)
                                    ((TextView) ViewId_scrollview_row_rinnai11i_registration.findViewById(R.id.textView185)).setText(ui_nicknamesplitb[0]);

                                    //set it on the row - textView186, App_Serial_Num (Appliance Serial Number)
                                    ((TextView) ViewId_scrollview_row_rinnai11i_registration.findViewById(R.id.textView186)).setText(ui_appserialnumsplitb[0]);

                                    //Add the Row to the table
                                    ViewId_appliance_tableLayout.addView(ViewId_scrollview_row_rinnai11i_registration);

                                    //Next
                                    id++;
                                }
                            } else {
                                Toast.makeText(Rinnai11iRegistration.this, "No Appliances or Web Services Error. \nTry Claim appliance.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("myApp_AWS", "Select appliance owned by a customer: (Exception - " + e + ")");

                            Toast.makeText(Rinnai11iRegistration.this, "Web Services Error. \nTry again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
