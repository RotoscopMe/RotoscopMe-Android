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
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        drawzone = (DrawZone) findViewById(R.id.drawzone);
        drawzone.setParent(this);
        button = (Button) findViewById(R.id.button);

        mPlanetTitles = new String[]{"Enregistrer","Enregistrer sous", "Exporter en image", "Exporter en vidéo","Partager","Préférences","Fermer le projet","Quitter","Aide","A propos"};

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

       //Set the adapter for the list view
       mDrawerList.setAdapter(new ArrayAdapter<String>(this,
               drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);;
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (position == 0 ){
                button.setText("Bravo !");
            };
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
       // Fragment fragment = new PlanetFragment();
        //Bundle args = new Bundle();
      //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
     // fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
       // fragmentManager.beginTransaction()
         //       .replace(R.id.content_frame, fragment)
           //     .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
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
}
