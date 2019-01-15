package com.rinnai.fireplacewifimodulenz;

/**
 * Created by JConci on 6/11/2017.
 */

public class WiFiAccessPoint_Info {
    public String wifiaccesspointName = "NA";
    public String wifiaccesspointSignalStrength = "NA";
    public String wifiaccesspointSecurityType = "NA";

    public WiFiAccessPoint_Info(String wifiapName, String wifiapSignalStrength, String wifiapSecurityType){

        this.wifiaccesspointName = wifiapName;
        this.wifiaccesspointSignalStrength = wifiapSignalStrength;
        this.wifiaccesspointSecurityType = wifiapSecurityType;

    }
}
