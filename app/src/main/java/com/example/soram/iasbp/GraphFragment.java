package com.example.soram.iasbp;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;


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
    TextView textView;


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
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setTextColor(Color.parseColor("#E8175D"));
        textView.setVisibility(View.GONE);

        ArrayList<Entry> entries = new ArrayList<>();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
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
        leftYaxis.setDrawAxisLine(false);
        leftYaxis.setGridColor(Color.parseColor("#CC527A"));
        leftYaxis.setTextColor(Color.parseColor("#CC527A"));
        LineDataSet lineDataSet = new LineDataSet(entries, "Humidity");
        lineDataSet.setColor(Color.parseColor("#E8175D"));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(Color.parseColor("#CC527A"));
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setHighlightPerTapEnabled(true);
        lineChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setLabelCount(6, true);
//        lineChart.getLegend().setEnabled(false);
        lineChart.getLegend().setTextColor(Color.parseColor("#CC527A"));
        lineChart.animateX(1500);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(" " +e.getY()+ "%");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //Graph2
        ArrayList<Entry> entries2 = new ArrayList<>();
        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setDrawAxisLine(false);
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
        leftYaxis2.setDrawAxisLine(false);
        leftYaxis2.setTextColor(Color.parseColor("#CC527A"));
        leftYaxis2.setGridColor(Color.parseColor("#CC527A"));
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "Temperature");
        lineDataSet2.setColor(Color.parseColor("#E8175D"));
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setLineWidth(3);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFillColor(Color.parseColor("#CC527A"));
        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);
        lineChart2.getXAxis().setLabelCount(6, true);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart2.getLegend().setTextColor(Color.parseColor("#CC527A"));
        lineChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(" " +e.getY() + "°C");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        lineChart2.animateX(1500);
        return view;


    }




}
