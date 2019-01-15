package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import AWSmodule.AWSconnection;

/**
 * Created by JConci on 5/02/2018.
 */

public class Rinnai11eRegistration extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    Button ViewId_button29;

    ImageButton ViewId_imagebutton25;

    TextView ViewId_textview109;
    TextView ViewId_textview172;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai11e_registration);

        //Permit external connection attempts
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onCreate.");

        /////////////////////////////////////////////////
        //Select Terms and Conditions
        /////////////////////////////////////////////////
        AWSconnection.selectTacURL(new AWSconnection.textResult() {
            @Override

            //Get Async callback results
            public void getResult(String resulttext) {
                Log.d("myApp_AWS", "TAC: " + resulttext);

                final String ui_resulttext = resulttext;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            //***** Text (Terms and Conditions from AWS) - ViewId_textview172 *****//
                            ViewId_textview172 = (TextView) findViewById(R.id.textView172);
                            ViewId_textview172.setText(ui_resulttext);

                        } catch (Exception e) {
                            Log.d("myApp_AWS", "Select Terms and Conditions: (Exception - " + e + ")");

                            Toast.makeText(Rinnai11eRegistration.this, "Web Services Error. \nTry again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //***************************//
        //***** OnTouchListener *****//
        //***************************//

        //***** OnTouchListener - button29 (Agree) *****//
        ViewId_button29 = (Button) findViewById(R.id.button29);
        ViewId_textview109 = (TextView) findViewById(R.id.textView109);

        ViewId_button29.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_button29.setBackgroundResource(R.drawable.registration_button_red_background_pressed);
                        ViewId_textview109.setTextColor(Color.parseColor("#FF808080"));
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_button29.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview109.setTextColor(Color.parseColor("#FFFFFFFF"));

                        setAWSUserRegistration_Agree();

                        AppGlobals.rfwmInitialSetupFlag = true;
                        AppGlobals.rfwmUserFlag = 1;
                        AppGlobals.UDPSrv = new UDPServer(3500);
                        AppGlobals.fireplaceWifi.clear();
                        AppGlobals.WiFiAccessPointInfo_List.clear();
                        AppGlobals.TimersInfo_List.clear();

                        isClosing = true;
                        intent = new Intent(Rinnai11eRegistration.this, Rinnai17Login.class);
                        startActivity(intent);

                        finish();
                        Log.d("myApp", "Rinnai11eRegistration: startActivity(Rinnai17Login).");
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_button29.setBackgroundResource(R.drawable.registration_button_red_background);
                        ViewId_textview109.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                return false;
            }
        });

        //***** OnTouchListener - imageButton25 (Question Mark) *****//
        ViewId_imagebutton25 = (ImageButton) findViewById(R.id.imageButton25);

        ViewId_imagebutton25.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // PRESSED
                        ViewId_imagebutton25.setImageResource(R.drawable.registration_button_questionmark_pressed);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED
                        ViewId_imagebutton25.setImageResource(R.drawable.registration_button_questionmark);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_CANCEL:
                        // ABORTED
                        ViewId_imagebutton25.setImageResource(R.drawable.registration_button_questionmark);
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onRestart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onStop.");

        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai11eRegistration_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai11eRegistration_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    //****************************************//
    //***** setAWSUserRegistration_Agree *****//
    //****************************************//

    public void setAWSUserRegistration_Agree() {

        Tx_RN171DeviceSetDeviceName();

        /////////////////////////////////////////////////
        //Register/Insert Customer
        /////////////////////////////////////////////////

        //Call method and post appropriate values
        AWSconnection.insertCustomerURL(AppGlobals.userregInfo.userregistrationEmail,
                AppGlobals.userregInfo.userregistrationPassword,
                AppGlobals.userregInfo.userregistrationStreetAddress,
                AppGlobals.userregInfo.userregistrationSuburb,
                AppGlobals.userregInfo.userregistrationCityRegion,
                AppGlobals.userregInfo.userregistrationFirstName,
                AppGlobals.userregInfo.userregistrationLastName,
                Integer.parseInt(AppGlobals.userregInfo.userregistrationPostcode),
                AppGlobals.userregInfo.userregistrationCountry,

                //Call interface to retrieve Async results
                new AWSconnection.textResult() {
                    @Override

                    public void getResult(String result) {

                        //Do stuff with results here
                        //Returns either success or fail message
                        //Log.i("Registration:", result);
                        Log.d("myApp_AWS", "Registration:" + result);

                        if (!result.equals("\"Successful Registration\"")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11eRegistration.this, "Web Services Error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11eRegistration.this, "Update Successful.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

        /////////////////////////////////////////
        //Register Appliance
        /////////////////////////////////////////

        //Call method and post appropriate values
        AWSconnection.insertCustomerApplianceURL(AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceSerial,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceType,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceModel,
                AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).UUID,
                AppGlobals.userregInfo.userregistrationEmail,
                AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceName,

                //Call interface to retrieve Async results
                new AWSconnection.textResult() {
                    @Override

                    public void getResult(String result) {

                        //Do stuff with results here
                        //Returns either success or fail message
                        //Log.i("Registration:", result);
                        Log.d("myApp_AWS", "Registration:" + result);

                        if (!result.equals("\"Appliance Registration Successful\"")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11eRegistration.this, "Web Services Error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Rinnai11eRegistration.this, "Update Successful.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
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

                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: clientCallBackTCP");
    }

    //***** RN171_DEVICE_SET_DEVICENAME *****//
    public void Tx_RN171DeviceSetDeviceName() {

        String rn171SetOptReplaceDeviceName = AppGlobals.userregInfo.userregApplianceInfo_now.userregistrationApplianceName;

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
            Log.d("myApp_WiFiTCP", "Rinnai11eRegistration: Tx_RN171DeviceSetDeviceName(Exception - " + e + ")");
        }
    }

}
