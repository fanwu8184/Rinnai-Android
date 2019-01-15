package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Timer;

/**
 * Created by jconci on 26/09/2017.
 */

public class Rinnai33bTimers extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    NumberPicker ViewId_numberpicker;
    NumberPicker ViewId_numberpicker2;
    NumberPicker ViewId_numberpicker3;
    NumberPicker ViewId_numberpicker4;
    NumberPicker ViewId_numberpicker5;
    NumberPicker ViewId_numberpicker6;
    NumberPicker ViewId_numberpicker7;

    ImageButton ViewId_imagebutton13;
    ImageButton ViewId_imagebutton14;
    ImageButton ViewId_imagebutton15;
    ImageButton ViewId_imagebutton16;
    ImageButton ViewId_imagebutton17;
    ImageButton ViewId_imagebutton18;
    ImageButton ViewId_imagebutton19;
    ImageButton ViewId_imagebutton20;

    TextView ViewId_textview36;

    //Timer startupCheckTimer;
    //int startupCheckTimerCount;

    int selected_scrollviewrowrinnai33btimersid;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai33b_timers);

        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onCreate.");

        selected_scrollviewrowrinnai33btimersid = getIntent().getExtras().getInt("selected_scrollviewrowrinnai33atimersid");

        //************************//
        //***** NumberPicker *****//
        //************************//

        //include - numberPicker (numberpicker_settemp_rinnai33b_timers)
        ViewId_numberpicker = (NumberPicker) findViewById(R.id.numberPicker);
        String[] nums = new String[15];
        for (int i = 0; i < nums.length; i++)
            nums[i] = Integer.toString(i + 16) + "°";

        ViewId_numberpicker.setMinValue(16);
        ViewId_numberpicker.setMaxValue(30);
        ViewId_numberpicker.setWrapSelectorWheel(false);
        ViewId_numberpicker.setDisplayedValues(nums);
        ViewId_numberpicker.setValue(AppGlobals.selected_scrollviewrowrinnai33atimerssettemperature);
        setNumberPickerTextColor(ViewId_numberpicker, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker, Color.parseColor("#FF000000"));

        //include - numberPicker2 - Hours (timepicker_on_rinnai33b_timers)
        ViewId_numberpicker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        String[] nums2 = new String[12];
        for (int j = 0; j < nums2.length; j++)
            nums2[j] = Integer.toString(j + 1);

        ViewId_numberpicker2.setMinValue(1);
        ViewId_numberpicker2.setMaxValue(12);
        ViewId_numberpicker2.setWrapSelectorWheel(true);
        ViewId_numberpicker2.setDisplayedValues(nums2);
        ViewId_numberpicker2.setValue(AppGlobals.selected_scrollviewrowrinnai33atimershourson);
        setNumberPickerTextColor(ViewId_numberpicker2, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker2, Color.parseColor("#FF000000"));

        //include - numberPicker3 - Minutes (timepicker_on_rinnai33b_timers)
        ViewId_numberpicker3 = (NumberPicker) findViewById(R.id.numberPicker3);
        String[] nums3 = new String[60];
        for (int k = 0; k < nums3.length; k++) {
            nums3[k] = String.format("%02d", k);
        }

        ViewId_numberpicker3.setMinValue(00);
        ViewId_numberpicker3.setMaxValue(59);
        ViewId_numberpicker3.setWrapSelectorWheel(true);
        ViewId_numberpicker3.setDisplayedValues(nums3);
        ViewId_numberpicker3.setValue(AppGlobals.selected_scrollviewrowrinnai33atimersminuteson);
        setNumberPickerTextColor(ViewId_numberpicker3, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker3, Color.parseColor("#FF000000"));

        //include - numberPicker4 - Merdian (timepicker_on_rinnai33b_timers)
        ViewId_numberpicker4 = (NumberPicker) findViewById(R.id.numberPicker4);
        String[] nums4 = new String[2];
        nums4[0] = String.format("am");
        nums4[1] = String.format("pm");


        ViewId_numberpicker4.setMinValue(0);
        ViewId_numberpicker4.setMaxValue(1);
        ViewId_numberpicker4.setWrapSelectorWheel(false);
        ViewId_numberpicker4.setDisplayedValues(nums4);
        ViewId_numberpicker4.setValue(AppGlobals.selected_scrollviewrowrinnai33atimersmeridianon);
        setNumberPickerTextColor(ViewId_numberpicker4, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker4, Color.parseColor("#FF000000"));

        //include - numberPicker5 - Hours (timepicker_off_rinnai33b_timers)
        ViewId_numberpicker5 = (NumberPicker) findViewById(R.id.numberPicker5);
        String[] nums5 = new String[12];
        for (int l = 0; l < nums5.length; l++)
            nums5[l] = Integer.toString(l + 1);

        ViewId_numberpicker5.setMinValue(1);
        ViewId_numberpicker5.setMaxValue(12);
        ViewId_numberpicker5.setWrapSelectorWheel(true);
        ViewId_numberpicker5.setDisplayedValues(nums5);
        ViewId_numberpicker5.setValue(AppGlobals.selected_scrollviewrowrinnai33atimershoursoff);
        setNumberPickerTextColor(ViewId_numberpicker5, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker5, Color.parseColor("#FF000000"));

        //include - numberPicker6 - Minutes (timepicker_off_rinnai33b_timers)
        ViewId_numberpicker6 = (NumberPicker) findViewById(R.id.numberPicker6);
        String[] nums6 = new String[60];
        for (int m = 0; m < nums6.length; m++) {
            nums6[m] = String.format("%02d", m);
        }

        ViewId_numberpicker6.setMinValue(00);
        ViewId_numberpicker6.setMaxValue(59);
        ViewId_numberpicker6.setWrapSelectorWheel(true);
        ViewId_numberpicker6.setDisplayedValues(nums6);
        ViewId_numberpicker6.setValue(AppGlobals.selected_scrollviewrowrinnai33atimersminutesoff);
        setNumberPickerTextColor(ViewId_numberpicker6, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker6, Color.parseColor("#FF000000"));

        //include - numberPicker7 - Merdian (timepicker_off_rinnai33b_timers)
        ViewId_numberpicker7 = (NumberPicker) findViewById(R.id.numberPicker7);
        String[] nums7 = new String[2];
        nums7[0] = String.format("am");
        nums7[1] = String.format("pm");


        ViewId_numberpicker7.setMinValue(0);
        ViewId_numberpicker7.setMaxValue(1);
        ViewId_numberpicker7.setWrapSelectorWheel(false);
        ViewId_numberpicker7.setDisplayedValues(nums7);
        ViewId_numberpicker7.setValue(AppGlobals.selected_scrollviewrowrinnai33atimersmeridianoff);
        setNumberPickerTextColor(ViewId_numberpicker7, Color.parseColor("#FFFFFFFF"));
        setDividerColor(ViewId_numberpicker7, Color.parseColor("#FF000000"));

        //*********************************//
        //***** Selected Days of Week *****//
        //*********************************//

        if(AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek <= 127){

            //***** set selected days of week - imagebutton14 (Day / Days, Monday) *****//
            ViewId_imagebutton14 = (ImageButton) findViewById(R.id.imageButton14);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 1) == 1) {
                ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday_white);
                AppGlobals.ViewId_imagebutton14_actionup = true;
            } else {
                ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday);
                AppGlobals.ViewId_imagebutton14_actionup = false;
            }

            //***** set selected days of week - imagebutton15 (Day / Days, Tuesday) *****//
            ViewId_imagebutton15 = (ImageButton) findViewById(R.id.imageButton15);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 2) == 2) {
                ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday_white);
                AppGlobals.ViewId_imagebutton15_actionup = true;
            } else {
                ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday);
                AppGlobals.ViewId_imagebutton15_actionup = false;
            }

            //***** set selected days of week - imagebutton16 (Day / Days, Wednesday) *****//
            ViewId_imagebutton16 = (ImageButton) findViewById(R.id.imageButton16);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 4) == 4) {
                ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday_white);
                AppGlobals.ViewId_imagebutton16_actionup = true;
            } else {
                ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday);
                AppGlobals.ViewId_imagebutton16_actionup = false;
            }

            //***** set selected days of week - imagebutton17 (Day / Days, Thursday) *****//
            ViewId_imagebutton17 = (ImageButton) findViewById(R.id.imageButton17);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 8) == 8) {
                ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday_white);
                AppGlobals.ViewId_imagebutton17_actionup = true;
            } else {
                ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday);
                AppGlobals.ViewId_imagebutton17_actionup = false;
            }

            //***** set selected days of week - imagebutton18 (Day / Days, Friday) *****//
            ViewId_imagebutton18 = (ImageButton) findViewById(R.id.imageButton18);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 16) == 16) {
                ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday_white);
                AppGlobals.ViewId_imagebutton18_actionup = true;
            } else {
                ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday);
                AppGlobals.ViewId_imagebutton18_actionup = false;
            }

            //***** set selected days of week - imagebutton19 (Day / Days, Saturday) *****//
            ViewId_imagebutton19 = (ImageButton) findViewById(R.id.imageButton19);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 32) == 32) {
                ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday_white);
                AppGlobals.ViewId_imagebutton19_actionup = true;
            } else {
                ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday);
                AppGlobals.ViewId_imagebutton19_actionup = false;
            }

            //***** set selected days of week - imagebutton20 (Day / Days, Sunday) *****//
            ViewId_imagebutton20 = (ImageButton) findViewById(R.id.imageButton20);

            if ((AppGlobals.selected_scrollviewrowrinnai33atimersdaysofweek & 64) == 64) {
                ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday_white);
                AppGlobals.ViewId_imagebutton20_actionup = true;
            } else {
                ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday);
                AppGlobals.ViewId_imagebutton20_actionup = false;
            }

        } else {
            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = 0;
            AppGlobals.ViewId_imagebutton14_actionup = false;
            AppGlobals.ViewId_imagebutton15_actionup = false;
            AppGlobals.ViewId_imagebutton16_actionup = false;
            AppGlobals.ViewId_imagebutton17_actionup = false;
            AppGlobals.ViewId_imagebutton18_actionup = false;
            AppGlobals.ViewId_imagebutton19_actionup = false;
            AppGlobals.ViewId_imagebutton20_actionup = false;
        }

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - imagebutton13 (Save) *****//
        ViewId_imagebutton13 = (ImageButton) findViewById(R.id.imageButton13);
        ViewId_textview36 = (TextView) findViewById(R.id.textView36);

        ViewId_imagebutton13.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview36.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview36.setTextColor(Color.parseColor("#FFFFFFFF"));

                        if (ViewId_numberpicker4.getValue() == 1) {
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOn = ViewId_numberpicker2.getValue() + 12;
                        } else {

                            if (ViewId_numberpicker2.getValue() == 12) {
                                AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOn = ViewId_numberpicker2.getValue() - 12;
                            } else {
                                AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOn = ViewId_numberpicker2.getValue();
                            }
                        }

                        AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersMinutesOn = ViewId_numberpicker3.getValue();

                        if (ViewId_numberpicker7.getValue() == 1) {
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOff = ViewId_numberpicker5.getValue() + 12;
                        } else {

                            if (ViewId_numberpicker5.getValue() == 12) {
                                AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOff = ViewId_numberpicker5.getValue() - 12;
                            } else {
                                AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersHoursOff = ViewId_numberpicker5.getValue();
                            }
                        }

                        AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersMinutesOff = ViewId_numberpicker6.getValue();

                        AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersSetTemperature = ViewId_numberpicker.getValue();

                        AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersOperationMode = 0;

                        AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersOnOff = 0;

                        Tx_RN171DeviceSetTimers();

                        Intent intent = new Intent(Rinnai33bTimers.this, Rinnai33aTimers.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai33bTimers_onTouch: startActivity(Rinnai33aTimers).");

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview36.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton14 (Day / Days, Monday) *****//
        ViewId_imagebutton14 = (ImageButton) findViewById(R.id.imageButton14);

        ViewId_imagebutton14.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton14 (Day / Days, Monday) *****//
                        if (AppGlobals.ViewId_imagebutton14_actionup == false) {
                            ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday_white);
                            AppGlobals.ViewId_imagebutton14_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (1 << pos);
                        } else {
                            ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday);
                            AppGlobals.ViewId_imagebutton14_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(1 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton14.setImageResource(R.drawable.timersb_monday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton15 (Day / Days, Tuesday) *****//
        ViewId_imagebutton15 = (ImageButton) findViewById(R.id.imageButton15);

        ViewId_imagebutton15.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton15 (Day / Days, Tuesday) *****//
                        if (AppGlobals.ViewId_imagebutton15_actionup == false) {
                            ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday_white);
                            AppGlobals.ViewId_imagebutton15_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (2 << pos);
                        } else {
                            ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday);
                            AppGlobals.ViewId_imagebutton15_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(2 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton15.setImageResource(R.drawable.timersb_tuesday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton16 (Day / Days, wednesday) *****//
        ViewId_imagebutton16 = (ImageButton) findViewById(R.id.imageButton16);

        ViewId_imagebutton16.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton16 (Day / Days, wednesday) *****//
                        if (AppGlobals.ViewId_imagebutton16_actionup == false) {
                            ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday_white);
                            AppGlobals.ViewId_imagebutton16_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (4 << pos);
                        } else {
                            ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday);
                            AppGlobals.ViewId_imagebutton16_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(4 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton16.setImageResource(R.drawable.timersb_wednesday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton17 (Day / Days, Thursday) *****//
        ViewId_imagebutton17 = (ImageButton) findViewById(R.id.imageButton17);

        ViewId_imagebutton17.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton17 (Day / Days, Thursday) *****//
                        if (AppGlobals.ViewId_imagebutton17_actionup == false) {
                            ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday_white);
                            AppGlobals.ViewId_imagebutton17_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (8 << pos);
                        } else {
                            ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday);
                            AppGlobals.ViewId_imagebutton17_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(8 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton17.setImageResource(R.drawable.timersb_thursday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton18 (Day / Days, Friday) *****//
        ViewId_imagebutton18 = (ImageButton) findViewById(R.id.imageButton18);

        ViewId_imagebutton18.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton18 (Day / Days, Friday) *****//
                        if (AppGlobals.ViewId_imagebutton18_actionup == false) {
                            ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday_white);
                            AppGlobals.ViewId_imagebutton18_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (16 << pos);
                        } else {
                            ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday);
                            AppGlobals.ViewId_imagebutton18_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(16 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton18.setImageResource(R.drawable.timersb_friday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton19 (Day / Days, Saturday) *****//
        ViewId_imagebutton19 = (ImageButton) findViewById(R.id.imageButton19);

        ViewId_imagebutton19.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton19 (Day / Days, Saturday) *****//
                        if (AppGlobals.ViewId_imagebutton19_actionup == false) {
                            ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday_white);
                            AppGlobals.ViewId_imagebutton19_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (32 << pos);
                        } else {
                            ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday);
                            AppGlobals.ViewId_imagebutton19_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(32 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton19.setImageResource(R.drawable.timersb_saturday);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton20 (Day / Days, Sunday) *****//
        ViewId_imagebutton20 = (ImageButton) findViewById(R.id.imageButton20);

        ViewId_imagebutton20.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton20 (Day / Days, Sunday) *****//
                        if (AppGlobals.ViewId_imagebutton20_actionup == false) {
                            ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday_white);
                            AppGlobals.ViewId_imagebutton20_actionup = true;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek | (64 << pos);
                        } else {
                            ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday);
                            AppGlobals.ViewId_imagebutton20_actionup = false;
                            AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek = AppGlobals.TimersInfo_List.get(selected_scrollviewrowrinnai33btimersid).timersDaysOfWeek & ~(64 << pos);
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton20.setImageResource(R.drawable.timersb_sunday);
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onRestart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onStop.");

        //startupCheckTimer.cancel();
        //isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai33bTimers_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai33bTimers_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //************************************//
    //***** setNumberPickerTextColor *****//
    //************************************//

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.d("myApp", "setNumberPickerTextColor: ", e);
                } catch (IllegalAccessException e) {
                    Log.d("myApp", "setNumberPickerTextColor: ", e);
                } catch (IllegalArgumentException e) {
                    Log.d("myApp", "setNumberPickerTextColor: ", e);
                }
            }
        }
        return false;
    }

    //***************************//
    //***** setDividerColor *****//
    //***************************//

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    Log.d("myApp", "setDividerColor: ", e);
                } catch (Resources.NotFoundException e) {
                    Log.d("myApp", "setDividerColor: ", e);
                } catch (IllegalAccessException e) {
                    Log.d("myApp", "setDividerColor: ", e);
                }
                break;
            }
        }
    }

    //************************//
    //***** TCP - Client *****//
    //************************//

    @Override
    public void clientCallBackTCP(String commandID, String text) {
        final String pType = commandID;
        final String pText = text;

        //if (isClosing == true) {
        //    return;
        //}

        if (commandID != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai33bTimers: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai33bTimers: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai33bTimers: clientCallBackTCP");
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
            Log.d("myApp_WiFiTCP", "Rinnai33bTimers: Tx_RN171DeviceSetTimers(Exception - " + e + ")");
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
        //    startupCheckTimer.cancel();
        Intent intent = new Intent(this, Rinnai21HomeScreen.class);
        startActivity(intent);
    }

    //Timers a - Scheduled Timers
    public void goToActivity_Rinnai33a_Timers(View view) {
        //    startupCheckTimer.cancel();
        Intent intent = new Intent(this, Rinnai33aTimers.class);
        startActivity(intent);
    }

    //Timers b - Scheduled Timer
    //public void goToActivity_Rinnai33b_Timers(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33bTimers.class);
    //    startActivity(intent);
    //}

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
