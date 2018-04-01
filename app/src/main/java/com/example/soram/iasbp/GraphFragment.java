package com.example.soram.iasbp;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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


public class GraphFragment extends Fragment {
    View view;
    ArrayList<String> HumiValues;
    ArrayList<String> HumiTime;
    ArrayList<String> TempTime;
    ArrayList<String> TempValues;
    LineChart lineChart;
    LineChart lineChart2;
    ArrayList<Entry> entries;
    ArrayList<Entry> entries2;
    int mainPink;
    int naviGreen;
    int graph_text;
    int graph_setColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.graph_fragment, container, false);
        lineChart = view.findViewById(R.id.chart1);
        lineChart2 = view.findViewById(R.id.chart2);
        //get mysql Data
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        TempTime = getArguments().getStringArrayList("TempTime");

        mainPink = getResources().getColor(R.color.mainPink);

        graph_setColor = getResources().getColor(R.color.graph_setColor);
        graph_text = getResources().getColor( R.color.graph_text);
        naviGreen = getResources().getColor(R.color.greenNavi);

        entries = new ArrayList<>();
        Drawable gradient = ContextCompat.getDrawable(getContext(), R.drawable.graph_bg);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextSize(9);
        xAxis.setTextColor(graph_text);
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
        yAxis.setDrawGridLines(false);
        YAxis leftYaxis = lineChart.getAxisLeft();
        leftYaxis.setDrawAxisLine(false);
        leftYaxis.setDrawGridLines(false);
        leftYaxis.setGridColor(graph_text);
        leftYaxis.setTextColor(graph_text);
        LineDataSet lineDataSet = new LineDataSet(entries, "Humidity");
        lineDataSet.setColor(graph_setColor);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(1);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);

//        lineDataSet.setFillColor(getResources().getColor(R.color.graph_text));
        lineDataSet.setFillDrawable(gradient);
        LineData lineData = new LineData(lineDataSet);


        lineChart.setData(lineData);



        lineChart.setHighlightPerTapEnabled(true);
        lineChart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setLabelCount(6, true);
//        lineChart.getLegend().setEnabled(false);
        lineChart.getLegend().setTextColor(naviGreen);
        lineChart.animateX(1500);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String value = HumiTime.get((int) e.getX()) + "  /  " +e.getY() + "%" + " / " + TempValues.get((int) e.getX()) + "°C";
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {
            }
        });

        //Graph2
        entries2 = new ArrayList<>();
        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setDrawAxisLine(false);
        xAxis2.setLabelRotationAngle(-45);
        xAxis2.setTextSize(9);
        xAxis2.setTextColor(graph_text);
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
        yAxis2.setDrawGridLines(false);
        YAxis leftYaxis2 = lineChart2.getAxisLeft();
        leftYaxis2.setDrawAxisLine(false);
        leftYaxis2.setDrawGridLines(false);
        leftYaxis2.setTextColor(graph_text);
        leftYaxis2.setGridColor(graph_text);
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "Temperature");
        lineDataSet2.setColor(graph_setColor);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setLineWidth(1);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFillDrawable(gradient);

//        lineDataSet2.setFillColor(getResources().getColor(R.color.graph_text));
        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);
        lineChart2.getXAxis().setLabelCount(6, true);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        lineChart2.getLegend().setTextColor(graph_text);
        lineChart2.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String value = TempTime.get((int) e.getX()) + "  /  " +e.getY() + "°C" + " / " + HumiValues.get((int) e.getX()) + "%";
                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected() {
            }
        });
        lineChart2.animateX(1500);
        return view;
    }
}
