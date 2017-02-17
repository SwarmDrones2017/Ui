package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Control extends AppCompatActivity implements BebopDrone.Listener{
    private ProgressBar progressBarBatterie;
    private ImageView batteryIndicator;
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

        ImageButton btnSwapView = (ImageButton) findViewById(R.id.btnSwapView);

        progressBarBatterie = (ProgressBar)findViewById(R.id.batteryLevel);
        progressBarBatterie.setProgress(progressBarBatterie.getMax());
        progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));

        batteryIndicator = (ImageView)findViewById(R.id.battery_indicator);
        batteryIndicator.setImageResource(R.drawable.ic_battery_full_24dp);

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
                if(isChecked){
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                            GlobalCouple.couples.get(i).getBebopDrone().takeOff();
                    }
                }
                else {
                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
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

    @Override
    public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {

    }

    @Override
    public void onBatteryChargeChanged(int batteryPercentage) {
        progressBarBatterie.setProgress(batteryPercentage);
        switch (batteryPercentage){
            case 90:
                batteryIndicator.setImageResource(R.drawable.ic_battery_90_24dp);
                break;
            case 80:
                batteryIndicator.setImageResource(R.drawable.ic_battery_80_24dp);
                break;
            case 60:
                batteryIndicator.setImageResource(R.drawable.ic_battery_60_24dp);
                break;
            case 50:
                batteryIndicator.setImageResource(R.drawable.ic_battery_50_24dp);
                break;
            case 30:
                batteryIndicator.setImageResource(R.drawable.ic_battery_30_24dp);
                break;
            case 20:
                batteryIndicator.setImageResource(R.drawable.ic_battery_20_24dp);
                break;
            case 10:
                batteryIndicator.setImageResource(R.drawable.ic_battery_alert_24dp);
                break;
        }
        if(batteryPercentage<75)
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_orange));
        else if(batteryPercentage<25)
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_red));
        else
            progressBarBatterie.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
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
}
