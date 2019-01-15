package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by JConci on 16/02/2018.
 */

public class Rinnai00eInitialSetupAlmost extends MillecActivityBase {

    Button ViewId_button44;

    TextView ViewId_textview155;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00e_initialsetup_almost);

        Log.d("myApp_ActivityLifecycle", "Rinnai00eInitialSetupAlmost_onCreate.");

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
                isClosing = true;
                intent = new Intent(Rinnai00eInitialSetupAlmost.this, Rinnai00dInitialSetupComplete.class);
                startActivity(intent);

                finish();
                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00dInitialSetupComplete).");
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
}