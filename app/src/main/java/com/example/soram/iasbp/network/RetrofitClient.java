package com.example.soram.iasbp.network;

import com.example.soram.iasbp.ApiKeys;
import com.example.soram.iasbp.pojo.GetHumiData;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    final String URL = new ApiKeys().getLink();
    OkHttpClient client;
    Retrofit retrofit;
    String USERNAME = new ApiKeys().getUsername();
    String PASSWORD =  new ApiKeys().getPublicKey();


    public RetrofitModel getRetrofitClient(String mode, String mode2){

        final Retrofit retrofit = getRetrofit(mode, mode2);

        return retrofit.create(RetrofitModel.class);
    }



    private Retrofit getRetrofit(String mode, String mode2){
        client = new HttpClient(USERNAME,PASSWORD).getDefaultClient();

        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public Single<List<GetHumiData>> getHumiDataSingle (){
        return retrofit.create(RetrofitModel.class)
                .sqlData(new ApiKeys().getHumiData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}