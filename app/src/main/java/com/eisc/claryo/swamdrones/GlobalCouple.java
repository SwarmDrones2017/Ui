package com.eisc.claryo.swamdrones;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jolwarden on 15/02/17.
 */

public class GlobalCouple {
    static public ArrayList<Couple> couples;

    static public boolean raspberryExist(InetAddress address) {
        InetAddress tmp;
        boolean exist = false;
        for (int i = 0; i < couples.size(); i++) {
            if(couples.get(i).getBebopDrone() != null) {
                if(couples.get(i).getRaspberry() != null){
                    tmp = couples.get(i).getRaspberry().getAddress();
                    if (tmp.equals(address)) {
                        exist = true;
                    }
                }
            }
        }
        return exist;
    }

    static public boolean droneExist(String nameDrone) {
        String tmp;
        boolean exist = false;
        for (int i = 0; i < couples.size(); i++) {
            if(couples.get(i).getBebopDrone() != null){
                tmp = couples.get(i).getBebopDrone().getInfoDrone().getDroneName();
                if (tmp.equals(nameDrone)) {
                    exist = true;
                }
            }
        }
        return exist;
    }

   static public int raspberryCorrespondante(BebopDrone drone){
       int positionCoupleDansListe =-1;
       byte[] droneAdd = drone.getIP().getAddress();
       byte droneLastOctet = droneAdd[droneAdd.length-1];

        for (int i = 0; i < couples.size(); i++) {
            if (couples.get(i).getRaspberry() != null) {
                byte[] rpiAdd = couples.get(i).getRaspberry().getAddress().getAddress();
                byte rPiLastOctet = rpiAdd[rpiAdd.length - 1];
                if (rPiLastOctet - 1 == droneLastOctet)
                    positionCoupleDansListe = i;
            }
        }
        return positionCoupleDansListe;
    }

    static public int droneCorrespondant(Raspberry raspberry) {
        int positionCoupleDansListe = -1;
        byte[] rpiAdd = raspberry.getAddress().getAddress();
        byte rPiLastOctet = rpiAdd[rpiAdd.length-1];
        for(int i=0; i<couples.size(); i++){
            byte[] droneAdd = couples.get(i).getBebopDrone().getIP().getAddress();
            byte droneLastOctet = droneAdd[droneAdd.length-1];
            if(rPiLastOctet == droneLastOctet+1)
                positionCoupleDansListe = i;
        }
        return positionCoupleDansListe;
    }

    /**
     * Retourne l'indice du couple dans lequel il contient le bebop avec le nom name
     * @param name
     * @return
     */
    static public int droneNameCorrespondant(String name){
        for (int i = 0;i < GlobalCouple.couples.size();i++){
            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                if(name.equals(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName())){
                    return i;
                }
            }

        }
        return -1;
    }

    /**
     *
     * @return l'indice du master, si aucun aster alors -1
     */
    static public int whoIsMaster(){

        for (int i = 0;i < GlobalCouple.couples.size();i++){
            if(GlobalCouple.couples.get(i).getBebopDrone().isMaster() == true){
                return i;
            }
        }
        return -1;
    }
    /**
     *
     * @param addr
     * @return l'indice correspondant, -1 si existe pas
     */
    static public int raspberryIPCorrespondante(InetAddress addr)
    {
        for (int i = 0;i < GlobalCouple.couples.size();i++){
            if(GlobalCouple.couples.get(i).getRaspberry()!=null){
                if(GlobalCouple.couples.get(i).getRaspberry().getAddress().equals(addr) == true){
                    return i;
                }
            }
        }
        return -1;
    }

}
