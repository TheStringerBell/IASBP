package com.example.soram.iasbp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textHumi;
    TextView textTemp;
    TextView refresh;
    TextView temperature;
    ProgressBar progressBar;
    Response response = null;
    Response responseMode = null;
    Response responseTemp = null;
    String jsonData;
    String jsonMode;
    String jsonTemp;
    String test;
    JSONArray jsonArray;
    JSONArray jsonArrayMode;
    JSONArray jsonArrayTemp;
    ArrayList<String> arrayTime = new ArrayList<String>();
    ArrayList<String> arrayDate = new ArrayList<String>();
    ArrayList<String> arrayValue = new ArrayList<String>();
    ArrayList<String> arrayMode = new ArrayList<String>();
    ArrayList<String> arrayStatus = new ArrayList<String>();
    ArrayList<String> arrayTempDate = new ArrayList<String>();
    ArrayList<String> arrayTempTime = new ArrayList<String>();
    ArrayList<String> arrayTempValue = new ArrayList<String>();
    ArrayList<String> arrayColors = new ArrayList<String>();
    Integer cont = 0;
    TriStateToggleButton tstb;
    TriStateToggleButton tstb2;
    Integer count = 0;
    Integer graphCount = 1;
    Integer status;
    Integer ms;
    Integer ms2;
    Integer mode;
    Integer statusto;
    Integer status2;
    Integer progress = 0;
    Integer i;
    Integer j;
    BarChart mBarChart;
    ConstraintLayout constraintLayout;
    ActionBar actionBar;
    Boolean switchOn = false;
    RetrofitClient retrofitClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tstb = (TriStateToggleButton) findViewById(R.id.tstb_1);
        tstb2 = (TriStateToggleButton) findViewById(R.id.tstb_2);
        temperature = (TextView) findViewById(R.id.textGraph);
        textHumi = (TextView) findViewById(R.id.textHumi);
        textTemp = (TextView) findViewById(R.id.textTemp);
        mBarChart = (BarChart) findViewById(R.id.barchart);
        refresh = (TextView) findViewById(R.id.refresh);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        constraintLayout = (ConstraintLayout) findViewById(R.id.cl);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        progressBar.setVisibility(View.GONE);
        retrofitClient = new RetrofitClient();



