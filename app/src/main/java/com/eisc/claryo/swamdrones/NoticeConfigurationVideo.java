package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by jolwarden on 02/03/17.
 */

public class NoticeConfigurationVideo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_config_video);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBackNoticeConfigVideo);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeConfigurationVideo.this.finish();
                Intent Notice = new Intent(NoticeConfigurationVideo.this, Notice.class);
                startActivity(Notice);
            }
        });
    }
}
