package com.example.soram.iasbp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;



public interface newControl{


    @GET
    retrofit2.Call<List<GetHumiData>> sqlData(@Url String url
    );
    @GET
    Call<List<GetControlData>> controlData(@Url String url
    );
    @GET
    Call<List<GetInsideData>> insideData(@Url String url
    );

}



