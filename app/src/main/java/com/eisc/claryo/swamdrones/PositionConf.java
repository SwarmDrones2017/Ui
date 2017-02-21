package com.eisc.claryo.swamdrones;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.eisc.claryo.swamdrones.EssaimConfig.NumeroDrone;

/**
 * Classe permettant de régler les paramètres de position d'un/des drone(s)
 */

public class PositionConf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.posit_conf);

        Intent PositionConfActivity = new Intent();
        setResult(RESULT_OK, PositionConfActivity);

        Bundle extras = getIntent().getExtras();
        final int correspondant = extras.getInt(MessageKEY.POSITIONCOUPLE);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        Button btnReset = (Button) findViewById(R.id.btnResetPilotDrones);
        TextView txtDroneNom = (TextView) findViewById(R.id.txtDroneNom);

        ImageButton btnVideoConf = (ImageButton) findViewById(R.id.btnVideoConf);
        ImageButton btnInfoConf = (ImageButton) findViewById(R.id.btnInfoConf);

        final TextView txtsBAltiMax = (TextView) findViewById(R.id.txtValeursBAltiMax);
        final TextView txtsBDistMax = (TextView) findViewById(R.id.txtValeursBDistMax);

        final SeekBar sBAltiMax = (SeekBar) findViewById(R.id.sBAltiMax);
        final SeekBar sBDistMax = (SeekBar) findViewById(R.id.sBDistMax);



        //Gerer le nom du drone à afficher dans le réglages des paramètres
        txtDroneNom.setText(GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getDroneName());
        /*
        for(int i=0; i<5; i++){
            if(NumeroDrone == i) {
                txtDroneNom.setText(MainActivity.items[i].getname());
            }
        }
*/
        //On gère la plage de valeurs des seekbars
        sBAltiMax.setProgress((int) GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getAltitude_max());

        sBAltiMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;
            double temp = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = ((5 + progress));
                temp = (double)progressChanged/10;
                String valeurFinale = String.valueOf(temp);
                txtsBAltiMax.setText(valeurFinale + "m");
                GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setAltitude_max(new Float(valeurFinale));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sBDistMax.setProgress((int) GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getDistance_max());
        sBDistMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 10;
            double temp = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = ((10 + progress));
                temp = (double)progressChanged;
                String valeurFinale = String.valueOf(temp);
                txtsBDistMax.setText(valeurFinale + "m");
                GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().setDistance_max(new Float(valeurFinale));
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
                sBAltiMax.setProgress(295);
                sBDistMax.setProgress(1990);
            }
        });

        //On gère le passage aux autres menus

        btnVideoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoConfActivity = new Intent(PositionConf.this, VideoConf.class);
                VideoConfActivity.putExtra(MessageKEY.POSITIONCOUPLE,correspondant);
                startActivity(VideoConfActivity);
            }
        });

        btnInfoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InfoConfActivity = new Intent(PositionConf.this, InfoConf.class);
                startActivity(InfoConfActivity);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PositionConf.this.finish();
                Intent EssaimConfigActivity = new Intent(PositionConf.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });
    }

}
