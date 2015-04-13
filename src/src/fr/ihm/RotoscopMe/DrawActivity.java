package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import static fr.ihm.RotoscopMe.R.layout.drawer_list_item;


public class DrawActivity extends Activity {

    public static float GLOBAL_INITIAL_POSITION_X = -1;
    public static float GLOBAL_CURRENT_POSITION_X = -1;
    public static int GLOBAL_DESSIN = -1;

    protected DrawZone drawzone;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;

    private ImageButton buttonColor;
    private ImageButton buttonPen;
    private ImageButton buttonRubber;

    private ImageButton buttonUndo;
    private ImageButton buttonRedo;

    private ImageButton buttonMenu;

    private LinearLayout listColor;
    private ImageButton buttonBlack;
    private ImageButton buttonRed;
    private ImageButton buttonBlue;
    private ImageButton buttonGreen;

    private LinearLayout listPen;
    private ImageButton buttonSmallPen;
    private ImageButton buttonMediumPen;
    private ImageButton buttonBigPen;

    private LinearLayout listRubber;
    private ImageButton buttonSmallRubber;
    private ImageButton buttonMediumRubber;
    private ImageButton buttonBigRubber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        drawzone = (DrawZone) findViewById(R.id.drawzone);
        drawzone.setParent(this);

        buttonColor = (ImageButton) findViewById(R.id.color);
        buttonPen = (ImageButton) findViewById(R.id.pen);
        buttonRubber = (ImageButton) findViewById(R.id.rubber);

        buttonUndo= (ImageButton) findViewById(R.id.undo);
        buttonRedo = (ImageButton) findViewById(R.id.redo);

        buttonMenu = (ImageButton) findViewById(R.id.menu);

        listColor = (LinearLayout) findViewById(R.id.listcolor);
        buttonBlack = (ImageButton) findViewById(R.id.black);
        buttonRed = (ImageButton) findViewById(R.id.red);
        buttonBlue = (ImageButton) findViewById(R.id.blue);
        buttonGreen = (ImageButton) findViewById(R.id.green);

        listPen = (LinearLayout) findViewById(R.id.listpen);
        buttonSmallPen = (ImageButton) findViewById(R.id.smallpen);
        buttonMediumPen = (ImageButton) findViewById(R.id.mediumpen);
        buttonBigPen = (ImageButton) findViewById(R.id.bigpen);

        listRubber = (LinearLayout) findViewById(R.id.listrubber);
        buttonSmallRubber = (ImageButton) findViewById(R.id.smallrubber);
        buttonMediumRubber = (ImageButton) findViewById(R.id.mediumrubber);
        buttonBigRubber = (ImageButton) findViewById(R.id.bigrubber);

        mPlanetTitles = new String[]{"Enregistrer","Enregistrer sous", "Exporter en image", "Exporter en vidéo","Partager","Préférences","Fermer le projet","Quitter","Aide","A propos"};


