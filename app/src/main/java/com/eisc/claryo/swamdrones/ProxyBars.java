package com.eisc.claryo.swamdrones;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by Claryo on 23/02/2017.
 */

public class ProxyBars extends AppCompatActivity {

    public ProxyBars(Context context){

        AbsoluteLayout Ecran = new AbsoluteLayout(context);

        FrameLayout.LayoutParams lpDrone = new FrameLayout.LayoutParams(50, 50);
        FrameLayout.LayoutParams lpBarH = new FrameLayout.LayoutParams(40, 4);
        FrameLayout.LayoutParams lpBarV = new FrameLayout.LayoutParams(4, 40);
        FrameLayout.LayoutParams lpBarUD = new FrameLayout.LayoutParams(30, 30);

        Random r = new Random();
        int PlageX = r.nextInt(633);
        int PlageY = r.nextInt(360);

        ImageView Drone = new ImageView(context);
        Drone.setLayoutParams(lpDrone);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.ic_drone);
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        ImageView ProxJauLeft = new ImageView(context);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 10);
        ProxJauLeft.setY(Ydrone + 5);
        ImageView ProxOrLeft = new ImageView(context);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 5);
        ProxOrLeft.setY(Ydrone + 5);
        ImageView ProxRedLeft = new ImageView(context);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone);
        ProxRedLeft.setY(Ydrone + 5);

        ImageView ProxJauRight = new ImageView(context);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 55);
        ProxJauRight.setY(Ydrone + 5);
        ImageView ProxOrRight = new ImageView(context);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 50);
        ProxOrRight.setY(Ydrone + 5);
        ImageView ProxRedRight = new ImageView(context);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 45);
        ProxRedRight.setY(Ydrone + 5);

        ImageView ProxJauTop = new ImageView(context);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 5);
        ProxJauTop.setY(Ydrone - 10);
        ImageView ProxOrTop = new ImageView(context);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 5);
        ProxOrTop.setY(Ydrone - 5);
        ImageView ProxRedTop = new ImageView(context);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 5);
        ProxRedTop.setY(Ydrone);

        ImageView ProxJauBot = new ImageView(context);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 5);
        ProxJauBot.setY(Ydrone + 55);
        ImageView ProxOrBot = new ImageView(context);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 5);
        ProxOrBot.setY(Ydrone + 50);
        ImageView ProxRedBot = new ImageView(context);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 5);
        ProxRedBot.setY(Ydrone + 45);

        ImageView ProxJauUp = new ImageView(context);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10);
        ProxJauUp.setY(Ydrone - 4);
        ImageView ProxOrUp = new ImageView(context);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10);
        ProxOrUp.setY(Ydrone);
        ImageView ProxRedUp = new ImageView(context);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10);
        ProxRedUp.setY(Ydrone + 4);

        ImageView ProxJauDown = new ImageView(context);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10);
        ProxJauDown.setY(Ydrone + 26);
        ImageView ProxOrDown = new ImageView(context);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10);
        ProxOrDown.setY(Ydrone + 22);
        ImageView ProxRedDown = new ImageView(context);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10);
        ProxRedDown.setY(Ydrone + 18);

        ProxJauBot.setLayoutParams(lpBarH);
        ProxOrBot.setLayoutParams(lpBarH);
        ProxRedBot.setLayoutParams(lpBarH);
        ProxJauTop.setLayoutParams(lpBarH);
        ProxOrTop.setLayoutParams(lpBarH);
        ProxRedTop.setLayoutParams(lpBarH);

        ProxJauRight.setLayoutParams(lpBarV);
        ProxOrRight.setLayoutParams(lpBarV);
        ProxRedRight.setLayoutParams(lpBarV);
        ProxJauLeft.setLayoutParams(lpBarV);
        ProxOrLeft.setLayoutParams(lpBarV);
        ProxRedLeft.setLayoutParams(lpBarV);

        ProxJauUp.setLayoutParams(lpBarUD);
        ProxOrUp.setLayoutParams(lpBarUD);
        ProxRedUp.setLayoutParams(lpBarUD);
        ProxJauDown.setLayoutParams(lpBarUD);
        ProxOrDown.setLayoutParams(lpBarUD);
        ProxRedDown.setLayoutParams(lpBarUD);

        Ecran.addView(Drone);
        Log.i("ContexteD", ""+Drone.getContext());
        Log.i("ContexteE", ""+Ecran.getContext());
    }
}
