package com.rinnai.fireplacewifimodulenz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Rinnai12OTA extends MillecActivityBase
        implements ActivityClientInterfaceTCP {

    ProgressBar ViewId_progressbar4;

    BufferedReader reader;
    int lineCount;
    String line;

    String[] intelHEX;
    String[] intelHEX_4Data;
    int intelHEX_4DataRows = 0;

    Timer startupCheckTimer;
    int startupCheckTimerCount;

    int ota_filetransfer_row;
    int ota_filetransfer_row_sent;
    boolean isOTAFileTransfer = false;

    Intent intent;

    boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinnai12_ota);

        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onCreate.");

        OTAprocess();

        //***** TX WiFi - TCP *****//
        Tx_RN171DeviceGetVersion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onStart.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onRestart.");

        isClosing = false;

        //***** TX WiFi - TCP *****//
        Tx_RN171DeviceGetVersion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onStop.");

        startupCheckTimer.cancel();
        isClosing = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myApp_ActivityLifecycle", "Rinnai12OTA_onDestroy.");
    }

    @Override
    public void onBackPressed() {
        Log.d("myApp", "Rinnai12OTA_onBackPressed.");

        //super.onBackPressed();
        moveTaskToBack(true);
    }

    public void OTAprocess() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    reader = new BufferedReader(
                            new InputStreamReader(getAssets().open("RinnaiWiFiMK2_V07.hex")));

                    //File row to String
                    List<String> list = new ArrayList<String>();
                    while ((line = reader.readLine()) != null) {
                        Log.d("myApp_FileReader", "Line(" + lineCount + "): " + line);
                        list.add(line);
                        lineCount++;
                    }

                    //File row (String) to Array
                    intelHEX = list.toArray(new String[0]);
                    Log.d("myApp_FileReader", "intelHEX: " + intelHEX[0]);

                    //Combine 4 File row (Array) to another Array
                    intelHEX_4Data = new String[list.size()];

                    int lineCount = 0;
                    intelHEX_4DataRows = 0;

                    intelHEX_4Data[0] = "";

                    for (int i = 0; i < intelHEX.length; i++) {

                        if (lineCount == 4) {
                            lineCount = 0;
                            intelHEX_4DataRows++;
                            intelHEX_4Data[intelHEX_4DataRows] = "";
                        }

                        //Line from file, remove ";"
                        String line = intelHEX[i];

                        //Build line of 4 strings
                        intelHEX_4Data[intelHEX_4DataRows] += line;

                        lineCount++;
                    }

                    ViewId_progressbar4 = (ProgressBar) findViewById(R.id.progressBar4);

                    ViewId_progressbar4.setMax(intelHEX_4DataRows);

                    for (int i = 0; i <= intelHEX_4DataRows; i++) {
                        Log.d("myApp_FileReader", "intelHEX_4Data" + i + ": " + intelHEX_4Data[i]);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    Log.d("myApp_FileReader", "IOException - " + ioe + "");
                }

            }
        });
    }

    //****************************************//
    //***** startTxRN171DeviceOTAProcess *****//
    //****************************************//

    public void startTxRN171DeviceOTAProcess() {

        this.startupCheckTimerCount = 0;

        this.startupCheckTimer = new Timer();

        this.startupCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.d("myApp", "Rinnai12OTA: Tick.. " + startupCheckTimerCount);

                if (ota_filetransfer_row <= intelHEX_4DataRows) {
                    if (startupCheckTimerCount >= 150) {

                        if (isOTAFileTransfer == true) {

                            startupCheckTimerCount = 150;

                            isOTAFileTransfer = false;

                            Tx_RN171DeviceOTAFileTransfer();
                            Log.d("myApp", "Rinnai12OTA - Tx_RN171DeviceOTAFileTransfer");

                            ota_filetransfer_row_sent = ota_filetransfer_row;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ViewId_progressbar4 = (ProgressBar) findViewById(R.id.progressBar4);

                                    ViewId_progressbar4.setProgress(ota_filetransfer_row);
                                }
                            });
                        } else {
                            if (startupCheckTimerCount >= 165) {
                                isOTAFileTransfer = true;
                            }
                        }
                    }
                } else {
                    startupCheckTimer.cancel();

                    Tx_RN171DeviceOTAEnd();
                }

                startupCheckTimerCount++;

            }

        }, 0, 100);

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
                        if (pType.contains("10")) {
                            Log.d("myApp_WiFiTCP", "Rinnai12OTA_clientCallBackTCP: Device Version (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion + ")");

                            if ((2.07f > AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                    (1.99f < AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion) &&
                                    (2.02f != AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion)) {

                                ota_filetransfer_row = 0;

                                ViewId_progressbar4 = (ProgressBar) findViewById(R.id.progressBar4);

                                ViewId_progressbar4.setProgress(ota_filetransfer_row);

                                isOTAFileTransfer = true;

                                if ((2.00f == AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).DeviceVersion)) {
                                    //***** TX WiFi - TCP *****//
                                    startTxRN171DeviceOTAProcess();

                                } else {
                                    //***** TX WiFi - TCP *****//
                                    Tx_RN171DeviceOTAStart();
                                    Log.d("myApp", "Rinnai12OTA - Tx_RN171DeviceOTAStart");
                                }

                            } else {
                                AppGlobals.rfwmInitialSetupFlag = true;
                                AppGlobals.rfwmUserFlag = 1;
                                AppGlobals.UDPSrv = new UDPServer(3500);
                                AppGlobals.fireplaceWifi.clear();
                                AppGlobals.WiFiAccessPointInfo_List.clear();
                                AppGlobals.TimersInfo_List.clear();

                                isClosing = true;
                                intent = new Intent(Rinnai12OTA.this, Rinnai17Login.class);
                                startActivity(intent);

                                finish();
                                Log.d("myApp", "Rinnai11eRegistration: startActivity(Rinnai17Login).");
                            }

                        }

                        if (pType.contains("9A")) {
                            Log.d("myApp_WiFiTCP", "Rinnai12OTA_clientCallBackTCP: OTA End Result (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otaendResult + ")");

                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otaendResult.equals("OK")) {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: END OK!!!");

                                AppGlobals.rfwmInitialSetupFlag = true;
                                AppGlobals.rfwmUserFlag = 1;
                                AppGlobals.UDPSrv = new UDPServer(3500);
                                AppGlobals.fireplaceWifi.clear();
                                AppGlobals.WiFiAccessPointInfo_List.clear();
                                AppGlobals.TimersInfo_List.clear();

                                isClosing = true;
                                intent = new Intent(Rinnai12OTA.this, Rinnai17Login.class);
                                startActivity(intent);

                                finish();
                                Log.d("myApp", "Rinnai11eRegistration: startActivity(Rinnai17Login).");

                            } else {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: END FAIL!!!");
                            }
                        }
                        if (pType.contains("9B")) {
                            Log.d("myApp_WiFiTCP", "Rinnai12OTA_clientCallBackTCP: OTA Start Result (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otastartResult + ")");

                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otastartResult.equals("OK")) {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: START OK!!!");

                                isOTAFileTransfer = true;

                                startTxRN171DeviceOTAProcess();

                            } else {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: START FAIL!!!");
                            }
                        }
                        if (pType.contains("99")) {
                            Log.d("myApp_WiFiTCP", "Rinnai12OTA_clientCallBackTCP: OTA File Transfer Result (" + AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otafiletransferResult + ")");

                            if (AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).otafiletransferResult.equals("OK") && isOTAFileTransfer == false) {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: FILE TRANSFER OK!!!");

                                ota_filetransfer_row = ota_filetransfer_row_sent + 1;

                                isOTAFileTransfer = true;

                            } else {
                                Log.d("myApp_WiFiTCP", "Rinnai12OTA: FILE TRANSFER FAIL!!!");
                            }
                        }

                    } catch (Exception e) {
                        Log.d("myApp_WiFiTCP", "Rinnai12OTA: clientCallBackTCP(Exception - " + e + ")");
                        Log.d("myApp_WiFiTCP", "Rinnai12OTA: clientCallBackTCP(RX - " + pText + ")");
                    }
                }
            });
        }

        Log.d("myApp_WiFiTCP", "Rinnai12OTA: clientCallBackTCP");
    }

    //***** RN171_DEVICE_GET_VERSION *****//
    public void Tx_RN171DeviceGetVersion() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_10,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai17Login: Tx_RN171DeviceGetVersion(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_OTA_START *****//
    public void Tx_RN171DeviceOTAStart() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_9B,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai12OTA: Tx_RN171DeviceOTAStart(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_OTA_END *****//
    public void Tx_RN171DeviceOTAEnd() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_9A,E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai12OTA: Tx_RN171DeviceOTAEnd(Exception - " + e + ")");
        }
    }

    //***** RN171_DEVICE_OTA_FILE_TRANSFER *****//
    public void Tx_RN171DeviceOTAFileTransfer() {

        try {
            TCPClient tcpClient = new TCPClient(
                    3000,
                    AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).ipAddress,
                    this,
                    "RINNAI_99," + intelHEX_4Data[ota_filetransfer_row] + "," + "E\n", true);
            tcpClient.start();
        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "Rinnai33aTimers: Tx_RN171DeviceDeleteTimers(Exception - " + e + ")");
        }
    }

}