//        final ObjectAnimator objectAnimator = ObjectAnimator.ofObject(constraintLayout, "backgroundColor", new ArgbEvaluator(), Color.WHITE, Color.DKGRAY);
//        objectAnimator.setDuration(500);
//        objectAnimator.setStartDelay(50);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new firstSession().execute();
                progressBar.setMax(5);
                progressBar.setVisibility(View.VISIBLE);
                retrofitClient.Login("HumiData");


                if (cont > 0){
                    if ((tstb.getToggleStatus().toString()).equals("off")){
                        ms = 0;
                    }
                    if ((tstb.getToggleStatus().toString()).equals("mid")){
                        ms = 1;
                    }
                    if ((tstb.getToggleStatus().toString()).equals("on")){
                        ms = 2;
                    }
                    if ((tstb2.getToggleStatus().toString()).equals("off")){
                        ms2 = 0;
                    }
                    if ((tstb2.getToggleStatus().toString()).equals("mid")){
                        ms2 = 1;
                    }
                    if ((tstb2.getToggleStatus().toString()).equals("on")){
                        ms2 = 2;
                    }
                    if (!mode.equals(ms) || !statusto.equals(ms2)){
                        new Control().execute(status.toString(), status2.toString());
                        mode = status;
                        statusto = status2;
                    }
                }
                cont++;
            }
        });

        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (graphCount > 0 && graphCount%2 !=0 || graphCount == 1){
                    temperature.setText("Humidity");
                    graphCount++;
                    mBarChart.clearChart();
                    Colours(i, arrayValue);
                }
                else{
                    temperature.setText("Temperature");
                    graphCount++;
                    mBarChart.clearChart();
                    Colours(j, arrayTempValue);
                }
            }
        });

        tstb.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off: status  = 0; switchOn = true; break;
                    case mid: status  = 1; switchOn = true; break;
                    case on:  status  = 2; switchOn = true; break;
                }
            }
        });
        tstb2.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off: status2  = 0; switchOn = true; break;
                    case mid: status2  = 1; switchOn = true; break;
                    case on:  status2  = 2; switchOn = true; break;
                }
            }
        });

    }
    public class firstSession extends AsyncTask<String, String, String> {

        private static final String link = "http://slm.uniza.sk/~sochor/HumiData.php";
        private static final String urlMode = "http://slm.uniza.sk/~sochor/ModeValue.php";
        private static final String urlTemp = "http://slm.uniza.sk/~sochor/TempData.php";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            i = jsonArray.length()-1;
            j = jsonArrayTemp.length()-1;
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setProgress(0);

            textHumi.setText(arrayDate.get(i) + " - " + arrayTime.get(i) + " - " + arrayValue.get(i) + "%");
            textTemp.setText(" " + arrayTempDate.get(j) + " - " + arrayTempTime.get(j) + " - " + arrayTempValue.get(j) + "°C");
            mode = Integer.parseInt(arrayMode.get(0));
            statusto = Integer.parseInt(arrayMode.get(1));
            if (switchOn == false){
                tstb.setToggleStatus(mode);
                tstb2.setToggleStatus(statusto);
            }
            if (cont == 1){
                status = mode;
                status2 = statusto;
                Colours(j, arrayTempValue);
            }
            switchOn = false;
            arrayMode.clear();

        }
        @Override
        protected String doInBackground(String... params) {

            try {

                OkHttpClient client = new OkHttpClient();
//                publishProgress(Integer.toString(1));
                Request request = new Request.Builder()
                        .url(link)
                        .build();
                Request requestMode = new Request.Builder()
                        .url(urlMode)
                        .build();
                Request requestTemp = new Request.Builder()
                        .url(urlTemp)
                        .build();




                responseMode = client.newCall(requestMode).execute();
                response = client.newCall(request).execute();
                responseTemp = client.newCall(requestTemp).execute();
                jsonData = response.body().string();
                jsonMode = responseMode.body().string();
                jsonTemp = responseTemp.body().string();
//                publishProgress(Integer.toString(2));


                try {
                    jsonArrayMode = new JSONArray(jsonMode);
                    jsonArray = new JSONArray(jsonData);
                    jsonArrayTemp = new JSONArray(jsonTemp);

                    for (int i = 0; i < jsonArray.length(); i++){
                        publishProgress(Integer.toString(jsonArray.length()), Integer.toString(i));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayValue.add(jsonObject1.optString("Value"));
                        arrayDate.add(jsonObject1.optString("Date"));
                        arrayTime.add(jsonObject1.optString("Time"));

                    }
                    publishProgress(Integer.toString(5), Integer.toString(0));


                    for (int i = 0; i < jsonArrayMode.length(); i++){
                        publishProgress(Integer.toString(jsonArrayMode.length()), Integer.toString(i));
                        JSONObject jsonObject2 = jsonArrayMode.getJSONObject(i);
                        arrayMode.add(jsonObject2.optString("Mode"));
//                        arrayStatus.add(jsonObject2.optString("Status"));
                    }
                    publishProgress(Integer.toString(5), Integer.toString(0));


                    for (int i = 0; i < jsonArrayTemp.length(); i++){
                        publishProgress(Integer.toString(jsonArrayTemp.length()), Integer.toString(i));
                        JSONObject jsonObject3 = jsonArrayTemp.getJSONObject(i);
                        arrayTempDate.add(jsonObject3.optString("Date"));
                        arrayTempTime.add(jsonObject3.optString("Time"));
                        arrayTempValue.add(jsonObject3.optString("Value"));
                    }
//                    publishProgress(Integer.toString(5));
                    publishProgress(Integer.toString(5), Integer.toString(5));





                }catch (JSONException js){
                    js.printStackTrace();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressBar.setMax(Integer.parseInt(values[0]));
            progressBar.setProgress(Integer.parseInt(values[1]));
        }
    }

    public void Colours(int j, ArrayList<String> array){
//        Random random = new Random();//
//        int[] colors = new int[]{0xFF123456,0xFF343456,0xFF563456,0xFF873F56,0xFF56B7F1,0xFF343456,0xFF1FF4AC,0xFF1BA4E6};
//        int randomColor = random.nextInt(colors.length);
        for (int k = 0; k < j; k++){
            float l = Float.parseFloat(array.get(k));
            mBarChart.addBar(new BarModel(l, Color.parseColor("#f2f4f7")));
        }

        mBarChart.setBarWidth(1);
        mBarChart.startAnimation();


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
}
