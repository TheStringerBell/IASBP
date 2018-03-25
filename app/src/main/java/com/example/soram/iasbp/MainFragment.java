package com.example.soram.iasbp;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.stealthcopter.networktools.Ping;
import com.stealthcopter.networktools.ping.PingResult;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import io.github.tonnyl.light.Light;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
    ArrayList<String> deviceStatus;
    CircleProgressView circleProgressView;
    CircleProgressView circleProgressView2;
    Float maxValue;
    Float maxValue2;
    Float maxValue3;
    Float maxValue4;
    String outside;
    String lowMin;
    String lowMin2;
    String raspberryPi;
    String ipCam;
    TextView minMax;
    TextView textTemp;
    TextView humiValues;
    TextView heating;
    TextView humidication;
    TextView devices;
    TextView device1;
    TextView device2;
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
    int mainPink;
    int mainGray;
    int mainCenter;
    int mainCenter2;
    int tiles_inactive;
    int gray;
    int graph_text;
    Handler handler = new Handler();
    Runnable runnable;
    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayList<String> ips;
    ArrayList<String> names;
    FloatingActionButton floatbut;
    PingResult pingResult;
    Observable mObservable;
    Observer mObserver;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.main_fragment, container, false);

        devices = view.findViewById(R.id.devices);
//        device1 = view.findViewById(R.id.device1);
//        device2 = view.findViewById(R.id.device2);
        listView = view.findViewById(R.id.listview);
        floatbut = view.findViewById(R.id.floatbut);

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

        // COLORS
        mainPink = getResources().getColor(R.color.mainPink);
        mainGray = getResources().getColor(R.color.mainGray);
        mainCenter =getResources().getColor( R.color.mainCenter);
        mainCenter2 =getResources().getColor( R.color.mainCenter2);
        tiles_inactive =getResources().getColor( R.color.tiles_inactive);
        gray = getResources().getColor(R.color.gray);
        graph_text =getResources().getColor( R.color.graph_text);

        setAnimation();
        getValues();
        listView.setOnItemClickListener((adapterView, view1, i, l) ->
            pingTime(i)
        );
