package com.example.soram.iasbp;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Control{

    final String URL = new ApiKeys().getLink();
    final String addition = new ApiKeys().getControltest();
    OkHttpClient client;
    String USERNAME;
    String PASSWORD;

    public void updateControl(String mode, String mode2){
        USERNAME = new ApiKeys().getUsername();
        PASSWORD = new ApiKeys().getPassword();
        client = new HttpClient(USERNAME,PASSWORD,mode, mode2).getClient();

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

