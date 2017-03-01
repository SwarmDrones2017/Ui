package com.eisc.claryo.swamdrones;

/**
 * Classe permettant de donner les caractéristiques de chaque drones
 *
 * A voir dans DroneDetails.java
 */

class CaractDrone {

    private String categorie;
    private String caracteristique;

    public CaractDrone(){
        super();
    }

    public CaractDrone(String name, String connect) {
        super();
        this.categorie = name;
        this.caracteristique = connect;
    }

    @Override
    public String toString() {
        return this.categorie + " " + this.caracteristique;
    }

}