//        mObservable.subscribe(mObserver);


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
        toggleSwitch.setActiveBgColor(mainCenter);
        toggleSwitch.setInactiveTextColor(tiles_inactive);
        toggleSwitch.setInactiveBgColor(gray);
        toggleSwitch.setCheckedTogglePosition(Integer.parseInt(mode.get(0)));
        toggleSwitch.setOnToggleSwitchChangeListener((position, isChecked) -> {
            new Control().generatePrivateToken(Integer.toString(position), mode.get(1) , 0);
            mode.set(0, Integer.toString(position));
        });

        labels = new ArrayList<>();
        labels.add("OFF");
        labels.add("AUTO");
        labels.add("ON");
        toggleSwitch2.setLabels(labels);
        toggleSwitch2.setActiveBgColor(mainCenter2);
        toggleSwitch2.setInactiveTextColor(tiles_inactive);
        toggleSwitch2.setInactiveBgColor(gray);
        toggleSwitch2.setCheckedTogglePosition(Integer.parseInt(mode.get(1)));
        toggleSwitch2.setOnToggleSwitchChangeListener((position, isChecked) -> {
            new Control().generatePrivateToken(mode.get(0), Integer.toString(position), 0);
            mode.set(1, Integer.toString(position));
        });


        minMax.setOnClickListener(view1 -> openDialog("Update temperature", "21", "23", 0));
        humiValues.setOnClickListener(view1 -> openDialog("Update humidity", "40", "60", 1));

        circleProgressView.setMaxValue(45);
        circleProgressView.setValue(0);
        circleProgressView.setBarColor(mainPink);
        circleProgressView.setRimColor(mainGray);
        circleProgressView.setTextSize(52);
        circleProgressView.setUnit("°C");
        circleProgressView.setUnitVisible(true);
        circleProgressView.setUnitSize(25);
        circleProgressView.setBarWidth(5);
        circleProgressView.setRimWidth(7);
        circleProgressView.setUnitColor(graph_text);
        circleProgressView.setTextColor(mainPink);
        circleProgressView.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView.setTextMode(TextMode.VALUE);
        circleProgressView.setValueAnimated(maxValue3);

        circleProgressView2.setMaxValue(100);
        circleProgressView2.setValue(0);
        circleProgressView2.setBarColor(mainPink);
        circleProgressView2.setRimColor(mainGray);
        circleProgressView2.setTextSize(52);
        circleProgressView2.setBarWidth(5);
        circleProgressView2.setRimWidth(7);
        circleProgressView2.setUnit("%");
        circleProgressView2.setUnitVisible(true);
        circleProgressView2.setUnitSize(25);
        circleProgressView2.setUnitColor(graph_text);
        circleProgressView2.setTextColor(mainPink);
        circleProgressView2.setDecimalFormat(new DecimalFormat("#.#"));
        circleProgressView2.setTextMode(TextMode.VALUE);
        circleProgressView2.setValueAnimated(maxValue4);


        floatbut.setOnClickListener(view1 -> addAddressDialog());

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
        devices.startAnimation(animation5);

    }

    public void addAddressDialog(){
        final EditText first = new EditText(getContext());
        final EditText second = new EditText(getContext());
        final LinearLayout ln = new LinearLayout(getContext());
        final TextView title = new TextView(getContext());
        title.setText(R.string.AddIPAddress);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0,25,0,0);

        first.setHint("Name");
        second.setHint("IP address");
        first.setGravity(Gravity.CENTER_HORIZONTAL);
        second.setGravity(Gravity.CENTER_HORIZONTAL);
        first.setTextColor(getResources().getColor(R.color.mainPink));
        second.setTextColor(getResources().getColor(R.color.mainPink));
        title.setTextColor(getResources().getColor(R.color.mainPink));
        first.setHintTextColor(getResources().getColor(R.color.tiles_inactive));
        second.setHintTextColor(getResources().getColor(R.color.tiles_inactive));
        first.setPadding(0,15,0,15);
        second.setPadding(0,15,0,15);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setGravity(Gravity.CENTER);
        ln.setBackgroundColor(getResources().getColor(R.color.gray));

        ln.addView(first, 500,100);
        ln.addView(second,500,100);
        new AlertDialog.Builder(getContext(), R.style.dialogTheme)
                .setCustomTitle(title)
                .setView(ln)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = first.getText().toString();
                        String ip = second.getText().toString();
                        arrayList.add("");
                        names.add( name);
                        ips.add(ip);


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    public void openDialog(String tit, final String def, final String def2, final int in){
        final EditText first = new EditText(getContext());
        final EditText second = new EditText(getContext());
        final LinearLayout ln = new LinearLayout(getContext());
        final TextView title = new TextView(getContext());
        title.setText(tit);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0,25,0,0);

        first.setHint(def);
        second.setHint(def2);
        first.setGravity(Gravity.CENTER_HORIZONTAL);
        second.setGravity(Gravity.CENTER_HORIZONTAL);
        first.setTextColor(getResources().getColor(R.color.mainPink));
        second.setTextColor(getResources().getColor(R.color.mainPink));
        title.setTextColor(getResources().getColor(R.color.mainPink));
        first.setHintTextColor(getResources().getColor(R.color.mainPink));
        second.setHintTextColor(getResources().getColor(R.color.mainPink));
        first.setPadding(0,15,0,15);
        second.setPadding(0,15,0,15);
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setGravity(Gravity.CENTER);
        ln.setBackgroundColor(getResources().getColor(R.color.gray));

        ln.addView(first, 100,100);
        ln.addView(second,100,100);

        new AlertDialog.Builder(getContext(), R.style.dialogTheme)
                .setCustomTitle(title)
                .setView(ln)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String one = first.getText().toString();
                        String two = second.getText().toString();
                        if (one.equals("")){
                            one = def;
                        }
                        if (two.equals("")){
                            two = def2;
                        }
                        if (in == 0){
                            new Control().generatePrivateToken(one, two, 1);
                        }else {
                            new Control().generatePrivateToken(one, two, 2);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }



    @Override
    public void onAttach(Context context) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ips.size(); i++){
                    pingIt(i,names.get(i),ips.get(i));

                }
//                Log.d("LIST ", names.toString());
                pingList();
                runnable = this;
                handler.postDelayed(runnable, 3000);

            }
        }, 3000);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        handler.removeCallbacks(runnable);
        super.onDetach();
    }

    public void pingList(){

        if (getContext() != null) {
            adapter = new ArrayAdapter<>(getContext(), R.layout.simple_row, arrayList);

            listView.setAdapter(adapter);
        }



    }
    public void getValues(){

        raspberryPi = new ApiKeys().getRaspberryPi();
        ipCam = new ApiKeys().getIpCamIP();
        arrayList = new ArrayList<>();

        names = new ArrayList<>();
        names.add("RaspBerry Pi");
        names.add("IP Cam");
        names.add("LM UNIZA");
        names.add("Google DNS");

        ips = new ArrayList<>();
        ips.add(raspberryPi);
        ips.add(ipCam);
        ips.add("158.193.254.60");
        ips.add("8.8.8.8");
        for (int i = 0; i < names.size(); i++){
            arrayList.add("");
        }

    }

    public void pingIt(int i ,String name, String url){

        mObservable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return Ping.onAddress(url).setTimeOutMillis(1000).doPing().isReachable();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result){
                        arrayList.set(i, name +"  |   ONLINE");
                    }
                    else {
                        arrayList.set(i, name +"  |  OFFLINE");
                    }
                });

    }
    public void pingTime(int i){
        mObservable.fromCallable(new Callable<Float>() {
            @Override
            public Float call() throws Exception {
                return Ping.onAddress(ips.get(i)).setTimeOutMillis(1000).doPing().getTimeTaken();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> Light.normal(listView,"IP: " +  ips.get(i) + "      Delay: " + result + " ms", Snackbar.LENGTH_SHORT).show());



    }
}