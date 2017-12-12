package com.example.soram.iasbp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;



public class ControlFragment extends Fragment {
    View view;
    ImageView imageView;
    ImageView imageView2;

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
        String url2 = "https://ia800208.us.archive.org/4/items/Popeye_forPresident/Popeye_forPresident_512kb.mp4";
//        InputStream inputStream = this.getResources().openRawResource(R.raw.frame1);
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageBitmap(bitmap);
//        Glide.with(getActivity()).load(R.raw.frame1).into(imageView);
//        Glide.with(getActivity()).load(R.raw.frame2).into(imageView2);




//        lowMax = view.findViewById(R.id.editText2);
//        lowMin = view.findViewById(R.id.editText);
//        highMax = view.findViewById(R.id.editText4);
//        highMin = view.findViewById(R.id.editText3);
//        lowMax2 = view.findViewById(R.id.editText5);
//        lowMin2 = view.findViewById(R.id.editText6);
//        highMin2 = view.findViewById(R.id.editText7);
//        highMax2 = view.findViewById(R.id.editText8);
//        lowM = view.findViewById(R.id.textView);
//        lowMa = view.findViewById(R.id.textView2);
//        highM = view.findViewById(R.id.textView3);
//        highMa = view.findViewById(R.id.textView4);
//        ln3 = view.findViewById(R.id.linearLayout3);
//        relativeLayout = view.findViewById(R.id.relativeView);
//        controlHighMax = getArguments().getStringArrayList("HighMax");
//        controlStatus = getArguments().getStringArrayList("Status");
//        controlLowMin = getArguments().getStringArrayList("LowMin");
//        controlLowMax = getArguments().getStringArrayList("LowMax");
//        controlHighMin = getArguments().getStringArrayList("HighMin");
//        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hideKeyboard(view);
//            }
//        });
//
//
//        lowMin.setText(controlLowMin.get(0));
//        lowMax.setText(controlLowMax.get(0));
//        highMin.setText(controlHighMin.get(0));
//        highMax.setText(controlHighMax.get(0));
//        lowMin2.setText(controlLowMin.get(1));
//        lowMax2.setText(controlLowMax.get(1));
//        highMin2.setText(controlHighMin.get(1));
//        highMax2.setText(controlHighMax.get(1));
//
        return view;
    }
    public void loadCam(){
        int TIMEOUT = 5;


    }

    @Override
    public void onResume() {
        super.onResume();
        loadCam();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    //    public void hideKeyboard(View v){
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
//
//    }
    public void loadVideo(){

    }


}
