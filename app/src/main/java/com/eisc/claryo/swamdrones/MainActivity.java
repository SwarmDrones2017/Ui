package com.eisc.claryo.swamdrones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Classe principale qui se créée au lancement de l'application
 */
public class MainActivity extends AppCompatActivity {

    public static final String MSG_ANY_DRONES = "Aucun";
    private ListView list; //liste des drones
    private TextView textViewNbDrones; //texte nombre de drone
    private TextView textViewDrones; //texte "Drone(s)"
    static private String[] listDrone; //liste des drones par nom
    private Button btnFly; //bouton "Fly"
    private DiscoveryDrone discoveryDrone; //objet DiscoveryDrone issu du SDK
    private final Handler handler = new Handler() { //handler pour la détection d'un nouveau drone sur le réseau
        @Override
        public void handleMessage(Message msg) {
            listDrone = msg.getData().getStringArray(MessageKEY.LISTDRONEUPDATE);
            ShowDroneList();
        }
    };

    /**
     * Affiche la liste des drones connectés au réseau
     */
    private void ShowDroneList() {
        if (listDrone != null) {
            if (listDrone[0].equals(MSG_ANY_DRONES)) {
                textViewNbDrones.setText(MSG_ANY_DRONES);
                textViewDrones.setText("drone");
                list.setVisibility(View.INVISIBLE);
                btnFly.setVisibility(View.INVISIBLE);
            } else {
                textViewNbDrones.setText("" + listDrone.length);
                ArrayAdapter<String> listitems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDrone);
                list.setAdapter(listitems);
                list.setVisibility(View.VISIBLE);
                btnFly.setVisibility(View.VISIBLE);
            }
            if (listDrone.length == 1)
                textViewDrones.setText("drone");
            else if (listDrone.length > 1)
                textViewDrones.setText("drones");

        }
    }

    /**
     * Méthode onCreate lancée à la création de la vue
     * Cette méthode créer la vue principale et appelle :
     *      * La classe ServerUDP qui relie l'application au script des raspberry (récupération des données capteurs)
     *      * La classe DiscoveryDrone qui lance la recherche des drones connectés au réseau
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (GlobalCouple.couples == null) {
            GlobalCouple.couples = new ArrayList<>();
            if(MessageKEY.FLAG_FIRSTUSE) {
                new ServerUDP(getApplicationContext());//recherche des raspberry sur le réseau et création des objets Raspberry
//                Log.i("newServerUDP", "new Serveur UDP");
                MessageKEY.FLAG_FIRSTUSE = false;
            }
        }

        list = (ListView) findViewById(R.id.listViewConnectedDrones);
        btnFly = (Button) findViewById(R.id.btnFly);
        btnFly.setVisibility(View.INVISIBLE);
        Button btnABout = (Button) findViewById(R.id.btnAbout);
        Button btnNotice = (Button) findViewById(R.id.btnNotice);
        textViewNbDrones = (TextView) findViewById(R.id.textViewNbDrones);
        textViewDrones = (TextView) findViewById(R.id.textViewDrones);
        ImageButton btnRefresh = (ImageButton) findViewById(R.id.btnMainActivityRefresh);

        ShowDroneList();//afficher les drones découverts

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Si aucun drone n'est connecté, cliquer sur l'item ne fera rien
                if(!list.getAdapter().getItem(0).equals("Aucun drone connecter")){
                    String droneClicked = (String)list.getAdapter().getItem(position);
                    int droneSelected=-1;
                    for(int i=0; i<GlobalCouple.couples.size(); i++){
                        if(GlobalCouple.couples.get(i).getBebopDrone() != null){
                            if(droneClicked.equals(GlobalCouple.couples.get(i).getBebopDrone().getdeviceService().getName()))
                                droneSelected=i;
                        }
                    }

                    Intent DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                    if(droneSelected !=-1){ //on ajoute en extra pour la vue DroneDetails les informations du drone sélectionné

                        DroneDetailsActivity.putExtra("Name", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDroneName())
                                .putExtra("Battery", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getBattery())
                                .putExtra("HardVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getHardwareVersion())
                                .putExtra("SerialID", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSerialID())
                                .putExtra("SoftVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareVersion())
                                .putExtra("GPSVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareGPSVersion())
                                .putExtra("nbFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getNbFlights())
                                .putExtra("LastFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationLastFlight())
                                .putExtra("TotalFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationTotalFlights());

                    } else {
                        DroneDetailsActivity.putExtra("Name", "null");
                    }

                    startActivity(DroneDetailsActivity);//on démarre l'activité DroneDetails concernant le drone sélectionné
                }

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });
/**
 * Bouton pour rafraichir la liste si aucun drone n'est découvert alors qu'il y en a connecté au réseau
 */
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (discoveryDrone == null) {
                    discoveryDrone = new DiscoveryDrone(getApplication(), handler);
                } else {
                    discoveryDrone.onServicesDevicesListUpdated();
                }
                Toast.makeText(getApplicationContext(), "Liste mise à jour", Toast.LENGTH_SHORT).show();

            }
        });
        btnFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ControlActivity = new Intent(MainActivity.this, Control.class);
                startActivity(ControlActivity);
            }
        });
        btnABout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AboutActivity = new Intent(MainActivity.this, APropos.class);
                startActivity(AboutActivity);
            }
        });

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NoticeActivity = new Intent(MainActivity.this, Notice.class);
                startActivity(NoticeActivity);
            }
        });
        discoveryDrone = new DiscoveryDrone(getApplicationContext(), handler); //lancement de l'activité de découverte de drone
    }

}

