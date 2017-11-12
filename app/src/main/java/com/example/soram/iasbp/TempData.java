package com.example.soram.iasbp;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by sOram on 11. 11. 2017.
 */

public interface TempData {
    @GET("http://slm.uniza.sk/~sochor/TempData/")
    retrofit2.Call<List<GetHumiData>> sqlData();
}
