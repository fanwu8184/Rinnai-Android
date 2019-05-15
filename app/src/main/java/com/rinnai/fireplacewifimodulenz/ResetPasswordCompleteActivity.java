package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ResetPasswordCompleteActivity extends MillecActivityBase {

    Timer startupCheckTimer;
    int startupCheckTimerCount;
    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_complete);

        startGotoLogin();
    }

    private void startGotoLogin() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                //Allow to run for >= 5 tries
                if (startupCheckTimerCount >= 5) {
                    AppGlobals.rfwmInitialSetupFlag = true;
                    AppGlobals.UDPSrv = new UDPServer(3500);
                    AppGlobals.fireplaceWifi.clear();
                    AppGlobals.WiFiAccessPointInfo_List.clear();
                    AppGlobals.TimersInfo_List.clear();

                    startupCheckTimer.cancel();
                    isClosing = true;
                    Intent intent = new Intent(ResetPasswordCompleteActivity.this, Rinnai17Login.class);
                    startActivity(intent);
                    finish();

                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }
}
