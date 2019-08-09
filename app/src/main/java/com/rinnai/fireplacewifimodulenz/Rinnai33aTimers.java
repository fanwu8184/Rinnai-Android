package com.rinnai.fireplacewifimodulenz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jconci on 20/09/2017.
 */

public class Rinnai33aTimers extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    SwitchCompat ViewId_switch3;

    Drawable ViewId_switch3_track_drawable;
    float[] ViewId_switch3_track_matrix;
    ColorFilter ViewId_switch3_track_colorFilter;
    Drawable ViewId_switch3_thumb_drawable;
    float[] ViewId_switch3_thumb_matrix;
    ColorFilter ViewId_switch3_thumb_colorFilter;

    TextView ViewId_textview30;
    TextView ViewId_textview31;
    TextView ViewId_textview32;
    TextView ViewId_textview33a;
    TextView ViewId_textview33b;
    TextView ViewId_textview33c;
    TextView ViewId_textview34a;
    TextView ViewId_textview34b;
    TextView ViewId_textview34c;
    TextView ViewId_textview66;
    TextView ViewId_textview67;
    TextView ViewId_clearTimerstextView;

    Button ViewId_imagebutton12;
    Button ViewId_clearTimersImageButton;

    View ViewId_scrollview_row_rinnai33a_timers;
    View ViewId_scrollview_row_switch_rinnai33a_timers;

    TableLayout ViewId_timersa_tableLayout;

    ViewGroup ViewId_include_waiting_timersa;
    ViewGroup ViewId_include_scrollview_lockout_timersa;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    boolean isClosing = false;
    boolean isDeviceGetTimers = false;

    int scrollviewrowrinnai33atimers_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai33a_timers);

        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onCreate.");

        setRinnai33aTimers();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imagebutton12 (Add New) *****//
        ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);
        ViewId_imagebutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal >= 8) {
                    Toast.makeText(Rinnai33aTimers.this, "Maximum of 8 Timers reached. \nPlease Edit or Delete a Timer.",
                            Toast.LENGTH_LONG).show();
                } else {
                    startupCheckTimer.cancel();
                    isClosing = true;
                    scrollviewrowrinnai33atimers_id = AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal;
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal++;

                    //defaults
                    AppGlobals.selected_scrollviewrowrinnai33atimersmeridianon = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimershourson = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimersminuteson = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimersmeridianoff = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimershoursoff = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimersminutesoff = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek = 0;
                    AppGlobals.selected_scrollviewrowrinnai33atimerssettemperature = 16;

                    Intent intent = new Intent(Rinnai33aTimers.this, Rinnai33bTimers.class);
                    intent.putExtra("selected_scrollviewrowrinnai33atimersid", scrollviewrowrinnai33atimers_id);
                    startActivity(intent);

                    finish();
                    Log.d("myApp", "Rinnai33aTimers_onClick: startActivity(Rinnai33bTimers).");
                }
            }
        });

        ViewId_clearTimersImageButton = (Button) findViewById(R.id.clearTimersImageButton);
        ViewId_clearTimersImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThereATimerRunning()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Rinnai33aTimers.this).create();
                    alertDialog.setTitle("Warning Automatic OFF Function Disabled");
                    alertDialog.setMessage("Please note that there is a unit running on this time. If you delete the timer you will have to operate the appliance manually.");

                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    clearTimers();
                                }
                            });
                    alertDialog.show();
                } else {
                    clearTimers();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onRestart.");

        //***** include - ViewId_include_waiting_timersa *****//
        ViewId_include_waiting_timersa = (ViewGroup) findViewById(R.id.include_waiting_timersa);

        ViewId_include_waiting_timersa.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_scrollview_lockout_timersa *****//
        ViewId_include_scrollview_lockout_timersa = (ViewGroup) findViewById(R.id.include_scrollview_lockout_timersa);

        ViewId_include_scrollview_lockout_timersa.setVisibility(View.VISIBLE);

        //***** TableLayout - ViewId_timersa_tableLayout *****//
        ViewId_timersa_tableLayout = (TableLayout) findViewById(R.id.timersa_tableLayout);

        ViewId_timersa_tableLayout.setVisibility(View.INVISIBLE);

        //***** Add New Button - ViewId_imagebutton12 *****//
        ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);

        ViewId_imagebutton12.setEnabled(false);

        isClosing = false;
        isDeviceGetTimers = false;

        //***** TX WiFi - TCP *****//
        startTxRN171DeviceGetTimers();
        Log.d("myApp", "Rinnai33aTimers - Tx_RN171DeviceGetTimers");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai33aTimers_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai33aTimers_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //****************************************//
    //***** startTxRN171DeviceGetTimers *****//
    //****************************************//

    public void startTxRN171DeviceGetTimers() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai33aTimers: Tick.. " + startupCheckTimerCount);

                if (startupCheckTimerCount >= 5) {

                    if (isDeviceGetTimers == false) {

                        if (startupCheckTimerCount % 5 == 0) {
                            Tx_RN171DeviceGetTimers();
                            Log.d("myApp", "Rinnai33aTimers - Tx_RN171DeviceGetTimers");
                        }
                    } else {
                        startupCheckTimer.cancel();
                    }
                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }

    //******************************//
    //***** setRinnai33aTimers *****//
    //******************************//

    public void setRinnai33aTimers() {

        //***** include - ViewId_include_waiting_timersa *****//
        ViewId_include_waiting_timersa = (ViewGroup) findViewById(R.id.include_waiting_timersa);

        ViewId_include_waiting_timersa.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_scrollview_lockout_timersa *****//
        ViewId_include_scrollview_lockout_timersa = (ViewGroup) findViewById(R.id.include_scrollview_lockout_timersa);

        ViewId_include_scrollview_lockout_timersa.setVisibility(View.VISIBLE);

        //***** TableLayout - ViewId_timersa_tableLayout *****//
        ViewId_timersa_tableLayout = (TableLayout) findViewById(R.id.timersa_tableLayout);

        ViewId_timersa_tableLayout.setVisibility(View.INVISIBLE);

        //***** Add New Button - ViewId_imagebutton12 *****//
        ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);

        ViewId_imagebutton12.setEnabled(false);

        //***** TX WiFi - TCP *****//
        startTxRN171DeviceGetTimers();
        Log.d("myApp", "Rinnai33aTimers - Tx_RN171DeviceGetTimers");

    }

    //*******************************************************//
    //***** scrollviewrowrinnai33atimersOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowrinnai33atimersOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}

            //Get Selected Text (timersmeridianon)
            ViewId_textview33c = ((TextView) v.findViewById(R.id.textView33));
            String[] dataSplit = ViewId_textview33c.getText().toString().split(" ");
            if (dataSplit[1].equals("AM")) {
                AppGlobals.selected_scrollviewrowrinnai33atimersmeridianon = 0;
            }
            if (dataSplit[1].equals("PM")) {
                AppGlobals.selected_scrollviewrowrinnai33atimersmeridianon = 1;
            }
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimersmeridianon:" + AppGlobals.selected_scrollviewrowrinnai33atimersmeridianon);

            //Get Selected Text (timershourson)
            ViewId_textview33a = ((TextView) v.findViewById(R.id.textView33));
            dataSplit = dataSplit[0].toString().split(":");
            AppGlobals.selected_scrollviewrowrinnai33atimershourson = Integer.parseInt(dataSplit[0], 10);
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimershourson:" + AppGlobals.selected_scrollviewrowrinnai33atimershourson);

            //Get Selected Text (timersminuteson)
            ViewId_textview33b = ((TextView) v.findViewById(R.id.textView33));
            AppGlobals.selected_scrollviewrowrinnai33atimersminuteson = Integer.parseInt(dataSplit[1], 10);
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimersminuteson:" + AppGlobals.selected_scrollviewrowrinnai33atimersminuteson);

            //Get Selected Text (timersmeridianoff)
            ViewId_textview34c = ((TextView) v.findViewById(R.id.textView34));
            dataSplit = ViewId_textview34c.getText().toString().split(" ");
            if (dataSplit[1].equals("AM")) {
                AppGlobals.selected_scrollviewrowrinnai33atimersmeridianoff = 0;
            }
            if (dataSplit[1].equals("PM")) {
                AppGlobals.selected_scrollviewrowrinnai33atimersmeridianoff = 1;
            }
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimersmeridianoff:" + AppGlobals.selected_scrollviewrowrinnai33atimersmeridianoff);

            //Get Selected Text (timershoursoff)
            ViewId_textview34a = ((TextView) v.findViewById(R.id.textView34));
            dataSplit = ViewId_textview34a.getText().toString().split(" ");
            dataSplit = dataSplit[0].toString().split(":");
            AppGlobals.selected_scrollviewrowrinnai33atimershoursoff = Integer.parseInt(dataSplit[0], 10);
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimershoursoff:" + AppGlobals.selected_scrollviewrowrinnai33atimershoursoff);

            //Get Selected Text (timersminutesoff)
            ViewId_textview34b = ((TextView) v.findViewById(R.id.textView34));
            AppGlobals.selected_scrollviewrowrinnai33atimersminutesoff = Integer.parseInt(dataSplit[1], 10);
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimersminutesoff:" + AppGlobals.selected_scrollviewrowrinnai33atimersminutesoff);

            ViewId_textview32 = ((TextView) v.findViewById(R.id.textView32));

            //Get Selected Text (timersdaysofweek)
            ViewId_textview66 = ((TextView) v.findViewById(R.id.textView66));
            scrollviewrowrinnai33atimers_id = Integer.parseInt(ViewId_textview66.getText().toString(), 10);
            AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek = AppGlobals.TimersInfo_List.get(scrollviewrowrinnai33atimers_id).timersDaysOfWeek;
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimersdaysofweek:" + AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek);

            //Get Selected Text (timerssettemperature)
            ViewId_textview31 = ((TextView) v.findViewById(R.id.textView31));
            dataSplit = ViewId_textview31.getText().toString().split("°");
            AppGlobals.selected_scrollviewrowrinnai33atimerssettemperature = Integer.parseInt(dataSplit[0], 10);
            Log.d("myApp", "Rinnai33aTimers_selected_scrollviewrowrinnai33atimerssettemperature:" + AppGlobals.selected_scrollviewrowrinnai33atimerssettemperature);

            //debug
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimershourson.getText() + "");
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimersminuteson.getText() + "");
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimershoursoff.getText() + "");
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimersminutesoff.getText() + "");
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimersdaysofweek.getText() + "");
            //Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + selected_scrollviewrowrinnai33atimerssettemperature.getText() + "");

            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview33a.getText() + "");
            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview33b.getText() + "");
            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview34a.getText() + "");
            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview34a.getText() + "");
            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview32.getText() + "");
            Log.d("myApp", "Rinnai33aTimers_scrollviewrowrinnai33atimersOnClickListener():" + ViewId_textview31.getText() + "");

            startupCheckTimer.cancel();
            isClosing = true;
            Intent intent = new Intent(Rinnai33aTimers.this, Rinnai33bTimers.class);
            intent.putExtra("selected_scrollviewrowrinnai33atimersid", scrollviewrowrinnai33atimers_id);
            intent.putExtra("isSelectedRow", true);
            startActivity(intent);

            finish();
            Log.d("myApp", "Rinnai33aTimers_onClick: startActivity(Rinnai33bTimers).");
        }
    };

    //****************************************//
    //***** viewidswitch3OnClickListener *****//
    //****************************************//

    private View.OnClickListener viewidswitch3OnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            SwitchCompat ViewId_switch3;

            Drawable ViewId_switch3_track_drawable;
            float[] ViewId_switch3_track_matrix;
            ColorFilter ViewId_switch3_track_colorFilter;
            Drawable ViewId_switch3_thumb_drawable;
            float[] ViewId_switch3_thumb_matrix;
            ColorFilter ViewId_switch3_thumb_colorFilter;

            //***** switch3 (Timers ON/OFF.) *****//
            ViewId_switch3 = (SwitchCompat) v.findViewById(R.id.switch3);

            // do something, the isChecked will be
            // true if the switch is in the On position
            if (ViewId_switch3.isChecked() == false) {
                Log.d("myApp", "ViewId_switch3 : OnClickListener(false)");

                //***** Switch - Track *****//
                ViewId_switch3_track_drawable = (Drawable) ViewId_switch3.getTrackDrawable();

                ViewId_switch3_track_matrix = new float[]{0, 0, 0, 0, 64,
                        0, 0, 0, 0, 64,
                        0, 0, 0, 0, 64,
                        0, 0, 0, 1, 0};

                ViewId_switch3_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_track_matrix);
                ViewId_switch3_track_drawable.setColorFilter(ViewId_switch3_track_colorFilter);

                //***** Switch - Thumb *****//
                ViewId_switch3_thumb_drawable = (Drawable) ViewId_switch3.getThumbDrawable();

                ViewId_switch3_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                        0, 0, 0, 0, 127,
                        0, 0, 0, 0, 127,
                        0, 0, 0, 1, 0};

                ViewId_switch3_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_thumb_matrix);
                ViewId_switch3_thumb_drawable.setColorFilter(ViewId_switch3_thumb_colorFilter);
            } else {
                Log.d("myApp", "ViewId_switch3 : OnClickListener(true)");

                //***** Switch - Track *****//
                ViewId_switch3_track_drawable = (Drawable) ViewId_switch3.getTrackDrawable();

                ViewId_switch3_track_matrix = new float[]{0, 0, 0, 0, 0,
                        0, 0, 0, 0, 191,
                        0, 0, 0, 0, 0,
                        0, 0, 0, 1, 0};

                ViewId_switch3_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_track_matrix);
                ViewId_switch3_track_drawable.setColorFilter(ViewId_switch3_track_colorFilter);

                //***** Switch - Thumb *****//
                ViewId_switch3_thumb_drawable = (Drawable) ViewId_switch3.getThumbDrawable();

                ViewId_switch3_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                        0, 0, 0, 0, 255,
                        0, 0, 0, 0, 255,
                        0, 0, 0, 1, 0};

                ViewId_switch3_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_thumb_matrix);
                ViewId_switch3_thumb_drawable.setColorFilter(ViewId_switch3_thumb_colorFilter);
            }

            try {

                updateScrollViewSwitchDetails();

                Tx_RN171DeviceSetTimers();

            } catch (Exception e) {

            }

        }
    };

    //****************************************//
    //***** viewidswitch3OnTouchListener *****//
    //****************************************//
    private View.OnTouchListener viewidswitch3OnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d("myApp", "ViewId_switch3 : OnTouchListener(false)");
            return event.getActionMasked() == MotionEvent.ACTION_MOVE;
        }
    };

    //*****************************************//
    //***** updateScrollViewSwitchDetails *****//
    //*****************************************//

    public void updateScrollViewSwitchDetails() {
        TableLayout ViewId_timersa_tableLayout = (TableLayout) findViewById(R.id.timersa_tableLayout);

        for (int a = 0; a <= AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal - 1; a++) {

            View ViewId_scrollview_row_switch_rinnai33a_timers = ViewId_timersa_tableLayout.getChildAt(a);

            SwitchCompat ViewId_switch3 = (SwitchCompat) ViewId_scrollview_row_switch_rinnai33a_timers.findViewById(R.id.switch3);

            if (ViewId_switch3.isChecked() == false) {

                AppGlobals.TimersInfo_List.get(a).timersOnOff = 0;

            } else {

                AppGlobals.TimersInfo_List.get(a).timersOnOff = 1;

            }

            Log.d("myApp", "Rinnai33aTimers_OnCheckedChangeListener: Timers On/Off (Switch" + a + "-" + AppGlobals.TimersInfo_List.get(a).timersOnOff + ")");
        }
    }

    //*************************************************************//
    //***** deletescrollviewrowrinnai33atimersOnClickListener *****//
    //*************************************************************//
    private String generateDaysOfWeekString(int daysOfWeek) {
        String daysOfWeekString = "";

        if ((daysOfWeek & 1) == 1) {
            daysOfWeekString = "Mon ";
        }
        if ((daysOfWeek & 2) == 2) {
            daysOfWeekString += "Tue ";
        }
        if ((daysOfWeek & 4) == 4) {
            daysOfWeekString += "Wed ";
        }
        if ((daysOfWeek & 8) == 8) {
            daysOfWeekString += "Thu ";
        }
        if ((daysOfWeek & 16) == 16) {
            daysOfWeekString += "Fri ";
        }
        if ((daysOfWeek & 32) == 32) {
            daysOfWeekString += "Sat ";
        }
        if ((daysOfWeek & 64) == 64) {
            daysOfWeekString += "Sun ";
        }
        return daysOfWeekString;
    }

    private Boolean isCurrentRunning(Timers_Info timerInfo) {
        DateFormat df = new SimpleDateFormat("EEE,HH,mm");
        String currentTime = df.format(Calendar.getInstance().getTime());
        String[] currentTimeArray = currentTime.split(",");
        String currentDayOfWeek = currentTimeArray[0];
        int currentHour = Integer.valueOf(currentTimeArray[1]);
        int currentMinute = Integer.valueOf(currentTimeArray[2]);

        //timersHoursOn and timersHoursOff bug
        int timersHoursOn = timerInfo.timersHoursOn;
        int timersHoursOff = timerInfo.timersHoursOff;
        if (timersHoursOn == 24) {
            timersHoursOn = 12;
        }
        if (timersHoursOff == 24) {
            timersHoursOff = 12;
        }

        if (timerInfo.timersOnOff == 1) {
            String daysOfWeekString = generateDaysOfWeekString(timerInfo.timersDaysOfWeek);
            if (daysOfWeekString.contains(currentDayOfWeek)) {
                if (currentHour > timersHoursOn || (currentHour == timersHoursOn && currentMinute >= timerInfo.timersMinutesOn)) {
                    if (currentHour < timersHoursOff || (currentHour == timersHoursOff && currentMinute <= timerInfo.timersMinutesOff)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Boolean isThereATimerRunning() {
        int numberOfTimers = AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal;
        for(int i=0; i<numberOfTimers; i++) {
            if (isCurrentRunning(AppGlobals.TimersInfo_List.get(i)) == true) {
                return true;
            }
        }
        return false;
    }

    private void deleteTimerAt(int index) {
        scrollviewrowrinnai33atimers_id = index;

        Tx_RN171DeviceDeleteTimers();

        //***** include - ViewId_include_waiting_timersa *****//
        ViewId_include_waiting_timersa = (ViewGroup) findViewById(R.id.include_waiting_timersa);

        ViewId_include_waiting_timersa.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_scrollview_lockout_timersa *****//
        ViewId_include_scrollview_lockout_timersa = (ViewGroup) findViewById(R.id.include_scrollview_lockout_timersa);

        ViewId_include_scrollview_lockout_timersa.setVisibility(View.VISIBLE);

        //***** Add New Button - ViewId_imagebutton12 *****//
        ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);

        ViewId_imagebutton12.setEnabled(false);
        ViewId_clearTimersImageButton.setEnabled(false);

        //***** TX WiFi - TCP *****//
        isDeviceGetTimers = false;
        startTxRN171DeviceGetTimers();
        Log.d("myApp", "Rinnai33aTimers_delete - Tx_RN171DeviceGetTimers");
    }

    private void clearTimers() {
        Tx_RN171DeviceClearTimers();

        //***** include - ViewId_include_waiting_timersa *****//
        ViewId_include_waiting_timersa = (ViewGroup) findViewById(R.id.include_waiting_timersa);

        ViewId_include_waiting_timersa.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_scrollview_lockout_timersa *****//
        ViewId_include_scrollview_lockout_timersa = (ViewGroup) findViewById(R.id.include_scrollview_lockout_timersa);

        ViewId_include_scrollview_lockout_timersa.setVisibility(View.VISIBLE);

        //***** Add New Button - ViewId_imagebutton12 *****//
        ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);

        ViewId_imagebutton12.setEnabled(false);
        ViewId_clearTimersImageButton.setEnabled(false);

        //***** TX WiFi - TCP *****//
        isDeviceGetTimers = false;
        startTxRN171DeviceGetTimers();
        Log.d("myApp", "Rinnai33aTimers_clear timers");
    }

    private View.OnClickListener deletescrollviewrowrinnai33atimersOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            final int index = Integer.valueOf(((TextView) v.findViewById(R.id.textView67)).getText().toString());

            if (isCurrentRunning(AppGlobals.TimersInfo_List.get(index)) == false) {
                deleteTimerAt(index);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(Rinnai33aTimers.this).create();
                alertDialog.setTitle("Warning Automatic OFF Function Disabled");
                alertDialog.setMessage("Please note that the unit running on this time. If you delete the timer you will have to operate the appliance manually.");

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTimerAt(index);
                            }
                        });
                alertDialog.show();
            }
        }
    };

    //*****************************************************//
    //***** scrollviewrowrinnai33atimersSwipeListener *****//
    //*****************************************************//

    private SwipeRevealLayout.SwipeListener scrollviewrowrinnai33atimersSwipeListener = new SwipeRevealLayout.SimpleSwipeListener() {
        @Override
        public void onClosed(SwipeRevealLayout view) {
            Log.d("myApp", "onClosed");
        }

        @Override
        public void onOpened(SwipeRevealLayout view) {
            Log.d("myApp", "onOpened");

            View ViewId_selectedtimerrowlayout = view.findViewById(R.id.timerrow_layout);

            String ViewId_selectedtimerrowlayout_text = ((TextView) ViewId_selectedtimerrowlayout.findViewById(R.id.textView66)).getText().toString();

            Log.d("myApp", "Selected View:" + ViewId_selectedtimerrowlayout_text);

            for (int a = 0; a <= AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal - 1; a++) {

                View ViewId_scrollview_row_switch_rinnai33a_timers = ViewId_timersa_tableLayout.getChildAt(a);

                View ViewId_timerrowlayout = ViewId_scrollview_row_switch_rinnai33a_timers.findViewById(R.id.timerrow_layout);

                String ViewId_timerrowlayout_text = ((TextView) ViewId_timerrowlayout.findViewById(R.id.textView66)).getText().toString();

                Log.d("myApp", "View:" + ViewId_timerrowlayout_text);

                if (ViewId_selectedtimerrowlayout_text != ViewId_timerrowlayout_text) {
                    ((SwipeRevealLayout) ViewId_scrollview_row_switch_rinnai33a_timers).close(true);
                }
            }
        }

        @Override
        public void onSlide(SwipeRevealLayout view, float slideOffset) {
            Log.d("myApp", "onSlide: " + slideOffset);
        }
    };

    //************************//
    //***** TCP - Client *****//
    //************************//

    @Override
    public void clientCallBackTCP(String commandID, String text) {
        final String pType = commandID;
        final String pText = text;

        if (isClosing == true) {
            return;
        }

        if (commandID != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (pType.contains("21")) {

                            ViewId_timersa_tableLayout = (TableLayout) findViewById(R.id.timersa_tableLayout);
                            //tl.setOnTouchListener(new AutoTimerTableTouchListener());

                            //clear the table, start with blank table
                            ViewId_timersa_tableLayout.removeAllViews();

                            int id = 0;

                            //tmrstotal limit check
                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal <= 8) {

                                for (int i = 0; i <= AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal - 1; i++) {
                                    String tmrsMeridianOn = "";
                                    String tmrsMeridianOff = "";
                                    String tmrsDaysOfWeek = "";

                                    int tmrsHoursOn = 0;
                                    int tmrsHoursOff = 0;

                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers HoursOn (" + AppGlobals.TimersInfo_List.get(i).timersHoursOn + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers MinutesOn (" + AppGlobals.TimersInfo_List.get(i).timersMinutesOn + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers HoursOff (" + AppGlobals.TimersInfo_List.get(i).timersHoursOff + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers MinutesOff (" + AppGlobals.TimersInfo_List.get(i).timersMinutesOff + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers DaysOfWeek (" + AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers OperationMode (" + AppGlobals.TimersInfo_List.get(i).timersOperationMode + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers SetTemperature (" + AppGlobals.TimersInfo_List.get(i).timersSetTemperature + ")");
                                    Log.d("myApp_WiFiTCP", "Rinnai33aTimers_clientCallBackTCP: Timers OnOff (" + AppGlobals.TimersInfo_List.get(i).timersOnOff + ")");

                                    ViewId_scrollview_row_rinnai33a_timers = getLayoutInflater().inflate(R.layout.scrollview_row_rinnai33a_timers, null, false);

                                    //ViewId_scrollview_row_rinnai33a_timers.setId(id);
                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView66)).setText(id + "");
                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView67)).setText(id + "");

                                    //ViewId_scrollview_row_rinnai33a_timers.setMinimumHeight(125);

                                    //clean text
                                    //nextSSID = ""+nextSSID.subSequence(nextSSID.indexOf("\"")+1,nextSSID.indexOf("\"",1));

                                    //add listener
                                    //ViewId_scrollview_row_rinnai33a_timers.setOnClickListener(scrollviewrowrinnai33atimersOnClickListener);//add OnClickListener Here

                                    ((SwipeRevealLayout) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.swipe_layout)).setSwipeListener(scrollviewrowrinnai33atimersSwipeListener);

                                    ((FrameLayout) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.delete_layout)).setOnClickListener(deletescrollviewrowrinnai33atimersOnClickListener);

                                    ((FrameLayout) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.timerrow_layout)).setOnClickListener(scrollviewrowrinnai33atimersOnClickListener);

                                    ((SwitchCompat) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.switch3)).setOnClickListener(viewidswitch3OnClickListener);

                                    ((SwitchCompat) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.switch3)).setOnTouchListener(viewidswitch3OnTouchListener);

                                    //set it on the row - textView33, timersHoursOn & timersMinutesOn
                                    tmrsHoursOn = AppGlobals.TimersInfo_List.get(i).timersHoursOn;

                                    if (tmrsHoursOn >= 12) {
                                        tmrsHoursOn = tmrsHoursOn - 12;

                                        if (tmrsHoursOn == 0) {
                                            tmrsHoursOn = 12;
                                        }

                                        tmrsMeridianOn = "PM";
                                    } else {

                                        if (tmrsHoursOn == 0) {
                                            tmrsHoursOn = 12;
                                        }

                                        tmrsMeridianOn = "AM";
                                    }

                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView33)).setText(tmrsHoursOn + "" + ":" + String.format("%02d", AppGlobals.TimersInfo_List.get(i).timersMinutesOn) + "" + " " + tmrsMeridianOn);

                                    //set it on the row - textView34, timersHoursOff & timersMinutesOff
                                    tmrsHoursOff = AppGlobals.TimersInfo_List.get(i).timersHoursOff;

                                    if (tmrsHoursOff >= 12) {
                                        tmrsHoursOff = tmrsHoursOff - 12;

                                        if (tmrsHoursOff == 0) {
                                            tmrsHoursOff = 12;
                                        }

                                        tmrsMeridianOff = "PM";
                                    } else {

                                        if (tmrsHoursOff == 0) {
                                            tmrsHoursOff = 12;
                                        }

                                        tmrsMeridianOff = "AM";
                                    }

                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView34)).setText(tmrsHoursOff + "" + ":" + String.format("%02d", AppGlobals.TimersInfo_List.get(i).timersMinutesOff) + "" + " " + tmrsMeridianOff);

                                    //set it on the row - textView32, timersDaysOfWeek
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 1) == 1) {
                                        tmrsDaysOfWeek = "Mon ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 2) == 2) {
                                        tmrsDaysOfWeek += "Tue ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 4) == 4) {
                                        tmrsDaysOfWeek += "Wed ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 8) == 8) {
                                        tmrsDaysOfWeek += "Thu ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 16) == 16) {
                                        tmrsDaysOfWeek += "Fri ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 32) == 32) {
                                        tmrsDaysOfWeek += "Sat ";
                                    }
                                    if ((AppGlobals.TimersInfo_List.get(i).timersDaysOfWeek & 64) == 64) {
                                        tmrsDaysOfWeek += "Sun ";
                                    }

                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView32)).setText(tmrsDaysOfWeek);

                                    //set it on the row - textView31, timersSetTemperature
                                    ((TextView) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.textView31)).setText(AppGlobals.TimersInfo_List.get(i).timersSetTemperature + "" + "°");

                                    //***** switch3 (Timers ON/OFF.) *****//
                                    ViewId_switch3 = (SwitchCompat) ViewId_scrollview_row_rinnai33a_timers.findViewById(R.id.switch3);

                                    switch (AppGlobals.TimersInfo_List.get(i).timersOnOff) {
                                        case 0:
                                            //***** switch3 (Timers ON/OFF. - OFF) *****//
                                            //***** Switch - Off Position *****//
                                            ViewId_switch3.setChecked(false);

                                            //***** Switch - Track *****//
                                            ViewId_switch3_track_drawable = (Drawable) ViewId_switch3.getTrackDrawable();

                                            ViewId_switch3_track_matrix = new float[]{0, 0, 0, 0, 64,
                                                    0, 0, 0, 0, 64,
                                                    0, 0, 0, 0, 64,
                                                    0, 0, 0, 1, 0};

                                            ViewId_switch3_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_track_matrix);
                                            ViewId_switch3_track_drawable.setColorFilter(ViewId_switch3_track_colorFilter);

                                            //***** Switch - Thumb *****//
                                            ViewId_switch3_thumb_drawable = (Drawable) ViewId_switch3.getThumbDrawable();

                                            ViewId_switch3_thumb_matrix = new float[]{0, 0, 0, 0, 127,
                                                    0, 0, 0, 0, 127,
                                                    0, 0, 0, 0, 127,
                                                    0, 0, 0, 1, 0};

                                            ViewId_switch3_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_thumb_matrix);
                                            ViewId_switch3_thumb_drawable.setColorFilter(ViewId_switch3_thumb_colorFilter);
                                            break;
                                        case 1:
                                            //***** switch3 (Timers ON/OFF. - ON) *****//
                                            //***** Switch - On Position *****//
                                            ViewId_switch3.setChecked(true);

                                            //***** Switch - Track *****//
                                            ViewId_switch3_track_drawable = (Drawable) ViewId_switch3.getTrackDrawable();

                                            ViewId_switch3_track_matrix = new float[]{0, 0, 0, 0, 0,
                                                    0, 0, 0, 0, 191,
                                                    0, 0, 0, 0, 0,
                                                    0, 0, 0, 1, 0};

                                            ViewId_switch3_track_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_track_matrix);
                                            ViewId_switch3_track_drawable.setColorFilter(ViewId_switch3_track_colorFilter);

                                            //***** Switch - Thumb *****//
                                            ViewId_switch3_thumb_drawable = (Drawable) ViewId_switch3.getThumbDrawable();

                                            ViewId_switch3_thumb_matrix = new float[]{0, 0, 0, 0, 255,
                                                    0, 0, 0, 0, 255,
                                                    0, 0, 0, 0, 255,
                                                    0, 0, 0, 1, 0};

                                            ViewId_switch3_thumb_colorFilter = new ColorMatrixColorFilter(ViewId_switch3_thumb_matrix);
                                            ViewId_switch3_thumb_drawable.setColorFilter(ViewId_switch3_thumb_colorFilter);
                                            break;
                                        default:
                                            break;
                                    }

                                    //Add the Row to the table
                                    ViewId_timersa_tableLayout.addView(ViewId_scrollview_row_rinnai33a_timers);

                                    //Next
                                    id++;
                                }

                            } else {
                                AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal = 0;
                            }

                            //***** include - ViewId_include_waiting_timersa *****//
                            ViewId_include_waiting_timersa = (ViewGroup) findViewById(R.id.include_waiting_timersa);

                            ViewId_include_waiting_timersa.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_scrollview_lockout_timersa *****//
                            ViewId_include_scrollview_lockout_timersa = (ViewGroup) findViewById(R.id.include_scrollview_lockout_timersa);

                            ViewId_include_scrollview_lockout_timersa.setVisibility(View.INVISIBLE);

                            //***** TableLayout - ViewId_timersa_tableLayout *****//
                            ViewId_timersa_tableLayout = (TableLayout) findViewById(R.id.timersa_tableLayout);

                            ViewId_timersa_tableLayout.setVisibility(View.VISIBLE);

                            //***** Add New Button - ViewId_imagebutton12 *****//
                            ViewId_imagebutton12 = (Button) findViewById(R.id.imageButton12);

                            ViewId_imagebutton12.setEnabled(true);
                            ViewId_clearTimersImageButton.setEnabled(true);

                            isDeviceGetTimers = true;

                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai33aTimers: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai33aTimers: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai33aTimers: clientCallBackTCP");
    }

    //***** RN171_DEVICE_GET_TIMERS *****//
    public void Tx_RN171DeviceGetTimers() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_21,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai33aTimers: Tx_RN171DeviceGetStatus(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_TIMERS *****//
    public void Tx_RN171DeviceSetTimers() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_31," +
                            String.format("%02X", AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).tmrstotal) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(0).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(1).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(2).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(3).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(4).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(5).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(6).timersOnOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersHoursOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersMinutesOn) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersHoursOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersMinutesOff) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersDaysOfWeek) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersOperationMode) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersSetTemperature) + "," +
                            String.format("%02X", AppGlobals.TimersInfo_List.get(7).timersOnOff) + "," +
                            "E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai33aTimers: Tx_RN171DeviceSetTimers(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_DELETE_TIMERS *****//
    public void Tx_RN171DeviceDeleteTimers() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_3A," + String.format("%02X", scrollviewrowrinnai33atimers_id) + "," + "E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai33aTimers: Tx_RN171DeviceDeleteTimers(Exception - " + e + ")");
        }
    }

    private void Tx_RN171DeviceClearTimers() {
        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_51," + "E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai33aTimers: Tx_RN171DeviceClearTimers(Exception - " + e + ")");
        }
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Login
    //public void goToActivity_Rinnai17_Login(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai17Login.class);
    //    startActivity(intent);
    //}

    //Home Screen (Finish)
    //public void goToActivity_Rinnai21_Home_Screen_Finish(View view) {
    //    frameAnimation.stop();
    //    for (int i = 0; i < frameAnimation.getNumberOfFrames(); ++i){
    //        Drawable frame = frameAnimation.getFrame(i);
    //        if (frame instanceof BitmapDrawable) {
    //            ((BitmapDrawable)frame).getBitmap().recycle();
    //        }
    //        frame.setCallback(null);
    //    }
    //    frameAnimation.setCallback(null);
    //
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //    finish();
    //}

    //Home Screen
    public void goToActivity_Rinnai21_Home_Screen(View view) {
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai21HomeScreen.class);
        startActivity(intent);
    }

    //Timers a - Scheduled Timers
    //public void goToActivity_Rinnai33a_Timers(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33aTimers.class);
    //    startActivity(intent);
    //}

    //Timers b - Scheduled Timer
    public void goToActivity_Rinnai33b_Timers(View view) {
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai33bTimers.class);
        startActivity(intent);
    }

    //Visit Rinnai
    //public void goToActivity_Rinnai35_Visit_Rinnai (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai35VisitRinnai.class);
    //    startActivity(intent);
    //}

    //Visit Rinnai - Website
    //public void goToActivity_Rinnai35_Visit_Rinnai_Website (View view){
    //    String locale = this.getResources().getConfiguration().locale.getCountry();
    //    Log.d("myApp", "Locale: " + locale);
    //
    //    String url;
    //    if(locale.equals("AU")){
    //        url = "http://rinnai.com.au/";
    //
    //        try {
    //            Intent i = new Intent("android.intent.action.MAIN");
    //            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //            i.addCategory("android.intent.category.LAUNCHER");
    //            i.setData(Uri.parse(url));
    //            startActivity(i);
    //        }
    //        catch(ActivityNotFoundException e) {
    //            // Chrome is not installed
    //            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //            startActivity(i);
    //        }
    //    }
    //    else if(locale.equals("NZ")){
    //        url = "https://rinnai.co.nz/";
    //
    //        try {
    //            Intent i = new Intent("android.intent.action.MAIN");
    //            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //            i.addCategory("android.intent.category.LAUNCHER");
    //            i.setData(Uri.parse(url));
    //            startActivity(i);
    //        }
    //        catch(ActivityNotFoundException e) {
    //            // Chrome is not installed
    //            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //            startActivity(i);
    //        }
    //    }
    //    else{
    //        Toast.makeText(this, "Rinnai Website not supported in your region.",
    //                Toast.LENGTH_LONG).show();
    //    }
    //}

    //Visit Rinnai - Facebook
    //public void goToActivity_Rinnai35_Visit_Rinnai_Facebook (View view){
    //    String url = "https://www.facebook.com/";
    //    try {
    //        Intent i = new Intent("android.intent.action.MAIN");
    //        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //        i.addCategory("android.intent.category.LAUNCHER");
    //        i.setData(Uri.parse(url));
    //        startActivity(i);
    //    }
    //    catch(ActivityNotFoundException e) {
    //        // Chrome is not installed
    //        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //        startActivity(i);
    //    }
    //}

    //Visit Rinnai - Youtube
    //public void goToActivity_Rinnai35_Visit_Rinnai_Youtube (View view){
    //    String url = "https://www.youtube.com/";
    //    try {
    //        Intent i = new Intent("android.intent.action.MAIN");
    //        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
    //        i.addCategory("android.intent.category.LAUNCHER");
    //        i.setData(Uri.parse(url));
    //        startActivity(i);
    //    }
    //    catch(ActivityNotFoundException e) {
    //        // Chrome is not installed
    //        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //        startActivity(i);
    //    }
    //}

    //Visit Rinnai - Phone
    //public void goToActivity_Rinnai35_Visit_Rinnai_Phone (View view){
    //    String locale = this.getResources().getConfiguration().locale.getCountry();
    //    Log.d("myApp", "Locale: " + locale);
    //
    //    if(locale.equals("AU")){
    //        Intent intent = new Intent(Intent.ACTION_DIAL);
    //        intent.setData(Uri.parse("tel:1300555545"));
    //        startActivity(intent);
    //    }
    //    else if(locale.equals("NZ")){
    //        Intent intent = new Intent(Intent.ACTION_DIAL);
    //        intent.setData(Uri.parse("tel:0800746624"));
    //        startActivity(intent);
    //    }
    //    else{
    //        Toast.makeText(this, "Rinnai Phone not supported in your region.",
    //                Toast.LENGTH_LONG).show();
    //    }
    //}

    //Lighting
    //public void goToActivity_Rinnai34_Lighting (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai34Lighting.class);
    //    startActivity(intent);
    //}

    //Network
    //public void goToActivity_Rinnai37_Network (View view){
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai37Network.class);
    //    startActivity(intent);
    //}

    //Fault
    //public void goToActivity_Rinnai26_Fault(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26Fault.class);
    //    startActivity(intent);
    //}

    //Fault - Service Fault Codes
    //public void goToActivity_Rinnai33_Service_Fault_Codes(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33ServiceFaultCodes.class);
    //    startActivity(intent);
    //}

    //Power Off
    //public void goToActivity_Rinnai26_Power_Off(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26PowerOff.class);
    //    startActivity(intent);
    //}

    //Ignition Sequence
    //public void goToActivity_Rinnai22_Ignition_Sequence(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai22IgnitionSequence.class);
    //    startActivity(intent);
    //}

}
