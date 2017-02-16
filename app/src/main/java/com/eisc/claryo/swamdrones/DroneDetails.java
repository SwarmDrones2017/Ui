package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Classe gérant les détails du drone choisit
 */

public class DroneDetails extends AppCompatActivity {

    int progress = 75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_details);
        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal);
        ProgressBar ProgressBarBattery = (ProgressBar) findViewById(R.id.progressBarBattery);
        TextView TextViewBattery = (TextView) findViewById(R.id.textViewBattery);
        Bundle extras = getIntent().getExtras();

        CaractDrone[] caract = {
                new CaractDrone("Type de produit", extras.getString("Name")),
                new CaractDrone("Version materiel", "HW_02"),
                new CaractDrone("Version logiciel", "3.9.0"),
                new CaractDrone("Version GPS", "2.01F"),
                new CaractDrone("Numéro de série", "P1823784716485"),
                new CaractDrone("Vols", "95"),
                new CaractDrone("Dernier vol", "00:02:28"),
                new CaractDrone("Temps de vol total", "00:49:55"),
        };


        ArrayAdapter<CaractDrone> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, caract);
        ListView listC = (ListView) findViewById(R.id.ListViewCaract);
        listC.setAdapter(adapter);

        TextViewBattery.setText(progress + "%");

        ProgressBarBattery.setProgress(progress);

        Intent DroneDetailsActivity = new Intent();
        setResult(RESULT_OK, DroneDetailsActivity);

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroneDetails.this.finish();
            }
        });
    }

}
