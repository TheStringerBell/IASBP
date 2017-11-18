package com.example.soram.iasbp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;
import lecho.lib.hellocharts.model.Line;

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
    CircleProgressView circleProgressView;
    CircleProgressView circleProgressView2;
    Float maxValue;
    Float maxValue2;
    ShimmerTextView typer;
    String dateAndTime;
    BlurMaskFilter blurMaskFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_fragment, container, false);
        typer = ( ShimmerTextView) view.findViewById(R.id.typer);
        textView = (TextView) view.findViewById(R.id.textView);
        textMode = (TextView) view.findViewById(R.id.textMode);
        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        circleProgressView2 = (CircleProgressView) view.findViewById(R.id.circleView2);
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        date = getArguments().getStringArrayList("Date");
        mode = getArguments().getStringArrayList("Mode");
        maxValue = Float.parseFloat(HumiValues.get(HumiValues.size()-1));
        maxValue2 = Float.parseFloat(TempValues.get(TempValues.size()-1));
        typer.setTextColor(Color.parseColor("#2d2e30"));
        typer.setReflectionColor(Color.parseColor("#E8175D"));
        typer.setTextSize(15);
        dateAndTime = date.get(date.size()-1) + "  " + HumiTime.get(HumiTime.size()-1);
        textView.setTextColor(Color.parseColor("#E8175D"));
        textView.setText(dateAndTime);
        blurMaskFilter = new BlurMaskFilter(textView.getTextSize()/6, BlurMaskFilter.Blur.SOLID);
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        textView.getPaint().setMaskFilter(blurMaskFilter);
        typer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        typer.getPaint().setMaskFilter(blurMaskFilter);


        Shimmer shimmer = new Shimmer();
        shimmer.setDuration(3000);

        shimmer.start(typer);
        if (maxValue < 40){
            typer.setText("Dry");
        }
        if (maxValue > 40 && maxValue < 60){
            typer.setText("Comfort");
        }
        if (maxValue > 60){
            typer.setText("Wet");
        }


        circleProgressView.setMaxValue(100);
        circleProgressView.setValue(0);
        circleProgressView.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView.setRimColor(Color.parseColor("#474747"));
        circleProgressView.setTextSize(100);
        circleProgressView.setUnit("%");
        circleProgressView.setUnitVisible(true);
        circleProgressView.setUnitSize(40);
        circleProgressView.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView.setTextMode(TextMode.VALUE);
        circleProgressView.setValueAnimated(maxValue);
//        BitmapFactory bitmapFactory = new BitmapFactory(circleProgressView);
//        Canvas canvas = new Canvas(circleProgressView);
//        Bitmap test = circleProgressView.getBackground();






                circleProgressView2.setMaxValue(50);
        circleProgressView2.setValue(0);
        circleProgressView2.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView2.setRimColor(Color.parseColor("#474747"));
        circleProgressView2.setTextSize(100);
        circleProgressView2.setUnit("°C");
        circleProgressView2.setUnitVisible(true);
        circleProgressView2.setUnitSize(40);
        circleProgressView2.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView2.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView2.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView2.setTextMode(TextMode.VALUE);
        circleProgressView2.setValueAnimated(maxValue2);


        return view;
    }
}
