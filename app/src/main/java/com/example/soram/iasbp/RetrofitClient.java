package com.example.soram.iasbp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sOram on 5. 11. 2017.
 */

public class RetrofitClient {
    String url = "http://slm.uniza.sk/~sochor/";
    List<GetHumiData> listing;
    String humiTemp;

    public void Login(String humiTemp){
        this.humiTemp = humiTemp;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newControl service = retrofit.create(newControl.class);
        Call<List<GetHumiData>> call = service.sqlData(humiTemp);
        call.enqueue(new Callback<List<GetHumiData>>() {
            @Override
            public void onResponse(Call<List<GetHumiData>> call, Response<List<GetHumiData>> response) {
                List<GetHumiData> list =response.body();
                GetHumiData getHumiData = null;
                listing = new ArrayList<GetHumiData>();
                for (int i =0 ;i<list.size();i++){
                    getHumiData = new GetHumiData();
                    String date= list.get(i).getDate();
                    String time = list.get(i).getTime();
                    String value = list.get(i).getValue();
                    getHumiData.setDate(date);
                    getHumiData.setTime(time);
                    getHumiData.setValue(value);
                    listing.add(getHumiData);




            }
                Log.e("succes ", " " + listing.get(listing.size()-1).getValue());
//                getHumiDatas(listing);


            }

            @Override
            public void onFailure(Call<List<GetHumiData>> call, Throwable t) {
                Log.e("Fail ",  " " + t);
            }
        });

    }
    public List<GetHumiData> getHumiDatas(List<GetHumiData> list){
        return list;

    }
//    public static void displayGraph(List<GetHumiData> list){
//        final BarChart mBarChart = (BarChart)
//        final List<GetHumiData> list2 = list;
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                for (int k = 0; k < list2.size()-1; k++){
//                    float l = Float.parseFloat(list2.get(k).getValue());
//                    mBarChart.addBar(new BarModel(l, Color.parseColor("#f2f4f7")));
//                }
//
//                mBarChart.setBarWidth(1);
//                mBarChart.startAnimation();
//            }
//        });
//    }



}
