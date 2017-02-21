package com.eisc.claryo.swamdrones;

import android.content.Intent;
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
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.ProgressBar;


import static com.eisc.claryo.swamdrones.MessageHandler.BATTERYLEVEL;

/**
 * Classe pour l'interface de controle de vol de l'essaim
 */

public class Control extends AppCompatActivity {
    private ProgressBar progressBarBatterie;
    private ImageView batteryIndicator;
    private int batteryPercentage;
    int positionMaster;
    private Handler handlerBattery = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            batteryPercentage = msg.getData().getInt(BATTERYLEVEL);
            updateLevelBattery();
        }
    };

    private ImageView ProxJauneGauche, ProxOrangeGauche, ProxRougeGauche, ProxJauneDroite, ProxOrangeDroite,
            ProxRougeDroite, ProxJauneDevant, ProxOrangeDevant, ProxRougeDevant, ProxJauneDerriere, ProxOrangeDerriere,
            ProxRougeDerriere, ProxJauneAbove, ProxOrangeAbove, ProxRougeAbove, ProxJauneBelow, ProxOrangeBelow, ProxRougeBelow;

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


    protected void proxyBars(){
        //Gérer l'apparition des lignes de proximité

        int ProxGauche = 121;
        int ProxDroite = 34;
        int ProxDevant = 74;
        int ProxDerriere = 500;
        int ProxBelow = 5000;
        int ProxAbove = 86;
        Toast.makeText(getApplicationContext(),"G:"+ProxGauche+" D:"+ProxDroite+" Dv:"+ProxDevant+" Dr:"+ProxDerriere,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"A:"+ProxAbove+" B:"+ProxBelow,Toast.LENGTH_LONG).show();

        if(ProxGauche > 100){
            ProxJauneGauche.setVisibility(View.INVISIBLE);
            ProxOrangeGauche.setVisibility(View.INVISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        }
        else if(ProxGauche <= 100 && ProxGauche > 75){
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.INVISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        }
        else if(ProxGauche <= 75 && ProxGauche > 50){
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.VISIBLE);
            ProxRougeGauche.setVisibility(View.INVISIBLE);
        }
        else if(ProxGauche <= 50){
            ProxJauneGauche.setVisibility(View.VISIBLE);
            ProxOrangeGauche.setVisibility(View.VISIBLE);
            ProxRougeGauche.setVisibility(View.VISIBLE);
        }

        if(ProxDroite > 100){
            ProxJauneDroite.setVisibility(View.INVISIBLE);
            ProxOrangeDroite.setVisibility(View.INVISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        }
        else if(ProxDroite <= 100 && ProxDroite > 75){
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.INVISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        }
        else if(ProxDroite <= 75 && ProxDroite > 50){
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.VISIBLE);
            ProxRougeDroite.setVisibility(View.INVISIBLE);
        }
        else if(ProxDroite <= 50){
            ProxJauneDroite.setVisibility(View.VISIBLE);
            ProxOrangeDroite.setVisibility(View.VISIBLE);
            ProxRougeDroite.setVisibility(View.VISIBLE);
        }

        if(ProxDevant > 100){
            ProxJauneDevant.setVisibility(View.INVISIBLE);
            ProxOrangeDevant.setVisibility(View.INVISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        }
        else if(ProxDevant <= 100 && ProxDevant > 75){
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.INVISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        }
        else if(ProxDevant <= 75 && ProxDevant > 50){
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.VISIBLE);
            ProxRougeDevant.setVisibility(View.INVISIBLE);
        }
        else if(ProxDevant <= 50){
            ProxJauneDevant.setVisibility(View.VISIBLE);
            ProxOrangeDevant.setVisibility(View.VISIBLE);
            ProxRougeDevant.setVisibility(View.VISIBLE);
        }

        if(ProxDerriere > 100){
            ProxJauneDerriere.setVisibility(View.INVISIBLE);
            ProxOrangeDerriere.setVisibility(View.INVISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        }
        else if(ProxDerriere <= 100 && ProxDerriere > 75){
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.INVISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        }
        else if(ProxDerriere <= 75 && ProxDerriere > 50){
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.VISIBLE);
            ProxRougeDerriere.setVisibility(View.INVISIBLE);
        }
        else if(ProxDerriere <= 50){
            ProxJauneDerriere.setVisibility(View.VISIBLE);
            ProxOrangeDerriere.setVisibility(View.VISIBLE);
            ProxRougeDerriere.setVisibility(View.VISIBLE);
        }

        if(ProxBelow > 100){
            ProxJauneBelow.setVisibility(View.INVISIBLE);
            ProxOrangeBelow.setVisibility(View.INVISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        }
        else if(ProxBelow <= 100 && ProxBelow > 75){
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.INVISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        }
        else if(ProxBelow <= 75 && ProxBelow > 50){
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.VISIBLE);
            ProxRougeBelow.setVisibility(View.INVISIBLE);
        }
        else if(ProxBelow <= 50){
            ProxJauneBelow.setVisibility(View.VISIBLE);
            ProxOrangeBelow.setVisibility(View.VISIBLE);
            ProxRougeBelow.setVisibility(View.VISIBLE);
        }

        if(ProxAbove > 100){
            ProxJauneAbove.setVisibility(View.INVISIBLE);
            ProxOrangeAbove.setVisibility(View.INVISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        }
        else if(ProxAbove <= 100 && ProxAbove > 75){
            ProxJauneAbove.setVisibility(View.VISIBLE);
            ProxOrangeAbove.setVisibility(View.INVISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        }
        else if(ProxAbove <= 75 && ProxAbove > 50){
            ProxJauneAbove.setVisibility(View.VISIBLE);
            ProxOrangeAbove.setVisibility(View.VISIBLE);
            ProxRougeAbove.setVisibility(View.INVISIBLE);
        }
        else if(ProxAbove <= 50){
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

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal1);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ToggleButton toggle_takeoff_land = (ToggleButton) findViewById(R.id.toggle_takeoff_land);

        ImageButton btn_forward = (ImageButton) findViewById(R.id.btn_forward);
        ImageButton btn_roll_left = (ImageButton) findViewById(R.id.btn_roll_left);
        ImageButton btn_roll_right = (ImageButton) findViewById(R.id.btn_roll_right);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);

        ImageButton btn_gaz_up = (ImageButton) findViewById(R.id.btn_gaz_up);
        ImageButton btn_gaz_down = (ImageButton) findViewById(R.id.btn_gaz_down);
        ImageButton btn_yaw_left = (ImageButton) findViewById(R.id.btn_yaw_left);
        ImageButton btn_yaw_right = (ImageButton) findViewById(R.id.btn_yaw_right);

        Button btn_emergency = (Button) findViewById(R.id.btn_emergency);

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

        ImageButton btnSwapView = (ImageButton) findViewById(R.id.btnSwapView);

        positionMaster = -1;
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone().isMaster())
                positionMaster = i;

            if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() == null)
                GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(handlerBattery);
        }
        Log.i("PositionMaster", "Position Master : "+positionMaster);
        progressBarBatterie = (ProgressBar) findViewById(R.id.batteryLevel);

        batteryPercentage = GlobalCouple.couples.get(positionMaster).getBebopDrone().getInfoDrone().getBattery();
        batteryIndicator = (ImageView) findViewById(R.id.battery_indicator);
        updateLevelBattery();

        proxyBars();

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
                        if (GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                            GlobalCouple.couples.get(i).getBebopDrone().takeOff();
                    }
                } else {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if (GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                            GlobalCouple.couples.get(i).getBebopDrone().land();
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
                            GlobalCouple.couples.get(i).getBebopDrone().setPitch((byte) 50);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setPitch((byte) 0);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setPitch((byte) -50);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setPitch((byte) 0);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setRoll((byte) -50);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setRoll((byte) 0);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setRoll((byte) 50);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setRoll((byte) 0);
                            GlobalCouple.couples.get(i).getBebopDrone().setFlag((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setYaw((byte) -50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setYaw((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setYaw((byte) 50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setYaw((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setGaz((byte) 50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setGaz((byte) 0);
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
                            GlobalCouple.couples.get(i).getBebopDrone().setGaz((byte) -50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                            GlobalCouple.couples.get(i).getBebopDrone().setGaz((byte) 0);
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
