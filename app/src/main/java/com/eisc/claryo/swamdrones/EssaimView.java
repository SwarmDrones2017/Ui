package com.eisc.claryo.swamdrones;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARFrame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe permettant de voir la formation de l'essaim
 */
//TODO Gérer les couleurs et les informations en haut à gauche
public class EssaimView extends AppCompatActivity {
    ArrayList<String> lDroneName;
    ArrayList<ProxyBars> lProxyBars;
    ImageView Drone1, D1ProxRedBot, D1ProxOrBot, D1ProxJauBot, D1ProxRedTop, D1ProxOrTop, D1ProxJauTop, D1ProxRedRight, D1ProxOrRight, D1ProxJauRight, D1ProxRedLeft, D1ProxOrLeft, D1ProxJauLeft,
            D1ProxRedUp, D1ProxOrUp, D1ProxJauUp, D1ProxRedDown, D1ProxOrDown, D1ProxJauDown;
    ImageView Drone2, D2ProxRedBot, D2ProxOrBot, D2ProxJauBot, D2ProxRedTop, D2ProxOrTop, D2ProxJauTop, D2ProxRedRight, D2ProxOrRight, D2ProxJauRight, D2ProxRedLeft, D2ProxOrLeft, D2ProxJauLeft,
            D2ProxRedUp, D2ProxOrUp, D2ProxJauUp, D2ProxRedDown, D2ProxOrDown, D2ProxJauDown;
    ImageView Drone3, D3ProxRedBot, D3ProxOrBot, D3ProxJauBot, D3ProxRedTop, D3ProxOrTop, D3ProxJauTop, D3ProxRedRight, D3ProxOrRight, D3ProxJauRight, D3ProxRedLeft, D3ProxOrLeft, D3ProxJauLeft,
            D3ProxRedUp, D3ProxOrUp, D3ProxJauUp, D3ProxRedDown, D3ProxOrDown, D3ProxJauDown;
    float density;
    String densite;
    ToggleButton TglDrone1, TglDrone2, TglDrone3;
    Button BtnAllDrone;
    LinearLayout LayoutDrone1, LayoutDrone2, LayoutDrone3;
    TextView batteryDrone1txt, batteryDrone2txt, batteryDrone3txt;
    TextView[] TabBatterieDronetxt;
    ImageView[] TabBatterieDrone;
    int batteryPercentage;
    ImageView batteryDrone1, batteryDrone2, batteryDrone3;
    AbsoluteLayout Ecran;
    LinearLayout LayoutDroneInfo;

    private void updateBatterieLevel() {
        Log.i("updateBattery", "UpdateBatteryView");

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            batteryPercentage = GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery();

            if (batteryPercentage > 97)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_full_24dp);
            else if (batteryPercentage > 90)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_90_24dp);
            else if (batteryPercentage > 80)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_80_24dp);
            else if (batteryPercentage > 60)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_60_24dp);
            else if (batteryPercentage > 50)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_50_24dp);
            else if (batteryPercentage > 30)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_30_24dp);
            else if (batteryPercentage > 20)
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_20_24dp);
            else
                TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_alert_24dp);


            TabBatterieDronetxt[i].setText(Integer.toString(batteryPercentage) + " %");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_view);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnBackSwapView);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettingsSwapView);
        TglDrone1 = (ToggleButton) findViewById(R.id.TglDrone1);
        TglDrone2 = (ToggleButton) findViewById(R.id.TglDrone2);
        TglDrone3 = (ToggleButton) findViewById(R.id.TglDrone3);
        BtnAllDrone = (Button) findViewById(R.id.BtnAllDrone);
        Ecran = (AbsoluteLayout) findViewById(R.id.Ecran);
        LayoutDroneInfo = (LinearLayout) findViewById(R.id.LayoutDroneInfo);

        Log.i("ContexteEcran", "" + Ecran.getContext());

//        TabBatterieDrone = new ImageView[3];
//        TabBatterieDrone[0] = batteryDrone1;
//        TabBatterieDrone[1] = batteryDrone2;
//        TabBatterieDrone[2] = batteryDrone3;

