package com.example.soram.iasbp.network;

import com.example.soram.iasbp.pojo.GetControlData;
import com.example.soram.iasbp.pojo.GetEnergyData;
import com.example.soram.iasbp.pojo.GetHumiData;
import com.example.soram.iasbp.pojo.GetInsideData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitModel{
    @GET
    Single<List<GetHumiData>> sqlData(@Url String url
    );
    @GET
    Single<List<GetControlData>> controlData(@Url String url
    );
    @GET
    Single<List<GetInsideData>> insideData(@Url String url
    );
    @GET
    Single<List<GetEnergyData>> energyData(@Url String url
    );
    @GET
    Call<Void> control(@Url String url
    );
    @GET
    Observable<Response<ResponseBody>> obstest(@Url String url
    );
    @GET
    Observable<List<GetHumiData>> humiTest(@Url String url
    );
    Observable<List<GetControlData>> contTest(@Url String url
    );

}



