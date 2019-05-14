package com.rinnai.fireplacewifimodulenz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.Timer;
import java.util.TimerTask;

import AWSmodule.AWSconnection;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public class Rinnai21HomeScreen extends MillecActivityBase
        implements ActivityClientInterfaceTCP, ActivityTimerInterface {

    DrawerLayout drawer;

    Button ViewId_button_navview2;
    Button ViewId_button_navview3;
    Button ViewId_button_navview4;
    Button ViewId_button_navview5;
    Button ViewId_button_navview7;
    Button ViewId_button;
    Button ViewId_button2;
    Button ViewId_button3;
    Button ViewId_button4;
    Button ViewId_button5;
    Button ViewId_button6;
    Button ViewId_button7;
    Button ViewId_button8;
    Button ViewId_button14;
    Button ViewId_button16;

    ImageButton ViewId_imagebutton;
    ImageButton ViewId_imagebutton2;
    ImageButton ViewId_imagebutton3;
    ImageButton ViewId_imagebutton22;
    ImageButton ViewId_imagebutton23;
    ImageButton ViewId_imagebutton23Standby;
    ImageButton ViewId_imagebutton30;

    ImageView ViewId_imageview;
    ImageView ViewId_imageview2;
    ImageView ViewId_imageview3;
    ImageView ViewId_imageview4;
    ImageView ViewId_imageview5;

    TextView ViewId_textview3;
    TextView ViewId_textview4;
    TextView ViewId_textview5;
    TextView ViewId_textview6;
    TextView ViewId_textview7;
    TextView ViewId_textview14;
    TextView ViewId_textview15;
    TextView ViewId_textview58;
    TextView ViewId_textview71;
    TextView ViewId_textview71Standby;
    TextView ViewId_textview75;
    TextView ViewId_textview80;
    TextView ViewId_textview81;
    TextView ViewId_textview85;

    VerticalSeekBar ViewId_myseekbar;
    VerticalSeekBar ViewId_myseekbar2;
    VerticalSeekBar ViewId_myseekbar3;
    VerticalSeekBar ViewId_myseekbar4;

    ViewGroup ViewId_include_content_home_screen;
    ViewGroup ViewId_include_button_flame_settemp;
    ViewGroup ViewId_include_showhints_flame;
    ViewGroup ViewId_include_showhints_settemp;
    ViewGroup ViewId_include_showhints_economy;
    ViewGroup ViewId_include_standby;
    ViewGroup ViewId_include_button_multiunit;
    ViewGroup ViewId_include_multiunit_lockout;
    ViewGroup ViewId_include_multiunit;
    ViewGroup ViewId_include_devicenameedit;
    ViewGroup ViewId_include_devicenameedit_lockout;
    ViewGroup ViewId_include_button_multiunit_standby;

    LinearLayout ViewId_linearlayout_seekbar_flame;
    LinearLayout ViewId_linearlayout_seekbar_settemp;
    LinearLayout ViewId_linearlayout_multiunit_row;
    LinearLayout ViewId_linearlayout_multiunit_devicenameedit;

    TableLayout ViewId_multiunit_tableLayout;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Intent intent;

    boolean isClosing = false;

    int seekbar_shadow_flamevalue = 0;
    int seekbar_shadow_settempvalue = 0;

    int scrollviewrowmultiunitrinnai21homescreen_id = 0;

    EditText ViewId_edittext2;
    EditText selected_edittextrinnai21devicename;
    boolean selected_edittext2firsttime = false;

    boolean scrollviewrowmultiunit_pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai21_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Permit external connection attempts
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("myApp_ActivityLifecycle", "Rinnai21HomeScreen_onCreate.");

        setRinnai21HomeScreen();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getActionBar().setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                setShowHints();

                setFlameSettempVisibility();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //***** layout_width - NavigationView *****//
        DrawerLayout ViewId_drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView ViewId_nav_view = (NavigationView) findViewById(R.id.nav_view);

        int width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.80);
        DrawerLayout.LayoutParams params_navview = (android.support.v4.widget.DrawerLayout.LayoutParams) ViewId_nav_view.getLayoutParams();
        params_navview.width = width_screen;
        ViewId_nav_view.setLayoutParams(params_navview);

        disableNavigationViewScrollbars(ViewId_nav_view);

        startCommunicationErrorFault();

        startTxRN171DeviceGetStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onRestart.");

        isClosing = false;

        startCommunicationErrorFault();

        startTxRN171DeviceGetStatus();

        selected_edittext2firsttime = false;

        //***** DeviceName edit (multiunit) - ViewId_edittext2 *****//
        ViewId_edittext2 = (EditText) findViewById(R.id.editText2);
        ViewId_edittext2.setInputType(InputType.TYPE_CLASS_TEXT);
        ViewId_edittext2.setText("Enter Heater Name");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onPause.");

        AppGlobals.CommErrorFault.stopTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai21Homescreen_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai21HomeScreen_onBackPressed.");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("myApp", "Rinnai21HomeScreen_onCreateOptionsMenu.");

        //********************************//
        //***** linearlayout_navview *****//
        //********************************//

        //***** layout_height - linearlayout_navview *****//
        LinearLayout ViewId_linearlayout_navview = (LinearLayout) findViewById(R.id.linearlayout_navview);

        int height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.30);
        LinearLayout.LayoutParams params_linearlayout_navview = (LinearLayout.LayoutParams) ViewId_linearlayout_navview.getLayoutParams();
        params_linearlayout_navview.height = height_screen;
        ViewId_linearlayout_navview.setLayoutParams(params_linearlayout_navview);

        //***** gravity - linearlayout_navview *****//
        ViewId_linearlayout_navview.setGravity(Gravity.BOTTOM);

        //***** layout_height - textview_navview *****//
        TextView ViewId_textview_navview = (TextView) findViewById(R.id.textview_navview);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_textview_navview = (LinearLayout.LayoutParams) ViewId_textview_navview.getLayoutParams();
        params_textview_navview.height = height_screen;
        ViewId_textview_navview.setLayoutParams(params_textview_navview);

        //***** layout_width - textview_navview *****//
        int width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_textview_navview = (LinearLayout.LayoutParams) ViewId_textview_navview.getLayoutParams();
        params_textview_navview.width = width_screen;
        ViewId_textview_navview.setLayoutParams(params_textview_navview);

        //***** layout_gravity - textview_navview *****//
        params_textview_navview.gravity = Gravity.CENTER;
        ViewId_textview_navview.setLayoutParams(params_textview_navview);

        //***** textColor - textview_navview *****//
        ViewId_textview_navview.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - textview_navview *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.05);
        ViewId_textview_navview.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - textview_navview *****//
        ViewId_textview_navview.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - textview_navview *****//
        float scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        int dpAsPixels = (int) (20 * scale + 0.5f);
        ViewId_textview_navview.setPadding(dpAsPixels, 0, 0, 0);

        //*********************************//
        //***** linearlayout_navview2 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview2 *****//
        LinearLayout ViewId_linearlayout_navview2 = (LinearLayout) findViewById(R.id.linearlayout_navview2);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.10);
        LinearLayout.LayoutParams params_linearlayout_navview2 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview2.getLayoutParams();
        params_linearlayout_navview2.height = height_screen;
        ViewId_linearlayout_navview2.setLayoutParams(params_linearlayout_navview2);

        //***** layout_height - button_navview2 (Timers) *****//
        ViewId_button_navview2 = (Button) findViewById(R.id.button_navview2);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_button_navview2 = (LinearLayout.LayoutParams) ViewId_button_navview2.getLayoutParams();
        params_button_navview2.height = height_screen;
        ViewId_button_navview2.setLayoutParams(params_button_navview2);

        //***** layout_width - button_navview2 (Timers) *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_button_navview2 = (LinearLayout.LayoutParams) ViewId_button_navview2.getLayoutParams();
        params_button_navview2.width = width_screen;
        ViewId_button_navview2.setLayoutParams(params_button_navview2);

        //***** layout_gravity - button_navview2 (Timers) *****//
        params_button_navview2.gravity = Gravity.CENTER;
        ViewId_button_navview2.setLayoutParams(params_button_navview2);

        //***** textColor - button_navview2 (Timers) *****//
        ViewId_button_navview2.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - button_navview2 (Timers) *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.03);
        ViewId_button_navview2.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - button_navview2 (Timers) *****//
        ViewId_button_navview2.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - button_navview2 (Timers) *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (25 * scale + 0.5f);
        ViewId_button_navview2.setPadding(dpAsPixels, 0, 0, 0);

        //*********************************//
        //***** linearlayout_navview3 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview3 *****//
        LinearLayout ViewId_linearlayout_navview3 = (LinearLayout) findViewById(R.id.linearlayout_navview3);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.10);
        LinearLayout.LayoutParams params_linearlayout_navview3 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview3.getLayoutParams();
        params_linearlayout_navview3.height = height_screen;
        ViewId_linearlayout_navview3.setLayoutParams(params_linearlayout_navview3);

        //***** layout_height - button_navview3 (Visit Rinnai) *****//
        ViewId_button_navview3 = (Button) findViewById(R.id.button_navview3);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_button_navview3 = (LinearLayout.LayoutParams) ViewId_button_navview3.getLayoutParams();
        params_button_navview3.height = height_screen;
        ViewId_button_navview3.setLayoutParams(params_button_navview3);

        //***** layout_width - button_navview3 (Visit Rinnai) *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_button_navview3 = (LinearLayout.LayoutParams) ViewId_button_navview3.getLayoutParams();
        params_button_navview3.width = width_screen;
        ViewId_button_navview3.setLayoutParams(params_button_navview3);

        //***** layout_gravity - button_navview3 (Visit Rinnai) *****//
        params_button_navview3.gravity = Gravity.CENTER;
        ViewId_button_navview3.setLayoutParams(params_button_navview3);

        //***** textColor - button_navview3 (Visit Rinnai) *****//
        ViewId_button_navview3.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - button_navview3 (Visit Rinnai) *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.03);
        ViewId_button_navview3.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - button_navview3 (Visit Rinnai) *****//
        ViewId_button_navview3.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - button_navview3 (Visit Rinnai) *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (25 * scale + 0.5f);
        ViewId_button_navview3.setPadding(dpAsPixels, 0, 0, 0);

        //*********************************//
        //***** linearlayout_navview4 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview4 *****//
        LinearLayout ViewId_linearlayout_navview4 = (LinearLayout) findViewById(R.id.linearlayout_navview4);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.10);
        LinearLayout.LayoutParams params_linearlayout_navview4 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview4.getLayoutParams();
        params_linearlayout_navview4.height = height_screen;
        ViewId_linearlayout_navview4.setLayoutParams(params_linearlayout_navview4);

        //***** layout_height - button_navview4 (Lighting) *****//
        ViewId_button_navview4 = (Button) findViewById(R.id.button_navview4);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_button_navview4 = (LinearLayout.LayoutParams) ViewId_button_navview4.getLayoutParams();
        params_button_navview4.height = height_screen;
        ViewId_button_navview4.setLayoutParams(params_button_navview4);

        //***** layout_width - button_navview4 (Lighting) *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_button_navview4 = (LinearLayout.LayoutParams) ViewId_button_navview4.getLayoutParams();
        params_button_navview4.width = width_screen;
        ViewId_button_navview4.setLayoutParams(params_button_navview4);

        //***** layout_gravity - button_navview4 (Lighting) *****//
        params_button_navview4.gravity = Gravity.CENTER;
        ViewId_button_navview4.setLayoutParams(params_button_navview4);

        //***** textColor - button_navview4 (Lighting) *****//
        ViewId_button_navview4.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - button_navview4 (Lighting) *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.03);
        ViewId_button_navview4.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - button_navview4 (Lighting) *****//
        ViewId_button_navview4.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - button_navview4 (Lighting) *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (25 * scale + 0.5f);
        ViewId_button_navview4.setPadding(dpAsPixels, 0, 0, 0);

        //*********************************//
        //***** linearlayout_navview5 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview5 *****//
        LinearLayout ViewId_linearlayout_navview5 = (LinearLayout) findViewById(R.id.linearlayout_navview5);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.10);
        LinearLayout.LayoutParams params_linearlayout_navview5 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview5.getLayoutParams();
        params_linearlayout_navview5.height = height_screen;
        ViewId_linearlayout_navview5.setLayoutParams(params_linearlayout_navview5);

        //***** layout_height - button_navview5 (Rinnai Account) *****//
        ViewId_button_navview5 = (Button) findViewById(R.id.button_navview5);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_button_navview5 = (LinearLayout.LayoutParams) ViewId_button_navview5.getLayoutParams();
        params_button_navview5.height = height_screen;
        ViewId_button_navview5.setLayoutParams(params_button_navview5);

        //***** layout_width - button_navview5 (Rinnai Account) *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_button_navview5 = (LinearLayout.LayoutParams) ViewId_button_navview5.getLayoutParams();
        params_button_navview5.width = width_screen;
        ViewId_button_navview5.setLayoutParams(params_button_navview5);

        //***** layout_gravity - button_navview5 (Rinnai Account) *****//
        params_button_navview5.gravity = Gravity.CENTER;
        ViewId_button_navview5.setLayoutParams(params_button_navview5);

        //***** textColor - button_navview5 (Rinnai Account) *****//
        ViewId_button_navview5.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - button_navview5 (Rinnai Account) *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.03);
        ViewId_button_navview5.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - button_navview5 (Rinnai Account) *****//
        ViewId_button_navview5.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - button_navview5 (Rinnai Account) *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (25 * scale + 0.5f);
        ViewId_button_navview5.setPadding(dpAsPixels, 0, 0, 0);

        //*********************************//
        //***** linearlayout_navview6 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview6 *****//
        LinearLayout ViewId_linearlayout_navview6 = (LinearLayout) findViewById(R.id.linearlayout_navview6);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.23);
        LinearLayout.LayoutParams params_linearlayout_navview6 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview6.getLayoutParams();
        params_linearlayout_navview6.height = height_screen;
        ViewId_linearlayout_navview6.setLayoutParams(params_linearlayout_navview6);

        //*********************************//
        //***** linearlayout_navview7 *****//
        //*********************************//

        //***** layout_height - linearlayout_navview7 *****//
        LinearLayout ViewId_linearlayout_navview7 = (LinearLayout) findViewById(R.id.linearlayout_navview7);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.05);
        LinearLayout.LayoutParams params_linearlayout_navview7 = (LinearLayout.LayoutParams) ViewId_linearlayout_navview7.getLayoutParams();
        params_linearlayout_navview7.height = height_screen;
        ViewId_linearlayout_navview7.setLayoutParams(params_linearlayout_navview7);

        //***** layout_height - button_navview7 (Show Hints) *****//
        ViewId_button_navview7 = (Button) findViewById(R.id.button_navview7);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        FrameLayout.LayoutParams params_button_navview7 = (FrameLayout.LayoutParams) ViewId_button_navview7.getLayoutParams();
        params_button_navview7.height = height_screen;
        ViewId_button_navview7.setLayoutParams(params_button_navview7);

        //***** layout_width - button_navview7 (Show Hints) *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.15);
        params_button_navview7 = (FrameLayout.LayoutParams) ViewId_button_navview7.getLayoutParams();
        params_button_navview7.width = width_screen;
        ViewId_button_navview7.setLayoutParams(params_button_navview7);

        //***** background - button_navview7 (Show Hints) *****//
        ViewId_button_navview7.setBackgroundColor(Color.parseColor("#00000000"));

        //***** layout_gravity - button_navview7 (Show Hints) *****//
        params_button_navview7.gravity = Gravity.CENTER_VERTICAL;
        ViewId_button_navview7.setLayoutParams(params_button_navview7);

        //***** layout_height - imageview_navview7 *****//
        final ImageView ViewId_imageview_navview7 = (ImageView) findViewById(R.id.imageview_navview7);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.075);
        LinearLayout.LayoutParams params_imageview_navview7 = (LinearLayout.LayoutParams) ViewId_imageview_navview7.getLayoutParams();
        params_imageview_navview7.height = height_screen;
        ViewId_imageview_navview7.setLayoutParams(params_imageview_navview7);

        //***** layout_width - imageview_navview7 *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.075);
        params_imageview_navview7 = (LinearLayout.LayoutParams) ViewId_imageview_navview7.getLayoutParams();
        params_imageview_navview7.width = width_screen;
        ViewId_imageview_navview7.setLayoutParams(params_imageview_navview7);

        //***** layout_gravity - imageview_navview7 *****//
        params_imageview_navview7.gravity = Gravity.CENTER_VERTICAL;
        ViewId_imageview_navview7.setLayoutParams(params_imageview_navview7);

        //***** layout_margin - imageview_navview7 *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (20 * scale + 0.5f);
        params_imageview_navview7 = (LinearLayout.LayoutParams) ViewId_imageview_navview7.getLayoutParams();
        params_imageview_navview7.leftMargin = dpAsPixels;
        ViewId_imageview_navview7.setLayoutParams(params_imageview_navview7);

        //***** layout_height - textview_navview7 *****//
        TextView ViewId_textview_navview7 = (TextView) findViewById(R.id.textview_navview7);

        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.095);
        LinearLayout.LayoutParams params_textview_navview7 = (LinearLayout.LayoutParams) ViewId_textview_navview7.getLayoutParams();
        params_textview_navview7.height = height_screen;
        ViewId_textview_navview7.setLayoutParams(params_textview_navview7);

        //***** layout_width - textview_navview7 *****//
        width_screen = (int) ((double) (getResources().getDisplayMetrics().widthPixels) * 0.75);
        params_textview_navview7 = (LinearLayout.LayoutParams) ViewId_textview_navview7.getLayoutParams();
        params_textview_navview7.width = width_screen;
        ViewId_textview_navview7.setLayoutParams(params_textview_navview7);

        //***** layout_gravity - textview_navview7 *****//
        params_textview_navview7.gravity = Gravity.CENTER;
        ViewId_textview_navview7.setLayoutParams(params_textview_navview7);

        //***** textColor - textview_navview7 *****//
        ViewId_textview_navview7.setTextColor(Color.parseColor("#FFFFFFFF"));

        //***** textSize - textview_navview7 *****//
        height_screen = (int) ((double) (getResources().getDisplayMetrics().heightPixels) * 0.03);
        ViewId_textview_navview7.setTextSize(COMPLEX_UNIT_PX, height_screen);

        //***** gravity - textview_navview7 *****//
        ViewId_textview_navview7.setGravity(Gravity.CENTER_VERTICAL);

        //***** Padding - textview_navview7 *****//
        scale = getResources().getDisplayMetrics().density;
        //int dpAsPixels = (int) (sizeInDp*scale + 0.5f);
        dpAsPixels = (int) (10 * scale + 0.5f);
        ViewId_textview_navview7.setPadding(dpAsPixels, 0, 0, 0);

        //***** srccompat - imageview_navview7 *****//
        if (AppGlobals.ViewId_imageview_navview7_actionup == false) {
            ViewId_imageview_navview7.setImageResource(R.drawable.checkbox1);
        } else {
            ViewId_imageview_navview7.setImageResource(R.drawable.checkbox2);
        }

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button_navview2 (Timers) *****//
        ViewId_button_navview2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button_navview2.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button_navview2.setTextColor(Color.parseColor("#FFFFFFFF"));
                        resetguardtimeRinnai21HomeScreen();
                        startupCheckTimer.cancel();
                        isClosing = true;
                        Intent intent = new Intent(Rinnai21HomeScreen.this, Rinnai33aTimers.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai21HomeScreen_onTouch: startActivity(Rinnai33aTimers).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button_navview2.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button_navview3 (Visit Rinnai) *****//
        ViewId_button_navview3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button_navview3.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button_navview3.setTextColor(Color.parseColor("#FFFFFFFF"));
                        resetguardtimeRinnai21HomeScreen();
                        startupCheckTimer.cancel();
                        isClosing = true;
                        Intent intent = new Intent(Rinnai21HomeScreen.this, Rinnai35VisitRinnai.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai21HomeScreen_onTouch: startActivity(Rinnai35VisitRinnai).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button_navview3.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button_navview4 (Lighting) *****//
        ViewId_button_navview4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button_navview4.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button_navview4.setTextColor(Color.parseColor("#FFFFFFFF"));
                        resetguardtimeRinnai21HomeScreen();
                        startupCheckTimer.cancel();
                        isClosing = true;
                        Intent intent = new Intent(Rinnai21HomeScreen.this, Rinnai34Lighting.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai21HomeScreen_onTouch: startActivity(Rinnai34Lighting).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button_navview4.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button_navview5 (Rinnai Account) *****//
        ViewId_button_navview5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button_navview5.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button_navview5.setTextColor(Color.parseColor("#FFFFFFFF"));
                        resetguardtimeRinnai21HomeScreen();
                        startupCheckTimer.cancel();
                        isClosing = true;
                        Intent intent = new Intent(Rinnai21HomeScreen.this, Rinnai11hRegistration.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai21HomeScreen_onTouch: startActivity(Rinnai11hRegistration).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button_navview5.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button_navview7 (Show Hints) *****//
        ViewId_button_navview7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imageview_navview7 *****//
                        AppGlobals.ViewId_imageview_navview7_actionup = !AppGlobals.ViewId_imageview_navview7_actionup;

                        if (AppGlobals.ViewId_imageview_navview7_actionup == false) {
                            ViewId_imageview_navview7.setImageResource(R.drawable.checkbox1);

                            //Economy function = OFF:[0x00]
                            //Economy function = ON:[0x01]
                            if (AppGlobals.ViewId_imagebutton2_actionup == false) {
                                AppGlobals.ShowHints_economy_actionvisible = false;
                                AppGlobals.ShowHints_flame_actionvisible = false;
                                AppGlobals.ShowHints_settemp_actionvisible = true;
                            } else {
                                AppGlobals.ShowHints_economy_actionvisible = true;
                                AppGlobals.ShowHints_flame_actionvisible = false;
                                AppGlobals.ShowHints_settemp_actionvisible = true;
                            }
                        } else {
                            ViewId_imageview_navview7.setImageResource(R.drawable.checkbox2);
                            AppGlobals.ShowHints_economy_actionvisible = false;
                            AppGlobals.ShowHints_flame_actionvisible = false;
                            AppGlobals.ShowHints_settemp_actionvisible = false;
                        }

                        AppGlobals.Button_flame_settemp_actionvisible = false;

                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton (Settings) *****//
        ViewId_imagebutton = (ImageButton) findViewById(R.id.imageButton);

        ViewId_imagebutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton.setImageResource(R.drawable.settings_press);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton (Settings) *****//
                        ViewId_imagebutton.setImageResource(R.drawable.settings);

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(Gravity.LEFT);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton.setImageResource(R.drawable.settings);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton2 (Economy) *****//
        ViewId_imagebutton2 = (ImageButton) findViewById(R.id.imageButton2);

        ViewId_imagebutton2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_imagebutton2.setImageResource(R.drawable.economy_press);

                        ViewId_imagebutton2.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton2 (Economy) *****//
                        AppGlobals.ViewId_imagebutton2_actionup = !AppGlobals.ViewId_imagebutton2_actionup;

                        //Economy function = OFF:[0x00]
                        //Economy function = ON:[0x01]
                        if (AppGlobals.ViewId_imagebutton2_actionup == false) {
                            ViewId_imagebutton2.setImageResource(R.drawable.economy);
                            AppGlobals.ShowHints_economy_actionvisible = false;
                            AppGlobals.ShowHints_flame_actionvisible = false;
                            AppGlobals.ShowHints_settemp_actionvisible = true;
                        } else {
                            ViewId_imagebutton2.setImageResource(R.drawable.economy_white_press);
                            AppGlobals.ShowHints_economy_actionvisible = true;
                            AppGlobals.ShowHints_flame_actionvisible = false;
                            AppGlobals.ShowHints_settemp_actionvisible = true;
                        }

                        setShowHints();

                        AppGlobals.Button_flame_settemp_actionvisible = false;

                        setFlameSettempVisibility();

                        ViewId_imagebutton2.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetEcon();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton2.setImageResource(R.drawable.economy);
                }
                return false;
            }
        });

        //***** OnTouchListener - button7 (Flame) *****//
        ViewId_button7 = (Button) findViewById(R.id.button7);

        ViewId_button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button7.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        AppGlobals.ShowHints_economy_actionvisible = false;
                        AppGlobals.ShowHints_flame_actionvisible = true;
                        AppGlobals.ShowHints_settemp_actionvisible = false;

                        setShowHints();

                        AppGlobals.Button_flame_settemp_actionvisible = true;

                        setFlameSettempVisibility();

                        ViewId_button7.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetFlame();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - button8 (Set Temp) *****//
        ViewId_button8 = (Button) findViewById(R.id.button8);

        ViewId_button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button8.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //Economy function = OFF:[0x00]
                        //Economy function = ON:[0x01]
                        if (AppGlobals.ViewId_imagebutton2_actionup == false) {
                            AppGlobals.ShowHints_economy_actionvisible = false;
                            AppGlobals.ShowHints_flame_actionvisible = false;
                            AppGlobals.ShowHints_settemp_actionvisible = true;
                        } else {
                            AppGlobals.ShowHints_economy_actionvisible = true;
                            AppGlobals.ShowHints_flame_actionvisible = false;
                            AppGlobals.ShowHints_settemp_actionvisible = true;
                        }

                        setShowHints();

                        AppGlobals.Button_flame_settemp_actionvisible = false;

                        setFlameSettempVisibility();

                        ViewId_button8.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetTemp();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - button (Seekbar Flame - Up) *****//
        ViewId_button = (Button) findViewById(R.id.button);
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_myseekbar = (VerticalSeekBar) findViewById(R.id.mySeekBar);

        ViewId_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** text - ViewId_textview6 *****//
                        if (AppGlobals.ViewId_textview6_flamevalue < 5) {
                            AppGlobals.ViewId_textview6_flamevalue++;
                        } else {
                            AppGlobals.ViewId_textview6_flamevalue = 5;
                        }
                        ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");

                        //***** seekbar - ViewId_myseekbar *****//
                        switch (AppGlobals.ViewId_textview6_flamevalue) {
                            case 1:
                                ViewId_myseekbar.setProgress(0);
                                break;
                            case 2:
                                ViewId_myseekbar.setProgress(25);
                                break;
                            case 3:
                                ViewId_myseekbar.setProgress(50);
                                break;
                            case 4:
                                ViewId_myseekbar.setProgress(75);
                                break;
                            case 5:
                                ViewId_myseekbar.setProgress(100);
                                break;
                            default:
                                break;
                        }

                        ViewId_button.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetFlame();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - button2 (Seekbar Flame - Down) *****//
        ViewId_button2 = (Button) findViewById(R.id.button2);
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_myseekbar = (VerticalSeekBar) findViewById(R.id.mySeekBar);

        ViewId_button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button2.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** text - ViewId_textview6 *****//
                        if (AppGlobals.ViewId_textview6_flamevalue > 1) {
                            AppGlobals.ViewId_textview6_flamevalue--;
                        } else {
                            AppGlobals.ViewId_textview6_flamevalue = 1;
                        }
                        ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");

                        //***** seekbar - ViewId_myseekbar *****//
                        switch (AppGlobals.ViewId_textview6_flamevalue) {
                            case 1:
                                ViewId_myseekbar.setProgress(0);
                                break;
                            case 2:
                                ViewId_myseekbar.setProgress(25);
                                break;
                            case 3:
                                ViewId_myseekbar.setProgress(50);
                                break;
                            case 4:
                                ViewId_myseekbar.setProgress(75);
                                break;
                            case 5:
                                ViewId_myseekbar.setProgress(100);
                                break;
                            default:
                                break;
                        }

                        ViewId_button2.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetFlame();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - myseekbar (Seekbar Flame - Seekbar) *****//
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_myseekbar = (VerticalSeekBar) findViewById(R.id.mySeekBar);

        ViewId_myseekbar.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //***** seekbar - ViewId_myseekbar *****//
                if (progress >= 0 && progress < 25) {
                    AppGlobals.ViewId_textview6_flamevalue = 1;
                    ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");
                } else if (progress >= 25 && progress < 50) {
                    AppGlobals.ViewId_textview6_flamevalue = 2;
                    ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");
                } else if (progress >= 50 && progress < 75) {
                    AppGlobals.ViewId_textview6_flamevalue = 3;
                    ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");
                } else if (progress >= 75 && progress < 100) {
                    AppGlobals.ViewId_textview6_flamevalue = 4;
                    ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");
                } else {
                    AppGlobals.ViewId_textview6_flamevalue = 5;
                    ViewId_textview6.setText("   " + String.valueOf(AppGlobals.ViewId_textview6_flamevalue) + "   ");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                enableguardtimeRinnai21HomeScreen();

                Tx_RN171DeviceSetFlame();
            }
        });

        //***** OnTouchListener - button3 (Seekbar Set Temp - Up) *****//
        ViewId_button3 = (Button) findViewById(R.id.button3);
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);
        ViewId_myseekbar2 = (VerticalSeekBar) findViewById(R.id.mySeekBar2);

        ViewId_button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button3.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** text - ViewId_textview7 *****//
                        if (AppGlobals.ViewId_textview7_settempvalue < 30) {
                            AppGlobals.ViewId_textview7_settempvalue++;
                        } else {
                            AppGlobals.ViewId_textview7_settempvalue = 30;
                        }
                        ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");

                        //***** seekbar - ViewId_myseekbar2 *****//
                        switch (AppGlobals.ViewId_textview7_settempvalue) {
                            case 16:
                                ViewId_myseekbar2.setProgress(0);
                                break;
                            case 17:
                                ViewId_myseekbar2.setProgress(7);
                                break;
                            case 18:
                                ViewId_myseekbar2.setProgress(14);
                                break;
                            case 19:
                                ViewId_myseekbar2.setProgress(21);
                                break;
                            case 20:
                                ViewId_myseekbar2.setProgress(29);
                                break;
                            case 21:
                                ViewId_myseekbar2.setProgress(36);
                                break;
                            case 22:
                                ViewId_myseekbar2.setProgress(43);
                                break;
                            case 23:
                                ViewId_myseekbar2.setProgress(50);
                                break;
                            case 24:
                                ViewId_myseekbar2.setProgress(57);
                                break;
                            case 25:
                                ViewId_myseekbar2.setProgress(64);
                                break;
                            case 26:
                                ViewId_myseekbar2.setProgress(71);
                                break;
                            case 27:
                                ViewId_myseekbar2.setProgress(79);
                                break;
                            case 28:
                                ViewId_myseekbar2.setProgress(86);
                                break;
                            case 29:
                                ViewId_myseekbar2.setProgress(93);
                                break;
                            case 30:
                                ViewId_myseekbar2.setProgress(100);
                                break;
                            default:
                                break;
                        }

                        ViewId_button3.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetTemp();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - button4 (Seekbar Set Temp - Down) *****//
        ViewId_button4 = (Button) findViewById(R.id.button4);
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);
        ViewId_myseekbar2 = (VerticalSeekBar) findViewById(R.id.mySeekBar2);

        ViewId_button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_button4.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** text - ViewId_textview7 *****//
                        if (AppGlobals.ViewId_textview7_settempvalue > 16) {
                            AppGlobals.ViewId_textview7_settempvalue--;
                        } else {
                            AppGlobals.ViewId_textview7_settempvalue = 16;
                        }
                        ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");

                        //***** seekbar - ViewId_myseekbar2 *****//
                        switch (AppGlobals.ViewId_textview7_settempvalue) {
                            case 16:
                                ViewId_myseekbar2.setProgress(0);
                                break;
                            case 17:
                                ViewId_myseekbar2.setProgress(7);
                                break;
                            case 18:
                                ViewId_myseekbar2.setProgress(14);
                                break;
                            case 19:
                                ViewId_myseekbar2.setProgress(21);
                                break;
                            case 20:
                                ViewId_myseekbar2.setProgress(29);
                                break;
                            case 21:
                                ViewId_myseekbar2.setProgress(36);
                                break;
                            case 22:
                                ViewId_myseekbar2.setProgress(43);
                                break;
                            case 23:
                                ViewId_myseekbar2.setProgress(50);
                                break;
                            case 24:
                                ViewId_myseekbar2.setProgress(57);
                                break;
                            case 25:
                                ViewId_myseekbar2.setProgress(64);
                                break;
                            case 26:
                                ViewId_myseekbar2.setProgress(71);
                                break;
                            case 27:
                                ViewId_myseekbar2.setProgress(79);
                                break;
                            case 28:
                                ViewId_myseekbar2.setProgress(86);
                                break;
                            case 29:
                                ViewId_myseekbar2.setProgress(93);
                                break;
                            case 30:
                                ViewId_myseekbar2.setProgress(100);
                                break;
                            default:
                                break;
                        }

                        ViewId_button4.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        Tx_RN171DeviceSetTemp();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - myseekbar2 (Seekbar Set Temp - Seekbar) *****//
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);
        ViewId_myseekbar2 = (VerticalSeekBar) findViewById(R.id.mySeekBar2);

        ViewId_myseekbar2.setOnSeekBarChangeListener(new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //***** seekbar - ViewId_myseekbar2 *****//
                if (progress >= 0 && progress < 7) {
                    AppGlobals.ViewId_textview7_settempvalue = 16;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 7 && progress < 14) {
                    AppGlobals.ViewId_textview7_settempvalue = 17;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 14 && progress < 21) {
                    AppGlobals.ViewId_textview7_settempvalue = 18;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 21 && progress < 29) {
                    AppGlobals.ViewId_textview7_settempvalue = 19;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 29 && progress < 36) {
                    AppGlobals.ViewId_textview7_settempvalue = 20;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 36 && progress < 43) {
                    AppGlobals.ViewId_textview7_settempvalue = 21;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 43 && progress < 50) {
                    AppGlobals.ViewId_textview7_settempvalue = 22;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 50 && progress < 57) {
                    AppGlobals.ViewId_textview7_settempvalue = 23;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 57 && progress < 64) {
                    AppGlobals.ViewId_textview7_settempvalue = 24;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 64 && progress < 71) {
                    AppGlobals.ViewId_textview7_settempvalue = 25;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 71 && progress < 79) {
                    AppGlobals.ViewId_textview7_settempvalue = 26;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 79 && progress < 86) {
                    AppGlobals.ViewId_textview7_settempvalue = 27;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 86 && progress < 93) {
                    AppGlobals.ViewId_textview7_settempvalue = 28;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else if (progress >= 93 && progress < 100) {
                    AppGlobals.ViewId_textview7_settempvalue = 29;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                } else {
                    AppGlobals.ViewId_textview7_settempvalue = 30;
                    ViewId_textview7.setText("  " + String.valueOf(AppGlobals.ViewId_textview7_settempvalue) + "  ");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                enableguardtimeRinnai21HomeScreen();

                Tx_RN171DeviceSetTemp();
            }
        });

        //***** OnTouchListener - button6 (Show Hints Flame - Ok got this) *****//
        ViewId_button6 = (Button) findViewById(R.id.button6);
        ViewId_textview15 = (TextView) findViewById(R.id.textView15);

        ViewId_button6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview15.setTextColor(Color.parseColor("#FF808080"));

                        ViewId_button6.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_textview15.setTextColor(Color.parseColor("#FF007FFF"));

                        ViewId_imageview_navview7.setImageResource(R.drawable.checkbox2);
                        AppGlobals.ViewId_imageview_navview7_actionup = true;
                        AppGlobals.ShowHints_economy_actionvisible = false;
                        AppGlobals.ShowHints_flame_actionvisible = false;
                        AppGlobals.ShowHints_settemp_actionvisible = false;

                        setShowHints();

                        ViewId_button6.setPressed(false);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview15.setTextColor(Color.parseColor("#FF007FFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - button5 (Show Hints Set Temp - Ok got this) *****//
        ViewId_button5 = (Button) findViewById(R.id.button5);
        ViewId_textview14 = (TextView) findViewById(R.id.textView14);

        ViewId_button5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview14.setTextColor(Color.parseColor("#FF808080"));

                        ViewId_button5.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_textview14.setTextColor(Color.parseColor("#FF007FFF"));

                        ViewId_imageview_navview7.setImageResource(R.drawable.checkbox2);
                        AppGlobals.ViewId_imageview_navview7_actionup = true;
                        AppGlobals.ShowHints_economy_actionvisible = false;
                        AppGlobals.ShowHints_flame_actionvisible = false;
                        AppGlobals.ShowHints_settemp_actionvisible = false;

                        setShowHints();

                        ViewId_button5.setPressed(false);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview14.setTextColor(Color.parseColor("#FF007FFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton3 (Standby) *****//
        ViewId_imagebutton3 = (ImageButton) findViewById(R.id.imageButton3);

        ViewId_imagebutton3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton3.setImageResource(R.drawable.standby_pressed);

                        ViewId_imagebutton3.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_imagebutton3.setImageResource(R.drawable.standby);

                        //Operation state = Stop:[0x00]
                        AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = false;

                        ViewId_imagebutton3.setPressed(false);

                        Tx_RN171DeviceSetOpState();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton3.setImageResource(R.drawable.standby);
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton22 (Standby - Return) *****//
        ViewId_imagebutton22 = (ImageButton) findViewById(R.id.imageButton22);

        ViewId_imagebutton22.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton22.setImageResource(R.drawable.standby_power_off_pressed);

                        ViewId_imagebutton22.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton22.setImageResource(R.drawable.standby_power_off);

                        //Operation state = Operate:[0x01]
                        AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = true;

                        ViewId_imagebutton22.setPressed(false);

                        Tx_RN171DeviceSetOpState();

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton22.setImageResource(R.drawable.standby_power_off);
                }
                return false;
            }
        });

        //***** OnTouchListener - imagebutton30 (Standby - Settings) *****//
        ViewId_imagebutton30 = (ImageButton) findViewById(R.id.imageButton30);

        ViewId_imagebutton30.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton30.setImageResource(R.drawable.settings_press);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        //***** srccompat - imagebutton30 (Settings) *****//
                        ViewId_imagebutton30.setImageResource(R.drawable.settings);

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.openDrawer(Gravity.LEFT);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton30.setImageResource(R.drawable.settings);
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton23Standby (Standby - Multi-unit) *****//
        ViewId_imagebutton23Standby = (ImageButton) findViewById(R.id.imageButton23Standby);

        ViewId_button7 = (Button) findViewById(R.id.button7);
        ViewId_button8 = (Button) findViewById(R.id.button8);

        ViewId_textview4 = (TextView) findViewById(R.id.textView4);
        ViewId_textview5 = (TextView) findViewById(R.id.textView5);
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);

        ViewId_imageview2 = (ImageView) findViewById(R.id.imageView2);
        ViewId_imageview3 = (ImageView) findViewById(R.id.imageView3);
        ViewId_imageview4 = (ImageView) findViewById(R.id.imageView4);
        ViewId_imageview5 = (ImageView) findViewById(R.id.imageView5);

        ViewId_linearlayout_seekbar_flame = (LinearLayout) findViewById(R.id.linearlayout_seekbar_flame);
        ViewId_linearlayout_seekbar_settemp = (LinearLayout) findViewById(R.id.linearlayout_seekbar_settemp);

        ViewId_myseekbar3 = (VerticalSeekBar) findViewById(R.id.mySeekBar3);
        ViewId_myseekbar4 = (VerticalSeekBar) findViewById(R.id.mySeekBar4);

        ViewId_include_showhints_flame = (ViewGroup) findViewById(R.id.include_showhints_flame);
        ViewId_include_showhints_settemp = (ViewGroup) findViewById(R.id.include_showhints_settemp);
        ViewId_include_showhints_economy = (ViewGroup) findViewById(R.id.include_showhints_economy);
        ViewId_include_button_multiunit_standby = (ViewGroup) findViewById(R.id.include_button_multiunit_standby);
        ViewId_include_multiunit_lockout = (ViewGroup) findViewById(R.id.include_multiunit_lockout);
        ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);

        ViewId_imagebutton23Standby.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_imagebutton23Standby.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_imagebutton23Standby.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        //***** srccompat - imagebutton23Standby (Multi-unit) *****//
                        AppGlobals.ViewId_imagebutton23standby_actionup = !AppGlobals.ViewId_imagebutton23standby_actionup;

                        //Multi-unit button OFF, Multi-unit List ON = [0x00]
                        //Multi-unit button ON, Multi-unit List OFF = [0x01]
                        if (AppGlobals.ViewId_imagebutton23standby_actionup == true) {

                            //***** button - ViewId_button7 (Flame) *****//
                            ViewId_button7.setVisibility(View.VISIBLE);

                            //***** button - ViewId_button8 (Set Temp) *****//
                            ViewId_button8.setVisibility(View.VISIBLE);

                            //***** Alpha - Flame *****//
                            ViewId_textview4.setAlpha(0.10f);
                            ViewId_textview6.setAlpha(0.10f);
                            ViewId_imageview2.setAlpha(0.25f);
                            ViewId_imageview4.setAlpha(0.25f);
                            ViewId_linearlayout_seekbar_flame.setAlpha(0.50f);
                            ViewId_myseekbar3.setAlpha(0.25f);

                            //***** Alpha - Set Temp *****//
                            ViewId_textview5.setAlpha(0.10f);
                            ViewId_textview7.setAlpha(0.10f);
                            ViewId_imageview3.setAlpha(0.25f);
                            ViewId_imageview5.setAlpha(0.25f);
                            ViewId_linearlayout_seekbar_settemp.setAlpha(0.50f);
                            ViewId_myseekbar4.setAlpha(0.25f);

                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit_standby.setAlpha(1.0f);

                            //***** include - ViewId_include_showhints_flame *****//
                            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_showhints_settemp *****//
                            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_showhints_economy *****//
                            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);

                            //***** Visibility - Multi-unit Lockout *****//
                            ViewId_include_multiunit_lockout.setVisibility(View.INVISIBLE);

                            //***** Visibility - Multi-unit *****//
                            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

                        } else {
                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit_standby.setAlpha(0.10f);

                            //***** Visibility - Multi-unit Lockout *****//
                            ViewId_include_multiunit_lockout.setVisibility(View.VISIBLE);

                            //***** Visibility - Multi-unit *****//
                            ViewId_include_multiunit.setVisibility(View.VISIBLE);

                            ViewId_multiunit_tableLayout = (TableLayout) findViewById(R.id.multiunit_tableLayout);
                            //tl.setOnTouchListener(new AutoTimerTableTouchListener());

                            //clear the table, start with blank table
                            ViewId_multiunit_tableLayout.removeAllViews();

                            int id = 0;

                            for (int i = 0; i <= AppGlobals.fireplaceWifi.size() - 1; i++) {

                                View ViewId_scrollview_row_multiunit_rinnai21_home_screen = getLayoutInflater().inflate(R.layout.scrollview_row_multiunit_rinnai21_home_screen, null, false);

                                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView80)).setText(id + "");

                                //add listener
                                ViewId_scrollview_row_multiunit_rinnai21_home_screen.setOnClickListener(scrollviewrowmultiunitrinnai21homescreenOnClickListener);//add OnClickListener Here

                                //add listener
                                ViewId_scrollview_row_multiunit_rinnai21_home_screen.setOnLongClickListener(scrollviewrowmultiunitrinnai21homescreenOnLongClickListener);//add OnClickListener Here

                                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView77)).setText(AppGlobals.fireplaceWifi.get(i).DeviceName + "");

                                //Add the Row to the table
                                ViewId_multiunit_tableLayout.addView(ViewId_scrollview_row_multiunit_rinnai21_home_screen);

                                //Next
                                id++;

                            }

                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton23 (Multi-unit) *****//
        ViewId_imagebutton23 = (ImageButton) findViewById(R.id.imageButton23);

        ViewId_button7 = (Button) findViewById(R.id.button7);
        ViewId_button8 = (Button) findViewById(R.id.button8);

        ViewId_textview4 = (TextView) findViewById(R.id.textView4);
        ViewId_textview5 = (TextView) findViewById(R.id.textView5);
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);

        ViewId_imageview2 = (ImageView) findViewById(R.id.imageView2);
        ViewId_imageview3 = (ImageView) findViewById(R.id.imageView3);
        ViewId_imageview4 = (ImageView) findViewById(R.id.imageView4);
        ViewId_imageview5 = (ImageView) findViewById(R.id.imageView5);

        ViewId_linearlayout_seekbar_flame = (LinearLayout) findViewById(R.id.linearlayout_seekbar_flame);
        ViewId_linearlayout_seekbar_settemp = (LinearLayout) findViewById(R.id.linearlayout_seekbar_settemp);

        ViewId_myseekbar3 = (VerticalSeekBar) findViewById(R.id.mySeekBar3);
        ViewId_myseekbar4 = (VerticalSeekBar) findViewById(R.id.mySeekBar4);

        ViewId_include_showhints_flame = (ViewGroup) findViewById(R.id.include_showhints_flame);
        ViewId_include_showhints_settemp = (ViewGroup) findViewById(R.id.include_showhints_settemp);
        ViewId_include_showhints_economy = (ViewGroup) findViewById(R.id.include_showhints_economy);
        ViewId_include_button_multiunit = (ViewGroup) findViewById(R.id.include_button_multiunit);
        ViewId_include_multiunit_lockout = (ViewGroup) findViewById(R.id.include_multiunit_lockout);
        ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);

        ViewId_imagebutton23.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED

                        ViewId_imagebutton23.setPressed(true);

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_imagebutton23.setPressed(false);

                        enableguardtimeRinnai21HomeScreen();

                        //***** srccompat - imagebutton23 (Multi-unit) *****//
                        AppGlobals.ViewId_imagebutton23_actionup = !AppGlobals.ViewId_imagebutton23_actionup;

                        //Multi-unit button OFF, Multi-unit List ON = [0x00]
                        //Multi-unit button ON, Multi-unit List OFF = [0x01]
                        if (AppGlobals.ViewId_imagebutton23_actionup == true) {

                            //***** button - ViewId_button7 (Flame) *****//
                            ViewId_button7.setVisibility(View.VISIBLE);

                            //***** button - ViewId_button8 (Set Temp) *****//
                            ViewId_button8.setVisibility(View.VISIBLE);

                            //***** Alpha - Flame *****//
                            ViewId_textview4.setAlpha(0.10f);
                            ViewId_textview6.setAlpha(0.10f);
                            ViewId_imageview2.setAlpha(0.25f);
                            ViewId_imageview4.setAlpha(0.25f);
                            ViewId_linearlayout_seekbar_flame.setAlpha(0.50f);
                            ViewId_myseekbar3.setAlpha(0.25f);

                            //***** Alpha - Set Temp *****//
                            ViewId_textview5.setAlpha(0.10f);
                            ViewId_textview7.setAlpha(0.10f);
                            ViewId_imageview3.setAlpha(0.25f);
                            ViewId_imageview5.setAlpha(0.25f);
                            ViewId_linearlayout_seekbar_settemp.setAlpha(0.50f);
                            ViewId_myseekbar4.setAlpha(0.25f);

                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit.setAlpha(1.0f);

                            //***** include - ViewId_include_showhints_flame *****//
                            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_showhints_settemp *****//
                            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_showhints_economy *****//
                            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);

                            //***** Visibility - Multi-unit Lockout *****//
                            ViewId_include_multiunit_lockout.setVisibility(View.INVISIBLE);

                            //***** Visibility - Multi-unit *****//
                            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

                        } else {
                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit.setAlpha(0.10f);

                            //***** Visibility - Multi-unit Lockout *****//
                            ViewId_include_multiunit_lockout.setVisibility(View.VISIBLE);

                            //***** Visibility - Multi-unit *****//
                            ViewId_include_multiunit.setVisibility(View.VISIBLE);

                            ViewId_multiunit_tableLayout = (TableLayout) findViewById(R.id.multiunit_tableLayout);
                            //tl.setOnTouchListener(new AutoTimerTableTouchListener());

                            //clear the table, start with blank table
                            ViewId_multiunit_tableLayout.removeAllViews();

                            int id = 0;

                            for (int i = 0; i <= AppGlobals.fireplaceWifi.size() - 1; i++) {

                                View ViewId_scrollview_row_multiunit_rinnai21_home_screen = getLayoutInflater().inflate(R.layout.scrollview_row_multiunit_rinnai21_home_screen, null, false);

                                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView80)).setText(id + "");

                                //add listener
                                ViewId_scrollview_row_multiunit_rinnai21_home_screen.setOnClickListener(scrollviewrowmultiunitrinnai21homescreenOnClickListener);//add OnClickListener Here

                                //add listener
                                ViewId_scrollview_row_multiunit_rinnai21_home_screen.setOnLongClickListener(scrollviewrowmultiunitrinnai21homescreenOnLongClickListener);//add OnClickListener Here

                                ((TextView) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.textView77)).setText(AppGlobals.fireplaceWifi.get(i).DeviceName + "");

                                //Add the Row to the table
                                ViewId_multiunit_tableLayout.addView(ViewId_scrollview_row_multiunit_rinnai21_home_screen);

                                //Next
                                id++;

                            }

                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                }
                return false;
            }
        });

        //***** OnTouchListener - button14 (OK) *****//
