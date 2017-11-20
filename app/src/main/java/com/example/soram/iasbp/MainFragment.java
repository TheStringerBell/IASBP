package com.example.soram.iasbp;


import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;


/**
 * Created by sOram on 11. 11. 2017.
 */

public class MainFragment extends Fragment{
    View view;
    TextView textView;
    TextView textMode;
    ArrayList<String> HumiValues;
    ArrayList<String> HumiTime;
    ArrayList<String> date;
    ArrayList<String> TempValues;
    ArrayList<String> mode;
    ArrayList<String> inside;
    CircleProgressView circleProgressView;
    CircleProgressView circleProgressView2;
    CircleProgressView circleProgressView3;
    CircleProgressView circleProgressView4;
    FrameLayout frameLayout;
    FrameLayout frameLayout2;
    Float maxValue;
    Float maxValue2;
    Float maxValue3;
    Float maxValue4;

    String dateAndTime;
    BlurMaskFilter blurMaskFilter;
    CircleMenu circleMenu;
    CircleMenu circleMenu2;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.test, container, false);

//        textView = (TextView) view.findViewById(R.id.textView);
        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        circleProgressView2 = (CircleProgressView) view.findViewById(R.id.circleView2);
        circleProgressView3 = (CircleProgressView) view.findViewById(R.id.circleView3);
        circleProgressView4 = (CircleProgressView) view.findViewById(R.id.circleView4);
//        frameLayout = (FrameLayout) view.findViewById(R.id.frame);
//        frameLayout2 = (FrameLayout) view.findViewById(R.id.frame2);
        circleMenu2 = (CircleMenu) view.findViewById(R.id.circle_menu2);
        circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);
//        frameLayout.setBackgroundColor(Color.parseColor("#474747"));

//        frameLayout2.setBackgroundColor(Color.parseColor("#474747"));
//        int[] colors = new int[]{ getResources().getColor(R.color.mainGray),getResources().getColor(R.color.gray)};
//        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//
//        gd.setStroke(1, getResources().getColor(R.color.white));
//        frameLayout2.setBackground(gd);
//        frameLayout.setBackground(gd);
//
//        frameLayout2.getBackground().setAlpha(50);
//        frameLayout.getBackground().setAlpha(50);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.frame);
        relativeLayout2 = (RelativeLayout) view.findViewById(R.id.frame2);
                int[] colors = new int[]{ getResources().getColor(R.color.mainGray),getResources().getColor(R.color.gray)};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setStroke(1, getResources().getColor(R.color.white));
        relativeLayout2.setBackground(gd);
        relativeLayout.setBackground(gd);

        relativeLayout.getBackground().setAlpha(75);
        relativeLayout2.getBackground().setAlpha(50);



        //get arraylists from Main Activity
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        date = getArguments().getStringArrayList("Date");
        mode = getArguments().getStringArrayList("Mode");
        inside = getArguments().getStringArrayList("Inside");

        maxValue = Float.parseFloat(HumiValues.get(HumiValues.size()-1));
        maxValue2 = Float.parseFloat(TempValues.get(TempValues.size()-1));
        maxValue3 = Float.parseFloat(inside.get(0));
        maxValue4 = Float.parseFloat(inside.get(1));
        dateAndTime = date.get(date.size()-1) + "  " + HumiTime.get(HumiTime.size()-1);
//        textView.setTextColor(Color.parseColor("#E8175D"));
//        textView.setText(dateAndTime);
//        blurMaskFilter = new BlurMaskFilter(textView.getTextSize()/6, BlurMaskFilter.Blur.SOLID);
//        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        textView.getPaint().setMaskFilter(blurMaskFilter);


        //cirslemenu
        circleMenu.setMainMenu(Color.parseColor("#E8175D"), R.mipmap.ic_menu, R.mipmap.ic_close_white)
                .addSubMenu(Color.parseColor("#680a29"), R.mipmap.ic_off3)
                .addSubMenu(Color.parseColor("#8c0e38"), R.mipmap.ic_auto2)
                .addSubMenu(Color.parseColor("#b21147"), R.mipmap.ic_fcd3);
        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onMenuSelected(int i) {
                new Control().updateControl(Integer.toString(i));
            }
        });

        circleMenu2.setMainMenu(Color.parseColor("#E8175D"), R.mipmap.ic_menu, R.mipmap.ic_close_white)
                .addSubMenu(Color.parseColor("#680a29"), R.mipmap.ic_off3)
                .addSubMenu(Color.parseColor("#8c0e38"), R.mipmap.ic_fcd3)
                .addSubMenu(Color.parseColor("#b21147"), R.mipmap.ic_auto2);

//        if (maxValue < 40){
//            typer.setText("Dry");
//        }
//        if (maxValue > 40 && maxValue < 60){
//            typer.setText("Comfort");
//        }
//        if (maxValue > 60){
//            typer.setText("Wet");
//        }


        circleProgressView.setMaxValue(100);
        circleProgressView.setValue(0);
        circleProgressView.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView.setRimColor(Color.parseColor("#474747"));
        circleProgressView.setTextSize(50);
        circleProgressView.setUnit("%");
        circleProgressView.setUnitVisible(true);
        circleProgressView.setUnitSize(27);
        circleProgressView.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView.setTextMode(TextMode.VALUE);
        circleProgressView.setValueAnimated(maxValue);

        circleProgressView2.setMaxValue(45);
        circleProgressView2.setValue(0);
        circleProgressView2.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView2.setRimColor(Color.parseColor("#474747"));
        circleProgressView2.setTextSize(50);
        circleProgressView2.setUnit("°C");
        circleProgressView2.setUnitVisible(true);
        circleProgressView2.setUnitSize(27);
        circleProgressView2.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView2.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView2.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView2.setTextMode(TextMode.VALUE);
        circleProgressView2.setValueAnimated(maxValue2);

        circleProgressView3.setMaxValue(45);
        circleProgressView3.setValue(0);
        circleProgressView3.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView3.setRimColor(Color.parseColor("#474747"));
        circleProgressView3.setTextSize(50);
        circleProgressView3.setUnit("°C");
        circleProgressView3.setUnitVisible(true);
        circleProgressView3.setUnitSize(27);
        circleProgressView3.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView3.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView3.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView3.setTextMode(TextMode.VALUE);
        circleProgressView3.setValueAnimated(maxValue3);

        circleProgressView4.setMaxValue(45);
        circleProgressView4.setValue(0);
        circleProgressView4.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView4.setRimColor(Color.parseColor("#474747"));
        circleProgressView4.setTextSize(50);
        circleProgressView4.setUnit("%");
        circleProgressView4.setUnitVisible(true);
        circleProgressView4.setUnitSize(27);
        circleProgressView4.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView4.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView4.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView4.setTextMode(TextMode.VALUE);
        circleProgressView4.setValueAnimated(maxValue4);


        return view;
    }
}
