package com.eisc.claryo.swamdrones;

/**
 * Created by sofiane on 11/02/17.
 * La classe BebopDrone contient toutes les informations d'un drone Bebop.
 * Attributs :
 *      - boolean flyAuthorization : active/désactive le contrôle du drone
 *      - InfoDrone infoDrone : contient les informations du drone
 *      - boolean isMaster : choix du drone maître
 *      - Handler handlerBattery : handler permettant de mettre à jour en temps réel l'état de la batterie
 *      - ARDeviceController mDeviceController : deviceController du drone (nécessaire pour le pilotage)
 *      - ARCONTROLLER_DEVICE_STATE_ENUM mState : état drone (en vol, arreté ...)
 *      - ARDeviceControllerListener mDeviceControllerListener : listener d'état du drone
 *      - ARDeviceControllerStreamListener mStreamListener : listener pour le stream vidéo du drone
 *      - InetAddress IP : l'adresse IP du drone
 *      - ARDiscoveryDeviceService deviceService : deviceService du drone
 *
 */


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DICTIONARY_KEY_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerArgumentDictionary;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARControllerDictionary;
import com.parrot.arsdk.arcontroller.ARControllerException;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import com.parrot.arsdk.arcontroller.ARDeviceControllerListener;
import com.parrot.arsdk.arcontroller.ARDeviceControllerStreamListener;
import com.parrot.arsdk.arcontroller.ARFeatureARDrone3;
import com.parrot.arsdk.arcontroller.ARFeatureCommon;
import com.parrot.arsdk.arcontroller.ARFrame;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_FAMILY_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDevice;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryException;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import com.parrot.arsdk.arutils.ARUtilsException;
import com.parrot.arsdk.arutils.ARUtilsManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.parrot.arsdk.arcontroller.ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY;

public class BebopDrone {
    private static final String TAG = "BebopDrone";
    private static final int DEVICE_PORT = 21;
    private boolean flyAuthorization = true;
    private final InfoDrone infoDrone = new InfoDrone();
    private boolean isMaster = false;
    private BebopVideoView bebopVideoView;

    public BebopVideoView getBebopVideoView() {
        return bebopVideoView;
    }

    public void setBebopVideoView(BebopVideoView bebopVideoView) {
        this.bebopVideoView = bebopVideoView;
    }
    private float xEssaimView = -1, yEssaimView = -1;

    public float getxEssaimView() {
        return xEssaimView;
    }

    public void setxEssaimView(float xEssaimView) {
        this.xEssaimView = xEssaimView;
    }

    public float getyEssaimView() {
        return yEssaimView;
    }

    public void setyEssaimView(float yEssaimView) {
        this.yEssaimView = yEssaimView;
    }

    public interface Listener {
        /**
         * Called when the connection to the drone changes
         * Called in the main thread
         *
         * @param state the state of the drone
         */
        void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state);

        /**
         * Called when the battery charge changes
         * Called in the main thread
         *
         * @param batteryPercentage the battery remaining (in percent)
         */
        void onBatteryChargeChanged(int batteryPercentage);

