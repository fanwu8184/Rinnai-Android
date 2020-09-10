package com.rinnai.fireplacewifimodulenz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JConci on 16/02/2018.
 */

public class Rinnai00eInitialSetupAlmost extends MillecActivityBase
        implements ActivityServerInterfaceUDP {

    Button ViewId_button44;

    TextView ViewId_textview155;

    Intent intent;

    boolean isClosing = false;

    Timer waitingUDPTimer;
    String wifiModuleNetWorkName;
    ProgressBar progressBarWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00e_initialsetup_almost);

        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onCreate.");

        wifiModuleNetWorkName = getIntent().getStringExtra("WIFIMODULE");
        progressBarWaiting = (ProgressBar) findViewById(R.id.progressBarWaiting);
        progressBarWaiting.setVisibility(View.INVISIBLE);

        TextView ssid = (TextView) findViewById(R.id.textView154);

        ssid.setText(AppGlobals.PushedSSID);

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button44 (Open Settings) *****//
        ViewId_button44 = (Button) findViewById(R.id.button44);
        ViewId_textview155 = (TextView) findViewById(R.id.textView155);

        ViewId_button44.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview155.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview155.setTextColor(Color.parseColor("#FFFFFFFF"));

                        //startupCheckTimer.cancel();
                        //isClosing = true;
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        //finish();
                        Log.d("myApp", "Rinnai00eInitialSetupAlmost_onClick: startActivity(ACTION_WIFI_SETTINGS).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview155.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onRestart.");

        isClosing = false;

        try{

            String compareAP = AppGlobals.PushedSSID;

            Log.e("myApp", "compareAP :" + compareAP);

            String currentAP = NetworkFunctions.getCurrentAccessPointName(this);

            Log.e("myApp", "currentAP :" + currentAP);

            if (currentAP.equals(compareAP))
            {
                System.out.println("CORRECT AP FOUND");
                setupUDP();
                progressBarWaiting.setVisibility(View.VISIBLE);
                waitingUDPTimer = new Timer();
                waitingUDPTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        stopUDP();
                        showAlert();
                    }
                }, 40000);
//                isClosing = true;
//                intent = new Intent(Rinnai00eInitialSetupAlmost.this, Rinnai00dInitialSetupComplete.class);
//                startActivity(intent);
//                finish();
//                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00dInitialSetupComplete).");
            }
        }
        catch(Exception e){
            Log.e("myApp", "Main18Activity - ERROR: " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onStop.");

        //startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00eInitialSetupAlmost_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    private void showProgress() {
    }

    private void showAlert() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressBarWaiting.setVisibility(View.INVISIBLE);
                AlertDialog alertDialog = new AlertDialog.Builder(Rinnai00eInitialSetupAlmost.this).create();
                alertDialog.setTitle("Setup Failed");
                alertDialog.setMessage("Please make sure the password you entered for the home network is correct. This App will be off for you to restart the setup again.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                alertDialog.show();
            }
        });
    }

    private void setupUDP() {
        try {
            //AppGlobals.UDPSrv.stopServer();
            AppGlobals.UDPSrv.setCurrentActivity(this);
            if (AppGlobals.UDPSrv.getState() == Thread.State.NEW) {
                AppGlobals.UDPSrv.start();
            } else if (AppGlobals.UDPSrv.getState() == Thread.State.TERMINATED) {
                AppGlobals.UDPSrv = new UDPServer(3500);
                AppGlobals.UDPSrv.start();
            }
            AppGlobals.UDPSrv.starServer();
        } catch (Exception e) {
            Log.d("myApp_WiFiUDP", "Rinnai17Login: appStart(Exception - " + e + ")");
        }
    }

    private void stopUDP(){
        AppGlobals.UDPSrv.stopServer();
    }

    @Override
    public void serverCallBackUDP(String text) {
        if (wifiModuleNetWorkName != null) {
            if (text.contains(wifiModuleNetWorkName)) {
                progressBarWaiting.setVisibility(View.INVISIBLE);
                stopUDP();
                waitingUDPTimer.cancel();
                isClosing = true;
                intent = new Intent(Rinnai00eInitialSetupAlmost.this, Rinnai00dInitialSetupComplete.class);
                startActivity(intent);
                finish();
                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00dInitialSetupComplete).");
            }
        }
    }
}