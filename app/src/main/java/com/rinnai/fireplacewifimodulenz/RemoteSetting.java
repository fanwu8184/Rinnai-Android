package com.rinnai.fireplacewifimodulenz;

public class RemoteSetting {
    public String faultCode = "";
    public int setTemp = 0;
    public int setFlame = 0;
    public int currentTemp = 0;
    public int mode = 0;

    public RemoteSetting(String faultCode, int setTemp, int setFlame, int currentTemp, int mode){

        this.faultCode = faultCode;
        this.setTemp = setTemp;
        this.setFlame = setFlame;
        this.currentTemp = currentTemp;
        this.mode = mode;
    }
}