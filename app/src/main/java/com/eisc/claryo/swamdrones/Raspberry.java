package com.eisc.claryo.swamdrones;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.net.InetAddress;

/**
 * Created by jolwarden on 15/02/17.
 */

public class Raspberry {
    private boolean state;
    private InetAddress address;
    private Cardinal obstacle = new Cardinal();
    private Bundle messageBundle = new Bundle();
    private Handler handlerObstacle;

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

    public void setHandlerObstacle(Handler handlerObstacle) {
        this.handlerObstacle = handlerObstacle;
    }
    public Handler getHandlerObstacle() {
        return handlerObstacle;
    }

    class Cardinal {
        private int north;
        private int west;
        private int south;
        private int est;

        public void setNorth(int north) {
            this.north = north;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLENORTH, north);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }

        public void setWest(int west) {
            this.west = west;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLENORTH, west);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }

        public void setSouth(int south) {
            this.south = south;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLENORTH, south);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }

        public void setEst(int est) {
            this.est = est;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLENORTH, est);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }
    }
}
