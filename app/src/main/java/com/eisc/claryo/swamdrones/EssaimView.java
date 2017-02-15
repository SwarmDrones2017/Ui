package com.eisc.claryo.swamdrones;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Classe permettant de voir la formation de l'essaim
 */

public class EssaimView extends AppCompatActivity {

    ImageView Drone1, D1ProxRedBot, D1ProxOrBot, D1ProxJauBot, D1ProxRedTop, D1ProxOrTop, D1ProxJauTop, D1ProxRedRight, D1ProxOrRight, D1ProxJauRight, D1ProxRedLeft, D1ProxOrLeft, D1ProxJauLeft,
            D1ProxRedUp, D1ProxOrUp, D1ProxJauUp, D1ProxRedDown, D1ProxOrDown, D1ProxJauDown;
    ImageView Drone2, D2ProxRedBot, D2ProxOrBot, D2ProxJauBot, D2ProxRedTop, D2ProxOrTop, D2ProxJauTop, D2ProxRedRight, D2ProxOrRight, D2ProxJauRight, D2ProxRedLeft, D2ProxOrLeft, D2ProxJauLeft,
            D2ProxRedUp, D2ProxOrUp, D2ProxJauUp, D2ProxRedDown, D2ProxOrDown, D2ProxJauDown;
    ImageView Drone3, D3ProxRedBot, D3ProxOrBot, D3ProxJauBot, D3ProxRedTop, D3ProxOrTop, D3ProxJauTop, D3ProxRedRight, D3ProxOrRight, D3ProxJauRight, D3ProxRedLeft, D3ProxOrLeft, D3ProxJauLeft,
            D3ProxRedUp, D3ProxOrUp, D3ProxJauUp, D3ProxRedDown, D3ProxOrDown, D3ProxJauDown;
    float density;
    String densite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_view);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnBackSwapView);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettingsSwapView);

        Drone1 = (ImageView) findViewById(R.id.Drone1);
        D1ProxRedBot = (ImageView) findViewById(R.id.D1ProxRedBot);
        D1ProxOrBot = (ImageView) findViewById(R.id.D1ProxOrBot);
        D1ProxJauBot = (ImageView) findViewById(R.id.D1ProxJauBot);
        D1ProxRedTop = (ImageView) findViewById(R.id.D1ProxRedTop);
        D1ProxOrTop = (ImageView) findViewById(R.id.D1ProxOrTop);
        D1ProxJauTop = (ImageView) findViewById(R.id.D1ProxJauTop);
        D1ProxRedRight = (ImageView) findViewById(R.id.D1ProxRedRight);
        D1ProxOrRight = (ImageView) findViewById(R.id.D1ProxOrRight);
        D1ProxJauRight = (ImageView) findViewById(R.id.D1ProxJauRight);
        D1ProxRedLeft = (ImageView) findViewById(R.id.D1ProxRedLeft);
        D1ProxOrLeft = (ImageView) findViewById(R.id.D1ProxOrLeft);
        D1ProxJauLeft = (ImageView) findViewById(R.id.D1ProxJauLeft);
        D1ProxRedUp = (ImageView) findViewById(R.id.D1ProxRedUp);
        D1ProxOrUp = (ImageView) findViewById(R.id.D1ProxOrUp);
        D1ProxJauUp = (ImageView) findViewById(R.id.D1ProxJauUp);
        D1ProxRedDown = (ImageView) findViewById(R.id.D1ProxRedDown);
        D1ProxOrDown = (ImageView) findViewById(R.id.D1ProxOrDown);
        D1ProxJauDown = (ImageView) findViewById(R.id.D1ProxJauDown);

        Drone2 = (ImageView) findViewById(R.id.Drone2);
        D2ProxRedBot = (ImageView) findViewById(R.id.D2ProxRedBot);
        D2ProxOrBot = (ImageView) findViewById(R.id.D2ProxOrBot);
        D2ProxJauBot = (ImageView) findViewById(R.id.D2ProxJauBot);
        D2ProxRedTop = (ImageView) findViewById(R.id.D2ProxRedTop);
        D2ProxOrTop = (ImageView) findViewById(R.id.D2ProxOrTop);
        D2ProxJauTop = (ImageView) findViewById(R.id.D2ProxJauTop);
        D2ProxRedRight = (ImageView) findViewById(R.id.D2ProxRedRight);
        D2ProxOrRight = (ImageView) findViewById(R.id.D2ProxOrRight);
        D2ProxJauRight = (ImageView) findViewById(R.id.D2ProxJauRight);
        D2ProxRedLeft = (ImageView) findViewById(R.id.D2ProxRedLeft);
        D2ProxOrLeft = (ImageView) findViewById(R.id.D2ProxOrLeft);
        D2ProxJauLeft = (ImageView) findViewById(R.id.D2ProxJauLeft);
        D2ProxRedUp = (ImageView) findViewById(R.id.D2ProxRedUp);
        D2ProxOrUp = (ImageView) findViewById(R.id.D2ProxOrUp);
        D2ProxJauUp = (ImageView) findViewById(R.id.D2ProxJauUp);
        D2ProxRedDown = (ImageView) findViewById(R.id.D2ProxRedDown);
        D2ProxOrDown = (ImageView) findViewById(R.id.D2ProxOrDown);
        D2ProxJauDown = (ImageView) findViewById(R.id.D2ProxJauDown);

        Drone3 = (ImageView) findViewById(R.id.Drone3);
        D3ProxRedBot = (ImageView) findViewById(R.id.D3ProxRedBot);
        D3ProxOrBot = (ImageView) findViewById(R.id.D3ProxOrBot);
        D3ProxJauBot = (ImageView) findViewById(R.id.D3ProxJauBot);
        D3ProxRedTop = (ImageView) findViewById(R.id.D3ProxRedTop);
        D3ProxOrTop = (ImageView) findViewById(R.id.D3ProxOrTop);
        D3ProxJauTop = (ImageView) findViewById(R.id.D3ProxJauTop);
        D3ProxRedRight = (ImageView) findViewById(R.id.D3ProxRedRight);
        D3ProxOrRight = (ImageView) findViewById(R.id.D3ProxOrRight);
        D3ProxJauRight = (ImageView) findViewById(R.id.D3ProxJauRight);
        D3ProxRedLeft = (ImageView) findViewById(R.id.D3ProxRedLeft);
        D3ProxOrLeft = (ImageView) findViewById(R.id.D3ProxOrLeft);
        D3ProxJauLeft = (ImageView) findViewById(R.id.D3ProxJauLeft);
        D3ProxRedUp = (ImageView) findViewById(R.id.D3ProxRedUp);
        D3ProxOrUp = (ImageView) findViewById(R.id.D3ProxOrUp);
        D3ProxJauUp = (ImageView) findViewById(R.id.D3ProxJauUp);
        D3ProxRedDown = (ImageView) findViewById(R.id.D3ProxRedDown);
        D3ProxOrDown = (ImageView) findViewById(R.id.D3ProxOrDown);
        D3ProxJauDown = (ImageView) findViewById(R.id.D3ProxJauDown);

        Drone1.setOnTouchListener(new MyTouchListener1());
        Drone2.setOnTouchListener(new MyTouchListener2());
        Drone3.setOnTouchListener(new MyTouchListener3());
        findViewById(R.id.Ecran).setOnDragListener(new MyDragListener());

        density = getResources().getDisplayMetrics().density;
        densite = Float.toString(density);
        Toast.makeText(getApplicationContext(), densite, Toast.LENGTH_LONG).show();

        // return 0.75 if it's LDPI
        // return 1.0 if it's MDPI
        // return 1.5 if it's HDPI
        // return 2.0 if it's XHDPI
        // return 3.0 if it's XXHDPI
        // return 4.0 if it's XXXHDPI


        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimView.this.finish();
                Intent ControlActivity = new Intent(EssaimView.this, Control.class);
                startActivity(ControlActivity);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EssaimConfigActivity = new Intent(EssaimView.this, EssaimConfig.class);
                startActivity(EssaimConfigActivity);
            }
        });


    }

    private final class MyTouchListener1 implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone1", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class MyTouchListener2 implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone2", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class MyTouchListener3 implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("Drone3", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_dropout);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    float x = event.getX();
                    float y = event.getY();
                    System.out.println(Float.toString(x));
                    System.out.println(Float.toString(y));
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    AbsoluteLayout container = (AbsoluteLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    if (event.getClipData().getDescription().getLabel().equals("Drone1")) {
                        Drone1.setX(x - Drone1.getWidth() / 2);
                        Drone1.setY(y - Drone1.getHeight() / 2);
                        D1ProxRedBot.setX(x - D1ProxRedBot.getWidth() / 2);
                        D1ProxRedBot.setY(y + 46*density/2 - D1ProxRedBot.getHeight() / 2);
                        D1ProxOrBot.setX(x - D1ProxOrBot.getWidth() / 2);
                        D1ProxOrBot.setY(y + 56*density/2 - D1ProxOrBot.getHeight() / 2);
                        D1ProxJauBot.setX(x - D1ProxJauBot.getWidth() / 2);
                        D1ProxJauBot.setY(y + 66*density/2 - D1ProxJauBot.getHeight() / 2);
                        D1ProxRedTop.setX(x - D1ProxRedTop.getWidth() / 2);
                        D1ProxRedTop.setY(y - 50*density/2 - D1ProxRedTop.getHeight() / 2);
                        D1ProxOrTop.setX(x - D1ProxOrTop.getWidth() / 2);
                        D1ProxOrTop.setY(y - 60*density/2 - D1ProxOrTop.getHeight() / 2);
                        D1ProxJauTop.setX(x - D1ProxJauTop.getWidth() / 2);
                        D1ProxJauTop.setY(y - 70*density/2 - D1ProxJauTop.getHeight() / 2);
                        D1ProxRedRight.setX(x + 45*density/2 - D1ProxRedRight.getWidth() / 2);
                        D1ProxRedRight.setY(y - D1ProxRedRight.getHeight() / 2);
                        D1ProxOrRight.setX(x + 55*density/2 - D1ProxOrRight.getWidth() / 2);
                        D1ProxOrRight.setY(y - D1ProxOrRight.getHeight() / 2);
                        D1ProxJauRight.setX(x + 65*density/2 - D1ProxJauRight.getWidth() / 2);
                        D1ProxJauRight.setY(y - D1ProxJauRight.getHeight() / 2);
                        D1ProxRedLeft.setX(x - 50*density/2 - D1ProxRedLeft.getWidth() / 2);
                        D1ProxRedLeft.setY(y - D1ProxRedLeft.getHeight() / 2);
                        D1ProxOrLeft.setX(x - 60*density/2 - D1ProxOrLeft.getWidth() / 2);
                        D1ProxOrLeft.setY(y - D1ProxOrLeft.getHeight() / 2);
                        D1ProxJauLeft.setX(x - 70*density/2 - D1ProxJauLeft.getWidth() / 2);
                        D1ProxJauLeft.setY(y - D1ProxJauLeft.getHeight() / 2);
                        D1ProxRedUp.setX(x - D1ProxRedUp.getWidth() / 2);
                        D1ProxRedUp.setY(y - 11*density/2 - D1ProxRedUp.getHeight() / 2);
                        D1ProxOrUp.setX(x - D1ProxOrUp.getWidth() / 2);
                        D1ProxOrUp.setY(y - 20*density/2 - D1ProxOrUp.getHeight() / 2);
                        D1ProxJauUp.setX(x - D1ProxJauUp.getWidth() / 2);
                        D1ProxJauUp.setY(y - 29*density/2 - D1ProxJauUp.getHeight() / 2);
                        D1ProxRedDown.setX(x - D1ProxRedUp.getWidth() / 2);
                        D1ProxRedDown.setY(y + 18*density/2 - D1ProxRedUp.getHeight() / 2);
                        D1ProxOrDown.setX(x - D1ProxOrUp.getWidth() / 2);
                        D1ProxOrDown.setY(y + 27*density/2 - D1ProxOrUp.getHeight() / 2);
                        D1ProxJauDown.setX(x - D1ProxJauUp.getWidth() / 2);
                        D1ProxJauDown.setY(y + 34*density/2 - D1ProxJauUp.getHeight() / 2);
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone2")) {
                        Drone2.setX(x - Drone2.getWidth() / 2);
                        Drone2.setY(y - Drone2.getHeight() / 2);
                        D2ProxRedBot.setX(x - D2ProxRedBot.getWidth() / 2);
                        D2ProxRedBot.setY(y + 46*density/2 - D2ProxRedBot.getHeight() / 2);
                        D2ProxOrBot.setX(x - D2ProxOrBot.getWidth() / 2);
                        D2ProxOrBot.setY(y + 56*density/2 - D2ProxOrBot.getHeight() / 2);
                        D2ProxJauBot.setX(x - D2ProxJauBot.getWidth() / 2);
                        D2ProxJauBot.setY(y + 66*density/2 - D2ProxJauBot.getHeight() / 2);
                        D2ProxRedTop.setX(x - D2ProxRedTop.getWidth() / 2);
                        D2ProxRedTop.setY(y - 50*density/2 - D2ProxRedTop.getHeight() / 2);
                        D2ProxOrTop.setX(x - D2ProxOrTop.getWidth() / 2);
                        D2ProxOrTop.setY(y - 60*density/2 - D2ProxOrTop.getHeight() / 2);
                        D2ProxJauTop.setX(x - D2ProxJauTop.getWidth() / 2);
                        D2ProxJauTop.setY(y - 70*density/2 - D2ProxJauTop.getHeight() / 2);
                        D2ProxRedRight.setX(x + 45*density/2 - D2ProxRedRight.getWidth() / 2);
                        D2ProxRedRight.setY(y - D2ProxRedRight.getHeight() / 2);
                        D2ProxOrRight.setX(x + 55*density/2 - D2ProxOrRight.getWidth() / 2);
                        D2ProxOrRight.setY(y - D2ProxOrRight.getHeight() / 2);
                        D2ProxJauRight.setX(x + 65*density/2 - D2ProxJauRight.getWidth() / 2);
                        D2ProxJauRight.setY(y - D2ProxJauRight.getHeight() / 2);
                        D2ProxRedLeft.setX(x - 50*density/2 - D2ProxRedLeft.getWidth() / 2);
                        D2ProxRedLeft.setY(y - D2ProxRedLeft.getHeight() / 2);
                        D2ProxOrLeft.setX(x - 60*density/2 - D2ProxOrLeft.getWidth() / 2);
                        D2ProxOrLeft.setY(y - D2ProxOrLeft.getHeight() / 2);
                        D2ProxJauLeft.setX(x - 70*density/2 - D2ProxJauLeft.getWidth() / 2);
                        D2ProxJauLeft.setY(y - D2ProxJauLeft.getHeight() / 2);
                        D2ProxRedUp.setX(x - D2ProxRedUp.getWidth() / 2);
                        D2ProxRedUp.setY(y - 11*density/2 - D2ProxRedUp.getHeight() / 2);
                        D2ProxOrUp.setX(x - D2ProxOrUp.getWidth() / 2);
                        D2ProxOrUp.setY(y - 20*density/2 - D2ProxOrUp.getHeight() / 2);
                        D2ProxJauUp.setX(x - D2ProxJauUp.getWidth() / 2);
                        D2ProxJauUp.setY(y - 29*density/2 - D2ProxJauUp.getHeight() / 2);
                        D2ProxRedDown.setX(x - D2ProxRedUp.getWidth() / 2);
                        D2ProxRedDown.setY(y + 18*density/2 - D2ProxRedUp.getHeight() / 2);
                        D2ProxOrDown.setX(x - D2ProxOrUp.getWidth() / 2);
                        D2ProxOrDown.setY(y + 27*density/2 - D2ProxOrUp.getHeight() / 2);
                        D2ProxJauDown.setX(x - D2ProxJauUp.getWidth() / 2);
                        D2ProxJauDown.setY(y + 34*density/2 - D2ProxJauUp.getHeight() / 2);
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone3")) {
                        Drone3.setX(x - Drone3.getWidth() / 2);
                        Drone3.setY(y - Drone3.getHeight() / 2);
                        D3ProxRedBot.setX(x - D3ProxRedBot.getWidth() / 2);
                        D3ProxRedBot.setY(y + 46*density/2 - D3ProxRedBot.getHeight() / 2);
                        D3ProxOrBot.setX(x - D3ProxOrBot.getWidth() / 2);
                        D3ProxOrBot.setY(y + 56*density/2 - D3ProxOrBot.getHeight() / 2);
                        D3ProxJauBot.setX(x - D3ProxJauBot.getWidth() / 2);
                        D3ProxJauBot.setY(y + 66*density/2 - D3ProxJauBot.getHeight() / 2);
                        D3ProxRedTop.setX(x - D3ProxRedTop.getWidth() / 2);
                        D3ProxRedTop.setY(y - 50*density/2 - D3ProxRedTop.getHeight() / 2);
                        D3ProxOrTop.setX(x - D3ProxOrTop.getWidth() / 2);
                        D3ProxOrTop.setY(y - 60*density/2 - D3ProxOrTop.getHeight() / 2);
                        D3ProxJauTop.setX(x - D3ProxJauTop.getWidth() / 2);
                        D3ProxJauTop.setY(y - 70*density/2 - D3ProxJauTop.getHeight() / 2);
                        D3ProxRedRight.setX(x + 45*density/2 - D3ProxRedRight.getWidth() / 2);
                        D3ProxRedRight.setY(y - D3ProxRedRight.getHeight() / 2);
                        D3ProxOrRight.setX(x + 55*density/2 - D3ProxOrRight.getWidth() / 2);
                        D3ProxOrRight.setY(y - D3ProxOrRight.getHeight() / 2);
                        D3ProxJauRight.setX(x + 65*density/2 - D3ProxJauRight.getWidth() / 2);
                        D3ProxJauRight.setY(y - D3ProxJauRight.getHeight() / 2);
                        D3ProxRedLeft.setX(x - 50*density/2 - D3ProxRedLeft.getWidth() / 2);
                        D3ProxRedLeft.setY(y - D3ProxRedLeft.getHeight() / 2);
                        D3ProxOrLeft.setX(x - 60*density/2 - D3ProxOrLeft.getWidth() / 2);
                        D3ProxOrLeft.setY(y - D3ProxOrLeft.getHeight() / 2);
                        D3ProxJauLeft.setX(x - 70*density/2 - D3ProxJauLeft.getWidth() / 2);
                        D3ProxJauLeft.setY(y - D3ProxJauLeft.getHeight() / 2);
                        D3ProxRedUp.setX(x - D3ProxRedUp.getWidth() / 2);
                        D3ProxRedUp.setY(y - 11*density/2 - D3ProxRedUp.getHeight() / 2);
                        D3ProxOrUp.setX(x - D3ProxOrUp.getWidth() / 2);
                        D3ProxOrUp.setY(y - 20*density/2 - D3ProxOrUp.getHeight() / 2);
                        D3ProxJauUp.setX(x - D3ProxJauUp.getWidth() / 2);
                        D3ProxJauUp.setY(y - 28*density/2 - D3ProxJauUp.getHeight() / 2);
                        D3ProxRedDown.setX(x - D3ProxRedUp.getWidth() / 2);
                        D3ProxRedDown.setY(y + 18*density/2 - D3ProxRedUp.getHeight() / 2);
                        D3ProxOrDown.setX(x - D3ProxOrUp.getWidth() / 2);
                        D3ProxOrDown.setY(y + 27*density/2 - D3ProxOrUp.getHeight() / 2);
                        D3ProxJauDown.setX(x - D3ProxJauUp.getWidth() / 2);
                        D3ProxJauDown.setY(y + 34*density/2 - D3ProxJauUp.getHeight() / 2);
                    }


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }

            return true;
        }
    }

}
