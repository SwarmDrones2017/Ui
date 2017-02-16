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

/**
 * Classe pour l'interface de controle de vol de l'essaim
 */

public class Control extends AppCompatActivity {

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

        //ImageButton btnSwapView = (ImageButton) findViewById(R.id.btnSwapView);

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
                    for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                            MainActivity.listBebop.get(i).takeOff();
                    }
                }
                else {
                    for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                        if(GlobalCouple.couples.get(i).getBebopDrone().isFlyAuthorization())
                            MainActivity.listBebop.get(i).land();
                    }
                }
            }
        });

        btn_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                    MainActivity.listBebop.get(i).emergency();
                }
            }
        });
        btn_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setPitch((byte) 50);
                            MainActivity.listBebop.get(i).setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setPitch((byte) 0);
                            MainActivity.listBebop.get(i).setFlag((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setPitch((byte) -50);
                            MainActivity.listBebop.get(i).setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setPitch((byte) 0);
                            MainActivity.listBebop.get(i).setFlag((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setRoll((byte) -50);
                            MainActivity.listBebop.get(i).setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setRoll((byte) 0);
                            MainActivity.listBebop.get(i).setFlag((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setRoll((byte) 50);
                            MainActivity.listBebop.get(i).setFlag((byte) 1);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setRoll((byte) 0);
                            MainActivity.listBebop.get(i).setFlag((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setYaw((byte) -50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setYaw((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setYaw((byte) 50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setYaw((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setGaz((byte) 50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setGaz((byte) 0);
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
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setGaz((byte) -50);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        for (int i = 0; i < MainActivity.listBebop.size(); i++) {
                            MainActivity.listBebop.get(i).setGaz((byte) 0);
                        }
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

       /* btnSwapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EssaimViewActivity = new Intent(Control.this, EssaimView.class);
                startActivity(EssaimViewActivity);
            }
        });*/
    }

}
