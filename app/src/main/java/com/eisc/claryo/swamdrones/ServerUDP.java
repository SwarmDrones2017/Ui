package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofiane on 14/02/17.
 */

public class ServerUDP {

    private final String TAG = "ServerUDP";
    final static int port = 55555;

    final static int taille = 512;
    static byte buffer[] = new byte[taille];

    final String RPI_SMARTPHONE = "Telephone";
    final String RPI_SENSORS = "Sensor";
    final String RPI_REPONSE = "Oui\n";
    final String RPI_ACK = "OK";
    final String DECOUPE_SENSOR = ",";

    ServerUDP(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(port);
                    DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                    DatagramPacket envoi = null;
                    String str;
                    while(true){
                        do {
                            socket.receive(paquet);
                            str = new String(paquet.getData());
                        }while(str.indexOf("\n") == -1);

                        String sframe = str.substring(0,str.indexOf("\n"));
                        String scmd = sframe.substring(0,sframe.indexOf(" "));
                        switch (scmd){
                            case RPI_SENSORS :
                                String north;
                                String west;
                                String south;
                                String est;

                                int tai=str.trim().split("\n").length;
                                Log.e(TAG,"Message tai :"+tai);

                                String ssensor = sframe.substring(sframe.lastIndexOf(" "),sframe.length());
                                String [] listSensor = ssensor.split(DECOUPE_SENSOR);
                                Log.e(TAG,"Message sensor :"+ssensor);
                                String back = ssensor.substring(0,ssensor.indexOf("\n"));
                                Log.e(TAG,"Message back :"+back);
                                String vsensor[] = back.split("\n");

                                for(int i = 0; i<listSensor.length; i++) {

                                    Log.e(TAG, "Message vsensor : " + listSensor[i]);
                                    String val = listSensor[i].substring(0, listSensor[i].indexOf(":"));
                                    String capt = listSensor[i].substring(listSensor[i].lastIndexOf(":")+1, listSensor[i].length());
                                    Log.e(TAG, "Message val : " + val);
                                    Log.e(TAG, "Message capt : " + capt);

                                    if (capt == "n") {
                                        north = val;
                                        Log.e(TAG, "Message north : " + north);
                                    }

                                    if (capt == "w") {
                                        west = val;
                                        Log.e(TAG, "Message north : " + west);
                                    }

                                    if (capt == "s") {
                                        south = val;
                                        Log.e(TAG, "Message north : " + south);
                                    }

                                    if (capt == "e") {
                                        est = val;
                                        Log.e(TAG, "Message north : " + est);
                                    }
                                }

                                //Toast.makeText(context,sframe,Toast.LENGTH_SHORT);

                                break;
                            case RPI_SMARTPHONE :
                                //Toast.makeText(context,sframe,Toast.LENGTH_SHORT);
                                //Log.d(TAG,"Message reçu : "+sframe);
                                byte [] buf_send = RPI_REPONSE.getBytes(Charset.forName("UTF-8"));
                                envoi = new DatagramPacket(buf_send,buf_send.length);
                                envoi.setAddress(paquet.getAddress());
                                envoi.setPort(paquet.getPort());
                                socket.send(envoi);

                                //Création de l'objet Raspberry s'il n'existe pas dans la liste des couples
                                //et insertion de l'objet dans la liste des couples
//                                if(!GlobalCouple.raspberryExist(paquet.getAddress())) {
//                                    Raspberry rpi = new Raspberry(paquet.getAddress());
//                                    int index=GlobalCouple.droneCorrepondant(rpi);
//                                    if(index!=-1)//on a trouvé un drone correspondant à la raspberry
//                                        GlobalCouple.couples.get(index).setRaspberry(rpi);
//                                    else
//                                        GlobalCouple.couples.add(new Couple(null, rpi));
//                                }

                                break;
                        }
                        paquet.setLength(buffer.length);
                    }



                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte []buf = new byte[1024];
                    DatagramSocket socket = new DatagramSocket(5300);
                    DatagramPacket packet = new DatagramPacket(buf,buf.length);
                    System.arraycopy("salut".getBytes(),0,buf,0,"salut".getBytes().length);
                    packet.setAddress(InetAddress.getByName("192.168.2.30"));
                    socket.send(packet);
                    int i = 0;
                    i+=20;
                    int A = 4;
                    A+=i;
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();*/

    }

}
