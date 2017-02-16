package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static DroneListeConnecte[] items = {
            new DroneListeConnecte("Bebop_648992", "Connected"),
            new DroneListeConnecte("Bebop_615482", "Connected"),
            new DroneListeConnecte("Bebop_026482", "Connected"),
            new DroneListeConnecte("Bebop_528462", "Connected"),
            new DroneListeConnecte("Bebop_645283", "Connected"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.listViewConnectedDrones);
        Button btnFly = (Button) findViewById(R.id.btnFly);
        Button btnABout = (Button) findViewById(R.id.btnAbout);
        Button btnNotice = (Button) findViewById(R.id.btnNotice);


        ArrayAdapter<DroneListeConnecte> adapter = new ArrayAdapter<DroneListeConnecte>(this, android.R.layout.simple_list_item_1, items);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:  Intent DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 1:  DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 2:  DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 3:  DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;
                    case 4:  DroneDetailsActivity = new Intent(MainActivity.this, DroneDetails.class);
                        startActivity(DroneDetailsActivity);
                        break;


                }

            }
            @SuppressWarnings("unused")
            public void onClick(View v){
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
    }
}
