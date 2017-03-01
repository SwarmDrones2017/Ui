package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARFrame;

/**
 * Classe pour l'interface de controle de vol de l'essaim
 */

public class Control extends AppCompatActivity {
    private ProgressBar progressBarBatterie;
    private ImageView batteryIndicator;
    private int batteryPercentage;
    private int positionMaster;
    private ImageButton btnRetour;

    private ImageButton btn_forward;
    private ImageButton btn_roll_left;
    private ImageButton btn_roll_right;
    private ImageButton btn_back;

    private int north = 151;
    private int south = 151;
    private int west = 151;
    private int east = 151;
    private int above = 151;
    private final int below = 151;

    private ImageView ProxJauneGauche, ProxOrangeGauche, ProxRougeGauche, ProxJauneDroite, ProxOrangeDroite,
            ProxRougeDroite, ProxJauneDevant, ProxOrangeDevant, ProxRougeDevant, ProxJauneDerriere, ProxOrangeDerriere,
            ProxRougeDerriere, ProxJauneAbove, ProxOrangeAbove, ProxRougeAbove, ProxJauneBelow, ProxOrangeBelow, ProxRougeBelow;

    private final Handler handlerBattery = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            batteryPercentage = msg.getData().getInt(MessageKEY.BATTERYLEVEL);
            updateLevelBattery();
        }
    };

    private final Handler handlerObstacle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            north = msg.getData().getInt(MessageKEY.OBSTACLENORTH);
            south = msg.getData().getInt(MessageKEY.OBSTACLESOUTH);
            west = msg.getData().getInt(MessageKEY.OBSTACLEWEST);
            east = msg.getData().getInt(MessageKEY.OBSTACLEEST);
            above = msg.getData().getInt(MessageKEY.OBSTACLEABOVE);

            if (north > 0) {
                if (north <= 50) {
                    btn_forward.setEnabled(false);
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        GlobalCouple.couples.get(i).getBebopDrone().stopMoveForward();
                    }
                    btn_forward.setColorFilter(Color.argb(255, 255, 255, 255));
                } else {
                    btn_forward.setEnabled(true);
                    btn_forward.setColorFilter(Color.argb(0, 0, 0, 0));
                }
            }
            if (south > 0) {
                if (south <= 50) {
                    btn_back.setEnabled(false);
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        GlobalCouple.couples.get(i).getBebopDrone().stopMoveBack();
                    }
                    btn_back.setColorFilter(Color.argb(255, 255, 255, 255));
                } else {
                    btn_back.setEnabled(true);
                    btn_back.setColorFilter(Color.argb(0, 0, 0, 0));
                }
            }
            if (west > 0) {
                if (west <= 50) {
                    btn_roll_left.setEnabled(false);
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        GlobalCouple.couples.get(i).getBebopDrone().stopMoveLeft();
                    }
                    btn_roll_left.setColorFilter(Color.argb(255, 255, 255, 255));
                } else {
                    btn_roll_left.setEnabled(true);
                    btn_roll_left.setColorFilter(Color.argb(0, 0, 0, 0));
                }
            }
            if (east > 0) {
                if (east <= 50) {
                    btn_roll_right.setEnabled(false);
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        GlobalCouple.couples.get(i).getBebopDrone().stopMoveRight();
                    }
                    btn_roll_right.setColorFilter(Color.argb(255, 255, 255, 255));
                } else {
                    btn_roll_right.setEnabled(true);
                    btn_roll_right.setColorFilter(Color.argb(0, 0, 0, 0));
                }
            }
            proxyBars();
        }
    };

    private void updateLevelBattery() {
        Log.i("updateBattery", "UpdateBatteryControl");
        if (batteryPercentage > 65)
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
        else if (batteryPercentage > 35)
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_orange));
        else
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_red));

        if (batteryPercentage > 97)
            batteryIndicator.setImageResource(R.drawable.ic_battery_full_24dp);
        else if (batteryPercentage > 90)
            batteryIndicator.setImageResource(R.drawable.ic_battery_90_24dp);
        else if (batteryPercentage > 80)
            batteryIndicator.setImageResource(R.drawable.ic_battery_80_24dp);
        else if (batteryPercentage > 60)
            batteryIndicator.setImageResource(R.drawable.ic_battery_60_24dp);
        else if (batteryPercentage > 50)
            batteryIndicator.setImageResource(R.drawable.ic_battery_50_24dp);
        else if (batteryPercentage > 30)
            batteryIndicator.setImageResource(R.drawable.ic_battery_30_24dp);
        else if (batteryPercentage > 20)
            batteryIndicator.setImageResource(R.drawable.ic_battery_20_24dp);
        else
            batteryIndicator.setImageResource(R.drawable.ic_battery_alert_24dp);


        progressBarBatterie.setProgress(batteryPercentage);

    }

    private void proxyBars() {
        //Gérer l'apparition des lignes de proximité

        if (west > 100) {
            ProxJauneGauche.setVisibility(View.INVISIBLE);
            ProxOrangeGauche.setVisibility(View.INVISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        } else if (west <= 100 && west > 75) {
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.INVISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        } else if (west <= 75 && west > 50) {
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.VISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        } else if (west <= 50) {
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.VISIBLE);
            ProxRougeGauche.setVisibility(View.VISIBLE);
        }

        if (east > 100) {
            ProxJauneDroite.setVisibility(View.INVISIBLE);
            ProxOrangeDroite.setVisibility(View.INVISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        } else if (east <= 100 && east > 75) {
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.INVISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        } else if (east <= 75 && east > 50) {
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.VISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        } else if (east <= 50) {
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.VISIBLE);
            ProxRougeDroite.setVisibility(View.VISIBLE);
        }

        if (north > 100) {
            ProxJauneDevant.setVisibility(View.INVISIBLE);
            ProxOrangeDevant.setVisibility(View.INVISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        } else if (north <= 100 && north > 75) {
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.INVISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        } else if (north <= 75 && north > 50) {
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.VISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        } else if (north <= 50) {
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.VISIBLE);
            ProxRougeDevant.setVisibility(View.VISIBLE);
        }

        if (south > 100) {
            ProxJauneDerriere.setVisibility(View.INVISIBLE);
            ProxOrangeDerriere.setVisibility(View.INVISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        } else if (south <= 100 && south > 75) {
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.INVISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        } else if (south <= 75 && south > 50) {
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.VISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        } else if (south <= 50) {
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.VISIBLE);
            ProxRougeDerriere.setVisibility(View.VISIBLE);
        }

        if (below > 100) {
            ProxJauneBelow.setVisibility(View.INVISIBLE);
            ProxOrangeBelow.setVisibility(View.INVISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        } else if (below <= 100 && below > 75) {
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.INVISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        } else if (below <= 75 && below > 50) {
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.VISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        } else if (below <= 50) {
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.VISIBLE);
            ProxRougeBelow.setVisibility(View.VISIBLE);
        }

        if (above > 100) {
            ProxJauneAbove.setVisibility(View.INVISIBLE);
            ProxOrangeAbove.setVisibility(View.INVISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        } else if (above <= 100 && above > 75) {
            ProxJauneAbove.setVisibility(View.VISIBLE);
            ProxOrangeAbove.setVisibility(View.INVISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        } else if (above <= 75 && above > 50) {
            ProxJauneAbove.setVisibility(View.VISIBLE);
            ProxOrangeAbove.setVisibility(View.VISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        } else if (above <= 50) {
            ProxJauneAbove.setVisibility(View.VISIBLE);
            ProxOrangeAbove.setVisibility(View.VISIBLE);
            ProxRougeAbove.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.control_drones);

        progressBarBatterie = (ProgressBar) findViewById(R.id.batteryLevel);
        batteryIndicator = (ImageView) findViewById(R.id.battery_indicator);

        btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal1);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageButton btnSwapView = (ImageButton) findViewById(R.id.btnSwapView);
        btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal1);
        btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        btn_roll_left = (ImageButton) findViewById(R.id.btn_roll_left);
        btn_roll_right = (ImageButton) findViewById(R.id.btn_roll_right);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        ImageButton btn_gaz_up = (ImageButton) findViewById(R.id.btn_gaz_up);
        ImageButton btn_gaz_down = (ImageButton) findViewById(R.id.btn_gaz_down);
        ImageButton btn_yaw_left = (ImageButton) findViewById(R.id.btn_yaw_left);
        ImageButton btn_yaw_right = (ImageButton) findViewById(R.id.btn_yaw_right);
        Button btn_emergency = (Button) findViewById(R.id.btn_emergency);
        ToggleButton toggle_takeoff_land = (ToggleButton) findViewById(R.id.toggle_takeoff_land);

        ProxJauneGauche = (ImageView) findViewById(R.id.ProxJauneGauche);
        ProxOrangeGauche = (ImageView) findViewById(R.id.ProxOrangeGauche);
        ProxRougeGauche = (ImageView) findViewById(R.id.ProxRougeGauche);

        ProxJauneDroite = (ImageView) findViewById(R.id.ProxJauneDroite);
        ProxOrangeDroite = (ImageView) findViewById(R.id.ProxOrangeDroite);
        ProxRougeDroite = (ImageView) findViewById(R.id.ProxRougeDroite);

        ProxJauneDevant = (ImageView) findViewById(R.id.ProxJauneDevant);
        ProxOrangeDevant = (ImageView) findViewById(R.id.ProxOrangeDevant);
        ProxRougeDevant = (ImageView) findViewById(R.id.ProxRougeDevant);

        ProxJauneDerriere = (ImageView) findViewById(R.id.ProxJauneDerriere);
        ProxOrangeDerriere = (ImageView) findViewById(R.id.ProxOrangeDerriere);
        ProxRougeDerriere = (ImageView) findViewById(R.id.ProxRougeDerriere);

        ProxJauneAbove = (ImageView) findViewById(R.id.ProxJauneAbove);
        ProxOrangeAbove = (ImageView) findViewById(R.id.ProxOrangeAbove);
        ProxRougeAbove = (ImageView) findViewById(R.id.ProxRougeAbove);

        ProxJauneBelow = (ImageView) findViewById(R.id.ProxJauneBelow);
        ProxOrangeBelow = (ImageView) findViewById(R.id.ProxOrangeBelow);
        ProxRougeBelow = (ImageView) findViewById(R.id.ProxRougeBelow);

        positionMaster = -1;
        batteryPercentage = 100;
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                if (GlobalCouple.couples.get(i).getBebopDrone().isMaster())
                    positionMaster = i;

                if (GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery() < batteryPercentage)
                    batteryPercentage = GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery();

                if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() == null)
                    GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(handlerBattery);

            }
            if (GlobalCouple.couples.get(i).getRaspberry() != null) {
                GlobalCouple.couples.get(i).getRaspberry().setHandlerObstacle(handlerObstacle);
            }
        }
        Log.i("PositionMaster", "Position Master : " + positionMaster);

        //affichage batterie
        updateLevelBattery();

