package com.rinnai.fireplacewifimodulenz;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Created by chris on 8/12/16.
 */

public class UDPServer extends Thread {

    private int port;
    private boolean isRunning = false;
    private DatagramSocket socket;

    private ActivityServerInterfaceUDP currentActivity;

    public UDPServer(int serverPort) {

        Log.d("myApp_WiFiUDP", "UDPServer_UDPServer: UDP Server init.");

        this.port = serverPort;

    }

    public void setCurrentActivity(ActivityServerInterfaceUDP activity) {
        this.currentActivity = activity;

        this.currentActivity.serverCallBackUDP("UDP Server Call Back Activity changed");
    }

    public boolean getRunning() {

        return this.isRunning;

    }

    public void stopServer() {

        try {

            if (this.isRunning) {
                socket.close();

                this.stop();
            }

            this.isRunning = false;

        } catch (Exception e) {
            Log.d("myApp_WiFiUDP", "UDPServer: stopServer(Exception - " + e + ")");
        }
    }

    public void serverCallBackUDP_Safe(String mesg){

        try{
            this.currentActivity.serverCallBackUDP(mesg);
        } catch(Exception e){
            Log.d("myApp_WiFiUDP", "UDPServer: serverCallBackUDP_Safe(Exception - " + e + ")");
        }
    }

    @Override
    public void run() {

        this.isRunning = true;

        try {

            Log.d("myApp_WiFiUDP", "UDPServer_run: UDP Server Has Started.");
            this.serverCallBackUDP_Safe("UDP Server Has Started (" + this.port + ")");

            socket = new DatagramSocket(this.port);

            while (this.isRunning) {

                try{

                    byte[] buf = new byte[256];//hard limit, me no like

                    // incoming
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);     //this code block the program flow

                    // reply?
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();

                    //this.serverCallBackUDP_Safe("Request from: " + address + ":" + port + "\n");
                    Log.d("myApp_WiFiUDP", "UDPServer_run: Recived Request:" + address + ":" + port + "\n");

                    String packetData = (new String(packet.getData(), Charset.forName("UTF-8")));

                    this.serverCallBackUDP_Safe(packetData);
                    Log.d("myApp_WiFiUDP", "UDPServer_run: RX Data(String) :" + packetData + "\n");
                    //Log.d("myApp_WiFiUDP","UDPServer:RX Data(bytes)  :"+FormattingFunctions.GetByteArrayAsHexString(packet.getData(),packet.getLength())+"\n");

                    String fromIpAddress = (address + "").substring(1);
                    String currentIpAddress = NetworkFunctions.getIPAddress();

                    //Log.d("myApp_WiFiUDP","UDPServer:IP Addresses :"+fromIpAddress+"|"+currentIpAddress+"\n");

                    if (fromIpAddress.compareTo(currentIpAddress) != 0) {

                        //check if device is in list...


                        //Process Data
                        boolean valid = RinnaiFireplaceWiFiModule.processRXUDPData(packetData);

                        if (valid == true) {

                            Log.d("myApp_WiFiUDP", "UDPServer_run: Valid Mesg  :) [" + fromIpAddress + "] @");

                            int index = AppGlobals.GetIndexOfDeviceInListWithIPAddress(fromIpAddress);

                            AppGlobals.fireplaceWifi.get(index).setIpAddress(fromIpAddress);

                            AppGlobals.fireplaceWifi.get(index).setDeviceName(packetData);

                        } else {

                            Log.d("myApp_WiFiUDP", "UDPServer_run: Invalid Mesg  :(");
                        }

                        /*
                        String dString = new Date().toString() + "\n"
                                + "Your address " + address.toString() + ":" + String.valueOf(port);

                        buf = dString.getBytes();
                        packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                        */
                    } else {
                        Log.d("myApp_WiFiUDP", "UDPServer_run: Device Already In List:" + fromIpAddress + "");
                    }

                } catch (Exception e){
                    Log.d("myApp_WiFiUDP", "UDPServer_run: (Exception - " + e + ")");

                    this.isRunning = false;
                }
            }

            Log.d("myApp_WiFiUDP", "UDPServer_run: UDP Server Has Stopped.");

        } catch (SocketException e) {
            e.printStackTrace();
            Log.d("myApp_WiFiUDP", "UDPServer_run: (SocketException - " + e + ")");
        } finally {
            if (socket != null) {
                socket.close();

                Log.d("myApp_WiFiUDP", "UDPServer_run: UDP Server Has Stopped.");

                this.serverCallBackUDP_Safe("UDP Server Has Stopped");
            }

            this.isRunning = false;
        }
    }


}
