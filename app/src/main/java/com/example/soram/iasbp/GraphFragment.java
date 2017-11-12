package com.example.soram.iasbp;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sOram on 11. 11. 2017.
 */

public class GraphFragment extends Fragment {
    View view;

    ArrayList<String> HumiValues;
    ArrayList<String> HumiTime;
    ArrayList<String> TempTime;
    ArrayList<String> TempValues;
    LineChart lineChart;
    LineChart lineChart2;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.graph_fragment, container, false);
        lineChart = (LineChart) view.findViewById(R.id.chart1);
        lineChart2 = (LineChart) view.findViewById(R.id.chart2);
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        TempTime = getArguments().getStringArrayList("TempTime");

//        valueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);


        ArrayList<Entry> entries = new ArrayList<>();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.parseColor("#CC527A"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return HumiTime.get((int)value);
            }
        });
        for (int i = 0; i < HumiValues.size()-1; i++){
            entries.add(new Entry(i,Float.parseFloat(HumiValues.get(i))));
        }
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        YAxis leftYaxis = lineChart.getAxisLeft();
        leftYaxis.setGridColor(Color.parseColor("#CC527A"));
        leftYaxis.setTextColor(Color.parseColor("#CC527A"));
        LineDataSet lineDataSet = new LineDataSet(entries, "Humidity");
        lineDataSet.setColor(Color.parseColor("#E8175D"));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.parseColor("#CC527A"));
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart.getDescription().setEnabled(false);
//        lineChart.getLegend().setEnabled(false);
        lineChart.getLegend().setTextColor(Color.parseColor("#CC527A"));
        lineChart.animateX(1500);

        //Graph2
        ArrayList<Entry> entries2 = new ArrayList<>();
        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setTextColor(Color.parseColor("#CC527A"));
        xAxis2.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return TempTime.get((int)value);
            }
        });
        for (int i = 0; i < TempValues.size()-1; i++){
            entries2.add(new Entry(i,Float.parseFloat(TempValues.get(i))));
        }
        YAxis yAxis2 = lineChart2.getAxisRight();
        yAxis2.setEnabled(false);
        YAxis leftYaxis2 = lineChart2.getAxisLeft();
        leftYaxis2.setTextColor(Color.parseColor("#CC527A"));
        leftYaxis2.setGridColor(Color.parseColor("#CC527A"));
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "Temperature");
        lineDataSet2.setColor(Color.parseColor("#E8175D"));
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setLineWidth(3);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFillColor(Color.parseColor("#CC527A"));
        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart2.getLegend().setTextColor(Color.parseColor("#CC527A"));
//        lineChart2.getLegend().setEnabled(false);
        lineChart2.animateX(1500);





//        for (int i = 0; i < 15; i++) {
//            float val = (float) (Math.random() * 2) + 3;
//            entries.add(new Entry(i, val));




//
//        ValueLineSeries series = new ValueLineSeries();
//        series.setColor(Color.parseColor("#E8175D"));
//        for (int k = 0; k < HumiTime.size()-1; k++){
//            float l = Float.parseFloat(HumiValues.get(k));
//
//            series.addPoint(new ValueLinePoint(HumiTime.get(k), l));
//        }
//        valueLineChart.addSeries(series);
//        valueLineChart.setVisibility(View.VISIBLE);
//        valueLineChart.setLegendColor(Color.parseColor("#f2f4f7"));
//        valueLineChart.setAxisTextColor(Color.parseColor("#A8A7A7"));
//        valueLineChart.setUseDynamicScaling(true);
//        valueLineChart.startAnimation();





        return view;
    }



}
