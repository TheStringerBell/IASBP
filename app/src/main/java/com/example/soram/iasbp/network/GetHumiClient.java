package com.example.soram.iasbp.network;


import com.example.soram.iasbp.ApiKeys;
import com.example.soram.iasbp.pojo.GetControlData;
import com.example.soram.iasbp.pojo.GetEnergyData;
import com.example.soram.iasbp.pojo.GetHumiData;
import com.example.soram.iasbp.pojo.GetInsideData;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetHumiClient {

    final String USERNAME = ApiKeys.username;
    final String PASSWORD = ApiKeys.publicKey;
    OkHttpClient client;
    Retrofit retrofit;

    public GetHumiClient(){

        client = new HttpClient(USERNAME,PASSWORD).getDefaultClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiKeys.link)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Single<List<GetHumiData>> getHumiDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .sqlData(ApiKeys.humiData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetHumiData>> getTempDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .sqlData(ApiKeys.tempData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetEnergyData>> getEnergyDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .energyData(ApiKeys.energy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetInsideData>> getInsideDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .insideData(ApiKeys.insideData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetControlData>> getControlDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .controlData(ApiKeys.control)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
