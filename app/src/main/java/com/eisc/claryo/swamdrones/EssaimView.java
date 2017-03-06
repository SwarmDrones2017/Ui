package com.eisc.claryo.swamdrones;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARFrame;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe permettant de voir la formation de l'essaim
 */
//TODO Gérer les couleurs et les informations en haut à gauche
public class EssaimView extends AppCompatActivity {
    private ArrayList<ProxyBars> lProxyBars;
    private ArrayList<EssaimViewInfoDrone> lEssaimViewInfoDrone;
    private float density;
    private String densite;
    private AbsoluteLayout Ecran;
    private LinearLayout LayoutToggleBtn;
    private LinearLayout LayoutDroneInfo;
    private Button btnAllDrones;
    private ArrayList<ToggleBtnSelectDrone> lToggleBtnSelectDrone;
    private String droneName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);

        setContentView(R.layout.essaim_view);

        ImageButton btnRetour = (ImageButton) findViewById(R.id.btnBackSwapView);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettingsSwapView);
        btnAllDrones = (Button) findViewById(R.id.BtnAllDrone);
        Ecran = (AbsoluteLayout) findViewById(R.id.Ecran);
        LayoutDroneInfo = (LinearLayout) findViewById(R.id.LayoutDroneInfo);
        LayoutToggleBtn = (LinearLayout) findViewById(R.id.LayoutToggleButton);
        density = getResources().getDisplayMetrics().density;
        densite = Float.toString(density);

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
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(ControlActivity);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EssaimView.this.finish();
                Intent EssaimConfigActivity = new Intent(EssaimView.this, EssaimConfig.class);
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone().getHandlerBattery() != null)
                        GlobalCouple.couples.get(i).getBebopDrone().setHandlerBattery(null);
                }
                startActivity(EssaimConfigActivity);
            }
        });

        lProxyBars = new ArrayList<>(GlobalCouple.couples.size());
        lToggleBtnSelectDrone = new ArrayList<>(GlobalCouple.couples.size());
        lEssaimViewInfoDrone = new ArrayList<>(GlobalCouple.couples.size());

        int tabColor[] = {Color.BLUE, Color.CYAN, Color.RED, Color.MAGENTA};
        int indexColor = -1;
        Random r = new Random();

        for (int i = 0; i < GlobalCouple.couples.size(); i++) {
            if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                droneName = GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName();
                if (GlobalCouple.couples.get(i).getBebopDrone().getxEssaimView() < 0 && GlobalCouple.couples.get(i).getBebopDrone().getyEssaimView() < 0) {
                    GlobalCouple.couples.get(i).getBebopDrone().setxEssaimView(r.nextInt(633));
                    GlobalCouple.couples.get(i).getBebopDrone().setyEssaimView(r.nextInt(360));
                }
            }
            indexColor++;
            if (indexColor > tabColor.length)
                indexColor = 0;

            EssaimViewInfoDrone essaimViewInfoDrone = new EssaimViewInfoDrone(getApplicationContext(), LayoutDroneInfo, droneName, tabColor[indexColor]);
            lEssaimViewInfoDrone.add(essaimViewInfoDrone);

            ProxyBars pb = new ProxyBars(getApplicationContext(), Ecran, density, droneName, tabColor[indexColor]);
            MyTouchListener myTouchListener = new MyTouchListener();
            myTouchListener.setDroneName((droneName));
            pb.Drone.setOnTouchListener(myTouchListener);
            lProxyBars.add(pb);

            //création des toggleButton pour choisir le/les drones à piloter
            ToggleBtnSelectDrone toggleBtnSelectDrone = new ToggleBtnSelectDrone(getApplicationContext(), LayoutToggleBtn, droneName, tabColor[indexColor]);
            lToggleBtnSelectDrone.add(toggleBtnSelectDrone);
        }
        //s'il y a un drone ou +, on affiche le bouton pour sélectionner tous les drones en même temps
        if (GlobalCouple.couples.size() > 0) {
            btnAllDrones.setVisibility(View.VISIBLE);
        }
        //Listener du bouton AllDrone
        btnAllDrones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                    if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                        GlobalCouple.couples.get(i).getBebopDrone().setFlyAuthorization(true);//on autorise tous les drones à voler
                    }
                    lToggleBtnSelectDrone.get(i).tglBtnSetDrone.setChecked(true);//on met tous les toggle button à true
                }
            }
        });

        findViewById(R.id.Ecran).setOnDragListener(new MyDragListener());

    }

    private final class MyTouchListener implements View.OnTouchListener {
        private String droneName;

        public void setDroneName(String droneName) {
            this.droneName = droneName;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ClipData clipData = ClipData.newPlainText(droneName, "");

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;

            } else

            {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        final Drawable enterShape = getResources().getDrawable(R.drawable.shape_dropout);
        final Drawable normalShape = getResources().getDrawable(R.drawable.shape);

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
                    int imgDroneWidth = lProxyBars.get(0).Drone.getWidth(); //c'est pour les dimensions de l'image, mais ce sont les mêmes images tout le temps;
                    int imgDroneHeight = lProxyBars.get(0).Drone.getHeight();
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    AbsoluteLayout container = (AbsoluteLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);

                    for (int i = 0; i < GlobalCouple.couples.size(); i++) {
                        if (GlobalCouple.couples.get(i).getBebopDrone() != null) {
                            if (event.getClipData().getDescription().getLabel().equals(GlobalCouple.couples.get(i).getBebopDrone().getInfoDrone().getDroneName())) {

                                lProxyBars.get(i).ProxJauDown.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauDown.setY(y + 65 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrDown.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrDown.setY(y + 55 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedDown.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedDown.setY(y + 45 * density / 2 - imgDroneHeight / 2);

                                lProxyBars.get(i).Drone.setX(x - imgDroneWidth / 2);
                                lProxyBars.get(i).Drone.setY(y - imgDroneHeight / 2);

                                GlobalCouple.couples.get(i).getBebopDrone().setxEssaimView(lProxyBars.get(i).Drone.getX());
                                GlobalCouple.couples.get(i).getBebopDrone().setyEssaimView(lProxyBars.get(i).Drone.getY());

                                lProxyBars.get(i).ProxJauLeft.setX(x - 30 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauLeft.setY(y + 10 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrLeft.setX(x - 20 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrLeft.setY(y + 10 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedLeft.setX(x - 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedLeft.setY(y + 10 * density / 2 - imgDroneHeight / 2);

                                lProxyBars.get(i).ProxJauRight.setX(x + 121 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauRight.setY(y + 10 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrRight.setX(x + 111 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrRight.setY(y + 10 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedRight.setX(x + 101 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedRight.setY(y + 10 * density / 2 - imgDroneHeight / 2);

                                lProxyBars.get(i).ProxJauTop.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauTop.setY(y - 30 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrTop.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrTop.setY(y - 20 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedTop.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedTop.setY(y - 10 * density / 2 - imgDroneHeight / 2);

                                lProxyBars.get(i).ProxJauBot.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauBot.setY(y + 121 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrBot.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrBot.setY(y + 111 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedBot.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedBot.setY(y + 101 * density / 2 - imgDroneHeight / 2);

                                lProxyBars.get(i).ProxJauUp.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxJauUp.setY(y - 5 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxOrUp.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxOrUp.setY(y + 5 * density / 2 - imgDroneHeight / 2);
                                lProxyBars.get(i).ProxRedUp.setX(x + 10 * density / 2 - imgDroneWidth / 2);
                                lProxyBars.get(i).ProxRedUp.setY(y + 15 * density / 2 - imgDroneHeight / 2);
                            }
                        }
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

class ProxyBars extends AppCompatActivity {
    private final float PlageY;
    private final float PlageX;
    private final AbsoluteLayout Ecran;
    private final Context context;
    private final float density;
    private final int indexDrone;
    private final int indexColor;
    ImageView Drone;
    ImageView ProxJauLeft, ProxOrLeft, ProxRedLeft, ProxJauRight, ProxOrRight, ProxRedRight, ProxJauTop, ProxOrTop, ProxRedTop, ProxJauBot,
            ProxOrBot, ProxRedBot, ProxJauUp, ProxOrUp, ProxRedUp, ProxJauDown, ProxOrDown, ProxRedDown;
    private int north = 151;
    private int south = 151;
    private int east = 151;
    private int west = 151;
    private int above = 151;

    public ProxyBars(Context context, AbsoluteLayout ecran, float density, String droneName, int indexColor) {
        this.context = context;
        this.density = density;
        this.indexColor = indexColor;
        this.Ecran = ecran;

        indexDrone = GlobalCouple.droneNameCorrespondant(droneName);
        if (GlobalCouple.couples.get(indexDrone).getRaspberry() == null) {
            Toast.makeText(context, "Raspberry Pi non trouvée", Toast.LENGTH_LONG);
        } else {
            Handler handlerObstacle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    east = msg.getData().getInt(MessageKEY.OBSTACLEEST);
                    west = msg.getData().getInt(MessageKEY.OBSTACLEWEST);
                    north = msg.getData().getInt(MessageKEY.OBSTACLENORTH);
                    south = msg.getData().getInt(MessageKEY.OBSTACLESOUTH);
                    above = msg.getData().getInt(MessageKEY.OBSTACLEABOVE);
                    hideAndShowBars();
                }
            };
            GlobalCouple.couples.get(indexDrone).getRaspberry().setHandlerObstacle(handlerObstacle);
        }
        // peut pas le proteger
        PlageX = GlobalCouple.couples.get(indexDrone).getBebopDrone().getxEssaimView();
        PlageY = GlobalCouple.couples.get(indexDrone).getBebopDrone().getyEssaimView();

        show();
        hideAndShowBars();

    }

    private void show() {
        Drone = new ImageView(context);
        Drone.setX(PlageX);
        Drone.setY(PlageY);
        Drone.setImageResource(R.drawable.drone512);
        Drone.setColorFilter(indexColor);
        float Xdrone = Drone.getX();
        float Ydrone = Drone.getY();

        ProxJauLeft = new ImageView(context);
        ProxJauLeft.setImageResource(R.drawable.barv);
        ProxJauLeft.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauLeft.setX(Xdrone - 30 * density / 2);
        ProxJauLeft.setY(Ydrone + 10 * density / 2);
        ProxOrLeft = new ImageView(context);
        ProxOrLeft.setImageResource(R.drawable.barv);
        ProxOrLeft.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrLeft.setX(Xdrone - 20 * density / 2);
        ProxOrLeft.setY(Ydrone + 10 * density / 2);
        ProxRedLeft = new ImageView(context);
        ProxRedLeft.setImageResource(R.drawable.barv);
        ProxRedLeft.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedLeft.setX(Xdrone - 10 * density / 2);
        ProxRedLeft.setY(Ydrone + 10 * density / 2);

        ProxJauRight = new ImageView(context);
        ProxJauRight.setImageResource(R.drawable.barv);
        ProxJauRight.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauRight.setX(Xdrone + 121 * density / 2);
        ProxJauRight.setY(Ydrone + 10 * density / 2);
        ProxOrRight = new ImageView(context);
        ProxOrRight.setImageResource(R.drawable.barv);
        ProxOrRight.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrRight.setX(Xdrone + 111 * density / 2);
        ProxOrRight.setY(Ydrone + 10 * density / 2);
        ProxRedRight = new ImageView(context);
        ProxRedRight.setImageResource(R.drawable.barv);
        ProxRedRight.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedRight.setX(Xdrone + 101 * density / 2);
        ProxRedRight.setY(Ydrone + 10 * density / 2);

        ProxJauTop = new ImageView(context);
        ProxJauTop.setImageResource(R.drawable.barh);
        ProxJauTop.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauTop.setX(Xdrone + 10 * density / 2);
        ProxJauTop.setY(Ydrone - 30 * density / 2);
        ProxOrTop = new ImageView(context);
        ProxOrTop.setImageResource(R.drawable.barh);
        ProxOrTop.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrTop.setX(Xdrone + 10 * density / 2);
        ProxOrTop.setY(Ydrone - 20 * density / 2);
        ProxRedTop = new ImageView(context);
        ProxRedTop.setImageResource(R.drawable.barh);
        ProxRedTop.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedTop.setX(Xdrone + 10 * density / 2);
        ProxRedTop.setY(Ydrone - 10 * density / 2);

        ProxJauBot = new ImageView(context);
        ProxJauBot.setImageResource(R.drawable.barh);
        ProxJauBot.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauBot.setX(Xdrone + 10 * density / 2);
        ProxJauBot.setY(Ydrone + 121 * density / 2);
        ProxOrBot = new ImageView(context);
        ProxOrBot.setImageResource(R.drawable.barh);
        ProxOrBot.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrBot.setX(Xdrone + 10 * density / 2);
        ProxOrBot.setY(Ydrone + 111 * density / 2);
        ProxRedBot = new ImageView(context);
        ProxRedBot.setImageResource(R.drawable.barh);
        ProxRedBot.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedBot.setX(Xdrone + 10 * density / 2);
        ProxRedBot.setY(Ydrone + 101 * density / 2);

        ProxJauDown = new ImageView(context);
        ProxJauDown.setImageResource(R.drawable.ic_down);
        ProxJauDown.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauDown.setX(Xdrone + 10 * density / 2);
        ProxJauDown.setY(Ydrone + 65 * density / 2);
        ProxOrDown = new ImageView(context);
        ProxOrDown.setImageResource(R.drawable.ic_down);
        ProxOrDown.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrDown.setX(Xdrone + 10 * density / 2);
        ProxOrDown.setY(Ydrone + 55 * density / 2);
        ProxRedDown = new ImageView(context);
        ProxRedDown.setImageResource(R.drawable.ic_down);
        ProxRedDown.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedDown.setX(Xdrone + 10 * density / 2);
        ProxRedDown.setY(Ydrone + 45 * density / 2);

        ProxJauUp = new ImageView(context);
        ProxJauUp.setImageResource(R.drawable.ic_up);
        ProxJauUp.setColorFilter(Color.parseColor("#F2EE1A"));
        ProxJauUp.setX(Xdrone + 10 * density / 2);
        ProxJauUp.setY(Ydrone - 5 * density / 2);
        ProxOrUp = new ImageView(context);
        ProxOrUp.setImageResource(R.drawable.ic_up);
        ProxOrUp.setColorFilter(Color.parseColor("#FFFF8800"));
        ProxOrUp.setX(Xdrone + 10 * density / 2);
        ProxOrUp.setY(Ydrone + 5 * density / 2);
        ProxRedUp = new ImageView(context);
        ProxRedUp.setImageResource(R.drawable.ic_up);
        ProxRedUp.setColorFilter(Color.parseColor("#FFCC0000"));
        ProxRedUp.setX(Xdrone + 10 * density / 2);
        ProxRedUp.setY(Ydrone + 15 * density / 2);

        Ecran.addView(ProxJauDown);
        Ecran.addView(ProxOrDown);
        Ecran.addView(ProxRedDown);
        Ecran.addView(Drone);
        Ecran.addView(ProxJauBot);
        Ecran.addView(ProxOrBot);
        Ecran.addView(ProxRedBot);
        Ecran.addView(ProxJauTop);
        Ecran.addView(ProxOrTop);
        Ecran.addView(ProxRedTop);
        Ecran.addView(ProxJauRight);
        Ecran.addView(ProxOrRight);
        Ecran.addView(ProxRedRight);
        Ecran.addView(ProxJauLeft);
        Ecran.addView(ProxOrLeft);
        Ecran.addView(ProxRedLeft);
        Ecran.addView(ProxJauUp);
        Ecran.addView(ProxOrUp);
        Ecran.addView(ProxRedUp);

//        Log.i("ContexteD", "" + Drone.getContext());
//        Log.i("ContexteE", "" + Ecran.getContext());

    }

    private void hideAndShowBars() {
//        Drone.setVisibility(View.VISIBLE);
        if (west > 0) {
            if (west > 100) {
                ProxJauLeft.setVisibility(View.INVISIBLE);
                ProxOrLeft.setVisibility(View.INVISIBLE);
                ProxRedLeft.setVisibility(View.INVISIBLE);
            } else if (west <= 100 && west > 75) {
                ProxJauLeft.setVisibility(View.VISIBLE);
                ProxOrLeft.setVisibility(View.INVISIBLE);
                ProxRedLeft.setVisibility(View.INVISIBLE);
            } else if (west <= 75 && west > 50) {
                ProxJauLeft.setVisibility(View.VISIBLE);
                ProxOrLeft.setVisibility(View.VISIBLE);
                ProxRedLeft.setVisibility(View.INVISIBLE);
            } else if (west <= 50) {
                ProxJauLeft.setVisibility(View.VISIBLE);
                ProxOrLeft.setVisibility(View.VISIBLE);
                ProxRedLeft.setVisibility(View.VISIBLE);
            }
        }
        if (east > 0) {
            if (east > 100) {
                ProxJauRight.setVisibility(View.INVISIBLE);
                ProxOrRight.setVisibility(View.INVISIBLE);
                ProxRedRight.setVisibility(View.INVISIBLE);
            } else if (east <= 100 && east > 75) {
                ProxJauRight.setVisibility(View.VISIBLE);
                ProxOrRight.setVisibility(View.INVISIBLE);
                ProxRedRight.setVisibility(View.INVISIBLE);
            } else if (east <= 75 && east > 50) {
                ProxJauRight.setVisibility(View.VISIBLE);
                ProxOrRight.setVisibility(View.VISIBLE);
                ProxRedRight.setVisibility(View.INVISIBLE);
            } else if (east <= 50) {
                ProxJauRight.setVisibility(View.VISIBLE);
                ProxOrRight.setVisibility(View.VISIBLE);
                ProxRedRight.setVisibility(View.VISIBLE);
            }
        }

        if (north > 0) {
            if (north > 100) {
                ProxJauTop.setVisibility(View.INVISIBLE);
                ProxOrTop.setVisibility(View.INVISIBLE);
                ProxRedTop.setVisibility(View.INVISIBLE);
            } else if (north <= 100 && north > 75) {
                ProxJauTop.setVisibility(View.VISIBLE);
                ProxOrTop.setVisibility(View.INVISIBLE);
                ProxRedTop.setVisibility(View.INVISIBLE);
            } else if (north <= 75 && north > 50) {
                ProxJauTop.setVisibility(View.VISIBLE);
                ProxOrTop.setVisibility(View.VISIBLE);
                ProxRedTop.setVisibility(View.INVISIBLE);
            } else if (north <= 50) {
                ProxJauTop.setVisibility(View.VISIBLE);
                ProxOrTop.setVisibility(View.VISIBLE);
                ProxRedTop.setVisibility(View.VISIBLE);
            }
        }

        if (south > 0) {
            if (south > 100) {
                ProxJauBot.setVisibility(View.INVISIBLE);
                ProxOrBot.setVisibility(View.INVISIBLE);
                ProxRedBot.setVisibility(View.INVISIBLE);
            } else if (south <= 100 && south > 75) {
                ProxJauBot.setVisibility(View.VISIBLE);
                ProxOrBot.setVisibility(View.INVISIBLE);
                ProxRedBot.setVisibility(View.INVISIBLE);
            } else if (south <= 75 && south > 50) {
                ProxJauBot.setVisibility(View.VISIBLE);
                ProxOrBot.setVisibility(View.VISIBLE);
                ProxRedBot.setVisibility(View.INVISIBLE);
            } else if (south <= 50) {
                ProxJauBot.setVisibility(View.VISIBLE);
                ProxOrBot.setVisibility(View.VISIBLE);
                ProxRedBot.setVisibility(View.VISIBLE);
            }
        }

        if (above > 0) {
            if (above > 100) {
                ProxJauUp.setVisibility(View.INVISIBLE);
                ProxOrUp.setVisibility(View.INVISIBLE);
                ProxRedUp.setVisibility(View.INVISIBLE);
            } else if (above <= 100 && above > 75) {
                ProxJauUp.setVisibility(View.VISIBLE);
                ProxOrUp.setVisibility(View.INVISIBLE);
                ProxRedUp.setVisibility(View.INVISIBLE);
            } else if (above <= 75 && above > 50) {
                ProxJauUp.setVisibility(View.VISIBLE);
                ProxOrUp.setVisibility(View.VISIBLE);
                ProxRedUp.setVisibility(View.INVISIBLE);
            } else if (above <= 50) {
                ProxJauUp.setVisibility(View.VISIBLE);
                ProxOrUp.setVisibility(View.VISIBLE);
                ProxRedUp.setVisibility(View.VISIBLE);
            }
        }


    }
}

class EssaimViewInfoDrone extends AppCompatActivity {

    private final Context context;
    private final LinearLayout LayoutDroneInfo;
    private final int indexDrone;
    private final BebopDrone.Listener mBebopDroneListener;
    private final int indexColor;
    private LinearLayout LayoutDroneVertical;
    private LinearLayout LayoutDroneVitesse;
    private LinearLayout LayoutDroneAltitude;
    private LinearLayout LayoutDroneBatterie;
    private TextView NomDrone;
    private TextView MaxSpeed;
    private TextView Altitude;
    private TextView Battery;
    private ImageView MaxSpeedImg;
    private ImageView AltitudeImg;
    private ImageView BatteryImg;
    private int batteryPercentage;
    private double AltitudeDrone;
    private DecimalFormat df;

    public EssaimViewInfoDrone(Context context, LinearLayout layoutdroneinfo, String droneName, int color) {
        this.context = context;
        LayoutDroneInfo = layoutdroneinfo;
        indexDrone = GlobalCouple.droneNameCorrespondant(droneName);
        this.indexColor = color;
        displayInfos();

        mBebopDroneListener = new BebopDrone.Listener() {
            @Override
            public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {

            }

            @Override
            public void onBatteryChargeChanged(int batteryPercentage) {
                if ((indexDrone != -1) && (GlobalCouple.couples.get(indexDrone).getBebopDrone() != null)) {
                    batteryPercentage = GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getBattery();
                }


                if (batteryPercentage > 97)
                    BatteryImg.setImageResource(R.drawable.ic_battery_full_24dp);
                else if (batteryPercentage > 90)
                    BatteryImg.setImageResource(R.drawable.ic_battery_90_24dp);
                else if (batteryPercentage > 80)
                    BatteryImg.setImageResource(R.drawable.ic_battery_80_24dp);
                else if (batteryPercentage > 60)
                    BatteryImg.setImageResource(R.drawable.ic_battery_60_24dp);
                else if (batteryPercentage > 50)
                    BatteryImg.setImageResource(R.drawable.ic_battery_50_24dp);
                else if (batteryPercentage > 30)
                    BatteryImg.setImageResource(R.drawable.ic_battery_30_24dp);
                else if (batteryPercentage > 20)
                    BatteryImg.setImageResource(R.drawable.ic_battery_20_24dp);
                else
                    BatteryImg.setImageResource(R.drawable.ic_battery_alert_24dp);

                Battery.setText(Integer.toString(batteryPercentage) + " %");
            }

            @Override
            public void onPilotingStateChanged(ARCOMMANDS_ARDRONE3_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {

            }

            @Override
            public void onPictureTaken(ARCOMMANDS_ARDRONE3_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {

            }

            @Override
            public void configureDecoder(ARControllerCodec codec) {

            }

            @Override
            public void onFrameReceived(ARFrame frame) {

            }

            @Override
            public void onMatchingMediasFound(int nbMedias) {

            }

            @Override
            public void onDownloadProgressed(String mediaName, int progress) {

            }

            @Override
            public void onDownloadComplete(String mediaName) {

            }
        };
        if ((indexDrone != -1) && (GlobalCouple.couples.get(indexDrone).getBebopDrone() != null)) {
            GlobalCouple.couples.get(indexDrone).getBebopDrone().addListener(mBebopDroneListener);
        }
    }

    private void displayInfos() {

        df = new DecimalFormat("0.0");

        LayoutDroneVertical = new LinearLayout(context);
        LayoutDroneVertical.setOrientation(LinearLayout.VERTICAL);

        LayoutDroneVitesse = new LinearLayout(context);
        LayoutDroneVitesse.setOrientation(LinearLayout.HORIZONTAL);

        LayoutDroneAltitude = new LinearLayout(context);
        LayoutDroneAltitude.setOrientation(LinearLayout.HORIZONTAL);

        LayoutDroneBatterie = new LinearLayout(context);
        LayoutDroneBatterie.setOrientation(LinearLayout.HORIZONTAL);

        NomDrone = new TextView(context);
        if ((indexDrone != -1) && (GlobalCouple.couples.get(indexDrone).getBebopDrone() != null)) {
            NomDrone.setText(" " + GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getDroneName() + " ");
        } else {
            NomDrone.setText(" Nop ");
        }

        NomDrone.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        NomDrone.setTextColor(indexColor);
        NomDrone.setGravity(Gravity.CENTER);

        MaxSpeedImg = new ImageView(context);
        MaxSpeedImg.setImageResource(R.drawable.ic_speed);

        MaxSpeed = new TextView(context);
        if ((indexDrone != -1) && (GlobalCouple.couples.get(indexDrone).getBebopDrone() != null)) {
            MaxSpeed.setText(df.format(GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getSpeedtilt()) + "\n°/s");
        } else {
            MaxSpeed.setText(" Nop ");
        }
        MaxSpeed.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        MaxSpeed.setTextColor(Color.BLACK);
        MaxSpeed.setGravity(Gravity.CENTER);

        AltitudeImg = new ImageView(context);
        AltitudeImg.setImageResource(R.drawable.ic_height);

        Altitude = new TextView(context);

        AltitudeDrone = GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getAltitude();
        Altitude.setText(df.format(AltitudeDrone) + " m");
        Altitude.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        Altitude.setTextColor(Color.BLACK);
        Altitude.setGravity(Gravity.CENTER);

        BatteryImg = new ImageView(context);
        if ((indexDrone != -1) && (GlobalCouple.couples.get(indexDrone).getBebopDrone() != null)) {
            batteryPercentage = GlobalCouple.couples.get(indexDrone).getBebopDrone().getInfoDrone().getBattery();
        }


        if (batteryPercentage > 97)
            BatteryImg.setImageResource(R.drawable.ic_battery_full_24dp);
        else if (batteryPercentage > 90)
            BatteryImg.setImageResource(R.drawable.ic_battery_90_24dp);
        else if (batteryPercentage > 80)
            BatteryImg.setImageResource(R.drawable.ic_battery_80_24dp);
        else if (batteryPercentage > 60)
            BatteryImg.setImageResource(R.drawable.ic_battery_60_24dp);
        else if (batteryPercentage > 50)
            BatteryImg.setImageResource(R.drawable.ic_battery_50_24dp);
        else if (batteryPercentage > 30)
            BatteryImg.setImageResource(R.drawable.ic_battery_30_24dp);
        else if (batteryPercentage > 20)
            BatteryImg.setImageResource(R.drawable.ic_battery_20_24dp);
        else
            BatteryImg.setImageResource(R.drawable.ic_battery_alert_24dp);

        Battery = new TextView(context);

        Battery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        Battery.setText(Integer.toString(batteryPercentage) + " %");
        Battery.setTextColor(Color.BLACK);
        Battery.setGravity(Gravity.CENTER);

        LayoutDroneInfo.addView(LayoutDroneVertical);

        LayoutDroneVertical.addView(NomDrone);
        LayoutDroneVertical.addView(LayoutDroneVitesse);
        LayoutDroneVertical.addView(LayoutDroneAltitude);
        LayoutDroneVertical.addView(LayoutDroneBatterie);

        LayoutDroneVitesse.addView(MaxSpeedImg);
        LayoutDroneVitesse.addView(MaxSpeed);

        LayoutDroneAltitude.addView(AltitudeImg);
        LayoutDroneAltitude.addView(Altitude);

        LayoutDroneBatterie.addView(BatteryImg);
        LayoutDroneBatterie.addView(Battery);
    }

}

@TargetApi(21)
class ToggleBtnSelectDrone extends AppCompatActivity {
    final ToggleButton tglBtnSetDrone;

    public ToggleBtnSelectDrone(Context context, LinearLayout TglLayout, final String droneName, final int indexColor) {
        tglBtnSetDrone = new ToggleButton(context);
        tglBtnSetDrone.setChecked(true);
        tglBtnSetDrone.setText(droneName);
        tglBtnSetDrone.setTextOff(droneName);
        tglBtnSetDrone.setTextOn(droneName);
        tglBtnSetDrone.setBackgroundTintList(ColorStateList.valueOf(indexColor));

        TglLayout.addView(tglBtnSetDrone);

        tglBtnSetDrone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int droneSelected = GlobalCouple.droneNameCorrespondant(droneName);
                if (isChecked) {
                    tglBtnSetDrone.setBackgroundTintList(ColorStateList.valueOf(indexColor));
                    GlobalCouple.couples.get(droneSelected).getBebopDrone().setFlyAuthorization(true);
                } else {
                    buttonView.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                    GlobalCouple.couples.get(droneSelected).getBebopDrone().setFlyAuthorization(false);
                }
            }
        });
    }
}