//        //set video
        GlobalCouple.couples.get(positionMaster).getBebopDrone().setBebopVideoView((BebopVideoView) findViewById(R.id.bebopVideoView));//set la vue VideoView avec l'objet BebopVideoView du drone maitre
        GlobalCouple.couples.get(positionMaster).getBebopDrone().addListener(mBebopListener);//ajout des listener au drone maitre
        startVideo(); //on lance la vidéo

        proxyBars();

        /**
         Gestion des boutons
         */
        /*Boutons pour changer de vue*/
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVideo();
                GlobalCouple.couples.get(positionMaster).getBebopDrone().setBebopVideoView(null);
                GlobalCouple.couples.get(positionMaster).getBebopDrone().removeListener(mBebopListener);
                Control.this.finish();
                Intent MainActivity = new Intent(Control.this, MainActivity.class);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(MainActivity);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVideo();
                GlobalCouple.couples.get(positionMaster).getBebopDrone().setBebopVideoView(null);
                GlobalCouple.couples.get(positionMaster).getBebopDrone().removeListener(mBebopListener);
                Control.this.finish();
                Control.this.finish();
                Intent EssaimConfigActivity = new Intent(Control.this, EssaimConfig.class);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(EssaimConfigActivity);
            }
        });

        btnSwapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVideo();
                GlobalCouple.couples.get(positionMaster).getBebopDrone().setBebopVideoView(null);
                GlobalCouple.couples.get(positionMaster).getBebopDrone().removeListener(mBebopListener);
                Control.this.finish();
                Intent EssaimViewActivity = new Intent(Control.this, EssaimView.class);
                startActivity(EssaimViewActivity);
            }
        });

        /*Boutons de pilotage*/
        toggle_takeoff_land.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                            if (GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                                GlobalCouple.couples.get(i).getBebopDrone().takeOff();
                        }
                    }
                } else {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                            if (GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                                GlobalCouple.couples.get(i).getBebopDrone().land();
                        }
                    }
                }
            }
        });

        btn_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    GlobalCouple.couples.get(i).getBebopDrone().emergency();
                }
            }
        });
        btn_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveForward();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveForward();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btn_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveBack();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveBack();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }

        });

        btn_roll_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveLeft();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveLeft();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        btn_roll_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveRight();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveRight();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btn_yaw_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startTurnLeft();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopTurnLeft();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btn_yaw_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startTurnRight();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopTurnRight();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btn_gaz_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveUp();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveUp();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        btn_gaz_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveDown();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                                GlobalCouple.couples.get(i).getBebopDrone().stopMoveDown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        Intent ControlActivity = new Intent();
        setResult(RESULT_OK, ControlActivity);
    }

    private final BebopDrone.Listener mBebopListener = new BebopDrone.Listener() {

        @Override
        public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {

        }

        @Override
        public void onBatteryChargeChanged(int batteryPercentage) {
            Log.i("updateBattery", "UpdateBatteryControl");
            if (batteryPercentage > 65)
                progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
            else if (batteryPercentage > 35)
                progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_orange));
            else
                progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_red));

            if (batteryPercentage > 97)
                batteryIndicator.setImageResource(R.drawable.ic_battery_full_24dp);
            else if (batteryPercentage > 90)
                batteryIndicator.setImageResource(R.drawable.ic_battery_90_24dp);
            else if (batteryPercentage > 80)
                batteryIndicator.setImageResource(R.drawable.ic_battery_80_24dp);
            else if (batteryPercentage > 60)
                batteryIndicator.setImageResource(R.drawable.ic_battery_60_24dp);
            else if (batteryPercentage > 50)
                batteryIndicator.setImageResource(R.drawable.ic_battery_50_24dp);
            else if (batteryPercentage > 30)
                batteryIndicator.setImageResource(R.drawable.ic_battery_30_24dp);
            else if (batteryPercentage > 20)
                batteryIndicator.setImageResource(R.drawable.ic_battery_20_24dp);
            else
                batteryIndicator.setImageResource(R.drawable.ic_battery_alert_24dp);


            progressBarBatterie.setProgress(batteryPercentage);
        }

        @Override
        public void onPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {

        }

        @Override
        public void onPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {

        }

        @Override
        public void configureDecoder(ARControllerCodec codec) {
            GlobalCouple.couples.get(positionMaster).getBebopDrone().getBebopVideoView().configureDecoder(codec);
        }

        @Override
        public void onFrameReceived(ARFrame frame) {
            GlobalCouple.couples.get(positionMaster).getBebopDrone().getBebopVideoView().displayFrame(frame);
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

    private void stopVideo() {
        GlobalCouple.couples.get(positionMaster).getBebopDrone().getmDeviceController().getFeatureARDrone3().sendMediaStreamingVideoEnable((byte) 0);
    }

    private void startVideo() {
        GlobalCouple.couples.get(positionMaster).getBebopDrone().getmDeviceController().getFeatureARDrone3().sendMediaStreamingVideoEnable((byte) 1);
    }
}
