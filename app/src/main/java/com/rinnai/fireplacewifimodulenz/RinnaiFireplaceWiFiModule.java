package com.rinnai.fireplacewifimodulenz;

import android.util.Log;

/**
 * Created by jconci on 18/10/2017.
 */

public class RinnaiFireplaceWiFiModule {

    //**************************************************//
    //***** Rinnai Fireplace WiFi Module variables *****//
    //**************************************************//

    public String ipAddress;
    public String DeviceName;
    public String UUID;
    public int WiFiHardware;

    int rfwmMainPowerSwitch = 0;
    int rfwmOperationState = 0;
    int rfwmErrorCodeHI = 0;
    int rfwmErrorCodeLO = 0;
    int rfwmOperationMode = 0;
    int rfwmBurningState = 0;
    int rfwmFlameLevel = 0;
    int rfwmEconomyFunction = 0;
    int rfwmLighting = 0;
    int rfwmRoomTemperature = 0;
    int rfwmSetTemperature = 0;
    int rfwmBurnSpeedInfo = 0;
    int rfwmLightingInfo = 0;
    int rfwmTimerActive = 0;

    int tmrstotal = 0;

    float DeviceVersion = 0;
    String settimeResult = "";

    int rfwmappSettingsChangeGuardTime = 0;

    String otastartResult = "";
    String otaendResult = "";
    String otafiletransferResult = "";

    //*********************//
    //***** Functions *****//
    //*********************//

    public void setIpAddress(String ip) {
        this.ipAddress = ip;
    }

    public void setDeviceName(String devname) {

        int data_count = 0;
        int data_length = 0;

        String devicename_RW = "";
        String uuid_RW = "";

        String devicename_RinnaiWiFi = "";
        String uuid_RinnaiWiFi = "";

        Log.d("myApp_WiFiUDP", "processRXUDPData (pre):" + devname);

        if (devname.contains("RW_") == true) {

            this.WiFiHardware = 0;

            uuid_RW = devname.substring(devname.indexOf("RW_"));
            devicename_RW = devname.substring(devname.indexOf("RW_"));

            Log.d("myApp_WiFiUDP", "processRXUDPData (RW - post1a):" + uuid_RW);
            Log.d("myApp_WiFiUDP", "processRXUDPData (RW - post1b):" + devicename_RW);

            try {
                this.UUID = uuid_RW.substring(3, 9);

                Log.d("myApp_WiFiUDP", "processRXUDPData (RW - post2a):" + this.UUID);

            } catch (Exception e) {

                Log.d("myApp_WiFiUDP", "processRXUDPData: (RW - Exceptiona):" + e.getMessage());
            }

            data_count = devicename_RW.length();

            try {
                for (int i = 9; i < data_count; i++) {

                    devicename_RW.charAt(i);

                    if ((devicename_RW.charAt(i) >= 0x20) && (devicename_RW.charAt(i) <= 0x7F)) {
                        //Good ASCII Characterst
                    } else {
                        data_length = i;
                        break;
                    }
                }

                this.DeviceName = devicename_RW.substring(9, data_length);

                Log.d("myApp_WiFiUDP", "processRXUDPData (RW - post2b):" + this.DeviceName);

            } catch (Exception e) {

                Log.d("myApp_WiFiUDP", "processRXUDPData: (RW - Exceptionb):" + e.getMessage());
            }
        }

        if (devname.contains("RinnaiWiFi_") == true) {

            this.WiFiHardware = 1;

            uuid_RinnaiWiFi = devname.substring(devname.indexOf("RinnaiWiFi_"));
            devicename_RinnaiWiFi = devname.substring(devname.indexOf("RinnaiWiFi_"));

            Log.d("myApp_WiFiUDP", "processRXUDPData (RinnaiWiFi - post1a):" + uuid_RinnaiWiFi);
            Log.d("myApp_WiFiUDP", "processRXUDPData (RinnaiWiFi - post1b):" + devicename_RinnaiWiFi);

            try {
                this.UUID = uuid_RinnaiWiFi.substring(11, 17);

                Log.d("myApp_WiFiUDP", "processRXUDPData (RinnaiWiFi - post2a):" + this.UUID);

            } catch (Exception e) {

                Log.d("myApp_WiFiUDP", "processRXUDPData: (RinnaiWiFi - Exceptiona):" + e.getMessage());
            }

            data_count = devicename_RinnaiWiFi.length();

            try {
                for (int i = 17; i < data_count; i++) {

                    devicename_RinnaiWiFi.charAt(i);

                    if ((devicename_RinnaiWiFi.charAt(i) >= 0x20) && (devicename_RinnaiWiFi.charAt(i) <= 0x7F)) {
                        //Good ASCII Characterst
                    } else {
                        data_length = i;
                        break;
                    }
                }

                this.DeviceName = devicename_RinnaiWiFi.substring(17, data_length);

                Log.d("myApp_WiFiUDP", "processRXUDPData (RinnaiWiFi - post2b):" + this.DeviceName);

            } catch (Exception e) {

                Log.d("myApp_WiFiTCP", "processRXUDPData: (RinnaiWiFi - Exceptionb):" + e.getMessage());
            }
        }
    }

