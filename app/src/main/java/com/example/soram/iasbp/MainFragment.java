package com.example.soram.iasbp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;


import java.net.UnknownHostException;
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
    Float maxValue;
    Float maxValue2;
    Float maxValue3;
    Float maxValue4;
    String outside;
    String lowMin;
    String lowMin2;
    TextView minMax;
    TextView textTemp;
    TextView humiValues;
    TextView heating;
    TextView humidication;
    ToggleSwitch toggleSwitch;
    ToggleSwitch toggleSwitch2;
    ArrayList<String> labels;
    LinearLayout linearLayout;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    RelativeLayout realL;
    RelativeLayout realL2;

    Animation animation;
    Animation animation2;
    Animation animation3;
    Animation animation4;
    Animation animation5;


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
        heating = view.findViewById(R.id.heating);
        humidication = view.findViewById(R.id.humiditySettings);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout3 = view.findViewById(R.id.linearLayout4);
        realL = view.findViewById(R.id.relatV);
        realL2 = view.findViewById(R.id.relatV2);
        setAnimation();




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
//        try {
//            PingResult pingResult = Ping.onAddress("172.217.5.195").doPing();
//            Log.e("das", pingResult.toString());
//        }catch (UnknownHostException e){
//            e.printStackTrace();
//        }








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
        circleProgressView.setBarColor(getResources().getColor(R.color.mainPink));
        circleProgressView.setRimColor(getResources().getColor(R.color.mainGray));
        circleProgressView.setTextSize(52);
        circleProgressView.setUnit("°C");
        circleProgressView.setUnitVisible(true);
        circleProgressView.setUnitSize(25);
        circleProgressView.setBarWidth(5);
        circleProgressView.setRimWidth(7);
        circleProgressView.setUnitColor(getResources().getColor(R.color.graph_text));
        circleProgressView.setTextColor(getResources().getColor(R.color.mainPink));
        circleProgressView.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView.setTextMode(TextMode.VALUE);
        circleProgressView.setValueAnimated(maxValue3);

        circleProgressView2.setMaxValue(100);
        circleProgressView2.setValue(0);
        circleProgressView2.setBarColor(getResources().getColor(R.color.mainPink));
        circleProgressView2.setRimColor(getResources().getColor(R.color.mainGray));
        circleProgressView2.setTextSize(52);
        circleProgressView2.setBarWidth(5);
        circleProgressView2.setRimWidth(7);
        circleProgressView2.setUnit("%");
        circleProgressView2.setUnitVisible(true);
        circleProgressView2.setUnitSize(25);
        circleProgressView2.setUnitColor(getResources().getColor(R.color.graph_text));
        circleProgressView2.setTextColor(getResources().getColor(R.color.mainPink));
        circleProgressView2.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView2.setTextMode(TextMode.VALUE);
        circleProgressView2.setValueAnimated(maxValue4);
        return view;
    }
    public void setAnimation(){
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        animation3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        animation4 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        animation5 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        animation.setDuration(1000);
        animation2.setDuration(1500);
        animation3.setDuration(2000);
        animation4.setDuration(2500);
        animation5.setDuration(3000);
        linearLayout3.startAnimation(animation);
        realL.startAnimation(animation2);
        textTemp.startAnimation(animation2);
        linearLayout.startAnimation(animation3);

        linearLayout2.startAnimation(animation3);

        heating.startAnimation(animation4);
        minMax.startAnimation(animation4);

        humiValues.startAnimation(animation5);
        humidication.startAnimation(animation5);
        realL2.startAnimation(animation5);

    }
}
