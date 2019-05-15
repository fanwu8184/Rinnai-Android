package com.rinnai.fireplacewifimodulenz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import AWSmodule.AWSconnection;

/**
 * Created by JConci on 6/02/2018.
 */

public class Rinnai11gRegistration extends MillecActivityBase {

    Button ViewId_button32;

    ImageButton ViewId_imagebutton27;

    TextView ViewId_textview113;
    TextView ViewId_textview114;
    TextView ViewId_textview115;
    TextView ViewId_textview116;

    EditText ViewId_edittext23;
    EditText ViewId_edittext24;

    LinearLayout ViewId_linearlayout_registration_reset_newpassword;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardg;

    String codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11g_registration);
        codeStr = getIntent().getExtras().getString("code");
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onCreate.");

        setRinnai11gRegistration();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imageButton27 (Cross) *****//
        ViewId_imagebutton27 = (ImageButton) findViewById(R.id.imageButton27);
        ViewId_textview116 = (TextView) findViewById(R.id.textView116);

        String locale = this.getResources().getConfiguration().locale.getCountry();
        if (locale.equals("AU")) {
            ViewId_textview116.setText("HELP: 1300555545");
        } else if (locale.equals("NZ")) {
            ViewId_textview116.setText("HELP: 0800 RINNAI");
        }

        ViewId_imagebutton27.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton27.setImageResource(R.drawable.registration_button_cross_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton27.setImageResource(R.drawable.registration_button_cross);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton27.setImageResource(R.drawable.registration_button_cross);
                }
                return false;
            }
        });

        //***** OnTouchListener - button32 (Password reset) *****//
        ViewId_button32 = (Button) findViewById(R.id.button32);
        ViewId_textview115 = (TextView) findViewById(R.id.textView115);

        ViewId_button32.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button32.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview115.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button32.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview115.setTextColor(Color.parseColor("#FFFFFFFF"));

//                        validate and send


                        AWSconnection.resetPasswordVerifyTokenURL("glenaries03@gmail.com", codeStr, "qwerty", new AWSconnection.textResult() {
                            @Override
                            public void getResult(final String textResult) {

                                if (textResult.equals("\"Password Successfully Changed\"")) {

                                    Intent intent = new Intent(Rinnai11gRegistration.this, ResetPasswordCompleteActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Rinnai11gRegistration.this, textResult,
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });


                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button32.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview115.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardg (Hide Soft KeyboardG) *****//
        ViewId_linearlayout_hidesoftkeyboardg = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardg);

        ViewId_linearlayout_hidesoftkeyboardg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationg();
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

        //***** OnClickListener - ViewId_edittext23 (Registration Reset New Password) *****//
        ViewId_edittext23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11gRegistration_onClick: editText23.");
            }
        });

        //***** OnClickListener - ViewId_edittext24 (Registration Reset Confirm Password) *****//
        ViewId_edittext24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11gRegistration_onClick: editText24.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext23 (Registration Reset New Password) *****//
        ViewId_edittext23.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11gRegistration_onFocusChange editText23: Got the focus.");

                    ViewId_edittext23 = (EditText) view.findViewById(R.id.editText23);
                    ViewId_edittext23.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationg(ViewId_edittext23);

                } else {
                    Log.d("myApp", "Rinnai11gRegistration_onFocusChange editText23: Lost the focus.");

                    hideSoftKeyboard_registrationg();
                }
            }
        });

        //***** OnFocusChangeListener - ViewId_edittext24 (Registration Reset Confirm Password) *****//
        ViewId_edittext24.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11gRegistration_onFocusChange editText24: Got the focus.");

                    ViewId_edittext24 = (EditText) view.findViewById(R.id.editText24);
                    ViewId_edittext24.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_registrationg(ViewId_edittext24);

                } else {
                    Log.d("myApp", "Rinnai11gRegistration_onFocusChange editText24: Lost the focus.");

                    hideSoftKeyboard_registrationg();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext23 (Registration Reset New Password) *****//
        ViewId_edittext23.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11gRegistration_afterTextChanged: editText23.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11gRegistration_beforeTextChanged: editText23.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11gRegistration_onTextChanged: editText23.");

                //***** Text "New Password" - ViewId_textview113 *****//
                ViewId_textview113 = (TextView) findViewById(R.id.textView113);
                if(s.length() == 0){
                    ViewId_textview113.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview113.setVisibility(View.INVISIBLE);
                }
            }
        });

        //***** TextChangedListener - ViewId_edittext24 (Registration Reset Confirm Password) *****//
        ViewId_edittext24.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11gRegistration_afterTextChanged: editText24.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11gRegistration_beforeTextChanged: editText24.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11gRegistration_onTextChanged: editText24.");

                //***** Text "Confirm Password" - ViewId_textview114 *****//
                ViewId_textview114 = (TextView) findViewById(R.id.textView114);
                if(s.length() == 0){
                    ViewId_textview114.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview114.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onStart.");
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onRestart.");

        ViewId_linearlayout_registration_reset_newpassword = (LinearLayout) findViewById(R.id.linearlayout_registration_reset_newpassword);
        ViewId_linearlayout_registration_reset_newpassword.requestFocus();

        //***** Text "New Password" - ViewId_textview113 *****//
        ViewId_textview113 = (TextView) findViewById(R.id.textView113);
        ViewId_textview113.setVisibility(View.VISIBLE);

        //***** Text "Confirm Password" - ViewId_textview114 *****//
        ViewId_textview114 = (TextView) findViewById(R.id.textView114);
        ViewId_textview114.setVisibility(View.VISIBLE);

        //***** Registration New Password - ViewId_edittext23 *****//
        ViewId_edittext23 = (EditText) findViewById(R.id.editText23);
        ViewId_edittext23.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext23.setText("");

        //***** Registration Confirm Password - ViewId_edittext24 *****//
        ViewId_edittext24 = (EditText) findViewById(R.id.editText24);
        ViewId_edittext24.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ViewId_edittext24.setText("");

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onResume.");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onPause.");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onStop.");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11gRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11gRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11gRegistration *****//
    //************************************//

    public void setRinnai11gRegistration() {

        //***** Text "New Password" - ViewId_textview113 *****//
        ViewId_textview113 = (TextView) findViewById(R.id.textView113);
        ViewId_textview113.setVisibility(View.VISIBLE);

        //***** Text "Confirm Password" - ViewId_textview114 *****//
        ViewId_textview114 = (TextView) findViewById(R.id.textView114);
        ViewId_textview114.setVisibility(View.VISIBLE);

        //***** Registration New Password - ViewId_edittext23 *****//
        ViewId_edittext23 = (EditText) findViewById(R.id.editText23);
        ViewId_edittext23.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //***** Registration Confirm Password - ViewId_edittext24 *****//
        ViewId_edittext24 = (EditText) findViewById(R.id.editText24);
        ViewId_edittext24.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationg() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationg(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Visit Rinnai - Phone
    public void goToActivity_Rinnai11g_Registration_Help(View view) {
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
        } catch(ActivityNotFoundException e) {
            Toast.makeText(this, "Rinnai Phone not supported on your device.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
