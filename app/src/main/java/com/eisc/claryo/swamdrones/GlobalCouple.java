package com.eisc.claryo.swamdrones;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jolwarden on 15/02/17.
 */

public class GlobalCouple {
    static public ArrayList<Couple> couples;

    static public boolean raspberryExist(InetAddress address){
        InetAddress tmp;
        boolean exist = false;
        for(int i=0; i<couples.size(); i++) {
            tmp = couples.get(i).getRaspberry().getAddress();
            if(tmp.equals(address)){
                exist = true;
            }
        }
        return exist;
    }

    static public boolean droneExist(BebopDrone drone){
        BebopDrone tmp;
        boolean exist = false;
        for(int i=0; i<couples.size(); i++) {
            tmp = couples.get(i).getBebopDrone();
            if(tmp.equals(drone)){
                exist = true;
            }
        }
        return exist;
    }

   static public int raspberryCorrespondante(BebopDrone drone){
        int positionCoupleDansListe =-1;
        for(int i=0; i<couples.size(); i++){
            if(drone.equals(couples.get(i).getBebopDrone()))
                positionCoupleDansListe = i;
        }
        return positionCoupleDansListe;
    }
    static public int droneCorrepondant(Raspberry raspberry){
        int positionCoupleDansListe =-1;
        for(int i=0; i<couples.size(); i++){
            if(raspberry.equals(couples.get(i).getRaspberry()))
                positionCoupleDansListe = i;
        }
        return positionCoupleDansListe;
    }

}
