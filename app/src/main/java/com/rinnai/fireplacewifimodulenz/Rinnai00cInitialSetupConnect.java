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

public class Rinnai00cInitialSetupConnect extends MillecActivityBase {

    Button ViewId_button41;

    TextView ViewId_textview149;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00c_initialsetup_connect);

        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onCreate.");

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button41 (Open Settings) *****//
        ViewId_button41 = (Button) findViewById(R.id.button41);
        ViewId_textview149 = (TextView) findViewById(R.id.textView149);

        ViewId_button41.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview149.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview149.setTextColor(Color.parseColor("#FFFFFFFF"));

                        //startupCheckTimer.cancel();
                        //isClosing = true;
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        //finish();
                        Log.d("myApp", "Rinnai00cInitialSetupConnect_onClick: startActivity(ACTION_WIFI_SETTINGS).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview149.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onRestart.");

        isClosing = false;

        String compareAP = "RinnaiWiFi_";
        String currentAP = NetworkFunctions.getCurrentAccessPointName(this);

        Log.d("myApp_WiFiSystem", "Rinnai00cInitialSetupConnect: AP FOUND (" + currentAP + ")");
        Log.d("myApp_WiFiSystem", "Rinnai00cInitialSetupConnect: AP FOUND Length (" + currentAP.length() + ")");

        if (currentAP.contains(compareAP) && currentAP.length() == 17) {
            Log.d("myApp_WiFiSystem", "Rinnai00cInitialSetupConnect: CORRECT AP FOUND.");

            //startupCheckTimer.cancel();
            isClosing = true;
            intent = new Intent(Rinnai00cInitialSetupConnect.this, Rinnai17Login.class);
            startActivity(intent);

            finish();
            Log.d("myApp", "Rinnai00cInitialSetupConnect_onClick: startActivity(Rinnai17Login).");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onStop.");

        //startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00cInitialSetupConnect_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00cInitialSetupConnect_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }
}
