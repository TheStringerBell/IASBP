package com.example.soram.iasbp;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Url;



public interface newControl{


    @GET
    retrofit2.Call<List<GetHumiData>> sqlData(@Url String url

    );

}



