package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Classe permettant la configuration de l'essaim
 * - paramètres communs ou pas
 * - Liste des drones et choix du drone "maitre" ainsi que possibilité d'acceder
 * aux paramètres des drones
 */

public class EssaimConfig extends AppCompatActivity {

    static int NumeroDrone = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_config);

        Intent EssaimConfigActivity = new Intent();
        setResult(RESULT_OK, EssaimConfigActivity);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        ImageButton btnSettingsSwarm = (ImageButton) findViewById(R.id.btnSettingsSwarm);
        final ToggleButton btnDrone1 = (ToggleButton) findViewById(R.id.tglBtnDrone1);
        final ToggleButton btnDrone2 = (ToggleButton) findViewById(R.id.tglBtnDrone2);
        final ToggleButton btnDrone3 = (ToggleButton) findViewById(R.id.tglBtnDrone3);
        final ToggleButton btnDrone4 = (ToggleButton) findViewById(R.id.tglBtnDrone4);
        final ToggleButton btnDrone5 = (ToggleButton) findViewById(R.id.tglBtnDrone5);
        final Button btnReset = (Button) findViewById(R.id.btnResetConfDrones);
        final ImageButton btnSet1 = (ImageButton) findViewById(R.id.btn_Drone1_Setting);
        final ImageButton btnSet2 = (ImageButton) findViewById(R.id.btn_Drone2_Setting);
        final ImageButton btnSet3 = (ImageButton) findViewById(R.id.btn_Drone3_Setting);
        final ImageButton btnSet4 = (ImageButton) findViewById(R.id.btn_Drone4_Setting);
        final ImageButton btnSet5 = (ImageButton) findViewById(R.id.btn_Drone5_Setting);

        //On gère le fonctionnement des toggleButton du choix du drone "maitre"

        btnDrone1.setText(MainActivity.items[0].getname());
        btnDrone1.setTextOn(MainActivity.items[0].getname());
        btnDrone1.setTextOff(MainActivity.items[0].getname());

        btnDrone2.setText(MainActivity.items[1].getname());
        btnDrone2.setTextOn(MainActivity.items[1].getname());
        btnDrone2.setTextOff(MainActivity.items[1].getname());

        btnDrone3.setText(MainActivity.items[2].getname());
        btnDrone3.setTextOn(MainActivity.items[2].getname());
        btnDrone3.setTextOff(MainActivity.items[2].getname());

        btnDrone4.setText(MainActivity.items[3].getname());
        btnDrone4.setTextOn(MainActivity.items[3].getname());
        btnDrone4.setTextOff(MainActivity.items[3].getname());

        btnDrone5.setText(MainActivity.items[4].getname());
        btnDrone5.setTextOn(MainActivity.items[4].getname());
        btnDrone5.setTextOff(MainActivity.items[4].getname());

        if (btnDrone1.isChecked()) {
            btnDrone1.setClickable(false);
        } else if (btnDrone2.isChecked()) {
            btnDrone2.setClickable(false);
        } else if (btnDrone3.isChecked()) {
            btnDrone3.setClickable(false);
        } else if (btnDrone4.isChecked()) {
            btnDrone4.setClickable(false);
        } else if (btnDrone5.isChecked()) {
            btnDrone5.setClickable(false);
        }

        btnDrone1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnDrone1.isChecked()) {
                    btnDrone2.setChecked(false);
                    btnDrone3.setChecked(false);
                    btnDrone4.setChecked(false);
                    btnDrone5.setChecked(false);
                    btnDrone1.setClickable(false);
                } else {
                    btnDrone1.setClickable(true);
                }
            }
        });

        btnDrone2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnDrone2.isChecked()) {
                    btnDrone1.setChecked(false);
                    btnDrone3.setChecked(false);
                    btnDrone4.setChecked(false);
                    btnDrone5.setChecked(false);
                    btnDrone2.setClickable(false);
                } else {
                    btnDrone2.setClickable(true);
                }
            }
        });

        btnDrone3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnDrone3.isChecked()) {
                    btnDrone2.setChecked(false);
                    btnDrone1.setChecked(false);
                    btnDrone4.setChecked(false);
                    btnDrone5.setChecked(false);
                    btnDrone3.setClickable(false);
                } else {
                    btnDrone3.setClickable(true);
                }
            }
        });

        btnDrone4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnDrone4.isChecked()) {
                    btnDrone2.setChecked(false);
                    btnDrone3.setChecked(false);
                    btnDrone1.setChecked(false);
                    btnDrone5.setChecked(false);
                    btnDrone4.setClickable(false);
                } else {
                    btnDrone4.setClickable(true);
                }
            }
        });

        btnDrone5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnDrone5.isChecked()) {
                    btnDrone2.setChecked(false);
                    btnDrone3.setChecked(false);
                    btnDrone4.setChecked(false);
                    btnDrone1.setChecked(false);
                    btnDrone5.setClickable(false);
                } else {
                    btnDrone5.setClickable(true);
                }
            }
        });

        //On gère le retour à l'état initial de l'interface

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDrone1.setChecked(true);
            }
        });

        //On gère le passage au paramétrage de l'essaim

        btnSettingsSwarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PilotageConf.class);
                startActivity(PilotageConfActivity);
            }
        });

        //On gère le passage au paramétrage des drones

        btnSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 0;
            }
        });

        btnSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 1;
            }
        });

        btnSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 2;
            }
        });

        btnSet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 3;
            }
        });

        btnSet5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                startActivity(PilotageConfActivity);
                NumeroDrone = 4;
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimConfig.this.finish();
                Intent ControlActivity = new Intent(EssaimConfig.this, Control.class);
                startActivity(ControlActivity);
            }
        });
    }

}
