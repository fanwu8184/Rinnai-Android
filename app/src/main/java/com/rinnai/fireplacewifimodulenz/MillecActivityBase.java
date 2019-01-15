package com.rinnai.fireplacewifimodulenz;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by cconci on 19/04/2018.
 *
 * Example implementation
 * public class ActivityMainScreen extends MillecActivityBase
 *
 */




public class MillecActivityBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        adjustFontScale(getResources().getConfiguration());

        super.onCreate(savedInstanceState);


        Log.e( "MAB", "MillecActivityBase()"); //Custom Log class, you can use Log.w

    }

    public void adjustFontScale(Configuration configuration) {

        Log.e( "MAB", "adjustFontScale()"); //Custom Log class, you can use Log.w

        if (configuration.fontScale != 1.00) {
            Log.e( "MAB", "fontScale=" + configuration.fontScale); //Custom Log class, you can use Log.w
            Log.e( "MAB", "font too big. scale down..."); //Custom Log class, you can use Log.w
            configuration.fontScale = (float) 1.00;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
        }
    }

}
