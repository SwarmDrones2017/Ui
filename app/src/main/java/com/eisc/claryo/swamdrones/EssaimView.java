package com.eisc.claryo.swamdrones;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

/**
 * Classe permettant de voir la formation de l'essaim
 */

public class EssaimView extends AppCompatActivity {

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
    TextView NomDrone1, NomDrone2, NomDrone3, batteryDrone1txt, batteryDrone2txt, batteryDrone3txt;
    TextView []TabNomDrone;
    TextView []TabBatterieDronetxt;
    ImageView []TabBatterieDrone;
    int batteryPercentage;
    ImageView batteryDrone1, batteryDrone2, batteryDrone3;
    AbsoluteLayout Ecran;

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


            TabBatterieDronetxt[i].setText(Integer.toString(batteryPercentage)+" %");

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
        LayoutDrone1 = (LinearLayout) findViewById(R.id.LayoutDrone1);
        LayoutDrone2 = (LinearLayout) findViewById(R.id.LayoutDrone2);
        LayoutDrone3 = (LinearLayout) findViewById(R.id.LayoutDrone3);
        Ecran = (AbsoluteLayout) findViewById(R.id.Ecran);

        for(int i=0; i<GlobalCouple.couples.size(); i++)
            GlobalCouple.couples.get(i).getBebopDrone().setContextEssaimView(Ecran.getContext());

        NomDrone1 = (TextView) findViewById(R.id.NomDrone1);
        NomDrone2 = (TextView) findViewById(R.id.NomDrone2);
        NomDrone3 = (TextView) findViewById(R.id.NomDrone3);
        TabNomDrone = new TextView[3];
        TabNomDrone[0] = NomDrone1;
        TabNomDrone[1] = NomDrone2;
        TabNomDrone[2] = NomDrone3;

        batteryDrone1 = (ImageView) findViewById(R.id.Batterie1);
        batteryDrone2 = (ImageView) findViewById(R.id.Batterie2);
        batteryDrone3 = (ImageView) findViewById(R.id.Batterie3);
        TabBatterieDrone = new ImageView[3];
        TabBatterieDrone[0] = batteryDrone1;
        TabBatterieDrone[1] = batteryDrone2;
        TabBatterieDrone[2] = batteryDrone3;

        batteryDrone1txt = (TextView) findViewById(R.id.Batterie1txt);
        batteryDrone2txt = (TextView) findViewById(R.id.Batterie2txt);
        batteryDrone3txt = (TextView) findViewById(R.id.Batterie3txt);
        TabBatterieDronetxt = new TextView[3];
        TabBatterieDronetxt[0] = batteryDrone1txt;
        TabBatterieDronetxt[1] = batteryDrone2txt;
        TabBatterieDronetxt[2] = batteryDrone3txt;

        Drone1 = (ImageView) findViewById(R.id.Drone1);
        D1ProxRedBot = (ImageView) findViewById(R.id.D1ProxRedBot);
        D1ProxOrBot = (ImageView) findViewById(R.id.D1ProxOrBot);
        D1ProxJauBot = (ImageView) findViewById(R.id.D1ProxJauBot);
        D1ProxRedTop = (ImageView) findViewById(R.id.D1ProxRedTop);
        D1ProxOrTop = (ImageView) findViewById(R.id.D1ProxOrTop);
        D1ProxJauTop = (ImageView) findViewById(R.id.D1ProxJauTop);
        D1ProxRedRight = (ImageView) findViewById(R.id.D1ProxRedRight);
        D1ProxOrRight = (ImageView) findViewById(R.id.D1ProxOrRight);
        D1ProxJauRight = (ImageView) findViewById(R.id.D1ProxJauRight);
        D1ProxRedLeft = (ImageView) findViewById(R.id.D1ProxRedLeft);
        D1ProxOrLeft = (ImageView) findViewById(R.id.D1ProxOrLeft);
        D1ProxJauLeft = (ImageView) findViewById(R.id.D1ProxJauLeft);
        D1ProxRedUp = (ImageView) findViewById(R.id.D1ProxRedUp);
        D1ProxOrUp = (ImageView) findViewById(R.id.D1ProxOrUp);
        D1ProxJauUp = (ImageView) findViewById(R.id.D1ProxJauUp);
        D1ProxRedDown = (ImageView) findViewById(R.id.D1ProxRedDown);
        D1ProxOrDown = (ImageView) findViewById(R.id.D1ProxOrDown);
        D1ProxJauDown = (ImageView) findViewById(R.id.D1ProxJauDown);

