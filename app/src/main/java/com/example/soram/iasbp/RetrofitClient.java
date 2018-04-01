package com.example.soram.iasbp;
import android.util.Log;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    final String URL = new ApiKeys().getLink();
    OkHttpClient client;
    String USERNAME = new ApiKeys().getUsername();
    String PASSWORD =  new ApiKeys().getPublicKey();


    public RetrofitModel getRetrofitClient(String mode, String mode2){

        final Retrofit retrofit = getRetrofit(mode, mode2);

        return retrofit.create(RetrofitModel.class);
    }

    private Retrofit getRetrofit(String mode, String mode2){
        client = new HttpClient(USERNAME,PASSWORD,mode, mode2).getClient();

        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
}