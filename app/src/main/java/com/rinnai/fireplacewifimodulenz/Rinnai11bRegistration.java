package com.rinnai.fireplacewifimodulenz;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import AWSmodule.AWSconnection;

/**
 * Created by JConci on 18/01/2018.
 */

public class Rinnai11bRegistration extends MillecActivityBase {

    Button ViewId_button18;
    Button ViewId_button19;
    Button ViewId_button20;

    TextView ViewId_textview86;
    TextView ViewId_textview87;
    TextView ViewId_textview88;
    TextView ViewId_textview89;
    TextView ViewId_textview90;
    TextView ViewId_textview91;

    EditText ViewId_edittext3;
    EditText ViewId_edittext4;

    LinearLayout ViewId_linearlayout_registration_email;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardb;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11b_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11bRegistration_onCreate.");

        //Permit external connection attempts
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setRinnai11bRegistration();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button18 (Login) *****//
        ViewId_button18 = (Button) findViewById(R.id.button18);
        ViewId_textview86 = (TextView) findViewById(R.id.textView86);
        ViewId_textview89 = (TextView) findViewById(R.id.textView89);
        ViewId_edittext3 = (EditText) findViewById(R.id.editText3);
        ViewId_edittext4 = (EditText) findViewById(R.id.editText4);

        String locale = this.getResources().getConfiguration().locale.getCountry();
        if (locale.equals("AU")) {
            ViewId_textview89.setText("HELP: 1300555545");
        } else if (locale.equals("NZ")) {
            ViewId_textview89.setText("HELP: 0800 RINNAI");
        }

        ViewId_button18.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button18.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview86.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button18.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview86.setTextColor(Color.parseColor("#FFFFFFFF"));

