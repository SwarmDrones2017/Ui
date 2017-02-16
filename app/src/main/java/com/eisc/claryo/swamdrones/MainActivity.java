package com.eisc.claryo.swamdrones;

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
import android.widget.ListView;
import android.widget.TextView;


import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import static com.eisc.claryo.swamdrones.MessageHandler.LISTDRONEUPDATE;
import static com.eisc.claryo.swamdrones.MessageHandler.NOTDRONE;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_ANY_DRONES = "Aucun";
    public static DroneListeConnecte[] items;
    private ArrayAdapter<String> adapter;
    private ListView list;
    private TextView textViewNbDrones;
    private MainActivity ici = this;
    static private String[] listDrone;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            listDrone = msg.getData().getStringArray(LISTDRONEUPDATE);
            ShowDroneList();
        }
    };

    /**
     * @author : sofiane :p
     */
    private void ShowDroneList() {
        if (listDrone != null) {
            if (listDrone[0].equals(MSG_ANY_DRONES)) {
                textViewNbDrones.setText(MSG_ANY_DRONES);
                list.setVisibility(View.INVISIBLE);
            } else {
                textViewNbDrones.setText("" + listDrone.length);
                ArrayAdapter<String> listitems = new ArrayAdapter<String>(ici, android.R.layout.simple_list_item_1, listDrone);
                list.setAdapter(listitems);
                list.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (GlobalCouple.couples == null) {
            GlobalCouple.couples = new ArrayList<Couple>();
            new ServerUDP(getApplicationContext());
        }

        list = (ListView) findViewById(R.id.listViewConnectedDrones);
        Button btnFly = (Button) findViewById(R.id.btnFly);
        textViewNbDrones = (TextView) findViewById(R.id.textViewNbDrones);

        ShowDroneList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        Intent DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
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

        new DiscoveryDrone(getApplicationContext(), handler);
    }
}

