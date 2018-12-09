package com.example.soram.iasbp.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.soram.iasbp.R;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;



public class EnergyFragment extends Fragment {
    View view;
    ArrayList<String> times;
    ArrayList<String> values;
    ArrayList<Entry> entries;
    TextView energytext;
    LineChart lineChart;
    int mainPink;
    int naviGreen;
    int graph_text;
    int graph_setColor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.energy_fragment, container, false);

        energytext = view.findViewById(R.id.energyText);
        lineChart = view.findViewById(R.id.chart3);

        times = getArguments().getStringArrayList("EnergyTime");
        values = getArguments().getStringArrayList("EnergyValue");

        energytext.setText(values.get(values.size()-1) + " W");

        mainPink = getResources().getColor(R.color.mainPink);

        graph_setColor = getResources().getColor(R.color.graph_setColor);
        graph_text = getResources().getColor( R.color.mainPink);
        naviGreen = getResources().getColor(R.color.graph_text);

        entries = new ArrayList<>();
        Drawable gradient = ContextCompat.getDrawable(getContext(), R.drawable.graph_bg);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextSize(9);
        xAxis.setDrawLabels(false);
        xAxis.setTextColor(graph_text);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return times.get((int)value);
            }
        });
        for (int i = 0; i < values.size()-1; i++){
            entries.add(new Entry(i,Float.parseFloat(values.get(i))));
        }
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        yAxis.setDrawGridLines(false);
        YAxis leftYaxis = lineChart.getAxisLeft();
        leftYaxis.setDrawAxisLine(false);
        leftYaxis.setDrawGridLines(false);
        leftYaxis.setGridColor(graph_text);
        leftYaxis.setTextColor(graph_text);
        LineDataSet lineDataSet = new LineDataSet(entries, "Humidity");
        lineDataSet.setColor(naviGreen);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(1);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);

//        lineDataSet.setFillColor(getResources().getColor(R.color.graph_text));
        lineDataSet.setFillDrawable(gradient);
        LineData lineData = new LineData(lineDataSet);


        lineChart.setData(lineData);



        lineChart.setHighlightPerTapEnabled(true);

        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setLabelCount(6, true);
//        lineChart.getLegend().setEnabled(false);
        lineChart.getLegend().setTextColor(naviGreen);
        lineChart.animateX(1500);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String value = times.get((int) e.getX()) +  " / " + values.get((int) e.getX()) + "W";
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {
            }
        });

        return view;

    }
}
