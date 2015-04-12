package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

public class MainActivity extends Activity  {

    public static int GLOBAL_TOUCH_POSITION_X = 0 ;
    public static int GLOBAL_TOUCH_CURRENT_POSITION_X = 0;

    protected TextView title;
    protected Button createProjectButton;
    protected Button openProjectButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        title = (TextView) findViewById(R.id.title);
        createProjectButton = (Button) findViewById(R.id.create_project);
        openProjectButton = (Button) findViewById(R.id.open_project);

       //Two-Finger Drag Gesture detection
        RelativeLayout TextLoggerLayout = (RelativeLayout)findViewById(R.id.drawer_layout);
        TextLoggerLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener(){

                    @Override
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }

                });
    }

    void handleTouch(MotionEvent m){
        //Number of touches
        int pointerCount = m.getPointerCount();
        if(pointerCount == 2){
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;
            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
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
            }

            pointerCount = 0;
        }
        else {
            GLOBAL_TOUCH_POSITION_X = 0;
            GLOBAL_TOUCH_CURRENT_POSITION_X = 0;
        }
    }
    
    public void createProject(View v)
    {
        Intent intentCreate = new Intent(this, CreateActivity.class);
        startActivity(intentCreate);
    }

    public void openProject(View v)
    {
        Intent intentDraw = new Intent(this, DrawActivity.class);
        startActivity(intentDraw);
    }
}