        /**
         * Called when the piloting state changes
         * Called in the main thread
         *
         * @param state the piloting state of the drone
         */
        void onPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state);

        /**
         * Called when a picture is taken
         * Called on a separate thread
         *
         * @param error ERROR_OK if picture has been taken, otherwise describe the error
         */
        void onPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error);

        /**
         * Called when the video decoder should be configured
         * Called on a separate thread
         *
         * @param codec the codec to configure the decoder with
         */
        void configureDecoder(ARControllerCodec codec);

        /**
         * Called when a video frame has been received
         * Called on a separate thread
         *
         * @param frame the video frame
         */
        void onFrameReceived(ARFrame frame);

        /**
         * Called before medias will be downloaded
         * Called in the main thread
         *
         * @param nbMedias the number of medias that will be downloaded
         */
        void onMatchingMediasFound(int nbMedias);

        /**
         * Called each time the progress of a download changes
         * Called in the main thread
         *
         * @param mediaName the name of the media
         * @param progress  the progress of its download (from 0 to 100)
         */
        void onDownloadProgressed(String mediaName, int progress);

        /**
         * Called when a media download has ended
         * Called in the main thread
         *
         * @param mediaName the name of the media
         */
        void onDownloadComplete(String mediaName);
    }

    private final List<Listener> mListeners;

    private final Handler mHandler;
    private Handler handlerBattery;

    private ARDeviceController mDeviceController;
    private ARCONTROLLER_DEVICE_STATE_ENUM mState;
    private ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM mFlyingState;
    private String mCurrentRunId;
    private final ARDeviceControllerListener mDeviceControllerListener = new ARDeviceControllerListener() {
        @Override
        public void onStateChanged(ARDeviceController deviceController, ARCONTROLLER_DEVICE_STATE_ENUM newState, ARCONTROLLER_ERROR_ENUM error) {
            mState = newState;
            switch (newState) {
                case ARCONTROLLER_DEVICE_STATE_RUNNING:
                    mDeviceController.getFeatureARDrone3().sendMediaStreamingVideoEnable((byte) 1);
                    break;
                case ARCONTROLLER_DEVICE_STATE_STOPPED:
                    break;
                case ARCONTROLLER_DEVICE_STATE_STARTING:
                    break;
                case ARCONTROLLER_DEVICE_STATE_STOPPING:
                    break;

                default:
                    break;
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    notifyConnectionChanged(mState);
                }
            });
        }

        @Override
        public void onExtensionStateChanged(ARDeviceController deviceController, ARCONTROLLER_DEVICE_STATE_ENUM newState, ARDISCOVERY_PRODUCT_ENUM product, String name, ARCONTROLLER_ERROR_ENUM error) {
        }


        @Override
        public void onCommandReceived(ARDeviceController deviceController, ARCONTROLLER_DICTIONARY_KEY_ENUM commandKey, ARControllerDictionary elementDictionary) {
            // if event received is the battery update
            if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_BATTERYSTATECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    final int battery = (Integer) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_BATTERYSTATECHANGED_PERCENT);
                    infoDrone.battery = battery;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyBatteryChanged(battery);
                        }
                    });

                    if (handlerBattery != null) {
                        Bundle messageBundle = new Bundle();
                        Message msg = handlerBattery.obtainMessage();
                        messageBundle.putInt(MessageKEY.BATTERYLEVEL, battery);
                        msg.setData(messageBundle);
                        handlerBattery.sendMessage(msg);
                    }
                }
            }
            // if event received is the flying state update
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    final ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state = ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE));

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mFlyingState = state;
                            notifyPilotingStateChanged(state);
                        }
                    });
                }
            }
            // if event received is the picture notification
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    final ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error = ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR));
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyPictureTaken(error);
                        }
                    });
                }
            }
            // if event received is the run id
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_RUNSTATE_RUNIDCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    final String runID = (String) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_RUNSTATE_RUNIDCHANGED_RUNID);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCurrentRunId = runID;
                        }
                    });
                }
            }
            //SerialID high
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALHIGHCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    String high = (String) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALHIGHCHANGED_HIGH);
                    infoDrone.serialIDHigh = high;
                }
            }
            //SerialID low
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALLOWCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    String low = (String) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTSERIALLOWCHANGED_LOW);
                    infoDrone.serialIDLow = low;
                }
            }
            //Drone Version
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTVERSIONCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.softwareVersion = (String) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTVERSIONCHANGED_SOFTWARE);
                    infoDrone.hardwareVersion = (String) args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_SETTINGSSTATE_PRODUCTVERSIONCHANGED_HARDWARE);
                }
            }
            //GPS Version
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.softwareGPSVersion = (String) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_PRODUCTGPSVERSIONCHANGED_SOFTWARE);

                }
            }
            //motor flight status
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.nbFlights = (short) ((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_NBFLIGHTS)).intValue();
                    infoDrone.durationLastFlight = (short) ((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_LASTFLIGHTDURATION)).intValue();
                    infoDrone.durationTotalFlights = (int) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SETTINGSSTATE_MOTORFLIGHTSSTATUSCHANGED_TOTALFLIGHTDURATION);
                }
            }
            //Altitude
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.altitude_max = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXALTITUDECHANGED_CURRENT)).doubleValue();
                }
            }
            //Distance
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXDISTANCECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.distance_max = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXDISTANCECHANGED_CURRENT)).doubleValue();
                }
            }
            //Video resoliution
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.video_resolution = ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE));
                }
            }
            //frame
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.framerate = ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE));
                }
            }
            //Stabilisation camera
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.videostabilization = ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE));
                }
            }
            //Anti-scintillement
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.antiflickering = ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM.getFromValue((Integer) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE));
                }
            }
            //Tilt inclinaison
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXTILTCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.tiltmax = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSETTINGSSTATE_MAXTILTCHANGED_CURRENT)).doubleValue();
                }
            }
            //speed tilt
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXPITCHROLLROTATIONSPEEDCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    float speedtilt = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXPITCHROLLROTATIONSPEEDCHANGED_CURRENT)).doubleValue();
                }
            }
            //Max vertical speed
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.speedverticale = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXVERTICALSPEEDCHANGED_CURRENT)).doubleValue();
                }
            }
            //Max rotation speed
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED) && (elementDictionary != null)) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.speedrotation = (float) ((Double) args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_SPEEDSETTINGSSTATE_MAXROTATIONSPEEDCHANGED_CURRENT)).doubleValue();
                }
            }
            //Deconnexion bebop
            else if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_NETWORKEVENT_DISCONNECTION) && (elementDictionary != null)){
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    ARCOMMANDS_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE_ENUM cause = ARCOMMANDS_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE_ENUM.getFromValue((Integer)args.get(ARFeatureCommon.ARCONTROLLER_DICTIONARY_KEY_COMMON_NETWORKEVENT_DISCONNECTION_CAUSE));
                    int indexDrone = GlobalCouple.indexDroneCorrespondant(deviceController);
                    if(indexDrone != -1){
                        GlobalCouple.couples.get(indexDrone).setBebopDrone(null);
                    }
                }
            }
            //Current altitude
            if ((commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ALTITUDECHANGED) && (elementDictionary != null)){
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    infoDrone.altitude = (double)args.get(ARFeatureARDrone3.ARCONTROLLER_DICTIONARY_KEY_ARDRONE3_PILOTINGSTATE_ALTITUDECHANGED_ALTITUDE);
                }
            }
        }
    };

    private final ARDeviceControllerStreamListener mStreamListener = new ARDeviceControllerStreamListener() {
        @Override
        public ARCONTROLLER_ERROR_ENUM configureDecoder(ARDeviceController deviceController, final ARControllerCodec codec) {
            notifyConfigureDecoder(codec);
            return ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        }

        @Override
        public ARCONTROLLER_ERROR_ENUM onFrameReceived(ARDeviceController deviceController, final ARFrame frame) {
            notifyFrameReceived(frame);
            return ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        }

        @Override
        public void onFrameTimeout(ARDeviceController deviceController) {
        }
    };

    private InetAddress IP;
    private final ARDiscoveryDeviceService deviceService;

    /**
     * Constructeur
     * Dans le constructeur on enregistre le deviceService et on crée et enregistre le deviceController.
     * Le constructeur enregistre également le nom du drone et son adresse IP dans la sous-classe InfoDrone, puis démarre le protocole FTP.
     * @param context Contexte de l'application
     * @param deviceService deviceService du drone découvert, c'est la fonction Discovery du SDK
     *                      qui construit automatiquement le deviceService. Il est nécessaire pour avoir des informations du drone.
     */
    public BebopDrone(Context context, @NonNull ARDiscoveryDeviceService deviceService) {
        this.deviceService = deviceService;
        mListeners = new ArrayList<>();

        // needed because some callbacks will be called on the main thread
        mHandler = new Handler(context.getMainLooper());

        mState = ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_STOPPED;

        // if the product type of the deviceService match with the types supported
        ARDISCOVERY_PRODUCT_ENUM productType = ARDiscoveryService.getProductFromProductID(deviceService.getProductID());
        ARDISCOVERY_PRODUCT_FAMILY_ENUM family = ARDiscoveryService.getProductFamily(productType);
        if (ARDISCOVERY_PRODUCT_FAMILY_ENUM.ARDISCOVERY_PRODUCT_FAMILY_ARDRONE.equals(family)) {

            ARDiscoveryDevice discoveryDevice = createDiscoveryDevice(deviceService, productType);
            if (discoveryDevice != null) {
                mDeviceController = createDeviceController(discoveryDevice);
                discoveryDevice.dispose();
            }

            try {
                String strIP = ((ARDiscoveryDeviceNetService) (deviceService.getDevice())).getIp();
                IP = InetAddress.getByName(strIP);
                infoDrone.droneName = this.deviceService.getName();
                ARUtilsManager ftpListManager = new ARUtilsManager();
                ARUtilsManager ftpQueueManager = new ARUtilsManager();

                ftpListManager.initWifiFtp(strIP, DEVICE_PORT, ARUtilsManager.FTP_ANONYMOUS, "");
                ftpQueueManager.initWifiFtp(strIP, DEVICE_PORT, ARUtilsManager.FTP_ANONYMOUS, "");


            } catch (ARUtilsException e) {
                Log.e(TAG, "Exception", e);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        } else {
            Log.e(TAG, "DeviceService type is not supported by BebopDrone");
        }
    }

    /**
     * Getter isMaster
     * @return retourne si le drone est maître ou non
     */
    public boolean isMaster() {
        return isMaster;
    }

    /**
     * Setter isMaster
     * @param master boolean, si true le drone est maître, si false le drone n'est pas maitre de l'essaim
     */
    public void setMaster(boolean master) {
        isMaster = master;
    }

    /**
     * Getter flyAuthorization
     * @return true si le drone est autorisé à volé, false sinon
     */
    public boolean isFlyAuthorization() {
        return flyAuthorization;
    }

    /**
     * Setter flyAuthorization
     * @param flyAuthorization boolean true si le drone est autorisé à voler, false sinon
     */
    public void setFlyAuthorization(boolean flyAuthorization) {
        this.flyAuthorization = flyAuthorization;
    }

    /**
     * Getter mDeviceController
     * @return le device controller du drone
     */
    public ARDeviceController getmDeviceController() {
        return mDeviceController;
    }

    /**
     * Getter HandlerBattery
     * @return le handler s'il est non nul, null sinon
     */
    public Handler getHandlerBattery() {
        return handlerBattery;
    }

    /**
     * Setter handlerBattery
     * @param handler un handler, à utiliser pour un affichage dynamique de la batterie sur l'activité souhaité
     */
    public void setHandlerBattery(Handler handler) {
        this.handlerBattery = handler;
    }

    /**
     * Getter IP
     * @return l'adresse IP du drone
     */
    public InetAddress getIP() {
        return IP;
    }

    /**
     * Getter deviceService
     * @return le device Service du drone
     */
    public ARDiscoveryDeviceService getdeviceService() {
        return deviceService;
    }

//    public void dispose() {
//        if (mDeviceController != null)
//            mDeviceController.dispose();
//    }

    /** region Listener functions **/
    /**
     * AddListener permet d'ajouter des listeners configurés au drone
     * @param listener un listener configuré
     */
    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    /**
     * removeListener supprime un listener configuré de la liste des listeners
     * @param listener le listener à supprimer
     */
    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }
    //endregion Listener

    /**
     * Connect to the drone
     *
     * @return true if operation was successful.
     * Returning true doesn't mean that device is connected.
     * You can be informed of the actual connection through {@link Listener#onDroneConnectionChanged}
     */
    public boolean connect() {
        boolean success = false;
        if ((mDeviceController != null) && (ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_STOPPED.equals(mState))) {
            ARCONTROLLER_ERROR_ENUM error = mDeviceController.start();
            if (error == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                success = true;
            }
        }
        return success;
    }

    /**
     * Disconnect from the drone
     *
     * @return true if operation was successful.
     * Returning true doesn't mean that device is disconnected.
     * You can be informed of the actual disconnection through {@link Listener#onDroneConnectionChanged}
     */
    public boolean disconnect() {
        boolean success = false;
        if ((mDeviceController != null) && (ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING.equals(mState))) {
            ARCONTROLLER_ERROR_ENUM error = mDeviceController.stop();
            if (error == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                success = true;
            }
        }
        return success;
    }

    /**
     * Get the current connection state
     *
     * @return the connection state of the drone
     */
    public ARCONTROLLER_DEVICE_STATE_ENUM getConnectionState() {
        return mState;
    }

    /**
     * Get the current flying state
     *
     * @return the flying state
     */
    public ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM getFlyingState() {
        return mFlyingState;
    }

    /**
     * Decollage
     */
    public void takeOff() {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().sendPilotingTakeOff();
        }
    }

    /**
     * Atterrissage
     */
    public void land() {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().sendPilotingLanding();
        }
    }

    /**
     * Atterrissage d'urgence
     */
    public void emergency() {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().sendPilotingEmergency();
        }
    }

    /**
     * Prendre une photo
     */
    public void takePicture() {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().sendMediaRecordPictureV2();
        }
    }

    /**
     * Vol Stationnaire
     */
    public void stationnaire() {
        setPitch((byte) 0);
        setRoll((byte) 0);
        setYaw((byte) 0);
        setGaz((byte) 0);
        setFlag((byte) 0);
    }

    /**
     * Avancer
     */
    public void startMoveForward() {
        if (isFlyAuthorization()) {
            setPitch((byte) 50);
            setFlag((byte) 1);
        }
    }

    /**
     * Arreter d'avancer
     */
    public void stopMoveForward() {
        if (isFlyAuthorization()) {
            setPitch((byte) 0);
            setFlag((byte) 0);
        }
    }

    /**
     * Reculer
     */
    public void startMoveBack() {
        if (isFlyAuthorization()) {
            setPitch((byte) -50);
            setFlag((byte) 1);
        }
    }

    /**
     * Arreter de reculer
     */
    public void stopMoveBack() {
        if (isFlyAuthorization()) {
            setPitch((byte) 0);
            setFlag((byte) 0);
        }
    }

    /**
     * Déplacement à gauche
     */
    public void startMoveLeft() {
        if (isFlyAuthorization()) {
            setRoll((byte) -50);
            setFlag((byte) 1);
        }
    }

    /**
     * Arreter le déplacement à gauche
     */
    public void stopMoveLeft() {
        if (isFlyAuthorization()) {
            setRoll((byte) 0);
            setFlag((byte) 0);
        }
    }

    /**
     * Déplacement à droite
     */
    public void startMoveRight() {
        if (isFlyAuthorization()) {
            setRoll((byte) 50);
            setFlag((byte) 1);
        }
    }

    /**
     * Arreter le déplacement à droite
     */
    public void stopMoveRight() {
        if (isFlyAuthorization()) {
            setRoll((byte) 0);
            setFlag((byte) 0);
        }
    }

    /**
     * Tourner dans le sens anti-horaire
     */
    public void startTurnLeft() {
        if (isFlyAuthorization()) {

            setYaw((byte) -50);
        }
    }

    /**
     * Arreter de tourner
     */
    public void stopTurnLeft() {
        if (isFlyAuthorization()) {

            setYaw((byte) 0);
        }
    }

    /**
     * Tourner dans le sens horaire
     */
    public void startTurnRight() {
        if (isFlyAuthorization()) {

            setYaw((byte) 50);
        }
    }

    /**
     * Arreter de tourner
     */
    public void stopTurnRight() {
        if (isFlyAuthorization()) {

            setYaw((byte) 0);
        }
    }

    /**
     * Monter
     */
    public void startMoveUp(){
        if (isFlyAuthorization()){
            setGaz((byte) 50);
        }
    }
    public void stopMoveUp(){
        if (isFlyAuthorization()){
            setGaz((byte) 0);
        }
    }

    /**
     * Arreter de monter
     */
    public void startMoveDown(){
        if (isFlyAuthorization()){
            setGaz((byte) -50);
        }
    }

    /**
     * Arreter de monter
     */
    public void stopMoveDown(){
        if (isFlyAuthorization()){
            setGaz((byte) 0);
        }
    }

    /**
     * Set the forward/backward angle of the drone
     * Note that {@link BebopDrone#setFlag(byte)} should be set to 1 in order to take in account the pitch value
     *
     * @param pitch value in percentage from -100 to 100
     */
    private void setPitch(byte pitch) {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().setPilotingPCMDPitch(pitch);
        }
    }

    /**
     * Set the side angle of the drone
     * Note that {@link BebopDrone#setFlag(byte)} should be set to 1 in order to take in account the roll value
     *
     * @param roll value in percentage from -100 to 100
     */
    private void setRoll(byte roll) {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().setPilotingPCMDRoll(roll);
        }
    }

    private void setYaw(byte yaw) {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().setPilotingPCMDYaw(yaw);
        }
    }

    private void setGaz(byte gaz) {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().setPilotingPCMDGaz(gaz);
        }
    }

    /**
     * Take in account or not the pitch and roll values
     *
     * @param flag 1 if the pitch and roll values should be used, 0 otherwise
     */
    private void setFlag(byte flag) {
        if ((mDeviceController != null) && (mState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING))) {
            mDeviceController.getFeatureARDrone3().setPilotingPCMDFlag(flag);
        }
    }

    /**
     * Methode du SDK pour commencer la découverte des drones connectés au point d'accès
     * @param service
     * @param productType
     * @return
     */
    private ARDiscoveryDevice createDiscoveryDevice(@NonNull ARDiscoveryDeviceService service, ARDISCOVERY_PRODUCT_ENUM productType) {
        ARDiscoveryDevice device = null;
        try {
            device = new ARDiscoveryDevice();

            ARDiscoveryDeviceNetService netDeviceService = (ARDiscoveryDeviceNetService) service.getDevice();
            device.initWifi(productType, netDeviceService.getName(), netDeviceService.getIp(), netDeviceService.getPort());

        } catch (ARDiscoveryException e) {
            Log.e(TAG, "Exception", e);
            Log.e(TAG, "Error: " + e.getError());
        }

        return device;
    }

