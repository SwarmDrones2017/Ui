package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

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

        final Button btnReset = (Button) findViewById(R.id.btnResetConfDrones);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_settings);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final LinearLayout linearLayoutB = (LinearLayout) findViewById(R.id.linearLayoutB);
        LinearLayout linearLayoutH = (LinearLayout) findViewById(R.id.linearLayoutH);

        final ArrayList<Listdronereglage> listbutton = new ArrayList<Listdronereglage>();
        int whoismaster = GlobalCouple.whoIsMaster();
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                RadioButton radiodrone = new RadioButton(this);
                radiodrone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            //Le maitre
                            for (int j = 0; j < listbutton.size(); j++) {
                                if (buttonView == listbutton.get(j).getRadiodrone()) {
                                    GlobalCouple.couples.get(j).getBebopDrone().setMaster(true);
                                    buttonView.setChecked(true);
                                } else {
                                    GlobalCouple.couples.get(j).getBebopDrone().setMaster(false);
                                    buttonView.setChecked(false);
                                }
                            }
                        }


                    }
                });
                ImageButton setting = new ImageButton(this);
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO lancer l'activity correspondant
                        Intent PilotageConfActivity = new Intent(EssaimConfig.this, PilotageConf.class);
                        startActivity(PilotageConfActivity);
                        //TODO ne pas oublier le putextra extra
                    }

                });
                //Bitmap resizedBitmap = Bitmap.createScaledBitmap(b,drone.getWidth(),drone.getHeight(),false);
                //drone.setText(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName());
                radiodrone.setText(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName());
                setting.setImageBitmap(b);
                linearLayoutB.addView(setting);
                ViewGroup.LayoutParams params = setting.getLayoutParams();
                //params.height = setting.getHeight();
                radiodrone.setLayoutParams(params);
                if (whoismaster == i) {
                    radiodrone.setChecked(true);
                }

                radioGroup.addView(radiodrone);
                listbutton.add(new Listdronereglage(radiodrone, setting));
            }
        }

        //On gère le retour à l'état initial de l'interface
/*
//TODO Le reset est important
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
*/
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

class Listdronereglage {

    private RadioButton radiodrone;
    private ImageButton setting;

    Listdronereglage(RadioButton radiodrone, ImageButton setting) {
        this.radiodrone = radiodrone;
        this.setting = setting;
    }

    public RadioButton getRadiodrone() {
        return radiodrone;
    }

    public ImageButton getSetting() {
        return setting;
    }


}
