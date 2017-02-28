package com.eisc.claryo.swamdrones;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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


import java.io.IOException;
import java.lang.annotation.Target;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String MSG_ANY_DRONES = "Aucun";
    public static DroneListeConnecte[] items;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private TextView textViewNbDrones;
    private MainActivity ici = this;
    static private String[] listDrone;
    private ImageButton btnRefresh;
    private Button btnFly;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listDrone = msg.getData().getStringArray(MessageKEY.LISTDRONEUPDATE);
            ShowDroneList();
        }
    };

    /**
     * @author : Sofiane
     */
    private void ShowDroneList() {
        if (listDrone != null) {
            if (listDrone[0].equals(MSG_ANY_DRONES)) {
                textViewNbDrones.setText(MSG_ANY_DRONES);
                list.setVisibility(View.INVISIBLE);
                btnFly.setVisibility(View.INVISIBLE);
            } else {
                textViewNbDrones.setText("" + listDrone.length);
                ArrayAdapter<String> listitems = new ArrayAdapter<String>(ici, android.R.layout.simple_list_item_1, listDrone);
                list.setAdapter(listitems);
                list.setVisibility(View.VISIBLE);
                btnFly.setVisibility(View.VISIBLE);
            }

        }
    }

    //TODO Créer un bouton refresh pour relancer un scan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (GlobalCouple.couples == null) {
            GlobalCouple.couples = new ArrayList<Couple>();
            new ServerUDP(getApplicationContext());
        }

        list = (ListView) findViewById(R.id.listViewConnectedDrones);
        btnFly = (Button) findViewById(R.id.btnFly);
        btnFly.setVisibility(View.INVISIBLE);
        Button btnABout = (Button) findViewById(R.id.btnAbout);
        Button btnNotice = (Button) findViewById(R.id.btnNotice);
        textViewNbDrones = (TextView) findViewById(R.id.textViewNbDrones);
        btnRefresh = (ImageButton) findViewById(R.id.BtnMainActivityRefresh);

        ShowDroneList();

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
                    if(droneSelected !=-1){

                        DroneDetailsActivity.putExtra(MessageKEY.NAME, GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDroneName())
                        .putExtra("Battery", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getBattery())
                        .putExtra("HardVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getHardwareVersion())
                        .putExtra("SerialID", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSerialID())
                        .putExtra("SoftVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareVersion())
                        .putExtra("GPSVersion", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getSoftwareGPSVersion())
                        .putExtra("nbFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getNbFlights())
                        .putExtra("LastFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationLastFlight())
                        .putExtra("TotalFlight", GlobalCouple.couples.get(droneSelected).getBebopDrone().getInfoDrone().getDurationTotalFlights());


                    }else{
                        DroneDetailsActivity.putExtra("Name", "null");
                    }

                    startActivity(DroneDetailsActivity);
                }

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DiscoveryDrone(getApplicationContext(), handler);
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
        new DiscoveryDrone(getApplicationContext(), handler);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        byte[] buf_send = "Deconnexion\n".getBytes(Charset.forName("UTF-8"));
        DatagramPacket envoi = new DatagramPacket(buf_send, buf_send.length);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(ServerUDP.port);
            for (int i = 0;i < GlobalCouple.couples.size();i++){
                if(GlobalCouple.couples.get(i).getRaspberry() != null){
                    envoi.setAddress(GlobalCouple.couples.get(i).getRaspberry().getAddress());
                    envoi.setPort(GlobalCouple.couples.get(i).getRaspberry().getPort());
                    socket.send(envoi);
                    GlobalCouple.couples.get(i).setRaspberry(null);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

