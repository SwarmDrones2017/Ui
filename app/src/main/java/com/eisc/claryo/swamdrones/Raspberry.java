package com.eisc.claryo.swamdrones;

import java.net.InetAddress;

/**
 * Created by jolwarden on 15/02/17.
 */

public class Raspberry {
    private boolean state;
    private InetAddress address;
    private Cardinal obstacle;

    public Raspberry(InetAddress address) {
        this.address = address;
        this.state = true;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }

    public boolean isState() {
        return state;
    }

    public Cardinal getObstacle() {
        return obstacle;
    }

    class Cardinal {
        private int north;
        private int west;
        private int south;
        private int est;

        public void setNorth(int north) {
            this.north = north;
        }

        public void setWest(int west) {
            this.west = west;
        }

        public void setSouth(int south) {
            this.south = south;
        }

        public void setEst(int est) {
            this.est = est;
        }

        
    }
}
