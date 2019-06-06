package com.rinnai.fireplacewifimodulenz;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import AWSmodule.AWSconnection;

import static com.rinnai.fireplacewifimodulenz.AppGlobals.userregInfo;

/**
 * Created by JConci on 25/01/2018.
 */

public class Rinnai11cRegistration extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    Button ViewId_button23;
    Button ViewId_button24;
    Button ViewId_button25;
    Button ViewId_button26;
    Button ViewId_button45;

    ImageButton ViewId_imagebutton24;

    TextView ViewId_textview92;
    TextView ViewId_textview93;
    TextView ViewId_textview95;
    TextView ViewId_textview96;
    TextView ViewId_textview97;
    TextView ViewId_textview99;
    TextView ViewId_textview174;
    TextView selected_scrollviewrowrinnai11cregistration;

    EditText ViewId_edittext5;
    EditText ViewId_edittext6;
    EditText ViewId_edittext7;
    EditText ViewId_edittext8;
    EditText ViewId_edittext9;
    EditText ViewId_edittext10;
    EditText ViewId_edittext11;
    EditText ViewId_edittext12;
    EditText ViewId_edittext13;
    EditText ViewId_edittext14;
    EditText ViewId_edittext15;

    ViewGroup ViewId_include_content_registrationc;
    ViewGroup ViewId_include_selectfire_registrationc;
    ViewGroup ViewId_include_scrollview_lockout_registrationc;
    ViewGroup ViewId_include_showhints_serialnumber;
    ViewGroup ViewId_include_showhints_lockout_registrationc;

    LinearLayout ViewId_linearlayout_registration_selectfire;
    LinearLayout ViewId_linearlayout_firemodel_row;
    LinearLayout ViewId_linearlayout_textview_selectfire;
    LinearLayout ViewId_linearlayout_textview_firemodel;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardc;

    TableLayout ViewId_firemodel_tableLayout;

    boolean scrollviewrow_pressed = false;

    Intent intent;

    boolean isClosing = false;

    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11c_registration);

        //Permit external connection attempts
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onCreate.");

        setRinnai11cRegistration();


        locale = this.getResources().getConfiguration().locale.getCountry();
        ViewId_textview92 = (TextView) findViewById(R.id.textView92);
        if (locale.equals("AU")) {
            ViewId_textview92.setText("HELP: 1300555545");
        } else if (locale.equals("NZ")) {
            ViewId_textview92.setText("HELP: 0800 RINNAI");
        }

        /////////////////////////////////////////////////
        //Select Appliance List from AWS as an ArrayList
        /////////////////////////////////////////////////
        AWSconnection.selectApplianceListURL(new AWSconnection.arrayResult() {
            @Override

            //Get Async callback results
            public void getResult(ArrayList<String> resultList) {

                ArrayList<String> filteredResult = new ArrayList<String>();
                //Do stuff with results here
                int listSize = resultList.size();
                for (int i = 0; i < listSize; i++) {
                    //Log.i("Appliance: ", resultList.get(i));
                    Log.d("myApp_AWS", "Appliance: " + resultList.get(i));

                    try {
                        JSONObject json = new JSONObject(resultList.get(i));
                        String countryCode = json.getString("fire_country");
                        if (locale.equals(countryCode)) {
                            String fireType = json.getString("fire_type");
                            filteredResult.add(fireType);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                final ArrayList<String> ui_resultList = filteredResult;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (ui_resultList.size() > 0) {

                                ViewId_firemodel_tableLayout = (TableLayout) findViewById(R.id.firemodel_tableLayout);
                                //ViewId_wifiaccesspoint_tableLayout.setOnTouchListener(new AutoTimerTableTouchListener());

                                //clear the table, start with blank table
                                ViewId_firemodel_tableLayout.removeAllViews();

                                int id = 0;

                                for (int i = 0; i <= ui_resultList.size() - 1; i++) {
                                    Log.d("myApp_AWS", "Rinnai11cRegistration: Fire Model (" + ui_resultList + ")");

                                    View ViewId_scrollview_row_rinnai11c_registration = getLayoutInflater().inflate(R.layout.scrollview_row_rinnai11c_registration, null, false);

                                    ViewId_scrollview_row_rinnai11c_registration.setId(id);

                                    ViewId_scrollview_row_rinnai11c_registration.setMinimumHeight(50);

                                    //clean text
                                    //nextSSID = ""+nextSSID.subSequence(nextSSID.indexOf("\"")+1,nextSSID.indexOf("\"",1));

                                    //add listener
                                    ViewId_scrollview_row_rinnai11c_registration.setOnClickListener(scrollviewrowrinnai11cregistrationOnClickListener);//add OnClickListener Here

                                    //set it on the row - textView63, wifiaccesspointName
                                    ((TextView) ViewId_scrollview_row_rinnai11c_registration.findViewById(R.id.textView173)).setText(ui_resultList.get(i));

                                    //Add the Row to the table
                                    ViewId_firemodel_tableLayout.addView(ViewId_scrollview_row_rinnai11c_registration);

                                    //Next
                                    id++;
                                }
                            } else {
                                Toast.makeText(Rinnai11cRegistration.this, "Web Services Error.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("myApp_AWS", "Select Appliance List: (Exception - " + e + ")");

                            Toast.makeText(Rinnai11cRegistration.this, "Web Services Error. \nTry again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button24 (Select Fire - Scrollview) *****//
        ViewId_button24 = (Button) findViewById(R.id.button24);
        ViewId_textview95 = (TextView) findViewById(R.id.textView95);
        ViewId_include_selectfire_registrationc = (ViewGroup) findViewById(R.id.include_selectfire_registrationc);
        ViewId_linearlayout_registration_selectfire = (LinearLayout) findViewById(R.id.linearlayout_registration_selectfire);
        ViewId_include_scrollview_lockout_registrationc = (ViewGroup) findViewById(R.id.include_scrollview_lockout_registrationc);

        ViewId_button24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview95.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview95.setTextColor(Color.parseColor("#FFFFFFFF"));
                        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
                        ViewId_linearlayout_registration_selectfire.requestFocus();
                        hideSoftKeyboard_registrationc();
                        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview95.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button23 (Done - Scrollview) *****//
        ViewId_button23 = (Button) findViewById(R.id.button23);
        ViewId_textview93 = (TextView) findViewById(R.id.textView93);

        ViewId_button23.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button23.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview93.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button23.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview93.setTextColor(Color.parseColor("#FFFFFFFF"));
                        Toast.makeText(Rinnai11cRegistration.this, "Please select Your Fire place model.",
                                Toast.LENGTH_LONG).show();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button23.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview93.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button25 (Select Fire - No Scrollview) *****//
        ViewId_button25 = (Button) findViewById(R.id.button25);
        ViewId_textview96 = (TextView) findViewById(R.id.textView96);

        ViewId_button25.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview96.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview96.setTextColor(Color.parseColor("#FFFFFFFF"));
                        ViewId_include_selectfire_registrationc.setVisibility(View.VISIBLE);
                        ViewId_linearlayout_registration_selectfire.setVisibility(View.INVISIBLE);
                        hideSoftKeyboard_registrationc();
                        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.VISIBLE);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        //ViewId_button25.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview96.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button26 (Done - No Scrollview) *****//
        ViewId_button26 = (Button) findViewById(R.id.button26);
        ViewId_textview97 = (TextView) findViewById(R.id.textView97);
        ViewId_textview174 = (TextView) findViewById(R.id.textView174);
        ViewId_edittext5 = (EditText) findViewById(R.id.editText5);
        ViewId_edittext6 = (EditText) findViewById(R.id.editText6);
        ViewId_edittext7 = (EditText) findViewById(R.id.editText7);
        ViewId_edittext8 = (EditText) findViewById(R.id.editText8);
        ViewId_edittext9 = (EditText) findViewById(R.id.editText9);
        ViewId_edittext10 = (EditText) findViewById(R.id.editText10);
        ViewId_edittext11 = (EditText) findViewById(R.id.editText11);
        ViewId_edittext12 = (EditText) findViewById(R.id.editText12);
        ViewId_edittext13 = (EditText) findViewById(R.id.editText13);
        ViewId_edittext14 = (EditText) findViewById(R.id.editText14);
        ViewId_edittext15 = (EditText) findViewById(R.id.editText15);

        ViewId_button26.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button26.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview97.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button26.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview97.setTextColor(Color.parseColor("#FFFFFFFF"));
                        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
                        ViewId_linearlayout_registration_selectfire.requestFocus();
                        hideSoftKeyboard_registrationc();
                        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);

                        if (!ViewId_textview174.getText().toString().equals("Select Fire") &&
                                !ViewId_edittext5.getText().toString().equals("") &&
                                !ViewId_edittext6.getText().toString().equals("") &&
                                !ViewId_edittext7.getText().toString().equals("") &&
                                !ViewId_edittext8.getText().toString().equals("") &&
                                !ViewId_edittext9.getText().toString().equals("") &&
                                !ViewId_edittext10.getText().toString().equals("") &&
                                !ViewId_edittext11.getText().toString().equals("") &&
                                !ViewId_edittext12.getText().toString().equals("") &&
                                !ViewId_edittext13.getText().toString().equals("") &&
                                !ViewId_edittext14.getText().toString().equals("") &&
                                !ViewId_edittext15.getText().toString().equals("")) {

                            UserRegistration_ApplianceInfo userregapplianceinfo_complete = new UserRegistration_ApplianceInfo("FIRE",
                                    ViewId_textview174.getText().toString(),
                                    ViewId_edittext5.getText().toString(),
                                    ViewId_edittext6.getText().toString() +
                                            ViewId_edittext7.getText().toString() +
                                            ViewId_edittext8.getText().toString() +
                                            ViewId_edittext9.getText().toString() +
                                            ViewId_edittext10.getText().toString() +
                                            ViewId_edittext11.getText().toString() +
                                            ViewId_edittext12.getText().toString() +
                                            ViewId_edittext13.getText().toString() +
                                            ViewId_edittext14.getText().toString() +
                                            ViewId_edittext15.getText().toString());

                            AppGlobals.userregInfo.userregApplianceInfo_now = userregapplianceinfo_complete;

                            //userregInfo.UserRegistrationApplianceInfo_List.add(userregapplianceinfo_complete);

                            Log.d("myApp", "Rinnai11cRegistration_onTouch: button26.");
                            Log.d("myApp", "Rinnai11cRegistration: (Appliance Type: " + AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceType + ")");
                            Log.d("myApp", "Rinnai11cRegistration: (Appliance Model: " + AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceModel + ")");
                            Log.d("myApp", "Rinnai11cRegistration: (Appliance Name: " + AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceName + ")");
                            Log.d("myApp", "Rinnai11cRegistration: (Appliance Serial: " + AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceSerial + ")");

                            if (AppGlobals.userregClaimAppliance == true) {
                                setAWSApplianceRegistration();
                            } else {
                                isClosing = true;
                                intent = new Intent(Rinnai11cRegistration.this, Rinnai11dRegistration.class);
                                startActivity(intent);

                                finish();
                                Log.d("myApp", "Rinnai11cRegistration: startActivity(Rinnai11dRegistration).");
                            }
                        } else {
                            Toast.makeText(Rinnai11cRegistration.this, "1- Select Fire, \n2- Name your fire, \n3- Enter Serial Number.",
                                    Toast.LENGTH_LONG).show();
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button26.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview97.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton24 (Question Mark - No Scrollview) *****//
        ViewId_imagebutton24 = (ImageButton) findViewById(R.id.imageButton24);
        ViewId_include_selectfire_registrationc = (ViewGroup) findViewById(R.id.include_selectfire_registrationc);
        ViewId_include_scrollview_lockout_registrationc = (ViewGroup) findViewById(R.id.include_scrollview_lockout_registrationc);
        ViewId_include_showhints_serialnumber = (ViewGroup) findViewById(R.id.include_showhints_serialnumber);
        ViewId_include_showhints_lockout_registrationc = (ViewGroup) findViewById(R.id.include_showhints_lockout_registrationc);

        ViewId_imagebutton24.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton24.setImageResource(R.drawable.registration_button_questionmark_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton24.setImageResource(R.drawable.registration_button_questionmark);

                        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
                        ViewId_linearlayout_registration_selectfire.requestFocus();
                        hideSoftKeyboard_registrationc();
                        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_include_showhints_serialnumber.setVisibility(View.VISIBLE);
                        ViewId_include_showhints_lockout_registrationc.setVisibility(View.VISIBLE);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton24.setImageResource(R.drawable.registration_button_questionmark);
                }
                return false;
            }
        });

        //***** OnTouchListener - Button45 (Show Hints Lockout) *****//
        ViewId_button45 = (Button) findViewById(R.id.button45);

        ViewId_button45.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
                        ViewId_linearlayout_registration_selectfire.requestFocus();
                        hideSoftKeyboard_registrationc();
                        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);
                        ViewId_include_showhints_serialnumber.setVisibility(View.INVISIBLE);
                        ViewId_include_showhints_lockout_registrationc.setVisibility(View.INVISIBLE);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton24.setImageResource(R.drawable.registration_button_questionmark);
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardc (Hide Soft KeyboardC) *****//
        ViewId_linearlayout_hidesoftkeyboardc = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardc);

        ViewId_linearlayout_hidesoftkeyboardc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationc();
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***************************//
        //***** OnClickListener *****//
        //***************************//

        //***** OnClickListener - ViewId_edittext5 (Registration Name your fire?) *****//
        ViewId_edittext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11cRegistration_onClick: editText5.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext5 (Registration Name your fire?) *****//
        ViewId_edittext5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11cRegistration_onFocusChange editText5: Got the focus.");

                    ViewId_edittext5 = (EditText) view.findViewById(R.id.editText5);
                    ViewId_edittext5.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationc(ViewId_edittext5);

                } else {
                    Log.d("myApp", "Rinnai11cRegistration_onFocusChange editText5: Lost the focus.");

                    hideSoftKeyboard_registrationc();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext5 (Registration Name your fire?) *****//
        ViewId_edittext5.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText5.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText5.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText5.");

                //***** Text "Name your fire?" - ViewId_textview99 *****//
                ViewId_textview99 = (TextView) findViewById(R.id.textView99);
                if (s.length() == 0) {
                    ViewId_textview99.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview99.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext6 (Registration Serial Number (Digit 1)) *****//
        ViewId_edittext6.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText6.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText6.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText6.");

                if (!ViewId_edittext6.getText().toString().equals("")) {
                    String[] years = { "J", "K", "L", "M", "N", "P", "R", "S", "T", "W", "X", "Y", "Z" };
                    if (Arrays.asList(years).contains(ViewId_edittext6.getText().toString().toUpperCase())) {
                        ViewId_edittext7.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("First Letter Is Invalid.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext6.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext6.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext7.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext7 (Registration Serial Number (Digit 2)) *****//
        ViewId_edittext7.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText7.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText7.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText7.");

                if (!ViewId_edittext7.getText().toString().equals("")) {
                    String[] months = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M" };
                    if (Arrays.asList(months).contains(ViewId_edittext7.getText().toString().toUpperCase())) {
                        ViewId_edittext8.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("Second Letter Is Invalid.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext7.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext7.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext8.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext8 (Registration Serial Number (Digit 3)) *****//
        ViewId_edittext8.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText8.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText8.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText8.");

                if (!ViewId_edittext8.getText().toString().equals("")) {
                    String[] factory = { "Z" };
                    if (Arrays.asList(factory).contains(ViewId_edittext8.getText().toString().toUpperCase())) {
                        ViewId_edittext9.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("Third Letter Is Invalid.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext8.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext8.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext9.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext9 (Registration Serial Number (Digit 4)) *****//
        ViewId_edittext9.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText9.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText9.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText9.");

                if (!ViewId_edittext9.getText().toString().equals("")) {
                    String[] categoty = { "B" };
                    if (Arrays.asList(categoty).contains(ViewId_edittext9.getText().toString().toUpperCase())) {
                        ViewId_edittext10.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("Forth Letter Is Invalid.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext9.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext9.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext10.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext10 (Registration Serial Number (Digit 5)) *****//
        ViewId_edittext10.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText10.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText10.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText10.");

                if (!ViewId_edittext10.getText().toString().equals("")) {
                    String[] firstModel = { "A", "B" };
                    if (Arrays.asList(firstModel).contains(ViewId_edittext10.getText().toString().toUpperCase())) {
                        ViewId_edittext11.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("Fifth Letter Is Invalid.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext10.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext10.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext11.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext11 (Registration Serial Number (Digit 6)) *****//
        ViewId_edittext11.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText11.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText11.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText11.");

                if (!ViewId_edittext11.getText().toString().equals("")) {
                    Map<String,String> auMap =  new HashMap<String,String>();
                    auMap.put("AB", "RIB2312");
                    auMap.put("AD", "RHFE952");
                    auMap.put("AF", "RHFE1252");
                    auMap.put("AH", "RDV600");
                    auMap.put("AK", "RDV700");
                    auMap.put("AM", "RHFE800SF");
                    auMap.put("AP", "RHFE800DF");
                    auMap.put("AR", "RHFE800S");
                    auMap.put("AT", "RHFE800D");
                    auMap.put("AV", "RHFE1000S");
                    auMap.put("AX", "RHFE1000D");
                    auMap.put("BA", "RHFE1500S");
                    auMap.put("BC", "RHFE1500D");
                    Map<String,String> nzMap =  new HashMap<String,String>();
                    nzMap.put("AA", "RIB2312");
                    nzMap.put("AC", "RHFE952");
                    nzMap.put("AE", "RHFE1252");
                    nzMap.put("AG", "RDV600");
                    nzMap.put("AJ", "RDV700");
                    nzMap.put("AL", "RHFE800SF");
                    nzMap.put("AN", "RHFE800DF");
                    nzMap.put("AQ", "RHFE800S");
                    nzMap.put("AS", "RHFE800D");
                    nzMap.put("AU", "RHFE1000S");
                    nzMap.put("AW", "RHFE1000D");
                    nzMap.put("AZ", "RHFE1500S");
                    nzMap.put("BB", "RHFE1500D");

                    String inputString = ViewId_edittext10.getText().toString().toUpperCase() + ViewId_edittext11.getText().toString().toUpperCase();
                    String modelString = "***";
                    String type = ViewId_textview174.getText().toString();

                    if (locale.equals("AU")) {
                        String getString = (String) auMap.get(inputString);
                        if (getString != null) {
                            modelString = getString;
                        }
                    } else if (locale.equals("NZ")) {
                        String getString = (String) nzMap.get(inputString);
                        if (getString != null) {
                            modelString = getString;
                        }
                    }


                    if (type != "") {
                        if (type.contains(modelString)) {
                            ViewId_edittext12.requestFocus();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                            alertDialog.setTitle("The Code For Model Is Not Matched.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            ViewId_edittext10.setText("");
                                            ViewId_edittext11.setText("");
                                            ViewId_edittext10.requestFocus();
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("Please Select Fire First.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext11.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext11.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext12.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext12 (Registration Serial Number (Digit 7)) *****//
        ViewId_edittext12.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText12.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText12.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText12.");

                if (!ViewId_edittext12.getText().toString().equals("")) {
                    //Integer aaa = Integer.parseInt(ViewId_edittext12.getText().toString());

                    if (ViewId_edittext12.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                        ViewId_edittext13.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("It Is Not A Number.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext12.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext12.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext13.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext13 (Registration Serial Number (Digit 8)) *****//
        ViewId_edittext13.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText13.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText13.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText13.");

                if (!ViewId_edittext13.getText().toString().equals("")) {
                    //Integer aaa = Integer.parseInt(ViewId_edittext12.getText().toString());

                    if (ViewId_edittext13.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                        ViewId_edittext14.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("It Is Not A Number.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext13.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext13.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext14.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext14 (Registration Serial Number (Digit 9)) *****//
        ViewId_edittext14.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText14.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText14.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText14.");

                if (!ViewId_edittext14.getText().toString().equals("")) {
                    //Integer aaa = Integer.parseInt(ViewId_edittext12.getText().toString());

                    if (ViewId_edittext14.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                        ViewId_edittext15.requestFocus();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("It Is Not A Number.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext14.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

//                if (ViewId_edittext14.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    ViewId_edittext15.requestFocus();
//                }
            }
        });

        //***** TextChangedListener - ViewId_edittext15 (Registration Serial Number (Digit 10)) *****//
        ViewId_edittext15.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11cRegistration_afterTextChanged: editText15.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11cRegistration_beforeTextChanged: editText15.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11cRegistration_onTextChanged: editText15.");

                if (!ViewId_edittext15.getText().toString().equals("")) {
                    if (!ViewId_edittext15.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Rinnai11cRegistration.this).create();
                        alertDialog.setTitle("It Is Not A Number.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ViewId_edittext15.setText("");
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

                //if(ViewId_edittext15.getText().toString().length()==1)     //size as per your requirement
                //{
                //    ViewId_edittext15.requestFocus();
                //}
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onRestart.");

        //***** include - ViewId_include_content_registrationc *****//
        ViewId_include_content_registrationc = (ViewGroup) findViewById(R.id.include_content_registrationc);
        ViewId_include_content_registrationc.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_selectfire_registrationc *****//
        ViewId_include_selectfire_registrationc = (ViewGroup) findViewById(R.id.include_selectfire_registrationc);
        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);

        //***** include - ViewId_linearlayout_registration_selectfire *****//
        ViewId_linearlayout_registration_selectfire = (LinearLayout) findViewById(R.id.linearlayout_registration_selectfire);
        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
        ViewId_linearlayout_registration_selectfire.requestFocus();
        hideSoftKeyboard_registrationc();

        //***** include - ViewId_include_scrollview_lockout_registrationc *****//
        ViewId_include_scrollview_lockout_registrationc = (ViewGroup) findViewById(R.id.include_scrollview_lockout_registrationc);
        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);

        //***** Text "Name your fire?" - ViewId_textview99 *****//
        ViewId_textview99 = (TextView) findViewById(R.id.textView99);
        ViewId_textview99.setVisibility(View.VISIBLE);

        //***** Registration Name your fire? - ViewId_edittext5 *****//
        ViewId_edittext5 = (EditText) findViewById(R.id.editText5);
        ViewId_edittext5.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext5.setText("");

        //***** Registration Serial Number (Digit 1) - ViewId_edittext6 *****//
        ViewId_edittext6 = (EditText) findViewById(R.id.editText6);
        ViewId_edittext6.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext6.setText("");

        //***** Registration Serial Number (Digit 2) - ViewId_edittext7 *****//
        ViewId_edittext7 = (EditText) findViewById(R.id.editText7);
        ViewId_edittext7.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext7.setText("");

        //***** Registration Serial Number (Digit 3) - ViewId_edittext8 *****//
        ViewId_edittext8 = (EditText) findViewById(R.id.editText8);
        ViewId_edittext8.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext8.setText("");

        //***** Registration Serial Number (Digit 4) - ViewId_edittext9 *****//
        ViewId_edittext9 = (EditText) findViewById(R.id.editText9);
        ViewId_edittext9.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext9.setText("");

        //***** Registration Serial Number (Digit 5) - ViewId_edittext10 *****//
        ViewId_edittext10 = (EditText) findViewById(R.id.editText10);
        ViewId_edittext10.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext10.setText("");

        //***** Registration Serial Number (Digit 6) - ViewId_edittext11 *****//
        ViewId_edittext11 = (EditText) findViewById(R.id.editText11);
        ViewId_edittext11.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext11.setText("");

        //***** Registration Serial Number (Digit 7) - ViewId_edittext12 *****//
        ViewId_edittext12 = (EditText) findViewById(R.id.editText12);
        ViewId_edittext12.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext12.setText("");

        //***** Registration Serial Number (Digit 8) - ViewId_edittext13 *****//
        ViewId_edittext13 = (EditText) findViewById(R.id.editText13);
        ViewId_edittext13.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext13.setText("");

        //***** Registration Serial Number (Digit 9) - ViewId_edittext14 *****//
        ViewId_edittext14 = (EditText) findViewById(R.id.editText14);
        ViewId_edittext14.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext14.setText("");

        //***** Registration Serial Number (Digit 10) - ViewId_edittext15 *****//
        ViewId_edittext15 = (EditText) findViewById(R.id.editText15);
        ViewId_edittext15.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext15.setText("");

        //***** LinearLayout Select Fire / Fire Model  *****//
        ViewId_textview174 = (TextView) findViewById(R.id.textView174);
        ViewId_textview174.setText("");

        ViewId_linearlayout_textview_selectfire = (LinearLayout) findViewById(R.id.linearlayout_textview_selectfire);
        ViewId_linearlayout_textview_selectfire.setVisibility(View.VISIBLE);
        ViewId_linearlayout_textview_firemodel = (LinearLayout) findViewById(R.id.linearlayout_textview_firemodel);
        ViewId_linearlayout_textview_firemodel.setVisibility(View.INVISIBLE);

        //***** include - ViewId_include_showhints_serialnumber *****//
        ViewId_include_showhints_serialnumber.setVisibility(View.INVISIBLE);
        ViewId_include_showhints_lockout_registrationc.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11cRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11cRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11cRegistration *****//
    //************************************//

    public void setRinnai11cRegistration() {

        //***** include - ViewId_include_content_registrationc *****//
        ViewId_include_content_registrationc = (ViewGroup) findViewById(R.id.include_content_registrationc);
        ViewId_include_content_registrationc.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_selectfire_registrationc *****//
        ViewId_include_selectfire_registrationc = (ViewGroup) findViewById(R.id.include_selectfire_registrationc);
        ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);

        //***** include - ViewId_linearlayout_registration_selectfire *****//
        ViewId_linearlayout_registration_selectfire = (LinearLayout) findViewById(R.id.linearlayout_registration_selectfire);
        ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
        ViewId_linearlayout_registration_selectfire.requestFocus();
        hideSoftKeyboard_registrationc();

        //***** include - ViewId_include_scrollview_lockout_registrationc *****//
        ViewId_include_scrollview_lockout_registrationc = (ViewGroup) findViewById(R.id.include_scrollview_lockout_registrationc);
        ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);

        //***** Text "Name your fire?" - ViewId_textview99 *****//
        ViewId_textview99 = (TextView) findViewById(R.id.textView99);
        ViewId_textview99.setVisibility(View.VISIBLE);

        //***** Registration Name your fire? - ViewId_edittext5 *****//
        ViewId_edittext5 = (EditText) findViewById(R.id.editText5);
        ViewId_edittext5.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 1) - ViewId_edittext6 *****//
        ViewId_edittext6 = (EditText) findViewById(R.id.editText6);
        ViewId_edittext6.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 2) - ViewId_edittext7 *****//
        ViewId_edittext7 = (EditText) findViewById(R.id.editText7);
        ViewId_edittext7.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 3) - ViewId_edittext8 *****//
        ViewId_edittext8 = (EditText) findViewById(R.id.editText8);
        ViewId_edittext8.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 4) - ViewId_edittext9 *****//
        ViewId_edittext9 = (EditText) findViewById(R.id.editText9);
        ViewId_edittext9.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 5) - ViewId_edittext10 *****//
        ViewId_edittext10 = (EditText) findViewById(R.id.editText10);
        ViewId_edittext10.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 6) - ViewId_edittext11 *****//
        ViewId_edittext11 = (EditText) findViewById(R.id.editText11);
        ViewId_edittext11.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 7) - ViewId_edittext12 *****//
        ViewId_edittext12 = (EditText) findViewById(R.id.editText12);
        ViewId_edittext12.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 8) - ViewId_edittext13 *****//
        ViewId_edittext13 = (EditText) findViewById(R.id.editText13);
        ViewId_edittext13.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 9) - ViewId_edittext14 *****//
        ViewId_edittext14 = (EditText) findViewById(R.id.editText14);
        ViewId_edittext14.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Serial Number (Digit 10) - ViewId_edittext15 *****//
        ViewId_edittext15 = (EditText) findViewById(R.id.editText15);
        ViewId_edittext15.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

    }

    //*******************************************************//
    //***** scrollviewrowrinnai37networkOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowrinnai11cregistrationOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            scrollviewrow_pressed = true;

            //Get Selected Text
            selected_scrollviewrowrinnai11cregistration = ((TextView) v.findViewById(R.id.textView173));

            //debug
            Log.d("myApp", "Rinnai11cRegistration_scrollviewrowrinnai11cregistrationOnClickListener():" + selected_scrollviewrowrinnai11cregistration.getText() + "");

            //Highlight Selection
            ViewId_linearlayout_firemodel_row = ((LinearLayout) v.findViewById(R.id.linearlayout_firemodel_row));

            ViewId_textview95.setTextColor(Color.parseColor("#FFFFFFFF"));
            ViewId_include_selectfire_registrationc.setVisibility(View.INVISIBLE);
            ViewId_linearlayout_registration_selectfire.setVisibility(View.VISIBLE);
            ViewId_linearlayout_registration_selectfire.requestFocus();
            hideSoftKeyboard_registrationc();
            ViewId_include_scrollview_lockout_registrationc.setVisibility(View.INVISIBLE);

            ViewId_textview174 = (TextView) findViewById(R.id.textView174);
            ViewId_textview174.setText(selected_scrollviewrowrinnai11cregistration.getText().toString());
            ViewId_linearlayout_textview_selectfire = (LinearLayout) findViewById(R.id.linearlayout_textview_selectfire);
            ViewId_linearlayout_textview_selectfire.setVisibility(View.INVISIBLE);
            ViewId_linearlayout_textview_firemodel = (LinearLayout) findViewById(R.id.linearlayout_textview_firemodel);
            ViewId_linearlayout_textview_firemodel.setVisibility(View.VISIBLE);

        }
    };

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationc() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationc(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Visit Rinnai - Phone
    public void goToActivity_Rinnai11c_Registration_Help(View view) {
        String locale = this.getResources().getConfiguration().locale.getCountry();
        Log.d("myApp", "Locale: " + locale);

        try {
            if (locale.equals("AU")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1300555545"));
                startActivity(intent);
            } else if (locale.equals("NZ")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0800746624"));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Rinnai Phone not supported in your region.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Rinnai Phone not supported on your device.",
                    Toast.LENGTH_LONG).show();
        }
    }

    //***************************************//
    //***** setAWSApplianceRegistration *****//
    //***************************************//

    public void setAWSApplianceRegistration() {

        Tx_RN171DeviceSetDeviceName();

        /////////////////////////////////////////
        //Register Appliance
        /////////////////////////////////////////

        //Call method and post appropriate values
        AWSconnection.insertCustomerApplianceURL(AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceSerial,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceType,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceModel,
                AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).UUID,
                AppGlobals.userregInfo.userregistrationEmail,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceName,

                //Call interface to retrieve Async results
                new AWSconnection.textResult() {
                    @Override

                    public void getResult(String result) {

                        //Do stuff with results here
                        //Returns either success or fail message
                        //Log.i("Registration:", result);
                        Log.d("myApp_AWS", "Registration:" + result);

                        if (!result.equals("\"Appliance Registration Successful\"")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11cRegistration.this, "Web Services Error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(Rinnai11cRegistration.this, "Update Successful.",
                                                Toast.LENGTH_LONG).show();

                                        AppGlobals.userregClaimAppliance = false;

                                        isClosing = true;
                                        intent = new Intent(Rinnai11cRegistration.this, Rinnai11iRegistration.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp", "Rinnai11cRegistration: startActivity(Rinnai11iRegistration).");
                                    } catch (Exception e) {
                                        Log.d("myApp", "Rinnai11cRegistration: AWSconnection.textResult(Exception - " + e + ")");
                                    }
                                }
                            });
                        }
                    }
                });
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

                    try {

                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP");
    }

    //***** RN171_DEVICE_SET_DEVICENAME *****//
    public void Tx_RN171DeviceSetDeviceName() {

        String rn171SetOptReplaceDeviceName = AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceName;

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).WiFiHardware == 0) {
            rn171SetOptReplaceDeviceName = rn171SetOptReplaceDeviceName.replace(' ', (char) 0x1B);
        }

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_38," + rn171SetOptReplaceDeviceName + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: Tx_RN171DeviceSetDeviceName(Exception - " + e + ")");
        }
    }

}
