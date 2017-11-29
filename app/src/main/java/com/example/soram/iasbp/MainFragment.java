package com.example.soram.iasbp;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;


import java.text.DecimalFormat;
import java.util.ArrayList;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;
import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import devlight.io.library.ArcProgressStackView;


public class MainFragment extends Fragment{
    View view;
    ArrayList<String> HumiValues;
    ArrayList<String> HumiTime;
    ArrayList<String> date;
    ArrayList<String> TempValues;
    ArrayList<String> mode;
    ArrayList<String> inside;
    ArrayList<String> controlLowMax;
    ArrayList<String> controlHighMin;
    CircleProgressView circleProgressView;
    CircleProgressView circleProgressView2;
    CircleProgressView circleProgressView3;
    CircleProgressView circleProgressView4;
    Float maxValue;
    Float maxValue2;
    Float maxValue3;
    Float maxValue4;
    String dateAndTime;
    String outside;
    String lowMin;
    String lowMin2;
    CircleMenu circleMenu;
    CircleMenu circleMenu2;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout2;
    TextView minMax;
    TextView textTemp;
    TextView humiValues;
    ToggleSwitch toggleSwitch;
    ToggleSwitch toggleSwitch2;
    ArrayList<String> labels;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.test, container, false);

        humiValues = view.findViewById(R.id.humiValues);
        toggleSwitch = view.findViewById(R.id.toggleswitch);
        toggleSwitch2 = view.findViewById(R.id.toggleswitch2);
        textTemp = view.findViewById(R.id.textTemp);
        circleProgressView  = view.findViewById(R.id.circleView);
        circleProgressView2 = view.findViewById(R.id.circleView2);
        minMax = view.findViewById(R.id.minMax);
//        circleProgressView3 = view.findViewById(R.id.circleView2);
//        circleProgressView4 = view.findViewById(R.id.circleView4);
//        circleMenu2 = view.findViewById(R.id.circle_menu2);
//        circleMenu  = view.findViewById(R.id.circle_menu);
//
//        relativeLayout  = view.findViewById(R.id.frame);
//        relativeLayout2 = view.findViewById(R.id.frame2);
//                int[] colors = new int[]{ getResources().getColor(R.color.mainGray),getResources().getColor(R.color.gray)};
//        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
//
//        gd.setStroke(1, getResources().getColor(R.color.white));
//        relativeLayout2.setBackground(gd);
//        relativeLayout.setBackground(gd);
//
//        relativeLayout.getBackground().setAlpha(75);
//        relativeLayout2.getBackground().setAlpha(75);
//
//
//
//        //get arraylists from Main Activity
        HumiTime = getArguments().getStringArrayList("HumiTime");
        HumiValues = getArguments().getStringArrayList("HumiValues");
        TempValues = getArguments().getStringArrayList("TempValues");
        controlLowMax = getArguments().getStringArrayList("LowMax");
        controlHighMin = getArguments().getStringArrayList("HighMin");
        date = getArguments().getStringArrayList("Date");
        mode = getArguments().getStringArrayList("Mode");
        inside = getArguments().getStringArrayList("Inside");



        maxValue = Float.parseFloat(HumiValues.get(HumiValues.size()-1));
        maxValue2 = Float.parseFloat(TempValues.get(TempValues.size()-1));
        maxValue3 = Float.parseFloat(inside.get(0));
        maxValue4 = Float.parseFloat(inside.get(1));
        outside ="\n\n" + "Outside:" + "\n" + TempValues.get(TempValues.size()-1) + " °C" + "\n" + HumiValues.get(HumiValues.size()-1) + " %";
        lowMin = "Min: " + controlLowMax.get(0) + " °C"  + "\n" + "Max: " + controlHighMin.get(0)+ " °C" ;
        lowMin2 = "Min: " + controlLowMax.get(1) + " %"  + "\n" + "Max: " + controlHighMin.get(1)+ " %" ;
        minMax.setText(lowMin);
        textTemp.setText(outside);
        humiValues.setText(lowMin2);
        labels = new ArrayList<>();
        labels.add("OFF");
        labels.add("AUTO");
        labels.add("ON");
        toggleSwitch.setLabels(labels);
        toggleSwitch.setActiveBgColor(getResources().getColor(R.color.mainPink));
        toggleSwitch.setInactiveTextColor(getResources().getColor(R.color.tiles_inactive));
        toggleSwitch.setInactiveBgColor(getResources().getColor(R.color.gray));
        toggleSwitch.setCheckedTogglePosition(Integer.parseInt(mode.get(0)));
        toggleSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                new Control().updateControl(Integer.toString(position), mode.get(1));
                mode.set(0, Integer.toString(position));
            }
        });
        labels = new ArrayList<>();
        labels.add("OFF");
        labels.add("AUTO");
        labels.add("ON");
        toggleSwitch2.setLabels(labels);
        toggleSwitch2.setActiveBgColor(getResources().getColor(R.color.mainPink));
        toggleSwitch2.setInactiveTextColor(getResources().getColor(R.color.tiles_inactive));
        toggleSwitch2.setInactiveBgColor(getResources().getColor(R.color.gray));
        toggleSwitch2.setCheckedTogglePosition(Integer.parseInt(mode.get(1)));
        toggleSwitch2.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                new Control().updateControl(mode.get(0), Integer.toString(position));
                mode.set(1, Integer.toString(position));

            }
        });