//        TabBatterieDronetxt = new TextView[3];
//        TabBatterieDronetxt[0] = batteryDrone1txt;
//        TabBatterieDronetxt[1] = batteryDrone2txt;
//        TabBatterieDronetxt[2] = batteryDrone3txt;

        density = getResources().getDisplayMetrics().density;
        densite = Float.toString(density);

        // return 0.75 if it's LDPI
        // return 1.0 if it's MDPI
        // return 1.5 if it's HDPI
        // return 2.0 if it's XHDPI
        // return 3.0 if it's XXHDPI
        // return 4.0 if it's XXXHDPI

        //On gère l'affichage de la batterie des drones

//        updateBatterieLevel();

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            GlobalCouple.couples.get(i).getBebopDrone().addListener(mBebopListenerBattery);
        }

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimView.this.finish();
                Intent ControlActivity = new Intent(EssaimView.this, Control.class);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(ControlActivity);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimView.this.finish();
                Intent EssaimConfigActivity = new Intent(EssaimView.this, EssaimConfig.class);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(EssaimConfigActivity);
            }
        });

        //On gère les boutons de selection des drones

        BtnAllDrone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TglDrone1.setChecked(true);
                TglDrone2.setChecked(true);
                TglDrone3.setChecked(true);
            }
        });

        lDroneName = new ArrayList<>(GlobalCouple.couples.size());
        lProxyBars = new ArrayList<>(GlobalCouple.couples.size());
        Random r = new Random();

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone().getxEssaimView() < 0 && GlobalCouple.couples.get(i).getBebopDrone().getyEssaimView() < 0) {
                GlobalCouple.couples.get(i).getBebopDrone().setxEssaimView(r.nextInt(633));
                GlobalCouple.couples.get(i).getBebopDrone().setyEssaimView(r.nextInt(360));
            }

            ProxyBars pb = new ProxyBars(getApplicationContext(), Ecran, density, GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
            MyTouchListener1 myTouchListener1 = new MyTouchListener1();
            myTouchListener1.setDroneName((GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName()));
            pb.Drone.setOnTouchListener(myTouchListener1);
            lProxyBars.add(pb);
            lDroneName.add(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
        }

        findViewById(R.id.Ecran).setOnDragListener(new MyDragListener());

    }

    private final BebopDrone.Listener mBebopListenerBattery = new BebopDrone.Listener() {
        @Override
        public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {

        }

        @Override
        public void onBatteryChargeChanged(int batteryPercentage) {
            Log.i("updateBattery", "UpdateBatteryView");

            for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                batteryPercentage = GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery();

                if (batteryPercentage > 97)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_full_24dp);
                else if (batteryPercentage > 90)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_90_24dp);
                else if (batteryPercentage > 80)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_80_24dp);
                else if (batteryPercentage > 60)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_60_24dp);
                else if (batteryPercentage > 50)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_50_24dp);
                else if (batteryPercentage > 30)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_30_24dp);
                else if (batteryPercentage > 20)
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_20_24dp);
                else
                    TabBatterieDrone[i].setImageResource(R.drawable.ic_battery_alert_24dp);


                TabBatterieDronetxt[i].setText(Integer.toString(batteryPercentage) + " %");

            }
        }

        @Override
        public void onPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {

        }

        @Override
        public void onPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {

        }

        @Override
        public void configureDecoder(ARControllerCodec codec) {

        }

        @Override
        public void onFrameReceived(ARFrame frame) {

        }

        @Override
        public void onMatchingMediasFound(int nbMedias) {

        }

        @Override
        public void onDownloadProgressed(String mediaName, int progress) {

        }

        @Override
        public void onDownloadComplete(String mediaName) {

        }
    };

    protected void proxyBarsView() {

        //Drone 1
        int D1ProxGauche = 150;
        int D1ProxDroite = 34;
        int D1ProxDevant = 150;
        int D1ProxDerriere = 150;
        int D1ProxBelow = 150;
        int D1ProxAbove = 150;

        //Drone 2
        int D2ProxGauche = 150;
        int D2ProxDroite = 150;
        int D2ProxDevant = 74;
        int D2ProxDerriere = 150;
        int D2ProxBelow = 150;
        int D2ProxAbove = 150;

        //Drone 2
        int D3ProxGauche = 150;
        int D3ProxDroite = 150;
        int D3ProxDevant = 150;
        int D3ProxDerriere = 150;
        int D3ProxBelow = 150;
        int D3ProxAbove = 86;

        switch (GlobalCouple.couples.size()) {

            case 3:

                Drone3.setVisibility(View.VISIBLE);
                TglDrone3.setVisibility(View.VISIBLE);
                LayoutDrone3.setVisibility(View.VISIBLE);

                if (D3ProxGauche > 100) {
                    D3ProxJauLeft.setVisibility(View.INVISIBLE);
                    D3ProxOrLeft.setVisibility(View.INVISIBLE);
                    D3ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D3ProxGauche <= 100 && D3ProxGauche > 75) {
                    D3ProxJauLeft.setVisibility(View.VISIBLE);
                    D3ProxOrLeft.setVisibility(View.INVISIBLE);
                    D3ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D3ProxGauche <= 75 && D3ProxGauche > 50) {
                    D3ProxJauLeft.setVisibility(View.VISIBLE);
                    D3ProxOrLeft.setVisibility(View.VISIBLE);
                    D3ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D3ProxGauche <= 50) {
                    D3ProxJauLeft.setVisibility(View.VISIBLE);
                    D3ProxOrLeft.setVisibility(View.VISIBLE);
                    D3ProxRedLeft.setVisibility(View.VISIBLE);
                }

                if (D3ProxDroite > 100) {
                    D3ProxJauRight.setVisibility(View.INVISIBLE);
                    D3ProxOrRight.setVisibility(View.INVISIBLE);
                    D3ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D3ProxDroite <= 100 && D3ProxDroite > 75) {
                    D3ProxJauRight.setVisibility(View.VISIBLE);
                    D3ProxOrRight.setVisibility(View.INVISIBLE);
                    D3ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D3ProxDroite <= 75 && D3ProxDroite > 50) {
                    D3ProxJauRight.setVisibility(View.VISIBLE);
                    D3ProxOrRight.setVisibility(View.VISIBLE);
                    D3ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D3ProxDroite <= 50) {
                    D3ProxJauRight.setVisibility(View.VISIBLE);
                    D3ProxOrRight.setVisibility(View.VISIBLE);
                    D3ProxRedRight.setVisibility(View.VISIBLE);
                }

                if (D3ProxDevant > 100) {
                    D3ProxJauTop.setVisibility(View.INVISIBLE);
                    D3ProxOrTop.setVisibility(View.INVISIBLE);
                    D3ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D3ProxDevant <= 100 && D3ProxDevant > 75) {
                    D3ProxJauTop.setVisibility(View.VISIBLE);
                    D3ProxOrTop.setVisibility(View.INVISIBLE);
                    D3ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D3ProxDevant <= 75 && D3ProxDevant > 50) {
                    D3ProxJauTop.setVisibility(View.VISIBLE);
                    D3ProxOrTop.setVisibility(View.VISIBLE);
                    D3ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D3ProxDevant <= 50) {
                    D3ProxJauTop.setVisibility(View.VISIBLE);
                    D3ProxOrTop.setVisibility(View.VISIBLE);
                    D3ProxRedTop.setVisibility(View.VISIBLE);
                }

                if (D3ProxDerriere > 100) {
                    D3ProxJauBot.setVisibility(View.INVISIBLE);
                    D3ProxOrBot.setVisibility(View.INVISIBLE);
                    D3ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D3ProxDerriere <= 100 && D3ProxDerriere > 75) {
                    D3ProxJauBot.setVisibility(View.VISIBLE);
                    D3ProxOrBot.setVisibility(View.INVISIBLE);
                    D3ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D3ProxDerriere <= 75 && D3ProxDerriere > 50) {
                    D3ProxJauBot.setVisibility(View.VISIBLE);
                    D3ProxOrBot.setVisibility(View.VISIBLE);
                    D3ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D3ProxDerriere <= 50) {
                    D3ProxJauBot.setVisibility(View.VISIBLE);
                    D3ProxOrBot.setVisibility(View.VISIBLE);
                    D3ProxRedBot.setVisibility(View.VISIBLE);
                }

                if (D3ProxBelow > 100) {
                    D3ProxJauDown.setVisibility(View.INVISIBLE);
                    D3ProxOrDown.setVisibility(View.INVISIBLE);
                    D3ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D3ProxBelow <= 100 && D3ProxBelow > 75) {
                    D3ProxJauDown.setVisibility(View.VISIBLE);
                    D3ProxOrDown.setVisibility(View.INVISIBLE);
                    D3ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D3ProxBelow <= 75 && D3ProxBelow > 50) {
                    D3ProxJauDown.setVisibility(View.VISIBLE);
                    D3ProxOrDown.setVisibility(View.VISIBLE);
                    D3ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D3ProxBelow <= 50) {
                    D3ProxJauDown.setVisibility(View.VISIBLE);
                    D3ProxOrDown.setVisibility(View.VISIBLE);
                    D3ProxRedDown.setVisibility(View.VISIBLE);
                }

                if (D3ProxAbove > 100) {
                    D3ProxJauUp.setVisibility(View.INVISIBLE);
                    D3ProxOrUp.setVisibility(View.INVISIBLE);
                    D3ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D3ProxAbove <= 100 && D3ProxAbove > 75) {
                    D3ProxJauUp.setVisibility(View.VISIBLE);
                    D3ProxOrUp.setVisibility(View.INVISIBLE);
                    D3ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D3ProxAbove <= 75 && D3ProxAbove > 50) {
                    D3ProxJauUp.setVisibility(View.VISIBLE);
                    D3ProxOrUp.setVisibility(View.VISIBLE);
                    D3ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D3ProxAbove <= 50) {
                    D3ProxJauUp.setVisibility(View.VISIBLE);
                    D3ProxOrUp.setVisibility(View.VISIBLE);
                    D3ProxRedUp.setVisibility(View.VISIBLE);
                }

            case 2:

                Drone2.setVisibility(View.VISIBLE);
                TglDrone1.setVisibility(View.VISIBLE);
                TglDrone2.setVisibility(View.VISIBLE);
                LayoutDrone2.setVisibility(View.VISIBLE);
                BtnAllDrone.setVisibility(View.VISIBLE);

                if (D2ProxGauche > 100) {
                    D2ProxJauLeft.setVisibility(View.INVISIBLE);
                    D2ProxOrLeft.setVisibility(View.INVISIBLE);
                    D2ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D2ProxGauche <= 100 && D2ProxGauche > 75) {
                    D2ProxJauLeft.setVisibility(View.VISIBLE);
                    D2ProxOrLeft.setVisibility(View.INVISIBLE);
                    D2ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D2ProxGauche <= 75 && D2ProxGauche > 50) {
                    D2ProxJauLeft.setVisibility(View.VISIBLE);
                    D2ProxOrLeft.setVisibility(View.VISIBLE);
                    D2ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D2ProxGauche <= 50) {
                    D2ProxJauLeft.setVisibility(View.VISIBLE);
                    D2ProxOrLeft.setVisibility(View.VISIBLE);
                    D2ProxRedLeft.setVisibility(View.VISIBLE);
                }

                if (D2ProxDroite > 100) {
                    D2ProxJauRight.setVisibility(View.INVISIBLE);
                    D2ProxOrRight.setVisibility(View.INVISIBLE);
                    D2ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D2ProxDroite <= 100 && D2ProxDroite > 75) {
                    D2ProxJauRight.setVisibility(View.VISIBLE);
                    D2ProxOrRight.setVisibility(View.INVISIBLE);
                    D2ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D2ProxDroite <= 75 && D2ProxDroite > 50) {
                    D2ProxJauRight.setVisibility(View.VISIBLE);
                    D2ProxOrRight.setVisibility(View.VISIBLE);
                    D2ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D2ProxDroite <= 50) {
                    D2ProxJauRight.setVisibility(View.VISIBLE);
                    D2ProxOrRight.setVisibility(View.VISIBLE);
                    D2ProxRedRight.setVisibility(View.VISIBLE);
                }

                if (D2ProxDevant > 100) {
                    D2ProxJauTop.setVisibility(View.INVISIBLE);
                    D2ProxOrTop.setVisibility(View.INVISIBLE);
                    D2ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D2ProxDevant <= 100 && D2ProxDevant > 75) {
                    D2ProxJauTop.setVisibility(View.VISIBLE);
                    D2ProxOrTop.setVisibility(View.INVISIBLE);
                    D2ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D2ProxDevant <= 75 && D2ProxDevant > 50) {
                    D2ProxJauTop.setVisibility(View.VISIBLE);
                    D2ProxOrTop.setVisibility(View.VISIBLE);
                    D2ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D2ProxDevant <= 50) {
                    D2ProxJauTop.setVisibility(View.VISIBLE);
                    D2ProxOrTop.setVisibility(View.VISIBLE);
                    D2ProxRedTop.setVisibility(View.VISIBLE);
                }

                if (D2ProxDerriere > 100) {
                    D2ProxJauBot.setVisibility(View.INVISIBLE);
                    D2ProxOrBot.setVisibility(View.INVISIBLE);
                    D2ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D2ProxDerriere <= 100 && D2ProxDerriere > 75) {
                    D2ProxJauBot.setVisibility(View.VISIBLE);
                    D2ProxOrBot.setVisibility(View.INVISIBLE);
                    D2ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D2ProxDerriere <= 75 && D2ProxDerriere > 50) {
                    D2ProxJauBot.setVisibility(View.VISIBLE);
                    D2ProxOrBot.setVisibility(View.VISIBLE);
                    D2ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D2ProxDerriere <= 50) {
                    D2ProxJauBot.setVisibility(View.VISIBLE);
                    D2ProxOrBot.setVisibility(View.VISIBLE);
                    D2ProxRedBot.setVisibility(View.VISIBLE);
                }

                if (D2ProxBelow > 100) {
                    D2ProxJauDown.setVisibility(View.INVISIBLE);
                    D2ProxOrDown.setVisibility(View.INVISIBLE);
                    D2ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D2ProxBelow <= 100 && D2ProxBelow > 75) {
                    D2ProxJauDown.setVisibility(View.VISIBLE);
                    D2ProxOrDown.setVisibility(View.INVISIBLE);
                    D2ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D2ProxBelow <= 75 && D2ProxBelow > 50) {
                    D2ProxJauDown.setVisibility(View.VISIBLE);
                    D2ProxOrDown.setVisibility(View.VISIBLE);
                    D2ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D2ProxBelow <= 50) {
                    D2ProxJauDown.setVisibility(View.VISIBLE);
                    D2ProxOrDown.setVisibility(View.VISIBLE);
                    D2ProxRedDown.setVisibility(View.VISIBLE);
                }

                if (D2ProxAbove > 100) {
                    D2ProxJauUp.setVisibility(View.INVISIBLE);
                    D2ProxOrUp.setVisibility(View.INVISIBLE);
                    D2ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D2ProxAbove <= 100 && D2ProxAbove > 75) {
                    D2ProxJauUp.setVisibility(View.VISIBLE);
                    D2ProxOrUp.setVisibility(View.INVISIBLE);
                    D2ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D2ProxAbove <= 75 && D2ProxAbove > 50) {
                    D2ProxJauUp.setVisibility(View.VISIBLE);
                    D2ProxOrUp.setVisibility(View.VISIBLE);
                    D2ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D2ProxAbove <= 50) {
                    D2ProxJauUp.setVisibility(View.VISIBLE);
                    D2ProxOrUp.setVisibility(View.VISIBLE);
                    D2ProxRedUp.setVisibility(View.VISIBLE);
                }

            case 1:

                Drone1.setVisibility(View.VISIBLE);
                LayoutDrone1.setVisibility(View.VISIBLE);

                if (D1ProxGauche > 100) {
                    D1ProxJauLeft.setVisibility(View.INVISIBLE);
                    D1ProxOrLeft.setVisibility(View.INVISIBLE);
                    D1ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D1ProxGauche <= 100 && D1ProxGauche > 75) {
                    D1ProxJauLeft.setVisibility(View.VISIBLE);
                    D1ProxOrLeft.setVisibility(View.INVISIBLE);
                    D1ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D1ProxGauche <= 75 && D1ProxGauche > 50) {
                    D1ProxJauLeft.setVisibility(View.VISIBLE);
                    D1ProxOrLeft.setVisibility(View.VISIBLE);
                    D1ProxRedLeft.setVisibility(View.INVISIBLE);
                } else if (D1ProxGauche <= 50) {
                    D1ProxJauLeft.setVisibility(View.VISIBLE);
                    D1ProxOrLeft.setVisibility(View.VISIBLE);
                    D1ProxRedLeft.setVisibility(View.VISIBLE);
                }

                if (D1ProxDroite > 100) {
                    D1ProxJauRight.setVisibility(View.INVISIBLE);
                    D1ProxOrRight.setVisibility(View.INVISIBLE);
                    D1ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D1ProxDroite <= 100 && D1ProxDroite > 75) {
                    D1ProxJauRight.setVisibility(View.VISIBLE);
                    D1ProxOrRight.setVisibility(View.INVISIBLE);
                    D1ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D1ProxDroite <= 75 && D1ProxDroite > 50) {
                    D1ProxJauRight.setVisibility(View.VISIBLE);
                    D1ProxOrRight.setVisibility(View.VISIBLE);
                    D1ProxRedRight.setVisibility(View.INVISIBLE);
                } else if (D1ProxDroite <= 50) {
                    D1ProxJauRight.setVisibility(View.VISIBLE);
                    D1ProxOrRight.setVisibility(View.VISIBLE);
                    D1ProxRedRight.setVisibility(View.VISIBLE);
                }

                if (D1ProxDevant > 100) {
                    D1ProxJauTop.setVisibility(View.INVISIBLE);
                    D1ProxOrTop.setVisibility(View.INVISIBLE);
                    D1ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D1ProxDevant <= 100 && D1ProxDevant > 75) {
                    D1ProxJauTop.setVisibility(View.VISIBLE);
                    D1ProxOrTop.setVisibility(View.INVISIBLE);
                    D1ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D1ProxDevant <= 75 && D1ProxDevant > 50) {
                    D1ProxJauTop.setVisibility(View.VISIBLE);
                    D1ProxOrTop.setVisibility(View.VISIBLE);
                    D1ProxRedTop.setVisibility(View.INVISIBLE);
                } else if (D1ProxDevant <= 50) {
                    D1ProxJauTop.setVisibility(View.VISIBLE);
                    D1ProxOrTop.setVisibility(View.VISIBLE);
                    D1ProxRedTop.setVisibility(View.VISIBLE);
                }

                if (D1ProxDerriere > 100) {
                    D1ProxJauBot.setVisibility(View.INVISIBLE);
                    D1ProxOrBot.setVisibility(View.INVISIBLE);
                    D1ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D1ProxDerriere <= 100 && D1ProxDerriere > 75) {
                    D1ProxJauBot.setVisibility(View.VISIBLE);
                    D1ProxOrBot.setVisibility(View.INVISIBLE);
                    D1ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D1ProxDerriere <= 75 && D1ProxDerriere > 50) {
                    D1ProxJauBot.setVisibility(View.VISIBLE);
                    D1ProxOrBot.setVisibility(View.VISIBLE);
                    D1ProxRedBot.setVisibility(View.INVISIBLE);
                } else if (D1ProxDerriere <= 50) {
                    D1ProxJauBot.setVisibility(View.VISIBLE);
                    D1ProxOrBot.setVisibility(View.VISIBLE);
                    D1ProxRedBot.setVisibility(View.VISIBLE);
                }

                if (D1ProxBelow > 100) {
                    D1ProxJauDown.setVisibility(View.INVISIBLE);
                    D1ProxOrDown.setVisibility(View.INVISIBLE);
                    D1ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D1ProxBelow <= 100 && D1ProxBelow > 75) {
                    D1ProxJauDown.setVisibility(View.VISIBLE);
                    D1ProxOrDown.setVisibility(View.INVISIBLE);
                    D1ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D1ProxBelow <= 75 && D1ProxBelow > 50) {
                    D1ProxJauDown.setVisibility(View.VISIBLE);
                    D1ProxOrDown.setVisibility(View.VISIBLE);
                    D1ProxRedDown.setVisibility(View.INVISIBLE);
                } else if (D1ProxBelow <= 50) {
                    D1ProxJauDown.setVisibility(View.VISIBLE);
                    D1ProxOrDown.setVisibility(View.VISIBLE);
                    D1ProxRedDown.setVisibility(View.VISIBLE);
                }

                if (D1ProxAbove > 100) {
                    D1ProxJauUp.setVisibility(View.INVISIBLE);
                    D1ProxOrUp.setVisibility(View.INVISIBLE);
                    D1ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D1ProxAbove <= 100 && D1ProxAbove > 75) {
                    D1ProxJauUp.setVisibility(View.VISIBLE);
                    D1ProxOrUp.setVisibility(View.INVISIBLE);
                    D1ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D1ProxAbove <= 75 && D1ProxAbove > 50) {
                    D1ProxJauUp.setVisibility(View.VISIBLE);
                    D1ProxOrUp.setVisibility(View.VISIBLE);
                    D1ProxRedUp.setVisibility(View.INVISIBLE);
                } else if (D1ProxAbove <= 50) {
                    D1ProxJauUp.setVisibility(View.VISIBLE);
                    D1ProxOrUp.setVisibility(View.VISIBLE);
                    D1ProxRedUp.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private final class MyTouchListener1 implements View.OnTouchListener {
        private String droneName;

        public void setDroneName(String droneName) {
            this.droneName = droneName;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ClipData clipData = ClipData.newPlainText(droneName, "");

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;

            } else

            {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_dropout);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    float x = event.getX();
                    float y = event.getY();

                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    AbsoluteLayout container = (AbsoluteLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if (event.getClipData().getDescription().getLabel().equals(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName())) {

                            lProxyBars.get(i).ProxJauDown.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauDown.setY(y + 65 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrDown.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrDown.setY(y + 55 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedDown.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedDown.setY(y + 45 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);

                            lProxyBars.get(i).Drone.setX(x - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).Drone.setY(y - lProxyBars.get(i).Drone.getHeight() / 2);

                            GlobalCouple.couples.get(i).getBebopDrone().setxEssaimView(lProxyBars.get(i).Drone.getX());
                            GlobalCouple.couples.get(i).getBebopDrone().setyEssaimView(lProxyBars.get(i).Drone.getY());

                            lProxyBars.get(i).ProxJauLeft.setX(x - 30 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauLeft.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrLeft.setX(x - 20 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrLeft.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedLeft.setX(x - 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedLeft.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);

                            lProxyBars.get(i).ProxJauRight.setX(x + 121 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauRight.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrRight.setX(x + 111 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrRight.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedRight.setX(x + 101 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedRight.setY(y + 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);

                            lProxyBars.get(i).ProxJauTop.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauTop.setY(y - 30 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrTop.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrTop.setY(y - 20 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedTop.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedTop.setY(y - 10 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);

                            lProxyBars.get(i).ProxJauBot.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauBot.setY(y + 121 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrBot.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrBot.setY(y + 111 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedBot.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedBot.setY(y + 101 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);

                            lProxyBars.get(i).ProxJauUp.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxJauUp.setY(y - 5 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxOrUp.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxOrUp.setY(y + 5 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                            lProxyBars.get(i).ProxRedUp.setX(x + 10 * density / 2 - lProxyBars.get(i).Drone.getWidth() / 2);
                            lProxyBars.get(i).ProxRedUp.setY(y + 15 * density / 2 - lProxyBars.get(i).Drone.getHeight() / 2);
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }

            return true;
        }
    }
}

class ProxyBars extends AppCompatActivity {
    float PlageY, PlageX;
    AbsoluteLayout Ecran;
    Context context;
    float density;
    ImageView Drone;
    ImageView ProxJauLeft, ProxOrLeft, ProxRedLeft, ProxJauRight, ProxOrRight, ProxRedRight, ProxJauTop, ProxOrTop, ProxRedTop, ProxJauBot,
            ProxOrBot, ProxRedBot, ProxJauUp, ProxOrUp, ProxRedUp, ProxJauDown, ProxOrDown, ProxRedDown;

    public ProxyBars(Context context, AbsoluteLayout ecran, float density, String droneName) {
        this.context = context;
        this.density = density;
        Ecran = ecran;

        int indexDrone = GlobalCouple.droneNameCorrespondant(droneName);

        PlageX = GlobalCouple.couples.get(indexDrone).getBebopDrone().getxEssaimView();
        PlageY = GlobalCouple.couples.get(indexDrone).getBebopDrone().getyEssaimView();

        show();

    }

    public void show() {
        Drone = new ImageView(context);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.drone512);
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        ProxJauLeft = new ImageView(context);
        ProxJauLeft.setImageResource(R.drawable.barv);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 30 * density / 2);
        ProxJauLeft.setY(Ydrone + 10 * density / 2);
        ProxOrLeft = new ImageView(context);
        ProxOrLeft.setImageResource(R.drawable.barv);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 20 * density / 2);
        ProxOrLeft.setY(Ydrone + 10 * density / 2);
        ProxRedLeft = new ImageView(context);
        ProxRedLeft.setImageResource(R.drawable.barv);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone - 10 * density / 2);
        ProxRedLeft.setY(Ydrone + 10 * density / 2);

        ProxJauRight = new ImageView(context);
        ProxJauRight.setImageResource(R.drawable.barv);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 121 * density / 2);
        ProxJauRight.setY(Ydrone + 10 * density / 2);
        ProxOrRight = new ImageView(context);
        ProxOrRight.setImageResource(R.drawable.barv);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 111 * density / 2);
        ProxOrRight.setY(Ydrone + 10 * density / 2);
        ProxRedRight = new ImageView(context);
        ProxRedRight.setImageResource(R.drawable.barv);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 101 * density / 2);
        ProxRedRight.setY(Ydrone + 10 * density / 2);

        ProxJauTop = new ImageView(context);
        ProxJauTop.setImageResource(R.drawable.barh);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 10 * density / 2);
        ProxJauTop.setY(Ydrone - 30 * density / 2);
        ProxOrTop = new ImageView(context);
        ProxOrTop.setImageResource(R.drawable.barh);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 10 * density / 2);
        ProxOrTop.setY(Ydrone - 20 * density / 2);
        ProxRedTop = new ImageView(context);
        ProxRedTop.setImageResource(R.drawable.barh);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 10 * density / 2);
        ProxRedTop.setY(Ydrone - 10 * density / 2);

        ProxJauBot = new ImageView(context);
        ProxJauBot.setImageResource(R.drawable.barh);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 10 * density / 2);
        ProxJauBot.setY(Ydrone + 121 * density / 2);
        ProxOrBot = new ImageView(context);
        ProxOrBot.setImageResource(R.drawable.barh);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 10 * density / 2);
        ProxOrBot.setY(Ydrone + 111 * density / 2);
        ProxRedBot = new ImageView(context);
        ProxRedBot.setImageResource(R.drawable.barh);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 10 * density / 2);
        ProxRedBot.setY(Ydrone + 101 * density / 2);

        ProxJauDown = new ImageView(context);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10 * density / 2);
        ProxJauDown.setY(Ydrone + 65 * density / 2);
        ProxOrDown = new ImageView(context);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10 * density / 2);
        ProxOrDown.setY(Ydrone + 55 * density / 2);
        ProxRedDown = new ImageView(context);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10 * density / 2);
        ProxRedDown.setY(Ydrone + 45 * density / 2);

        ProxJauUp = new ImageView(context);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10 * density / 2);
        ProxJauUp.setY(Ydrone - 5 * density / 2);
        ProxOrUp = new ImageView(context);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10 * density / 2);
        ProxOrUp.setY(Ydrone + 5 * density / 2);
        ProxRedUp = new ImageView(context);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10 * density / 2);
        ProxRedUp.setY(Ydrone + 15 * density / 2);

        Ecran.addView(ProxJauDown);
        Ecran.addView(ProxOrDown);
        Ecran.addView(ProxRedDown);
        Ecran.addView(Drone);
        Ecran.addView(ProxJauBot);
        Ecran.addView(ProxOrBot);
        Ecran.addView(ProxRedBot);
        Ecran.addView(ProxJauTop);
        Ecran.addView(ProxOrTop);
        Ecran.addView(ProxRedTop);
        Ecran.addView(ProxJauRight);
        Ecran.addView(ProxOrRight);
        Ecran.addView(ProxRedRight);
        Ecran.addView(ProxJauLeft);
        Ecran.addView(ProxOrLeft);
        Ecran.addView(ProxRedLeft);
        Ecran.addView(ProxJauUp);
        Ecran.addView(ProxOrUp);
        Ecran.addView(ProxRedUp);

        Log.i("ContexteD", "" + Drone.getContext());
        Log.i("ContexteE", "" + Ecran.getContext());
    }
}

