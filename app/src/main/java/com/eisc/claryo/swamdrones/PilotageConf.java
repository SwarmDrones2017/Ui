package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Classe permettant de régler les paramètres de pilotage d'un/des drone(s)
 */

public class PilotageConf extends AppCompatActivity {

    private final static String TILT = "°";
    private final static String SPEEDTILT = "°/s";
    private final static String SPEEDDISTANCE = "m/s";

    /**
     * Création de l'interface
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.pilotage_conf);

        Intent PilotageConfActivity = new Intent();
        setResult(RESULT_OK, PilotageConfActivity);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        Button btnReset = (Button) findViewById(R.id.btnResetPilotDrones);

        int master = GlobalCouple.whoIsMaster();

        final SeekBar sBInclinMax = (SeekBar) findViewById(R.id.sBInclinMax);
        final SeekBar sBInclinSpeed = (SeekBar) findViewById(R.id.sBInclinSpeed);
        final SeekBar sBVerticalSpeed = (SeekBar) findViewById(R.id.sBVerticalSpeed);
        final SeekBar sBRotatSpeed = (SeekBar) findViewById(R.id.sBRotatSpeed);

        final TextView txtsBInclinMax = (TextView) findViewById(R.id.txtVerSoft);
        final TextView txtsBInclinSpeed = (TextView) findViewById(R.id.txtVerMat);
        final TextView txtsBVerticalSpeed = (TextView) findViewById(R.id.txtValeursBVerticalSpeed);
        final TextView txtsBRotatSpeed = (TextView) findViewById(R.id.txtValeursBRotatSpeed);

        //On gère la plage de valeur des seekbars

        sBInclinMax.setProgress((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getTiltmax() - 5);
        txtsBInclinMax.setText(String.valueOf((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getTiltmax()) + TILT);
        sBInclinMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;

            /**
             * Gestion du paramétrage des drones en fonction de la valeur de la seekbar
             * Changement de la plage de valeur de la seekbar
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 5 + progress;
                sBInclinMax.setProgress(progress);
                txtsBInclinMax.setText(String.valueOf(progressChanged) + TILT);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                        GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().setTiltmax(progressChanged);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBInclinSpeed.setProgress((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedtilt() - 80);
        txtsBInclinSpeed.setText(String.valueOf((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedtilt()) + SPEEDTILT);
        sBInclinSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 80;

            /**
             * Gestion du paramétrage des drones en fonction de la valeur de la seekbar
             * Changement de la plage de valeur de la seekbar
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 80 + progress;
                sBInclinSpeed.setProgress(progress);
                txtsBInclinSpeed.setText(String.valueOf(progressChanged) + SPEEDTILT);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                        GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().setSpeedtilt(progressChanged);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        float tempVerticalSpeed = GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedverticale() * 10 - 5;
        sBVerticalSpeed.setProgress((int) tempVerticalSpeed);
        txtsBVerticalSpeed.setText(GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedverticale() + SPEEDDISTANCE);
        sBVerticalSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;
            float temp = 0;

            /**
             * Gestion du paramétrage des drones en fonction de la valeur de la seekbar
             * Changement de la plage de valeur de la seekbar
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = ((5 + progress));
                temp = ((float) progressChanged) / 10;
                String valeurFinale = Float.toString(temp);
                sBVerticalSpeed.setProgress(progress);
                txtsBVerticalSpeed.setText(valeurFinale + SPEEDDISTANCE);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                        GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().setSpeedverticale(temp);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBRotatSpeed.setProgress((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedrotation() - 10);
        txtsBRotatSpeed.setText((int) GlobalCouple.couples.get(master).getBebopDrone().getInfoDrone().getSpeedrotation() + SPEEDTILT);
        sBRotatSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 10;

            /**
             * Gestion du paramétrage des drones en fonction de la valeur de la seekbar
             * Changement de la plage de valeur de la seekbar
             * @param seekBar
             * @param progress
             * @param fromUser
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 10 + progress;
                sBRotatSpeed.setProgress(progress);
                txtsBRotatSpeed.setText(progressChanged + SPEEDTILT);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                        GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().setSpeedrotation(progressChanged);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**
         * Gestion des boutons
         */
        //On gère le reset des réglages

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sBInclinMax.setProgress(15);
                sBInclinSpeed.setProgress(220);
                sBVerticalSpeed.setProgress(5);
                sBRotatSpeed.setProgress(90);
            }
        });

        //On gère le passage aux autres menus

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilotageConf.this.finish();
                Intent EssaimConfigActivity = new Intent(PilotageConf.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });
    }
}
