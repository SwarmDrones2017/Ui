package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by Claryo on 23/02/2017.
 */

public class ProxyBars extends AppCompatActivity {

    public ProxyBars(Context context, String name){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        lp.leftMargin = x;
        lp.topMargin =  y;

        Random r = new Random();
        int PlageX = r.nextInt(633);
        int PlageY = r.nextInt(360);
//TODO voir pourquoi layoutParam=null
        ImageView Drone = new ImageView(context);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.ic_drone);
        Drone.setLayoutParams(params);
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        ImageView ProxJauLeft = new ImageView(context);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 10);
        ProxJauLeft.setY(Ydrone + 5);
        ProxJauLeft.setLayoutParams().height = 40;
        ProxJauLeft.setLayoutParams().width = 4;
        ImageView ProxOrLeft = new ImageView(context);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 5);
        ProxOrLeft.setY(Ydrone + 5);
        ProxOrLeft.setLayoutParams().height = 40;
        ProxOrLeft.setLayoutParams().width = 4;
        ImageView ProxRedLeft = new ImageView(context);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone);
        ProxRedLeft.setY(Ydrone + 5);
        ProxRedLeft.setLayoutParams().height = 40;
        ProxRedLeft.setLayoutParams().width = 4;

        ImageView ProxJauRight = new ImageView(context);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 55);
        ProxJauRight.setY(Ydrone + 5);
        ProxJauRight.setLayoutParams().height = 40;
        ProxJauRight.setLayoutParams().width = 4;
        ImageView ProxOrRight = new ImageView(context);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 50);
        ProxOrRight.setY(Ydrone + 5);
        ProxOrRight.setLayoutParams().height = 40;
        ProxOrRight.setLayoutParams().width = 4;
        ImageView ProxRedRight = new ImageView(context);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 45);
        ProxRedRight.setY(Ydrone + 5);
        ProxRedRight.setLayoutParams().height = 40;
        ProxRedRight.setLayoutParams().width = 4;

        ImageView ProxJauTop = new ImageView(context);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 5);
        ProxJauTop.setY(Ydrone - 10);
        ProxJauTop.setLayoutParams().height = 4;
        ProxJauTop.setLayoutParams().width = 40;
        ImageView ProxOrTop = new ImageView(context);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 5);
        ProxOrTop.setY(Ydrone - 5);
        ProxOrTop.setLayoutParams().height = 4;
        ProxOrTop.setLayoutParams().width = 40;
        ImageView ProxRedTop = new ImageView(context);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 5);
        ProxRedTop.setY(Ydrone);
        ProxRedTop.setLayoutParams().height = 4;
        ProxRedTop.setLayoutParams().width = 40;

        ImageView ProxJauBot = new ImageView(context);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 5);
        ProxJauBot.setY(Ydrone + 55);
        ProxJauBot.setLayoutParams().height = 4;
        ProxJauBot.setLayoutParams().width = 40;
        ImageView ProxOrBot = new ImageView(context);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 5);
        ProxOrBot.setY(Ydrone + 50);
        ProxOrBot.setLayoutParams().height = 4;
        ProxOrBot.setLayoutParams().width = 40;
        ImageView ProxRedBot = new ImageView(context);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 5);
        ProxRedBot.setY(Ydrone + 45);
        ProxRedBot.setLayoutParams().height = 4;
        ProxRedBot.setLayoutParams().width = 40;

        ImageView ProxJauUp = new ImageView(context);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10);
        ProxJauUp.setY(Ydrone - 4);
        ProxJauUp.setLayoutParams().height = 30;
        ProxJauUp.setLayoutParams().width = 30;
        ImageView ProxOrUp = new ImageView(context);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10);
        ProxOrUp.setY(Ydrone);
        ProxOrUp.setLayoutParams().height = 30;
        ProxOrUp.setLayoutParams().width = 30;
        ImageView ProxRedUp = new ImageView(context);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10);
        ProxRedUp.setY(Ydrone + 4);
        ProxRedUp.setLayoutParams().height = 30;
        ProxRedUp.setLayoutParams().width = 30;

        ImageView ProxJauDown = new ImageView(context);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10);
        ProxJauDown.setY(Ydrone + 26);
        ProxJauDown.setLayoutParams().height = 30;
        ProxJauDown.setLayoutParams().width = 30;
        ImageView ProxOrDown = new ImageView(context);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10);
        ProxOrDown.setY(Ydrone + 22);
        ProxOrDown.setLayoutParams().height = 30;
        ProxOrDown.setLayoutParams().width = 30;
        ImageView ProxRedDown = new ImageView(context);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10);
        ProxRedDown.setY(Ydrone + 18);
        ProxRedDown.setLayoutParams().height = 30;
        ProxRedDown.setLayoutParams().width = 30;

    }
}
