package com.example.soram.iasbp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

/**
 * Created by sOram on 11. 11. 2017.
 */

public class MainFragment extends Fragment{
    View view;
    TextView textHumi;
    TextView textTemp;
    ArrayList<String> HumiValues;
    ArrayList<String> HumiTime;
    ArrayList<String> TempTime;
    ArrayList<String> TempValues;
    CircleProgressView circleProgressView;
    Float maxValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_fragment, container, false);

        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        maxValue = Float.parseFloat(HumiValues.get(HumiValues.size()-1));
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


        return view;
    }
}
