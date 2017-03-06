package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Classe présentant la notice
 * Essaim View
 */

public class NoticeEssaimView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_essaim_view);
        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBackNoticeEssaimView);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeEssaimView.this.finish();
                Intent Notice = new Intent(NoticeEssaimView.this, Notice.class);
                startActivity(Notice);
            }
        });
    }
}
