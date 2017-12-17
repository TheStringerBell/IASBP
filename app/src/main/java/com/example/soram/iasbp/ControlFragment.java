package com.example.soram.iasbp;


import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class ControlFragment extends Fragment {
    View view;
    ImageView imageView;
    ImageView imageView2;
    String url2;
    Handler handler = new Handler();
    Runnable runnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, container, false);
        imageView = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);

        String url = "http://188.123.101.50:8083/mjpg/video.mjpg";
        url2 = new ApiKeys().getIpCam();
        return view;
    }
    public void loadCam(){
        Picasso.with(getContext())
                .load(url2)
                .noPlaceholder()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imageView);
    }

    @Override
    public void onAttach(Context context) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCam();
                runnable = this;
                handler.postDelayed(runnable, 1000);

            }
        }, 100);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        handler.removeCallbacks(runnable);
        super.onDetach();
    }

}
