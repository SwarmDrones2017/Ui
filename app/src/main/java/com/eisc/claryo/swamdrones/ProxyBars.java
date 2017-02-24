package com.eisc.claryo.swamdrones;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by Claryo on 23/02/2017.
 */

public class ProxyBars extends AppCompatActivity {

    public ProxyBars(){

        Random r = new Random();
        int PlageX = r.nextInt(633);
        int PlageY = r.nextInt(360);

        ImageView Drone = new ImageView(this);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.ic_drone);
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        RelativeLayout.LayoutParams lpDrone = (RelativeLayout.LayoutParams)Drone.getLayoutParams();
        lpDrone.width = 50;
        lpDrone.height = 50;
        Drone.setLayoutParams(lpDrone);

        ImageView ProxJauLeft = new ImageView(this);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 10);
        ProxJauLeft.setY(Ydrone + 5);
        ImageView ProxOrLeft = new ImageView(this);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 5);
        ProxOrLeft.setY(Ydrone + 5);
        ImageView ProxRedLeft = new ImageView(this);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone);
        ProxRedLeft.setY(Ydrone + 5);

        ImageView ProxJauRight = new ImageView(this);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 55);
        ProxJauRight.setY(Ydrone + 5);
        ImageView ProxOrRight = new ImageView(this);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 50);
        ProxOrRight.setY(Ydrone + 5);
        ImageView ProxRedRight = new ImageView(this);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 45);
        ProxRedRight.setY(Ydrone + 5);

        ImageView ProxJauTop = new ImageView(this);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 5);
        ProxJauTop.setY(Ydrone - 10);
        ImageView ProxOrTop = new ImageView(this);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 5);
        ProxOrTop.setY(Ydrone - 5);
        ImageView ProxRedTop = new ImageView(this);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 5);
        ProxRedTop.setY(Ydrone);

        ImageView ProxJauBot = new ImageView(this);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 5);
        ProxJauBot.setY(Ydrone + 55);
        ImageView ProxOrBot = new ImageView(this);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 5);
        ProxOrBot.setY(Ydrone + 50);
        ImageView ProxRedBot = new ImageView(this);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 5);
        ProxRedBot.setY(Ydrone + 45);

        ImageView ProxJauUp = new ImageView(this);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10);
        ProxJauUp.setY(Ydrone - 4);
        ImageView ProxOrUp = new ImageView(this);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10);
        ProxOrUp.setY(Ydrone);
        ImageView ProxRedUp = new ImageView(this);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10);
        ProxRedUp.setY(Ydrone + 4);

        ImageView ProxJauDown = new ImageView(this);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10);
        ProxJauDown.setY(Ydrone + 26);
        ImageView ProxOrDown = new ImageView(this);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10);
        ProxOrDown.setY(Ydrone + 22);
        ImageView ProxRedDown = new ImageView(this);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10);
        ProxRedDown.setY(Ydrone + 18);

        RelativeLayout.LayoutParams lpBarH = (RelativeLayout.LayoutParams)ProxJauBot.getLayoutParams();
        lpBarH.width = 40;
        lpBarH.height = 4;

        ProxJauBot.setLayoutParams(lpBarH);
        ProxOrBot.setLayoutParams(lpBarH);
        ProxRedBot.setLayoutParams(lpBarH);
        ProxJauTop.setLayoutParams(lpBarH);
        ProxOrTop.setLayoutParams(lpBarH);
        ProxRedTop.setLayoutParams(lpBarH);

        RelativeLayout.LayoutParams lpBarV = (RelativeLayout.LayoutParams)ProxJauRight.getLayoutParams();
        lpBarV.width = 4;
        lpBarV.height = 40;

        ProxJauRight.setLayoutParams(lpBarV);
        ProxOrRight.setLayoutParams(lpBarV);
        ProxRedRight.setLayoutParams(lpBarV);
        ProxJauLeft.setLayoutParams(lpBarV);
        ProxOrLeft.setLayoutParams(lpBarV);
        ProxRedLeft.setLayoutParams(lpBarV);

        RelativeLayout.LayoutParams lpBarUD = (RelativeLayout.LayoutParams)ProxJauUp.getLayoutParams();
        lpBarUD.width = 30;
        lpBarUD.height = 30;

        ProxJauUp.setLayoutParams(lpBarUD);
        ProxOrUp.setLayoutParams(lpBarUD);
        ProxRedUp.setLayoutParams(lpBarUD);
        ProxJauDown.setLayoutParams(lpBarUD);
        ProxOrDown.setLayoutParams(lpBarUD);
        ProxRedDown.setLayoutParams(lpBarUD);
    }
}
