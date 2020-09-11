package com.rinnai.fireplacewifimodulenz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jconci on 13/09/2017.
 */

public class Rinnai37Network extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    Button ViewId_button9;
    Button ViewId_button13;

    ImageButton ViewId_imagebutton10;

    TextView ViewId_textview25;
    TextView ViewId_textview26;
    TextView ViewId_textview27;
    TextView selected_scrollviewrowrinnai37network;
    TextView ViewId_textview70;

    EditText selected_scrollviewrowrinnai37networkpassword;
    EditText ViewId_edittext;

    ViewGroup ViewId_include_scanning_network;
    ViewGroup ViewId_include_scrollview_lockout_network;
    ViewGroup ViewId_include_detailsaccepted_network;

    LinearLayout ViewId_linearlayout_network_password;
    LinearLayout ViewId_linearlayout_network_row;

    TableLayout ViewId_wifiaccesspoint_tableLayout;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    boolean isClosing = false;
    boolean isDeviceScanResult = false;
    boolean isDeviceScanNetwork = false;

    boolean scrollviewrow_pressed = false;

    boolean selected_edittextfirsttime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai37_network);

        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onCreate.");

        setRinnai37Network();

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button9 (OK) *****//
        ViewId_button9 = (Button) findViewById(R.id.button9);
        ViewId_textview27 = (TextView) findViewById(R.id.textView27);
        ViewId_linearlayout_network_password = (LinearLayout) findViewById(R.id.linearlayout_network_password);
        selected_scrollviewrowrinnai37networkpassword = (EditText) findViewById(R.id.editText);

        ViewId_button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview27.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        ViewId_textview27.setTextColor(Color.parseColor("#FFFFFFFF"));

                        Log.d("myApp", "Rinnai37Network_onTouch" + selected_scrollviewrowrinnai37networkpassword);

                        if (scrollviewrow_pressed == true && !selected_scrollviewrowrinnai37networkpassword.getText().toString().equals("Type your password") && !selected_scrollviewrowrinnai37networkpassword.getText().toString().equals("")) {
                            Tx_RN171DeviceNetworkSetup();

                            hideSoftKeyboard();

                            //***** include - ViewId_include_scanning_network *****//
                            ViewId_include_scanning_network = (ViewGroup) findViewById(R.id.include_scanning_network);

                            ViewId_include_scanning_network.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_scrollview_lockout_network *****//
                            ViewId_include_scrollview_lockout_network = (ViewGroup) findViewById(R.id.include_scrollview_lockout_network);

                            ViewId_include_scrollview_lockout_network.setVisibility(View.VISIBLE);

                            //***** imageButton - ViewId_imagebutton10 *****//
                            ViewId_imagebutton10 = (ImageButton) findViewById(R.id.imageButton10);

                            ViewId_imagebutton10.setEnabled(false);

                            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

                            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.VISIBLE);

                            //***** include - ViewId_include_detailsaccepted_network *****//
                            ViewId_include_detailsaccepted_network = (ViewGroup) findViewById(R.id.include_detailsaccepted_network);

                            ViewId_include_detailsaccepted_network.setVisibility(View.VISIBLE);

                        } else {
                            ObjectAnimator
                                    .ofFloat(ViewId_linearlayout_network_password, "translationX", 0, 10, -10, 10, -10, 10, -10, 10, -10, 10, -10, 0)
                                    .setDuration(500)
                                    .start();
                            Toast.makeText(Rinnai37Network.this, "1- Select Network, \n2- Type Password, \n3- Press OK.",
                                    Toast.LENGTH_LONG).show();
                        }

                        selected_edittextfirsttime = false;

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview27.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***************************//
        //***** OnClickListener *****//
        //***************************//
        ViewId_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myApp", "Rinnai37Network_onClick: EditText.");

                ViewId_edittext = (EditText) v.findViewById(R.id.editText);
                //ViewId_edittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                if (selected_edittextfirsttime == false) {
                    ViewId_edittext.setText("");
                    selected_edittextfirsttime = true;
                }

            }
        });

        //***************************//
        //***** OnTouchListener *****//
        //***************************//
        ViewId_button13 = (Button) findViewById(R.id.button13);
        ViewId_textview70 = (TextView) findViewById(R.id.textView70);

        //***** OnTouchListener - button13 (Network Details Accepted - OK) *****//
        ViewId_button13.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_textview70.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_textview70.setTextColor(Color.parseColor("#FF007FFF"));

                        startupCheckTimer.cancel();
                        isClosing = true;
                        System.exit(0);
                        Log.d("myApp", "Rinnai37Network_onTouch: RinnaiWiFi App EXIT.");

                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_textview70.setTextColor(Color.parseColor("#FF007FFF"));
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onRestart.");

        selected_edittextfirsttime = false;

        //***** Network Password - ViewId_edittext *****//
        ViewId_edittext = (EditText) findViewById(R.id.editText);
        ViewId_edittext.setInputType(InputType.TYPE_CLASS_TEXT);
        ViewId_edittext.setText("Type your password");

        //***** include - ViewId_include_scanning_network *****//
        ViewId_include_scanning_network = (ViewGroup) findViewById(R.id.include_scanning_network);

        ViewId_include_scanning_network.setVisibility(View.VISIBLE);

        //***** include - ViewId_include_scrollview_lockout_network *****//
        ViewId_include_scrollview_lockout_network = (ViewGroup) findViewById(R.id.include_scrollview_lockout_network);

        ViewId_include_scrollview_lockout_network.setVisibility(View.VISIBLE);

        //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
        ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

        ViewId_wifiaccesspoint_tableLayout.setVisibility(View.INVISIBLE);

        isClosing = false;
        isDeviceScanResult = false;

        //***** TX WiFi - TCP *****//
        startTxRN171DeviceScanResult();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai37Network_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai37Network_onBackPressed.");

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

                Log.d("myApp", "Rinnai37Network: Tick.. " + startupCheckTimerCount);

                if (startupCheckTimerCount >= 5) {

                    if (startupCheckTimerCount >= 15) {
                        startupCheckTimerCount = 0;
                        isDeviceScanNetwork = true;
                    }

                    if (isDeviceScanResult == false) {

                        if (startupCheckTimerCount % 5 == 0) {

                            if (isDeviceScanNetwork == false) {
                                Tx_RN171DeviceScanResult();
                                Log.d("myApp", "Rinnai37Network - Tx_RN171DeviceScanResult");
                            } else {
                                Tx_RN171DeviceScanNetwork();
                                isDeviceScanNetwork = false;
                                Log.d("myApp", "Rinnai37Network - Tx_RN171DeviceScanNetwork");
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

    //******************************//
    //***** setRinnai37Network *****//
    //******************************//

    public void setRinnai37Network() {

        try {
            //***** textview25 - Current Device Network Details *****//
            ViewId_textview25 = (TextView) findViewById(R.id.textView25);

            final String currentNetworkName = NetworkFunctions.getCurrentAccessPointName(this);

            ViewId_textview25.setText(currentNetworkName);

            //***** Network Password - ViewId_edittext *****//
            ViewId_edittext = (EditText) findViewById(R.id.editText);
            ViewId_edittext.setInputType(InputType.TYPE_CLASS_TEXT);

            //***** include - ViewId_include_scanning_network *****//
            ViewId_include_scanning_network = (ViewGroup) findViewById(R.id.include_scanning_network);

            ViewId_include_scanning_network.setVisibility(View.VISIBLE);

            //***** include - ViewId_include_scrollview_lockout_network *****//
            ViewId_include_scrollview_lockout_network = (ViewGroup) findViewById(R.id.include_scrollview_lockout_network);

            ViewId_include_scrollview_lockout_network.setVisibility(View.VISIBLE);

            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.INVISIBLE);

            //***** textview26 - Scanning for Networks, Networks Found *****//
            ViewId_textview26 = (TextView) findViewById(R.id.textView26);

            ViewId_textview26.setText("Scanning for Networks");

        } catch (Exception e) {
            Log.d("myApp", "Rinnai37Network: setRinnai37Network(Exception - " + e + ")");
        }

        //***** TX WiFi - TCP *****//
        Tx_RN171DeviceScanNetwork();
        Log.d("myApp", "Rinnai37Network - Tx_RN171DeviceScanNetwork");

        startTxRN171DeviceScanResult();
    }

    //*******************************************************//
    //***** scrollviewrowrinnai37networkOnClickListener *****//
    //*******************************************************//

    private View.OnClickListener scrollviewrowrinnai37networkOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            //if(scrollview_listener_lockout == true){
            //    return;
            //}
            scrollviewrow_pressed = true;

            //Get Selected Text
            selected_scrollviewrowrinnai37network = ((TextView) v.findViewById(R.id.textView63));

            //debug
            Log.d("myApp", "Rinnai37Network_scrollviewrowrinnai37networkOnClickListener():" + selected_scrollviewrowrinnai37network.getText() + "");

            updateScrollViewTableLayoutRinnai37NetworkDetails();

            //Highlight Selection
            ViewId_linearlayout_network_row = ((LinearLayout) v.findViewById(R.id.linearlayout_network_row));

            ViewId_linearlayout_network_row.setBackgroundColor(Color.parseColor("#32FFFFFF"));
        }
    };

    //*************************************************************//
    //***** updateScrollViewTableLayoutRinnai37NetworkDetails *****//
    //*************************************************************//

    public void updateScrollViewTableLayoutRinnai37NetworkDetails() {

        TableLayout ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

        for (int a = 0; a <= AppGlobals.WiFiAccessPointInfo_List.size() - 1; a++) {

            View ViewId_scrollview_row_rinnai37_network = ViewId_wifiaccesspoint_tableLayout.getChildAt(a);

            //Highlight Selection
            ViewId_linearlayout_network_row = ((LinearLayout) ViewId_scrollview_row_rinnai37_network.findViewById(R.id.linearlayout_network_row));

            ViewId_linearlayout_network_row.setBackgroundColor(Color.parseColor("#00000000"));
        }
    }

    //***********************************//
    //***** Hides the soft keyboard *****//
    //***********************************//
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //***********************************//
    //***** Shows the soft keyboard *****//
    //***********************************//
    public void showSoftKeyboard(View view) {
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
                        if (pType.contains("14")) {

                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);
                            //ViewId_wifiaccesspoint_tableLayout.setOnTouchListener(new AutoTimerTableTouchListener());

                            //clear the table, start with blank table
                            ViewId_wifiaccesspoint_tableLayout.removeAllViews();

                            int id = 0;

                            for (int i = 0; i <= AppGlobals.WiFiAccessPointInfo_List.size() - 1; i++) {
                                Log.d("myApp_WiFiTCP", "Rinnai37Network_clientCallBackTCP: WiFi Access Point Name (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointName + ")");
                                Log.d("myApp_WiFiTCP", "Rinnai37Network_clientCallBackTCP: WiFi Access Point SignalStrength (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSignalStrength + ")");
                                Log.d("myApp_WiFiTCP", "Rinnai37Network_clientCallBackTCP: WiFi Access Point SecurityType (" + AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSecurityType + ")");

                                View ViewId_scrollview_row_rinnai37_network = getLayoutInflater().inflate(R.layout.scrollview_row_rinnai37_network, null, false);

                                ViewId_scrollview_row_rinnai37_network.setId(id);

                                ViewId_scrollview_row_rinnai37_network.setMinimumHeight(50);

                                //clean text
                                //nextSSID = ""+nextSSID.subSequence(nextSSID.indexOf("\"")+1,nextSSID.indexOf("\"",1));

                                //add listener
                                ViewId_scrollview_row_rinnai37_network.setOnClickListener(scrollviewrowrinnai37networkOnClickListener);//add OnClickListener Here

                                //set it on the row - textView63, wifiaccesspointName
                                ((TextView) ViewId_scrollview_row_rinnai37_network.findViewById(R.id.textView63)).setText(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointName);

                                //set it on the row - textView64, wifiaccesspointSignalStrength
                                ((TextView) ViewId_scrollview_row_rinnai37_network.findViewById(R.id.textView64)).setText(AppGlobals.WiFiAccessPointInfo_List.get(i).wifiaccesspointSignalStrength);

                                //set it on the row - textView65, wifiaccesspointSecurityType
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

                                ((TextView) ViewId_scrollview_row_rinnai37_network.findViewById(R.id.textView65)).setText(networksecuritytypeInfo);

                                //Add the Row to the table
                                ViewId_wifiaccesspoint_tableLayout.addView(ViewId_scrollview_row_rinnai37_network);

                                //Next
                                id++;

                            }

                            //***** include - ViewId_include_scanning_network *****//
                            ViewId_include_scanning_network = (ViewGroup) findViewById(R.id.include_scanning_network);

                            ViewId_include_scanning_network.setVisibility(View.INVISIBLE);

                            //***** include - ViewId_include_scrollview_lockout_network *****//
                            ViewId_include_scrollview_lockout_network = (ViewGroup) findViewById(R.id.include_scrollview_lockout_network);

                            ViewId_include_scrollview_lockout_network.setVisibility(View.INVISIBLE);

                            //***** TableLayout - ViewId_wifiaccesspoint_tableLayout *****//
                            ViewId_wifiaccesspoint_tableLayout = (TableLayout) findViewById(R.id.wifiaccesspoint_tableLayout);

                            ViewId_wifiaccesspoint_tableLayout.setVisibility(View.VISIBLE);

                            //***** textview26 - Scanning for Networks, Networks Found *****//
                            ViewId_textview26 = (TextView) findViewById(R.id.textView26);

                            ViewId_textview26.setText("1- Select Network, 2- Type Password, 3- Press OK.");

                            isDeviceScanResult = true;

                        }
                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai37Network: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai37Network: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai37Network: clientCallBackTCP");
    }

    //***** RN171_DEVICE_NETWORK_SETUP *****//
    public void Tx_RN171DeviceNetworkSetup() {

        String rn171SetOptReplaceNetwork = selected_scrollviewrowrinnai37network.getText() + "";

        if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).WiFiHardware == 0) {
            rn171SetOptReplaceNetwork = rn171SetOptReplaceNetwork.replace(' ', (char) 0x1B);
        }

        String rn171SetOptReplaceNetworkPassword = selected_scrollviewrowrinnai37networkpassword.getText() + "";

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
            Log.d("myApp_WiFiTCP", "Rinnai37Network: Tx_RN171DeviceNetworkSetup(Exception - " + e + ")");
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
            Log.d("myApp_WiFiTCP", "Rinnai37Network: Tx_RN171DeviceScanNetwork(Exception - " + e + ")");
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
            Log.d("myApp_WiFiTCP", "Rinnai37Network: Tx_RN171DeviceScanResult(Exception - " + e + ")");
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
        Log.d("myApp", "Rinnai37Network_onClick: startActivity(Rinnai21HomeScreen).");
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