//    public ARDeviceControllerStreamListener getmStreamListener() {
//        return mStreamListener;
//    }

    /**
     * Méthode du SDK permettant la création du device Controller
     * @param discoveryDevice
     * @return
     */
    private ARDeviceController createDeviceController(@NonNull ARDiscoveryDevice discoveryDevice) {
        ARDeviceController deviceController = null;
        try {
            deviceController = new ARDeviceController(discoveryDevice);

            deviceController.addListener(mDeviceControllerListener);
            deviceController.addStreamListener(mStreamListener);
        } catch (ARControllerException e) {
            Log.e(TAG, "Exception", e);
        }

        return deviceController;
    }

    //region notify listener block
    private void notifyConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onDroneConnectionChanged(state);
        }
    }

    private void notifyBatteryChanged(int battery) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onBatteryChargeChanged(battery);
        }

    }

    private void notifyPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onPilotingStateChanged(state);
        }
    }

    private void notifyPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onPictureTaken(error);
        }
    }

    private void notifyConfigureDecoder(ARControllerCodec codec) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.configureDecoder(codec);
        }
    }

    private void notifyFrameReceived(ARFrame frame) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onFrameReceived(frame);
        }
    }

    private void notifyMatchingMediasFound(int nbMedias) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onMatchingMediasFound(nbMedias);
        }
    }

    private void notifyDownloadProgressed(String mediaName, int progress) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onDownloadProgressed(mediaName, progress);
        }
    }

    private void notifyDownloadComplete(String mediaName) {
        List<Listener> listenersCpy = new ArrayList<>(mListeners);
        for (Listener listener : listenersCpy) {
            listener.onDownloadComplete(mediaName);
        }
    }

    /**
     * Getter de la classe InfoDrone
     * @return la classe InfoDrone de l'objet BebopDrone instancié
     */
    public InfoDrone getInfoDrone() {
        return infoDrone;
    }

    /**
     * La classe InfoDrone permet d'enregistrer les informations utiles à l'application concernant
     * l'objet BebopDrone créé, et donc le drone découvert.
     * Elle contient les attributs serialID, battery, la version hardware et software du drone, ainsi que
     * des informations/paramètres de vol
     */
    public class InfoDrone {
        protected String serialID;
        protected String serialIDLow;
        protected String serialIDHigh;
        protected int battery;
        protected String hardwareVersion;
        protected String softwareVersion;
        protected String softwareGPSVersion;
        protected short nbFlights;
        protected short durationLastFlight;
        protected int durationTotalFlights;
        protected String droneName;
        protected float distance_max;
        private float altitude_max;
        private ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_ENUM video_resolution;
        private ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_ENUM framerate;
        private ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM antiflickering;
        private float tiltmax;
        private float speedtilt;
        private float speedverticale;
        private float speedrotation;
        private double altitude;
        private ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ENUM videostabilization;

        /**
         * Getter de la stabilisation video
         * @return l'état de la stabilisation de la camera
         */
        public ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOSTABILIZATIONMODECHANGED_MODE_ENUM getVideostabilization() {
            return videostabilization;
        }

        /**
         * Setter de la stabilisation vidéo
         * @param videostabilization mode du SDK pour activer/desactiver la stabilisation de la camera
         * @return true si l'opération s'est bien déroulée, false sinon
         */
        public boolean setVideostabilization(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOSTABILIZATIONMODE_MODE_ENUM videostabilization) {
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendPictureSettingsVideoStabilizationMode(videostabilization);

            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Getter du framerate
         * @return le framerate de la camera (24, 25 ou 30)
         */
        public ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEOFRAMERATECHANGED_FRAMERATE_ENUM getFramerate() {
            return framerate;
        }

        /**
         * Getter drone Name
         * @return le nom du drone
         */
        public String getDroneName() {
            return droneName;
        }

        /**
         * Getter NbFlights
         * @return le nombre de vol enregistrés
         */
        public short getNbFlights() {
            return nbFlights;
        }

        /**
         * Getter durationLastFlight
         * @return la durée du dernier vol enregistré
         */
        public short getDurationLastFlight() {
            return durationLastFlight;
        }

        /**
         * Getter durationTotalFlights
         * @return la durée de vol total
         */
        public int getDurationTotalFlights() {
            return durationTotalFlights;
        }

        /**
         * Getter de la version du GPS
         * @return la version du GPS
         */
        public String getSoftwareGPSVersion() {
            return softwareGPSVersion;
        }

        /**
         * Getter de la version du matériel
         * @return la version du matériel
         */
        public String getHardwareVersion() {
            return hardwareVersion;
        }

        /**
         * Getter de la version du logiciel
         * @return la version du logiciel
         */
        public String getSoftwareVersion() {
            return softwareVersion;
        }

        /**
         * Getter du niveau de batterie
         * @return int : le niveau de batterie (0-100)
         */
        public int getBattery() {
            return battery;
        }

        /**
         * Getter de l'altitude
         * @return double : l'altitude
         */
        public double getAltitude() {
            return altitude;
        }

        /**
         * Getter du numéro de série
         * @return numéro de série
         */
        public String getSerialID() {
            serialID = serialIDHigh + serialIDLow;

            return serialID;
        }

        /**
         * Getter de l'altitude maximum paramétrée
         * @return l'altitude maximum paramétrée
         */
        public float getAltitude_max() {
            return altitude_max;
        }

        /**
         * Setter de l'altitude maximum
         * @param altitude_max float qui indique l'altitude maximum
         */
        public void setAltitude_max(float altitude_max) {
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendPilotingSettingsMaxAltitude(altitude_max);

            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                this.altitude_max = altitude_max;
            }
        }

        /**
         * Getter de distance_max
         * @return la distance max paramétrée
         */
        public float getDistance_max() {
            return distance_max;
        }

        /**
         * Setter de distance_max
         * @param distance_max float pour parametrer la distance max
         */
        public void setDistance_max(float distance_max) {
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendPilotingSettingsMaxDistance(distance_max);

            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                this.distance_max = distance_max;
            }
        }

        /**
         * Getter de la résolution vidéo
         * @return 720 ou 1080
         */
        public ARCOMMANDS_ARDRONE3_PICTURESETTINGSSTATE_VIDEORESOLUTIONSCHANGED_TYPE_ENUM getVideo_resolution() {
            return video_resolution;
        }

        /**
         * Setter de la resolution vidéo
         * @param video_resolution ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM
         * @return true si l'opération s'est bien déroulée, false sinon
         */
        public boolean setVideo_resolution(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEORESOLUTIONS_TYPE_ENUM video_resolution) {
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendPictureSettingsVideoResolutions(video_resolution);
            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                return true;
            } else {
                return false;
            }

        }

        /**
         * Setter du framerate
         * @param framerate ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM
         * @return true si l'opération s'est bien déroulée, false sinon
         */
        public boolean setFramerate(ARCOMMANDS_ARDRONE3_PICTURESETTINGS_VIDEOFRAMERATE_FRAMERATE_ENUM framerate) {
            //this.framerate = framerate;
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendPictureSettingsVideoFramerate(framerate);

            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                return true;
            } else {
                return false;
            }


        }

        /**
         * Getter de l'anti flickering
         * @return ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM
         */
        public ARCOMMANDS_ARDRONE3_ANTIFLICKERINGSTATE_MODECHANGED_MODE_ENUM getAntiflickering() {
            return antiflickering;
        }

        /**
         * Setter de l'anti flickering
         * @param antiflickering ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM
         * @return true si l'opération s'est bien déroulée, false sinon
         */
        public boolean setAntiflickering(ARCOMMANDS_ARDRONE3_ANTIFLICKERING_SETMODE_MODE_ENUM antiflickering) {
            ARCONTROLLER_ERROR_ENUM test = mDeviceController.getFeatureARDrone3().sendAntiflickeringSetMode(antiflickering);
            if (test == ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Getter tiltMax
         * @return float
         */
        public float getTiltmax() {
            return tiltmax;
        }

        /**
         * Setter tiltMax
         * @param tiltmax float
         */
        public void setTiltmax(float tiltmax) {
            mDeviceController.getFeatureARDrone3().sendPilotingSettingsMaxTilt(tiltmax);
        }

        /**
         * Getter speedTilt
         * @return float
         */
        public float getSpeedtilt() {
            return speedtilt;
        }

        /**
         * Setter SpeedTilt
         * @param speedtilt float
         */
        public void setSpeedtilt(float speedtilt) {
            mDeviceController.getFeatureARDrone3().sendSpeedSettingsMaxPitchRollRotationSpeed(speedtilt);
        }

        /**
         * Getter vitesse verticale
         * @return float vitesse verticale
         */
        public float getSpeedverticale() {
            return speedverticale;
        }

        /**
         * Setter vitesse verticale
         * @param speedverticale float
         */
        public void setSpeedverticale(float speedverticale) {
            mDeviceController.getFeatureARDrone3().sendSpeedSettingsMaxVerticalSpeed(speedverticale);
        }

        /**
         * Getter vitesse de rotation
         * @return float
         */
        public float getSpeedrotation() {
            return speedrotation;
        }

        /**
         * Setter vitesse de rotation
         * @param speedrotation float
         */
        public void setSpeedrotation(float speedrotation) {
            mDeviceController.getFeatureARDrone3().sendSpeedSettingsMaxRotationSpeed(speedrotation);
        }
    }
}
