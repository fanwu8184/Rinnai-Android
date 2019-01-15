package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jconci on 11/09/2017.
 */

public class Rinnai34Lighting extends MillecActivityBase
        implements ActivityClientInterfaceTCP, ActivityTimerInterface {

    SwitchCompat ViewId_switch1;
    SwitchCompat ViewId_switch2;

    Drawable ViewId_switch1_track_drawable;
    float[] ViewId_switch1_track_matrix;
    ColorFilter ViewId_switch1_track_colorFilter;
    Drawable ViewId_switch1_thumb_drawable;
    float[] ViewId_switch1_thumb_matrix;
    ColorFilter ViewId_switch1_thumb_colorFilter;

    Drawable ViewId_switch2_track_drawable;
    float[] ViewId_switch2_track_matrix;
    ColorFilter ViewId_switch2_track_colorFilter;
    Drawable ViewId_switch2_thumb_drawable;
    float[] ViewId_switch2_thumb_matrix;
    ColorFilter ViewId_switch2_thumb_colorFilter;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai34_lighting);

        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onCreate.");

        startCommunicationErrorFault();

        startTxRN171DeviceGetStatus();

        //**************************************//
        //***** setOnCheckedChangeListener *****//
        //**************************************//

        //***** setOnCheckedChangeListener - switch1 (Ember glow when fireplace POWER button OFF.) *****//
        ViewId_switch1 = (SwitchCompat) findViewById(R.id.switch1);
        //Switch ViewId_switch1 = (Switch)findViewById(R.id.switch1);

        ViewId_switch1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.d("myApp", "ViewId_switch1 : onClick");

                updateSwitch1Details(v);
            }
        });

        //***** setOnCheckedChangeListener - switch2 (Ember glow when fireplace is in STANDBY) *****//
        ViewId_switch2 = (SwitchCompat) findViewById(R.id.switch2);
        //Switch ViewId_switch2 = (Switch)findViewById(R.id.switch2);

        ViewId_switch2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("myApp", "ViewId_switch2 : onClick");

                updateSwitch2Details(v);
            }
        });

        //**************************************//
        //***** setOnCheckedChangeListener *****//
        //**************************************//

        //***** setOnCheckedChangeListener - switch1 (Ember glow when fireplace POWER button OFF.) *****//
        ViewId_switch1 = (SwitchCompat) findViewById(R.id.switch1);
        //Switch ViewId_switch1 = (Switch)findViewById(R.id.switch1);

        ViewId_switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("myApp", "ViewId_switch1 : onCheckedChanged");

                updateSwitch1Details(buttonView);
            }
        });

        //***** setOnCheckedChangeListener - switch2 (Ember glow when fireplace is in STANDBY) *****//
        ViewId_switch2 = (SwitchCompat) findViewById(R.id.switch2);
        //Switch ViewId_switch2 = (Switch)findViewById(R.id.switch2);

        ViewId_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("myApp", "ViewId_switch2 : onCheckedChanged");

                updateSwitch2Details(buttonView);
            }
        });
    }

    //********************************//
    //***** updateSwitch1Details *****//
    //********************************//

    public void updateSwitch1Details(View v) {
        // do something, the isChecked will be
        // true if the switch is in the On position
        if (((SwitchCompat) v).isChecked() == false) {
            Log.d("myApp", "ViewId_switch1 : false");

            //***** Switch - Track *****//
            ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

            ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 64,
                    0, 0, 0, 0, 64,
                    0, 0, 0, 0, 64,
                    0, 0, 0, 1, 0};

            ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
            ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

            //***** Switch - Thumb *****//
            ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

            ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                    0, 0, 0, 0, 127,
                    0, 0, 0, 0, 127,
                    0, 0, 0, 1, 0};

            ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
            ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);
        } else {
            Log.d("myApp", "ViewId_switch1 : true");

            //***** Switch - Track *****//
            ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

            ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 0,
                    0, 0, 0, 0, 191,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0};

            ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
            ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

            //***** Switch - Thumb *****//
            ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

            ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                    0, 0, 0, 0, 255,
                    0, 0, 0, 0, 255,
                    0, 0, 0, 1, 0};

            ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
            ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);
        }

        if (ViewId_switch1.isChecked() == true && ViewId_switch2.isChecked() == true) {
            AppGlobals.ViewId_switch1_switch2value = 1;
        } else if (ViewId_switch1.isChecked() == true && ViewId_switch2.isChecked() == false) {
            AppGlobals.ViewId_switch1_switch2value = 2;
        } else if (ViewId_switch1.isChecked() == false && ViewId_switch2.isChecked() == true) {
            AppGlobals.ViewId_switch1_switch2value = 3;
        } else {
            AppGlobals.ViewId_switch1_switch2value = 0;
        }

        enableguardtimeRinnai34Lighting();

        Tx_RN171_DEVICE_SET_LIGHTINGINFO();
    }

    //********************************//
    //***** updateSwitch2Details *****//
    //********************************//

    public void updateSwitch2Details(View v) {
        // do something, the isChecked will be
        // true if the switch is in the On position
        if (((SwitchCompat) v).isChecked() == false) {
            Log.d("myApp", "ViewId_switch2 : false");

            //***** Switch - Track *****//
            ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

            ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 64,
                    0, 0, 0, 0, 64,
                    0, 0, 0, 0, 64,
                    0, 0, 0, 1, 0};

            ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
            ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

            //***** Switch - Thumb *****//
            ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

            ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                    0, 0, 0, 0, 127,
                    0, 0, 0, 0, 127,
                    0, 0, 0, 1, 0};

            ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
            ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
        } else {
            Log.d("myApp", "ViewId_switch2 : true");

            //***** Switch - Track *****//
            ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

            ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 0,
                    0, 0, 0, 0, 191,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 1, 0};

            ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
            ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

            //***** Switch - Thumb *****//
            ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

            ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                    0, 0, 0, 0, 255,
                    0, 0, 0, 0, 255,
                    0, 0, 0, 1, 0};

            ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
            ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
        }

        if (ViewId_switch1.isChecked() == true && ViewId_switch2.isChecked() == true) {
            AppGlobals.ViewId_switch1_switch2value = 1;
        } else if (ViewId_switch1.isChecked() == true && ViewId_switch2.isChecked() == false) {
            AppGlobals.ViewId_switch1_switch2value = 2;
        } else if (ViewId_switch1.isChecked() == false && ViewId_switch2.isChecked() == true) {
            AppGlobals.ViewId_switch1_switch2value = 3;
        } else {
            AppGlobals.ViewId_switch1_switch2value = 0;
        }

        enableguardtimeRinnai34Lighting();

        Tx_RN171_DEVICE_SET_LIGHTINGINFO();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onRestart.");

        isClosing = false;

        startCommunicationErrorFault();

        startTxRN171DeviceGetStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onPause.");

        AppGlobals.CommErrorFault.stopTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai34Lighting_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai34Lighting_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //***************************************//
    //***** startTxRN171DeviceGetStatus *****//
    //***************************************//

    public void startTxRN171DeviceGetStatus() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai34Lighting: Tick.. " + startupCheckTimerCount);

                Tx_RN171DeviceGetStatus();

                AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime--;

            }

        }, 0, 2000);

    }

    //*******************************************//
    //***** enableguardtimeRinnai34Lighting *****//
    //*******************************************//

    public void enableguardtimeRinnai34Lighting() {
        Log.d("myApp", "enableguardtimeRinnai34Lighting");

        AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime = 5;
    }

    //******************************************//
    //***** resetguardtimeRinnai34Lighting *****//
    //******************************************//

    public void resetguardtimeRinnai34Lighting() {
        Log.d("myApp", "resetguardtimeRinnai34Lighting");

        AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime = 0;
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
                        if (ViewId_switch1.isPressed() == false && ViewId_switch2.isPressed() == false) {

                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime <= 0) {

                                if (pType.contains("22")) {

                                    //*****************************//
                                    //***** Main Power Switch *****//
                                    //*****************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Main Power Switch (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch + ")");

                                    //Main power switch = ON:[0x00]
                                    if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch == 0) {

                                        //***************************//
                                        //***** Error Code HIGH *****//
                                        //***************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Error Code HIGH (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI + ")");

                                        //***************************//
                                        //***** Error Code LOW *****//
                                        //***************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Error Code LOW (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO + ")");

                                        //Error code HIGH = no error code:[Hx(0x20), Dec(32)]
                                        //Error code LOW = no error code:[Hx(0x20), Dec(32)]
                                        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI == 32 && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO == 32) {

                                            //***************************//
                                            //***** Operation State *****//
                                            //***************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Operation State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState + ")");

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
                                                resetguardtimeRinnai34Lighting();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            } else {
                                                resetguardtimeRinnai34Lighting();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //*************************//
                                            //***** Burning State *****//
                                            //*************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Burning State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState + ")");

                                            //Burning state = Extinguish:[0x00]
                                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 0) {
                                                //resetguardtimeRinnai34Lighting();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai34Lighting.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);

                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            }

                                            //Burning state = Ignite:[0x01]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 1) {
                                                resetguardtimeRinnai34Lighting();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai34Lighting.this, Rinnai22IgnitionSequence.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai22IgnitionSequence).");
                                            }

                                            //Burning state = Thermostat:[0x02]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 2) {
                                                //resetguardtimeRinnai34Lighting();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai34Lighting.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);

                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            }

                                            //Burning state = Thermostat OFF:[0x03]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 3) {
                                                //resetguardtimeRinnai34Lighting();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai34Lighting.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);

                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            } else {
                                                resetguardtimeRinnai34Lighting();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //**************************//
                                            //***** Operation Mode *****//
                                            //**************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Operation Mode (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode + ")");

                                            //Operation mode = Mode not exist:[0x00]
                                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 0) {
                                                //resetguardtimeRinnai34Lighting();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                                //startActivity(intent);

                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //Operation mode = Flame mode:[0x01]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 1) {
                                                AppGlobals.Button_flame_settemp_actionvisible = true;
                                            }

                                            //Operation mode = Thermostat mode:[0x02]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 2) {
                                                AppGlobals.Button_flame_settemp_actionvisible = false;
                                            } else {
                                                resetguardtimeRinnai34Lighting();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //**********************//
                                            //***** Lightening *****//
                                            //**********************//

                                            Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: Lightening (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmLightingInfo + ")");

                                            //***** switch1 (Ember glow when fireplace POWER button OFF.) *****//
                                            //***** switch2 (Ember glow when fireplace is in STANDBY) *****//
                                            switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmLightingInfo) {
                                                case 0:
                                                    //***** switch1 (Ember glow when fireplace POWER button OFF. - OFF) *****//
                                                    //***** Switch - Off Position *****//
                                                    ViewId_switch1.setChecked(false);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

                                                    ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
                                                    ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

                                                    ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
                                                    ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);

                                                    //***** switch2 (Ember glow when fireplace is in STANDBY - OFF) *****//
                                                    //***** Switch - Off Position *****//
                                                    ViewId_switch2.setChecked(false);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

                                                    ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
                                                    ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

                                                    ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
                                                    ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
                                                    break;

                                                case 1:
                                                    //***** switch1 (Ember glow when fireplace POWER button OFF. - ON) *****//
                                                    //***** Switch - On Position *****//
                                                    ViewId_switch1.setChecked(true);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

                                                    ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 0,
                                                            0, 0, 0, 0, 191,
                                                            0, 0, 0, 0, 0,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
                                                    ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

                                                    ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
                                                    ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);

                                                    //***** switch2 (Ember glow when fireplace is in STANDBY - ON) *****//
                                                    //***** Switch - On Position *****//
                                                    ViewId_switch2.setChecked(true);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

                                                    ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 0,
                                                            0, 0, 0, 0, 191,
                                                            0, 0, 0, 0, 0,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
                                                    ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

                                                    ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
                                                    ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
                                                    break;

                                                case 2:
                                                    //***** switch1 (Ember glow when fireplace POWER button OFF. - ON) *****//
                                                    //***** Switch - On Position *****//
                                                    ViewId_switch1.setChecked(true);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

                                                    ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 0,
                                                            0, 0, 0, 0, 191,
                                                            0, 0, 0, 0, 0,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
                                                    ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

                                                    ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
                                                    ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);

                                                    //***** switch2 (Ember glow when fireplace is in STANDBY - OFF) *****//
                                                    //***** Switch - Off Position *****//
                                                    ViewId_switch2.setChecked(false);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

                                                    ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
                                                    ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

                                                    ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
                                                    ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
                                                    break;

                                                case 3:
                                                    //***** switch1 (Ember glow when fireplace POWER button OFF. - OFF) *****//
                                                    //***** Switch - Off Position *****//
                                                    ViewId_switch1.setChecked(false);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch1_track_drawable = (Drawable) ViewId_switch1.getTrackDrawable();

                                                    ViewId_switch1_track_matrix = new float[]{0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 0, 64,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_track_matrix);
                                                    ViewId_switch1_track_drawable.setColorFilter(ViewId_switch1_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch1_thumb_drawable = (Drawable) ViewId_switch1.getThumbDrawable();

                                                    ViewId_switch1_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 0, 127,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch1_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch1_thumb_matrix);
                                                    ViewId_switch1_thumb_drawable.setColorFilter(ViewId_switch1_thumb_colorFilter);

                                                    //***** switch2 (Ember glow when fireplace is in STANDBY - ON) *****//
                                                    //***** Switch - On Position *****//
                                                    ViewId_switch2.setChecked(true);

                                                    //***** Switch - Track *****//
                                                    ViewId_switch2_track_drawable = (Drawable) ViewId_switch2.getTrackDrawable();

                                                    ViewId_switch2_track_matrix = new float[]{0, 0, 0, 0, 0,
                                                            0, 0, 0, 0, 191,
                                                            0, 0, 0, 0, 0,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_track_matrix);
                                                    ViewId_switch2_track_drawable.setColorFilter(ViewId_switch2_track_colorFilter);

                                                    //***** Switch - Thumb *****//
                                                    ViewId_switch2_thumb_drawable = (Drawable) ViewId_switch2.getThumbDrawable();

                                                    ViewId_switch2_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 0, 255,
                                                            0, 0, 0, 1, 0};

                                                    ViewId_switch2_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch2_thumb_matrix);
                                                    ViewId_switch2_thumb_drawable.setColorFilter(ViewId_switch2_thumb_colorFilter);
                                                    break;
                                                default:
                                                    break;
                                            }

                                        } else {
                                            resetguardtimeRinnai34Lighting();
                                            startupCheckTimer.cancel();
                                            isClosing = true;
                                            intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                        }
                                    }
                                    //Main power switch = OFF:[0x01]
                                    else {
                                        resetguardtimeRinnai34Lighting();
                                        startupCheckTimer.cancel();
                                        isClosing = true;
                                        intent = new Intent(Rinnai34Lighting.this, Rinnai26PowerOff.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai34Lighting_clientCallBackTCP: startActivity(Rinnai26PowerOff).");
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai34Lighting: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai34Lighting: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai34Lighting: clientCallBackTCP");
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
            Log.d("myApp_WiFiTCP", "Rinnai34Lighting: Tx_RN171DeviceGetStatus(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_LIGHTINGINFO *****//
    public void Tx_RN171_DEVICE_SET_LIGHTINGINFO() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_36," + String.format("%02X", AppGlobals.ViewId_switch1_switch2value) + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai34Lighting: Tx_RN171_DEVICE_SET_LIGHTINGINFO(Exception - " + e + ")");
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
        resetguardtimeRinnai34Lighting();
        startupCheckTimer.cancel();
        isClosing = true;
        intent = new Intent(Rinnai34Lighting.this, Rinnai26Fault.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai34Lighting_timereventCallBackTimer: startActivity(Rinnai26Fault).");
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Login
    //public void goToActivity_Rinnai17_Login(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai17Login.class);
    //    startActivity(intent);
    //}

    //Home Screen (Finish)
    //public void goToActivity_Rinnai21_Home_Screen_Finish(View view) {
    //    frameAnimation.stop();
    //    for (int i = 0; i < frameAnimation.getNumberOfFrames(); ++i){
    //        Drawable frame = frameAnimation.getFrame(i);
    //        if (frame instanceof BitmapDrawable) {
    //            ((BitmapDrawable)frame).getBitmap().recycle();
    //        }
    //        frame.setCallback(null);
    //    }
    //    frameAnimation.setCallback(null);
    //
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //    finish();
    //}

    //Home Screen
    public void goToActivity_Rinnai21_Home_Screen(View view) {
        resetguardtimeRinnai34Lighting();
        startupCheckTimer.cancel();
        Intent intent = new Intent(this, Rinnai21HomeScreen.class);
        startActivity(intent);
    }

    //Timers a - Scheduled Timers
    //public void goToActivity_Rinnai33a_Timers(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33aTimers.class);
    //    startActivity(intent);
    //}

    //Timers b - Scheduled Timer
    //public void goToActivity_Rinnai33b_Timers(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33bTimers.class);
    //    startActivity(intent);
    //}

    //Visit Rinnai
    //public void goToActivity_Rinnai35_Visit_Rinnai (View view){
    //    resetguardtimeRinnai34Lighting();
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
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai34Lighting.class);
    //    startActivity(intent);
    //}

    //Network
    //public void goToActivity_Rinnai37_Network (View view){
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai37Network.class);
    //    startActivity(intent);
    //}

    //Fault
    //public void goToActivity_Rinnai26_Fault(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26Fault.class);
    //    startActivity(intent);
    //}

    //Fault - Service Fault Codes
    //public void goToActivity_Rinnai33_Service_Fault_Codes(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33ServiceFaultCodes.class);
    //    startActivity(intent);
    //}

    //Power Off
    //public void goToActivity_Rinnai26_Power_Off(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26PowerOff.class);
    //    startActivity(intent);
    //}

    //Ignition Sequence
    //public void goToActivity_Rinnai22_Ignition_Sequence(View view) {
    //    resetguardtimeRinnai34Lighting();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai22IgnitionSequence.class);
    //    startActivity(intent);
    //}

}
