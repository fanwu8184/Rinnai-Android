package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JConci on 16/01/2018.
 */

public class Rinnai11aRegistration extends MillecActivityBase {

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11a_registration);

        Log.d("myApp_ActivityLifecycle", "Rinnai11aRegistration_onCreate.");

        startGotoRegistrationLogin();
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

        startupCheckTimer.cancel();
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

    //**************************//
    //***** startGotoLogin *****//
    //**************************//

    public void startGotoRegistrationLogin() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai11aRegistration: Tick.. " + startupCheckTimerCount);

                //Allow to run for >= 5 tries
                if (startupCheckTimerCount >= 5) {
                    startupCheckTimer.cancel();
                    isClosing = true;
                    intent = new Intent(Rinnai11aRegistration.this, Rinnai11bRegistration.class);
                    startActivity(intent);

                    finish();
                    Log.d("myApp", "Rinnai11aRegistration: startActivity(Rinnai11bRegistration).");

                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }
}