//        dateAndTime = date.get(date.size()-1) + "  " + HumiTime.get(HumiTime.size()-1);
//
//
        //cirslemenu
//        circleMenu.setMainMenu(Color.parseColor("#E8175D"), R.mipmap.ic_menu, R.mipmap.ic_close_white)
//                .addSubMenu(Color.parseColor("#680a29"), R.mipmap.ic_off3)
//                .addSubMenu(Color.parseColor("#8c0e38"), R.mipmap.ic_auto2)
//                .addSubMenu(Color.parseColor("#b21147"), R.mipmap.ic_fcd3);
//        circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
//            @Override
//            public void onMenuSelected(int i) {
//                new Control().updateControl(Integer.toString(i), mode.get(1));
//                mode.set(0, Integer.toString(i));
//            }
//        });
//
//        circleMenu2.setMainMenu(Color.parseColor("#E8175D"), R.mipmap.ic_menu, R.mipmap.ic_close_white)
//                .addSubMenu(Color.parseColor("#680a29"), R.mipmap.ic_off3)
//                .addSubMenu(Color.parseColor("#8c0e38"), R.mipmap.ic_auto2)
//                .addSubMenu(Color.parseColor("#b21147"), R.mipmap.ic_fcd3);
//        circleMenu2.setOnMenuSelectedListener(new OnMenuSelectedListener() {
//            @Override
//            public void onMenuSelected(int i) {
//                new Control().updateControl(mode.get(0), Integer.toString(i));
//                mode.set(1, Integer.toString(i));
//
//            }
//        });


        circleProgressView.setMaxValue(45);
        circleProgressView.setValue(0);
        circleProgressView.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView.setRimColor(Color.parseColor("#474747"));
        circleProgressView.setTextSize(52);
        circleProgressView.setUnit("°C");
        circleProgressView.setUnitVisible(true);
        circleProgressView.setUnitSize(25);
        circleProgressView.setBarWidth(5);
        circleProgressView.setRimWidth(7);
        circleProgressView.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView.setTextMode(TextMode.VALUE);
        circleProgressView.setValueAnimated(maxValue3);

        circleProgressView2.setMaxValue(100);
        circleProgressView2.setValue(0);
        circleProgressView2.setBarColor(Color.parseColor("#E8175D"));
        circleProgressView2.setRimColor(Color.parseColor("#474747"));
        circleProgressView2.setTextSize(52);
        circleProgressView2.setBarWidth(5);
        circleProgressView2.setRimWidth(7);
        circleProgressView2.setUnit("%");
        circleProgressView2.setUnitVisible(true);
        circleProgressView2.setUnitSize(25);
        circleProgressView2.setUnitColor(Color.parseColor("#CC527A"));
        circleProgressView2.setTextColor(Color.parseColor("#E8175D"));
        circleProgressView2.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView2.setTextMode(TextMode.VALUE);
        circleProgressView2.setValueAnimated(maxValue4);

//        circleProgressView3.setMaxValue(45);
//        circleProgressView3.setValue(0);
//        circleProgressView3.setBarColor(Color.parseColor("#E8175D"));
//        circleProgressView3.setRimColor(Color.parseColor("#474747"));
//        circleProgressView3.setTextSize(50);
//        circleProgressView3.setUnit("°C");
//        circleProgressView3.setUnitVisible(true);
//        circleProgressView3.setUnitSize(27);
//        circleProgressView3.setUnitColor(Color.parseColor("#CC527A"));
//        circleProgressView3.setTextColor(Color.parseColor("#E8175D"));
//        circleProgressView3.setDecimalFormat(new DecimalFormat("#.#"));
//        circleProgressView3.setTextMode(TextMode.VALUE);
//        circleProgressView3.setValueAnimated(maxValue3);
//
//        circleProgressView4.setMaxValue(100);
//        circleProgressView4.setValue(0);
//        circleProgressView4.setBarColor(Color.parseColor("#E8175D"));
//        circleProgressView4.setRimColor(Color.parseColor("#474747"));
//        circleProgressView4.setTextSize(50);
//        circleProgressView4.setUnit("%");
//        circleProgressView4.setUnitVisible(true);
//        circleProgressView4.setUnitSize(27);
//        circleProgressView4.setUnitColor(Color.parseColor("#CC527A"));
//        circleProgressView4.setTextColor(Color.parseColor("#E8175D"));
//        circleProgressView4.setDecimalFormat(new DecimalFormat("#.#"));
//        circleProgressView4.setTextMode(TextMode.VALUE);
//        circleProgressView4.setValueAnimated(maxValue4);


        return view;
    }
}
