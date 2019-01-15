package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import AWSmodule.AWSconnection;

/**
 * Created by JConci on 7/02/2018.
 */

public class Rinnai11hRegistration extends MillecActivityBase {

    Button ViewId_button34;
    Button ViewId_button35;

    ImageButton ViewId_imagebutton28;

    TextView ViewId_textview118;
    TextView ViewId_textview119;
    TextView ViewId_textview120;
    TextView ViewId_textview121;
    TextView ViewId_textview122;
    TextView ViewId_textview123;
    TextView ViewId_textview124;
    TextView ViewId_textview180;
    TextView ViewId_textview181;
    TextView ViewId_textview182;

    EditText ViewId_edittext25;
    EditText ViewId_edittext26;
    EditText ViewId_edittext27;
    EditText ViewId_edittext28;
    EditText ViewId_edittext29;
    EditText ViewId_edittext34;
    EditText ViewId_edittext35;
    EditText ViewId_edittext36;

    LinearLayout ViewId_linearlayout_registration_reset_email;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardh;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11h_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onCreate.");

        //Permit external connection attempts
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setRinnai11hRegistration();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imageButton28 (Cross) *****//
        ViewId_imagebutton28 = (ImageButton) findViewById(R.id.imageButton28);

        ViewId_imagebutton28.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton28.setImageResource(R.drawable.registration_button_cross_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton28.setImageResource(R.drawable.registration_button_cross);

                        isClosing = true;
                        Intent intent = new Intent(Rinnai11hRegistration.this, Rinnai21HomeScreen.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11hRegistration: startActivity(Rinnai21HomeScreen).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton28.setImageResource(R.drawable.registration_button_cross);
                }
                return false;
            }
        });

        //***** OnTouchListener - button35 (Linked Appliances) *****//
        ViewId_button34 = (Button) findViewById(R.id.button34);
        ViewId_textview123 = (TextView) findViewById(R.id.textView123);

        ViewId_button34.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button34.setBackgroundResource(R.drawable.registration_button_small_transparent_background_pressed);
                        ViewId_textview123.setTextColor(Color.parseColor("#FF800000"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button34.setBackgroundResource(R.drawable.registration_button_small_transparent_background);
                        ViewId_textview123.setTextColor(Color.parseColor("#FFFF0000"));
                        hideSoftKeyboard_registrationh();

                        isClosing = true;
                        intent = new Intent(Rinnai11hRegistration.this, Rinnai11iRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11hRegistration: startActivity(Rinnai11iRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button34.setBackgroundResource(R.drawable.registration_button_small_transparent_background);
                        ViewId_textview123.setTextColor(Color.parseColor("#FFFF0000"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button35 (Update) *****//
        ViewId_button35 = (Button) findViewById(R.id.button35);
        ViewId_textview124 = (TextView) findViewById(R.id.textView124);
        ViewId_edittext25 = (EditText) findViewById(R.id.editText25);
        ViewId_edittext26 = (EditText) findViewById(R.id.editText26);
        ViewId_edittext27 = (EditText) findViewById(R.id.editText27);
        ViewId_edittext28 = (EditText) findViewById(R.id.editText28);
        ViewId_edittext34 = (EditText) findViewById(R.id.editText34);
        ViewId_edittext35 = (EditText) findViewById(R.id.editText35);
        ViewId_edittext36 = (EditText) findViewById(R.id.editText36);

        ViewId_button35.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button35.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview124.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button35.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview124.setTextColor(Color.parseColor("#FFFFFFFF"));
                        hideSoftKeyboard_registrationh();

                        if (!ViewId_edittext25.getText().toString().equals("") &&
                                !ViewId_edittext26.getText().toString().equals("") &&
                                !ViewId_edittext27.getText().toString().equals("") &&
                                !ViewId_edittext28.getText().toString().equals("") &&
                                !ViewId_edittext34.getText().toString().equals("") &&
                                !ViewId_edittext35.getText().toString().equals("") &&
                                !ViewId_edittext36.getText().toString().equals("")) {

                            AppGlobals.userregInfo.userregistrationStreetAddress = ViewId_edittext25.getText().toString();
                            AppGlobals.userregInfo.userregistrationSuburb = ViewId_edittext26.getText().toString();
                            AppGlobals.userregInfo.userregistrationCityRegion = ViewId_edittext27.getText().toString();
                            AppGlobals.userregInfo.userregistrationPostcode = ViewId_edittext28.getText().toString();
                            AppGlobals.userregInfo.userregistrationEmail = ViewId_edittext34.getText().toString();
                            AppGlobals.userregInfo.userregistrationFirstName = ViewId_edittext35.getText().toString();
                            AppGlobals.userregInfo.userregistrationLastName = ViewId_edittext36.getText().toString();
                            AppGlobals.userregInfo.userregistrationCountry = getResources().getConfiguration().locale.getCountry();

                            Log.d("myApp", "Rinnai11hRegistration: (Street Address: " + AppGlobals.userregInfo.userregistrationStreetAddress + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (Suburb: " + AppGlobals.userregInfo.userregistrationSuburb + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (City / Region: " + AppGlobals.userregInfo.userregistrationCityRegion + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (Postcode: " + AppGlobals.userregInfo.userregistrationPostcode + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (Email: " + AppGlobals.userregInfo.userregistrationEmail + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (First Name: " + AppGlobals.userregInfo.userregistrationFirstName + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (Last Name: " + AppGlobals.userregInfo.userregistrationLastName + ")");
                            Log.d("myApp", "Rinnai11hRegistration: (Country: " + AppGlobals.userregInfo.userregistrationCountry + ")");

                            setAWSCurrentInformation_Update();

                        } else {
                            Toast.makeText(Rinnai11hRegistration.this, "Enter Your Details.",
                                    Toast.LENGTH_LONG).show();
                        }
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button35.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview124.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardh (Hide Soft KeyboardH) *****//
        ViewId_linearlayout_hidesoftkeyboardh = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardh);

        ViewId_linearlayout_hidesoftkeyboardh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationh();
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

        //***** OnClickListener - ViewId_edittext25 (Registration Street Address) *****//
        ViewId_edittext25 = (EditText) findViewById(R.id.editText25);

        ViewId_edittext25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText25.");
            }
        });

        //***** OnClickListener - ViewId_edittext26 (Registration Suburb) *****//
        ViewId_edittext26 = (EditText) findViewById(R.id.editText26);

        ViewId_edittext26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText26.");
            }
        });

        //***** OnClickListener - ViewId_edittext27 (Registration City / Region) *****//
        ViewId_edittext27 = (EditText) findViewById(R.id.editText27);

        ViewId_edittext27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText27.");
            }
        });

        //***** OnClickListener - ViewId_edittext28 (Registration Postcode) *****//
        ViewId_edittext28 = (EditText) findViewById(R.id.editText28);

        ViewId_edittext28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText28.");
            }
        });

        //***** OnClickListener - ViewId_edittext29 (Registration Password) *****//
        ViewId_edittext29 = (EditText) findViewById(R.id.editText29);

        ViewId_edittext29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText29.");
            }
        });

        //***** OnClickListener - ViewId_edittext34 (Registration Email) *****//
        ViewId_edittext34 = (EditText) findViewById(R.id.editText34);

        ViewId_edittext34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText34.");
            }
        });

        //***** OnClickListener - ViewId_edittext35 (Registration First Name) *****//
        ViewId_edittext35 = (EditText) findViewById(R.id.editText35);

        ViewId_edittext35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText35.");
            }
        });

        //***** OnClickListener - ViewId_edittext36 (Registration Last Name) *****//
        ViewId_edittext36 = (EditText) findViewById(R.id.editText36);

        ViewId_edittext36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11hRegistration_onClick: editText36.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext25 (Registration Street Address) *****//
        ViewId_edittext25.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText25: Got the focus.");

                    ViewId_edittext25 = (EditText) view.findViewById(R.id.editText25);
                    ViewId_edittext25.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext25);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText25: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext26 (Registration Suburb) *****//
        ViewId_edittext26.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText26: Got the focus.");

                    ViewId_edittext26 = (EditText) view.findViewById(R.id.editText26);
                    ViewId_edittext26.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext26);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText26: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext27 (Registration City / Region) *****//
        ViewId_edittext27.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText27: Got the focus.");

                    ViewId_edittext27 = (EditText) view.findViewById(R.id.editText27);
                    ViewId_edittext27.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext27);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText27: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext28 (Registration Postcode) *****//
        ViewId_edittext28.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText28: Got the focus.");

                    ViewId_edittext28 = (EditText) view.findViewById(R.id.editText28);
                    ViewId_edittext28.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

                    showSoftKeyboard_registrationh(ViewId_edittext28);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText28: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext29 (Registration Password) *****//
        ViewId_edittext29.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText29: Got the focus.");

                    ViewId_edittext29 = (EditText) view.findViewById(R.id.editText29);
                    ViewId_edittext29.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext29);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText29: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext34 (Registration Email) *****//
        ViewId_edittext34.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText34: Got the focus.");

                    ViewId_edittext34 = (EditText) view.findViewById(R.id.editText34);
                    ViewId_edittext34.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                    showSoftKeyboard_registrationh(ViewId_edittext34);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText34: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext35 (Registration First Name) *****//
        ViewId_edittext35.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText35: Got the focus.");

                    ViewId_edittext35 = (EditText) view.findViewById(R.id.editText35);
                    ViewId_edittext35.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext35);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText35: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext36 (Registration Last Name) *****//
        ViewId_edittext36.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText36: Got the focus.");

                    ViewId_edittext36 = (EditText) view.findViewById(R.id.editText36);
                    ViewId_edittext36.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationh(ViewId_edittext36);

                } else {
                    Log.d("myApp", "Rinnai11hRegistration_onFocusChange editText36: Lost the focus.");

                    hideSoftKeyboard_registrationh();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext25 (Registration Street Address) *****//
        ViewId_edittext25.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText25.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText25.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText25.");

                //***** Text "Street Address" - ViewId_textview118 *****//
                ViewId_textview118 = (TextView) findViewById(R.id.textView118);
                if (s.length() == 0) {
                    ViewId_textview118.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview118.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext26 (Registration Suburb) *****//
        ViewId_edittext26.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText26.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText26.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText26.");

                //***** Text "Suburb" - ViewId_textview119 *****//
                ViewId_textview119 = (TextView) findViewById(R.id.textView119);
                if (s.length() == 0) {
                    ViewId_textview119.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview119.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext27 (Registration City / Region) *****//
        ViewId_edittext27.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText27.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText27.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText27.");

                //***** Text "City / Region" - ViewId_textview120 *****//
                ViewId_textview120 = (TextView) findViewById(R.id.textView120);
                if (s.length() == 0) {
                    ViewId_textview120.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview120.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext28 (Registration Postcode) *****//
        ViewId_edittext28.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText28.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText28.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText28.");

                //***** Text "Postcode" - ViewId_textview121 *****//
                ViewId_textview121 = (TextView) findViewById(R.id.textView121);
                if (s.length() == 0) {
                    ViewId_textview121.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview121.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext29 (Registration Password) *****//
        ViewId_edittext29.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText29.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText29.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText29.");

                //***** Text "Password" - ViewId_textview122 *****//
                ViewId_textview122 = (TextView) findViewById(R.id.textView122);
                if (s.length() == 0) {
                    ViewId_textview122.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview122.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext34 (Registration Email) *****//
        ViewId_edittext34.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText34.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText34.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText34.");

                //***** Text "Email" - ViewId_textview180 *****//
                ViewId_textview180 = (TextView) findViewById(R.id.textView180);
                if (s.length() == 0) {
                    ViewId_textview180.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview180.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext35 (Registration First Name) *****//
        ViewId_edittext35.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText35.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText35.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText35.");

                //***** Text "First Name" - ViewId_textview181 *****//
                ViewId_textview181 = (TextView) findViewById(R.id.textView181);
                if (s.length() == 0) {
                    ViewId_textview181.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview181.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext36 (Registration Last Name) *****//
        ViewId_edittext36.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11hRegistration_afterTextChanged: editText36.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11hRegistration_beforeTextChanged: editText36.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11hRegistration_onTextChanged: editText36.");

                //***** Text "Last Name" - ViewId_textview182 *****//
                ViewId_textview182 = (TextView) findViewById(R.id.textView182);
                if (s.length() == 0) {
                    ViewId_textview182.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview182.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onRestart.");

        //***** include - ViewId_linearlayout_registration_reset_email *****//
        ViewId_linearlayout_registration_reset_email = (LinearLayout) findViewById(R.id.linearlayout_registration_reset_email);
        ViewId_linearlayout_registration_reset_email.requestFocus();
        hideSoftKeyboard_registrationh();

        //***** Registration Street Address - ViewId_edittext25 *****//
        ViewId_edittext25 = (EditText) findViewById(R.id.editText25);
        ViewId_edittext25.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext25.setText("");

        //***** Registration Suburb - ViewId_edittext26 *****//
        ViewId_edittext26 = (EditText) findViewById(R.id.editText26);
        ViewId_edittext26.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext26.setText("");

        //***** Registration City / Region - ViewId_edittext27 *****//
        ViewId_edittext27 = (EditText) findViewById(R.id.editText27);
        ViewId_edittext27.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext27.setText("");

        //***** Registration Postcode - ViewId_edittext28 *****//
        ViewId_edittext28 = (EditText) findViewById(R.id.editText28);
        ViewId_edittext28.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        ViewId_edittext28.setText("");

        //***** Registration Password - ViewId_edittext29 *****//
        ViewId_edittext29 = (EditText) findViewById(R.id.editText29);
        ViewId_edittext29.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext29.setText("");

        //***** Registration Email - ViewId_edittext34 *****//
        ViewId_edittext34 = (EditText) findViewById(R.id.editText34);
        ViewId_edittext34.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ViewId_edittext34.setText("");

        //***** Registration First Name - ViewId_edittext35 *****//
        ViewId_edittext35 = (EditText) findViewById(R.id.editText35);
        ViewId_edittext35.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext35.setText("");

        //***** Registration Last Name - ViewId_edittext36 *****//
        ViewId_edittext36 = (EditText) findViewById(R.id.editText36);
        ViewId_edittext36.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext36.setText("");

        getAWSCurrentInformation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11hRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11hRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11hRegistration *****//
    //************************************//

    public void setRinnai11hRegistration() {

        //***** include - ViewId_linearlayout_registration_reset_email *****//
        ViewId_linearlayout_registration_reset_email = (LinearLayout) findViewById(R.id.linearlayout_registration_reset_email);
        ViewId_linearlayout_registration_reset_email.requestFocus();
        hideSoftKeyboard_registrationh();

        //***** Registration Street Address - ViewId_edittext25 *****//
        ViewId_edittext25 = (EditText) findViewById(R.id.editText25);
        ViewId_edittext25.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Suburb - ViewId_edittext26 *****//
        ViewId_edittext26 = (EditText) findViewById(R.id.editText26);
        ViewId_edittext26.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration City / Region - ViewId_edittext27 *****//
        ViewId_edittext27 = (EditText) findViewById(R.id.editText27);
        ViewId_edittext27.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Postcode - ViewId_edittext28 *****//
        ViewId_edittext28 = (EditText) findViewById(R.id.editText28);
        ViewId_edittext28.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        //***** Registration Password - ViewId_edittext29 *****//
        ViewId_edittext29 = (EditText) findViewById(R.id.editText29);
        ViewId_edittext29.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //***** Registration Email - ViewId_edittext34 *****//
        ViewId_edittext34 = (EditText) findViewById(R.id.editText34);
        ViewId_edittext34.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //***** Registration First Name - ViewId_edittext35 *****//
        ViewId_edittext35 = (EditText) findViewById(R.id.editText35);
        ViewId_edittext35.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Last Name - ViewId_edittext36 *****//
        ViewId_edittext36 = (EditText) findViewById(R.id.editText36);
        ViewId_edittext36.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        getAWSCurrentInformation();

    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationh() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationh(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************************//
    //***** getAWSCurrentInformation *****//
    //************************************//

    public void getAWSCurrentInformation() {

        /////////////////////////////////////////////////
        //Select Customer
        /////////////////////////////////////////////////
        AWSconnection.selectCustomerURL(AppGlobals.userregInfo.userregistrationEmail, AppGlobals.userregInfo.userregistrationPassword, new AWSconnection.arrayResult() {
            @Override

            //Get Async callback results
            public void getResult(ArrayList<String> resultList) {

                //Do stuff with results here
                int listSize = resultList.size();
                for (int i = 0; i < listSize; i++) {
                    //Log.i("Appliance: ", resultList.get(i));
                    Log.d("myApp_AWS", "Select Customer: " + resultList.get(i));
                }

                final ArrayList<String> ui_resultList = resultList;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            if (ui_resultList.size() > 0) {

                                //Raw split
                                String[] ui_resultListsplit = ui_resultList.get(0).split("\"");

                                Log.d("myApp_AWS", "First Name: " + ui_resultListsplit[3]);
                                Log.d("myApp_AWS", "Email: " + ui_resultListsplit[7]);
                                Log.d("myApp_AWS", "Suburb: " + ui_resultListsplit[11]);
                                Log.d("myApp_AWS", "Last Name: " + ui_resultListsplit[15]);
                                Log.d("myApp_AWS", "Street Address: " + ui_resultListsplit[19]);
                                Log.d("myApp_AWS", "Postcode: " + ui_resultListsplit[22]);
                                Log.d("myApp_AWS", "Password: " + ui_resultListsplit[25]);
                                Log.d("myApp_AWS", "City / Region: " + ui_resultListsplit[29]);
                                Log.d("myApp_AWS", "Country: " + ui_resultListsplit[33]);

                                //Split "first_name"
                                String[] ui_firstnamesplita = ui_resultList.get(0).split("first_name\":\"");
                                String[] ui_firstnamesplitb = ui_firstnamesplita[1].split("\"");
                                Log.d("myApp_AWS", "First Name(split): " + ui_firstnamesplitb[0]);

                                //Split "email"
                                String[] ui_emailsplita = ui_resultList.get(0).split("email\":\"");
                                String[] ui_emailsplitb = ui_emailsplita[1].split("\"");
                                Log.d("myApp_AWS", "Email(split): " + ui_emailsplitb[0]);

                                //Split "suburb"
                                String[] ui_suburbsplita = ui_resultList.get(0).split("suburb\":\"");
                                String[] ui_suburbsplitb = ui_suburbsplita[1].split("\"");
                                Log.d("myApp_AWS", "Suburb(split): " + ui_suburbsplitb[0]);

                                //Split "last_name"
                                String[] ui_lastnamesplita = ui_resultList.get(0).split("last_name\":\"");
                                String[] ui_lastnamesplitb = ui_lastnamesplita[1].split("\"");
                                Log.d("myApp_AWS", "Last Name(split): " + ui_lastnamesplitb[0]);

                                //Split "street_address"
                                String[] ui_streetaddresssplita = ui_resultList.get(0).split("street_address\":\"");
                                String[] ui_streetaddresssplitb = ui_streetaddresssplita[1].split("\"");
                                Log.d("myApp_AWS", "Street Address(split): " + ui_streetaddresssplitb[0]);

                                //Split "postcode"
                                String[] ui_postcodesplita = ui_resultList.get(0).split("postcode");
                                String[] ui_postcodesplitb = ui_postcodesplita[1].split("\"");
                                String[] ui_postcodesplitc = ui_postcodesplitb[1].split(":");
                                String[] ui_postcodesplitd = ui_postcodesplitc[1].split(",");
                                Log.d("myApp_AWS", "Postcode(split): " + ui_postcodesplitd[0]);

                                //Split "password"
                                String[] ui_passwordsplita = ui_resultList.get(0).split("password\":\"");
                                String[] ui_passwordsplitb = ui_passwordsplita[1].split("\"");
                                Log.d("myApp_AWS", "Password(split): " + ui_passwordsplitb[0]);

                                //Split "city"
                                String[] ui_cityregionsplita = ui_resultList.get(0).split("city\":\"");
                                String[] ui_cityregionsplitb = ui_cityregionsplita[1].split("\"");
                                Log.d("myApp_AWS", "City / Region(split): " + ui_cityregionsplitb[0]);

                                //Split "country"
                                String[] ui_countrysplita = ui_resultList.get(0).split("country\":\"");
                                String[] ui_countrysplitb = ui_countrysplita[1].split("\"");
                                Log.d("myApp_AWS", "Country(split): " + ui_countrysplitb[0]);

                                AppGlobals.userregInfo.userregistrationFirstName = ui_firstnamesplitb[0];
                                AppGlobals.userregInfo.userregistrationEmail = ui_emailsplitb[0];
                                AppGlobals.userregInfo.userregistrationSuburb = ui_suburbsplitb[0];
                                AppGlobals.userregInfo.userregistrationLastName = ui_lastnamesplitb[0];
                                AppGlobals.userregInfo.userregistrationStreetAddress = ui_streetaddresssplitb[0];
                                AppGlobals.userregInfo.userregistrationPostcode = ui_postcodesplitd[0];
                                AppGlobals.userregInfo.userregistrationPassword = ui_passwordsplitb[0];
                                AppGlobals.userregInfo.userregistrationCityRegion = ui_cityregionsplitb[0];
                                AppGlobals.userregInfo.userregistrationCountry = ui_countrysplitb[0];

                                //***** Text (Street Address from AWS) - ViewId_edittext25 *****//
                                ViewId_edittext25 = (EditText) findViewById(R.id.editText25);
                                ViewId_edittext25.setText(AppGlobals.userregInfo.userregistrationStreetAddress);

                                //***** Text (Suburb from AWS) - ViewId_edittext26 *****//
                                ViewId_edittext26 = (EditText) findViewById(R.id.editText26);
                                ViewId_edittext26.setText(AppGlobals.userregInfo.userregistrationSuburb);

                                //***** Text (City / Region from AWS) - ViewId_edittext27 *****//
                                ViewId_edittext27 = (EditText) findViewById(R.id.editText27);
                                ViewId_edittext27.setText(AppGlobals.userregInfo.userregistrationCityRegion);

                                //***** Text (Postcode from AWS) - ViewId_edittext28 *****//
                                ViewId_edittext28 = (EditText) findViewById(R.id.editText28);
                                ViewId_edittext28.setText(AppGlobals.userregInfo.userregistrationPostcode);

                                //***** Text (Password from AWS) - ViewId_edittext29 *****//
                                ViewId_edittext29 = (EditText) findViewById(R.id.editText29);
                                ViewId_edittext29.setText(AppGlobals.userregInfo.userregistrationPassword);

                                //***** Text (Email from AWS) - ViewId_edittext34 *****//
                                ViewId_edittext34 = (EditText) findViewById(R.id.editText34);
                                ViewId_edittext34.setText(AppGlobals.userregInfo.userregistrationEmail);

                                //***** Text (First Name from AWS) - ViewId_edittext35 *****//
                                ViewId_edittext35 = (EditText) findViewById(R.id.editText35);
                                ViewId_edittext35.setText(AppGlobals.userregInfo.userregistrationFirstName);

                                //***** Text (Last Name from AWS) - ViewId_edittext36 *****//
                                ViewId_edittext36 = (EditText) findViewById(R.id.editText36);
                                ViewId_edittext36.setText(AppGlobals.userregInfo.userregistrationLastName);
                            } else {
                                Toast.makeText(Rinnai11hRegistration.this, "Web Services Error.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Log.d("myApp_AWS", "Select Customer: (Exception - " + e + ")");

                            Toast.makeText(Rinnai11hRegistration.this, "Web Services Error. \nTry again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }

    //*******************************************//
    //***** setAWSCurrentInformation_Update *****//
    //*******************************************//

    public void setAWSCurrentInformation_Update() {

        /////////////////////////////////////////////////
        //Update Customer
        /////////////////////////////////////////////////

        //Call method and post appropriate values
        AWSconnection.updateCustomerDetailsURL(AppGlobals.userregInfo.userregistrationEmail,
                AppGlobals.userregInfo.userregistrationStreetAddress,
                AppGlobals.userregInfo.userregistrationSuburb,
                AppGlobals.userregInfo.userregistrationCityRegion,
                AppGlobals.userregInfo.userregistrationFirstName,
                AppGlobals.userregInfo.userregistrationLastName,
                Integer.parseInt(AppGlobals.userregInfo.userregistrationPostcode),
                AppGlobals.userregInfo.userregistrationCountry,

                //Call interface to retrieve Async call results
                new AWSconnection.textResult() {
                    @Override

                    public void getResult(String result) {

                        //Do stuff with results here
                        //Returns either success or error message
                        //Log. i ( "Update Customer:" , result);
                        Log.d("myApp_AWS", "Update Customer:" + result);

                        if (!result.equals("\"Update Successful\"")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11hRegistration.this, "Web Services Error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11hRegistration.this, "Update Successful.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
    }
}
