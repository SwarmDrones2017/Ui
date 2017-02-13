package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.eisc.claryo.swamdrones.EssaimConfig.NumeroDrone;

/**
 * Classe permettant de régler les paramètres vidéo d'un/des drone(s)
 */

public class VideoConf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.video_conf);

        Intent VideoConfActivity = new Intent();
        setResult(RESULT_OK, VideoConfActivity);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetour);
        TextView txtDroneNom = (TextView) findViewById(R.id.txtDroneNom);

        ImageButton btnPilotConf = (ImageButton) findViewById(R.id.btnPilotConf);
        ImageButton btnPositConf = (ImageButton) findViewById(R.id.btnPositConf);
        ImageButton btnNetConf = (ImageButton) findViewById(R.id.btnNetConf);
        ImageButton btnInfoConf = (ImageButton) findViewById(R.id.btnInfoConf);

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
                Intent PilotageConfActivity = new Intent(VideoConf.this, PilotageConf.class);
                startActivity(PilotageConfActivity);
            }
        });

        btnPositConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoConfActivity = new Intent(VideoConf.this, PositionConf.class);
                startActivity(VideoConfActivity);
            }
        });

        btnNetConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NetConfActivity = new Intent(VideoConf.this, NetConf.class);
                startActivity(NetConfActivity);
            }
        });

        btnInfoConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InfoConfActivity = new Intent(VideoConf.this, InfoConf.class);
                startActivity(InfoConfActivity);
            }
        });

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoConf.this.finish();
                Intent EssaimConfigActivity = new Intent(VideoConf.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });
    }

}
