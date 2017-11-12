package com.example.soram.iasbp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.provider.BlockedNumberContract;
import android.support.annotation.IntegerRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;



public class MainActivity extends AppCompatActivity {


    ArrayList<String> arrayTime = new ArrayList<String>();
    ArrayList<String> arrayDate = new ArrayList<String>();
    ArrayList<String> arrayValue = new ArrayList<String>();
    ArrayList<String> arrayMode = new ArrayList<String>();
    ArrayList<String> arrayStatus = new ArrayList<String>();
    ArrayList<String> arrayTempDate = new ArrayList<String>();
    ArrayList<String> arrayTempTime = new ArrayList<String>();
    ArrayList<String> arrayTempValue = new ArrayList<String>();
    Integer cont = 0;
    ConstraintLayout constraintLayout;
    ActionBar actionBar;
    Boolean humiOrTemp = false;
    List<GetHumiData> listing;
    Bundle bundle;
    NavigationTabStrip tiles;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tiles = (NavigationTabStrip) findViewById(R.id.tiles);
        constraintLayout = (ConstraintLayout) findViewById(R.id.cl);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#282828")));
        actionBar.setElevation(0);
        actionBar.setTitle(Html.fromHtml("<font color='#444444'>IAS BP</font>"));
//        actionBar.setTitle("IAS BP");
        Login(new ApiKeys().getHumiData());

        tiles.setTitles("Home", "Graphs", "Control");
        tiles.setInactiveColor(Color.parseColor("#7c7c7c"));
        tiles.setActiveColor(Color.parseColor("#E8175D"));
        tiles.setTabIndex(0, true);
        tiles.setStripColor(Color.parseColor("#E8175D"));

        tiles.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                switch (title){
                    case "GRAPHS": loadFragment(new GraphFragment(), bundle);  break;
                    case "HOME": arrayDate.clear();
                        arrayTempTime.clear();
                        arrayTempValue.clear();
                        arrayTime.clear();
                        arrayValue.clear();
                        arrayTempDate.clear();
                        cont = 0;
                        humiOrTemp = false;
                        Login(new ApiKeys().getHumiData()
                        ); break;
                    case "CONTROL": loadFragment(new MainFragment(), bundle); break;
                }
            }

            @Override
            public void onEndTabSelected(String title, int index) {

            }
        });


    }

    public void Login(String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://slm.uniza.sk/~sochor/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newControl service = retrofit.create(newControl.class);
        Call<List<GetHumiData>> call = service.sqlData(url);
        call.enqueue(new Callback<List<GetHumiData>>() {
            @Override
            public void onResponse(Call<List<GetHumiData>> call, Response<List<GetHumiData>> response) {
                List<GetHumiData> list = response.body();
                GetHumiData getHumiData = null;
                listing = new ArrayList<GetHumiData>();
                for (int i = 0; i < list.size(); i++) {
                    getHumiData = new GetHumiData();
                    String date = list.get(i).getDate();
                    String time = list.get(i).getTime();
                    String value = list.get(i).getValue();
                    getHumiData.setDate(date);
                    getHumiData.setTime(time);
                    getHumiData.setValue(value);
                    if (!humiOrTemp){

                        arrayValue.add(value);
                        arrayTime.add(time);
                        arrayDate.add(date);

                    }else {
                        arrayTempDate.add(date);
                        arrayTempValue.add(value);
                        arrayTempTime.add(time);
                    }
                    listing.add(getHumiData);
                }
                if (humiOrTemp){
                    bundle = new Bundle();
                    bundle.putStringArrayList("HumiValues", arrayValue);
                    bundle.putStringArrayList("HumiTime", arrayTime);
                    bundle.putStringArrayList("TempValues", arrayTempValue);
                    bundle.putStringArrayList("TempTime", arrayTempTime);
//                    deleyedLoop();


                    if (cont == 0){
                        loadFragment(new MainFragment(), bundle);
                    }
                    cont++;
                }else {
                    humiOrTemp = true;
                    Login(new ApiKeys().getTempData());

                }
            }

            @Override
            public void onFailure(Call<List<GetHumiData>> call, Throwable t) {
                Log.e("Fail ", " " + t);
            }
        });

    }
    public void loadFragment(Fragment fragment, Bundle bundle){
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();

    }
//    public void deleyedLoop(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true){
//                        Thread.sleep(5000);
//                        Log.e("toto","deley");
//                        arrayDate.clear();
//                        arrayTempTime.clear();
//                        arrayTempValue.clear();
//                        arrayTime.clear();
//                        arrayValue.clear();
//                        arrayTempDate.clear();
//                        cont = 0;
//                        humiOrTemp = false;
//                        Login(new ApiKeys().getHumiData());
//
//                    }
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//    }


}


