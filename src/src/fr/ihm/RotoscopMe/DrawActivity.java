package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DrawActivity extends Activity {

    protected DrawZone drawzone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);

        drawzone = (DrawZone) findViewById(R.id.drawzone);
    }
}
