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
    ApiKeys apiKeys = new ApiKeys();

    final String URL = apiKeys.getLink();
    OkHttpClient client;
    Retrofit retrofit;
    String USERNAME = apiKeys.getUsername();
    String PASSWORD =  apiKeys.getPublicKey();



    public GetHumiClient(){
        client = new HttpClient(USERNAME,PASSWORD).getDefaultClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Single<List<GetHumiData>> getHumiDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .sqlData(apiKeys.getHumiData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetHumiData>> getTempDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .sqlData(apiKeys.getTempData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetEnergyData>> getEnergyDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .energyData(apiKeys.getEnergy())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetInsideData>> getInsideDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .insideData(apiKeys.getInsideData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<GetControlData>> getControlDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .controlData(apiKeys.getControl())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
