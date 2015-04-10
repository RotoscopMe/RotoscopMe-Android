package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends Activity {

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
        createProjectButton.setText("Bravo !");
    }

    public void openProject(View v)
    {
        Intent intentDraw = new Intent(this, DrawActivity.class);
        startActivity(intentDraw);
    }
}
