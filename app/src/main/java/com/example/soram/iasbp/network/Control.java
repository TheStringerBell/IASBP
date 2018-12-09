package com.example.soram.iasbp.network;
import com.example.soram.iasbp.ApiKeys;
import com.stealthcopter.networktools.WakeOnLan;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Control{


    final String addition = ApiKeys.controltest;
    final String tempLowMin = ApiKeys.tempLowMin;
    final String humiLowMin = ApiKeys.humiLowMin;
    final String USERNAME = ApiKeys.username;
    final String PASSWORD = ApiKeys.publicKey;
    final String URL = ApiKeys.link;
    OkHttpClient client;
    RetrofitModel service;
    Observable observable;


    public void generatePrivateToken(final String mode,final String mode2, final int i){
        client = new HttpClient(USERNAME,PASSWORD).getControlClient(mode, mode2);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitModel.class);


            switch (i){
                case 0: updateControl(); break;
                case 1: updateTemp(); break;
                case 2: updateHumi(); break;
            }
    }


    public void updateControl(){

        retrofit2.Call<Void> call = service.control(addition);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {

            }
            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {

            }
        });
    }
    public void updateTemp(){

        retrofit2.Call<Void> call = service.control(tempLowMin);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {

            }
            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {

            }
        });
    }
    public void updateHumi(){
        retrofit2.Call<Void> call = service.control(humiLowMin);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
            }
            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {

            }
        });
    }

    public void wakeOnLan(String ip, String mac){
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
               WakeOnLan.sendWakeOnLan(ip, mac);
               return 1;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();


    }

}

