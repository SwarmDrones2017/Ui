package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.eisc.claryo.swamdrones.EssaimConfig.NumeroDrone;

/**
 * Classe permettant de voir les infos du drone choisit
 */

public class InfoConf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.info_conf);

        Intent InfoConfActivity = new Intent();
        setResult(RESULT_OK, InfoConfActivity);

        Bundle extras = getIntent().getExtras();
        final int correspondant = extras.getInt(MessageKEY.POSITIONCOUPLE);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        TextView txtDroneNom = (TextView) findViewById(R.id.txtDroneNom);

        ImageButton btnPositConf = (ImageButton) findViewById(R.id.btnPositConf);
        ImageButton btnVideoConf = (ImageButton) findViewById(R.id.btnVideoConf);

        TextView txtVerSoft = (TextView) findViewById(R.id.txtVerSoft);
        TextView txtVerMat = (TextView) findViewById(R.id.txtVerMat);

        //Gerer le nom du drone à afficher dans le réglages des paramètres
        txtDroneNom.setText(GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getDroneName());

        txtVerSoft.setText(GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getSoftwareVersion());
        txtVerMat.setText(GlobalCouple.couples.get(correspondant).getBebopDrone().getInfoDrone().getHardwareVersion());
        //On gère le passage aux autres menus

        btnPositConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PositionConfActivity = new Intent(InfoConf.this, PositionConf.class);
                PositionConfActivity.putExtra(MessageKEY.POSITIONCOUPLE, correspondant);
                startActivity(PositionConfActivity);
            }
        });

        btnVideoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoConfActivity = new Intent(InfoConf.this, VideoConf.class);
                VideoConfActivity.putExtra(MessageKEY.POSITIONCOUPLE, correspondant);
                startActivity(VideoConfActivity);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoConf.this.finish();
                Intent EssaimConfigActivity = new Intent(InfoConf.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });
    }

}
