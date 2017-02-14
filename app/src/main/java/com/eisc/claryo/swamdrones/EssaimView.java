package com.eisc.claryo.swamdrones;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Classe permettant de voir la formation de l'essaim
 */

public class EssaimView extends AppCompatActivity {

    RelativeLayout LayoutDrone1, LayoutDrone2, LayoutDrone3;
    ImageView Drone1, Drone2, Drone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_view);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnBackSwapView);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettingsSwapView);
        Drone1 = (ImageView) findViewById(R.id.Drone1);
        Drone2 = (ImageView) findViewById(R.id.Drone2);
        Drone3 = (ImageView) findViewById(R.id.Drone3);

        LayoutDrone1 = (RelativeLayout) findViewById(R.id.LayoutDrone1);
        LayoutDrone2 = (RelativeLayout) findViewById(R.id.LayoutDrone2);
        LayoutDrone3 = (RelativeLayout) findViewById(R.id.LayoutDrone3);

        Drone1.setOnTouchListener(new MyTouchListener1());
        Drone2.setOnTouchListener(new MyTouchListener2());
        Drone3.setOnTouchListener(new MyTouchListener3());
        findViewById(R.id.Ecran).setOnDragListener(new MyDragListener());

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
                    System.out.println("action started");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    System.out.println("action entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    System.out.println("action exited");
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
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone2")) {
                        Drone2.setX(x - Drone2.getWidth() / 2);
                        Drone2.setY(y - Drone2.getHeight() / 2);
                    }
                    if (event.getClipData().getDescription().getLabel().equals("Drone3")) {
                        Drone3.setX(x - Drone3.getWidth() / 2);
                        Drone3.setY(y - Drone3.getHeight() / 2);
                    }


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    System.out.println("action ended");
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

}