        Drone2 = (ImageView) findViewById(R.id.Drone2);
        D2ProxRedBot = (ImageView) findViewById(R.id.D2ProxRedBot);
        D2ProxOrBot = (ImageView) findViewById(R.id.D2ProxOrBot);
        D2ProxJauBot = (ImageView) findViewById(R.id.D2ProxJauBot);
        D2ProxRedTop = (ImageView) findViewById(R.id.D2ProxRedTop);
        D2ProxOrTop = (ImageView) findViewById(R.id.D2ProxOrTop);
        D2ProxJauTop = (ImageView) findViewById(R.id.D2ProxJauTop);
        D2ProxRedRight = (ImageView) findViewById(R.id.D2ProxRedRight);
        D2ProxOrRight = (ImageView) findViewById(R.id.D2ProxOrRight);
        D2ProxJauRight = (ImageView) findViewById(R.id.D2ProxJauRight);
        D2ProxRedLeft = (ImageView) findViewById(R.id.D2ProxRedLeft);
        D2ProxOrLeft = (ImageView) findViewById(R.id.D2ProxOrLeft);
        D2ProxJauLeft = (ImageView) findViewById(R.id.D2ProxJauLeft);
        D2ProxRedUp = (ImageView) findViewById(R.id.D2ProxRedUp);
        D2ProxOrUp = (ImageView) findViewById(R.id.D2ProxOrUp);
        D2ProxJauUp = (ImageView) findViewById(R.id.D2ProxJauUp);
        D2ProxRedDown = (ImageView) findViewById(R.id.D2ProxRedDown);
        D2ProxOrDown = (ImageView) findViewById(R.id.D2ProxOrDown);
        D2ProxJauDown = (ImageView) findViewById(R.id.D2ProxJauDown);

        Drone3 = (ImageView) findViewById(R.id.Drone3);
        D3ProxRedBot = (ImageView) findViewById(R.id.D3ProxRedBot);
        D3ProxOrBot = (ImageView) findViewById(R.id.D3ProxOrBot);
        D3ProxJauBot = (ImageView) findViewById(R.id.D3ProxJauBot);
        D3ProxRedTop = (ImageView) findViewById(R.id.D3ProxRedTop);
        D3ProxOrTop = (ImageView) findViewById(R.id.D3ProxOrTop);
        D3ProxJauTop = (ImageView) findViewById(R.id.D3ProxJauTop);
        D3ProxRedRight = (ImageView) findViewById(R.id.D3ProxRedRight);
        D3ProxOrRight = (ImageView) findViewById(R.id.D3ProxOrRight);
        D3ProxJauRight = (ImageView) findViewById(R.id.D3ProxJauRight);
        D3ProxRedLeft = (ImageView) findViewById(R.id.D3ProxRedLeft);
        D3ProxOrLeft = (ImageView) findViewById(R.id.D3ProxOrLeft);
        D3ProxJauLeft = (ImageView) findViewById(R.id.D3ProxJauLeft);
        D3ProxRedUp = (ImageView) findViewById(R.id.D3ProxRedUp);
        D3ProxOrUp = (ImageView) findViewById(R.id.D3ProxOrUp);
        D3ProxJauUp = (ImageView) findViewById(R.id.D3ProxJauUp);
        D3ProxRedDown = (ImageView) findViewById(R.id.D3ProxRedDown);
        D3ProxOrDown = (ImageView) findViewById(R.id.D3ProxOrDown);
        D3ProxJauDown = (ImageView) findViewById(R.id.D3ProxJauDown);

        Drone1.setOnTouchListener(new MyTouchListener1());
        Drone2.setOnTouchListener(new MyTouchListener2());
        Drone3.setOnTouchListener(new MyTouchListener3());
        findViewById(R.id.Ecran).setOnDragListener(new MyDragListener());

        density = getResources().getDisplayMetrics().density;
        densite = Float.toString(density);

        // return 0.75 if it's LDPI
        // return 1.0 if it's MDPI
        // return 1.5 if it's HDPI
        // return 2.0 if it's XHDPI
        // return 3.0 if it's XXHDPI
        // return 4.0 if it's XXXHDPI

