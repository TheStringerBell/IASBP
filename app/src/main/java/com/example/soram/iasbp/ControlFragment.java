package com.example.soram.iasbp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.reactivestreams.Subscription;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ControlFragment extends Fragment {
    View view;
    ImageView imageView;
    ImageView imageView2;
    String url2;
    Handler handler = new Handler();
    Runnable runnable;



    ArrayList<String> controlMode;
    ArrayList<String> controlStatus;
    ArrayList<String> controlLowMin;
    ArrayList<String> controlLowMax;
    ArrayList<String> controlHighMin;
    ArrayList<String> controlHighMax;
    EditText lowMin;
    EditText lowMax;
    EditText highMin;
    EditText highMax;
    EditText lowMin2;
    EditText lowMax2;
    EditText highMin2;
    EditText highMax2;
    TextView lowM;
    TextView lowMa;
    TextView highM;
    TextView highMa;
    RelativeLayout relativeLayout;
    InputMethodManager imm;
    LinearLayout ln3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, container, false);
        imageView = view.findViewById(R.id.imageView1);
        imageView2 = view.findViewById(R.id.imageView2);

        String url = "http://188.123.101.50:8083/mjpg/video.mjpg";
        url2 = "http://158.193.254.248/cgi-bin/video.jpg";


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
    public void infiniteLoop(){


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

    public void loadVideo(){

    }


}
