package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import AWSmodule.AWSconnection;

/**
 * Created by jconci on 10/10/2017.
 */

public class Rinnai26PowerOff extends MillecActivityBase
        implements ActivityClientInterfaceTCP, ActivityTimerInterface {

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    RemoteSetting remoteSetting;
    Timer remoteTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai26_power_off);

        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onCreate.");

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress != null) {
            startCommunicationErrorFault();
        }

        startTxRN171DeviceGetStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onRestart.");

        isClosing = false;

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress != null) {
            startCommunicationErrorFault();
        }

        startTxRN171DeviceGetStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onPause.");

        AppGlobals.CommErrorFault.stopTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onStop.");

        cancelTimers();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai26PowerOff_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai26PowerOff_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //***************************************//
    //***** startTxRN171DeviceGetStatus *****//
    //***************************************//

    public void startTxRN171DeviceGetStatus() {

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress == null) {

            remoteTimer = new Timer();
            remoteTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getRemoteStat();
                }
            }, 0, 2000);

        } else {
            this.startupCheckTimerCount = 0;

            this.startupCheckTimer = new Timer();

            this.startupCheckTimer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Log.d("myApp", "Rinnai26PowerOff: Tick.. " + startupCheckTimerCount);

                    Tx_RN171DeviceGetStatus();

                }

            }, 0, 2000);
        }
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
                        if (pType.contains("22")) {

                            //*****************************//
                            //***** Main Power Switch *****//
                            //*****************************//

                            Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: Main Power Switch (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch + ")");

                            //Main power switch = ON:[0x00]
                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch == 0) {

                                //***************************//
                                //***** Error Code HIGH *****//
                                //***************************//

                                Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: Error Code HIGH (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI + ")");

                                //***************************//
                                //***** Error Code LOW *****//
                                //***************************//

                                Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: Error Code LOW (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO + ")");

                                //Error code HIGH = no error code:[Hx(0x20), Dec(32)]
                                //Error code LOW = no error code:[Hx(0x20), Dec(32)]
                                if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI == 32 && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO == 32) {

                                    //***************************//
                                    //***** Operation State *****//
                                    //***************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: Operation State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState + ")");

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
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai26Fault.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                    } else {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai26Fault.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                    }

                                    //*************************//
                                    //***** Burning State *****//
                                    //*************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: Burning State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState + ")");

                                    //Burning state = Extinguish:[0x00]
                                    if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 0) {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai21HomeScreen.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                    }

                                    //Burning state = Ignite:[0x01]
                                    else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 1) {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai22IgnitionSequence.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai22IgnitionSequence).");
                                    }

                                    //Burning state = Thermostat:[0x02]
                                    else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 2) {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai21HomeScreen.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                    }

                                    //Burning state = Thermostat OFF:[0x03]
                                    else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 3) {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai21HomeScreen.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                    } else {
                                        cancelTimers();
                                        isClosing = true;
                                        intent = new Intent(Rinnai26PowerOff.this, Rinnai26Fault.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                    }

                                } else {
                                    cancelTimers();
                                    isClosing = true;
                                    intent = new Intent(Rinnai26PowerOff.this, Rinnai26Fault.class);
                                    startActivity(intent);

                                    finish();
                                    Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                }
                            }
                            //Main power switch = OFF:[0x01]
                            else {
                                //    startupCheckTimer.cancel();
                                //    isClosing = true;
                                //    intent = new Intent(Rinnai26PowerOff.this, Rinnai26PowerOff.class);
                                //    startActivity(intent);
                                //
                                //    finish();
                                //    Log.d("myApp_WiFiTCP", "Rinnai26PowerOff_clientCallBackTCP: startActivity(Rinnai26PowerOff).");
                            }
                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai26PowerOff: clientCallBackTCP");
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
            Log.d("myApp_WiFiTCP", "Rinnai26PowerOff: Tx_RN171DeviceGetStatus(Exception - " + e + ")");
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
        cancelTimers();
        isClosing = true;
        intent = new Intent(Rinnai26PowerOff.this, Rinnai26Fault.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai26PowerOff_timereventCallBackTimer: startActivity(Rinnai26Fault).");
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

    private void getRemoteStat() {
        final String uuid = AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).UUID;
        AWSconnection.remoteControlSelectURL(uuid,

                //Call interface to retrieve Async results
                new AWSconnection.textResult() {
                    @Override

                    public void getResult(String result) {

                        //Log outputs
                        Log.d("RC Values::", result);
                        try {
                            JSONObject jObject = new JSONObject(result);
                            JSONArray jArray = jObject.getJSONArray("Items");
                            int setFlame = jArray.getJSONObject(0).getInt("set_flame");
                            int currentTemp = jArray.getJSONObject(0).getInt("current_temp");
                            int mode = jArray.getJSONObject(0).getInt("mode");
                            int setTemp = jArray.getJSONObject(0).getInt("set_temp");
                            String faultCode = jArray.getJSONObject(0).getString("fault");
                            remoteSetting = new RemoteSetting(uuid, faultCode, setTemp, setFlame, currentTemp, mode);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUI();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void updateUI() {

        if (remoteSetting != null) {

            if (remoteSetting.mode != 4) {
                cancelTimers();
                isClosing = true;
                intent = new Intent(Rinnai26PowerOff.this, Rinnai21HomeScreen.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void cancelTimers() {
        if (startupCheckTimer != null) {
            startupCheckTimer.cancel();
        }
        if (remoteTimer != null) {
            remoteTimer.cancel();
        }
    }
}