//        ViewId_button14 = (Button) findViewById(R.id.button14);
//        ViewId_textview75 = (TextView) findViewById(R.id.textView75);
        ViewId_include_multiunit_lockout = (ViewGroup) findViewById(R.id.include_multiunit_lockout);
        ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);

        //***************************//
        //***** OnClickListener *****//
        //***************************//
        ViewId_edittext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai21HomeScreen_onClick: EditText.");

                ViewId_edittext2 = (EditText) v.findViewById(R.id.editText2);
                ViewId_edittext2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                if (selected_edittext2firsttime == false) {
                    ViewId_edittext2.setText("");
                    selected_edittext2firsttime = true;
                }

            }
        });

        //***** OnTouchListener - button16 (Save) *****//
        ViewId_button16 = (Button) findViewById(R.id.button16);
        ViewId_textview85 = (TextView) findViewById(R.id.textView85);
        ViewId_linearlayout_multiunit_devicenameedit = (LinearLayout) findViewById(R.id.linearlayout_multiunit_devicenameedit);
        selected_edittextrinnai21devicename = (EditText) findViewById(R.id.editText2);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewId_include_devicenameedit = (ViewGroup) findViewById(R.id.include_devicenameedit);
        ViewId_include_devicenameedit_lockout = (ViewGroup) findViewById(R.id.include_devicenameedit_lockout);

        ViewId_button16.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button16.setPressed(true);

                        ViewId_textview85.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_textview85.setTextColor(Color.parseColor("#FF007FFF"));

                        Log.d("myApp", "Rinnai21HomeScreen_onTouch" + selected_edittextrinnai21devicename);

                        if (scrollviewrowmultiunit_pressed == true && !selected_edittextrinnai21devicename.getText().toString().equals("Enter Heater Name") && !selected_edittextrinnai21devicename.getText().toString().equals("")) {

                            AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;
                            Log.d("myApp", "Rinnai21HomeScreen_selected_fireplaceWifi:" + AppGlobals.selected_fireplaceWifi);

                            Tx_RN171DeviceSetDeviceName();

                            hideSoftKeyboard_multiunit();

                            ViewId_button16.setPressed(false);

                            enableguardtimeRinnai21HomeScreen();

                            ViewId_textview75.setTextColor(Color.parseColor("#FFFFFFFF"));

                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit.setAlpha(1.0f);

                            //***** Alpha - Multi-unit (button/text) *****//
                            ViewId_include_button_multiunit_standby.setAlpha(1.0f);

                            //***** Set Text - Multi-unit (button/text) *****//
                            AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName = selected_edittextrinnai21devicename.getText() + "";

                            ViewId_textview71.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");

                            ViewId_textview71Standby.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");

                            //***** Visibility - Multi-unit Lockout *****//
                            ViewId_include_multiunit_lockout.setVisibility(View.INVISIBLE);

                            //***** Visibility - Multi-unit *****//
                            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

                            AppGlobals.ViewId_imagebutton23_actionup = true;

                            AppGlobals.ViewId_imagebutton23standby_actionup = true;

                            //Lock/Closed Drawer
                            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                            //***** Visibility - DeviceName Edit *****//
                            ViewId_include_devicenameedit.setVisibility(View.INVISIBLE);

                            //***** Visibility - DeviceName Edit Lockout *****//
                            ViewId_include_devicenameedit_lockout.setVisibility(View.INVISIBLE);

                            /////////////////////////////////////////////////
                            //Update Appliance Nickname based upon UUID
                            /////////////////////////////////////////////////
                            //Call method and post values
                            AWSconnection.updateApplianceNicknameURL(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).UUID, AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName,

                                    //Call interface to retrieve Async results
                                    new AWSconnection.textResult() {
                                        @Override

                                        public void getResult(String result) {

                                            //Returns either "New Nickname" : the new nickname you have inputted" or error
                                            //message
                                            //Log. i ( "Nickname Update Status:" , result);
                                            Log.d("myApp_AWS", "Nickname Update Status:" + result);

                                            if (!result.equals("\"New Nickname: " + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "\"")) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Rinnai21HomeScreen.this, "Web Services Error.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(Rinnai21HomeScreen.this, "Update Successful.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        }
                                    });


                        } else {
                            ObjectAnimator
                                    .ofFloat(ViewId_linearlayout_multiunit_devicenameedit, "translationX", 0, 10, -10, 10, -10, 10, -10, 10, -10, 10, -10, 0)
                                    .setDuration(500)
                                    .start();
                            Toast.makeText(Rinnai21HomeScreen.this, "1- Enter Heater Name, \n2- Press Save.",
                                    Toast.LENGTH_LONG).show();
                        }

                        selected_edittext2firsttime = false;

                        //***** DeviceName edit (multiunit) - ViewId_edittext2 *****//
                        ViewId_edittext2 = (EditText) findViewById(R.id.editText2);
                        ViewId_edittext2.setText("Enter Heater Name");

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview85.setTextColor(Color.parseColor("#FF007FFF"));
                }
                return false;
            }
        });

        return true;
    }

    //************************//
    //***** setShowHints *****//
    //************************//

    public void setShowHints() {
        Log.d("myApp", "Rinnai21HomeScreen_setShowHints.");

        ViewId_include_showhints_flame = (ViewGroup) findViewById(R.id.include_showhints_flame);
        ViewId_include_showhints_settemp = (ViewGroup) findViewById(R.id.include_showhints_settemp);
        ViewId_include_showhints_economy = (ViewGroup) findViewById(R.id.include_showhints_economy);

        if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.ShowHints_economy_actionvisible == true && AppGlobals.ShowHints_settemp_actionvisible == true) {
            //***** include - ViewId_include_showhints_flame *****//
            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_settemp *****//
            ViewId_include_showhints_settemp.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_showhints_economy *****//
            ViewId_include_showhints_economy.setVisibility(View.VISIBLE);
        } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.ShowHints_economy_actionvisible == true) {
            //***** include - ViewId_include_showhints_flame *****//
            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_settemp *****//
            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_economy *****//
            ViewId_include_showhints_economy.setVisibility(View.VISIBLE);
        } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.ShowHints_flame_actionvisible == true) {
            //***** include - ViewId_include_showhints_flame *****//
            ViewId_include_showhints_flame.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_showhints_settemp *****//
            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_economy *****//
            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);
        } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.ShowHints_settemp_actionvisible == true) {
            //***** include - ViewId_include_showhints_flame *****//
            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_settemp *****//
            ViewId_include_showhints_settemp.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_showhints_economy *****//
            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);
        } else {
            //***** include - ViewId_include_showhints_flame *****//
            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_settemp *****//
            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_showhints_economy *****//
            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);
        }
    }

    //*************************************//
    //***** setFlameSettempVisibility *****//
    //*************************************//

    public void setFlameSettempVisibility() {
        Log.d("myApp", "Rinnai21HomeScreen_setFlameSettempVisibility.");

        ViewId_button7 = (Button) findViewById(R.id.button7);
        ViewId_button8 = (Button) findViewById(R.id.button8);

        ViewId_textview4 = (TextView) findViewById(R.id.textView4);
        ViewId_textview5 = (TextView) findViewById(R.id.textView5);
        ViewId_textview6 = (TextView) findViewById(R.id.textView6);
        ViewId_textview7 = (TextView) findViewById(R.id.textView7);
        ViewId_textview71 = (TextView) findViewById(R.id.textView71);
        ViewId_textview71Standby = (TextView) findViewById(R.id.textView71Standby);

        ViewId_imageview2 = (ImageView) findViewById(R.id.imageView2);
        ViewId_imageview3 = (ImageView) findViewById(R.id.imageView3);
        ViewId_imageview4 = (ImageView) findViewById(R.id.imageView4);
        ViewId_imageview5 = (ImageView) findViewById(R.id.imageView5);

        ViewId_linearlayout_seekbar_flame = (LinearLayout) findViewById(R.id.linearlayout_seekbar_flame);
        ViewId_linearlayout_seekbar_settemp = (LinearLayout) findViewById(R.id.linearlayout_seekbar_settemp);

        ViewId_myseekbar3 = (VerticalSeekBar) findViewById(R.id.mySeekBar3);
        ViewId_myseekbar4 = (VerticalSeekBar) findViewById(R.id.mySeekBar4);

        ViewId_include_button_multiunit = (ViewGroup) findViewById(R.id.include_button_multiunit);
        ViewId_include_multiunit_lockout = (ViewGroup) findViewById(R.id.include_multiunit_lockout);
        ViewId_include_multiunit = (ViewGroup) findViewById(R.id.include_multiunit);

        ViewId_include_button_multiunit_standby = (ViewGroup) findViewById(R.id.include_button_multiunit_standby);

        if (AppGlobals.Button_flame_settemp_actionvisible == true) {
            //***** button - ViewId_button7 (Flame) *****//
            ViewId_button7.setVisibility(View.INVISIBLE);

            //***** button - ViewId_button8 (Set Temp) *****//
            ViewId_button8.setVisibility(View.VISIBLE);

            //***** Alpha - Flame *****//
            ViewId_textview4.setAlpha(1.0f);
            ViewId_textview6.setAlpha(1.0f);
            ViewId_imageview2.setAlpha(1.0f);
            ViewId_imageview4.setAlpha(1.0f);
            ViewId_linearlayout_seekbar_flame.setAlpha(1.0f);
            ViewId_myseekbar3.setAlpha(0.50f);

            //***** Alpha - Set Temp *****//
            ViewId_textview5.setAlpha(0.10f);
            ViewId_textview7.setAlpha(0.10f);
            ViewId_imageview3.setAlpha(0.25f);
            ViewId_imageview5.setAlpha(0.25f);
            ViewId_linearlayout_seekbar_settemp.setAlpha(0.50f);
            ViewId_myseekbar4.setAlpha(0.25f);

            //***** Alpha - Multi-unit (button/text) *****//
            ViewId_include_button_multiunit.setAlpha(0.10f);

            //***** Alpha - Multi-unit (button/text) *****//
            ViewId_include_button_multiunit_standby.setAlpha(0.10f);

            //***** Set Text - Multi-unit (button/text) *****//
            ViewId_textview71.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");

            //***** Set Text - Multi-unit (button/text) *****//
            ViewId_textview71Standby.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");

            //***** Visibility - Multi-unit Lockout *****//
            ViewId_include_multiunit_lockout.setVisibility(View.INVISIBLE);

            //***** Visibility - Multi-unit *****//
            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

            AppGlobals.ViewId_imagebutton23_actionup = false;

            AppGlobals.ViewId_imagebutton23standby_actionup = false;

        } else {
            //***** button - ViewId_button7 (Flame) *****//
            ViewId_button7.setVisibility(View.VISIBLE);

            //***** button - ViewId_button8 (Set Temp) *****//
            ViewId_button8.setVisibility(View.INVISIBLE);

            //***** Alpha - Flame *****//
            ViewId_textview4.setAlpha(0.10f);
            ViewId_textview6.setAlpha(0.10f);
            ViewId_imageview2.setAlpha(0.25f);
            ViewId_imageview4.setAlpha(0.25f);
            ViewId_linearlayout_seekbar_flame.setAlpha(0.50f);
            ViewId_myseekbar3.setAlpha(0.25f);

            //***** Alpha - Set Temp *****//
            ViewId_textview5.setAlpha(1.0f);
            ViewId_textview7.setAlpha(1.0f);
            ViewId_imageview3.setAlpha(1.0f);
            ViewId_imageview5.setAlpha(1.0f);
            ViewId_linearlayout_seekbar_settemp.setAlpha(1.0f);
            ViewId_myseekbar4.setAlpha(0.50f);

            //***** Alpha - Multi-unit (button/text) *****//
            ViewId_include_button_multiunit.setAlpha(0.10f);

            //***** Alpha - Multi-unit (button/text) *****//
            ViewId_include_button_multiunit_standby.setAlpha(0.10f);

            //***** Set Text - Multi-unit (button/text) *****//
//            ViewId_textview71.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");
            ViewId_textview71.setText(AppGlobals.fireplaceWifi.get(0).DeviceName + "");
            //***** Set Text - Multi-unit (button/text) *****//
            ViewId_textview71Standby.setText(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceName + "");

            //***** Visibility - Multi-unit Lockout *****//
            ViewId_include_multiunit_lockout.setVisibility(View.INVISIBLE);

            //***** Visibility - Multi-unit *****//
            ViewId_include_multiunit.setVisibility(View.INVISIBLE);

            AppGlobals.ViewId_imagebutton23_actionup = false;

            AppGlobals.ViewId_imagebutton23standby_actionup = false;
        }
    }

    //**********************//
    //***** setStandby *****//
    //**********************//

    public void setStandby() {
        Log.d("myApp", "Rinnai21HomeScreen_setStandby.");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewId_include_content_home_screen = (ViewGroup) findViewById(R.id.include_content_home_screen);
        ViewId_include_button_flame_settemp = (ViewGroup) findViewById(R.id.include_button_flame_settemp);
        ViewId_include_standby = (ViewGroup) findViewById(R.id.include_standby);
        ViewId_include_button_multiunit_standby = (ViewGroup) findViewById(R.id.include_button_multiunit_standby);

        //Operation state = Stop:[0x00]
        //Operation state = Operate:[0x01]
        if (AppGlobals.ViewId_imagebutton3_imagebutton22_actionup == false) {
            //***** include - ViewId_include_standby *****//
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            ViewId_include_content_home_screen.setVisibility(View.INVISIBLE);
            ViewId_include_button_flame_settemp.setVisibility(View.INVISIBLE);
            ViewId_include_showhints_flame.setVisibility(View.INVISIBLE);
            ViewId_include_showhints_settemp.setVisibility(View.INVISIBLE);
            ViewId_include_showhints_economy.setVisibility(View.INVISIBLE);
            ViewId_include_standby.setVisibility(View.VISIBLE);
            ViewId_include_button_multiunit_standby.setVisibility(View.VISIBLE);

            //***** Alpha - Multi-unit (button/text) *****//
            ViewId_include_button_multiunit_standby.setAlpha(0.10f);

            AppGlobals.ViewId_imagebutton23standby_actionup = false;
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            ViewId_include_content_home_screen.setVisibility(View.VISIBLE);
            ViewId_include_button_flame_settemp.setVisibility(View.VISIBLE);
            ViewId_include_showhints_flame.setVisibility(View.VISIBLE);
            ViewId_include_showhints_settemp.setVisibility(View.VISIBLE);
            ViewId_include_showhints_economy.setVisibility(View.VISIBLE);
            ViewId_include_standby.setVisibility(View.INVISIBLE);
            ViewId_include_button_multiunit_standby.setVisibility(View.INVISIBLE);
        }
    }

    //************************************************************//
    //***** NavigationView - disableNavigationViewScrollbars *****//
    //************************************************************//

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
                navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }

    //***************************************//
    //***** startTxRN171DeviceGetStatus *****//
    //***************************************//

    public void startTxRN171DeviceGetStatus() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai21HomeScreen: Tick.. " + startupCheckTimerCount);

                //***** myseekbar3 (Seekbar Shadow Flame - Seekbar) *****//
                ViewId_myseekbar = (VerticalSeekBar) findViewById(R.id.mySeekBar);
                ViewId_myseekbar3 = (VerticalSeekBar) findViewById(R.id.mySeekBar3);

                if (ViewId_myseekbar3.getProgress() < ViewId_myseekbar.getProgress()) {
                    ViewId_myseekbar3.setProgress(seekbar_shadow_flamevalue++);
                } else if (ViewId_myseekbar3.getProgress() > ViewId_myseekbar.getProgress()) {
                    ViewId_myseekbar3.setProgress(seekbar_shadow_flamevalue--);
                }

                //***** myseekbar4 (Seekbar Shadow Set Temp - Seekbar) *****//
                ViewId_myseekbar4 = (VerticalSeekBar) findViewById(R.id.mySeekBar4);

                switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmRoomTemperature) {
                    case 16:
                        ViewId_myseekbar4.setProgress(0);
                        break;
                    case 17:
                        ViewId_myseekbar4.setProgress(7);
                        break;
                    case 18:
                        ViewId_myseekbar4.setProgress(14);
                        break;
                    case 19:
                        ViewId_myseekbar4.setProgress(21);
                        break;
                    case 20:
                        ViewId_myseekbar4.setProgress(29);
                        break;
                    case 21:
                        ViewId_myseekbar4.setProgress(36);
                        break;
                    case 22:
                        ViewId_myseekbar4.setProgress(43);
                        break;
                    case 23:
                        ViewId_myseekbar4.setProgress(50);
                        break;
                    case 24:
                        ViewId_myseekbar4.setProgress(57);
                        break;
                    case 25:
                        ViewId_myseekbar4.setProgress(64);
                        break;
                    case 26:
                        ViewId_myseekbar4.setProgress(71);
                        break;
                    case 27:
                        ViewId_myseekbar4.setProgress(79);
                        break;
                    case 28:
                        ViewId_myseekbar4.setProgress(86);
                        break;
                    case 29:
                        ViewId_myseekbar4.setProgress(93);
                        break;
                    case 30:
                        ViewId_myseekbar4.setProgress(100);
                        break;
                    default:
                        break;
                }

                if (startupCheckTimerCount % 20 == 0) {
                    Tx_RN171DeviceGetStatus();

                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime--;
                }

                startupCheckTimerCount++;

            }

        }, 0, 100);

    }

    //*********************************//
    //***** setRinnai21HomeScreen *****//
    //*********************************//

    public void setRinnai21HomeScreen() {
        Log.d("myApp", "Rinnai21HomeScreen_setRinnai21HomeScreen.");

        //Intialise Rinnai21HomeScreen.
        setShowHints();

        AppGlobals.Button_flame_settemp_actionvisible = false;

        setFlameSettempVisibility();

        //Check if Standby Screen is set.
        //Operation state = Stop:[0x00]
        if (AppGlobals.ViewId_imagebutton3_imagebutton22_actionup == false) {
            setStandby();
        }
        //Operation state = Operate:[0x01]
        else {
            setShowHints();

            AppGlobals.Button_flame_settemp_actionvisible = false;

            setFlameSettempVisibility();
        }

        //***** myseekbar3 (Seekbar Shadow Flame - Seekbar) *****//
        ViewId_myseekbar3 = (VerticalSeekBar) findViewById(R.id.mySeekBar3);
        ViewId_myseekbar3.setEnabled(false);

        //***** myseekbar4 (Seekbar Shadow Set Temp - Seekbar) *****//
        ViewId_myseekbar4 = (VerticalSeekBar) findViewById(R.id.mySeekBar4);
        ViewId_myseekbar4.setEnabled(false);

        //***** DeviceName edit (multiunit) - ViewId_edittext2 *****//
        ViewId_edittext2 = (EditText) findViewById(R.id.editText2);
        ViewId_edittext2.setInputType(InputType.TYPE_CLASS_TEXT);

    }

    //*********************************************//
    //***** enableguardtimeRinnai21HomeScreen *****//
    //*********************************************//

    public void enableguardtimeRinnai21HomeScreen() {
        Log.d("myApp", "enableguardtimeRinnai21HomeScreen");

        AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime = 5;
    }

    //*********************************************//
    //***** resetguardtimeRinnai21HomeScreen *****//
    //*********************************************//

    public void resetguardtimeRinnai21HomeScreen() {
        Log.d("myApp", "resetguardtimeRinnai21HomeScreen");

        AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime = 0;
    }

    //*******************************************************//
    //***** scrollviewrowrinnai21homescreenOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowmultiunitrinnai21homescreenOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            //scrollviewrow_pressed = true;

            //Get Selected Text (timersdaysofweek)
            ViewId_textview80 = ((TextView) v.findViewById(R.id.textView80));
            scrollviewrowmultiunitrinnai21homescreen_id = Integer.parseInt(ViewId_textview80.getText().toString(), 10);

            updateScrollViewTableLayoutRinnai21HomeScreenDetails();

            //Highlight Selection
            ViewId_linearlayout_multiunit_row = ((LinearLayout) v.findViewById(R.id.linearlayout_multiunit_row));

            ViewId_linearlayout_multiunit_row.setBackgroundColor(Color.parseColor("#32FFFFFF"));

            AppGlobals.selected_fireplaceWifi = scrollviewrowmultiunitrinnai21homescreen_id;
            intent = new Intent(Rinnai21HomeScreen.this, Rinnai21HomeScreen.class);
            startActivity(intent);
            finish();
        }
    };

    //***********************************************************//
    //***** scrollviewrowrinnai21homescreenOnLongClickListener *****//
    //***********************************************************//

    private View.OnLongClickListener scrollviewrowmultiunitrinnai21homescreenOnLongClickListener = new View.OnLongClickListener() {
        public boolean onLongClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            scrollviewrowmultiunit_pressed = true;

            //Get Selected Text (timersdaysofweek)
            ViewId_textview80 = ((TextView) v.findViewById(R.id.textView80));
            scrollviewrowmultiunitrinnai21homescreen_id = Integer.parseInt(ViewId_textview80.getText().toString(), 10);

            updateScrollViewTableLayoutRinnai21HomeScreenDetails();

            //Highlight Selection
            ViewId_linearlayout_multiunit_row = ((LinearLayout) v.findViewById(R.id.linearlayout_multiunit_row));

            ViewId_linearlayout_multiunit_row.setBackgroundColor(Color.parseColor("#32FFFFFF"));

            //Lock/Closed Drawer
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            //***** Visibility - DeviceName Edit *****//
            ViewId_include_devicenameedit = (ViewGroup) findViewById(R.id.include_devicenameedit);
            ViewId_include_devicenameedit.setVisibility(View.VISIBLE);

            //***** Visibility - DeviceName Edit Lockout *****//
            ViewId_include_devicenameedit_lockout = (ViewGroup) findViewById(R.id.include_devicenameedit_lockout);
            ViewId_include_devicenameedit_lockout.setVisibility(View.VISIBLE);

            //Set Selected Text (Rename DeviceName)
            ViewId_textview81 = (TextView) findViewById(R.id.textView81);
            ViewId_textview81.setText("Rename " + AppGlobals.fireplaceWifi.get(scrollviewrowmultiunitrinnai21homescreen_id).DeviceName + "");

            return true;
        }
    };

    //****************************************************************//
    //***** updateScrollViewTableLayoutRinnai21HomeScreenDetails *****//
    //****************************************************************//

    public void updateScrollViewTableLayoutRinnai21HomeScreenDetails() {

        TableLayout ViewId_multiunit_tableLayout = (TableLayout) findViewById(R.id.multiunit_tableLayout);

        for (int a = 0; a <= AppGlobals.fireplaceWifi.size() - 1; a++) {

            View ViewId_scrollview_row_multiunit_rinnai21_home_screen = ViewId_multiunit_tableLayout.getChildAt(a);

            //Highlight Selection
            ViewId_linearlayout_multiunit_row = ((LinearLayout) ViewId_scrollview_row_multiunit_rinnai21_home_screen.findViewById(R.id.linearlayout_multiunit_row));

            ViewId_linearlayout_multiunit_row.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_multiunit() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_multiunit(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

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
                        if (ViewId_imagebutton2.isPressed() == false &&
                                ViewId_button.isPressed() == false && ViewId_button2.isPressed() == false && ViewId_button3.isPressed() == false && ViewId_button4.isPressed() == false &&
                                ViewId_myseekbar.isPressed() == false && ViewId_myseekbar2.isPressed() == false &&
                                ViewId_button7.isPressed() == false && ViewId_button8.isPressed() == false &&
                                ViewId_imagebutton3.isPressed() == false && ViewId_imagebutton22.isPressed() == false &&
                                ViewId_button5.isPressed() == false && ViewId_button6.isPressed() == false &&
                                ViewId_imagebutton23.isPressed() == false &&
                                ViewId_imagebutton23Standby.isPressed() == false &&
                                ViewId_include_multiunit.getVisibility() == View.INVISIBLE && ViewId_button14.isPressed() == false &&
                                ViewId_include_devicenameedit.getVisibility() == View.INVISIBLE && ViewId_button16.isPressed() == false &&
                                !drawer.isDrawerOpen(GravityCompat.START)) {

                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmappSettingsChangeGuardTime <= 0) {

                                if (pType.contains("22")) {

                                    //*****************************//
                                    //***** Main Power Switch *****//
                                    //*****************************//

                                    Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Main Power Switch (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch + ")");

                                    //Main power switch = ON:[0x00]
                                    if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmMainPowerSwitch == 0) {

                                        //***************************//
                                        //***** Error Code HIGH *****//
                                        //***************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Error Code HIGH (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI + ")");

                                        //***************************//
                                        //***** Error Code LOW *****//
                                        //***************************//

                                        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Error Code LOW (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO + ")");

                                        //Error code HIGH = no error code:[Hx(0x20), Dec(32)]
                                        //Error code LOW = no error code:[Hx(0x20), Dec(32)]
                                        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeHI == 32 && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmErrorCodeLO == 32) {

                                            //***************************//
                                            //***** Operation State *****//
                                            //***************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Operation State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState + ")");

                                            //Operation state = Stop:[0x00]
                                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 0) {
                                                AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = false;
                                            }

                                            //Operation state = Operate:[0x01]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 1) {
                                                AppGlobals.ViewId_imagebutton3_imagebutton22_actionup = true;
                                            }

                                            //Operation state = Error stop:[0x02]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationState == 2) {
                                                resetguardtimeRinnai21HomeScreen();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            } else {
                                                resetguardtimeRinnai21HomeScreen();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //*************************//
                                            //***** Burning State *****//
                                            //*************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Burning State (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState + ")");

                                            //Burning state = Extinguish:[0x00]
                                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 0) {
                                                //resetguardtimeRinnai21HomeScreen();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai21HomeScreen.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);
                                                //
                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            }

                                            //Burning state = Ignite:[0x01]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 1) {
                                                resetguardtimeRinnai21HomeScreen();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai21HomeScreen.this, Rinnai22IgnitionSequence.class);
                                                startActivity(intent);
                                                finish();

                                                Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai22IgnitionSequence).");
                                            }

                                            //Burning state = Thermostat:[0x02]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 2) {
                                                //resetguardtimeRinnai21HomeScreen();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai21HomeScreen.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);
                                                //
                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            }

                                            //Burning state = Thermostat OFF:[0x03]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmBurningState == 3) {
                                                //resetguardtimeRinnai21HomeScreen();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai21HomeScreen.this, Rinnai21HomeScreen.class);
                                                //startActivity(intent);
                                                //
                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai21HomeScreen).");
                                            } else {
                                                resetguardtimeRinnai21HomeScreen();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //**************************//
                                            //***** Operation Mode *****//
                                            //**************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Operation Mode (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode + ")");

                                            //Operation mode = Mode not exist:[0x00]
                                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 0) {
                                                //resetguardtimeRinnai21HomeScreen();
                                                //startupCheckTimer.cancel();
                                                //isClosing = true;
                                                //intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                                //startActivity(intent);
                                                //
                                                //finish();
                                                //Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //Operation mode = Flame mode:[0x01]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 1) {
                                                AppGlobals.Button_flame_settemp_actionvisible = true;
                                            }

                                            //Operation mode = Thermostat mode:[0x02]
                                            else if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 2) {
                                                AppGlobals.Button_flame_settemp_actionvisible = false;
                                            } else {
                                                resetguardtimeRinnai21HomeScreen();
                                                startupCheckTimer.cancel();
                                                isClosing = true;
                                                intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                                startActivity(intent);

                                                finish();
                                                Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                            }

                                            //***********************//
                                            //***** Flame Level *****//
                                            //***********************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Flame Level (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmFlameLevel + ")");

                                            ViewId_textview6.setText("   " + String.valueOf(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmFlameLevel) + "   ");

                                            //***** seekbar - ViewId_myseekbar *****//
                                            switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmFlameLevel) {
                                                case 1:
                                                    ViewId_myseekbar.setProgress(0);
                                                    break;
                                                case 2:
                                                    ViewId_myseekbar.setProgress(25);
                                                    break;
                                                case 3:
                                                    ViewId_myseekbar.setProgress(50);
                                                    break;
                                                case 4:
                                                    ViewId_myseekbar.setProgress(75);
                                                    break;
                                                case 5:
                                                    ViewId_myseekbar.setProgress(100);
                                                    break;
                                                default:
                                                    break;
                                            }

                                            //****************************//
                                            //***** Economy Function *****//
                                            //****************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Economy Function (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmEconomyFunction + ")");

                                            //Economy function = OFF:[0x00]
                                            //Economy function = ON:[0x01]
                                            switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmEconomyFunction) {
                                                case 0:
                                                    AppGlobals.ViewId_imagebutton2_actionup = false;
                                                    ViewId_imagebutton2.setImageResource(R.drawable.economy);
                                                    break;
                                                case 1:
                                                    AppGlobals.ViewId_imagebutton2_actionup = true;
                                                    ViewId_imagebutton2.setImageResource(R.drawable.economy_white_press);
                                                    break;
                                                default:
                                                    break;
                                            }

                                            //****************************//
                                            //***** Room Temperature *****//
                                            //****************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Room Temperature (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmRoomTemperature + ")");

                                            ViewId_textview3 = (TextView) findViewById(R.id.textView3);

                                            ViewId_textview3.setText(String.valueOf(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmRoomTemperature) + "°");

                                            ViewId_textview58 = (TextView) findViewById(R.id.textView58);

                                            ViewId_textview58.setText(String.valueOf(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmRoomTemperature) + "°");

                                            //***************************//
                                            //***** Set Temperature *****//
                                            //***************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Set Temperature (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmSetTemperature + ")");

                                            ViewId_textview7.setText("  " + String.valueOf(AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmSetTemperature) + "  ");

                                            //***** seekbar - ViewId_myseekbar2 *****//
                                            switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmSetTemperature) {
                                                case 16:
                                                    ViewId_myseekbar2.setProgress(0);
                                                    break;
                                                case 17:
                                                    ViewId_myseekbar2.setProgress(7);
                                                    break;
                                                case 18:
                                                    ViewId_myseekbar2.setProgress(14);
                                                    break;
                                                case 19:
                                                    ViewId_myseekbar2.setProgress(21);
                                                    break;
                                                case 20:
                                                    ViewId_myseekbar2.setProgress(29);
                                                    break;
                                                case 21:
                                                    ViewId_myseekbar2.setProgress(36);
                                                    break;
                                                case 22:
                                                    ViewId_myseekbar2.setProgress(43);
                                                    break;
                                                case 23:
                                                    ViewId_myseekbar2.setProgress(50);
                                                    break;
                                                case 24:
                                                    ViewId_myseekbar2.setProgress(57);
                                                    break;
                                                case 25:
                                                    ViewId_myseekbar2.setProgress(64);
                                                    break;
                                                case 26:
                                                    ViewId_myseekbar2.setProgress(71);
                                                    break;
                                                case 27:
                                                    ViewId_myseekbar2.setProgress(79);
                                                    break;
                                                case 28:
                                                    ViewId_myseekbar2.setProgress(86);
                                                    break;
                                                case 29:
                                                    ViewId_myseekbar2.setProgress(93);
                                                    break;
                                                case 30:
                                                    ViewId_myseekbar2.setProgress(100);
                                                    break;
                                                default:
                                                    break;
                                            }

                                            //*************************//
                                            //***** Timers Active *****//
                                            //*************************//

                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: Timer Active (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmTimerActive + ")");

                                            ViewId_imageview = (ImageView) findViewById(R.id.imageView);

                                            switch (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmTimerActive) {
                                                case 0:
                                                    ViewId_imageview.setVisibility(View.INVISIBLE);
                                                    break;
                                                case 1:
                                                    ViewId_imageview.setVisibility(View.VISIBLE);
                                                    break;
                                                default:
                                                    break;
                                            }

                                            if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 2 && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmEconomyFunction == 1) {
                                                AppGlobals.ShowHints_economy_actionvisible = true;
                                                AppGlobals.ShowHints_flame_actionvisible = false;
                                                AppGlobals.ShowHints_settemp_actionvisible = true;
                                            } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmEconomyFunction == 1) {
                                                AppGlobals.ShowHints_economy_actionvisible = true;
                                                AppGlobals.ShowHints_flame_actionvisible = false;
                                                AppGlobals.ShowHints_settemp_actionvisible = false;
                                            } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 1) {
                                                AppGlobals.ShowHints_economy_actionvisible = false;
                                                AppGlobals.ShowHints_flame_actionvisible = true;
                                                AppGlobals.ShowHints_settemp_actionvisible = false;
                                            } else if (AppGlobals.ViewId_imageview_navview7_actionup == false && AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).rfwmOperationMode == 2) {
                                                AppGlobals.ShowHints_economy_actionvisible = false;
                                                AppGlobals.ShowHints_flame_actionvisible = false;
                                                AppGlobals.ShowHints_settemp_actionvisible = true;
                                            } else {
                                                AppGlobals.ShowHints_economy_actionvisible = false;
                                                AppGlobals.ShowHints_flame_actionvisible = false;
                                                AppGlobals.ShowHints_settemp_actionvisible = false;
                                            }

                                            //Check if Standby Screen is set.
                                            //Operation state = Stop:[0x00]
                                            if (AppGlobals.ViewId_imagebutton3_imagebutton22_actionup == false) {
                                                setStandby();
                                            }
                                            //Operation state = Operate:[0x01]
                                            else {
                                                setStandby();

                                                setShowHints();

                                                setFlameSettempVisibility();
                                            }

                                        } else {
                                            resetguardtimeRinnai21HomeScreen();
                                            startupCheckTimer.cancel();
                                            isClosing = true;
                                            intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
                                            startActivity(intent);

                                            finish();
                                            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26Fault).");
                                        }
                                    }
                                    //Main power switch = OFF:[0x01]
                                    else {
                                        resetguardtimeRinnai21HomeScreen();
                                        startupCheckTimer.cancel();
                                        isClosing = true;
                                        intent = new Intent(Rinnai21HomeScreen.this, Rinnai26PowerOff.class);
                                        startActivity(intent);

                                        finish();
                                        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen_clientCallBackTCP: startActivity(Rinnai26PowerOff).");
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: clientCallBackTCP");
    }

    //***** RN171_DEVICE_GET_STATUS *****//
    public void Tx_RN171DeviceGetStatus() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_22,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceGetStatus(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_FLAME *****//
    public void Tx_RN171DeviceSetFlame() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_32," + String.format("%02X", AppGlobals.ViewId_textview6_flamevalue) + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceSetFlame(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_TEMP *****//
    public void Tx_RN171DeviceSetTemp() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_33," + String.format("%02X", AppGlobals.ViewId_textview7_settempvalue) + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceSetTemp(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_OP_STATE *****//
    public void Tx_RN171DeviceSetOpState() {

        int myInt_Opstate = (AppGlobals.ViewId_imagebutton3_imagebutton22_actionup) ? 1 : 0;

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_34," + String.format("%02X", myInt_Opstate) + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceSetOpState(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_ECON *****//
    public void Tx_RN171DeviceSetEcon() {

        int myInt_Econ = (AppGlobals.ViewId_imagebutton2_actionup) ? 1 : 0;

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_35," + String.format("%02X", myInt_Econ) + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceSetEcon(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SET_DEVICENAME *****//
    public void Tx_RN171DeviceSetDeviceName() {

        String rn171SetOptReplaceDeviceName = selected_edittextrinnai21devicename.getText() + "";

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).WiFiHardware == 0) {
            rn171SetOptReplaceDeviceName = rn171SetOptReplaceDeviceName.replace(' ', (char) 0x1B);
        }

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_38," + rn171SetOptReplaceDeviceName + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai21HomeScreen: Tx_RN171DeviceSetDeviceName(Exception - " + e + ")");
        }
    }

    //****************************************//
    //***** startCommunicationErrorFault *****//
    //****************************************//

    public void startCommunicationErrorFault() {
        AppGlobals.CommErrorFault.setCurrentActivity(this);

        AppGlobals.CommErrorFault.checkRN171DeviceCommunication();
    }

    //***********************************//
    //***** timereventCallBackTimer *****//
    //***********************************//

    @Override
    public void timereventCallBackTimer(int timerID) {
        resetguardtimeRinnai21HomeScreen();
        startupCheckTimer.cancel();
        isClosing = true;
        intent = new Intent(Rinnai21HomeScreen.this, Rinnai26Fault.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai21HomeScreen_timereventCallBackTimer: startActivity(Rinnai26Fault).");
    }

    //************************//
    //***** goToActivity *****//
    //************************//

    //Login
    //public void goToActivity_Rinnai17_Login(View view) {
    //    resetguardtimeRinnai21HomeScreen();
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
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //    finish();
    //}

    //Home Screen
    //public void goToActivity_Rinnai21_Home_Screen (View view){
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent (this, Rinnai21HomeScreen.class);
    //    startActivity(intent);
    //}

    //Timers a - Scheduled Timers
    public void goToActivity_Rinnai33a_Timers(View view) {
        resetguardtimeRinnai21HomeScreen();
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai33aTimers.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai21HomeScreen_onClick: startActivity(Rinnai33aTimers).");
    }

    //Timers b - Scheduled Timer
    //public void goToActivity_Rinnai33b_Timers(View view) {
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33bTimers.class);
    //    startActivity(intent);
    //}

    //Visit Rinnai
    public void goToActivity_Rinnai35_Visit_Rinnai(View view) {
        resetguardtimeRinnai21HomeScreen();
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai35VisitRinnai.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai21HomeScreen_onClick: startActivity(Rinnai35VisitRinnai).");
    }

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
    public void goToActivity_Rinnai34_Lighting(View view) {
        resetguardtimeRinnai21HomeScreen();
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai34Lighting.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai21HomeScreen_onClick: startActivity(Rinnai34Lighting).");
    }

    //Rinnai Account
    public void goToActivity_Rinnai11h_Registration(View view) {
        resetguardtimeRinnai21HomeScreen();
        startupCheckTimer.cancel();
        isClosing = true;
        Intent intent = new Intent(this, Rinnai11hRegistration.class);
        startActivity(intent);

        finish();
        Log.d("myApp", "Rinnai21HomeScreen_onClick: startActivity(Rinnai11hRegistration).");
    }

    //Fault
    //public void goToActivity_Rinnai26_Fault(View view) {
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26Fault.class);
    //    startActivity(intent);
    //}

    //Fault - Service Fault Codes
    //public void goToActivity_Rinnai33_Service_Fault_Codes(View view) {
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33ServiceFaultCodes.class);
    //    startActivity(intent);
    //}

    //Power Off
    //public void goToActivity_Rinnai26_Power_Off(View view) {
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai26PowerOff.class);
    //    startActivity(intent);
    //}

    //Ignition Sequence
    //public void goToActivity_Rinnai22_Ignition_Sequence(View view) {
    //    resetguardtimeRinnai21HomeScreen();
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai22IgnitionSequence.class);
    //    startActivity(intent);
    //}

}
