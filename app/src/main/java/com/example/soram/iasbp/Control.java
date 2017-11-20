package com.example.soram.iasbp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Control{

    final String URL = new ApiKeys().getLink();
    final String das = new ApiKeys().getControltest();

    public void updateControl(String mode){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newControl service = retrofit.create(newControl.class);
        retrofit2.Call<Void> call = service.control(das+mode);
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

