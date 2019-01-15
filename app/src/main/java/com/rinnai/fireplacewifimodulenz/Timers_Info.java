package com.rinnai.fireplacewifimodulenz;

/**
 * Created by JConci on 16/11/2017.
 */

public class Timers_Info {
    //public String timersTotal = "NA";
    public int timersHoursOn = 0;
    public int timersMinutesOn = 0;
    public int timersHoursOff = 0;
    public int timersMinutesOff = 0;
    public int timersDaysOfWeek = 0;
    public int timersOperationMode = 0;
    public int timersSetTemperature = 0;
    public int timersOnOff = 0;

    public Timers_Info(/*String tmrsTotal,*/ int tmrsHoursOn, int tmrsMinutesOn,
                       int tmrsHoursOff, int tmrsMinutesOff, int tmrsDaysOfWeek,
                       int tmrsOperationMode, int tmrsSetTemperature, int tmrsOnOff){

        //this.timersTotal = tmrsTotal;
        this.timersHoursOn = tmrsHoursOn;
        this.timersMinutesOn = tmrsMinutesOn;
        this.timersHoursOff = tmrsHoursOff;
        this.timersMinutesOff = tmrsMinutesOff;
        this.timersDaysOfWeek = tmrsDaysOfWeek;
        this.timersOperationMode = tmrsOperationMode;
        this.timersSetTemperature = tmrsSetTemperature;
        this.timersOnOff = tmrsOnOff;

    }
}