        //Two-Finger Drag Gesture detection
        DrawerLayout TextLoggerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        TextLoggerLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener(){

                    @Override
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }

                });

        buttonColor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listColor.setVisibility(View.VISIBLE);
                buttonColor.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        buttonPen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listPen.setVisibility(View.VISIBLE);
                buttonPen.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        buttonRubber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listRubber.setVisibility(View.VISIBLE);
                buttonRubber.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }

    void handleTouch(MotionEvent m){
        //Number of touches
        int action = m.getActionMasked();

        if (GLOBAL_INITIAL_POSITION_X == -1) {
            GLOBAL_INITIAL_POSITION_X = m.getX();
        }

        GLOBAL_CURRENT_POSITION_X = m.getX();

        int pointerCount = m.getPointerCount();

        if(pointerCount == 2) {
            GLOBAL_DESSIN = 0;
            if (GLOBAL_CURRENT_POSITION_X + 500 < GLOBAL_INITIAL_POSITION_X) {
                Log.d("Move : ", "Left");
            } else if (GLOBAL_CURRENT_POSITION_X - 500 > GLOBAL_INITIAL_POSITION_X) {
                Log.d("Move : ", "Right");
            }
        }
        else
        {
            if(GLOBAL_DESSIN == -1)
                GLOBAL_DESSIN = 1;

            float x = m.getX();
            float y = m.getY();

            switch (m.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawzone.touchStart(x, y);
                    drawzone.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(GLOBAL_DESSIN == 1) {
                        drawzone.touchMove(x, y);
                        drawzone.invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(GLOBAL_DESSIN == 1) {
                        drawzone.touchEnd();
                        drawzone.invalidate();
                    }
                    else {
                        drawzone.touchUndo();
                        drawzone.invalidate();
                    }

                    break;
            }

            /*int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;
            switch (action)
            {
                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d("Pointeur : ", Integer.toString(pointerCount));
                    break;
                /*case MotionEvent.ACTION_DOWN:
                    GLOBAL_TOUCH_POSITION_X = (int) m.getX(1);
                    actionString = "DOWN"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    break;
                case MotionEvent.ACTION_UP:
                    GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
                    actionString = "UP"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    break;
                case MotionEvent.ACTION_MOVE:
                    GLOBAL_TOUCH_CURRENT_POSITION_X = (int) m.getX(1);
                    int diff = GLOBAL_TOUCH_POSITION_X-GLOBAL_TOUCH_CURRENT_POSITION_X;
                    actionString = "Diff "+diff+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    GLOBAL_TOUCH_POSITION_X = (int) m.getX(1);
                    actionString = "DOWN"+" current "+GLOBAL_TOUCH_CURRENT_POSITION_X+" prev "+GLOBAL_TOUCH_POSITION_X;
                    break;
                default:
                    actionString = "";
            }*/
        }

        if(action == MotionEvent.ACTION_UP)
        {
            GLOBAL_INITIAL_POSITION_X = -1;
            GLOBAL_CURRENT_POSITION_X = -1;
            GLOBAL_DESSIN = -1;
        }
    }

    public void getPen(View v)
    {
        drawzone.getPen();
    }

    public void getRubber(View v)
    {
        drawzone.getRubber();
    }

    public void setSmallPen(View v)
    {
        drawzone.setSizePen(8);
        buttonPen.setVisibility(View.VISIBLE);
        listPen.setVisibility(View.INVISIBLE);
    }

    public void setMediumPen(View v)
    {
        drawzone.setSizePen(12);
        buttonPen.setVisibility(View.VISIBLE);
        listPen.setVisibility(View.INVISIBLE);
    }

    public void setBigPen(View v)
    {
        drawzone.setSizePen(16);
        buttonPen.setVisibility(View.VISIBLE);
        listPen.setVisibility(View.INVISIBLE);
    }

    public void setSmallRubber(View v)
    {
        drawzone.setSizeRubber(8);
        buttonRubber.setVisibility(View.VISIBLE);
        listRubber.setVisibility(View.INVISIBLE);
    }

    public void setMediumRubber(View v)
    {
        drawzone.setSizeRubber(12);
        buttonRubber.setVisibility(View.VISIBLE);
        listRubber.setVisibility(View.INVISIBLE);
    }

    public void setBigRubber(View v)
    {
        drawzone.setSizeRubber(16);
        buttonRubber.setVisibility(View.VISIBLE);
        listRubber.setVisibility(View.INVISIBLE);
    }


    public void setBlack(View v)
    {
        buttonColor.setImageResource(R.drawable.noir);
        drawzone.setBlack();

        buttonColor.setVisibility(View.VISIBLE);
        listColor.setVisibility(View.INVISIBLE);
    }

    public void setRed(View v)
    {
        buttonColor.setImageResource(R.drawable.rouge);
        drawzone.setRed();

        buttonColor.setVisibility(View.VISIBLE);
        listColor.setVisibility(View.INVISIBLE);
    }

    public void setBlue(View v)
    {
        buttonColor.setImageResource(R.drawable.bleu);
        drawzone.setBlue();

        buttonColor.setVisibility(View.VISIBLE);
        listColor.setVisibility(View.INVISIBLE);
    }

    public void setGreen(View v)
    {
        buttonColor.setImageResource(R.drawable.vert);
        drawzone.setGreen();

        buttonColor.setVisibility(View.VISIBLE);
        listColor.setVisibility(View.INVISIBLE);
    }

    public void afficheColor(View v)
    {
        listColor.setVisibility(View.VISIBLE);
        buttonColor.setVisibility(View.INVISIBLE);
    }
}
