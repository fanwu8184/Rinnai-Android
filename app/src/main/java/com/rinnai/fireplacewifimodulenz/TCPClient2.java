package com.rinnai.fireplacewifimodulenz;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by chris on 16/8/17.
 */

public class TCPClient2 extends Thread {

    private int dataMode;   //String or bytes

    private int destinationPort;
    private String destinationIPAddress;

    private ActivityClientInterfaceTCP currentActicity;

    private String packetPayload;
    private String serverResponse;

    //Bytes
    private OutputStream outRaw;
    private InputStream inRaw;

    //Strings
    private PrintWriter outStream;
    private BufferedReader inStream;

    private boolean Reply;

    Socket socket;

    public TCPClient2(int destinationPort, String destinationIPAddress, ActivityClientInterfaceTCP activity, String payload, boolean Reply) {

        this.dataMode = 0;

        this.destinationPort = destinationPort;
        this.destinationIPAddress = destinationIPAddress;
        this.currentActicity = activity;
        this.packetPayload = payload;

        this.Reply = Reply;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        boolean mRun = true;
        try {


            Log.d("myApp_WiFiTCP", "TCPClient_run - connecting to " + this.destinationIPAddress + ":" + this.destinationPort);

            //destination
            InetAddress serverAddr = InetAddress.getByName(this.destinationIPAddress);

            //create a socket to make the connection with the server
            socket = new Socket(serverAddr, this.destinationPort);

            socket.setKeepAlive(false);
            socket.setSoTimeout(0);

            try {

                if (this.dataMode == 0) {

                    //ready to send data
                    outStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


                    outStream.write(this.packetPayload);
                    outStream.flush();
                    Log.d("myApp_WiFiTCP", "TCPClient_run - TX:" + this.packetPayload + "");

                    long communicationStart = System.currentTimeMillis();

                    if (this.Reply == false) {
                        mRun = false;
                    } else {
                        //ready to read data
                        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    }

                    while (mRun == true) {
                        this.serverResponse = inStream.readLine();

                        if (this.serverResponse != null) {

                            Log.d("myApp_WiFiTCP", "TCPClient_run - RX(test):" + this.serverResponse);

                            String commandID = AppGlobals.fireplaceWifi.get(AppGlobals.selected_fireplaceWifi).processRXTCPData(this.serverResponse);
                            Log.d("myApp_WiFiTCP", "TCPClient_run - RX(commandID):" + commandID);

                            this.currentActicity.clientCallBackTCP(commandID, this.serverResponse + "");
                            Log.d("myApp_WiFiTCP", "TCPClient_run - RX:" + this.serverResponse);

                            AppGlobals.CommErrorFault.resetCommunicationErrorFault();

                            mRun = false;
                        }
                        this.serverResponse = null;

                        long communicationTime = System.currentTimeMillis();

                        if ((communicationTime - communicationStart) > 500) {
                            //Timeout
                            mRun = false;

                            Log.d("myApp_WiFiTCP", "TCPClient_run - Timeout");
                        }
                    }

                } else {
                    //send our message
                    outRaw = socket.getOutputStream();

                    //setup to receive server response
                    inRaw = socket.getInputStream();

                    byte[] buf = new byte[1024];

                    while (mRun == true) {

                        int len = inRaw.read(buf);


                        if (len > 0) {
                            //call the method messageReceived from MyActivity class

                        }


                    }
                }

            } catch (Exception e) {

                Log.d("myApp_WiFiTCP", "TCPClient_run -Exception:" + e.getMessage());

            } finally {
                socket.close();
                Log.d("myApp_WiFiTCP", "TCPClient_run - socket close()");
            }

        } catch (Exception e) {

            Log.d("myApp_WiFiTCP", "TCPClient_run outer -Exception:" + e.getMessage());

        }
        //this.currentActicity.clientCallBackTCP("0", "Client Closed");
    }
}
