package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static com.eisc.claryo.swamdrones.MessageHandler.LISTDRONEUPDATE;
import static com.eisc.claryo.swamdrones.MessageHandler.NOTDRONE;

public class MainActivity extends AppCompatActivity {
/*
    public static DroneListeConnecte[] items = {
            new DroneListeConnecte("Bebop_648992", "Connected"),
            new DroneListeConnecte("Bebop_615482", "Connected"),
            new DroneListeConnecte("Bebop_026482", "Connected"),
            new DroneListeConnecte("Bebop_528462", "Connected"),
            new DroneListeConnecte("Bebop_645283", "Connected"),
    };
*/


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //String msgNULL = msg.getData().getString(NOTDRONE);
            String[] listDrone = msg.getData().getStringArray(LISTDRONEUPDATE);
            if (listDrone != null) {
                //items = listDrone;

                DroneListeConnecte[] items = new DroneListeConnecte[listDrone.length];

                for (int i = 0; i < listDrone.length; i++) {
                    items[i] = new DroneListeConnecte(listDrone[i], "En cours");
                }

            }
            ArrayAdapter<DroneListeConnecte> adapt = new ArrayAdapter<DroneListeConnecte>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
            list.setAdapter(adapt);
        }
    };

    public static DroneListeConnecte[] items;

    private ArrayAdapter<DroneListeConnecte> adapter;
    private ListView list;
    public static byte[] test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = new byte[1];

        //new ServerUDP(getApplicationContext());

        new DiscoveryDrone(getApplicationContext(), handler);
        list = (ListView) findViewById(R.id.listViewConnectedDrones);
        Button btnFly = (Button) findViewById(R.id.btnFly);
        //adapter = new ArrayAdapter<DroneListeConnecte>();
        if (items == null) {
            items = new DroneListeConnecte[1];
            if (items != null)
                items[0] = new DroneListeConnecte("Aucun drone connecter", "Activer le wifi");
        }

        adapter = new ArrayAdapter<DroneListeConnecte>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 1:
                        DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 2:
                        DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 3:
                        DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 4:
                        DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;


                }

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {
            }
        });


        btnFly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ControlActivity = new Intent(MainActivity.this, Control.class);
                startActivity(ControlActivity);
            }
        });

    }
}

