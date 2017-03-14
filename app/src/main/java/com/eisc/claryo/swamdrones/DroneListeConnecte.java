package com.eisc.claryo.swamdrones;

/**
 * Classe permettant de gerer la liste des drones ainsi que leur état
 * connectés ou non
 * <p>
 * A voir dans MainActivity.java
 */

public class DroneListeConnecte {

    private String name;
    private String connect;

    public DroneListeConnecte(String name, String connect) {
        super();
        this.name = name;
        this.connect = connect;
    }

    @Override
    public String toString() {
        return this.name + "              " + this.connect;
    }

    public String getname() {
        return name;
    }

    public String getConnect() {
        return connect;
    }

}
