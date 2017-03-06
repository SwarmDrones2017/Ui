package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Classe permettant la configuration de l'essaim
 * - paramètres communs à l'essaim
 * - Liste des drones et choix du drone "maitre" ainsi que possibilité d'acceder
 * aux paramètres individuels des drones
 */

public class EssaimConfig extends AppCompatActivity {

    static int NumeroDrone = 0;

    /**
     * Création de l'interface
     * @param savedInstanceState
     */
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
        final Button btnReset = (Button) findViewById(R.id.btnResetConfDrones);

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_settings);

        final LinearLayout linearLayoutB = (LinearLayout) findViewById(R.id.linearLayoutB);
        final LinearLayout linearLayoutA = (LinearLayout) findViewById(R.id.linearLayoutA);

        final ArrayList<Listdronereglage> listbutton = new ArrayList<>();

        /**
         * Gestion de l'affichage dynamique des drones de l'essaim
         * pour la selection du drone maitre
         */
        int whoismaster = GlobalCouple.whoIsMaster();
        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                ToggleButton choixdrone = new ToggleButton(this);
                choixdrone.setText(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
                choixdrone.setTextOn(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
                choixdrone.setTextOff(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName());
                choixdrone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            for (int j = 0; j < listbutton.size(); j++) {
                                if (buttonView == listbutton.get(j).getChoixdrone()) {
                                    GlobalCouple.couples.get(j).getBebopDrone().setMaster(true);
                                    buttonView.setClickable(false);
                                }
                                else {
                                    GlobalCouple.couples.get(j).getBebopDrone().setMaster(false);
                                    listbutton.get(j).getChoixdrone().setChecked(false);
                                    listbutton.get(j).getChoixdrone().setClickable(true);
                                }
                            }
                        }
                    }
                });
                ImageButton setting = new ImageButton(this);
                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int correspondant = -1;
                        for (int i = 0; i < listbutton.size();i++){
                            if(v == listbutton.get(i).getSetting()){
                                correspondant = i;
                                break;
                            }
                        }
                        Intent PositionConfActivity = new Intent(EssaimConfig.this, PositionConf.class);
                        PositionConfActivity.putExtra(MessageKEY.POSITIONCOUPLE,correspondant);
                        startActivity(PositionConfActivity);
                    }

                });
                //radiodrone.setText(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName());
                setting.setImageBitmap(b);
                linearLayoutB.addView(setting);
                linearLayoutA.addView(choixdrone);
                if (whoismaster == i) {
                    choixdrone.setChecked(true);
                    choixdrone.setClickable(false);
                }


                listbutton.add(new Listdronereglage(choixdrone, setting));
            }
        }

        /**
         * Remise à zéro du choix du drone maitre
         */

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listbutton.size() > 0){
                    listbutton.get(0).getChoixdrone().setChecked(true);
                }
                //btnDrone1.setChecked(true);
            }
        });


        /**
         * Gestion du passage aux interfaces de paramétrages
         */

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimConfig.this.finish();
                Intent ControlActivity = new Intent(EssaimConfig.this, Control.class);
                startActivity(ControlActivity);
            }
        });

        btnSettingsSwarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimConfig.this.finish();
                Intent PilotageConfActivity = new Intent(EssaimConfig.this ,PilotageConf.class);
                startActivity(PilotageConfActivity);
            }
        });
    }

}

class Listdronereglage {

    private final ToggleButton choixdrone;
    private final ImageButton setting;

    Listdronereglage(ToggleButton choixdrone, ImageButton setting) {
        this.choixdrone = choixdrone;
        this.setting = setting;
    }

    public ToggleButton getChoixdrone() {
        return choixdrone;
    }

    public ImageButton getSetting() {
        return setting;
    }


}
