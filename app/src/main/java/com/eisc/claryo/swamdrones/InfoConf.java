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

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        TextView txtDroneNom = (TextView) findViewById(R.id.txtDroneNom);

        ImageButton btnPilotConf = (ImageButton) findViewById(R.id.btnPilotConf);
        ImageButton btnPositConf = (ImageButton) findViewById(R.id.btnPositConf);
        ImageButton btnVideoConf = (ImageButton) findViewById(R.id.btnVideoConf);
        ImageButton btnNetConf = (ImageButton) findViewById(R.id.btnNetConf);

        //Gerer le nom du drone à afficher dans le réglages des paramètres

        for(int i=0; i<5; i++){
            if(NumeroDrone == i) {
                txtDroneNom.setText(MainActivity.items[i].getname());
            }
        }

        //On gère le passage aux autres menus

        btnPilotConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PilotageConfActivity = new Intent(InfoConf.this, PilotageConf.class);
                startActivity(PilotageConfActivity);
            }
        });

        btnPositConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoConfActivity = new Intent(InfoConf.this, PositionConf.class);
                startActivity(VideoConfActivity);
            }
        });

        btnVideoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NetConfActivity = new Intent(InfoConf.this, VideoConf.class);
                startActivity(NetConfActivity);
            }
        });

        btnNetConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InfoConfActivity = new Intent(InfoConf.this, NetConf.class);
                startActivity(InfoConfActivity);
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
