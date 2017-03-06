package com.eisc.claryo.swamdrones;

import com.parrot.arsdk.arcontroller.ARDeviceController;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by jolwarden on 15/02/17.
 * Gestion des couples Drone/Raspberry et fonctions de vérification
 */

class GlobalCouple {
    static public ArrayList<Couple> couples;

    /**
     * Lors de la détection d'une Raspberry, on vérifie si celle-ci n'est pas déjà enregistrée
     * dans le système, ceci afin d'éviter des doublons et d'avoir une liste infinie
     * @param address Adresse IPv d'une Raspberry Pi
     * @return true si la raspberry existe déjà, false sinon
     */
    static public boolean raspberryExist(InetAddress address) {
        InetAddress tmp;
        for (int i = 0; i < couples.size(); i++) {
            if (couples.get(i).getRaspberry() != null) {
                tmp = couples.get(i).getRaspberry().getAddress();
                if (tmp.equals(address)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Lors de la création d'un objet BebopDrone, on vérifie si celui-ci n'existe pas déjà dans le système
     * ceci afin d'éviter les doublons et une liste infinie
     * @param nameDrone vérification par nom de drone
     * @return true si le drone existe déjà, false sinon
     */
    static public boolean droneExist(String nameDrone) {
        String tmp;
        for (int i = 0; i < couples.size(); i++) {
            if (couples.get(i).getBebopDrone() != null) {
                tmp = couples.get(i).getBebopDrone().getInfoDrone().getDroneName();
                if (tmp.equals(nameDrone)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Chercher la pi correspondante au drone. Lors de la création d'un objet BebopDrone,
     * on vérifie si la Raspberry correspondante n'existe pas dans le système.
     * La règle de correspondance est celle-ci :
     *      Le dernier octet de l'adresse IPv de la Raspberry Pi est égale au dernier octet de
     *      l'adresse IPv du drone -1.
     *   Exemple :  - @Drone = 192.168.2.49
     *              - @Raspberry Pi Correspondante = 192.168.2.48
     * @param drone objet Bebop crée
     * @return index du couple, -1 si la raspberry pi n'est pas créée
     */
    static public int raspberryCorrespondante(BebopDrone drone) {
        byte[] droneAdd = drone.getIP().getAddress();
        byte droneLastOctet = droneAdd[droneAdd.length - 1];

        for (int i = 0; i < couples.size(); i++) {
            if (couples.get(i).getRaspberry() != null) {
                byte[] rpiAdd = couples.get(i).getRaspberry().getAddress().getAddress();
                byte rPiLastOctet = rpiAdd[rpiAdd.length - 1];
                if (rPiLastOctet + 1 == droneLastOctet)
                    return i;
            }
        }
        return -1;
    }
    /**
     * Chercher le drone correspondant à la Raspberry Pi. Lors de la création d'un objet Raspberry,
     * on vérifie si le Drone correspondant n'existe pas dans le système.
     * La règle de correspondance est celle-ci :
     *      Le dernier octet de l'adresse IPv de la Raspberry Pi est égale au dernier octet de
     *      l'adresse IPv du drone -1.
     *   Exemple :  - @Drone = 192.168.2.49
     *              - @Raspberry Pi Correspondante = 192.168.2.48
     * @param raspberry objet Raspberry créé
     * @return index du couple, -1 si le drone n'est pas créé
     */
    static public int droneCorrespondant(Raspberry raspberry) {
        byte[] rpiAdd = raspberry.getAddress().getAddress();
        byte rPiLastOctet = rpiAdd[rpiAdd.length - 1];
        for (int i = 0; i < couples.size(); i++) {
            if (couples.get(i).getBebopDrone() != null) {
                byte[] droneAdd = couples.get(i).getBebopDrone().getIP().getAddress();
                byte droneLastOctet = droneAdd[droneAdd.length - 1];
                if (rPiLastOctet == droneLastOctet - 1)
                    return i;
            }
        }
        return -1;
    }

    /**
     * recherche dans la liste des couples la position du drone donné en paramètre. La recherche s'effectue par nom.
     * @param name nom du drone
     * @return l'indice du couple dans lequel il contient le bebop name, -1 si le drone n'est pas trouvé
     */
    static public int droneNameCorrespondant(String name) {
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                if (name.equals(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName())) {
                    return i;
                }
            }

        }
        return -1;
    }

    /**
     * recherche dans la liste des couples la position du drone donné en paramètre. La recherche s'effectue par deviceController.
     * @param deviceController le device controller du drone recherché
     * @return la position du drone recherché dans la liste de couple, -1 si le drone n'existe pas
     */
    static public int indexDroneCorrespondant(ARDeviceController deviceController) {
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                if (GlobalCouple.couples.get(i).getBebopDrone().getmDeviceController() == deviceController) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * @return l'indice du master, si aucun master alors -1
     */
    static public int whoIsMaster() {

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                if (GlobalCouple.couples.get(i).getBebopDrone().isMaster()) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Recherche une Raspberry Pi dans la liste des couples, recherche par adresse IP
     * @param addr l'adresse IP de la Raspberry recherchée
     * @return l'indice correspondant, -1 si existe pas
     */
    static public int raspberryIPCorrespondante(InetAddress addr) {
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getRaspberry() != null) {
                if (GlobalCouple.couples.get(i).getRaspberry().getAddress().equals(addr)) {
                    return i;
                }
            }
        }
        return -1;
    }

}
