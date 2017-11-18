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
    ArrayList<String> inside;
    CircleProgressView circleProgressView;
    CircleProgressView circleProgressView2;
    CircleProgressView circleProgressView3;
    CircleProgressView circleProgressView4;
    Float maxValue;
    Float maxValue2;
    Float maxValue3;
    Float maxValue4;
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
//        textMode = (TextView) view.findViewById(R.id.textMode);
        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        circleProgressView2 = (CircleProgressView) view.findViewById(R.id.circleView2);
        circleProgressView3 = (CircleProgressView) view.findViewById(R.id.circleView3);
        circleProgressView4 = (CircleProgressView) view.findViewById(R.id.circleView4);
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
