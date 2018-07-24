package retrofit_tutotrial.ibadan.android.videoview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private static final String PLAYTIME_TAG = "PLAYTIME-TAG";
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = findViewById(R.id.videoview);

        //Check if coming from orientation change and set the position
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYTIME_TAG);
        }

        //specify the file to be played from raw file
        String mediaName = "videofile";
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() +
                "/raw/" + mediaName);
        mVideoView.setVideoURI(videoUri);

        //specify the file to be played from external or internal file
        String mediaNameFromFile = "videofile.mp4";
        String fullPath = Environment.getExternalStorageDirectory() + "/" + mediaNameFromFile;
        File file = new File(fullPath);
        Uri videoUriFromFile = Uri.fromFile(file);
        mVideoView.setVideoURI(videoUriFromFile);

        //specify the file to be played from online url
        String mediaNameFromUri = "http://myserver.com/videofile.mp4";
        Uri videoUriFromUri = Uri.parse(mediaNameFromUri);
        mVideoView.setVideoURI(videoUriFromUri);

        //Set Media controller to the VideoView
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        if (mCurrentPosition > 0) {
            mVideoView.seekTo(mCurrentPosition);
        }
        mVideoView.start();

    }

    //Save the media position during orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYTIME_TAG, mVideoView.getCurrentPosition());
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Stop the video when no longer visible
        mVideoView.stopPlayback();
    }

}
