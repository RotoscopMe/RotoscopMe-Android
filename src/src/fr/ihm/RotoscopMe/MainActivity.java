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
