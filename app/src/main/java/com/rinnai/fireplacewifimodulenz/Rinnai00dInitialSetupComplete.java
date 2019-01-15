package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JConci on 16/02/2018.
 */

public class Rinnai00dInitialSetupComplete extends MillecActivityBase {

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00d_initialsetup_complete);

        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onCreate.");

        startGotoLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onRestart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00dInitialSetupComplete_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00dInitialSetupComplete_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //**************************//
    //***** startGotoLogin *****//
    //**************************//

    public void startGotoLogin() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai00dInitialSetupComplete: Tick.. " + startupCheckTimerCount);

                //Allow to run for >= 5 tries
                if (startupCheckTimerCount >= 5) {
                    AppGlobals.rfwmInitialSetupFlag = true;
                    AppGlobals.UDPSrv = new UDPServer(3500);
                    AppGlobals.fireplaceWifi.clear();
                    AppGlobals.WiFiAccessPointInfo_List.clear();
                    AppGlobals.TimersInfo_List.clear();

                    startupCheckTimer.cancel();
                    isClosing = true;
                    intent = new Intent(Rinnai00dInitialSetupComplete.this, Rinnai17Login.class);
                    startActivity(intent);

                    finish();
                    Log.d("myApp_WiFiTCP", "Rinnai00dInitialSetupComplete: startActivity(Rinnai17Login).");

                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }
}
