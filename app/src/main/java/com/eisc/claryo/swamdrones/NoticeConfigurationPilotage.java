package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Classe présentant la notice
 * Config pilotage
 */

public class NoticeConfigurationPilotage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notice_configuration_pilotage);

        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBackNoticeConfigPilotage);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeConfigurationPilotage.this.finish();
                Intent Notice = new Intent(NoticeConfigurationPilotage.this, Notice.class);
                startActivity(Notice);
            }
        });
    }
}
