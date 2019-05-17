package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import static com.rinnai.fireplacewifimodulenz.AppGlobals.userregInfo;

/**
 * Created by JConci on 2/02/2018.
 */

public class Rinnai11dRegistration extends MillecActivityBase {

    Button ViewId_button28;

    TextView ViewId_textview101;
    TextView ViewId_textview102;
    TextView ViewId_textview103;
    TextView ViewId_textview104;
    TextView ViewId_textview105;
    TextView ViewId_textview106;
    TextView ViewId_textview107;
    TextView ViewId_textview177;
    TextView ViewId_textview178;
    TextView ViewId_textview179;

    EditText ViewId_edittext16;
    EditText ViewId_edittext17;
    EditText ViewId_edittext18;
    EditText ViewId_edittext19;
    EditText ViewId_edittext20;
    EditText ViewId_edittext21;
    EditText ViewId_edittext31;
    EditText ViewId_edittext32;
    EditText ViewId_edittext33;

    LinearLayout ViewId_linearlayout_registration_email;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardd;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11d_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onCreate.");

        setRinnai11dRegistration();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button28 (Done) *****//
        ViewId_button28 = (Button) findViewById(R.id.button28);
        ViewId_textview107 = (TextView) findViewById(R.id.textView107);
        ViewId_edittext16 = (EditText) findViewById(R.id.editText16);
        ViewId_edittext17 = (EditText) findViewById(R.id.editText17);
        ViewId_edittext18 = (EditText) findViewById(R.id.editText18);
        ViewId_edittext19 = (EditText) findViewById(R.id.editText19);
        ViewId_edittext20 = (EditText) findViewById(R.id.editText20);
        ViewId_edittext21 = (EditText) findViewById(R.id.editText21);
        ViewId_edittext31 = (EditText) findViewById(R.id.editText31);
        ViewId_edittext32 = (EditText) findViewById(R.id.editText32);
        ViewId_edittext33 = (EditText) findViewById(R.id.editText33);

        ViewId_button28.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button28.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview107.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button28.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview107.setTextColor(Color.parseColor("#FFFFFFFF"));
                        hideSoftKeyboard_registrationd();

                        if (!ViewId_edittext16.getText().toString().equals("") &&
                                !ViewId_edittext17.getText().toString().equals("") &&
                                !ViewId_edittext18.getText().toString().equals("") &&
                                !ViewId_edittext19.getText().toString().equals("") &&
                                !ViewId_edittext20.getText().toString().equals("") &&
                                !ViewId_edittext21.getText().toString().equals("") &&
                                !ViewId_edittext31.getText().toString().equals("") &&
                                !ViewId_edittext32.getText().toString().equals("") &&
                                !ViewId_edittext33.getText().toString().equals("")) {

                            if (isValidEmail(ViewId_edittext31.getText().toString())) {
                                if(ViewId_edittext20.getText().toString().equals(ViewId_edittext21.getText().toString())){

                                    AppGlobals.userregInfo.userregistrationStreetAddress = ViewId_edittext16.getText().toString();
                                    AppGlobals.userregInfo.userregistrationSuburb = ViewId_edittext17.getText().toString();
                                    AppGlobals.userregInfo.userregistrationCityRegion = ViewId_edittext18.getText().toString();
                                    AppGlobals.userregInfo.userregistrationPostcode = ViewId_edittext19.getText().toString();
                                    AppGlobals.userregInfo.userregistrationPassword = ViewId_edittext20.getText().toString();
                                    AppGlobals.userregInfo.userregistrationConfirmPassword = ViewId_edittext21.getText().toString();
                                    AppGlobals.userregInfo.userregistrationEmail = ViewId_edittext31.getText().toString();
                                    AppGlobals.userregInfo.userregistrationFirstName = ViewId_edittext32.getText().toString();
                                    AppGlobals.userregInfo.userregistrationLastName = ViewId_edittext33.getText().toString();
                                    AppGlobals.userregInfo.userregistrationCountry = getResources().getConfiguration().locale.getCountry();

                                    Log.d("myApp", "Rinnai11dRegistration: (Street Address: " + AppGlobals.userregInfo.userregistrationStreetAddress + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Suburb: " + AppGlobals.userregInfo.userregistrationSuburb + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (City / Region: " + AppGlobals.userregInfo.userregistrationCityRegion + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Postcode: " + AppGlobals.userregInfo.userregistrationPostcode + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Password: " + AppGlobals.userregInfo.userregistrationPassword + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Confirm Password: " + AppGlobals.userregInfo.userregistrationConfirmPassword + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Email: " + AppGlobals.userregInfo.userregistrationEmail + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (First Name: " + AppGlobals.userregInfo.userregistrationFirstName + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Last Name: " + AppGlobals.userregInfo.userregistrationLastName + ")");
                                    Log.d("myApp", "Rinnai11dRegistration: (Country: " + AppGlobals.userregInfo.userregistrationCountry + ")");

                                    isClosing = true;
                                    intent = new Intent(Rinnai11dRegistration.this, Rinnai11eRegistration.class);
                                    startActivity(intent);

                                    finish();
                                    Log.d("myApp", "Rinnai11dRegistration: startActivity(Rinnai11eRegistration).");

                                } else {
                                    Toast.makeText(Rinnai11dRegistration.this, "Password and Confirm Password Error. \nTry entering details again.",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Rinnai11dRegistration.this, "Email Format is NOT correct.",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Rinnai11dRegistration.this, "Enter Your Details.",
                                    Toast.LENGTH_LONG).show();
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button28.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview107.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardd (Hide Soft KeyboardD) *****//
        ViewId_linearlayout_hidesoftkeyboardd = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardd);

        ViewId_linearlayout_hidesoftkeyboardd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationd();
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

        //***** OnClickListener - ViewId_edittext16 (Registration Street Address) *****//
        ViewId_edittext16 = (EditText) findViewById(R.id.editText16);

        ViewId_edittext16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText16.");
            }
        });

