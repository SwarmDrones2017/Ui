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

    /**
     * Méthode pour récuperer le nom du drone ainsi que son état de connexion
     * @param name
     * @param connect
     */

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
