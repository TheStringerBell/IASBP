package com.example.soram.iasbp;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Control{

    final String URL = new ApiKeys().getLink();
    final String addition = new ApiKeys().getControltest();
    final String tempLowMin = new ApiKeys().getTempLowMin();
    final String humiLowMin = new ApiKeys().getHumiLowMin();
    final String GETTOKEN = new ApiKeys().getGetToken();
    final String USERNAME = new ApiKeys().getUsername();
    newControl mNewControl;
    String key;
    OkHttpClient client;

    String PASSWORD;
    public void generatePrivateToken(final String mode,final String mode2, final int i){
        mNewControl = new GetPrivateToken().getNewControl("", "");
        mNewControl.obstest(GETTOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        key = responseBodyResponse.headers().get("Token");
                        new ApiKeys().encryptToken(key, new GeneralCallback() {
                            @Override
                            public void onSuccess(String token) {
                                Log.e("Token", token);
                                PASSWORD = token;
                                client = new HttpClient(USERNAME,token, mode, mode2).getClient();
                                switch (i){
                                    case 0: updateControl(); break;
                                    case 1: updateTemp(); break;
                                    case 2: updateHumi(); break;
                                }


                            }
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateControl(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
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
    public void updateDefault(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
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


}

