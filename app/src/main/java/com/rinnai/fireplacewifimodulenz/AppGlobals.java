package com.rinnai.fireplacewifimodulenz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jconci on 16/08/2017.
 */

public class AppGlobals {

    public static CommunicationErrorFault CommErrorFault = new CommunicationErrorFault();

    public static UDPServer UDPSrv = new UDPServer(3500);

    public static List<RinnaiFireplaceWiFiModule> fireplaceWifi = new ArrayList<RinnaiFireplaceWiFiModule>();
    public static ArrayList<WiFiAccessPoint_Info> WiFiAccessPointInfo_List = new ArrayList<WiFiAccessPoint_Info>();
    public static ArrayList<Timers_Info> TimersInfo_List = new ArrayList<Timers_Info>();

    public static UserRegistration_Info userregInfo = new UserRegistration_Info();

    public static boolean ViewId_imageview_navview7_actionup = false;
    public static boolean ShowHints_economy_actionvisible = false;
    public static boolean ShowHints_flame_actionvisible = false;
    public static boolean ShowHints_settemp_actionvisible = true;
    public static boolean ViewId_imagebutton2_actionup = false;
    public static boolean Button_flame_settemp_actionvisible = false;

    public static boolean ViewId_imagebutton14_actionup = false;
    public static boolean ViewId_imagebutton15_actionup = false;
    public static boolean ViewId_imagebutton16_actionup = false;
    public static boolean ViewId_imagebutton17_actionup = false;
    public static boolean ViewId_imagebutton18_actionup = false;
    public static boolean ViewId_imagebutton19_actionup = false;
    public static boolean ViewId_imagebutton20_actionup = false;
    public static boolean ViewId_imagebutton23_actionup = false;
    public static boolean ViewId_imagebutton23standby_actionup = false;

    public static boolean ViewId_imagebutton3_imagebutton22_actionup = false;
    public static boolean Standby_actionvisible = false;

    public static int ViewId_textview6_flamevalue = 1;
    public static int ViewId_textview7_settempvalue = 16;
    public static int ViewId_switch1_switch2value = 0;

    public static int selected_scrollviewrowrinnai33atimersmeridianon = 0;
    public static int selected_scrollviewrowrinnai33atimershourson = 0;
    public static int selected_scrollviewrowrinnai33atimersminuteson = 0;
    public static int selected_scrollviewrowrinnai33atimersmeridianoff = 0;
    public static int selected_scrollviewrowrinnai33atimershoursoff = 0;
    public static int selected_scrollviewrowrinnai33atimersminutesoff = 0;
    public static int selected_scrollviewrowrinnai33atimersdaysofweek = 0;
    public static int selected_scrollviewrowrinnai33atimerssettemperature = 0;
    public static int selected_fireplaceWifi = 0;
    public static boolean isStandbyOn = false;
    public static String PushedSSID;

    public static boolean rfwmInitialSetupFlag = false;

    public static boolean userregClaimAppliance = false;

    static int    rfwmUserFlag;
    static String rfwmEmail;
    static String rfwmPassword;

    //**********************************************//
    //***** CheckIfDeviceIsInListWithIPAddress *****//
    //**********************************************//

    public static boolean CheckIfDeviceIsInListWithIPAddress(String IPAddress) {

        for (int i = 0; i < fireplaceWifi.size(); i++) {
            if (fireplaceWifi.get(i) != null) {
                if (fireplaceWifi.get(i).ipAddress.compareTo(IPAddress) == 0) {
                    //We already know this device
                    return true;
                }
            }
        }

        return false;
    }

    //***********************************************//
    //***** GetIndexOfDeviceInListWithIPAddress *****//
    //***********************************************//

    public static int GetIndexOfDeviceInListWithIPAddress(String IPAddress) {

        for (int i = 0; i < fireplaceWifi.size(); i++) {
            if (fireplaceWifi.get(i) != null) {
                if (fireplaceWifi.get(i).ipAddress == null) {
                    return i; //use the current Slot as it has not been set yet
                } else if (fireplaceWifi.get(i).ipAddress.compareTo(IPAddress) == 0) {
                    //We already know this device
                    return i;
                }
            }
        }

        //New Fireplace
        RinnaiFireplaceWiFiModule nFireplace = new RinnaiFireplaceWiFiModule();
        fireplaceWifi.add(nFireplace);

        return (fireplaceWifi.size() - 1); //current Fireplace
    }

    //****************************************************//
    //***** saveRinnaiFireplaceWiFiModuleCredentials *****//
    //****************************************************//

    public static boolean saveRinnaiFireplaceWiFiModuleCredentials(Context context, String userName, String password) {

        boolean statusUserName = false;
        boolean statusPassword = false;
        boolean statusFlag = false;

        statusUserName = PersistentStorage.StoreValueString(
                context,
                PersistentStorageDefines.SHARED_PREF_KEY,
                PersistentStorageDefines.SHARED_PREF_ID_RFWM_EMAIL,
                userName);


        statusPassword = PersistentStorage.StoreValueString(
                context,
                PersistentStorageDefines.SHARED_PREF_KEY,
                PersistentStorageDefines.SHARED_PREF_ID_RFWM_PASSWORD,
                password);

        statusFlag = PersistentStorage.StoreValueInt(
                context,
                PersistentStorageDefines.SHARED_PREF_KEY,
                PersistentStorageDefines.SHARED_PREF_ID_RFWM_USER_FLAG,
                1);

        //ensure all saves are AOK
        if(     statusUserName == false
                ||  statusPassword == false
                ||  statusFlag == false
                ){

            return false;

        }else{
            return true;
        }

    }

    //*********************************//
    //***** loadPersistentStorage *****//
    //*********************************//

    public static void loadPersistentStorage(Context context){

        //check if there is a user name and password saved
        AppGlobals.rfwmUserFlag = PersistentStorage.RetrieveStoredValueInt(
                context,
                PersistentStorageDefines.SHARED_PREF_KEY,
                PersistentStorageDefines.SHARED_PREF_ID_RFWM_USER_FLAG,
                PersistentStorageDefines.SHARED_PREF_DEFAULT_ID_RFWM_USER_FLAG
        );

        Log.e("MyApp", "loadPersistentStorage:");

        if(AppGlobals.rfwmUserFlag == 1){

            //We have an Rinnai Fireplace WiFi Module User saved



            //Load the details

            AppGlobals.rfwmEmail = PersistentStorage.RetrieveStoredValueString(
                    context,
                    PersistentStorageDefines.SHARED_PREF_KEY,
                    PersistentStorageDefines.SHARED_PREF_ID_RFWM_EMAIL,
                    PersistentStorageDefines.SHARED_PREF_DEFAULT_ID_RFWM_EMAIL
            );

            AppGlobals.rfwmPassword = PersistentStorage.RetrieveStoredValueString(
                    context,
                    PersistentStorageDefines.SHARED_PREF_KEY,
                    PersistentStorageDefines.SHARED_PREF_ID_RFWM_PASSWORD,
                    PersistentStorageDefines.SHARED_PREF_DEFAULT_ID_RFWM_PASSWORD
            );

            Log.e("MyApp", "loadPersistentStorage: Rinnai Fireplace WiFi Module User Found {"+AppGlobals.rfwmEmail+"]");

        }else{

            Log.e("MyApp", "loadPersistentStorage: No User Data");

        }

    }
}
