package com.rinnai.fireplacewifimodulenz;

/**
 * Created by JConci on 1/11/2017.
 */

public class ServiceFaultCodes_Info {
    public String faultCause = "NA";
    public String faultDescription = "NA";
    public String faultAction = "NA";

    public static ServiceFaultCodes_Info GetServiceFaultCodes_Info(int servicefaultcode){

        ServiceFaultCodes_Info info = new ServiceFaultCodes_Info();

        switch (servicefaultcode)
        {
            //Error code HIGH = no error code:[Hx(0x30), Dec(48)]
            //Error code LOW = no error code:[Hx(0x30), Dec(48)]
            case 0:
                info.faultCause            = "Mains power failure.";
                info.faultDescription      = "When power failure is sensed operation stops.";
                info.faultAction           = "Reset heater, press the On/Off button at the fireplace twice.";
                break;

            //Error code HIGH = no error code:[Hx(0x31), Dec(49)]
            //Error code LOW = no error code:[Hx(0x31), Dec(49)]
            case 11:
                info.faultCause           = "Ignition Failure Flame rod.";
                info.faultDescription     = "Current cannot reach 0.1 µA for 3 seconds during initial combustion.";
                info.faultAction          = "Check Gas supply is turned on, switch the fireplace On/Off at the fireplace twice to try and re–ignite.";
                break;

            //Error code HIGH = no error code:[Hx(0x31), Dec(49)]
            //Error code LOW = no error code:[Hx(0x32), Dec(50)]
            case 12:
                info.faultCause           = "Incomplete combustion.";
                info.faultDescription     = "Flame rod current remains below 0.1 µA for 3 seconds during initial combustion.";
                info.faultAction          = "Check Gas supply is turned on, switch the fireplace On/Off at the fireplace twice to try and re–ignite.";
                break;

            //Error code HIGH = no error code:[Hx(0x31), Dec(49)]
            //Error code LOW = no error code:[Hx(0x34), Dec(52)]
            case 14:
                info.faultCause           = "Over heat safety device.";
                info.faultDescription     = "High Limit temperature thermistor or thermal fuse has activated.";
                info.faultAction          = "Clean the air filters on the fireplace, if the error code continues a service call is required.";
                break;

            //Error code HIGH = no error code:[Hx(0x31), Dec(49)]
            //Error code LOW = no error code:[Hx(0x36), Dec(54)]
            case 16:
                info.faultCause           = "Room overheat.";
                info.faultDescription     = "Room temperature is sensed as being above 40°C for more than 10 minutes.";
                info.faultAction          = "Lower room temperature.";
                break;

            //Error code HIGH = no error code:[Hx(0x33), Dec(51)]
            //Error code LOW = no error code:[Hx(0x31), Dec(49)]
            case 31:
                info.faultCause           = "Room temp sensor faulty.";
                info.faultDescription     = "Open circuit in the temperature thermistor.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x33), Dec(51)]
            //Error code LOW = no error code:[Hx(0x32), Dec(50)]
            case 32:
                info.faultCause           = "Room temp sensor faulty.";
                info.faultDescription     = "Open circuit in temperature sensor 1.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x33), Dec(51)]
            //Error code LOW = no error code:[Hx(0x33), Dec(51)]
            case 33:
                info.faultCause           = "Room temp sensor faulty.";
                info.faultDescription     = "Open circuit in temperature sensor 2.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x35), Dec(53)]
            //Error code LOW = no error code:[Hx(0x33), Dec(51)]
            case 53:
                info.faultCause           = "Spark sensor faulty.";
                info.faultDescription     = "Open circuit in spark sensor.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x36), Dec(54)]
            //Error code LOW = no error code:[Hx(0x31), Dec(49)]
            case 61:
                info.faultCause           = "Combustion fan motor faulty.";
                info.faultDescription     = "Open circuit in combustion fan wiring.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x37), Dec(55)]
            //Error code LOW = no error code:[Hx(0x31), Dec(49)]
            case 71:
                info.faultCause           = "Solenoids faulty.";
                info.faultDescription     = "Open circuit or stuck solenoid valve.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x37), Dec(55)]
            //Error code LOW = no error code:[Hx(0x32), Dec(50)]
            case 72:
                info.faultCause           = "Flame detection circuit.";
                info.faultDescription     = "Open circuit to flame detection sensor.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x37), Dec(55)]
            //Error code LOW = no error code:[Hx(0x33), Dec(51)]
            case 73:
                info.faultCause           = "Communication Error.";
                info.faultDescription     = "Main pcb faulty.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x39), Dec(57)]
            //Error code LOW = no error code:[Hx(0x30), Dec(48)]
            case 90:
                info.faultCause           = "Communication Error.";
                info.faultDescription     = "Communication error detected between Main pcb and WiFi module.";
                info.faultAction          = "Service Call.";
                break;

            //Error code HIGH = no error code:[Hx(0x39), Dec(57)]
            //Error code LOW = no error code:[Hx(0x31), Dec(49)]
            case 91:
                info.faultCause           = "Communication Error.";
                info.faultDescription     = "Communication error detected between Smart Device and WiFi module.";
                info.faultAction          = "Please check that your Smart Device and Rinnai WiFi module are within range. Check network settings.";
                break;

            case 92:
                info.faultCause           = "Cloud Function.";
                info.faultDescription     = "Could function currently not available.";
                info.faultAction          = "This is due to another device having control on the Home network or the WiFi module has gone offline.";
                break;

            default:
                info.faultCause           = "Unknown Fault.";
                info.faultDescription     = "An unknown Fault has occured.";
                info.faultAction          = "Service Call.";
                break;
        }
        return info;
    }
}
