package com.example.soram.iasbp;

import java.util.List;
import java.util.Observable;

import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.*;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by sOram on 2. 11. 2017.
 */

public interface newControl{

    @GET
    retrofit2.Call<List<GetHumiData>> sqlData(@Url String url
//            @Path("data") String data
    );

}
//    @GET("/{data}")
//    io.reactivex.Observable<List<GetHumiData>> sqlData(@Path("data") String Data);


