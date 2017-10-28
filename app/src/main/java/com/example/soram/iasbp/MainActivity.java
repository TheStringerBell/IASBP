package com.example.soram.iasbp;

import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import it.beppi.tristatetogglebutton_library.TriStateToggleButton;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textHumi;
    Response response = null;
    Response responseMode = null;
    String jsonData;
    String jsonMode;
    Integer mode;
    JSONObject jsonObject;
    JSONArray jsonArray;
    JSONArray jsonArrayMode;
    JSONObject value;
    Object jsonFinal;
    ArrayList<String> arrayTime = new ArrayList<String>();
    ArrayList<String> arrayDate = new ArrayList<String>();
    ArrayList<String> arrayValue = new ArrayList<String>();
    ArrayList<String> arrayMode = new ArrayList<String>();
    String values;
    Integer cont = 0;
    TriStateToggleButton tstb;
    Integer count = 0;
    Integer status;
    Integer ms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tstb = (TriStateToggleButton) findViewById(R.id.tstb_1);
        button = (Button) findViewById(R.id.button);
        textHumi = (TextView) findViewById(R.id.textHumi);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new firstSession().execute();


                if (cont > 0){
                    String on = "on";
                    String mid = "mid";
                    String off = "off";
                    if ((tstb.getToggleStatus().toString()).equals("off")){
                        ms = 0;
                    }
                    if ((tstb.getToggleStatus().toString()).equals("mid")){
                        ms = 1;
                    }
                    if ((tstb.getToggleStatus().toString()).equals("on")){
                        ms = 2;
                    }
                    if (!mode.equals(ms)){
                        new Control().execute(status.toString());




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

    }
    public class firstSession extends AsyncTask<String, String, String> {
        private static final String username = "sochor";
        private static final String password = "20sochor17";
        private static final String link = "http://slm.uniza.sk/~sochor/HumiData.php";
        private static final String urlMode = "    http://slm.uniza.sk/~sochor/ModeValue.php";


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Integer i = jsonArray.length()-1;
            textHumi.setText(arrayDate.get(i) + " - " + arrayTime.get(i) + " - " + arrayValue.get(i) + "%");
            Log.e("toto ", arrayMode.toString());
            mode = Integer.parseInt(arrayMode.get(0));
            tstb.setToggleStatus(mode);
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

                responseMode = client.newCall(requestMode).execute();
                response = client.newCall(request).execute();
                jsonData = response.body().string();
                jsonMode = responseMode.body().string();

                try {
                    jsonArrayMode = new JSONArray(jsonMode);
                    jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        arrayValue.add(jsonObject1.optString("Value"));
                        arrayDate.add(jsonObject1.optString("Date"));
                        arrayTime.add(jsonObject1.optString("Time"));
                    }
                    for (int i = 0; i < jsonArrayMode.length(); i++){
                        JSONObject jsonObject2 = jsonArrayMode.getJSONObject(i);

                        arrayMode.add(jsonObject2.optString("Mode"));

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
}
