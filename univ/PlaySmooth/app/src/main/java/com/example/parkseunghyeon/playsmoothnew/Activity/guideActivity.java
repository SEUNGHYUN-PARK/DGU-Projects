package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.parkseunghyeon.playsmoothnew.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class guideActivity extends YouTubeBaseActivity {


    /**
     * VideoView and MediaController
     */

    VideoView videoView2;
    MediaController mediaController2;
    String uriPath2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        videoView2 = (VideoView) findViewById(R.id.videoView2);
        mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView2);
        uriPath2 = "android.resource://"+getPackageName()+"/"+R.raw.guide;

        Uri video = Uri.parse(uriPath2);
        videoView2.setMediaController(mediaController2);
        videoView2.setVideoURI(video);
        videoView2.start();
    }
}