        layoutDrone();

        //On gère l'affichage de la batterie des drones

        updateBatterieLevel();

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            GlobalCouple.couples.get(i).getBebopDrone().addListener(mBebopListenerBattery);
        }

        proxyBarsView();

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


                TabBatterieDronetxt[i].setText(Integer.toString(batteryPercentage)+" %");

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

    protected void layoutDrone(){

        for(int i = 0 ; i < GlobalCouple.couples.size() ; i++){
            Toast.makeText(getApplicationContext(), GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName(), Toast.LENGTH_SHORT).show();
            TabNomDrone[i].setText(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
        }
    }

    protected void proxyBarsView(){

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
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone1", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class MyTouchListener2 implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone2", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class MyTouchListener3 implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone3", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
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
                    System.out.println(Float.toString(x));
                    System.out.println(Float.toString(y));
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    AbsoluteLayout container = (AbsoluteLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    if (event.getClipData().getDescription().getLabel().equals("Drone1")) {
                        Drone1.setX(x - Drone1.getWidth() / 2);
                        Drone1.setY(y - Drone1.getHeight() / 2);
                        D1ProxRedBot.setX(x - D1ProxRedBot.getWidth() / 2);
                        D1ProxRedBot.setY(y + 46 * density / 2 - D1ProxRedBot.getHeight() / 2);
                        D1ProxOrBot.setX(x - D1ProxOrBot.getWidth() / 2);
                        D1ProxOrBot.setY(y + 56 * density / 2 - D1ProxOrBot.getHeight() / 2);
                        D1ProxJauBot.setX(x - D1ProxJauBot.getWidth() / 2);
                        D1ProxJauBot.setY(y + 66 * density / 2 - D1ProxJauBot.getHeight() / 2);
                        D1ProxRedTop.setX(x - D1ProxRedTop.getWidth() / 2);
                        D1ProxRedTop.setY(y - 50 * density / 2 - D1ProxRedTop.getHeight() / 2);
                        D1ProxOrTop.setX(x - D1ProxOrTop.getWidth() / 2);
                        D1ProxOrTop.setY(y - 60 * density / 2 - D1ProxOrTop.getHeight() / 2);
                        D1ProxJauTop.setX(x - D1ProxJauTop.getWidth() / 2);
                        D1ProxJauTop.setY(y - 70 * density / 2 - D1ProxJauTop.getHeight() / 2);
                        D1ProxRedRight.setX(x + 45 * density / 2 - D1ProxRedRight.getWidth() / 2);
                        D1ProxRedRight.setY(y - D1ProxRedRight.getHeight() / 2);
                        D1ProxOrRight.setX(x + 55 * density / 2 - D1ProxOrRight.getWidth() / 2);
                        D1ProxOrRight.setY(y - D1ProxOrRight.getHeight() / 2);
                        D1ProxJauRight.setX(x + 65 * density / 2 - D1ProxJauRight.getWidth() / 2);
                        D1ProxJauRight.setY(y - D1ProxJauRight.getHeight() / 2);
                        D1ProxRedLeft.setX(x - 50 * density / 2 - D1ProxRedLeft.getWidth() / 2);
                        D1ProxRedLeft.setY(y - D1ProxRedLeft.getHeight() / 2);
                        D1ProxOrLeft.setX(x - 60 * density / 2 - D1ProxOrLeft.getWidth() / 2);
                        D1ProxOrLeft.setY(y - D1ProxOrLeft.getHeight() / 2);
                        D1ProxJauLeft.setX(x - 70 * density / 2 - D1ProxJauLeft.getWidth() / 2);
                        D1ProxJauLeft.setY(y - D1ProxJauLeft.getHeight() / 2);
                        D1ProxRedUp.setX(x - D1ProxRedUp.getWidth() / 2);
                        D1ProxRedUp.setY(y - 11 * density / 2 - D1ProxRedUp.getHeight() / 2);
                        D1ProxOrUp.setX(x - D1ProxOrUp.getWidth() / 2);
                        D1ProxOrUp.setY(y - 20 * density / 2 - D1ProxOrUp.getHeight() / 2);
                        D1ProxJauUp.setX(x - D1ProxJauUp.getWidth() / 2);
                        D1ProxJauUp.setY(y - 29 * density / 2 - D1ProxJauUp.getHeight() / 2);
                        D1ProxRedDown.setX(x - D1ProxRedUp.getWidth() / 2);
                        D1ProxRedDown.setY(y + 18 * density / 2 - D1ProxRedUp.getHeight() / 2);
                        D1ProxOrDown.setX(x - D1ProxOrUp.getWidth() / 2);
                        D1ProxOrDown.setY(y + 27 * density / 2 - D1ProxOrUp.getHeight() / 2);
                        D1ProxJauDown.setX(x - D1ProxJauUp.getWidth() / 2);
                        D1ProxJauDown.setY(y + 34 * density / 2 - D1ProxJauUp.getHeight() / 2);
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone2")) {
                        Drone2.setX(x - Drone2.getWidth() / 2);
                        Drone2.setY(y - Drone2.getHeight() / 2);
                        D2ProxRedBot.setX(x - D2ProxRedBot.getWidth() / 2);
                        D2ProxRedBot.setY(y + 46 * density / 2 - D2ProxRedBot.getHeight() / 2);
                        D2ProxOrBot.setX(x - D2ProxOrBot.getWidth() / 2);
                        D2ProxOrBot.setY(y + 56 * density / 2 - D2ProxOrBot.getHeight() / 2);
                        D2ProxJauBot.setX(x - D2ProxJauBot.getWidth() / 2);
                        D2ProxJauBot.setY(y + 66 * density / 2 - D2ProxJauBot.getHeight() / 2);
                        D2ProxRedTop.setX(x - D2ProxRedTop.getWidth() / 2);
                        D2ProxRedTop.setY(y - 50 * density / 2 - D2ProxRedTop.getHeight() / 2);
                        D2ProxOrTop.setX(x - D2ProxOrTop.getWidth() / 2);
                        D2ProxOrTop.setY(y - 60 * density / 2 - D2ProxOrTop.getHeight() / 2);
                        D2ProxJauTop.setX(x - D2ProxJauTop.getWidth() / 2);
                        D2ProxJauTop.setY(y - 70 * density / 2 - D2ProxJauTop.getHeight() / 2);
                        D2ProxRedRight.setX(x + 45 * density / 2 - D2ProxRedRight.getWidth() / 2);
                        D2ProxRedRight.setY(y - D2ProxRedRight.getHeight() / 2);
                        D2ProxOrRight.setX(x + 55 * density / 2 - D2ProxOrRight.getWidth() / 2);
                        D2ProxOrRight.setY(y - D2ProxOrRight.getHeight() / 2);
                        D2ProxJauRight.setX(x + 65 * density / 2 - D2ProxJauRight.getWidth() / 2);
                        D2ProxJauRight.setY(y - D2ProxJauRight.getHeight() / 2);
                        D2ProxRedLeft.setX(x - 50 * density / 2 - D2ProxRedLeft.getWidth() / 2);
                        D2ProxRedLeft.setY(y - D2ProxRedLeft.getHeight() / 2);
                        D2ProxOrLeft.setX(x - 60 * density / 2 - D2ProxOrLeft.getWidth() / 2);
                        D2ProxOrLeft.setY(y - D2ProxOrLeft.getHeight() / 2);
                        D2ProxJauLeft.setX(x - 70 * density / 2 - D2ProxJauLeft.getWidth() / 2);
                        D2ProxJauLeft.setY(y - D2ProxJauLeft.getHeight() / 2);
                        D2ProxRedUp.setX(x - D2ProxRedUp.getWidth() / 2);
                        D2ProxRedUp.setY(y - 11 * density / 2 - D2ProxRedUp.getHeight() / 2);
                        D2ProxOrUp.setX(x - D2ProxOrUp.getWidth() / 2);
                        D2ProxOrUp.setY(y - 20 * density / 2 - D2ProxOrUp.getHeight() / 2);
                        D2ProxJauUp.setX(x - D2ProxJauUp.getWidth() / 2);
                        D2ProxJauUp.setY(y - 29 * density / 2 - D2ProxJauUp.getHeight() / 2);
                        D2ProxRedDown.setX(x - D2ProxRedUp.getWidth() / 2);
                        D2ProxRedDown.setY(y + 18 * density / 2 - D2ProxRedUp.getHeight() / 2);
                        D2ProxOrDown.setX(x - D2ProxOrUp.getWidth() / 2);
                        D2ProxOrDown.setY(y + 27 * density / 2 - D2ProxOrUp.getHeight() / 2);
                        D2ProxJauDown.setX(x - D2ProxJauUp.getWidth() / 2);
                        D2ProxJauDown.setY(y + 34 * density / 2 - D2ProxJauUp.getHeight() / 2);
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone3")) {
                        Drone3.setX(x - Drone3.getWidth() / 2);
                        Drone3.setY(y - Drone3.getHeight() / 2);
                        D3ProxRedBot.setX(x - D3ProxRedBot.getWidth() / 2);
                        D3ProxRedBot.setY(y + 46 * density / 2 - D3ProxRedBot.getHeight() / 2);
                        D3ProxOrBot.setX(x - D3ProxOrBot.getWidth() / 2);
                        D3ProxOrBot.setY(y + 56 * density / 2 - D3ProxOrBot.getHeight() / 2);
                        D3ProxJauBot.setX(x - D3ProxJauBot.getWidth() / 2);
                        D3ProxJauBot.setY(y + 66 * density / 2 - D3ProxJauBot.getHeight() / 2);
                        D3ProxRedTop.setX(x - D3ProxRedTop.getWidth() / 2);
                        D3ProxRedTop.setY(y - 50 * density / 2 - D3ProxRedTop.getHeight() / 2);
                        D3ProxOrTop.setX(x - D3ProxOrTop.getWidth() / 2);
                        D3ProxOrTop.setY(y - 60 * density / 2 - D3ProxOrTop.getHeight() / 2);
                        D3ProxJauTop.setX(x - D3ProxJauTop.getWidth() / 2);
                        D3ProxJauTop.setY(y - 70 * density / 2 - D3ProxJauTop.getHeight() / 2);
                        D3ProxRedRight.setX(x + 45 * density / 2 - D3ProxRedRight.getWidth() / 2);
                        D3ProxRedRight.setY(y - D3ProxRedRight.getHeight() / 2);
                        D3ProxOrRight.setX(x + 55 * density / 2 - D3ProxOrRight.getWidth() / 2);
                        D3ProxOrRight.setY(y - D3ProxOrRight.getHeight() / 2);
                        D3ProxJauRight.setX(x + 65 * density / 2 - D3ProxJauRight.getWidth() / 2);
                        D3ProxJauRight.setY(y - D3ProxJauRight.getHeight() / 2);
                        D3ProxRedLeft.setX(x - 50 * density / 2 - D3ProxRedLeft.getWidth() / 2);
                        D3ProxRedLeft.setY(y - D3ProxRedLeft.getHeight() / 2);
                        D3ProxOrLeft.setX(x - 60 * density / 2 - D3ProxOrLeft.getWidth() / 2);
                        D3ProxOrLeft.setY(y - D3ProxOrLeft.getHeight() / 2);
                        D3ProxJauLeft.setX(x - 70 * density / 2 - D3ProxJauLeft.getWidth() / 2);
                        D3ProxJauLeft.setY(y - D3ProxJauLeft.getHeight() / 2);
                        D3ProxRedUp.setX(x - D3ProxRedUp.getWidth() / 2);
                        D3ProxRedUp.setY(y - 11 * density / 2 - D3ProxRedUp.getHeight() / 2);
                        D3ProxOrUp.setX(x - D3ProxOrUp.getWidth() / 2);
                        D3ProxOrUp.setY(y - 20 * density / 2 - D3ProxOrUp.getHeight() / 2);
                        D3ProxJauUp.setX(x - D3ProxJauUp.getWidth() / 2);
                        D3ProxJauUp.setY(y - 28 * density / 2 - D3ProxJauUp.getHeight() / 2);
                        D3ProxRedDown.setX(x - D3ProxRedUp.getWidth() / 2);
                        D3ProxRedDown.setY(y + 18 * density / 2 - D3ProxRedUp.getHeight() / 2);
                        D3ProxOrDown.setX(x - D3ProxOrUp.getWidth() / 2);
                        D3ProxOrDown.setY(y + 27 * density / 2 - D3ProxOrUp.getHeight() / 2);
                        D3ProxJauDown.setX(x - D3ProxJauUp.getWidth() / 2);
                        D3ProxJauDown.setY(y + 34 * density / 2 - D3ProxJauUp.getHeight() / 2);
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
