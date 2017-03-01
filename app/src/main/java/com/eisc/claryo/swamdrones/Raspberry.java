package com.eisc.claryo.swamdrones;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.net.InetAddress;

/**
 * Created by jolwarden on 15/02/17.
 */

public class Raspberry {
    private InetAddress address;
    private final int port;
    private final Cardinal obstacle = new Cardinal();
    private final Bundle messageBundle = new Bundle();
    private Handler handlerObstacle;
    public final static int SEUIL_OBSTACLE_STOP = 50;

    public Raspberry(InetAddress address,int port) {
        this.address = address;
        this.port = port;
    }


    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public Cardinal getObstacle() {
        return obstacle;
    }

    public void setHandlerObstacle(Handler handlerObstacle) {
        this.handlerObstacle = handlerObstacle;
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
                messageBundle.putInt(MessageKEY.OBSTACLEWEST, west);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }

        public void setSouth(int south) {
            this.south = south;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLESOUTH, south);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }

        public void setEst(int est) {
            this.est = est;
            if(handlerObstacle != null){
                Message myMessage = handlerObstacle.obtainMessage();
                messageBundle.putInt(MessageKEY.OBSTACLEEST, est);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handlerObstacle.sendMessage(myMessage);
            }
        }
    }
}
