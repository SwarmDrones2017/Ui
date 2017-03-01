package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;

/**
 * Created by sofiane on 14/02/17.
 */

class ServerUDP {

    public final static int port = 55555;
    private final static int taille = 1024;
    private static final byte[] buffer = new byte[taille];
    private final String RPI_SMARTPHONE = "Telephone";
    private final String RPI_SENSORS = "Sensor";
    private final String RPI_REPONSE = "Oui\n";
    private final String DECOUPE_SENSOR = ",";
    private final String TAG = "ServerUDP";
    public static DatagramSocket socket;
    public static Thread t;


    ServerUDP(final Context context) {
        //Thread t = new Thread();
        //t.start();
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new DatagramSocket(port);
                    DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
                    DatagramPacket envoi;
                    String str;
                    while (true) {
                        do {
                            socket.receive(paquet);
                            str = new String(paquet.getData());
                        } while (!str.contains("\n"));

                        String sframe = str.substring(0, str.indexOf("\n"));
                        String scmd = sframe.substring(0, sframe.indexOf(" "));
                        switch (scmd) {
                            case RPI_SENSORS:
                                String north;
                                String west;
                                String south;
                                String est;

                                Log.e(TAG, "Message str :" + str);
                                Log.e(TAG, "Message sframe :" + sframe);
                                String ssensor = sframe.substring(sframe.lastIndexOf(" "), sframe.length());

                                if (ssensor != null) {
                                    String[] listSensor = ssensor.split(DECOUPE_SENSOR);
                                    Log.e(TAG, "Message sensor :" + ssensor);

                                    for (String aListSensor : listSensor) {

                                        Log.e(TAG, "Message vsensor : " + aListSensor);
                                        String val = aListSensor.substring(1, aListSensor.indexOf(":"));
                                        String capt = aListSensor.substring(aListSensor.lastIndexOf(":") + 1, aListSensor.length());
                                        Log.e(TAG, "Message val : " + val);
                                        Log.e(TAG, "Message capt : " + capt);
                                        int index = GlobalCouple.raspberryIPCorrespondante(paquet.getAddress());

                                        if (index != -1) {
                                            if (capt.equals("n")) {
                                                north = val;
                                                Log.e(TAG, "Capteur nord : " + north);
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);
                                                        /*if (valeur <= Raspberry.SEUIL_OBSTACLE_STOP) {
                                                            for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                                if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                                    GlobalCouple.couples.get(j).getBebopDrone().stopMoveForward();
                                                                }
                                                            }
                                                        }*/
                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setNorth(valeur);
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            if (capt.equals("w")) {
                                                west = val;
                                                Log.e(TAG, "Capteur ouest : " + west);
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);
                                                        /*if (valeur <= Raspberry.SEUIL_OBSTACLE_STOP) {
                                                            for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                                if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                                    GlobalCouple.couples.get(j).getBebopDrone().stopMoveLeft();
                                                                }
                                                            }
                                                        }*/
                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setWest(valeur);
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                            if (capt.equals("s")) {
                                                south = val;
                                                Log.e(TAG, "Capteur sud : " + south);
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);
                                                        /*if (valeur <= Raspberry.SEUIL_OBSTACLE_STOP) {
                                                            for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                                if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                                    GlobalCouple.couples.get(j).getBebopDrone().stopMoveBack();
                                                                }
                                                            }
                                                        }*/
                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setSouth(valeur);
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }

                                            if (capt.equals("e")) {
                                                est = val;

                                                Log.e(TAG, "Capteur est : " + est);
                                                if (index != -1) {
                                                    try {
                                                        int valeur = Integer.valueOf(val);
                                                        /*if (valeur <= Raspberry.SEUIL_OBSTACLE_STOP) {
                                                            for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                                if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                                    GlobalCouple.couples.get(j).getBebopDrone().stopMoveRight();
                                                                }
                                                            }
                                                        }*/
                                                        GlobalCouple.couples.get(index).getRaspberry().getObstacle().setEst(valeur);
                                                    } catch (NumberFormatException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                           /* try{
                                                if (Integer.valueOf(val) <= 50) {
                                                    for (int j = 0; j < GlobalCouple.couples.size(); j++) {
                                                        if (GlobalCouple.couples.get(j).getBebopDrone() != null) {
                                                            GlobalCouple.couples.get(j).getBebopDrone().stationnaire();
                                                        }
                                                    }
                                                }
                                            }catch (NumberFormatException e){

                                            }*/

                                        } else {//sinon reconstructon//TODO realiser une focntion car bout de code utiliser deux fois

                                        }
                                    }
                                }

                                //Toast.ma{keText(context,sframe,Toast.LENGTH_SHORT);

                                break;
                            case RPI_SMARTPHONE:
                                //Toast.makeText(context,sframe,Toast.LENGTH_SHORT);
                                //Log.d(TAG,"Message reçu : "+sframe);
                                byte[] buf_send = RPI_REPONSE.getBytes(Charset.forName("UTF-8"));
                                envoi = new DatagramPacket(buf_send, buf_send.length);
                                envoi.setAddress(paquet.getAddress());
                                envoi.setPort(paquet.getPort());
                                socket.send(envoi);

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

                                break;
                        }
                        paquet.setLength(buffer.length);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        t.start();
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
