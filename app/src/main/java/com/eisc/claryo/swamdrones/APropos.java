package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * A propos de l'application SwarmDrones
 */

public class APropos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_propos);

        ImageButton btnBackAbout = (ImageButton) findViewById(R.id.btnBackAbout);

        btnBackAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APropos.this.finish();
                Intent MainActivity= new Intent(APropos.this, MainActivity.class);
                startActivity(MainActivity);
            }
        });
    }
}
