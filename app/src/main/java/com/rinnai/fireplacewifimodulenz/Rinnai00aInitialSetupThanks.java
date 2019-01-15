package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by JConci on 14/02/2018.
 */

public class Rinnai00aInitialSetupThanks extends MillecActivityBase {

    Button ViewId_button42;

    TextView ViewId_textview156;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00a_initialsetup_thanks);

        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onCreate.");

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button42 (Next) *****//
        ViewId_button42 = (Button) findViewById(R.id.button42);
        ViewId_textview156 = (TextView) findViewById(R.id.textView156);

        ViewId_button42.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview156.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview156.setTextColor(Color.parseColor("#FFFFFFFF"));

                        //startupCheckTimer.cancel();
                        //isClosing = true;
                        intent = new Intent(Rinnai00aInitialSetupThanks.this, Rinnai00bInitialSetupPower.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai00aInitialSetupThanks_onClick: startActivity(Rinnai00bInitialSetupPower).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview156.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onRestart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onStop.");

        //startupCheckTimer.cancel();
        //isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00aInitialSetupThanks_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00aInitialSetupThanks_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }
}
