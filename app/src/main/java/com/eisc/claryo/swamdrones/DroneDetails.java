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

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARFrame;

/**
 * Classe gérant les détails du drone choisit
 */

public class DroneDetails extends AppCompatActivity implements BebopDrone.Listener {

    int progress = 75;
    TextView TextViewBattery;
    ProgressBar ProgressBarBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drone_details);
        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnRetourMenuPrincipal);
        ProgressBarBattery = (ProgressBar) findViewById(R.id.progressBarBattery);
        TextViewBattery = (TextView) findViewById(R.id.textViewBattery);

        CaractDrone[] caract = {
                new CaractDrone("Type de produit", "Bebop_648992"),
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
        ProgressBarBattery.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
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

    @Override
    public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {

    }

    @Override
    public void onBatteryChargeChanged(int batteryPercentage) {
        TextViewBattery.setText(batteryPercentage + "%");
        ProgressBarBattery.setProgress(batteryPercentage);
        if(batteryPercentage<75)
            ProgressBarBattery.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_orange));
        else if(batteryPercentage<25)
            ProgressBarBattery.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal_red));
        else
            ProgressBarBattery.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_bar_horizontal));
    }

    @Override
    public void onPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {

    }

    @Override
    public void onPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {

    }

    @Override
    public void configureDecoder(ARControllerCodec codec) {

    }

    @Override
    public void onFrameReceived(ARFrame frame) {

    }

    @Override
    public void onMatchingMediasFound(int nbMedias) {

    }

    @Override
    public void onDownloadProgressed(String mediaName, int progress) {

    }

    @Override
    public void onDownloadComplete(String mediaName) {

    }
}
