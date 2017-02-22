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

        final SeekBar sBInclinMax = (SeekBar) findViewById(R.id.sBInclinMax);
        final SeekBar sBInclinSpeed = (SeekBar) findViewById(R.id.sBInclinSpeed);
        final SeekBar sBVerticalSpeed = (SeekBar) findViewById(R.id.sBVerticalSpeed);
        final SeekBar sBRotatSpeed = (SeekBar) findViewById(R.id.sBRotatSpeed);

        final TextView txtsBInclinMax = (TextView) findViewById(R.id.txtVerSoft);
        final TextView txtsBInclinSpeed = (TextView) findViewById(R.id.txtVerMat);
        final TextView txtsBVerticalSpeed = (TextView) findViewById(R.id.txtValeursBVerticalSpeed);
        final TextView txtsBRotatSpeed = (TextView) findViewById(R.id.txtValeursBRotatSpeed);

        //On gère la plage de valeur des seekbars

        sBInclinMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 5 + progress;
                txtsBInclinMax.setText(String.valueOf(progressChanged) + "°");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBInclinSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 80;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 80 + progress;
                txtsBInclinSpeed.setText(String.valueOf(progressChanged) + "°/s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBVerticalSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;
            double temp = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = ((5 + progress));
                temp = (double)progressChanged/10;
                String valeurFinale = String.valueOf(temp);
                txtsBVerticalSpeed.setText(valeurFinale + "°/s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBRotatSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 10;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = 10 + progress;
                txtsBRotatSpeed.setText(progressChanged + "°/s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
