package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    protected Button createProjectButton;
    protected Button openProjectButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        createProjectButton = (Button) findViewById(R.id.create_project);
        openProjectButton = (Button) findViewById(R.id.open_project);
    }

    public void createProject(View v)
    {
        createProjectButton.setText("Bravo !");
    }
}
