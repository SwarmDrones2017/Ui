package com.eisc.claryo.swamdrones;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Claryo on 15/02/2017.
 */

public class Notice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);

        ImageButton btnBackNotice = (ImageButton) findViewById(R.id.btnBackNotice);


        btnBackNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notice.this.finish();
                Intent MainActivity = new Intent(Notice.this, MainActivity.class);
                MainActivity.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(MainActivity);
            }
        });


    }

}
