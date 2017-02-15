package com.eisc.claryo.swamdrones;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.parrot.arsdk.ARSDK;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import com.parrot.arsdk.ardiscovery.receivers.ARDiscoveryServicesDevicesListUpdatedReceiver;
import com.parrot.arsdk.ardiscovery.receivers.ARDiscoveryServicesDevicesListUpdatedReceiverDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sofiane on 13/02/17.
 */

/**
 * @author sofiane
 * @version 1.0
 *          Class permet de decouvrir les drones sur le reseau
 */
public class DiscoveryDrone implements ARDiscoveryServicesDevicesListUpdatedReceiverDelegate {

    private final static String TAG = "DiscoveryDrone";
    private Context context;
    private List<ARDiscoveryDeviceService> deviceList;

    private Handler handler;

    /////////Start discovery:
    private ARDiscoveryService mArdiscoveryService;
    private ServiceConnection mArdiscoveryServiceConnection;

    private void initDiscoveryService() {
        // create the service connection
        if (mArdiscoveryServiceConnection == null) {
            mArdiscoveryServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mArdiscoveryService = ((ARDiscoveryService.LocalBinder) service).getService();

                    startDiscovery();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    mArdiscoveryService = null;
                }
            };
        }

        if (mArdiscoveryService == null) {
            // if the discovery service doesn't exists, bind to it
            //Ne fonctionne pas avec l'interface
            Intent i = new Intent(context, ARDiscoveryService.class);
            context.bindService(i, mArdiscoveryServiceConnection, Context.BIND_AUTO_CREATE);
        } else {
            // if the discovery service already exists, start discovery
            startDiscovery();
        }
    }

    private void startDiscovery() {
        if (mArdiscoveryService != null) {
            mArdiscoveryService.start();
        }
    }

    ///////////The libARDiscovery will let you know when BLE and Wifi devices have been found on network:
    ARDiscoveryServicesDevicesListUpdatedReceiver mArdiscoveryServicesDevicesListUpdatedReceiver;

    // your class should implement ARDiscoveryServicesDevicesListUpdatedReceiverDelegate
    private void registerReceivers() {
        mArdiscoveryServicesDevicesListUpdatedReceiver = new ARDiscoveryServicesDevicesListUpdatedReceiver(this);
        LocalBroadcastManager localBroadcastMgr = LocalBroadcastManager.getInstance(context);
        localBroadcastMgr.registerReceiver(mArdiscoveryServicesDevicesListUpdatedReceiver, new IntentFilter(ARDiscoveryService.kARDiscoveryServiceNotificationServicesDevicesListUpdated));
    }

    @Override
    public void onServicesDevicesListUpdated() {
        Log.d(TAG, "onServicesDevicesListUpdated ...");

        if (mArdiscoveryService != null) {
            deviceList = mArdiscoveryService.getDeviceServicesArray();

            Bundle messageBundle = new Bundle();
            Message myMessage = handler.obtainMessage();

            // Do what you want with the device list
            String[] listDrone;
            if (deviceList != null) {
                if (deviceList.size() > 0) {
                    listDrone = new String[deviceList.size()];
                    for (int i = 0; i < listDrone.length; i++) {
                        listDrone[i] = deviceList.get(i).getName();
                    }
                } else {
                    listDrone = new String[1];
                    listDrone[0] = MainActivity.MSG_ANY_DRONES;

                }
                messageBundle.putStringArray(MessageHandler.LISTDRONEUPDATE, listDrone);
                myMessage.setData(messageBundle);
                //Envoyer le message
                handler.sendMessage(myMessage);
                //messageBundle.putStringArray(MessageHandler.LISTDRONEUPDATE, listDrone);
                //messageBundle.putParcelableArrayList(MessageHandler.LISTDRONEUPDATE,deviceList.toArray());

            }

        }

    }

    DiscoveryDrone(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        ARSDK.loadSDKLibs();
        initDiscoveryService();
        registerReceivers();
    }


}
