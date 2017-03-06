package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Classe présentant la notice
 * Page des chapitres
 */

public class Notice extends AppCompatActivity {

    /**
     * Création de l'interface
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);

        ImageButton btnBackNotice = (ImageButton) findViewById(R.id.btnBackNotice);
        ListView listView = (ListView) findViewById(R.id.menuNotice);
        String[] menus = new String[]{"Menu Principal", "Détails des drones", "Contrôler les drones", "Vue globale de l'essaim", "Configuration de l'essaim", "Paramètres de pilotage", "Configuration vidéo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Notice.this, android.R.layout.simple_list_item_1, menus);
        listView.setAdapter(adapter);

        /**
         * Gestion du changement d'activité
         */
        btnBackNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notice.this.finish();
                Intent MainActivity = new Intent(Notice.this, MainActivity.class);
                startActivity(MainActivity);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent NoticeMenuPrincipal = new Intent(Notice.this, NoticeMenuPrincipal.class);
                        Notice.this.finish();

                        startActivity(NoticeMenuPrincipal);
                        break;
                    case 1:
                        Intent NoticeDetailsDrone = new Intent(Notice.this, NoticeDetailsDrone.class);
                        Notice.this.finish();
                        startActivity(NoticeDetailsDrone);
                        break;
                    case 2:
                        Intent NoticeControlDrone = new Intent(Notice.this, NoticeControlDrone.class);
                        Notice.this.finish();
                        startActivity(NoticeControlDrone);
                        break;
                    case 3:
                        Intent NoticeEssaimView = new Intent(Notice.this, NoticeEssaimView.class);
                        Notice.this.finish();
                        startActivity(NoticeEssaimView);
                        break;
                    case 4:
                        Intent NoticeConfigurationDrone = new Intent(Notice.this, NoticeConfigurationDrone.class);
                        Notice.this.finish();
                        startActivity(NoticeConfigurationDrone);
                        break;
                    case 5:
                        Intent NoticeConfigurationPilotage = new Intent(Notice.this, NoticeConfigurationPilotage.class);
                        Notice.this.finish();
                        startActivity(NoticeConfigurationPilotage);
                        break;
                    case 6:
                        Intent NoticeConfigurationVideo = new Intent(Notice.this, NoticeConfigurationVideo.class);
                        Notice.this.finish();
                        startActivity(NoticeConfigurationVideo);
                        break;
                }
            }
        });
    }
}