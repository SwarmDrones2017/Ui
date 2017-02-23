package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Claryo on 23/02/2017.
 */

public class ProxyBars extends AppCompatActivity {

    public ProxyBars(Context context, String name){

        Random r = new Random();
        int PlageX = r.nextInt(633);
        int PlageY = r.nextInt(360);
//TODO voir pourquoi layoutParam=null
        ImageView Drone = new ImageView(context);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.ic_drone);
        Drone.getLayoutParams().height = 50;
        Drone.getLayoutParams().width = 50;
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        ImageView ProxJauLeft = new ImageView(context);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 10);
        ProxJauLeft.setY(Ydrone + 5);
        ProxJauLeft.getLayoutParams().height = 40;
        ProxJauLeft.getLayoutParams().width = 4;
        ImageView ProxOrLeft = new ImageView(context);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 5);
        ProxOrLeft.setY(Ydrone + 5);
        ProxOrLeft.getLayoutParams().height = 40;
        ProxOrLeft.getLayoutParams().width = 4;
        ImageView ProxRedLeft = new ImageView(context);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone);
        ProxRedLeft.setY(Ydrone + 5);
        ProxRedLeft.getLayoutParams().height = 40;
        ProxRedLeft.getLayoutParams().width = 4;

        ImageView ProxJauRight = new ImageView(context);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 55);
        ProxJauRight.setY(Ydrone + 5);
        ProxJauRight.getLayoutParams().height = 40;
        ProxJauRight.getLayoutParams().width = 4;
        ImageView ProxOrRight = new ImageView(context);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 50);
        ProxOrRight.setY(Ydrone + 5);
        ProxOrRight.getLayoutParams().height = 40;
        ProxOrRight.getLayoutParams().width = 4;
        ImageView ProxRedRight = new ImageView(context);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 45);
        ProxRedRight.setY(Ydrone + 5);
        ProxRedRight.getLayoutParams().height = 40;
        ProxRedRight.getLayoutParams().width = 4;

        ImageView ProxJauTop = new ImageView(context);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 5);
        ProxJauTop.setY(Ydrone - 10);
        ProxJauTop.getLayoutParams().height = 4;
        ProxJauTop.getLayoutParams().width = 40;
        ImageView ProxOrTop = new ImageView(context);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 5);
        ProxOrTop.setY(Ydrone - 5);
        ProxOrTop.getLayoutParams().height = 4;
        ProxOrTop.getLayoutParams().width = 40;
        ImageView ProxRedTop = new ImageView(context);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 5);
        ProxRedTop.setY(Ydrone);
        ProxRedTop.getLayoutParams().height = 4;
        ProxRedTop.getLayoutParams().width = 40;

        ImageView ProxJauBot = new ImageView(context);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 5);
        ProxJauBot.setY(Ydrone + 55);
        ProxJauBot.getLayoutParams().height = 4;
        ProxJauBot.getLayoutParams().width = 40;
        ImageView ProxOrBot = new ImageView(context);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 5);
        ProxOrBot.setY(Ydrone + 50);
        ProxOrBot.getLayoutParams().height = 4;
        ProxOrBot.getLayoutParams().width = 40;
        ImageView ProxRedBot = new ImageView(context);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 5);
        ProxRedBot.setY(Ydrone + 45);
        ProxRedBot.getLayoutParams().height = 4;
        ProxRedBot.getLayoutParams().width = 40;

        ImageView ProxJauUp = new ImageView(context);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10);
        ProxJauUp.setY(Ydrone - 4);
        ProxJauUp.getLayoutParams().height = 30;
        ProxJauUp.getLayoutParams().width = 30;
        ImageView ProxOrUp = new ImageView(context);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10);
        ProxOrUp.setY(Ydrone);
        ProxOrUp.getLayoutParams().height = 30;
        ProxOrUp.getLayoutParams().width = 30;
        ImageView ProxRedUp = new ImageView(context);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10);
        ProxRedUp.setY(Ydrone + 4);
        ProxRedUp.getLayoutParams().height = 30;
        ProxRedUp.getLayoutParams().width = 30;

        ImageView ProxJauDown = new ImageView(context);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10);
        ProxJauDown.setY(Ydrone + 26);
        ProxJauDown.getLayoutParams().height = 30;
        ProxJauDown.getLayoutParams().width = 30;
        ImageView ProxOrDown = new ImageView(context);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10);
        ProxOrDown.setY(Ydrone + 22);
        ProxOrDown.getLayoutParams().height = 30;
        ProxOrDown.getLayoutParams().width = 30;
        ImageView ProxRedDown = new ImageView(context);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10);
        ProxRedDown.setY(Ydrone + 18);
        ProxRedDown.getLayoutParams().height = 30;
        ProxRedDown.getLayoutParams().width = 30;

    }
}
