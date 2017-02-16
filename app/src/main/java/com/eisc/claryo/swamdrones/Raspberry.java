package com.eisc.claryo.swamdrones;

import java.net.InetAddress;

/**
 * Created by jolwarden on 15/02/17.
 */

public class Raspberry {
    private boolean state;
    private InetAddress address;

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
}
