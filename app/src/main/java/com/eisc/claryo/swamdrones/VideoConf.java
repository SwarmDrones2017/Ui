package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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
        Button btnReset = (Button) findViewById(R.id.btnResetVideoDrones);

        ImageButton btnPilotConf = (ImageButton) findViewById(R.id.btnPilotConf);
        ImageButton btnPositConf = (ImageButton) findViewById(R.id.btnPositConf);
        ImageButton btnInfoConf = (ImageButton) findViewById(R.id.btnInfoConf);

        final ToggleButton btn720p = (ToggleButton) findViewById(R.id.btnVid720);
        final ToggleButton btn1080p = (ToggleButton) findViewById(R.id.btnVid1080);
        final ToggleButton btnStdr = (ToggleButton) findViewById(R.id.btnStdr);
        final ToggleButton btnHigh = (ToggleButton) findViewById(R.id.btnHigh);
        final ToggleButton btn24fps = (ToggleButton) findViewById(R.id.btn24);
        final ToggleButton btn25fps = (ToggleButton) findViewById(R.id.btn25);
        final ToggleButton btn30fps = (ToggleButton) findViewById(R.id.btn30);
        final ToggleButton btnStabNon = (ToggleButton) findViewById(R.id.btnStablN);
        final ToggleButton btnStabOui = (ToggleButton) findViewById(R.id.btnStabO);
        final ToggleButton btn50Hz = (ToggleButton) findViewById(R.id.btn50Hz);
        final ToggleButton btn60Hz = (ToggleButton) findViewById(R.id.btn60Hz);
        final ToggleButton btnAutoHz = (ToggleButton) findViewById(R.id.btnAutoHz);

        if(btn720p.isChecked())
            btn720p.setClickable(false);
        else if(btn1080p.isChecked())
            btn1080p.setClickable(false);

        if(btnStdr.isChecked())
            btnStdr.setClickable(false);
        else if(btnHigh.isChecked())
            btnHigh.setClickable(false);

        if(btn24fps.isChecked())
            btn24fps.setClickable(false);
        else if(btn25fps.isChecked())
            btn25fps.setClickable(false);
        else if(btn30fps.isChecked())
            btn30fps.setClickable(false);

        if(btnStabNon.isChecked())
            btnStabNon.setClickable(false);
        else if(btnStabOui.isChecked())
            btnStabOui.setClickable(false);

        if(btn50Hz.isChecked())
            btn50Hz.setClickable(false);
        else if(btn60Hz.isChecked())
            btn60Hz.setClickable(false);
        else if(btnAutoHz.isChecked())
            btnAutoHz.setClickable(false);

        //Gerer le fonctionnement des boutons

            //Résolution de l'enregistrement

        btn1080p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn1080p.isChecked()) {
                    btn720p.setChecked(false);
                    btn1080p.setClickable(false);
                }
                else {
                    btn720p.setChecked(true);
                    btn1080p.setClickable(true);
                }
            }
        });

        btn720p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn720p.isChecked()) {
                    btn1080p.setChecked(false);
                    btn720p.setClickable(false);
                }
                else {
                    btn1080p.setChecked(true);
                    btn720p.setClickable(true);
                }
            }
        });

            //Qualité de l'enregistrement

        btnStdr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStdr.isChecked()) {
                    btnHigh.setChecked(false);
                    btnStdr.setClickable(false);
                }
                else {
                    btnHigh.setChecked(true);
                    btnStdr.setClickable(true);
                }
            }
        });

        btnHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnHigh.isChecked()) {
                    btnStdr.setChecked(false);
                    btnHigh.setClickable(false);
                }
                else {
                    btnStdr.setChecked(true);
                    btnHigh.setClickable(true);
                }
            }
        });

            //Fréquence d'images

        btn24fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn24fps.isChecked()) {
                    btn25fps.setChecked(false);
                    btn30fps.setChecked(false);
                    btn24fps.setClickable(false);
                }
                else {
                    btn24fps.setClickable(true);
                }
            }
        });

        btn25fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn25fps.isChecked()) {
                    btn24fps.setChecked(false);
                    btn30fps.setChecked(false);
                    btn25fps.setClickable(false);
                }
                else {
                    btn25fps.setClickable(true);
                }
            }
        });

        btn30fps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn30fps.isChecked()) {
                    btn24fps.setChecked(false);
                    btn25fps.setChecked(false);
                    btn30fps.setClickable(false);
                }
                else {
                    btn30fps.setClickable(true);
                }
            }
        });

            //Stabilisation d'image

        btnStabNon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStabNon.isChecked()) {
                    btnStabOui.setChecked(false);
                    btnStabNon.setClickable(false);
                }
                else {
                    btnStabOui.setChecked(true);
                    btnStabNon.setClickable(true);
                }
            }
        });

        btnStabOui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnStabOui.isChecked()) {
                    btnStabNon.setChecked(false);
                    btnStabOui.setClickable(false);
                }
                else {
                    btnStabNon.setChecked(true);
                    btnStabOui.setClickable(true);
                }
            }
        });

            //Mode d'anti-scintillement

        btn50Hz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn50Hz.isChecked()) {
                    btn60Hz.setChecked(false);
                    btnAutoHz.setChecked(false);
                    btn50Hz.setClickable(false);
                }
                else {
                    btn50Hz.setClickable(true);
                }
            }
        });

        btn60Hz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btn60Hz.isChecked()) {
                    btn50Hz.setChecked(false);
                    btnAutoHz.setChecked(false);
                    btn50Hz.setClickable(false);
                }
                else {
                    btn50Hz.setClickable(true);
                }
            }
        });

        btnAutoHz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btnAutoHz.isChecked()) {
                    btn50Hz.setChecked(false);
                    btn60Hz.setChecked(false);
                    btnAutoHz.setClickable(false);
                }
                else {
                    btnAutoHz.setClickable(true);
                }
            }
        });

        //Gerer le nom du drone à afficher dans le réglages des paramètres

        for(int i=0; i<5; i++){
            if(NumeroDrone == i) {
                txtDroneNom.setText(MainActivity.items[i].getname());
            }
        }

        //On gère le reset des paramétrages

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1080p.setChecked(true);
                btnStdr.setChecked(true);
                btn30fps.setChecked(true);
                btnStabOui.setChecked(true);
                btnAutoHz.setChecked(true);
            }
        });

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
