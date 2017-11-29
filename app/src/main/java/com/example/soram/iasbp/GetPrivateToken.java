package com.example.soram.iasbp;
import android.util.Log;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetPrivateToken {
    final String URL = new ApiKeys().getLink();
    final String addition = new ApiKeys().getGetToken();
    OkHttpClient client;
    String USERNAME = new ApiKeys().getUsername();
    String PASSWORD =  new ApiKeys().getPublicKey();
    String token;
    String key;

    public void setToken(String mode, String mode2){
        client = new HttpClient(USERNAME,PASSWORD,mode, mode2).getClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        retrofit2.Call<Void> call = service.control(addition);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                token = response.headers().get("Token");
                Log.e("qew", key);
            }
            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                Log.e("Error ", t.toString());
            }

        });


    }
}