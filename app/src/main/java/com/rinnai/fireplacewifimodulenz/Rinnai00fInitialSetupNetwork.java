package com.rinnai.fireplacewifimodulenz;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jconci on 14/05/2018.
 */

public class Rinnai00fInitialSetupNetwork extends MillecActivityBase
        implements ActivityClientInterfaceTCP, ActivityServerInterfaceUDP {

    Button ViewId_button43;

    TextView ViewId_textview163;
    TextView ViewId_textview164;
    TextView ViewId_textview165;
    TextView ViewId_textview166;
    TextView selected_scrollviewrowrinnai00finitialsetupnetwork;

    EditText selected_scrollviewrowrinnai00finitialsetupnetworkpassword;
    EditText ViewId_edittext30;

    ViewGroup ViewId_include_scanning_initialsetupnetwork;
    ViewGroup ViewId_include_scrollview_lockout_initialsetupnetwork;
    ViewGroup ViewId_include_detailsaccepted_initialsetupnetwork;

    LinearLayout ViewId_linearlayout_network_password;
    LinearLayout ViewId_linearlayout_network_row;

    TableLayout ViewId_wifiaccesspoint_tableLayout;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    Timer networkCheckTimer;
    int networkCheckTimerCount;
    int NETWORK_TIMER_CHECK_TIMEOUT_COUNT = 30; //counts of Timer before result is acted on

    Intent intent;

    boolean isClosing = false;
    boolean isDeviceScanResult = false;
    boolean isDeviceScanNetwork = false;
    boolean isNetworkCheck = false;

    boolean scrollviewrow_pressed = false;

    Timer waitingUDPTimer;
    String wifiModuleNetWorkName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai00f_initialsetup_network);

        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onCreate.");

        setRinnai00fInitialSetupNetwork();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button43 (OK) *****//
        ViewId_button43 = (Button) findViewById(R.id.button43);
        ViewId_textview166 = (TextView) findViewById(R.id.textView166);
        ViewId_linearlayout_network_password = (LinearLayout) findViewById(R.id.linearlayout_network_password);
        selected_scrollviewrowrinnai00finitialsetupnetworkpassword = (EditText) findViewById(R.id.editText30);

        ViewId_button43.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview166.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_textview166.setTextColor(Color.parseColor("#FFFFFFFF"));


                        Log.d("myApp", "Rinnai00fInitialSetupNetwork_onTouch" + selected_scrollviewrowrinnai00finitialsetupnetworkpassword);

                        if (scrollviewrow_pressed == true && !selected_scrollviewrowrinnai00finitialsetupnetworkpassword.getText().toString().equals("Type your password") && !selected_scrollviewrowrinnai00finitialsetupnetworkpassword.getText().toString().equals("")) {
                            Tx_RN171DeviceNetworkSetup();

                            hideSoftKeyboard_initialsetupf();

                            //***** include - ViewId_include_scanning_initialsetupnetwork *****//
                            ViewId_include_scanning_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scanning_initialsetupnetwork);

                            ViewId_include_scanning_initialsetupnetwork.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_scrollview_lockout_initialsetupnetwork *****//
                            ViewId_include_scrollview_lockout_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scrollview_lockout_initialsetupnetwork);

                            ViewId_include_scrollview_lockout_initialsetupnetwork.setVisibility(View.VISIBLE);

                            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

                            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.VISIBLE);

                            //***** include - ViewId_include_detailsaccepted_initialsetupnetwork *****//
                            ViewId_include_detailsaccepted_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_detailsaccepted_initialsetupnetwork);

                            ViewId_include_detailsaccepted_initialsetupnetwork.setVisibility(View.VISIBLE);

                            startNetworkCheck();

                            waitingUDPTimer = new Timer();
                            waitingUDPTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopUDP();
                                    showAlert();
                                }
                            }, 45000);
                        } else {
                            ObjectAnimator
                                    .ofFloat(ViewId_linearlayout_network_password, "translationX", 0, 10, -10, 10, -10, 10, -10, 10, -10, 10, -10, 0)
                                    .setDuration(500)
                                    .start();
                            Toast.makeText(Rinnai00fInitialSetupNetwork.this, "1- Select Network, \n2- Type Password, \n3- Press OK.",
                                    Toast.LENGTH_LONG).show();
                        }

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview166.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***************************//
        //***** OnClickListener *****//
        //***************************//

        //***** OnClickListener - ViewId_edittext30 (Network password) *****//
        ViewId_edittext30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai00fInitialSetupNetwork_onClick: editText30.");
            }
        });

        //*********************************//
        //***** OnFocusChangeListener *****//
        //*********************************//

        //***** OnFocusChangeListener - ViewId_edittext30 (Network password) *****//
        ViewId_edittext30.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.d("myApp", "Rinnai00fInitialSetupNetwork_onFocusChange editText30: Got the focus.");

                    ViewId_edittext30 = (EditText) view.findViewById(R.id.editText30);
                    ViewId_edittext30.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    showSoftKeyboard_initialsetupf(ViewId_edittext30);

                } else {
                    Log.d("myApp", "Rinnai00fInitialSetupNetwork_onFocusChange editText30: Lost the focus.");

                    hideSoftKeyboard_initialsetupf();
                }
            }
        });

        //*******************************//
        //***** TextChangedListener *****//
        //*******************************//

        //***** TextChangedListener - ViewId_edittext30 (Network password) *****//
        ViewId_edittext30.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("myApp", "Rinnai00fInitialSetupNetwork_afterTextChanged: editText30.");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                Log.d("myApp", "Rinnai00fInitialSetupNetwork_beforeTextChanged: editText30.");
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.d("myApp", "Rinnai00fInitialSetupNetwork_onTextChanged: editText30.");

                //***** Text "Type your password" - ViewId_textview165 *****//
                ViewId_textview165 = (TextView) findViewById(R.id.textView165);
                if (s.length() == 0) {
                    ViewId_textview165.setVisibility(View.VISIBLE);
                } else {
                    ViewId_textview165.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onRestart.");

        if (isNetworkCheck == true) {
            hideSoftKeyboard_initialsetupf();

            //***** include - ViewId_include_scanning_initialsetupnetwork *****//
            ViewId_include_scanning_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scanning_initialsetupnetwork);

            ViewId_include_scanning_initialsetupnetwork.setVisibility(View.INVISIBLE);

            //***** include - ViewId_include_scrollview_lockout_initialsetupnetwork *****//
            ViewId_include_scrollview_lockout_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scrollview_lockout_initialsetupnetwork);

            ViewId_include_scrollview_lockout_initialsetupnetwork.setVisibility(View.VISIBLE);

            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_detailsaccepted_initialsetupnetwork *****//
            ViewId_include_detailsaccepted_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_detailsaccepted_initialsetupnetwork);

            ViewId_include_detailsaccepted_initialsetupnetwork.setVisibility(View.VISIBLE);

            isClosing = false;
            isNetworkCheck = false;

            startNetworkCheck();

        } else {
            //***** Text "Type your password" - ViewId_textview165 *****//
            ViewId_textview165 = (TextView) findViewById(R.id.textView165);
            ViewId_textview165.setVisibility(View.VISIBLE);

            //***** Network password - ViewId_edittext30 *****//
            ViewId_edittext30 = (EditText) findViewById(R.id.editText30);
            ViewId_edittext30.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ViewId_edittext30.setText("");

            //***** include - ViewId_include_scanning_initialsetupnetwork *****//
            ViewId_include_scanning_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scanning_initialsetupnetwork);

            ViewId_include_scanning_initialsetupnetwork.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_scrollview_lockout_initialsetupnetwork *****//
            ViewId_include_scrollview_lockout_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scrollview_lockout_initialsetupnetwork);

            ViewId_include_scrollview_lockout_initialsetupnetwork.setVisibility(View.VISIBLE);

            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.INVISIBLE);

            isClosing = false;
            isDeviceScanResult = false;

            //***** TX WiFi - TCP *****//
            startTxRN171DeviceScanResult();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onStop.");

        if (isNetworkCheck == true) {
            startupCheckTimer.cancel();
            networkCheckTimer.cancel();
        } else {
            startupCheckTimer.cancel();
        }

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai00fInitialSetupNetwork_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai00fInitialSetupNetwork_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //****************************************//
    //***** startTxRN171DeviceScanResult *****//
    //****************************************//

    public void startTxRN171DeviceScanResult() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai00fInitialSetupNetwork: Tick.. " + startupCheckTimerCount);

                if (startupCheckTimerCount >= 5) {

                    if (startupCheckTimerCount >= 15) {
                        startupCheckTimerCount = 0;
                        isDeviceScanNetwork = true;
                    }

                    if (isDeviceScanResult == false) {

                        if (startupCheckTimerCount % 5 == 0) {

                            if (isDeviceScanNetwork == false) {
                                Tx_RN171DeviceScanResult();
                                Log.d("myApp", "Rinnai00fInitialSetupNetwork - Tx_RN171DeviceScanResult");
                            } else {
                                Tx_RN171DeviceScanNetwork();
                                isDeviceScanNetwork = false;
                                Log.d("myApp", "Rinnai00fInitialSetupNetwork - Tx_RN171DeviceScanNetwork");
                            }
                        }
                    } else {
                        startupCheckTimer.cancel();
                    }
                }

                startupCheckTimerCount++;

            }

        }, 0, 1000);

    }

    //*******************************************//
    //***** setRinnai00fInitialSetupNetwork *****//
    //*******************************************//

    public void setRinnai00fInitialSetupNetwork() {

        try {
            //***** textview163 - Current Device Network Details *****//
            ViewId_textview163 = (TextView) findViewById(R.id.textView163);

            final String currentNetworkName = NetworkFunctions.getCurrentAccessPointName(this);

            wifiModuleNetWorkName = currentNetworkName;
            ViewId_textview163.setText(currentNetworkName);

            //***** Text "Type you password" - ViewId_textview165 *****//
            ViewId_textview165 = (TextView) findViewById(R.id.textView165);
            ViewId_textview165.setVisibility(View.VISIBLE);

            //***** Network Password - ViewId_edittext30 *****//
            ViewId_edittext30 = (EditText) findViewById(R.id.editText30);
            ViewId_edittext30.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            //***** include - ViewId_include_scanning_initialsetupnetwork *****//
            ViewId_include_scanning_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scanning_initialsetupnetwork);

            ViewId_include_scanning_initialsetupnetwork.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_scrollview_lockout_initialsetupnetwork *****//
            ViewId_include_scrollview_lockout_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scrollview_lockout_initialsetupnetwork);

            ViewId_include_scrollview_lockout_initialsetupnetwork.setVisibility(View.VISIBLE);

            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.INVISIBLE);

            //***** textview164 - Scanning for Networks, Networks Found *****//
            ViewId_textview164 = (TextView) findViewById(R.id.textView164);

            ViewId_textview164.setText("Scanning for Networks");

        } catch (Exception e) {
            Log.d("myApp", "Rinnai00fInitialSetupNetwork: setRinnai00fInitialSetupNetwork(Exception - " + e + ")");
        }

        //***** TX WiFi - TCP *****//
        Tx_RN171DeviceScanNetwork();
        Log.d("myApp", "Rinnai00fInitialSetupNetwork - Tx_RN171DeviceScanNetwork");

        startTxRN171DeviceScanResult();
    }

    //*******************************************************//
    //***** scrollviewrowrinnai00finitialsetupnetworkOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowrinnai00finitialsetupnetworkOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            scrollviewrow_pressed = true;

            //Get Selected Text
            selected_scrollviewrowrinnai00finitialsetupnetwork = ((TextView) v.findViewById(R.id.textView167));

            //debug
            Log.d("myApp", "Rinnai00fInitialSetupNetwork_scrollviewrowrinnai00finitialsetupnetworkOnClickListener():" + selected_scrollviewrowrinnai00finitialsetupnetwork.getText() + "");

            updateScrollViewTableLayoutRinnai00finitialsetupnetworkDetails();

            //Highlight Selection
            ViewId_linearlayout_network_row = ((LinearLayout) v.findViewById(R.id.linearlayout_network_row));

            ViewId_linearlayout_network_row.setBackgroundColor(Color.parseColor("#32FFFFFF"));
        }
    };

    //**************************************************************************//
    //***** updateScrollViewTableLayoutRinnai00finitialsetupnetworkDetails *****//
    //**************************************************************************//

    public void updateScrollViewTableLayoutRinnai00finitialsetupnetworkDetails() {

        TableLayout ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

        for (int a = 0; a <= AppGlobals.WiFiAccessPointInfo_List.size() - 1; a++) {

            View ViewId_scrollview_row_rinnai00f_initialsetupnetwork = ViewId_wifiaccesspoint_tableLayout.getChildAt(a);

            //Highlight Selection
            ViewId_linearlayout_network_row = ((LinearLayout) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.linearlayout_network_row));

            ViewId_linearlayout_network_row.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    //*****************************//
    //***** startNetworkCheck *****//
    //*****************************//

    public void startNetworkCheck() {

        //start timer to peridocally check the AP we are on

        isNetworkCheck = true;

        this.networkCheckTimerCount = 0;

        this.networkCheckTimer = new Timer();

        this.networkCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                networkCheckTimerEvent();

            }

        }, 0, 1000);
    }

    //**********************************//
    //***** networkCheckTimerEvent *****//
    //**********************************//

    public void networkCheckTimerEvent() {

        final String currentNetworkName = NetworkFunctions.getCurrentAccessPointName(this);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.d("myApp", "currentNetworkName: " + currentNetworkName);

                String networkName = currentNetworkName;

                try {
                    Log.d("myApp", "networkName: " + networkName);

                    Log.d("myApp", "networkCheckTimerEvent():'runOnUIThread'");

                    String ssidText = selected_scrollviewrowrinnai00finitialsetupnetwork.getText() + "";

                    AppGlobals.PushedSSID = ssidText;

                    Log.d("myApp", "ssidText: " + ssidText);

                    Log.d("myApp", "networkCheckTimerEvent():'Comp:" + networkName + " & " + ssidText);

                    if (networkName.compareTo(ssidText) != 0) {
                        Log.d("myApp", "networkCheckTimerEvent():'AP Does NOT match' :(");
                    } else {
                        Log.d("myApp", "networkCheckTimerEvent():'AP Does match' :)");

                        //Does Match
                        startupCheckTimer.cancel();
                        networkCheckTimer.cancel();
                        setupUDP();
//                        isClosing = true;
//                        intent = new Intent(Rinnai00fInitialSetupNetwork.this, Rinnai00dInitialSetupComplete.class);
//                        startActivity(intent);
//                        finish();
//                        Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00dInitialSetupComplete).");

                    }
                } catch (Exception e) {
                    Log.d("myApp", "networkCheckTimerEvent():'Exception'[" + e.getMessage() + "]");
                }

                if (networkCheckTimerCount > NETWORK_TIMER_CHECK_TIMEOUT_COUNT) {
                    startupCheckTimer.cancel();
                    networkCheckTimer.cancel();
                    waitingUDPTimer.cancel();
                    isClosing = true;
                    intent = new Intent(Rinnai00fInitialSetupNetwork.this, Rinnai00eInitialSetupAlmost.class);
                    intent.putExtra("WIFIMODULE", wifiModuleNetWorkName);
                    startActivity(intent);
                    finish();
                    Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00eInitialSetupAlmost).");

                } else {
                    networkCheckTimerCount++;
                }
            }
        });
    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard_initialsetupf() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard_initialsetupf(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    //************************//
    //***** UDP - Client *****//
    //************************//
    private void showAlert() {
        runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(Rinnai00fInitialSetupNetwork.this).create();
                alertDialog.setTitle("Setup Failed");
                alertDialog.setMessage("Please make sure the password you entered for the home network is correct. This App will be off for you to restart the setup again.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                alertDialog.show();
            }
        });
    }

    private void setupUDP() {
        try {
            //AppGlobals.UDPSrv.stopServer();
            AppGlobals.UDPSrv.setCurrentActivity(this);
            AppGlobals.UDPSrv.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiUDP", "Rinnai17Login: appStart(Exception - " + e + ")");
        }
    }

    private void stopUDP(){
        AppGlobals.UDPSrv.stopServer();
    }

    @Override
    public void serverCallBackUDP(String text) {
        if (wifiModuleNetWorkName != null) {
            if (text.contains(wifiModuleNetWorkName)) {
                stopUDP();
                waitingUDPTimer.cancel();
                isClosing = true;
                intent = new Intent(Rinnai00fInitialSetupNetwork.this, Rinnai00dInitialSetupComplete.class);
                startActivity(intent);
                finish();
                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: startActivity(Rinnai00dInitialSetupComplete).");
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


        if (isClosing == true) {
            return;
        }

        if (commandID != null) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (pType.contains("14")) {

                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);
                            //ViewId_wifiaccesspoint_tableLayout.setOnTouchListener(new AutoTimerTableTouchListener());

                            //clear the table, start with blank table
                            ViewId_wifiaccesspoint_tableLayout.removeAllViews();

                            int id = 0;

                            for (int i = 0; i <= AppGlobals.WiFiAccessPointInfo_List.size() - 1; i++) {
                                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: WiFi Access Point Name (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointName + ")");
                                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: WiFi Access Point SignalStrength (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSignalStrength + ")");
                                Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork_clientCallBackTCP: WiFi Access Point SecurityType (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSecurityType + ")");

                                View ViewId_scrollview_row_rinnai00f_initialsetupnetwork = getLayoutInflater().inflate(R.layout.scrollview_row_rinnai00f_initialsetupnetwork, null, false);

                                ViewId_scrollview_row_rinnai00f_initialsetupnetwork.setId(id);

                                ViewId_scrollview_row_rinnai00f_initialsetupnetwork.setMinimumHeight(50);

                                //clean text
                                //nextSSID = ""+nextSSID.subSequence(nextSSID.indexOf("\"")+1,nextSSID.indexOf("\"",1));

                                //add listener
                                ViewId_scrollview_row_rinnai00f_initialsetupnetwork.setOnClickListener(scrollviewrowrinnai00finitialsetupnetworkOnClickListener);//add OnClickListener Here

                                //set it on the row - textView167, wifiaccesspointName
                                ((TextView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.textView167)).setText(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointName);

                                //set it on the row - textView168, wifiaccesspointSignalStrength
                                //((TextView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.textView168)).setText(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSignalStrength);
                                int decimalStrenth = Integer.parseInt(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSignalStrength, 16);
                                if (decimalStrenth >= 225) {
                                    ((ImageView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.wifiStrength)).setImageResource(R.mipmap.wifi_siginal_bars_5);
                                } else if (decimalStrenth >= 188) {
                                    ((ImageView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.wifiStrength)).setImageResource(R.mipmap.wifi_siginal_bars_4);
                                } else if (decimalStrenth >= 185) {
                                    ((ImageView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.wifiStrength)).setImageResource(R.mipmap.wifi_siginal_bars_3);
                                } else if (decimalStrenth >= 175) {
                                    ((ImageView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.wifiStrength)).setImageResource(R.mipmap.wifi_siginal_bars_2);
                                } else {
                                    ((ImageView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.wifiStrength)).setImageResource(R.mipmap.wifi_siginal_bars_1);
                                }

                                //set it on the row - textView169, wifiaccesspointSecurityType
                                int networksecuritytypeValue = Integer.parseInt(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSecurityType.toString());

                                String networksecuritytypeInfo = "NA";

                                switch (networksecuritytypeValue) {
                                    case 0:// OPEN
                                        networksecuritytypeInfo = "OPEN";
                                        break;
                                    case 1:// WEP (64 or 128)
                                        networksecuritytypeInfo = "WEP";
                                        break;
                                    case 2:// WPA1
                                        networksecuritytypeInfo = "WPA1";
                                        break;
                                    case 3:// MIXED
                                        networksecuritytypeInfo = "MIXED";
                                        break;
                                    case 4:// WPA2
                                        networksecuritytypeInfo = "WPA2";
                                        break;
                                    case 5:// Enterprise WEP
                                        networksecuritytypeInfo = "Enterprise WEP";
                                        break;
                                    case 6:// Enterprise WPA1
                                        networksecuritytypeInfo = "Enterprise WPA1";
                                        break;
                                    case 7:// Enterprise WPA mixed
                                        networksecuritytypeInfo = "Enterprise WPA mixed";
                                        break;
                                    case 8:// Enterprise WPA2
                                        networksecuritytypeInfo = "Enterprise WPA2";
                                        break;
                                    case 9:// Enterprise NO security
                                        networksecuritytypeInfo = "Enterprise NO security";
                                        break;
                                }

                                ((TextView) ViewId_scrollview_row_rinnai00f_initialsetupnetwork.findViewById(R.id.textView169)).setText(networksecuritytypeInfo);

                                //Add the Row to the table
                                ViewId_wifiaccesspoint_tableLayout.addView(ViewId_scrollview_row_rinnai00f_initialsetupnetwork);

                                //Next
                                id++;

                            }

                            //***** include - ViewId_include_scanning_initialsetupnetwork *****//
                            ViewId_include_scanning_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scanning_initialsetupnetwork);

                            ViewId_include_scanning_initialsetupnetwork.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_scrollview_lockout_initialsetupnetwork *****//
                            ViewId_include_scrollview_lockout_initialsetupnetwork = (ViewGroup) findViewById(R.id.include_scrollview_lockout_initialsetupnetwork);

                            ViewId_include_scrollview_lockout_initialsetupnetwork.setVisibility(View.INVISIBLE);

                            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

                            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.VISIBLE);

                            //***** textview164 - Scanning for Networks, Networks Found *****//
                            ViewId_textview164 = (TextView) findViewById(R.id.textView164);

                            ViewId_textview164.setText("Available Networks.");

                            isDeviceScanResult = true;

                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: clientCallBackTCP");
    }

    //***** RN171_DEVICE_NETWORK_SETUP *****//
    public void Tx_RN171DeviceNetworkSetup() {

        String rn171SetOptReplaceNetwork = selected_scrollviewrowrinnai00finitialsetupnetwork.getText() + "";

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).WiFiHardware == 0) {
            rn171SetOptReplaceNetwork = rn171SetOptReplaceNetwork.replace(' ', (char) 0x1B);
        }

        String rn171SetOptReplaceNetworkPassword = selected_scrollviewrowrinnai00finitialsetupnetworkpassword.getText() + "";

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).WiFiHardware == 0) {
            rn171SetOptReplaceNetworkPassword = rn171SetOptReplaceNetworkPassword.replace(' ', (char) 0x1B);
        }

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_11," + rn171SetOptReplaceNetwork + "," + rn171SetOptReplaceNetworkPassword + ",E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: Tx_RN171DeviceNetworkSetup(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SCAN_NETWORK *****//
    public void Tx_RN171DeviceScanNetwork() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_13,E\n", false);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: Tx_RN171DeviceScanNetwork(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_SCAN_RESULT *****//
    public void Tx_RN171DeviceScanResult() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_14,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai00fInitialSetupNetwork: Tx_RN171DeviceScanResult(Exception - " + e + ")");
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

        finish();
        Log.d("myApp", "Rinnai00fInitialSetupNetwork_onClick: startActivity(Rinnai21HomeScreen).");
    }

    //Timers a - Scheduled Timers
    //public void goToActivity_Rinnai33a_Timers(View view) {
    //    startupCheckTimer.cancel();
    //    Intent intent = new Intent(this, Rinnai33aTimers.class);
    //    startActivity(intent);
    //}

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