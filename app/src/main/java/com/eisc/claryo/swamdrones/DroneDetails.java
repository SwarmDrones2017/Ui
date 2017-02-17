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
        ProgressBar ProgressBarBattery = (ProgressBar) findViewById(R.id.progressBarBattery);
        TextView TextViewBattery = (TextView) findViewById(R.id.textViewBattery);
        Bundle extras = getIntent().getExtras();

        short durationLastFlight = extras.getShort("LastFlight");
        int rsDurationLastFlight=durationLastFlight%60;
        int mDurationLastFlight=durationLastFlight/60;
        int hDurationLastFlight=mDurationLastFlight/60;
        mDurationLastFlight=mDurationLastFlight%60;
        String strDurationLastFlight = String.format("%02d:%02d:%02d",hDurationLastFlight, mDurationLastFlight, rsDurationLastFlight);

        int durationTotalFlight = extras.getShort("TotalFlight");
        int rsDurationTotalFlight=durationTotalFlight%60;
        int mDurationTotalFlight=durationTotalFlight/60;
        int hDurationTotalFlight=mDurationTotalFlight/60;
        mDurationTotalFlight=mDurationTotalFlight%60;
        String strDurationTotalFlight = String.format("%02d:%02d:%02d",hDurationTotalFlight, mDurationTotalFlight, rsDurationTotalFlight);

        CaractDrone[] caract = {
                new CaractDrone("Type de produit", extras.getString("Name")),
                new CaractDrone("Version materiel", extras.getString("HardVersion")),
                new CaractDrone("Version logiciel", extras.getString("SoftVersion")),
                new CaractDrone("Version GPS", extras.getString("GPSVersion")),
                new CaractDrone("Numéro de série", extras.getString("SerialID")),
                new CaractDrone("Vols", Short.toString(extras.getShort("nbFlight"))),
                new CaractDrone("Dernier vol", strDurationLastFlight),
                new CaractDrone("Temps de vol total", strDurationTotalFlight),
        };

        ArrayAdapter<CaractDrone> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, caract);
        ListView listC = (ListView) findViewById(R.id.ListViewCaract);
        listC.setAdapter(adapter);
        progress = extras.getInt("Battery");
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
