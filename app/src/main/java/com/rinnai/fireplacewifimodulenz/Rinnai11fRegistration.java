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

/**
 * Created by JConci on 6/02/2018.
 */

public class Rinnai11fRegistration extends MillecActivityBase {

    Button ViewId_button30;

    ImageButton ViewId_imagebutton26;

    TextView ViewId_textview110;
    TextView ViewId_textview111;

    EditText ViewId_edittext22;

    LinearLayout ViewId_linearlayout_registration_reset_email;
    LinearLayout ViewId_linearlayout_hidesoftkeyboardf;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11f_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onCreate.");

        setRinnai11fRegistration();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imageButton26 (Cross) *****//
        ViewId_imagebutton26 = (ImageButton) findViewById(R.id.imageButton26);

        ViewId_imagebutton26.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton26.setImageResource(R.drawable.registration_button_cross_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton26.setImageResource(R.drawable.registration_button_cross);

                        isClosing = true;
                        intent = new Intent(Rinnai11fRegistration.this, Rinnai11bRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11fRegistration: startActivity(Rinnai11bRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton26.setImageResource(R.drawable.registration_button_cross);
                }
                return false;
            }
        });

        //***** OnTouchListener - button30 (Password reset) *****//
        ViewId_button30 = (Button) findViewById(R.id.button30);
        ViewId_textview111 = (TextView) findViewById(R.id.textView111);

        ViewId_button30.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button30.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview111.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button30.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview111.setTextColor(Color.parseColor("#FFFFFFFF"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button30.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview111.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - linearlayout_hidesoftkeyboardf (Hide Soft KeyboardF) *****//
        ViewId_linearlayout_hidesoftkeyboardf = (LinearLayout) findViewById(R.id.linearlayout_hidesoftkeyboardf);

        ViewId_linearlayout_hidesoftkeyboardf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        hideSoftKeyboard_registrationf();
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

        //***** OnClickListener - ViewId_edittext22 (Registration Reset Email) *****//
        ViewId_edittext22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai11fRegistration_onClick: editText22.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext22 (Registration Reset Email) *****//
        ViewId_edittext22.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai11fRegistration_onFocusChange editText22: Got the focus.");

                    ViewId_edittext22 = (EditText) view.findViewById(R.id.editText22);
                    ViewId_edittext22.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                    showSoftKeyboard_registrationf(ViewId_edittext22);

                } else {
                    Log.d("myApp", "Rinnai11fRegistration_onFocusChange editText22: Lost the focus.");

                    hideSoftKeyboard_registrationf();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext22 (Registration Reset Email) *****//
        ViewId_edittext22.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai11fRegistration_afterTextChanged: editText22.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai11fRegistration_beforeTextChanged: editText22.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai11fRegistration_onTextChanged: editText22.");

                //***** Text "Email" - ViewId_textview110 *****//
                ViewId_textview110 = (TextView) findViewById(R.id.textView110);
                if(s.length() == 0){
                    ViewId_textview110.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview110.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onStart.");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onRestart.");

        ViewId_linearlayout_registration_reset_email = (LinearLayout) findViewById(R.id.linearlayout_registration_reset_email);
        ViewId_linearlayout_registration_reset_email.requestFocus();

        //***** Text "Email" - ViewId_textview110 *****//
        ViewId_textview110 = (TextView) findViewById(R.id.textView110);
        ViewId_textview110.setVisibility(View.VISIBLE);

        //***** Registration Email - ViewId_edittext22 *****//
        ViewId_edittext22 = (EditText) findViewById(R.id.editText22);
        ViewId_edittext22.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ViewId_edittext22.setText("");

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onResume.");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onPause.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11fRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11fRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setRinnai11fRegistration *****//
    //************************************//

    public void setRinnai11fRegistration() {

        //***** Text "Email" - ViewId_textview110 *****//
        ViewId_textview110 = (TextView) findViewById(R.id.textView110);
        ViewId_textview110.setVisibility(View.VISIBLE);

        //***** Registration Email - ViewId_edittext22 *****//
        ViewId_edittext22 = (EditText) findViewById(R.id.editText22);
        ViewId_edittext22.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_registrationf() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_registrationf(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Visit Rinnai - Phone
    public void goToActivity_Rinnai11f_Registration_Help(View view) {
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
