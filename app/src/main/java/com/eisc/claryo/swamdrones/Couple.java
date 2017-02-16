package com.eisc.claryo.swamdrones;

/**
 * Created by jolwarden on 15/02/17.
 */

public class Couple {
    BebopDrone bebopDrone;
    Raspberry raspberry;
    boolean ok=false;

    public Couple(BebopDrone bebopDrone, Raspberry raspberry) {
        this.bebopDrone = bebopDrone;
        this.raspberry = raspberry;
    }

    public BebopDrone getBebopDrone() {
        return bebopDrone;
    }

    public void setBebopDrone(BebopDrone bebopDrone) {
        this.bebopDrone = bebopDrone;
        if(this.raspberry !=null)
            ok=true;
    }

    public Raspberry getRaspberry() {

        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
        if(this.bebopDrone != null)
            ok = true;
    }

}