        //***** OnClickListener - ViewId_edittext17 (Registration Suburb) *****//
        ViewId_edittext17 = (EditText) findViewById(R.id.editText17);

        ViewId_edittext17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText17.");
            }
        });

        //***** OnClickListener - ViewId_edittext18 (Registration City / Region) *****//
        ViewId_edittext18 = (EditText) findViewById(R.id.editText18);

        ViewId_edittext18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText18.");
            }
        });

        //***** OnClickListener - ViewId_edittext19 (Registration Postcode) *****//
        ViewId_edittext19 = (EditText) findViewById(R.id.editText19);

        ViewId_edittext19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText19.");
            }
        });

        //***** OnClickListener - ViewId_edittext20 (Registration Password) *****//
        ViewId_edittext20 = (EditText) findViewById(R.id.editText20);

        ViewId_edittext20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText20.");
            }
        });

        //***** OnClickListener - ViewId_edittext21 (Registration Confirm Password) *****//
        ViewId_edittext21 = (EditText) findViewById(R.id.editText21);

        ViewId_edittext21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText21.");
            }
        });

        //***** OnClickListener - ViewId_edittext31 (Registration Email) *****//
        ViewId_edittext31 = (EditText) findViewById(R.id.editText31);

        ViewId_edittext31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText31.");
            }
        });

        //***** OnClickListener - ViewId_edittext32 (Registration First Name) *****//
        ViewId_edittext32 = (EditText) findViewById(R.id.editText32);

        ViewId_edittext32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText32.");
            }
        });

        //***** OnClickListener - ViewId_edittext33 (Registration Last Name) *****//
        ViewId_edittext33 = (EditText) findViewById(R.id.editText33);

        ViewId_edittext33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11dRegistration_onClick: editText33.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext16 (Registration Street Address) *****//
        ViewId_edittext16.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText16: Got the focus.");

                    ViewId_edittext16 = (EditText) view.findViewById(R.id.editText16);
                    ViewId_edittext16.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext16);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText16: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext17 (Registration Suburb) *****//
        ViewId_edittext17.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText17: Got the focus.");

                    ViewId_edittext17 = (EditText) view.findViewById(R.id.editText17);
                    ViewId_edittext17.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext17);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText17: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext18 (Registration City / Region) *****//
        ViewId_edittext18.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText18: Got the focus.");

                    ViewId_edittext18 = (EditText) view.findViewById(R.id.editText18);
                    ViewId_edittext18.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext18);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText18: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext19 (Registration Postcode) *****//
        ViewId_edittext19.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText19: Got the focus.");

                    ViewId_edittext19 = (EditText) view.findViewById(R.id.editText19);
                    ViewId_edittext19.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

                    showSoftKeyboard_registrationd(ViewId_edittext19);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText19: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext20 (Registration Password) *****//
        ViewId_edittext20.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText20: Got the focus.");

                    ViewId_edittext20 = (EditText) view.findViewById(R.id.editText20);
                    ViewId_edittext20.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext20);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText20: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext21 (Registration Confirm Password) *****//
        ViewId_edittext21.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText21: Got the focus.");

                    ViewId_edittext21 = (EditText) view.findViewById(R.id.editText21);
                    ViewId_edittext21.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext21);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText21: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext31 (Registration Email) *****//
        ViewId_edittext31.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText31: Got the focus.");

                    ViewId_edittext31 = (EditText) view.findViewById(R.id.editText31);
                    ViewId_edittext31.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                    showSoftKeyboard_registrationd(ViewId_edittext31);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText31: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext32 (Registration First Name) *****//
        ViewId_edittext32.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText32: Got the focus.");

                    ViewId_edittext32 = (EditText) view.findViewById(R.id.editText32);
                    ViewId_edittext32.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext32);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText32: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext33 (Registration Last Name) *****//
        ViewId_edittext33.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText33: Got the focus.");

                    ViewId_edittext33 = (EditText) view.findViewById(R.id.editText33);
                    ViewId_edittext33.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    showSoftKeyboard_registrationd(ViewId_edittext33);

                } else {
                    Log.d("myApp", "Rinnai11dRegistration_onFocusChange editText33: Lost the focus.");

                    hideSoftKeyboard_registrationd();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext16 (Registration Street Address) *****//
        ViewId_edittext16.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText16.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText16.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText16.");

                //***** Text "Street Address" - ViewId_textview101 *****//
                ViewId_textview101 = (TextView) findViewById(R.id.textView101);
                if (s.length() == 0) {
                    ViewId_textview101.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview101.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext17 (Registration Suburb) *****//
        ViewId_edittext17.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText17.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText17.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText17.");

                //***** Text "Suburb" - ViewId_textview102 *****//
                ViewId_textview102 = (TextView) findViewById(R.id.textView102);
                if (s.length() == 0) {
                    ViewId_textview102.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview102.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext18 (Registration City / Region) *****//
        ViewId_edittext18.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText18.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText18.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText18.");

                //***** Text "City / Region" - ViewId_textview103 *****//
                ViewId_textview103 = (TextView) findViewById(R.id.textView103);
                if (s.length() == 0) {
                    ViewId_textview103.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview103.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext19 (Registration Postcode) *****//
        ViewId_edittext19.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText19.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText19.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText19.");

                //***** Text "Postcode" - ViewId_textview104 *****//
                ViewId_textview104 = (TextView) findViewById(R.id.textView104);
                if (s.length() == 0) {
                    ViewId_textview104.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview104.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext20 (Registration Password) *****//
        ViewId_edittext20.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText20.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText20.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText20.");

                //***** Text "Password" - ViewId_textview105 *****//
                ViewId_textview105 = (TextView) findViewById(R.id.textView105);
                if (s.length() == 0) {
                    ViewId_textview105.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview105.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext21 (Registration Confirm Password) *****//
        ViewId_edittext21.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText21.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText21.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText21.");

                //***** Text "Confirm Password" - ViewId_textview106 *****//
                ViewId_textview106 = (TextView) findViewById(R.id.textView106);
                if (s.length() == 0) {
                    ViewId_textview106.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview106.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext31 (Registration Email) *****//
        ViewId_edittext31.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText31.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText31.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText31.");

                //***** Text "Email" - ViewId_textview177 *****//
                ViewId_textview177 = (TextView) findViewById(R.id.textView177);
                if (s.length() == 0) {
                    ViewId_textview177.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview177.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext32 (Registration First Name) *****//
        ViewId_edittext32.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText32.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText32.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText32.");

                //***** Text "First Name" - ViewId_textview178 *****//
                ViewId_textview178 = (TextView) findViewById(R.id.textView178);
                if (s.length() == 0) {
                    ViewId_textview178.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview178.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext33 (Registration Last Name) *****//
        ViewId_edittext33.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11dRegistration_afterTextChanged: editText33.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11dRegistration_beforeTextChanged: editText33.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11dRegistration_onTextChanged: editText33.");

                //***** Text "Last Name" - ViewId_textview33 *****//
                ViewId_textview179 = (TextView) findViewById(R.id.textView179);
                if (s.length() == 0) {
                    ViewId_textview179.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview179.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onRestart.");

        //***** include - ViewId_linearlayout_registration_email *****//
        ViewId_linearlayout_registration_email = (LinearLayout) findViewById(R.id.linearlayout_registration_email);
        ViewId_linearlayout_registration_email.requestFocus();
        hideSoftKeyboard_registrationd();

        //***** Registration Street Address - ViewId_edittext16 *****//
        ViewId_edittext16 = (EditText) findViewById(R.id.editText16);
        ViewId_edittext16.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext16.setText("");

        //***** Registration Suburb - ViewId_edittext17 *****//
        ViewId_edittext17 = (EditText) findViewById(R.id.editText17);
        ViewId_edittext17.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext17.setText("");

        //***** Registration City / Region - ViewId_edittext18 *****//
        ViewId_edittext18 = (EditText) findViewById(R.id.editText18);
        ViewId_edittext18.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext18.setText("");

        //***** Registration Postcode - ViewId_edittext19 *****//
        ViewId_edittext19 = (EditText) findViewById(R.id.editText19);
        ViewId_edittext19.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        ViewId_edittext19.setText("");

        //***** Registration Password - ViewId_edittext20 *****//
        ViewId_edittext20 = (EditText) findViewById(R.id.editText20);
        ViewId_edittext20.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext20.setText("");

        //***** Registration Confirm Password - ViewId_edittext21 *****//
        ViewId_edittext21 = (EditText) findViewById(R.id.editText21);
        ViewId_edittext21.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext21.setText("");

        //***** Registration Email - ViewId_edittext31 *****//
        ViewId_edittext31 = (EditText) findViewById(R.id.editText31);
        ViewId_edittext31.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ViewId_edittext31.setText("");

        //***** Registration First Name - ViewId_edittext32 *****//
        ViewId_edittext32 = (EditText) findViewById(R.id.editText32);
        ViewId_edittext32.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext32.setText("");

        //***** Registration Last Name - ViewId_edittext33 *****//
        ViewId_edittext33 = (EditText) findViewById(R.id.editText33);
        ViewId_edittext33.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        ViewId_edittext33.setText("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11dRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11dRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11dRegistration *****//
    //************************************//

    public void setRinnai11dRegistration() {

        //***** include - ViewId_linearlayout_registration_email *****//
        ViewId_linearlayout_registration_email = (LinearLayout) findViewById(R.id.linearlayout_registration_email);
        ViewId_linearlayout_registration_email.requestFocus();
        hideSoftKeyboard_registrationd();

        //***** Registration Street Address - ViewId_edittext16 *****//
        ViewId_edittext16 = (EditText) findViewById(R.id.editText16);
        ViewId_edittext16.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Suburb - ViewId_edittext17 *****//
        ViewId_edittext17 = (EditText) findViewById(R.id.editText17);
        ViewId_edittext17.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration City / Region - ViewId_edittext18 *****//
        ViewId_edittext18 = (EditText) findViewById(R.id.editText18);
        ViewId_edittext18.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Postcode - ViewId_edittext19 *****//
        ViewId_edittext19 = (EditText) findViewById(R.id.editText19);
        ViewId_edittext19.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        //***** Registration Password - ViewId_edittext20 *****//
        ViewId_edittext20 = (EditText) findViewById(R.id.editText20);
        ViewId_edittext20.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //***** Registration Confirm Password - ViewId_edittext21 *****//
        ViewId_edittext21 = (EditText) findViewById(R.id.editText21);
        ViewId_edittext21.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //***** Registration Email - ViewId_edittext31 *****//
        ViewId_edittext31 = (EditText) findViewById(R.id.editText31);
        ViewId_edittext31.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //***** Registration First Name - ViewId_edittext32 *****//
        ViewId_edittext32 = (EditText) findViewById(R.id.editText32);
        ViewId_edittext32.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        //***** Registration Last Name - ViewId_edittext33 *****//
        ViewId_edittext33 = (EditText) findViewById(R.id.editText33);
        ViewId_edittext33.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationd() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationd(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
