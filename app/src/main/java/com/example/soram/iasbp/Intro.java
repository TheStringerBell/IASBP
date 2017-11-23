package com.example.soram.iasbp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;


/**
 * Created by sOram on 22. 11. 2017.
 */

public class Intro extends Fragment {
    View view;
    VideoView videoView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.intro_fragment, container, false);
        videoView = view.findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                Fragment fragment = new MainFragment();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.relativeView, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


            }
        });
        videoView.start();






        return view;
    }
}
