package fr.ihm.RotoscopMe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;

public class CreateActivity extends Activity {

    protected static final int GET_VIDEO_ACTIVITY = 1;
    protected static final int CAPTURE_VIDEO_ACTIVITY = 2;

    protected TextView title;
    protected Button getVideoButton;
    protected Button captureVideoButton;
    protected TextView name;
    protected EditText editName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        editName = (EditText) findViewById(R.id.editName);
        getVideoButton = (Button) findViewById(R.id.get_video);
        captureVideoButton = (Button) findViewById(R.id.capture_video);
    }

    public void getVideo(View v)
    {
        Intent getVideo = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getVideo, GET_VIDEO_ACTIVITY);
    }

    public void captureVideo(View v)
    {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // TODO : force camera capture in landscape
        takeVideoIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, CAPTURE_VIDEO_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == GET_VIDEO_ACTIVITY)
        {
            getVideoButton.setText("Bravo!");
        }
        else if(requestCode == CAPTURE_VIDEO_ACTIVITY)
        {
            captureVideoButton.setText("Bravo!");
        }
    }
}
