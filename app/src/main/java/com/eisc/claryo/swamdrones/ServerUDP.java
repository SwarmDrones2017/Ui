package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Created by sofiane on 14/02/17.
 */

public class ServerUDP {

    public final static int port = 55555;
    final static int taille = 1024;
    public static DatagramSocket socket;
    public static Thread t;
    static byte buffer[] = new byte[taille];
    final String RPI_SMARTPHONE = "Telephone";
    final String RPI_SENSORS = "Sensor";
    final String RPI_REPONSE = "Oui\n";
    final String DECOUPE_SENSOR = ",";
    private final String TAG = "ServerUDP";


    ServerUDP(final Context context) {
        //Thread t = new Thread();
        //t.start();
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new DatagramSocket(port);
                    DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                    String str;
                    while (true) {
                        do {
                            socket.receive(paquet);
                            str = new String(paquet.getData());
                        } while (str.indexOf("\n") == -1);

                        String sframe = str.substring(0, str.indexOf("\n"));
                        String scmd = sframe.substring(0, sframe.indexOf(" "));
                        switch (scmd) {
                            case RPI_SENSORS:

                                String north;
                                String west;
                                String south;
                                String est;


                                String ssensor = sframe.substring(sframe.lastIndexOf(" "), sframe.length());

                                if (ssensor != null) {
                                    String[] listSensor = ssensor.split(DECOUPE_SENSOR);
                                    int index = GlobalCouple.raspberryIPCorrespondante(paquet.getAddress());
                                    if (index != -1) {
                                        for (int i = 0; i < listSensor.length; i++) {

                                            Log.e(TAG, "Message vsensor : " + listSensor[i] + paquet.getAddress().getAddress());
                                            String val = listSensor[i].substring(1, listSensor[i].indexOf(":"));
                                            String capt = listSensor[i].substring(listSensor[i].lastIndexOf(":") + 1, listSensor[i].length());

                                            if (capt.equals("n")) {
                                                north = val;
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);

                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setNorth(valeur);
                                                    } catch (NumberFormatException e) {

                                                    }
                                                }

                                            }

                                            if (capt.equals("w")) {
                                                west = val;
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);

                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setWest(valeur);
                                                    } catch (NumberFormatException e) {

                                                    }
                                                }
                                            }

                                            if (capt.equals("s")) {
                                                south = val;
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);
                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setSouth(valeur);
                                                    } catch (NumberFormatException e) {

                                                    }
                                                }

                                            }

                                            if (capt.equals("e")) {
                                                est = val;

                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);

                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setEst(valeur);
                                                    } catch (NumberFormatException e) {

                                                    }
                                                }

                                            }


                                        }
                                        sendAck(paquet.getAddress(), paquet.getPort());
                                    } else {//sinon reconstructon//TODO realiser une focntion car bout de code utiliser deux fois

                                    }
                                }
                                //Toast.ma{keText(context,sframe,Toast.LENGTH_SHORT);

                                break;
                            case RPI_SMARTPHONE:


                                //Création de l'objet Raspberry s'il n'existe pas dans la liste des couples
                                //et insertion de l'objet dans la liste des couples
                                if (!GlobalCouple.raspberryExist(paquet.getAddress())) {
                                    Raspberry rpi = new Raspberry(paquet.getAddress(), paquet.getPort());
                                    int index = GlobalCouple.droneCorrespondant(rpi);
                                    if (index != -1)//on a trouvé un drone correspondant à la raspberry
                                        GlobalCouple.couples.get(index).setRaspberry(rpi);
                                    else
                                        GlobalCouple.couples.add(new Couple(null, rpi));
                                }
                                sendAck(paquet.getAddress(), paquet.getPort());
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
        });
        t.start();

    }

    void sendAck(InetAddress iaddr, int port) throws IOException {
        byte[] buf_send = RPI_REPONSE.getBytes(Charset.forName("UTF-8"));
        DatagramPacket envoi = new DatagramPacket(buf_send, buf_send.length);
        envoi.setAddress(iaddr);
        envoi.setPort(port);
        socket.send(envoi);
    }
}