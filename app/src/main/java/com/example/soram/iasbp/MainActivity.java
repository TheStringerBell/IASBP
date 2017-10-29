package com.example.soram.iasbp;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textHumi;
    TextView textTemp;
    Response response = null;
    Response responseMode = null;
    Response responseTemp = null;
    String jsonData;
    String jsonMode;
    String jsonTemp;
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
    Integer status;
    Integer ms;
    Integer ms2;
    Integer mode;
    Integer statusto;
    Integer status2;
    BarChart mBarChart;
    ConstraintLayout constraintLayout;
    ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tstb = (TriStateToggleButton) findViewById(R.id.tstb_1);
        tstb2 = (TriStateToggleButton) findViewById(R.id.tstb_2);
        button = (Button) findViewById(R.id.button);
        textHumi = (TextView) findViewById(R.id.textHumi);
        textTemp = (TextView) findViewById(R.id.textTemp);
        mBarChart = (BarChart) findViewById(R.id.barchart);
        constraintLayout = (ConstraintLayout) findViewById(R.id.cl);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
        final ObjectAnimator objectAnimator = ObjectAnimator.ofObject(constraintLayout, "backgroundColor", new ArgbEvaluator(), Color.WHITE, Color.DKGRAY);
        objectAnimator.setDuration(500);
        objectAnimator.setStartDelay(50);







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont == 0){
                    objectAnimator.start();
                }

                new firstSession().execute();




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
                    }
                }
                cont++;
            }
        });

        tstb.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off: status  = 0; break;
                    case mid: status  = 1; break;
                    case on:  status  = 2; break;
                }
            }
        });
        tstb2.setOnToggleChanged(new TriStateToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(TriStateToggleButton.ToggleStatus toggleStatus, boolean booleanToggleStatus, int toggleIntValue) {
                switch (toggleStatus) {
                    case off: status2  = 0; break;
                    case mid: status2  = 1; break;
                    case on:  status2  = 2; break;
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
            Integer i = jsonArray.length()-1;
            Integer j = jsonArrayTemp.length()-1;
            textHumi.setText(arrayDate.get(i) + " - " + arrayTime.get(i) + " - " + arrayValue.get(i) + "%");
            textTemp.setText(" " + arrayTempDate.get(j) + " - " + arrayTempTime.get(j) + " - " + arrayTempValue.get(j) + "°C");
            Log.e("toto ", arrayMode.toString());
            mode = Integer.parseInt(arrayMode.get(0));
            statusto = Integer.parseInt(arrayMode.get(1));
            status = mode;
            status2 = statusto;
            tstb.setToggleStatus(mode);
            tstb2.setToggleStatus(statusto);
            Colours(j);

            arrayMode.clear();


        }
        @Override
        protected String doInBackground(String... params) {
            try {

                OkHttpClient client = new OkHttpClient();
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

                try {
                    jsonArrayMode = new JSONArray(jsonMode);
                    jsonArray = new JSONArray(jsonData);
                    jsonArrayTemp = new JSONArray(jsonTemp);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayValue.add(jsonObject1.optString("Value"));
                        arrayDate.add(jsonObject1.optString("Date"));
                        arrayTime.add(jsonObject1.optString("Time"));
                    }
                    for (int i = 0; i < jsonArrayMode.length(); i++){
                        JSONObject jsonObject2 = jsonArrayMode.getJSONObject(i);
                        arrayMode.add(jsonObject2.optString("Mode"));
//                        arrayStatus.add(jsonObject2.optString("Status"));
                    }
                    for (int i = 0; i < jsonArrayTemp.length(); i++){
                        JSONObject jsonObject3 = jsonArrayTemp.getJSONObject(i);
                        arrayTempDate.add(jsonObject3.optString("Date"));
                        arrayTempTime.add(jsonObject3.optString("Time"));
                        arrayTempValue.add(jsonObject3.optString("Value"));
                    }

                }catch (JSONException js){
                    js.printStackTrace();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
            return null;
        }
    }

    public void Colours(int j){
        Random random = new Random();
        int[] colors = new int[]{0xFF123456,0xFF343456,0xFF563456,0xFF873F56,0xFF56B7F1,0xFF343456,0xFF1FF4AC,0xFF1BA4E6};

        for (int k = 0; k < j; k+=10){
            int randomColor = random.nextInt(colors.length);
            float l = Float.parseFloat(arrayTempValue.get(k));
            mBarChart.addBar(new BarModel(l, colors[randomColor]));
        }
        mBarChart.startAnimation();




    }
}
