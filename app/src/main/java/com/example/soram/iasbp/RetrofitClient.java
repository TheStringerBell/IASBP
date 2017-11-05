package com.example.soram.iasbp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.client.params.HttpClientParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by sOram on 5. 11. 2017.
 */

public class RetrofitClient {
    String url = "http://slm.uniza.sk/~sochor/";
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
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
                getHumiDatas(listing);

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

}
