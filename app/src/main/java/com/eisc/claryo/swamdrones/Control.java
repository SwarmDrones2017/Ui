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



/**
 * Classe pour l'interface de controle de vol de l'essaim
 */

public class Control extends AppCompatActivity {
    private ProgressBar progressBarBatterie;
    private ImageView batteryIndicator;
    private int batteryPercentage;
    int positionMaster;

    private ImageButton btnRetour;
    private ImageButton btnSettings;
    private ImageButton btn_forward;
    private ImageButton btn_roll_left;
    private ImageButton btn_roll_right;
    private ImageButton btn_back;
    private ImageButton btnSwapView;
    private ImageButton btn_gaz_up;
    private ImageButton btn_gaz_down;
    private ImageButton btn_yaw_left;
    private ImageButton btn_yaw_right;

    private Handler handlerBattery = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            batteryPercentage = msg.getData().getInt(MessageKEY.BATTERYLEVEL);
            updateLevelBattery();
        }
    };

    private Handler handlerObstacle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int north = msg.getData().getInt(MessageKEY.OBSTACLENORTH);
            int south = msg.getData().getInt(MessageKEY.OBSTACLESOUTH);
            int west = msg.getData().getInt(MessageKEY.OBSTACLEWEST);
            int est = msg.getData().getInt(MessageKEY.OBSTACLEEST);

            if(north > 0){
                if(north <= Raspberry.SEUIL_OBSTACLE_STOP){
                    if(btn_forward.isPressed()){
                        btn_forward.setPressed(false);
                    }
                    btn_forward.setEnabled(false);

                    //btn_forward.setBackgroundColor(Color.RED);
                    btn_forward.setColorFilter(Color.argb(255,255,255,255));
                }
                else{
                    btn_forward.setEnabled(true);
                    btn_forward.setColorFilter(Color.argb(0,0,0,0));
                }
            }
            if (south > 0){
                if(south <= Raspberry.SEUIL_OBSTACLE_STOP){
                    btn_back.setPressed(false);
                    btn_back.setEnabled(false);
                    btn_back.setColorFilter(Color.argb(255,255,255,255));
                }
                else{
                    btn_back.setEnabled(true);
                    btn_back.setColorFilter(Color.argb(0,0,0,0));
                }
            }
            if(west > 0){
                if(west <= Raspberry.SEUIL_OBSTACLE_STOP){
                    btn_roll_left.setPressed(false);
                    btn_roll_left.setEnabled(false);
                    btn_roll_left.setColorFilter(Color.argb(255,255,255,255));
                }
                else{
                    btn_roll_left.setEnabled(true);
                    btn_roll_left.setColorFilter(Color.argb(0,0,0,0));
                }
            }
            if(est > 0){
                if(est <= Raspberry.SEUIL_OBSTACLE_STOP){
                    btn_roll_right.setPressed(false);
                    btn_roll_right.setEnabled(false);
                    btn_roll_right.setColorFilter(Color.argb(255,255,255,255));
                }
                else{
                    btn_roll_right.setEnabled(true);
                    btn_roll_right.setColorFilter(Color.argb(0,0,0,0));
                }
            }
        }
    };


    private void updateLevelBattery() {
        Log.i("updateBattery", "UpdateBattery");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.control_drones);

        progressBarBatterie = (ProgressBar) findViewById(R.id.batteryLevel);
        batteryIndicator = (ImageView) findViewById(R.id.battery_indicator);

        btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal1);
        btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        btn_roll_left = (ImageButton) findViewById(R.id.btn_roll_left);
        btn_roll_right = (ImageButton) findViewById(R.id.btn_roll_right);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btnSwapView = (ImageButton) findViewById(R.id.btnSwapView);
        btn_gaz_up = (ImageButton) findViewById(R.id.btn_gaz_up);
        btn_gaz_down = (ImageButton) findViewById(R.id.btn_gaz_down);
        btn_yaw_left = (ImageButton) findViewById(R.id.btn_yaw_left);
        btn_yaw_right = (ImageButton) findViewById(R.id.btn_yaw_right);

        ToggleButton toggle_takeoff_land = (ToggleButton) findViewById(R.id.toggle_takeoff_land);

        Button btn_emergency = (Button) findViewById(R.id.btn_emergency);

        positionMaster = -1;
        batteryPercentage = 100;
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                if (GlobalCouple.couples.get(i).getBebopDrone().isMaster())
                    positionMaster = i;

                if(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery()<batteryPercentage)
                    batteryPercentage = GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getBattery();

                if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() == null)
                    GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(handlerBattery);

            }
            if (GlobalCouple.couples.get(i).getRaspberry() != null){
                GlobalCouple.couples.get(i).getRaspberry().setHandlerObstacle(handlerObstacle);
            }
        }
        Log.i("PositionMaster", "Position Master : "+positionMaster);

        updateLevelBattery();

        Intent ControlActivity = new Intent();
        setResult(RESULT_OK, ControlActivity);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.this.finish();
                Intent MainActivity = new Intent(Control.this, MainActivity.class);
                startActivity(MainActivity);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EssaimConfigActivity = new Intent(Control.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });

        toggle_takeoff_land.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                            if (GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                                GlobalCouple.couples.get(i).getBebopDrone().takeOff();
                        }
                    }
                } else {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone() != null) {
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveForward();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveBack();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveLeft();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveRight();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startTurnLeft();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startTurnRight();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveUp();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                                GlobalCouple.couples.get(i).getBebopDrone().startMoveDown();
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            if(GlobalCouple.couples.get(i).getBebopDrone() != null){
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

        btnSwapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EssaimViewActivity = new Intent(Control.this, EssaimView.class);
                startActivity(EssaimViewActivity);
            }
        });
    }
}
