package com.example.soram.iasbp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;



public class MysqlConnect{
    ArrayList<String> arrayTime = new ArrayList<String>();
    ArrayList<String> arrayDate = new ArrayList<String>();
    ArrayList<String> arrayValue = new ArrayList<String>();
    ArrayList<String> insideArray = new ArrayList<String>();
    ArrayList<String> arrayTempDate = new ArrayList<String>();
    ArrayList<String> arrayTempTime = new ArrayList<String>();
    ArrayList<String> arrayTempValue = new ArrayList<String>();
    //Control
    ArrayList<String> controlMode = new ArrayList<>();
    ArrayList<String> controlStatus = new ArrayList<String>();
    ArrayList<String> controlLowMin = new ArrayList<String>();
    ArrayList<String> controlLowMax = new ArrayList<String>();
    ArrayList<String> controlHighMin = new ArrayList<String>();
    ArrayList<String> controlHighMax = new ArrayList<String>();
    Integer cont = 0;
    Boolean humiOrTemp = false;
    Bundle bundle;
    String HOST_URL;
    String USERNAME;
    String PASSWORD;
    String HUMIDATA;
    String TEMPDATA;
    String CONTROL;
    String INSIDEDATA;
    OkHttpClient client;
    String emptyTag;

    public void getHumiData(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newControl service = retrofit.create(newControl.class);
        Call<List<GetHumiData>> call = service.sqlData(url);
        call.enqueue(new Callback<List<GetHumiData>>() {
            @Override
            public void onResponse(Call<List<GetHumiData>> call, Response<List<GetHumiData>> response) {
                List<GetHumiData> list = response.body();
                for (int i = 0; i < list.size(); i++) {
                    Log.e("Humi", "");
                    String date = list.get(i).getDate();
                    String time = list.get(i).getTime();
                    String value = list.get(i).getValue();
                    if (!humiOrTemp){
                        arrayValue.add(value);
                        arrayTime.add(time);
                        arrayDate.add(date);
                    }else {
                        arrayTempDate.add(date);
                        arrayTempValue.add(value);
                        arrayTempTime.add(time);
                    }
                }
                if (humiOrTemp){
                    bundle = new Bundle();
                    bundle.putStringArrayList("HumiValues", arrayValue);
                    bundle.putStringArrayList("HumiTime", arrayTime);
                    bundle.putStringArrayList("TempValues", arrayTempValue);
                    bundle.putStringArrayList("TempTime", arrayTempTime);
                    bundle.putStringArrayList("Date", arrayTempDate);

                    if (cont == 0){
                        getControlData();
                    }
                    cont++;
                }else {
                    humiOrTemp = true;
                    getHumiData(TEMPDATA);
                }
            }
            @Override
            public void onFailure(Call<List<GetHumiData>> call, Throwable t) {
                Log.e("Fail ", " " + t);
            }
        });

    }
    public void getControlData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        Call<List<GetControlData>> call = service.controlData(CONTROL);
        call.enqueue(new Callback<List<GetControlData>>() {
            @Override
            public void onResponse(Call<List<GetControlData>> call, Response<List<GetControlData>> response) {
                List<GetControlData> list = response.body();
                Log.e("Control", "");

                for (int i = 0; i < list.size(); i++){

                    controlMode.add(list.get(i).getMode());
                    controlHighMax.add(list.get(i).getHighMax());
                    controlHighMin.add(list.get(i).getHighMin());
                    controlLowMax.add(list.get(i).getLowMax());
                    controlLowMin.add(list.get(i).getLowMin());
                    controlStatus.add(list.get(i).getStatus());
                }
                bundle.putStringArrayList("HighMax", controlHighMax);
                bundle.putStringArrayList("HighMin", controlHighMin);
                bundle.putStringArrayList("LowMax", controlLowMax);
                bundle.putStringArrayList("LowMin", controlLowMin);
                bundle.putStringArrayList("Status", controlStatus);
                bundle.putStringArrayList("Mode", controlMode);
                getInsideData();
            }
            @Override
            public void onFailure(Call<List<GetControlData>> call, Throwable t) {
                Log.e("Fail ", " " + t);
            }
        });

    }
    public void getInsideData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        Call<List<GetInsideData>> call = service.insideData(INSIDEDATA);
        call.enqueue(new Callback<List<GetInsideData>>() {
            @Override
            public void onResponse(Call<List<GetInsideData>> call, Response<List<GetInsideData>> response) {
                Log.e("Inside", "");
                List<GetInsideData> list = response.body();
                for (int i = 0; i < list.size(); i++){
                    insideArray.add(list.get(i).getValue());
                }
                bundle.putStringArrayList("Inside", insideArray);

            }

            @Override
            public void onFailure(Call<List<GetInsideData>> call, Throwable t) {

            }
        });


    }
    public void generateCredentials(){
        USERNAME = new ApiKeys().getUsername();
        PASSWORD = new ApiKeys().getPassword();
        Log.e("cred", "");
        client = new HttpClient(USERNAME,PASSWORD, emptyTag, emptyTag).getClient();



    }
    public void getValues(){
        emptyTag = "";
        HOST_URL = new ApiKeys().getLink();
        HUMIDATA = new ApiKeys().getHumiData();
        TEMPDATA = new ApiKeys().getTempData();
        CONTROL  = new ApiKeys().getControl();
        INSIDEDATA = new ApiKeys().getInsideData();
        Log.e("values","");


    }
    public Bundle getBundle(){
        return bundle;
    }
}