    public static boolean processRXUDPData(String data) {

        return true;
    }

    public String processRXTCPData(String data) {
        String header = "";

        try {

            Log.d("myApp_WiFiTCP", "processRXTCPData (pre):" + data);

            data = data.substring(data.indexOf("RINNAI_"));

            Log.d("myApp_WiFiTCP", "processRXTCPData (post1):" + data);

            if ((data.substring(0, 7)).contains("RINNAI_")) {
                Log.d("myApp_WiFiTCP", "processRXTCPData (post2):" + data);

                header = data.substring(7, 9);

                if (header.compareTo("10") == 0) {
                    //RINNAI_10,2.03,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 3) {

                        try {
                            this.DeviceVersion = Float.parseFloat(dataSplit[1]);
                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }

                } else if (header.compareTo("12") == 0) {
                    //RINNAI_12,OK,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 3) {

                        try {
                            this.settimeResult = dataSplit[1];
                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }

                } else if (header.compareTo("14") == 0) {
                    //RINNAI_14,Xxxxxx_XX_Xxx1,0E,04,Xxxxxx_2,08,04,xxxxxxxxxx_xx,0D,04,Xxxxxxxxxx Xxxxx,10,04,E

                    String[] dataSplit = data.split(",");

                    AppGlobals.WiFiAccessPointInfo_List.clear();

                    try {
                        for (int i = 1; i < dataSplit.length - 1; i += 3) {

                            WiFiAccessPoint_Info wifiapinfo = new WiFiAccessPoint_Info(dataSplit[i], dataSplit[i + 1], dataSplit[i + 2]);

                            AppGlobals.WiFiAccessPointInfo_List.add(wifiapinfo);
                        }
                    } catch (Exception e) {

                        Log.d("myApp_WiFiTCP", "processRXTCPData: -Exception:" + e.getMessage());
                    }

                } else if (header.compareTo("21") == 0) {
                /*
                #Total
                #HoursOn
                #MinutesOn
                #HoursOff
                #MinutesOff
                #DaysOfWeek
                #OperationMode
                #SetTemperature
                #OnOff
                */

                    String[] dataSplit = data.split(",");

                    try {
                        this.tmrstotal = Integer.parseInt(dataSplit[1], 16);

                    } catch (Exception e) {
                        header = "";//Error with data
                    }

                    AppGlobals.TimersInfo_List.clear();

                    try {
                        for (int i = 2; i < dataSplit.length - 1; i += 8) {

                            int tmrshourson = Integer.parseInt(dataSplit[i], 16);
                            int tmrsminuteson = Integer.parseInt(dataSplit[i + 1], 16);
                            int tmrshoursoff = Integer.parseInt(dataSplit[i + 2], 16);
                            int tmrsminutesoff = Integer.parseInt(dataSplit[i + 3], 16);
                            int tmrsdaysofweek = Integer.parseInt(dataSplit[i + 4], 16);
                            int tmrsoperationmode = Integer.parseInt(dataSplit[i + 5], 16);
                            int tmrssettemperature = Integer.parseInt(dataSplit[i + 6], 16);
                            int tmrsonoff = Integer.parseInt(dataSplit[i + 7], 16);

                            Timers_Info tmrsinfo = new Timers_Info(tmrshourson, tmrsminuteson, tmrshoursoff, tmrsminutesoff, tmrsdaysofweek, tmrsoperationmode, tmrssettemperature, tmrsonoff);

                            AppGlobals.TimersInfo_List.add(tmrsinfo);
                        }
                    } catch (Exception e) {

                        Log.d("myApp_WiFiTCP", "processRXTCPData: -Exception:" + e.getMessage());
                    }

                } else if (header.compareTo("22") == 0) {
                    //RINNAI_22,00,01,20,20,02,00,00,00,00,15,17,00,00,00,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 16) {
                    /*
                    #Main Power Switch
					#Operation State
					#Error Code HI
					#Error Code LO
					#Operation Mode
					#Burning State
					#Flame Level
					#Economy Function
					#Lighting
					#Room Temperature
					#Set Temperature
					#Burn Speed Info
					#Lighting Info
					#Timer Active
					*/

                        try {
                            this.rfwmMainPowerSwitch = Integer.parseInt(dataSplit[1], 16);
                            this.rfwmOperationState = Integer.parseInt(dataSplit[2], 16);
                            this.rfwmErrorCodeHI = Integer.parseInt(dataSplit[3], 16);
                            this.rfwmErrorCodeLO = Integer.parseInt(dataSplit[4], 16);
                            this.rfwmOperationMode = Integer.parseInt(dataSplit[5], 16);
                            this.rfwmBurningState = Integer.parseInt(dataSplit[6], 16);
                            this.rfwmFlameLevel = Integer.parseInt(dataSplit[7], 16);
                            this.rfwmEconomyFunction = Integer.parseInt(dataSplit[8], 16);
                            this.rfwmLighting = Integer.parseInt(dataSplit[9], 16);
                            this.rfwmRoomTemperature = Integer.parseInt(dataSplit[10], 16);
                            this.rfwmSetTemperature = Integer.parseInt(dataSplit[11], 16);
                            this.rfwmBurnSpeedInfo = Integer.parseInt(dataSplit[12], 16);
                            this.rfwmLightingInfo = Integer.parseInt(dataSplit[13], 16);
                            this.rfwmTimerActive = Integer.parseInt(dataSplit[14], 16);

                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }
                } else if (header.compareTo("9A") == 0) {
                    //RINNAI_9A,OK,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 3) {

                        try {
                            this.otaendResult = dataSplit[1];
                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }
                } else if (header.compareTo("9B") == 0) {
                    //RINNAI_9B,OK,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 3) {

                        try {
                            this.otastartResult = dataSplit[1];
                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }
                } else if (header.compareTo("99") == 0) {
                    //RINNAI_99,OK,E

                    String[] dataSplit = data.split(",");

                    if (dataSplit.length >= 3) {

                        try {
                            this.otafiletransferResult = dataSplit[1];
                        } catch (Exception e) {
                            header = "";//Error with data
                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.d("myApp_WiFiTCP", "RinnaiFireplaceWiFiModule: processRXTCPData(Exception - " + e + ")");
            Log.d("myApp_WiFiTCP", "RinnaiFireplaceWiFiModule: processRXTCPData(RX - " + data + ")");
        }

        return header;
    }

}
