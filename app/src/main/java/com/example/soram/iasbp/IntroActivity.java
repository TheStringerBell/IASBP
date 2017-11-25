package com.example.soram.iasbp;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;


public class IntroActivity extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        videoView =(VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switchScreen();
            }
        });
        videoView.start();
    }
    public void switchScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