                        if (!ViewId_edittext3.getText().toString().equals("") &&
                                    !ViewId_edittext4.getText().toString().equals("")) {

                                /////////////////////////////////////////////////
                                //Select Customer
                                /////////////////////////////////////////////////
                                AWSconnection.selectCustomerURL(ViewId_edittext3.getText().toString(), ViewId_edittext4.getText().toString(), new AWSconnection.arrayResult() {
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

                                                        //Split "postcode 2"
                                                        String[] ui_postcodesplitc = ui_resultList.get(0).split("postcode\":\"");
                                                        String[] ui_postcodesplitd = ui_postcodesplitc[1].split("\"");
                                                        Log.d("myApp_AWS", "postcode 2(split): " + ui_postcodesplitd[0]);

//                                                    //Split "postcode"
//                                                    String[] ui_postcodesplita = ui_resultList.get(0).split("postcode");
//                                                    String[] ui_postcodesplitb = ui_postcodesplita[1].split("\"");
//                                                    String[] ui_postcodesplitc = ui_postcodesplitb[1].split(":");
//                                                    String[] ui_postcodesplitd = ui_postcodesplitc[1].split(",");
//                                                    Log.d("myApp_AWS", "Postcode(split): " + ui_postcodesplitd[0]);

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

                                                        AppGlobals.rfwmInitialSetupFlag = true;
                                                        AppGlobals.rfwmUserFlag = 1;
                                                        AppGlobals.UDPSrv = new UDPServer(3500);
                                                        AppGlobals.fireplaceWifi.clear();
                                                        AppGlobals.WiFiAccessPointInfo_List.clear();
                                                        AppGlobals.TimersInfo_List.clear();

                                                        AppGlobals.saveRinnaiFireplaceWiFiModuleCredentials(Rinnai11bRegistration.this, AppGlobals.userregInfo.userregistrationEmail, AppGlobals.userregInfo.userregistrationPassword);

                                                        isClosing = true;
                                                        intent = new Intent(Rinnai11bRegistration.this, Rinnai17Login.class);
                                                        startActivity(intent);

                                                        finish();
                                                        Log.d("myApp", "Rinnai11bRegistration: startActivity(Rinnai17Login).");

                                                    } else {
                                                        Toast.makeText(Rinnai11bRegistration.this, "Email, Password or Web Services Error. \nTry entering details again.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("myApp_AWS", "Select Customer: (Exception - " + e + ")");

                                                    Toast.makeText(Rinnai11bRegistration.this, "Web Services Error. \nTry again.",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }
                                });

                            } else {
                                Toast.makeText(Rinnai11bRegistration.this, "1- Enter Email, \n2- Enter Password.",
                                        Toast.LENGTH_LONG).show();
                            }



                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button18.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview86.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button19 (Register) *****//
        ViewId_button19 = (Button) findViewById(R.id.button19);
        ViewId_textview87 = (TextView) findViewById(R.id.textView87);

        ViewId_button19.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button19.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview87.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button19.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview87.setTextColor(Color.parseColor("#FFFFFFFF"));

                        if (AppGlobals.fireplaceWifi.size() >= 1) {

                            isClosing = true;
                            intent = new Intent(Rinnai11bRegistration.this, Rinnai11cRegistration.class);
                            startActivity(intent);

                            //finish();
                            Log.d("myApp", "Rinnai11bRegistration: startActivity(Rinnai11cRegistration).");
                            return true; // if you want to handle the touch event

                        }else{
//                            show popup
                            showPopup();
                        }

                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button19.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview87.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button20 (Password reset) *****//
        ViewId_button20 = (Button) findViewById(R.id.button20);
        ViewId_textview88 = (TextView) findViewById(R.id.textView88);

        ViewId_button20.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button20.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview88.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button20.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview88.setTextColor(Color.parseColor("#FFFFFFFF"));

                        isClosing = true;
                        intent = new Intent(Rinnai11bRegistration.this, Rinnai11fRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11bRegistration: startActivity(Rinnai11fRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button20.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview88.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardb (Hide Soft KeyboardB) *****//
        ViewId_linearlayout_hidesoftkeyboardb = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardb);

        ViewId_linearlayout_hidesoftkeyboardb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationb();
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

        //***** OnClickListener - ViewId_edittext3 (Registration Email) *****//
        ViewId_edittext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11bRegistration_onClick: editText3.");
            }
        });

        //***** OnClickListener - ViewId_edittext4 (Registration Password) *****//
        ViewId_edittext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11bRegistration_onClick: editText4.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext3 (Registration Email) *****//
        ViewId_edittext3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11bRegistration_onFocusChange editText3: Got the focus.");

                    ViewId_edittext3 = (EditText) view.findViewById(R.id.editText3);
                    ViewId_edittext3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                    showSoftKeyboard_registrationb(ViewId_edittext3);

                } else {
                    Log.d("myApp", "Rinnai11bRegistration_onFocusChange editText3: Lost the focus.");

                    hideSoftKeyboard_registrationb();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext4 (Registration Password) *****//
        ViewId_edittext4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11bRegistration_onFocusChange editText4: Got the focus.");

                    ViewId_edittext4 = (EditText) view.findViewById(R.id.editText4);
                    ViewId_edittext4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationb(ViewId_edittext4);

                } else {
                    Log.d("myApp", "Rinnai11bRegistration_onFocusChange editText4: Lost the focus.");

                    hideSoftKeyboard_registrationb();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext3 (Registration Email) *****//
        ViewId_edittext3.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11bRegistration_afterTextChanged: editText3.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11bRegistration_beforeTextChanged: editText3.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11bRegistration_onTextChanged: editText3.");

                //***** Text "Email" - ViewId_textview90 *****//
                ViewId_textview90 = (TextView) findViewById(R.id.textView90);
                if (s.length() == 0) {
                    ViewId_textview90.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview90.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext4 (Registration Password) *****//
        ViewId_edittext4.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11bRegistration_afterTextChanged: editText4.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11bRegistration_beforeTextChanged: editText4.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11bRegistration_onTextChanged: editText4.");

                //***** Text "Password" - ViewId_textview91 *****//
                ViewId_textview91 = (TextView) findViewById(R.id.textView91);
                if (s.length() == 0) {
                    ViewId_textview91.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview91.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void showPopup(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("No Flame Device Found!")
                .setMessage("Please setup your flame device first.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        intent = new Intent(Rinnai11bRegistration.this, Rinnai00aInitialSetupThanks.class);
                        startActivity(intent);

                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog al = builder.create();
        al.requestWindowFeature(Window.FEATURE_NO_TITLE);
        al.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onRestart.");

        ViewId_linearlayout_registration_email = (LinearLayout) findViewById(R.id.linearlayout_registration_email);
        ViewId_linearlayout_registration_email.requestFocus();

        //***** Text "Email" - ViewId_textview90 *****//
        ViewId_textview90 = (TextView) findViewById(R.id.textView90);
        ViewId_textview90.setVisibility(View.VISIBLE);

        //***** Text "Password" - ViewId_textview91 *****//
        ViewId_textview91 = (TextView) findViewById(R.id.textView91);
        ViewId_textview91.setVisibility(View.VISIBLE);

        //***** Registration Email - ViewId_edittext3 *****//
        ViewId_edittext3 = (EditText) findViewById(R.id.editText3);
        ViewId_edittext3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ViewId_edittext3.setText("");

        //***** Registration Password - ViewId_edittext4 *****//
        ViewId_edittext4 = (EditText) findViewById(R.id.editText4);
        ViewId_edittext4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext4.setText("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11aRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11bRegistration *****//
    //************************************//

    public void setRinnai11bRegistration() {

        //***** Text "Email" - ViewId_textview90 *****//
        ViewId_textview90 = (TextView) findViewById(R.id.textView90);
        ViewId_textview90.setVisibility(View.VISIBLE);

        //***** Registration Email - ViewId_edittext3 *****//
        ViewId_edittext3 = (EditText) findViewById(R.id.editText3);
        ViewId_edittext3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //***** Registration Password - ViewId_edittext4 *****//
        ViewId_edittext4 = (EditText) findViewById(R.id.editText4);
        ViewId_edittext4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationb() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationb(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Visit Rinnai - Phone
    public void goToActivity_Rinnai11b_Registration_Help(View view) {
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

}