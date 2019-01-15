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

public class Rinnai00bInitialSetupPower extends MillecActivityBase {

    Button ViewId_button40;

    TextView ViewId_textview134;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00b_initialsetup_power);

        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onCreate.");

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button40 (Next) *****//
        ViewId_button40 = (Button) findViewById(R.id.button40);
        ViewId_textview134 = (TextView) findViewById(R.id.textView134);

        ViewId_button40.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview134.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview134.setTextColor(Color.parseColor("#FFFFFFFF"));

                        //startupCheckTimer.cancel();
                        //isClosing = true;
                        intent = new Intent(Rinnai00bInitialSetupPower.this, Rinnai00cInitialSetupConnect.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai00bInitialSetupPower_onClick: startActivity(Rinnai00cInitialSetupConnect).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview134.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onRestart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onStop.");

        //startupCheckTimer.cancel();
        //isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00bInitialSetupPower_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00bInitialSetupPower_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }
}
