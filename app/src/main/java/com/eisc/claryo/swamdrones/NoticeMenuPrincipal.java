package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Classe présentant la notice
 * Menu principal
 */

public class NoticeMenuPrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_menu_principal);

        ImageButton btnBack = (ImageButton)findViewById(R.id.btnBackNoticeMenuPrincipal);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeMenuPrincipal.this.finish();
                Intent Notice = new Intent(NoticeMenuPrincipal.this, Notice.class);
                startActivity(Notice);
            }
        });
    }
}