class EssaimViewInfoDrone extends AppCompatActivity{

    Context context;
    LinearLayout LayoutDroneInfo, LayoutDroneVertical;
    TextView NomDrone, MaxSpeed, Altitude, Battery;
    ImageView MaxSpeedImg, AltitudeImg, BatteryImg;
    int indexDrone;

    public EssaimViewInfoDrone(Context context, LinearLayout layoutdroneinfo, String droneName){
        this.context = context;
        LayoutDroneInfo = layoutdroneinfo;

        indexDrone = GlobalCouple.droneNameCorrespondant(droneName);
    }

    public void displayInfos(){

        LayoutDroneVertical = new LinearLayout(context);
        LayoutDroneVertical.setOrientation(LinearLayout.VERTICAL);

        NomDrone = new TextView(context);
        NomDrone.setText(GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getDroneName());

        MaxSpeedImg = new ImageView(context);
        MaxSpeedImg.setImageResource(R.drawable.ic_speed);

        MaxSpeed = new TextView(context);
        //MaxSpeed.setText(GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone());

        AltitudeImg = new ImageView(context);
        AltitudeImg.setImageResource(R.drawable.ic_height);

        Altitude = new TextView(context);

        BatteryImg = new ImageView(context);

        Battery = new TextView(context);

    }

}
