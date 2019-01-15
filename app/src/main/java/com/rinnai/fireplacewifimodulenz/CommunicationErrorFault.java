package com.rinnai.fireplacewifimodulenz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rinnai.fireplacewifimodulenz.ActivityTimerInterface;
import com.rinnai.fireplacewifimodulenz.Rinnai17Login;
import com.rinnai.fireplacewifimodulenz.Rinnai26Fault;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JConci on 30/11/2017.
 */

public class CommunicationErrorFault {

    Timer universalCheckTimer;
    int universalCheckTimerCount;

    private ActivityTimerInterface currentActivity;

    void setCurrentActivity(ActivityTimerInterface currentActivity){
        resetCommunicationErrorFault();
        this.currentActivity = currentActivity;
        Log.d("myAppR", "CommunicationErrorFault: setCurrentActivity (" + universalCheckTimerCount + ")");
    }

    void resetCommunicationErrorFault(){
        universalCheckTimerCount = 0;

        Log.d("myAppR", "CommunicationErrorFault: resetCommunicationErrorFault (" + universalCheckTimerCount + ")");
    }

    void stopTimer(){

        Log.d("myAppR", "CommunicationErrorFault: stopTimer (" + universalCheckTimerCount + ")");
        this.universalCheckTimer.cancel();

    }

    //*****************************************//
    //***** checkRN171DeviceCommunication *****//
    //*****************************************//

    public void checkRN171DeviceCommunication() {

        Log.d("myAppR", "CommunicationErrorFault: checkRN171DeviceCommunication.");

        try{
            this.universalCheckTimer.cancel();
        }catch(Exception e){
            Log.d("myAppR", "CommunicationErrorFault: checkRN171DeviceCommunication(Exception - " + e + ")");
        }

        this.universalCheckTimerCount = 0;

        this.universalCheckTimer = new Timer();

        this.universalCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myAppR", "Rinnai_AppGlobals: Tick.. " + universalCheckTimerCount);

                ((Activity)currentActivity).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("myAppR", "Rinnai_AppGlobals: Tick(UI).. " + universalCheckTimerCount);

                        //Allow to run for >= 20 tries
                        if (universalCheckTimerCount >= 20) {

                            currentActivity.timereventCallBackTimer(1);

                        }

                        universalCheckTimerCount++;

                    }});

            }

        }, 0, 1000);

    }

}
